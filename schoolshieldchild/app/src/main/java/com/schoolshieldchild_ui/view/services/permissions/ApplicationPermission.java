package com.schoolshieldchild_ui.view.services.permissions;

import java.util.List;


import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

public class ApplicationPermission {


    private DevicePolicyManager mgr = null;
    private ComponentName cn = null;

    public void activateAdminPermissions(Activity activity, int resultCode) {
        cn = new ComponentName(activity, MyAdmin.class);
        mgr = (DevicePolicyManager) activity.getApplicationContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Activate application administrator");
        activity.startActivityForResult(intent, resultCode);
    }

    public boolean isAdminActive(Context context) {
        cn = new ComponentName(context, MyAdmin.class);
        mgr = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (mgr.isAdminActive(cn)) {
            return true;
        } else {
            return false;
        }
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean isUsageAccessActive(Context context) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            try {

                PackageManager packageManager = context.getPackageManager();
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid,
                        applicationInfo.packageName);
                return (mode == AppOpsManager.MODE_ALLOWED);

            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        } else {
            return true;
        }
    }

    public void enableUsageAccess(Activity activity, int resultCode) {

        if (!isUsageAccessActive(activity)) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(intent, resultCode);
        } else {

        }
    }

    public void enableDrawOverOtherApplications(Activity activity, int resultCode) {

        if (!isUsageAccessActive(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivityForResult(intent, resultCode);
        } else {

        }
    }

}