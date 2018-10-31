package com.schoolshieldchild_ui.view.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.presenter.WebServiceResult;
import com.schoolshieldchild_ui.view.database.DataBaseHandler;

public class InstallUninstallIntentReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            {
                String PACKAGE = intent.getData().toString().substring(8);
                if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                    if (!PACKAGE.equalsIgnoreCase("com.android.packageinstaller")) {
                        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                        dataBaseHandler.deleteApplication(PACKAGE);
                        WebServiceResult.addNotifications(SharedPref.getString(MyApplication.STUDENT_ID), MyApplication.TYPE_UNINSTALL, "", "", PACKAGE);
                    }
                }
            }
        } else {
            String PACKAGE = intent.getData().toString().substring(8);
            final PackageManager pm = context.getPackageManager();
            String app_name = "";
            ApplicationInfo inf = new ApplicationInfo();
            inf.packageName = PACKAGE;
            PackageManager packageManager = context.getPackageManager();
            Drawable icon;
            Bitmap APKicon = null;
            try {
                app_name = (String) packageManager
                        .getApplicationLabel(packageManager.getApplicationInfo(PACKAGE, PackageManager.GET_META_DATA));
                icon = packageManager.getApplicationIcon(PACKAGE);

                if (icon instanceof BitmapDrawable) {
                    APKicon = ((BitmapDrawable) icon).getBitmap();
                } else {
                    Bitmap bitmap = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(),
                            Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    icon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    icon.draw(canvas);
                    APKicon = bitmap;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if (!PACKAGE.equalsIgnoreCase("com.android.packageinstaller")) {
                WebServiceResult.addNotifications(SharedPref.getString(MyApplication.STUDENT_ID), MyApplication.TYPE_INSTALL, "", "", PACKAGE);
            }
        }
    }
}