package com.schoolshieldparent_ui.view.custom_controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root gpson 19/7/16.
 */
public class TextView_Condessed extends TextView {
    public TextView_Condessed(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Condensed_5.ttf");
        setTypeface(tf);
    }
}