package com.schoolshieldparent_ui.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.model.allscreenlockchildren.AllChildForScreenLock;
import com.schoolshieldparent_ui.model.allscreenlockchildren.ScreenLock;
import com.schoolshieldparent_ui.model.universalresponce.UniversalResponce;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.Adapter_ScreenLockChildren;
import com.schoolshieldparent_ui.view.custom_controls.Button_Regular;
import com.schoolshieldparent_ui.view.custom_controls.EditText_Regular;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class Activity_AllChild_LockScreen extends AppCompatActivity {
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.listViewScreenLocks)
    ListView listViewScreenLocks;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeContainerNotification;

    CheckBox checkBoxSunday;
    CheckBox checkBoxMonday;
    CheckBox checkBoxTuesday;
    CheckBox checkBoxWednesday;
    CheckBox checkBoxThursday;
    CheckBox checkBoxFriday;
    CheckBox checkBoxSaturday;
    ToggleButton toggleDaily;

    private static Activity_AllChild_LockScreen instance;
    private List<ScreenLock> screenLock = new ArrayList<>();
    private String From = "parent";
    private String startTime = "";
    private String endTime = "";
    private String finalDayString = "";
    private String daily = "";
    private TextView_Regular textViewStartTime;
    private TextView_Regular textViewEndTime;
    private Button buttonDONE;
    private EditText_Regular editTextTitle;
    private int Time_PICKER_ID;
    private Calendar mCalen;
    private int hourOfDay;
    private int minute;
    private int ampm;
    private String lock_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_child__lock_screen);
        ButterKnife.bind(this);
        instance = this;
        getSupportActionBar().hide();
        textView11.setText(getString(R.string.childrens));
        init();
        getChildren(getString(R.string.refresh));
        setSwipeLoader();
    }

    private void setSwipeLoader() {
        swipeContainerNotification.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeContainerNotification.setRefreshing(false);
                getChildren(getString(R.string.refresh));
            }

        });
        swipeContainerNotification.setColorSchemeResources(android.R.color.black, android.R.color.black, android.R.color.black, android.R.color.black);
    }

    @OnItemClick(R.id.listViewScreenLocks)
    public void setScreenLock(int position) {
        showOptions(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @OnClick(R.id.imageButton_Back)
    public void doback() {
        finish();
        overridePendingTransition(0, 0);

    }

    public void getChildren(String refresh) {
        if (refresh.equalsIgnoreCase(getString(R.string.refresh))) {
            WebServiceResult.AllChildForScreenLock(SharedPref.getString(MyApplication.PARENT_ID));
        } else {
            DialogManager.startProgressDialog(this);
            WebServiceResult.AllChildForScreenLock(SharedPref.getString(MyApplication.PARENT_ID));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        instance = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance = this;
    }

    private void setChildren(List<ScreenLock> screenLock) {
        this.screenLock.clear();
        this.screenLock = screenLock;
        Adapter_ScreenLockChildren adapter_children = new Adapter_ScreenLockChildren(this, screenLock);
        listViewScreenLocks.setAdapter(adapter_children);
    }


    public static Activity_AllChild_LockScreen getInstance() {
        return instance;
    }


    public void updateChildren(AllChildForScreenLock screenLock) {
        setChildren(screenLock.getResult().getScreenLock());
    }

    public void updateLock(UniversalResponce body) {
        if (body.getResult().getStatus() == 1) {
            showLockStatus(this, getString(R.string.screenlocked));
        } else {
            showLockStatus(this, getString(R.string.screenLockedFail));
        }

    }

    private void init() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mCalen = Calendar.getInstance();
        hourOfDay = mCalen.get(Calendar.HOUR_OF_DAY);
        minute = mCalen.get(Calendar.MINUTE);
        ampm = mCalen.get(Calendar.AM_PM);
    }

    private void showOptions(final int position) {

        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_screenlock_type);

        Button_Regular buttonPermanentLock = (Button_Regular) dialog.findViewById(R.id.buttonPermanentLock);
        Button_Regular buttonCustom_Lock = (Button_Regular) dialog.findViewById(R.id.buttonCustom_Lock);
        Button_Regular buttonViewCustom_Lock = (Button_Regular) dialog.findViewById(R.id.buttonViewCustom_Lock);
        if (screenLock.get(position).getPermanentLock().equalsIgnoreCase("1")) {
            buttonPermanentLock.setText(getString(R.string.permanetRemove));
        } else {
        }
        buttonPermanentLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (screenLock.get(position).getPermanentLock().equalsIgnoreCase("1")) {
                    DialogManager.startProgressDialog(Activity_AllChild_LockScreen.this);
                    WebServiceResult.deleteScreenlock("-1", SharedPref.getString(MyApplication.PARENT_ID), "parent", screenLock.get(position).getStudentId(), "1");
                } else {
                    setLock("", screenLock.get(position).getStudentId(), From, SharedPref.getString(MyApplication.PARENT_ID), "", "", "", "", "1");
                }
                dialog.dismiss();
            }
        });
        buttonCustom_Lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomLockDialog(position);
                dialog.dismiss();

            }
        });
        buttonViewCustom_Lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(Activity_AllChild_LockScreen.this, Activity_CustomScreenLock.class).putExtra("studentid", screenLock.get(position).getStudentId()));
                overridePendingTransition(0, 0);

            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        dialog.show();
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    private void showCustomLockDialog(final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_lock_screen);
        findViewByIds(dialog);
        buttonDONE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                checkValidation(position);
            }
        });
        textViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Time_PICKER_ID = 0;
                showDialog(Time_PICKER_ID);
            }
        });
        textViewEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Time_PICKER_ID = 1;
                showDialog(Time_PICKER_ID);
            }
        });
        toggleDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    selectDeselectAllDays(true);
                } else {
                    selectDeselectAllDays(false);
                }

            }
        });
        checkboxClicked();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        dialog.show();
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    private void checkboxClicked() {
        checkBoxSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAllChecked();
            }
        });

        checkBoxMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAllChecked();
            }
        });
        checkBoxTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAllChecked();
            }
        });
        checkBoxWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAllChecked();
            }
        });
        checkBoxThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAllChecked();
            }
        });
        checkBoxFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAllChecked();
            }
        });
        checkBoxSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAllChecked();
            }
        });
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

    private void selectDeselectAllDays(boolean daily) {
        checkBoxSunday.setChecked(daily);
        checkBoxMonday.setChecked(daily);
        checkBoxTuesday.setChecked(daily);
        checkBoxWednesday.setChecked(daily);
        checkBoxThursday.setChecked(daily);
        checkBoxFriday.setChecked(daily);
        checkBoxSaturday.setChecked(daily);

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

    private void checkValidation(int position) {
        if (editTextTitle.getText().toString().replace(" ", "").length() > 5) {
            if (startTime.length() != 0 && endTime.length() != 0) {
                boolean dailyB = toggleDaily.isChecked();
                String finalDayString = "";
                if (dailyB == false) {
                    if (checkBoxSunday.isChecked()) {
                        finalDayString += getString(R.string.sunday) + ",";
                    }
                    if (checkBoxMonday.isChecked()) {
                        finalDayString += getString(R.string.monday) + ",";
                    }
                    if (checkBoxTuesday.isChecked()) {
                        finalDayString += getString(R.string.tuesday) + ",";
                    }
                    if (checkBoxWednesday.isChecked()) {
                        finalDayString += getString(R.string.wednesday) + ",";
                    }
                    if (checkBoxThursday.isChecked()) {
                        finalDayString += getString(R.string.Thursday) + ",";
                    }
                    if (checkBoxFriday.isChecked()) {
                        finalDayString += getString(R.string.Friday) + ",";
                    }
                    if (checkBoxSaturday.isChecked()) {
                        finalDayString += getString(R.string.Saturday) + ",";
                    }
                    daily = "0";
                } else {
                    finalDayString = getString(R.string.allDays);
                    daily = "1";
                }
                if (finalDayString.length() > 0) {
                    finalDayString = finalDayString.substring(0, finalDayString.length() - 1);
                }

                if (dailyB == false && finalDayString.equalsIgnoreCase("")) {
                    SimpleAlertDialog.showAlertDialog(Activity_AllChild_LockScreen.this,
                            getString(R.string.pleaseselecteanyday));
                } else {
                    setLock(editTextTitle.getText().toString(), screenLock.get(position).getStudentId(), From, SharedPref.getString(MyApplication.PARENT_ID), startTime, endTime, finalDayString, daily, "0");
                }
            } else {
                CustomToast.showToast(this, getString(R.string.checkYourTime));


            }
        } else {
            CustomToast.showToast(this, getString(R.string.titleCheck));

        }

    }

    private void findViewByIds(Dialog dialog) {
        checkBoxSunday = (CheckBox) dialog.findViewById(R.id.sunday);
        checkBoxMonday = (CheckBox) dialog.findViewById(R.id.monday);
        checkBoxTuesday = (CheckBox) dialog.findViewById(R.id.tuesday);
        checkBoxWednesday = (CheckBox) dialog.findViewById(R.id.wednesday);
        checkBoxThursday = (CheckBox) dialog.findViewById(R.id.thursday);
        checkBoxFriday = (CheckBox) dialog.findViewById(R.id.friday);
        checkBoxSaturday = (CheckBox) dialog.findViewById(R.id.saturday);
        textViewStartTime = (TextView_Regular) dialog.findViewById(R.id.textViewStartTime);
        textViewEndTime = (TextView_Regular) dialog.findViewById(R.id.textViewEndTime);
        toggleDaily = (ToggleButton) dialog.findViewById(R.id.toggle);
        editTextTitle = (EditText_Regular) dialog.findViewById(R.id.editextTitle);
        buttonDONE = (Button) dialog.findViewById(R.id.imageButtonDone);

    }

    private void setLock(String title, String child_id, String from, String parentid, String startTime, String endTime, String finalDayString, String daily, String permanent) {
        DialogManager.startProgressDialog(this);
        WebServiceResult.LockScreen(title, child_id, from, parentid, startTime, endTime, finalDayString, daily, permanent);
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
                getChildren("");
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                try {
                    getChildren("");
                    dialog.dismiss();
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        });
    }

    public void updateChildLOck(UniversalResponce body) {
        if (body.getResult().getStatus() == 1) {
            showLockStatus(this, getString(R.string.notifylockdeleted));
        } else {
            showLockStatus(this, getString(R.string.notifylockdeletedfail));

        }
    }
}
