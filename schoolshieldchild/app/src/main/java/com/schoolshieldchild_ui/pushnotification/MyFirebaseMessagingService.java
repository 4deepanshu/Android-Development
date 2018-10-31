package com.schoolshieldchild_ui.pushnotification;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.model.studentlocation.Data;
import com.schoolshieldchild_ui.presenter.WebServiceResult;
import com.schoolshieldchild_ui.view.database.DataBaseHandler;
import com.schoolshieldchild_ui.view.services.screenlock.SceenLockView;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private String data3;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String type = remoteMessage.getData().get("type");
        if (type.equalsIgnoreCase("add_applock")) {
            applyApplicationLock(remoteMessage.getData());
        } else if (type.equalsIgnoreCase("password")) {
            String password = remoteMessage.getData().get("password");
            SharedPref.setString(MyApplication.STUDENT_PASSWORD, password);
        } else if (type.equalsIgnoreCase("remove_applock")) {
            removeApplicationLock(remoteMessage.getData());
        } else if (type.equalsIgnoreCase("add_screenlock")) {
            addScreenLock(remoteMessage.getData());
        } else if (type.equalsIgnoreCase("remove_Screen_lock")) {
            removeScreenLock(remoteMessage.getData());
        }
    }

    private void removeScreenLock(Map<String, String> data) {
        try {
            String is_permanent = data.get("is_permanent");
            String lock_id = data.get("lock_id");
            String lock_by_id = data.get("lock_by_id");
            String lock_by = data.get("lock_by");
            String student_id = data.get("student_id");

            if (Integer.parseInt(lock_id) < 1) {
                SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK, "No");
                SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK_FROM, "");
                SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK_FROMID, "");
                SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK_ID, "");
                SceenLockView.stopService();
                WebServiceResult.deleteScreenLockPermanently("-1", lock_by_id, lock_by, SharedPref.getString(MyApplication.STUDENT_ID), "1");
            } else {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(getApplicationContext());
                boolean lockDeleted = dataBaseHandler.deleteScreenLock(lock_id);
                if (lockDeleted == true) {
                    WebServiceResult.deleteScreenLockPermanently(lock_id, lock_by_id, lock_by, SharedPref.getString(MyApplication.STUDENT_ID), "0");
                }
            }
        } catch (NullPointerException e) {

        } catch (Exception e) {

        }

    }

    private void addScreenLock(Map<String, String> data) {

        try {
            String lock_from_id = data.get("lock_from_id");
            String lock_from = data.get("lock_from");
            String permanent_lock = data.get("permanent_lock");
            String lock_id = data.get("lock_id");
            String start_time = data.get("start_time");
            String end_time = data.get("end_time");
            String days = data.get("days");

            if (permanent_lock.equalsIgnoreCase("1")) {
                SceenLockView.stopService();
                SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK, "Yes");
                SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK_ID, lock_id);
                SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK_FROM, lock_from);
                SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK_FROMID, lock_from_id);

            } else {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(getApplicationContext());
                dataBaseHandler.applyScreenLock(start_time, end_time, days, lock_id);
            }
            WebServiceResult.activateScreenlock(lock_id, lock_from, lock_from_id);
        } catch (NullPointerException e) {

        } catch (Exception e) {

        }

    }

    private void applyApplicationLock(Map<String, String> dataFromPush) {
        try {
            String app_package = dataFromPush.get("app_package");
            String start_time = dataFromPush.get("start_time");
            String end_time = dataFromPush.get("end_time");
            String lock_from = dataFromPush.get("lock_from");
            String title = dataFromPush.get("title");
            String days = dataFromPush.get("days");
            String lock_from_id = dataFromPush.get("lock_from_id");
            String permanent_lock = dataFromPush.get("permanent_lock");
            String lock_id = dataFromPush.get("lock_id");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(getApplicationContext());
            boolean isSaved = dataBaseHandler.applyApplicationLock(app_package, start_time, end_time, days, lock_from, permanent_lock, title, lock_id);
            if (isSaved) {
                WebServiceResult.activatelock(lock_id, lock_from, lock_from_id);
            }
        } catch (NullPointerException e) {

        } catch (Exception e) {

        }
    }


    private void removeApplicationLock(Map<String, String> data) {
        try {
            String lock_id = data.get("lock_id");
            String lock_by_id = data.get("lock_by_id");
            String lock_by = data.get("lock_by");
            String is_permanent = data.get("is_permanent");
            String student_id = data.get("student_id");
            String package_name = data.get("package_name");
            DataBaseHandler dataBaseHandler = new DataBaseHandler(getApplicationContext());
            if (is_permanent.equalsIgnoreCase("1")) {
                boolean isRemoved = dataBaseHandler.deleteAppPermanetLock(package_name);
            } else {
                boolean isRemoved = dataBaseHandler.deleteAppLock(lock_id);
            }
            WebServiceResult.deleteApplicationlock(lock_id, lock_by_id, lock_by, student_id, is_permanent, package_name);
        } catch (NullPointerException e) {

        } catch (Exception e) {

        }
    }


}