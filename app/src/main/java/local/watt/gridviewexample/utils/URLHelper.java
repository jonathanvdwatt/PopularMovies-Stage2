package local.watt.gridviewexample.utils;

import android.net.Uri;
import android.util.Log;

/**
 * Created by f4720431 on 2015/09/15.
 */
public class URLHelper {
    public static final String TAG = URLHelper.class.getSimpleName();

    public static String buildMovieSearchURL(String searchOrder, int page) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", "9d0d99343c233776817535ad193d2d86")
                .appendQueryParameter("vote_count.gte", "10")
                .appendQueryParameter("page", page + "")
                .appendQueryParameter("sort_by", searchOrder);

        String URL = builder.build().toString();
        return URL;
    }

    public static String buildImageURL(String image, String imageSize) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath(imageSize)
                .appendPath(image.startsWith("/") ? image.substring(1): image);

        String URL = builder.build().toString();

        return URL;
    }

    public static String buildTrailersURL(long movieID) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("" + movieID)
                .appendPath("videos")
                .appendQueryParameter("api_key", "9d0d99343c233776817535ad193d2d86");

        // print out full URL
        String URL = builder.build().toString();
        Log.d(TAG, "URL[" + URL + ")");

        return URL;
    }

    // https://api.themoviedb.org/3/movie/87101?api_key=9d0d99343c233776817535ad193d2d86
    public static String buildMoviewDetailsURL(long movieID) {
        // build up URL
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("" + movieID)
                .appendQueryParameter("api_key", "9d0d99343c233776817535ad193d2d86");

        // print out full URL
        String URL = builder.build().toString();
        //Log.d(TAG, "URL[" + URL + ")");

        return( URL );
    }

    // https://api.themoviedb.org/3/movie/76341/reviews?api_key=9d0d99343c233776817535ad193d2d86
    public static String buildMovieReviewsURL(long movieID) {
        // build up URL
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("" + movieID)
                .appendPath("reviews")
                .appendQueryParameter("api_key", "9d0d99343c233776817535ad193d2d86");

        // print out full URL
        String URL = builder.build().toString();
        Log.d(TAG, "URL[" + URL + ")");

        return( URL );
    }
}
