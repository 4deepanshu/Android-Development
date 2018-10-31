package com.schoolshieldchild_ui.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.app.MyApplication;

/**
 * Created by mb on 27/8/16.
 */
public class PermisionsUtil {

  /*  public void checkAdminAndOverlayPermissions() {

        boolean isOverLayserviceGranted = false;
        boolean isUsageAccessEnabled = false;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(TutorialActivity.this)) {
                isOverLayserviceGranted = false;
                if (toast != null) {
                    toast.cancel();
                }
                toastToShowPermitOverlay(getString(R.string.enableoverotherapps));
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 5469);
            } else {
                isOverLayserviceGranted = true;
            }
        } else {
            isOverLayserviceGranted = true;
        }

        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            if (isOverLayserviceGranted == true) {
                if (!MyApplication.permissions.isUsageAccessActive(this)) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toastToShowPermitOverlay(getString(R.string.usageaccessenable));
                    isUsageAccessEnabled = false;
                } else {
                    isUsageAccessEnabled = true;
                }
            }
        } else {
            isUsageAccessEnabled = true;
        }

        if (!MyApplication.permissions.isAdminActive(this) && isUsageAccessEnabled == true && isOverLayserviceGranted == true) {
            if (toast != null) {
                toast.cancel();
            }
            toastToShowPermitOverlay(getString(R.string.activateadmin));
            MyApplication.permissions.activateAdminPermissions(this, REQUEST_CODE_LOCK_SCREEN_AND_ADMIN);
        }
    }


    private boolean checkIsUsageAccessEanbled()
    {

    }*/

}
