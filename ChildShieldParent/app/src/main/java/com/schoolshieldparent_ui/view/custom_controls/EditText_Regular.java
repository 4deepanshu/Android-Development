package com.schoolshieldparent_ui.view.custom_controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Patterns;
import android.widget.EditText;

/**
 * Created by root gpson 19/7/16.
 */
public class EditText_Regular extends EditText {
    public EditText_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular_5.ttf");
        setTypeface(tf);


    }

    public static boolean isEmpty(String text) {
        if (text.replace(" ", "").length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmailValid(String text) {

        if (!Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()) {

            return false;
        }

        return true;
    }

    public static boolean isPasswordConfirm(String s1, String s2) {
        if (s1.equals(s2)) {
            return true;
        } else {
            return false;
        }
    }
}
