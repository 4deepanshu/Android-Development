package com.schoolshieldchild_ui.controller.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.schoolshieldchild_ui.R;

public class DialogManager {

    static ProgressDialog pDialog;
    private static View view;


    public static void startProgressDialog(Context act) {
        LayoutInflater inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pDialog = new ProgressDialog(act);
        view = inflater.inflate(R.layout.custom_progress_dialog, null);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.setContentView(view);
    }

    public static void stopProgressDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }


}

