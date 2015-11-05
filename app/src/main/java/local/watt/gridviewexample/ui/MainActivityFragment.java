package local.watt.gridviewexample.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import local.watt.gridviewexample.R;
import local.watt.gridviewexample.adapters.MovieGridAdapter;
import local.watt.gridviewexample.dialogs.NetworkConnectivityDialog;
import local.watt.gridviewexample.dialogs.ProblemAlertDialog;
import local.watt.gridviewexample.interfaces.OnMovieItemSelected;
import local.watt.gridviewexample.interfaces.OnTaskCompleted;
import local.watt.gridviewexample.models.Movie;
import local.watt.gridviewexample.models.MovieList;
import local.watt.gridviewexample.parsers.ParseMovieData;
import local.watt.gridviewexample.tasks.SearchMovies;
import local.watt.gridviewexample.utils.DBHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnTaskCompleted<List<Movie>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    private static final String STATE_SORT = TAG + ".SORT";
    private static final String STATE_POSITION = TAG + ".POSITION";
    private static final String STATE_DATA = TAG + ".DATA";

    private String mSortOrder;

    private String mApiKey;
    private String mVoteCount;
    private Context mContext;
    private View mView;
    private ArrayList<Movie> mMovieList;
    private MovieGridAdapter mMovieGridAdapter;

    private int mCurrentPage = 1;
    private boolean mPreferencesHaveChanged;
    private DBHelper mDBHelper;

    static int index;

    /*
    Inject views using ButterKnife
    */
    @InjectView(R.id.gridView) GridView mGridView;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;
    @InjectView(R.id.imageLoadingError) View mLoadingErrorView;

    public MainActivityFragment() {
        Log.d(TAG, "########## Initializing MainActivityFragment()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        setHasOptionsMenu(true);
        setRetainInstance(true);
        ButterKnife.inject(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (isNetworkAvailable()) {
            Log.d(TAG, "onCreateView()");

            mView = inflater.inflate(R.layout.fragment_main, container, false);

            mGridView = (GridView) mView.findViewById(R.id.gridView);
            mProgressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
            mLoadingErrorView = mView.findViewById(R.id.imageLoadingError);

            mGridView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            mLoadingErrorView.setVisibility(View.GONE);

            // Handle savedInstanceState and loading of favorites here
            if (savedInstanceState == null || !savedInstanceState.containsKey(STATE_DATA)) {
                Log.d(TAG, "savedInstanceState is null and does not contain key: " + STATE_DATA);
                mMovieList = new ArrayList<>();

                // Load favorites from the get-go if the preferences have been saved as such
                if (isfavorite()) {
                    Log.d(TAG, "Loading favorites...");

                    mDBHelper = new DBHelper(mContext);
                    List<Movie> movieList = mDBHelper.getAllMovies();
                    Log.d(TAG, "######### movieList array size: " + movieList.size());

                    mMovieList = new ArrayList<Movie>(movieList);

                    mMovieGridAdapter = new MovieGridAdapter(getActivity(), R.layout.movies, mMovieList);
                    mGridView.setAdapter(mMovieGridAdapter);
                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Movie movie = mMovieGridAdapter.getItem(position);
                            ((OnMovieItemSelected) getActivity()).onItemSelected(movie);
                        }
                    });
                    onTaskCompleted(movieList);

                    mProgressBar.setVisibility(View.GONE);
                    mGridView.setVisibility(View.VISIBLE);
                } else {
                    fetchMovies();
                    mProgressBar.setVisibility(View.GONE);
                    mGridView.setVisibility(View.VISIBLE);
                }
            } else {
                Log.d(TAG, "savedInstanceState is not null and contains key: " + STATE_DATA);
                mMovieList = savedInstanceState.getParcelableArrayList(STATE_DATA);

                mProgressBar.setVisibility(View.GONE);
                mGridView.setVisibility(View.VISIBLE);
            }

            mMovieGridAdapter = new MovieGridAdapter(getActivity(), R.layout.movies, mMovieList);
            mGridView.setAdapter(mMovieGridAdapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Movie movie = mMovieGridAdapter.getItem(position);
                    ((OnMovieItemSelected) getActivity()).onItemSelected(movie);
                }
            });
            mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    String sortOrder = getSortOrder();

                    if (sortOrder.equals("favorites")) {
                        return;
                    }

                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                        mCurrentPage++;
                        fetchMovies();
                    }
                }
            });

            PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
        } else {
            alertUserAboutNetworkError();
        }
        return mView;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        mGridView.setSelection(index);
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        index = mGridView.getFirstVisiblePosition();
        super.onPause();
    }

    /*
    Methods implemented by the OnTaskCompleted interface
    This method is executed AFTER the AsyncTask has done its work, unless it's being
    called by fetchMovies() to load movies from the database
    */
    @Override
    public void onTaskCompleted(List<Movie> result) {
        Log.d(TAG, "onTaskCompleted()");

        // Add items to the adapter
        for (Movie results : result) {
            mMovieGridAdapter.add(results);
        }
    }

    @Override
    public void onError() {
        mGridView.setVisibility(View.GONE);
        mLoadingErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState()");

        outState.putString(STATE_SORT, getSortOrder());
        outState.putInt(STATE_POSITION, mCurrentPage);
        outState.putParcelableArrayList(STATE_DATA, mMovieList);
    }

    private String getSortOrder() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mSortOrder = sharedPreferences.getString("sortOrder", "");

        return mSortOrder;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "########## Preferences have changed.");
        mPreferencesHaveChanged = true;
        fetchMovies();
    }

    /*
    Check if network is available
    */
    public boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService((Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    /*
    Check if we're trying to load up favorites
    */
    public boolean isfavorite() {
        String result = getSortOrder();

        if (result.equals("favorites")) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
    Error dialogs
    */
    private void alertUserAboutNetworkError() {
        NetworkConnectivityDialog dialog = new NetworkConnectivityDialog();
        dialog.show(getActivity().getFragmentManager(), "network_error_dialog");
    }

    private void alertUserAboutError() {
        ProblemAlertDialog dialog = new ProblemAlertDialog();
        dialog.show(getActivity().getFragmentManager(), "error_dialog");
    }

    /*
    Method to kick off the SearchMovies AsyncTask or to build JSONArray from database
    */
    private void fetchMovies() {
        Log.d(TAG, "fetchMovies()");

        if (mPreferencesHaveChanged) {
            Log.d(TAG, "Preferences have changed. Clearing adapter and reloading movies.");
            mMovieGridAdapter.clear();
            mCurrentPage = 1;
            mPreferencesHaveChanged = false;
        }

        if (getSortOrder() != null) {

            String sortOrder = getSortOrder();
            Log.d(TAG, "sortOrder == " + sortOrder);

            /*
            mApiKey = getString(R.string.api_key);
            mVoteCount = 1000 + "";

            new SearchMovies(this, getSortOrder(), mCurrentPage).execute(mMovieGridAdapter);
            */

            if (isfavorite()) {
                Log.d(TAG, "Preferences set to \"favorites\". Loading favorites from database...");

                mMovieGridAdapter.clear();
                mDBHelper = new DBHelper(mContext);
                List<Movie> movieList = mDBHelper.getAllMovies();


                //Create a JSONArray from movieList and feed it to onTaskCompleted for parsing.
                onTaskCompleted(movieList);
            }
            else {
                Log.d(TAG, "Preferences set to " + sortOrder + ". Fetching movies via AsyncTask...");
                mApiKey = getString(R.string.api_key);
                mVoteCount = 1000 + "";

                new SearchMovies(this, getSortOrder(), mCurrentPage).execute(mMovieGridAdapter);
            }
        }
        else {
            Log.d(TAG, "getSortOrder() returns null!");
        }
    }
}