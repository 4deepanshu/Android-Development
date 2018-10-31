package com.schoolshieldchild_ui.view.services.locationlock;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.schoolshieldchild_ui.R;

public class LocationLockScreenView extends Service  {
    static View mView;
    static Context context;
    static Service service;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inf.inflate(R.layout.view_lock_screen_location, null);
        context = getApplicationContext();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.alpha = 1.0f;
        layoutParams.packageName = context.getPackageName();
        layoutParams.buttonBrightness = 1f;
        layoutParams.windowAnimations = android.R.style.Animation_Dialog;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        service = this;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mView, layoutParams);
     }




    public static void stopService() {
        try {
            service.stopSelf();
            LocationUpdate.isLockViewVisible = false;
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).removeView(mView);
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
    }
}
