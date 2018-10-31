package com.schoolshieldchild_ui.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.view.custom_controls.TextView_Regular;
import com.schoolshieldchild_ui.view.services.messagereciver.InitilizeIncommingAndOutgoingmessageHandler;
import com.schoolshieldchild_ui.view.services.screenlock.DetectScreenLock;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.textView_AgreeAndContinue)
    TextView_Regular textView_AgreeAndContinue;


    String[] permissions = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_SMS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private int REQUEST_ID_MULTIPLE_PERMISSIONS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        validateLogin();

    }


    public void validateLogin() {

        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (checkPermissions()) {
                if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                    checkIsPermissionEnabled();
                }
            }

        }
        else {
            if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                checkIsPermissionEnabled();
            }
        }
    }


    @OnClick(R.id.textView_AgreeAndContinue)
    public void AgreeAndContinueClick() {
        checkIsPermissionEnabled();
    }

    private void checkIsPermissionEnabled() {
        int currentapiVersion = Build.VERSION.SDK_INT;

        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (MyApplication.permissions.isUsageAccessActive(this) == true && Settings.canDrawOverlays(SplashActivity.this) == true && MyApplication.permissions.isAdminActive(this) == true) {
                if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, TutorialActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                if (MyApplication.permissions.isUsageAccessActive(this) == false) {
                    startActivity(new Intent(this, Activity_UsageAccessEnable.class));
                    overridePendingTransition(0, 0);
                } else if (Settings.canDrawOverlays(SplashActivity.this) == false) {
                    startActivity(new Intent(this, Activity_DrawOverOtherApplicatin.class));
                    overridePendingTransition(0, 0);
                } else if (MyApplication.permissions.isAdminActive(this) == false) {
                    startActivity(new Intent(this, Activity_AdminEnable.class));
                    overridePendingTransition(0, 0);
                }

            }

        } else if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP  && currentapiVersion < Build.VERSION_CODES.M) {
            if (MyApplication.permissions.isUsageAccessActive(this) == true && MyApplication.permissions.isAdminActive(this) == true) {
                if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, TutorialActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                if (MyApplication.permissions.isUsageAccessActive(this) == false) {
                    startActivity(new Intent(this, Activity_UsageAccessEnable.class));
                    overridePendingTransition(0, 0);
                } else if (MyApplication.permissions.isAdminActive(this) == false) {
                    startActivity(new Intent(this, Activity_AdminEnable.class));
                    overridePendingTransition(0, 0);
                }
            }

        } else if (currentapiVersion < Build.VERSION_CODES.LOLLIPOP) {
            if (MyApplication.permissions.isAdminActive(this) == false) {
                startActivity(new Intent(this, Activity_AdminEnable.class));
                overridePendingTransition(0, 0);
            } else {
                if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, TutorialActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }

    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checkPermissions();
    }

}
