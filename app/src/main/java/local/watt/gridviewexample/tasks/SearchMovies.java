package local.watt.gridviewexample.tasks;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import local.watt.gridviewexample.R;
import local.watt.gridviewexample.interfaces.OnTaskCompleted;
import local.watt.gridviewexample.models.Movie;
import local.watt.gridviewexample.parsers.ParseMovieData;
import local.watt.gridviewexample.ui.MainActivity;
import local.watt.gridviewexample.ui.MainActivityFragment;
import local.watt.gridviewexample.utils.HTTPUtil;
import local.watt.gridviewexample.utils.URLHelper;

/**
 * Created by f4720431 on 2015/09/16.
 */
public class SearchMovies extends AsyncTask<ArrayAdapter<Movie>, Void, List<Movie>> {

    public static final String TAG = SearchMovies.class.getSimpleName();

    private ArrayAdapter mArrayAdapter;
    private String mSearchOrder;
    private int mPage;
    private OnTaskCompleted<List<Movie>> delegate;

    public SearchMovies(OnTaskCompleted<List<Movie>> delegate, String searchOrder, int page) {
        this.delegate = delegate;
        this.mSearchOrder = searchOrder;
        this.mPage = page;
    }

    /*
    doInbackground is executed first via the .execute() method in the main activity.
    */
    @Override
    protected List<Movie> doInBackground(ArrayAdapter... params) {

        if (params.length == 0) {
            return null;
        }

        mArrayAdapter = params[0];

        try {
            String json = HTTPUtil.GET(URLHelper.buildMovieSearchURL(mSearchOrder, mPage));
            List<Movie> movies = ParseMovieData.getMovieListFromJSON(json);
            return movies;
        }
        catch (Exception e) {
            Log.e(TAG, "Failed to find movies");
        }
        return null;
    }

    /*
    onPostExecute is called by doInBackground(). This method delegates the
    task back to the main activity via the OnTaskCompleted interface
    */
    @Override
    protected void onPostExecute(List<Movie> result) {
        super.onPostExecute(result);

        if (result == null) {
            this.delegate.onError();
            return;
        }

        /*
         Let the activity know that we are done
        */
        this.delegate.onTaskCompleted(result);
    }
}
