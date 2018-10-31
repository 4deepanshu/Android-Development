package com.schoolshieldparent_ui.controller.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.schoolshieldparent_ui.R;


public class CustomProgressDialogGCM extends Dialog {

	public CustomProgressDialogGCM(Context context) {
		super(context, R.style.NewDialog);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view_progressdialogui);
		setCancelable(false);

	}
}