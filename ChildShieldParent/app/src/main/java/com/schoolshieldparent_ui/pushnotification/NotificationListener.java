package com.schoolshieldparent_ui.pushnotification;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;

/**
 * Created by root on 1/9/16.
 */
public class NotificationListener extends NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("notification--------", "onCreate");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d("notification--------", "notification------posted--");
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d("notification--------", sbn.getPackageName());
        if (sbn.getPackageName().equalsIgnoreCase("com.schoolshieldparent_ui")) {
            SharedPref.clearNotiPref(this);
        }

    }
}
