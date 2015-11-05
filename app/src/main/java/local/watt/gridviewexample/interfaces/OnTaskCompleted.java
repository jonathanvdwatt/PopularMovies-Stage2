package local.watt.gridviewexample.interfaces;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by f4720431 on 2015/09/16.
 */
public interface OnTaskCompleted<E> {
    // Called from task when completed
    //void onTaskCompleted(String event);
    public void onTaskCompleted(E event);

    // Called from task when an error is encountered
    public void onError();
}
