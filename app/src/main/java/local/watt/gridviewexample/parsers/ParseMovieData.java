package local.watt.gridviewexample.parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import local.watt.gridviewexample.models.Details;
import local.watt.gridviewexample.models.Movie;
import local.watt.gridviewexample.models.Review;
import local.watt.gridviewexample.models.Trailer;

/**
 * Created by f4720431 on 2015/09/16.
 */
public class ParseMovieData {

    public static final String TAG = ParseMovieData.class.getSimpleName();

    public static List<Movie> getMovieListFromJSON(String movieJsonStr) throws JSONException {

        /*
        The names of the JSON objects that need to be extracted
        */
        final String TMDB_RESULTS = "results";
        final String TMDB_TITLE = "title";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_RELEASEDATE = "release_date";
        final String TMDB_POSTERPATH = "poster_path";
        final String TMDB_VOTEAVERAGE = "vote_average";
        final String TMDB_ID = "id";

        JSONObject moviesJson = new JSONObject(movieJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(TMDB_RESULTS);

        List<Movie> movies = new ArrayList<Movie>();

        for (int i=0; i < moviesArray.length(); i++) {
            JSONObject popularMovies = moviesArray.getJSONObject(i);

            Movie movie = new Movie();

            movie.setOriginalTitle(popularMovies.getString(TMDB_TITLE));
            movie.setSynopsis(popularMovies.getString(TMDB_OVERVIEW));
            movie.setReleaseDate(popularMovies.getString(TMDB_RELEASEDATE));
            movie.setPoster(popularMovies.getString(TMDB_POSTERPATH));
            movie.setRating(popularMovies.getDouble(TMDB_VOTEAVERAGE));
            movie.setMovieId(popularMovies.getInt(TMDB_ID));

            movies.add(movie);
        }
        return movies;
    }


    public static List<Trailer> getMovieTrailerFromJson(String trailerJsonStr) throws JSONException {

        List<Trailer> results = new ArrayList<Trailer>();

        JSONObject root = new JSONObject(trailerJsonStr);
        JSONArray list = root.getJSONArray("results");

        for (int i=0; i < list.length(); i++) {

            JSONObject x = list.getJSONObject(i);

            Trailer trailer = new Trailer();

            trailer.setMovieId(x.getString("id"));
            trailer.setTrailerKey(x.getString("key"));
            trailer.setTrailerName(x.getString("name"));
            trailer.setTrailerSite(x.getString("site"));
            trailer.setTrailerSize(x.getInt("size"));
            trailer.setTrailerType(x.getString("type"));

            results.add(trailer);
        }
        return results;
    }


    public static List<Review> getMovieReviewFromJson(String reviewJsonStr) throws JSONException {

        List<Review> results = new ArrayList<Review>();

        JSONObject root = new JSONObject(reviewJsonStr);
        JSONArray list = root.getJSONArray("results");

        for (int i=0; i < list.length(); i++) {

            JSONObject x = list.getJSONObject(i);

            Review review = new Review();

            review.setReviewId(x.getString("id"));
            review.setReviewAuthor(x.getString("author"));
            review.setReviewContent(x.getString("content"));
            review.setReviewURL(x.getString("url"));

            results.add(review);
        }
        return results;
    }


    public static List<Details> getMovieDetailsFromJson(String moviesDetailsJSONString) throws JSONException {

        List<Details> results = new ArrayList<Details>();

        JSONObject root = new JSONObject(moviesDetailsJSONString);
        JSONArray reviewsArray = root.getJSONArray("reviews");
        JSONArray trailersArray = root.getJSONArray("trailers");

        for (int i=0; i<reviewsArray.length(); i++) {

            JSONObject x = reviewsArray.getJSONObject(i);

            Details reviews = new Details();

            reviews.setReviewId(x.getString("review_id"));
            reviews.setReviewAuthor(x.getString("author"));
            reviews.setReviewContent(x.getString("content"));
            reviews.setReviewURL(x.getString("url"));

            results.add(reviews);
        }

        for (int i=0; i<trailersArray.length(); i++) {

            JSONObject x = trailersArray.getJSONObject(i);

            Details trailers = new Details();

            trailers.setTrailerKey(x.getString("key"));
            trailers.setTrailerName(x.getString("name"));
            trailers.setTrailerSite(x.getString("site"));
            trailers.setTrailerSize(x.getInt("size"));
            trailers.setTrailerType(x.getString("type"));

            results.add(trailers);
        }
        return results;
    }
}
