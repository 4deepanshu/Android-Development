package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.presenter.WebServiceResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_ForumAddAgeLimit extends AppCompatActivity {

    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.checkBox1)
    CheckBox checkBox1;
    @BindView(R.id.checkBox2)
    CheckBox checkBox2;
    @BindView(R.id.checkBox3)
    CheckBox checkBox3;
    @BindView(R.id.checkBox4)
    CheckBox checkBox4;

    private static Activity_ForumAddAgeLimit instance;
    private String APP_NAME = "";
    private String PACKAGE = "";
    private String age = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forum_add_age_limit );
        ButterKnife.bind( this );
        getSupportActionBar().hide();
        getIntentData();
        instance = this;
    }

    private void sendAgeLimit() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.SetAgeLimit( SharedPref.getString( MyApplication.PARENT_ID ), PACKAGE, age );
    }

    public static Activity_ForumAddAgeLimit getInstance() {
        return instance;
    }

    private void getIntentData() {
        APP_NAME = getIntent().getStringExtra( "APP_NAME" );
        PACKAGE = getIntent().getStringExtra( "PACKAGE" );
        textView11.setText( APP_NAME );
    }

    @OnClick(R.id.RL_underMainLayout1)
    public void firstAge() {
        checkBox1.setChecked( true );
        checkBox2.setChecked( false );
        checkBox3.setChecked( false );
        checkBox4.setChecked( false );

        age = "0-6";
    }

    @OnClick(R.id.RL_underMainLayout2)
    public void secondAge() {
        checkBox1.setChecked( false );
        checkBox2.setChecked( true );
        checkBox3.setChecked( false );
        checkBox4.setChecked( false );

        age = "6-12";

    }

    @OnClick(R.id.RL_underMainLayout3)
    public void thirdAge() {
        checkBox1.setChecked( false );
        checkBox2.setChecked( false );
        checkBox3.setChecked( true );
        checkBox4.setChecked( false );

        age = "12-18";
    }

    @OnClick(R.id.RL_underMainLayout4)
    public void fourthAge() {
        checkBox1.setChecked( false );
        checkBox2.setChecked( false );
        checkBox3.setChecked( false );
        checkBox4.setChecked( true );

        age = "18";
    }

    @OnClick(R.id.buttonSubmit)
    public void setAgeLimit() {
        if (age.length() > 0) {
            sendAgeLimit();
        } else {
            CustomToast.showToast(this, getString(R.string.setagelimit));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, 0 );

    }

    @OnClick(R.id.imageButton_Back)
    public void customGoBack() {
        finish();
        overridePendingTransition( 0, 0 );

    }

    public void updateAgeStatus() {
        CustomToast.showToast(this, getString(R.string.ageaddedsuccessfully));
        Activity_ForumsAllApps.getInstance().getForumsData();
    }
}
