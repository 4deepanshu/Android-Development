package com.rg.milkwala.view.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Mobile on 1/9/2017.
 */

public class EditTextRegular extends EditText {
    public EditTextRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Roboto-Regular_5.ttf");
        this.setTypeface(face);

    }
}
