package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.presenter.WebServiceResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_ForumAddTrafficLight extends AppCompatActivity {
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.checkBox1)
    CheckBox checkBox1;
    @BindView(R.id.checkBox2)
    CheckBox checkBox2;
    @BindView(R.id.checkBox3)
    CheckBox checkBox3;


    private static Activity_ForumAddTrafficLight instance;
    private String APP_NAME = "";
    private String PACKAGE = "";
    private String light = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_add_traffic_light);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        instance = this;
        getIntentData();
    }

    public static Activity_ForumAddTrafficLight getInstance() {
        return instance;
    }

    private void getIntentData() {
        APP_NAME = getIntent().getStringExtra("APP_NAME");
        PACKAGE = getIntent().getStringExtra("PACKAGE");
        textView11.setText(APP_NAME);
    }

    private void setTrafficLimit() {
        DialogManager.startProgressDialog(this);
        WebServiceResult.setTrafficLimit(SharedPref.getString(MyApplication.PARENT_ID), PACKAGE, light);
    }

    public void updateAgeStatus() {
        CustomToast.showToast(this, getString(R.string.trafficlightadded));
        Activity_ForumsAllApps.getInstance().getForumsData();
    }

    @OnClick(R.id.buttonSubmit)
    public void buttonsubmit() {
        if (light.length() > 0) {
            setTrafficLimit();
        } else {
            CustomToast.showToast(this, getString(R.string.setlight));
        }
    }

    @OnClick(R.id.RL_underMainLayout1)
    public void firstLimit() {
        checkBox1.setChecked(true);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        light = "1";
    }

    @OnClick(R.id.RL_underMainLayout2)
    public void secondLimit() {
        checkBox1.setChecked(false);
        checkBox2.setChecked(true);
        checkBox3.setChecked(false);
        light = "2";

    }

    @OnClick(R.id.RL_underMainLayout3)
    public void thirdLimit() {
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(true);
        light = "3";
    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        finish();
        overridePendingTransition(0, 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);

    }

}
