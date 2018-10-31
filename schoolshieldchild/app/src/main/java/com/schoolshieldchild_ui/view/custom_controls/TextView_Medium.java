package com.schoolshieldchild_ui.view.custom_controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 19/7/16.
 */
public class TextView_Medium extends TextView {
    public TextView_Medium(Context context, AttributeSet attrs) {
        super(context, attrs);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium_5.ttf");
        setTypeface(tf);


    }
}
