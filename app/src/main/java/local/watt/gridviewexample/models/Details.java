package local.watt.gridviewexample.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by f4720431 on 2015/10/09.
 */
public class Details {
    private long mMovieId;
    private long mDuration;
    private List<Trailer> mTrailers = new ArrayList<Trailer>();
    private List<Review> mReviews = new ArrayList<Review>();

    private String mReviewId;
    private String mReviewAuthor;
    private String mReviewContent;
    private String mReviewURL;

    private String mTrailerKey;
    private String mTrailerName;
    private String mTrailerSite;
    private int mTrailerSize;
    private String mTrailerType;

    public long getMovieId() {
        return mMovieId;
    }

    public void setMovieId(long movieId) {
        mMovieId = movieId;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public List<Trailer> getTrailers() {
        return mTrailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        mTrailers = trailers;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    public String getReviewId() {
        return mReviewId;
    }

    public void setReviewId(String reviewId) {
        mReviewId = reviewId;
    }

    public String getReviewAuthor() {
        return mReviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        mReviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return mReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        mReviewContent = reviewContent;
    }

    public String getReviewURL() {
        return mReviewURL;
    }

    public void setReviewURL(String reviewURL) {
        mReviewURL = reviewURL;
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
