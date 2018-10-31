package com.schoolshieldparent_ui.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.custom_controls.Button_Regular;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class Activity_ApplicationLock extends AppCompatActivity {

    public static Activity_ApplicationLock instance;
    @BindView(R.id.cont)
    LinearLayout container;
    @BindView(R.id.textViewStartTime)
    TextView_Regular textViewStartTime;
    @BindView(R.id.textview_new)
    TextView_Regular textview_new;
    @BindView(R.id.textViewEndTime)
    TextView_Regular textViewEndTime;
    @BindView(R.id.toggle)
    ToggleButton toggleDaily;
    @BindView(R.id.textViewAppName)
    TextView_Regular txtViewApplicationName;
    @BindView(R.id.imageButtonDone)
    Button buttonLockUnlock;
    @BindView(R.id.imageButtonPermanent)
    Button buttonPremanet;
    @BindView(R.id.editextTitle)
    EditText_Regular editTextTitle;
    @BindView(R.id.sunday)
    CheckBox checkBoxSunday;
    @BindView(R.id.monday)
    CheckBox checkBoxMonday;
    @BindView(R.id.tuesday)
    CheckBox checkBoxTuesday;
    @BindView(R.id.wednesday)
    CheckBox checkBoxWednesday;
    @BindView(R.id.thursday)
    CheckBox checkBoxThursday;
    @BindView(R.id.friday)
    CheckBox checkBoxFriday;
    @BindView(R.id.saturday)
    CheckBox checkBoxSaturday;
    private static String packageName = "";
    private String permanent = "";
    String startTime = "";
    String endTime = "";
    private String From = "parent";
    private String days = "";
    private String daily = "";
    private int Time_PICKER_ID;
    private Calendar mCalen;
    private int hourOfDay;
    private int minute;
    private int ampm;
    private String fragmentPos = "0";
    private String AppName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_lock);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        instance = this;
        init();
        getIntentData();
    }

    @OnClick(R.id.textview_new)
    public void setCustomTextClick() {
        startActivity(new Intent(this, Activity_CustomLock.class).putExtra("packageName", packageName));
        overridePendingTransition(0, 0);
    }

    private void getIntentData() {
        AppName = getIntent().getStringExtra("AppName");
        fragmentPos = getIntent().getStringExtra("fragmentPos");
        permanent = getIntent().getStringExtra("permanent");
        packageName = getIntent().getStringExtra("package");
        if (permanent.equalsIgnoreCase("1")) {
            buttonPremanet.setText(getString(R.string.permanetRemove));
        } else {
            buttonPremanet.setText(getString(R.string.permanetLock));
        }
        editTextTitle.requestFocusFromTouch();
        if (fragmentPos.equalsIgnoreCase("1")) {
            textview_new.setText("");
            textview_new.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
        }
        txtViewApplicationName.setText("Schedule " + AppName + " lock");
    }

    public static Activity_ApplicationLock getInstance() {
        return instance;
    }

    @OnClick(R.id.imageButtonDone)
    public void setCustomLock() {
        checkValidation();
    }

    @OnClick(R.id.imageButton_Back)
    public void doback() {
        finish();
        overridePendingTransition(0, 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);

    }

    private void checkValidation() {
        if (editTextTitle.getText().toString().replace(" ", "").length() > 5) {
            if (startTime.length() != 0 && endTime.length() != 0) {
                boolean dailyB = toggleDaily.isChecked();
                String finalDayString = "";
                if (dailyB == false) {
                    if (checkBoxSunday.isChecked()) {
                        finalDayString += "Sunday" + ",";
                    }
                    if (checkBoxMonday.isChecked()) {
                        finalDayString += "Monday" + ",";
                    }
                    if (checkBoxTuesday.isChecked()) {
                        finalDayString += "Tuesday" + ",";
                    }
                    if (checkBoxWednesday.isChecked()) {
                        finalDayString += "Wednesday" + ",";
                    }
                    if (checkBoxThursday.isChecked()) {
                        finalDayString += "Thursday" + ",";
                    }
                    if (checkBoxFriday.isChecked()) {
                        finalDayString += "Friday" + ",";
                    }
                    if (checkBoxSaturday.isChecked()) {
                        finalDayString += "Saturday" + ",";
                    }
                    daily = "0";
                } else {
                    finalDayString = "Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,";
                    daily = "1";
                }
                if (finalDayString.length() > 0) {
                    finalDayString = finalDayString.substring(0, finalDayString.length() - 1);
                }

                if (dailyB == false && finalDayString.equalsIgnoreCase("")) {
                    SimpleAlertDialog.showAlertDialog(Activity_ApplicationLock.this,
                            "Feedback\n\n Please select any Day or Check Daily option");
                } else {
                    setLock(editTextTitle.getText().toString(), packageName, MyApplication.currentChildID, From, SharedPref.getString(MyApplication.PARENT_ID), startTime, endTime, finalDayString, daily, "0");
                }
            } else {
                CustomToast.showToast(this, getString(R.string.checkYourTime));

            }
        } else {
            CustomToast.showToast(this, getString(R.string.titleCheck));

        }

    }

    public static void setLock(String title, String packageName, int currentChildID, String from, String parentid, String startTime, String endTime, String finalDayString, String daily, String permanent) {
        DialogManager.startProgressDialog(getInstance());
        WebServiceResult.LockApplication(title, packageName, currentChildID, from, parentid, startTime, endTime, finalDayString, daily, permanent);

    }

    @OnClick(R.id.imageButtonPermanent)
    public void setPermanetLock() {
        if (permanent.equalsIgnoreCase("1")) {
            WebServiceResult.deletelock("-1", SharedPref.getString(MyApplication.PARENT_ID), "parent", MyApplication.currentChildID + "", "1", packageName);

        } else {
            setLock(editTextTitle.getText().toString(), packageName, MyApplication.currentChildID, From, SharedPref.getString(MyApplication.PARENT_ID), "", "", "", "", "1");
        }

    }

    @OnCheckedChanged(R.id.toggle)
    public void toggleChanged(ToggleButton tlg) {
        if (tlg.isChecked()) {
            selectDeselectAllDays(true);
        } else {
            selectDeselectAllDays(false);
        }
    }

    private void selectDeselectAllDays(boolean daily) {
        checkBoxSunday.setChecked(daily);
        checkBoxMonday.setChecked(daily);
        checkBoxTuesday.setChecked(daily);
        checkBoxWednesday.setChecked(daily);
        checkBoxThursday.setChecked(daily);
        checkBoxFriday.setChecked(daily);
        checkBoxSaturday.setChecked(daily);

    }

    private void isAllChecked() {
        if (checkBoxSunday.isChecked() && checkBoxMonday.isChecked() && checkBoxTuesday.isChecked()
                && checkBoxWednesday.isChecked() && checkBoxThursday.isChecked() && checkBoxFriday.isChecked()
                && checkBoxSaturday.isChecked()) {
            toggleDaily.setChecked(true);
        } else {
            toggleDaily.setChecked(false);
        }
    }

    @OnCheckedChanged(R.id.sunday)
    public void onSundayCheck(CheckBox checkBox) {
        isAllChecked();
    }

    @OnCheckedChanged(R.id.monday)
    public void onMondayCheck(CheckBox checkBox) {
        isAllChecked();
    }

    @OnCheckedChanged(R.id.tuesday)
    public void onTuesdayCheck(CheckBox checkBox) {
        isAllChecked();
    }

    @OnCheckedChanged(R.id.wednesday)
    public void onWednesDayCheck(CheckBox checkBox) {
        isAllChecked();
    }

    @OnCheckedChanged(R.id.thursday)
    public void onThursdayCheck(CheckBox checkBox) {
        isAllChecked();
    }

    @OnCheckedChanged(R.id.friday)
    public void onFirdayCheck(CheckBox checkBox) {
        isAllChecked();
    }

    @OnCheckedChanged(R.id.saturday)
    public void onSaturdayCheck(CheckBox checkBox) {
        isAllChecked();
    }

    private void init() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mCalen = Calendar.getInstance();
        hourOfDay = mCalen.get(Calendar.HOUR_OF_DAY);
        minute = mCalen.get(Calendar.MINUTE);
        ampm = mCalen.get(Calendar.AM_PM);
    }

    @OnClick(R.id.textViewStartTime)
    public void showStartTimeDilog() {
        Time_PICKER_ID = 0;
        showDialog(Time_PICKER_ID);
    }

    @OnClick(R.id.textViewEndTime)
    public void showEndTimeDilog() {
        Time_PICKER_ID = 1;
        showDialog(Time_PICKER_ID);
    }

    public void updateAppLock(UniversalResponce body) {
        if (Activity_Applications.instance != null) {
            Activity_Applications.getInstance().viewPager.getAdapter().notifyDataSetChanged();
        }
        if (body.getResult().getStatus() == 0) {
            showLockStatus(this, getString(R.string.applicationlockedfail));
        } else {
            showLockStatus(this, getString(R.string.applicationlocked));

        }
    }

    public void showLockStatus(final Activity activity, final String message) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.view_simple_alert);
        TextView_Regular text = (TextView_Regular) dialog.findViewById(R.id.message);
        text.setText(message);
        Button_Regular dialogButton = (Button_Regular) dialog.findViewById(R.id.button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentPos.equalsIgnoreCase("-1")) {
                    Activity_Analytics.getInstance().getAnalyticsData();
                } else {
                    Activity_Applications.getInstance().getAllApps(getString(R.string.refresh));
                }
                activity.finish();
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                try {
                    if (fragmentPos.equalsIgnoreCase("-1")) {
                        Activity_Analytics.getInstance().getAnalyticsData();
                    } else {
                        Activity_Applications.getInstance().getAllApps(getString(R.string.refresh));
                    }
                    activity.finish();
                    dialog.dismiss();
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        });
    }

    @Override

    protected Dialog onCreateDialog(int id) {

        switch (Time_PICKER_ID) {
            case 0:
                TimePickerDialog dlg1 = new TimePickerDialog(this, TimePickerListener, hourOfDay, minute, false);
                dlg1.setTitle(getString(R.string.starttime));
                return dlg1;
            case 1:
                TimePickerDialog dlg2 = new TimePickerDialog(this, TimePickerListener, hourOfDay, minute, false);
                dlg2.setTitle(getString(R.string.endtime));
                return dlg2;
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener TimePickerListener = new TimePickerDialog.OnTimeSetListener() {

        // while dialog box is closed, below method is called.
        public void onTimeSet(TimePicker view, int hour, int minute) {

            mCalen.set(Calendar.HOUR_OF_DAY, hour);
            mCalen.set(Calendar.MINUTE, minute);

            int hour12format = mCalen.get(Calendar.HOUR);
            hourOfDay = mCalen.get(Calendar.HOUR_OF_DAY);
            minute = mCalen.get(Calendar.MINUTE);
            ampm = mCalen.get(Calendar.AM_PM);
            String ampmStr = (ampm == 0) ? getString(R.string.am) : getString(R.string.pm);
            if (Time_PICKER_ID == 0) {
                if (hour12format == 0) {
                    hour12format = 12;
                }
                startTime = hour12format + " : " + minute + " / " + ampmStr;
                textViewStartTime.setText(getString(R.string.starttime) + "  " + startTime);
            } else if (Time_PICKER_ID == 1) {
                if (hour12format == 0) {
                    hour12format = 12;
                }
                endTime = hour12format + " : " + minute + " / " + ampmStr;
                textViewEndTime.setText(getString(R.string.endtime) + "  " + endTime);

            }
        }
    };

    public void lockDeleted(UniversalResponce body) {
        if (body.getResult().getStatus().toString().equalsIgnoreCase("1")) {
            showLockStatus(this, getString(R.string.notifylockdeleted));
        } else {
            showLockStatus(this, getString(R.string.notifylockdeletedfail));

        }
    }
}
