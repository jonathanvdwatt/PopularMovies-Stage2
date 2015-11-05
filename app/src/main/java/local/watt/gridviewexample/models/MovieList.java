package local.watt.gridviewexample.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by f4720431 on 2015/11/02.
 */
public class MovieList {
    private List<Movie> results;

    public MovieList() {
    }

    public MovieList(List<Movie> results) {
        this.results = results;
    }

    public List<Movie>  getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        results = results;
    }
}
