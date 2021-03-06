package local.watt.gridviewexample.tasks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import local.watt.gridviewexample.interfaces.OnTaskCompleted;
import local.watt.gridviewexample.models.Review;
import local.watt.gridviewexample.models.Trailer;
import local.watt.gridviewexample.parsers.ParseMovieData;
import local.watt.gridviewexample.utils.HTTPUtil;
import local.watt.gridviewexample.utils.URLHelper;

/**
 * Created by f4720431 on 2015/09/26.
 */
public class SearchDetails extends AsyncTask<String, Void, String> {
    private static final String TAG = SearchDetails.class.getSimpleName();

    private OnTaskCompleted<String> delegate;
    private long mMovieID;
    private List<Review> mReviewList = new ArrayList<>();
    private List<Trailer> mTrailerList = new ArrayList<>();

    public SearchDetails(OnTaskCompleted<String> delegate) {
        this.delegate = delegate;
    }


    /*
    We're executing this AsyncTask from the MovieDetails activity.
     */
    @Override
    protected String doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }

        mMovieID = Integer.valueOf(params[0]);

        /*
        The JSON needs to be retrieved and parsed
        */
        String reviewsJSONString = null;
        String trailersJSONString = null;
        String moviesDetailsJSONString = null;

        try {
            // Get reviews
            reviewsJSONString = HTTPUtil.GET(URLHelper.buildMovieReviewsURL(mMovieID));

            // Get trailers
            trailersJSONString = HTTPUtil.GET(URLHelper.buildTrailersURL(mMovieID));

            ParseMovieData parseMovieData = new ParseMovieData();

            mReviewList = parseMovieData.getMovieReviewFromJson(reviewsJSONString);
            mTrailerList = parseMovieData.getMovieTrailerFromJson(trailersJSONString);

            JSONArray reviewsJSONArray = new JSONArray();

            for (int i=0 ; i<mReviewList.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("review_id", mReviewList.get(i).getReviewId());
                obj.put("author", mReviewList.get(i).getReviewAuthor());
                obj.put("content", mReviewList.get(i).getReviewContent());
                obj.put("url", mReviewList.get(i).getReviewURL());

                reviewsJSONArray.put(obj);
            }

            JSONArray trailersJSONArray = new JSONArray();

            for (int i=0; i<mTrailerList.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("trailer_id", mTrailerList.get(i).getMovieId());
                obj.put("key", mTrailerList.get(i).getTrailerKey());
                obj.put("name", mTrailerList.get(i).getTrailerName());
                obj.put("site", mTrailerList.get(i).getTrailerSite());
                obj.put("size", mTrailerList.get(i).getTrailerSize());
                obj.put("type", mTrailerList.get(i).getTrailerType());

                trailersJSONArray.put(obj);
            }

            JSONObject movieDetailsJSONObject = new JSONObject();

            movieDetailsJSONObject.put("reviews", reviewsJSONArray);
            movieDetailsJSONObject.put("trailers", trailersJSONArray);

            moviesDetailsJSONString = movieDetailsJSONObject.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return moviesDetailsJSONString;
    }

    /*
    onPostExecute called by doInbackground().
    Here we delegate the task back to calling activity (MovieDetails)
    */
    @Override
    protected void onPostExecute(String result) {

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
