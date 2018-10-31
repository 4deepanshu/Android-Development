package com.schoolshieldchild_ui.controller.helper.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.schoolshieldchild_ui.app.MyApplication;


/**
 * Created by root on 26/8/15.
 */
public class SharedPref {

    private static String pref_name = "CHILD";
    private static SharedPreferences pref;


    public static void setString(String var_name, String var_value) {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit();
        edit.putString(var_name, var_value);
        edit.apply();

    }

    public static String getString(String var_name) {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        return pref.getString(var_name, "");
    }







    public static void ClearData() {
        pref = MyApplication.getInstance().getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        Editor edit = pref.edit();
        edit.clear();
        edit.commit();
    }
}
