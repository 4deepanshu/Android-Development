package com.schoolshieldchild_ui.view.services.detectors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.presenter.WebServiceResult;

/**
 * Created by mb on 28/7/16.
 */
public class GpsOnOffDetector extends BroadcastReceiver {
    static String GPSSTATUS = "";

    @Override
    public void onReceive(Context context, Intent intent) {


        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            if (!GPSSTATUS.equalsIgnoreCase("OFF")) {
                GPSSTATUS = "OFF";
                WebServiceResult.addNotifications(SharedPref.getString(MyApplication.STUDENT_ID), MyApplication.TYPE_GPS, "", GPSSTATUS, "");
            }
        } else {
            if (!GPSSTATUS.equalsIgnoreCase("ON")) {
                GPSSTATUS = "ON";
                WebServiceResult.addNotifications(SharedPref.getString(MyApplication.STUDENT_ID), MyApplication.TYPE_GPS, "", GPSSTATUS, "");
            }
        }


    }
}
