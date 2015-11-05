package local.watt.gridviewexample.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import local.watt.gridviewexample.R;
import local.watt.gridviewexample.interfaces.OnMovieItemSelected;
import local.watt.gridviewexample.models.Movie;
import local.watt.gridviewexample.ui.MovieDetails;
import local.watt.gridviewexample.utils.URLHelper;

/**
 * Created by f4720431 on 2015/09/15.
 */
public class MovieGridAdapter extends ArrayAdapter<Movie> {

    public static final String TAG = MovieGridAdapter.class.getSimpleName();

    Context mContext;
    private int layoutResourceId;
    private List<Movie> mMovies = new ArrayList<>();

    String mImageSize;

    public MovieGridAdapter(Context context, int layoutResourceId, List<Movie> movies) {
        super(context, layoutResourceId, movies);
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
        this.mMovies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Movie movie = mMovies.get(position);

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        Picasso.with(mContext)
                .load(URLHelper.buildImageURL(movie.getPoster(), mImageSize = "w185"))
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_loading_error)
                .fit()
                .into(imageView);

        return convertView;
    }

    public List<Movie> getData() {
        return mMovies;
    }
}