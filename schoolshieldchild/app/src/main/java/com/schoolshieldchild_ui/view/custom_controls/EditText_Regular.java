package com.schoolshieldchild_ui.view.custom_controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by root on 19/7/16.
 */
public class EditText_Regular extends EditText {
    public EditText_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular_5.ttf");
        setTypeface(tf);


    }
}
