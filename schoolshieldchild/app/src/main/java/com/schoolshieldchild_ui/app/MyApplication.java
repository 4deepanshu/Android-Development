package com.schoolshieldchild_ui.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.schoolshieldchild_ui.presenter.WebServiceConnection;
import com.schoolshieldchild_ui.view.services.ApplicationRunningTime;
import com.schoolshieldchild_ui.view.services.applicationlocking.DetectLockService;
import com.schoolshieldchild_ui.view.services.UploadApplications;
import com.schoolshieldchild_ui.view.services.UploadGalleryImages;
import com.schoolshieldchild_ui.view.services.UploadHistory;
import com.schoolshieldchild_ui.view.services.locationlock.LocationUpdate;
import com.schoolshieldchild_ui.view.services.messagereciver.InitilizeIncommingAndOutgoingmessageHandler;
import com.schoolshieldchild_ui.view.services.permissions.ApplicationPermission;
import com.schoolshieldchild_ui.view.services.screenlock.DetectScreenLock;
import com.schoolshieldchild_ui.view.services.userlocation.UserLocationUpdate;
import io.fabric.sdk.android.Fabric;

public class MyApplication extends MultiDexApplication {


    private static MyApplication mInstance;


    //region Constant Variables
    public static String DEVICE_TOKEN = "TOKEN";
    public static String STUDENT_ID = "STUDENT_ID";
    public static String STUDENT_ROLE = "ROLE";
    public static String STUDENT_PASSWORD = "STUDENT_PASSWORD";

    public static String STUDENT_IS_INSIDE_SCHOOL = "INSIDE_OUTSIDE"; // YES NO
    public static String ISLOCATION_LOCKED = "ISLOCATION_LOCKED"; // YES NO

    public static final String SCREEN_PREMANENT_LOCK = "SCREEN_PREMANENT_LOCK"; //YES NO
    public static final String SCREEN_PREMANENT_LOCK_ID = "SCREEN_PREMANENT_LOCK_ID";
    public static final String SCREEN_PREMANENT_LOCK_FROM = "SCREEN_PREMANENT_LOCK_FROM";
    public static final String SCREEN_PREMANENT_LOCK_FROMID = "SCREEN_PREMANENT_LOCK_FROMID";


    public static String STUDENT_LAT = "LATITUDE";
    public static String STUDENT_LON = "LONGITUDE";
    public static ApplicationPermission permissions = new ApplicationPermission();


    public static String TYPE_TIME = "TIME";
    public static String TYPE_GPS = "GPS";
    public static String TYPE_INSTALL = "INSTALL";
    public static String TYPE_UNINSTALL = "UNINSTALL";
    public static String TYPE_LOCK = "LOCK";
    public static String TYPE_UNLOCK = "UNLOCK";
    public static String TYPE_MESSAGE_INCOMING = "MESSAGE_INCOMING";
    public static String TYPE_MESSAGE_OUTGOING = "MESSAGE_OUTGOING";
    public static String TYPE_PHONE_RESTART = "PHONE_RESTART";




    public static final String PHONE_FATHER = "PHONE_FATHER";
    public static final String PHONE_MOTHER = "PHONE_MOTHER";
    public static final String MESSAGE_MOTHER = "MESSAGE_MOTHER";
    public static final String MESSAGE_FATHER = "MESSAGE_FATHER";
    public static final String MESSAGE_DEFAULT = "MESSAGE_DEFAULT";


    //endregion

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        WebServiceConnection webServiceConnection = new WebServiceConnection();
        mInstance = this;
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public void startBackgroundServices() {

        if (!isMyServiceRunning(UploadApplications.class)) {
            startService(new Intent(getApplicationContext(), UploadApplications.class));
        }
        if (!isMyServiceRunning(UploadGalleryImages.class)) {
            startService(new Intent(getApplicationContext(), UploadGalleryImages.class));
        }
        if (!isMyServiceRunning(UploadHistory.class)) {
            startService(new Intent(getApplicationContext(), UploadHistory.class));
        }
        if (!isMyServiceRunning(DetectLockService.class)) {
            startService(new Intent(getApplicationContext(), DetectLockService.class));
        }
        if (!isMyServiceRunning(LocationUpdate.class)) {
            startService(new Intent(getApplicationContext(), LocationUpdate.class));
        }
        if (!isMyServiceRunning(ApplicationRunningTime.class)) {
            startService(new Intent(getApplicationContext(), ApplicationRunningTime.class));
        }
        if (!isMyServiceRunning(UserLocationUpdate.class)) {
            startService(new Intent(getApplicationContext(), UserLocationUpdate.class));
        }
        if (!isMyServiceRunning(DetectScreenLock.class)) {
            startService(new Intent(getApplicationContext(), DetectScreenLock.class));
        }
        if (!isMyServiceRunning(InitilizeIncommingAndOutgoingmessageHandler.class)) {
            startService(new Intent(getApplicationContext(), InitilizeIncommingAndOutgoingmessageHandler.class));
        }

    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}