package com.schoolshieldparent_ui.controller.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.schoolshieldparent_ui.R;


public class DialogManager {

    static ProgressDialog pDialog;
    private static View view;


    public static void startProgressDialog(Context act) {
        LayoutInflater inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pDialog = new ProgressDialog(act);
        pDialog.getWindow().setBackgroundDrawableResource(R.color.transparent );
        view = inflater.inflate( R.layout.custom_progress_dialog, null);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.setContentView(view);
    }

    public static void stopProgressDialog() {
        try {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        } catch (NullPointerException e) {

        } catch (Exception e){

        }
    }
    public static void startToast(Context act,String msg) {

    }


}

