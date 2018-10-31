package com.schoolshieldchild_ui.view.services.phonerestart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.presenter.WebServiceResult;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
            MyApplication.getInstance().startBackgroundServices();
            WebServiceResult.addNotifications(SharedPref.getString(MyApplication.STUDENT_ID),MyApplication.TYPE_PHONE_RESTART,"","","");

        }
    }
}