package com.rg.milkwala.view.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Mobile on 1/9/2017.
 */

public class TextViewBold extends TextView {
    public TextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(),"Roboto-Bold_5.ttf");
        this.setTypeface(face);
    }
}
