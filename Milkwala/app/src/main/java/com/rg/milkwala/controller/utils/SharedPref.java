package com.rg.milkwala.controller.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.rg.milkwala.model.user.Constants;

/**
 * Created by Mobile on 1/11/2017.
 */

public class SharedPref {
    SharedPreferences sharedpreferences;
    public static SharedPref mInstance;

    public SharedPref(Context context) {
        mInstance = this;
        sharedpreferences = context.getSharedPreferences(Constants.APP_NAME + "SDIUOIOWN", Context.MODE_PRIVATE);
    }

    public static SharedPref getInstance() {
        return mInstance;
    }

    public void setString(String name, String value) {
        sharedpreferences.edit().putString(name, value).commit();
    }

    public String getString(String value) {
        return sharedpreferences.getString(value, "");
    }

    public void clear() {
        sharedpreferences.edit().clear().commit();
    }
}
