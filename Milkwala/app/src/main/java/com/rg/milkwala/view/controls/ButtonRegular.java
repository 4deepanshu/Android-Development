package com.rg.milkwala.view.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Mobile on 1/9/2017.
 */

public class ButtonRegular extends Button {
    public ButtonRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "Roboto-Medium_5.ttf");
        this.setTypeface(face);
    }
}
