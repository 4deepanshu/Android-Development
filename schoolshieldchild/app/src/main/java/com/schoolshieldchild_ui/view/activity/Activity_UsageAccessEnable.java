package com.schoolshieldchild_ui.view.activity;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_UsageAccessEnable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activate_usage_access);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @OnClick(R.id.activate)
    public void activate() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (MyApplication.permissions.isUsageAccessActive(this) == false) {
            Toast.makeText(Activity_UsageAccessEnable.this, getString(R.string.activationfail), Toast.LENGTH_SHORT).show();
        } else {
            checkIsPermissionEnabled();
        }

    }


    private void checkIsPermissionEnabled() {
        int currentapiVersion = Build.VERSION.SDK_INT;

        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (MyApplication.permissions.isUsageAccessActive(this) == true && Settings.canDrawOverlays(this) == true && MyApplication.permissions.isAdminActive(this) == true) {
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
                if (Settings.canDrawOverlays(this) == false) {
                    startActivity(new Intent(this, Activity_DrawOverOtherApplicatin.class));
                    overridePendingTransition(0, 0);
                    finish();
                } else if (MyApplication.permissions.isAdminActive(this) == false) {
                    startActivity(new Intent(this, Activity_AdminEnable.class));
                    overridePendingTransition(0, 0);
                    finish();
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
                if (MyApplication.permissions.isAdminActive(this) == false) {
                    startActivity(new Intent(this, Activity_AdminEnable.class));
                    overridePendingTransition(0, 0);
                    finish();
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

        } else if (currentapiVersion < Build.VERSION_CODES.LOLLIPOP) {
            if (MyApplication.permissions.isAdminActive(this) == false) {
                startActivity(new Intent(this, Activity_AdminEnable.class));
                overridePendingTransition(0, 0);
                finish();
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


}
