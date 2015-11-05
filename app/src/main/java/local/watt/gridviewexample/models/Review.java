package local.watt.gridviewexample.models;

/**
 * Created by f4720431 on 2015/10/09.
 */
public class Review {

    private String mReviewId;
    private String mReviewAuthor;
    private String mReviewContent;
    private String mReviewURL;

    public Review() {}

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
}
