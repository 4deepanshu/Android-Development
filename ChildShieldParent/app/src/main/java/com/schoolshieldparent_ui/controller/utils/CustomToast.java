package com.schoolshieldparent_ui.controller.utils;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by root on 5/9/16.
 */
public class CustomToast {
    public static Toast toast;

    public static void showToast(Activity activity, String message) {
        toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void stopToast() {
        toast.cancel();

    }
}
