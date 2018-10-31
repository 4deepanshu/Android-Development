package com.schoolshieldchild_ui.view.services.screenlock;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.schoolshieldchild_ui.R;
import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldchild_ui.presenter.WebServiceResult;
import com.schoolshieldchild_ui.view.database.DataBaseHandler;

public class SceenLockView extends Service implements OnClickListener {
    static View mView;
    static Context context;
    static Service service;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inf.inflate(R.layout.view_lock_screen, null);
        context = getApplicationContext();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.alpha = 1.0f;
        layoutParams.packageName = context.getPackageName();
        layoutParams.buttonBrightness = 1f;
        layoutParams.windowAnimations = android.R.style.Animation_Dialog;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        service = this;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mView, layoutParams);
        getUI();
        onEditeTextChange();
    }

    EditText edittextPassword;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn0;
    Button btnClose;
    Button btnClear;
    private Button btnEmergency;

    private void getUI() {
        edittextPassword = (EditText) mView.findViewById(R.id.editTextPasswr);
        btn1 = (Button) mView.findViewById(R.id.buttonone);
        btn2 = (Button) mView.findViewById(R.id.buttontwo);
        btn3 = (Button) mView.findViewById(R.id.buttonthree);
        btn4 = (Button) mView.findViewById(R.id.buttonfour);
        btn5 = (Button) mView.findViewById(R.id.buttonfive);
        btn6 = (Button) mView.findViewById(R.id.buttonsix);
        btn7 = (Button) mView.findViewById(R.id.buttonseven);
        btn8 = (Button) mView.findViewById(R.id.buttoneight);
        btn9 = (Button) mView.findViewById(R.id.buttonnine);
        btn0 = (Button) mView.findViewById(R.id.buttonzerro);
        btnClose = (Button) mView.findViewById(R.id.buttonclose);
        btnClear = (Button) mView.findViewById(R.id.buttonclear);
        btnEmergency = (Button) mView.findViewById(R.id.buttonEmergency);
        setButtonClicks(btn0);
        setButtonClicks(btn1);
        setButtonClicks(btn2);
        setButtonClicks(btn3);
        setButtonClicks(btn4);
        setButtonClicks(btn5);
        setButtonClicks(btn6);
        setButtonClicks(btn7);
        setButtonClicks(btn8);
        setButtonClicks(btn9);
        setButtonClicks(btnClose);
        setButtonClicks(btnClear);
        btnEmergency.setVisibility(View.GONE);
        btnClear.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                passwordEntered = "";
                edittextPassword.setText(passwordEntered);
                return false;
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }

    String passwordEntered = "";

    private void setButtonClicks(final Button buttonClicked) {
        buttonClicked.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (buttonClicked.getText().toString().equalsIgnoreCase(getApplicationContext().getString(R.string.clear))
                        || buttonClicked.getText().toString()
                        .equalsIgnoreCase(getApplicationContext().getString(R.string.close))) {

                    if (buttonClicked.getText().toString()
                            .equalsIgnoreCase(getApplicationContext().getString(R.string.clear))) {
                        if (passwordEntered.length() > 0) {
                            passwordEntered = passwordEntered.substring(0, passwordEntered.length() - 1);
                            edittextPassword.setText(passwordEntered);

                        }
                    } else {
                        try {
                            stopService();
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(startMain);
                        } catch (NullPointerException e) {
                        } catch (Exception e) {
                        }
                    }
                } else {
                    passwordEntered += buttonClicked.getText().toString();
                    edittextPassword.setText(passwordEntered);
                }
            }
        });

    }


    public static void stopService() {
        try {
            DetectScreenLock.isLockViewVisible = false;
            service.stopSelf();
            ((WindowManager) context.getSystemService(WINDOW_SERVICE)).removeView(mView);
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
    }


    private void onEditeTextChange() {
        edittextPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equalsIgnoreCase(SharedPref.getString(MyApplication.STUDENT_PASSWORD))) {
                    stopService();
                    if (SharedPref.getString(MyApplication.SCREEN_PREMANENT_LOCK).equalsIgnoreCase("Yes")) {
                        WebServiceResult.deleteScreenlock("-1", SharedPref.getString(MyApplication.SCREEN_PREMANENT_LOCK_FROMID), SharedPref.getString(MyApplication.SCREEN_PREMANENT_LOCK_FROM), SharedPref.getString(MyApplication.STUDENT_ID), "1");
                    } else {
                        if (DetectScreenLock.currentScreenLockId > 0) {
                            WebServiceResult.deleteScreenlock(DetectScreenLock.currentScreenLockId + "", "-1", DetectScreenLock.currentScreenLockFrom, SharedPref.getString(MyApplication.STUDENT_ID), "-1");
                        }
                    }
                } else if (s.toString().equalsIgnoreCase("336699777")) {
                    stopService();
                    SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK, "No");
                    SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK_FROM, "");
                    SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK_FROMID, "");
                    SharedPref.setString(MyApplication.SCREEN_PREMANENT_LOCK_ID, "");
                    SceenLockView.stopService();

                    DataBaseHandler dataBaseHandler=new DataBaseHandler(context);
                    dataBaseHandler.truncateTableScreenLock();

                    WebServiceResult.deleteScreenlock("-1", SharedPref.getString(MyApplication.SCREEN_PREMANENT_LOCK_FROMID), SharedPref.getString(MyApplication.SCREEN_PREMANENT_LOCK_FROM), SharedPref.getString(MyApplication.STUDENT_ID), "1");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


}
