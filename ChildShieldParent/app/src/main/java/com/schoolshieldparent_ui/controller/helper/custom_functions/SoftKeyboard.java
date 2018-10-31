package com.schoolshieldparent_ui.controller.helper.custom_functions;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.schoolshieldparent_ui.view.custom_controls.EditText_Regular;

/**
 * Created by root on 17/8/16.
 */
public class SoftKeyboard {


    public static void hideSoftKeyboard(Activity activity,EditText_Regular editText) {
        View view = editText;
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService( activity.INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
        }

    }

    public static void showSoftKeyboard(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService( activity.INPUT_METHOD_SERVICE );
        imm.showSoftInput( editText, InputMethodManager.SHOW_IMPLICIT );

    }
}
