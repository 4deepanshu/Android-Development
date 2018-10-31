package com.schoolshieldchild_ui.view.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.presenter.WebServiceResult;

public class ScreenReceiver extends BroadcastReceiver {

    public static boolean screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("onReceive ");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = true;
            if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                MyApplication.getInstance().startBackgroundServices();
                WebServiceResult.addNotifications(SharedPref.getString(MyApplication.STUDENT_ID), MyApplication.TYPE_PHONE_RESTART, "", "", "");

            }
            System.out.println("SCREEN TURNED OFF on BroadcastReceiver");
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = false;
            if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                MyApplication.getInstance().startBackgroundServices();
                WebServiceResult.addNotifications(SharedPref.getString(MyApplication.STUDENT_ID),MyApplication.TYPE_PHONE_RESTART,"","","");

            }
            System.out.println("SCREEN TURNED ON on BroadcastReceiver");
        }

    }

}
