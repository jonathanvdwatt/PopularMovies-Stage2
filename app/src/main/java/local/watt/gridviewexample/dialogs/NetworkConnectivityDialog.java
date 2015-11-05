package local.watt.gridviewexample.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import local.watt.gridviewexample.R;

/**
 * Created by f4720431 on 2015/09/15.
 */
public class NetworkConnectivityDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstabnceState) {
        Context context = getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.network_connectivity_error_title)
                .setMessage(R.string.network_connectivity_error_message)
                .setPositiveButton(R.string.error_positive_button_text, null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
