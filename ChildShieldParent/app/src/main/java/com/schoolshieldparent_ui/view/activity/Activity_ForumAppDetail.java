package com.schoolshieldparent_ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.controller.utils.SimpleAlertDialog;
import com.schoolshieldparent_ui.presenter.WebServiceResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_ForumAppDetail extends AppCompatActivity {

    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.customTextViewDetail)
    TextView customTextViewDetail;
    @BindView(R.id.imageButton_new)
    ImageButton imageButton_new;

    String APP_NAME = "";
    private String APP_ID = "";
    private String PACKAGE = "";
    static Activity_ForumAppDetail instance;
    private String DESC = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        ButterKnife.bind(this);
        instance = this;
        getSupportActionBar().hide();
        getIntentData();
    }

    public static Activity_ForumAppDetail getInstance() {
        return instance;
    }

    private void getIntentData() {
        DESC = getIntent().getStringExtra("DESC");
        APP_NAME = getIntent().getStringExtra("APP_NAME");
        APP_ID = getIntent().getStringExtra("APP_ID");
        PACKAGE = getIntent().getStringExtra("PACKAGE");
        textView11.setText(APP_NAME);
        imageButton_new.setBackgroundResource(R.drawable.rate);
        customTextViewDetail.setText(DESC);
    }

    @OnClick(R.id.imageButton_new)
    public void addApplicationRating() {
        DialogManager.startProgressDialog(this);
        WebServiceResult.GetAppRating(PACKAGE, SharedPref.getString(MyApplication.PARENT_ID));
    }

    @OnClick(R.id.view_Option1)
    public void showOthersComments() {
        startActivity(new Intent(this, Activity_ForumAppCommets.class)
                .putExtra("PACKAGE", PACKAGE).putExtra("APP_NAME", APP_NAME).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    @OnClick(R.id.view_Option2)
    public void addMyCommnets() {
        startActivity(new Intent(this, Activity_ForumAddMyCommments.class)
                .putExtra("PACKAGE", PACKAGE).putExtra("APP_NAME", APP_NAME).putExtra("PARENT_ID", SharedPref.getString(MyApplication.PARENT_ID)).putExtra("APP_NAME", APP_NAME).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @OnClick(R.id.view_Option4)
    public void showAgeOptions() {
        startActivity(new Intent(this, Activity_ForumAddAgeLimit.class)
                .putExtra("PACKAGE", PACKAGE).putExtra("PARENT_ID", SharedPref.getString(MyApplication.PARENT_ID)).putExtra("APP_NAME", APP_NAME).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @OnClick(R.id.view_Option5)
    public void showTraficLightOptions() {
        startActivity(new Intent(this, Activity_ForumAddTrafficLight.class)
                .putExtra("PACKAGE", PACKAGE).putExtra("PARENT_ID", SharedPref.getString(MyApplication.PARENT_ID)).putExtra("APP_NAME", APP_NAME).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

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

    public void addRatingToApp(String rating) {
        DialogManager.startProgressDialog(this);
        WebServiceResult.addAppRating(PACKAGE, SharedPref.getString(MyApplication.PARENT_ID), rating);
    }

    public void updateRating(String rating) {
        SimpleAlertDialog.showAlertDialogRate(this, rating);
    }

    public void updateaddRatingStatus() {
        CustomToast.showToast(this, getString(R.string.ratedsuccessfully));
    }
}
