package com.schoolshieldchild_ui.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.model.studentlocation.StudentLocation;
import com.schoolshieldchild_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldchild_ui.presenter.WebServiceResult;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_LOCK_SCREEN = 101;
    static HomeActivity instance = null;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    public static HomeActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        startServices();
        getSchoolLatLong();


    }


    @Override
    protected void onResume() {
        super.onResume();
        instance = HomeActivity.this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        instance = null;

    }

    private void getSchoolLatLong() {
        if (SharedPref.getString(MyApplication.STUDENT_ROLE).equalsIgnoreCase("student")) {
            WebServiceResult.getStudentLatLong(SharedPref.getString(MyApplication.STUDENT_ID));
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOCK_SCREEN) {
            if (Settings.canDrawOverlays(this)) {
            }
        }
    }

    private void startServices() {
        MyApplication.getInstance().startBackgroundServices();
    }

    public void updatelatlong(StudentLocation response) {
        try {
            SharedPref.setString(MyApplication.STUDENT_LAT, response.getData().getSchoolLatitude());
            SharedPref.setString(MyApplication.STUDENT_LON, response.getData().getSchoolLongitude());
        } catch (NullPointerException e) {

        }
    }

    @OnClick(R.id.settings)
    public void openSettingsScreen() {

        startActivity(new Intent(this, NumbersSettingsActivity.class));
        overridePendingTransition(0, 0);

    }

    @OnClick(R.id.notification)
    public void sendNotification() {
        WebServiceResult.emergency(SharedPref.getString(MyApplication.STUDENT_ID));
    }


    public void udpateEmergency(UniversalResponce body) {

        if (instance != null) {
            if (body.getResult().getStatus() == 1) {
                Toast.makeText(instance, getString(R.string.notificationsend), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(instance, getString(R.string.unabletosendnotification), Toast.LENGTH_LONG).show();
            }
        }

    }


    @OnClick(R.id.boy_msg)
    public void fatherMessage(ImageButton img) {
        img.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        ImageButton view1 = (ImageButton) v;
                        view1.getBackground().clearColorFilter();
                        view1.invalidate();
                        try {
                            if (SharedPref.getString(MyApplication.MESSAGE_FATHER).length() > 0) {
                                SmsManager smsManager = SmsManager.getDefault();

                                smsManager.sendTextMessage(SharedPref.getString(MyApplication.MESSAGE_FATHER),
                                        null, getString(R.string.contactme), null, null);

                            } else {
                                Toast.makeText(getApplicationContext(), "Please add number in settings", Toast.LENGTH_LONG)
                                        .show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;

            }
        });
    }

    @OnClick(R.id.girl_msg)
    public void motherMessage(ImageButton img) {

        img.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        ImageButton view1 = (ImageButton) v;
                        view1.getBackground().clearColorFilter();
                        view1.invalidate();
                        try {

                            if (SharedPref.getString(MyApplication.MESSAGE_MOTHER).length() > 0) {
                                SmsManager smsManager = SmsManager.getDefault();

                                smsManager.sendTextMessage(SharedPref.getString(MyApplication.MESSAGE_MOTHER),
                                        null, getString(R.string.contactme), null, null);

                            } else {
                                Toast.makeText(getApplicationContext(), "Please add number in settings", Toast.LENGTH_LONG)
                                        .show();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;

            }
        });

    }

    @OnClick(R.id.boy_call)
    public void fatherCall(ImageButton img) {

        img.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        ImageButton view1 = (ImageButton) v;
                        view1.getBackground().clearColorFilter();
                        view1.invalidate();
                        try {
                            if (SharedPref.getString(MyApplication.PHONE_FATHER).length() > 0) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    callIntent.setPackage("com.android.server.telecom");
                                } else {
                                    callIntent.setPackage("com.android.phone");
                                }
                                callIntent.setData(
                                        Uri.parse("tel:" + SharedPref.getString(MyApplication.PHONE_FATHER)));
                                startActivityForResult(callIntent, 0);
                            } else {
                                Toast.makeText(getApplicationContext(), "Please add number in settings", Toast.LENGTH_LONG)
                                        .show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;

            }
        });

    }

    @OnClick(R.id.girl_call)
    public void motherCall(ImageButton img) {

        img.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        ImageButton view1 = (ImageButton) v;
                        view1.getBackground().clearColorFilter();
                        view1.invalidate();
                        try {
                            if (SharedPref.getString(MyApplication.PHONE_MOTHER).length() > 0) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    callIntent.setPackage("com.android.server.telecom");
                                } else {
                                    callIntent.setPackage("com.android.phone");
                                }

                                callIntent.setData(
                                        Uri.parse("tel:" + SharedPref.getString(MyApplication.PHONE_MOTHER)));
                                startActivityForResult(callIntent, 0);
                            } else {
                                Toast.makeText(getApplicationContext(), "Please add number in settings", Toast.LENGTH_LONG)
                                        .show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;

            }
        });

    }

}
