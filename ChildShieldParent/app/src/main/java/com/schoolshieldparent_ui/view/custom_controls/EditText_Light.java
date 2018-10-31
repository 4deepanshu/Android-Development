package com.schoolshieldparent_ui.view.custom_controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root gpson 19/7/16.
 */
public class EditText_Light extends EditText {
    public EditText_Light(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light_5.ttf");
        setTypeface(tf);


    }
}
