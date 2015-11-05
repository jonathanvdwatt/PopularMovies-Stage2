package local.watt.gridviewexample.models;

import android.util.Log;
import android.widget.TableRow;

/**
 * Created by f4720431 on 2015/10/09.
 */
public class Trailer {

    private String mMovieId;
    private String mTrailerKey;
    private String mTrailerName;
    private String mTrailerSite;
    private int mTrailerSize;
    private String mTrailerType;

    public Trailer() {}

    public Trailer(String name) {
        this.mTrailerName = name;
    }

    public String getMovieId() {
        return mMovieId;
    }

    public void setMovieId(String movieId) {
        mMovieId = movieId;
    }

    public String getTrailerKey() {
        return mTrailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        mTrailerKey = trailerKey;
    }

    public String getTrailerName() {
        return mTrailerName;
    }

    public void setTrailerName(String trailerName) {
        mTrailerName = trailerName;
    }

    public String getTrailerSite() {
        return mTrailerSite;
    }

    public void setTrailerSite(String trailerSite) {
        mTrailerSite = trailerSite;
    }

    public int getTrailerSize() {
        return mTrailerSize;
    }

    public void setTrailerSize(int trailerSize) {
        mTrailerSize = trailerSize;
    }

    public String getTrailerType() {
        return mTrailerType;
    }

    public void setTrailerType(String trailerType) {
        mTrailerType = trailerType;
    }
}
