package com.stevensbytes.steven.househelper.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.EditText;

import com.stevensbytes.steven.househelper.R;

import static com.stevensbytes.steven.househelper.MainActivity.handler;

/**
 * Created by Steven on 4/8/2017.
 */

public class AddTaskDialogFragment extends DialogFragment {

    public static final int TASK_MSG = 8246;

    public interface AddTaskListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_add_task)
                .setTitle(R.string.dialog_add_task)
                .setPositiveButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task_desc = ((EditText) getDialog().findViewById(
                                R.id.dialog_task_description)).getText().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("desc", task_desc);
                        bundle.putLong("group", 0L);
                        Message msg = new Message();
                        msg.setData(bundle);
                        msg.what = TASK_MSG;
                        handler.handleMessage(msg);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

}
