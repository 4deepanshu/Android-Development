package com.schoolshieldchild_ui.view.services.permissions;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class MyAdmin extends DeviceAdminReceiver {
	static final String TAG = "DemoDeviceAdminReceiver";

	@Override
	public CharSequence onDisableRequested(Context context, Intent intent) {

		return "Are you sure ?\n\n Do you want to deactivate this application";
	}

}