package com.schoolshieldchild_ui.view.custom_controls;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.schoolshieldchild_ui.R;

/**
 * Created by root on 19/7/16.
 */
public class Progress_Dialog extends ProgressDialog {


    public Progress_Dialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_progress_dialog);
    }
}
