package com.schoolshieldparent_ui.controller.helper.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.schoolshieldparent_ui.app.MyApplication;


/**
 * Created by root gpson 26/8/15.
 */
public class SharedPref {

    private static String pref_name = "PARENT";
    private static String pref_notification = "NOTIFICATION";
    public static String KEY_BUY_PRODUCT = "INAPP_PREMIUM" + SharedPref.class.getName();
    private static SharedPreferences pref;
    public static String KEY_NOTIFICATION = "notification" + SharedPref.class.getName();
    public static String TAG = SharedPref.class.getName();

    public static void setString(String var_name, String var_value) {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit();
        edit.putString(var_name, var_value);
        edit.apply();

    }

    public static String getNotiString(String var_name) {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_notification,
                Context.MODE_PRIVATE);
        return pref.getString(var_name, "");
    }

    public static void setNotiString(String var_name, String var_value) {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_notification,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit();
        edit.putString(var_name, var_value);
        edit.apply();

    }

    public static boolean clearNotiPref(Context act) {
        try {
            SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_notification,
                    Context.MODE_PRIVATE);
            pref.edit().clear().commit();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static String getString(String var_name) {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        return pref.getString(var_name, "");
    }

    public static void setNotificationStatus(Context context, boolean ON_OFF) {
        SharedPreferences preferences = context.getSharedPreferences("pref" + TAG, 0);
        Editor edit = preferences.edit();
        edit.putBoolean(KEY_NOTIFICATION, ON_OFF);
        edit.commit();
    }

    public static boolean getNotifiationStatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("pref" + TAG, 0);
        return preferences.getBoolean(KEY_NOTIFICATION, true);
    }


    public static boolean clearPref(Context act) {
        try {
            SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
                    Context.MODE_PRIVATE);
            pref.edit().clear().commit();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean getPremimumVersionFree(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("pref" + TAG, 0);
        return preferences.getBoolean(KEY_BUY_PRODUCT, false);
    }

    public static void setPremimumVersionFree(Context context, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences("pref" + TAG, 0);
        Editor edit = preferences.edit();
        edit.putBoolean(KEY_BUY_PRODUCT, value);
        edit.commit();
    }

}
