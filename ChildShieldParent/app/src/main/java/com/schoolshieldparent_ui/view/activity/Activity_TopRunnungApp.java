package com.schoolshieldparent_ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.presenter.WebServiceConnection;
import com.schoolshieldparent_ui.view.custom_controls.CircleDisplay;
import com.schoolshieldparent_ui.view.custom_controls.TextView_Regular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_TopRunnungApp extends AppCompatActivity {
    String userID;
    String PNAME;
    @BindView(R.id.textView1)
    TextView_Regular textView1;
    @BindView(R.id.textView2)
    TextView_Regular textView_Detail;
    @BindView(R.id.lock_application_)
    Button lock_application_;
    @BindView(R.id.textView_AllApps)
    TextView_Regular textView_AllApps;

    @BindView(R.id.imageViewAppIcon)
    ImageView imageViewAppIcon;

    @BindView(R.id.textViewName)
    TextView_Regular textViewName;

    @BindView(R.id.nttextViewTime)
    TextView_Regular nttextViewTime;

    @BindView(R.id.nttextViewCount)
    TextView_Regular nttextViewCount;

    @BindView(R.id.textViewDuration)
    TextView_Regular textViewDuration;

    @BindView(R.id.textView3)
    TextView_Regular appPerCentrage;

    @BindView(R.id.textView4)
    TextView_Regular otherPerCentrage;

    @BindView(R.id.red)
    CheckBox checkBoxRed;
    @BindView(R.id.green)
    CheckBox checkBoxGreen;
    @BindView(R.id.orange)
    CheckBox checkBoxOrange;

    public static Activity_TopRunnungApp instance;
    private CircleDisplay mCircleDisplay;
    private String light = "";
    private String percentage;
    private String appname = "";
    private String count = "";
    private String duration = "";
    private String age = "";
    private String locked;
    private String icon = "";
    String from = "";
    private String package_name = "";
    private String permanent = "";
    private String fragment_pos = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_top_runnung_app );
        ButterKnife.bind( this );
        getSupportActionBar().hide();
        instance = this;
        getData();
        setData();
    }

    private void getData() {
        from = getIntent().getStringExtra( "From" );
        if (from.equalsIgnoreCase( "" )) {
        } else {
            textView1.setText( getResources().getString( R.string.analytics ) );
        }
        light = getIntent().getStringExtra( "light" );
        appname = getIntent().getStringExtra( "appname" );
        package_name = getIntent().getStringExtra( "package_name" );
        permanent = getIntent().getStringExtra( "permanent" );
        icon = getIntent().getStringExtra( "icon" );
        count = getIntent().getStringExtra( "count" );
        age = getIntent().getStringExtra( "age" );
        duration = getIntent().getStringExtra( "duration" );
        locked = getIntent().getStringExtra( "locked" );
        percentage = getIntent().getStringExtra( "percentage" );
    }

    public Activity_TopRunnungApp getinstance() {
        return instance;
    }


    @OnClick(R.id.textView_AllApps)
    public void showAllApps() {
        finish();
    }


    private void setData() {
        setPercentage();
        try {
            if (!icon.equalsIgnoreCase( "" )) {
                ImageLoader.getInstance().displayImage( WebServiceConnection.APPLICATION_ICON_URLS + icon, imageViewAppIcon );
            } else
                imageViewAppIcon.setBackgroundResource( R.drawable.app_placeholder );
        } catch (Exception e) {
        }
        textViewName.setText( appname );
        nttextViewCount.setText( "X " + count );
        textViewDuration.setText( convertDuration( duration + "" ) );
        nttextViewTime.setText( age );
        if (light.equalsIgnoreCase( "2" )) {
            checkBoxGreen.setChecked( true );
        } else if (light.equalsIgnoreCase( "3" )) {
            checkBoxOrange.setChecked( true );
        } else if (light.equalsIgnoreCase( "1" )) {
            checkBoxRed.setChecked( true );
        }
        if (permanent.equals( "1" )) {
            lock_application_.setText( getResources().getString( R.string.islocked ) );
        } else {
            lock_application_.setText( getResources().getString( R.string.isnotlocked ) );
        }
    }

    private String convertDuration(String duration) {
        int totalDuraton = Integer.parseInt( duration );
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int minutes = totalDuraton / SECONDS_IN_A_MINUTE;
        totalDuraton -= minutes * SECONDS_IN_A_MINUTE;

        int hours = minutes / MINUTES_IN_AN_HOUR;
        minutes -= hours * MINUTES_IN_AN_HOUR;
        String newDuration = hours + "h " + minutes + "m " + totalDuraton + "s";
        return newDuration;
    }


    @OnClick(R.id.lock_application_)
    public void lockCurrentApp() {
        Intent intent = new Intent( this, Activity_ApplicationLock.class );
        intent.putExtra( "package", package_name );
        intent.putExtra( "fragmentPos", fragment_pos );
        intent.putExtra( "AppName", appname );
        intent.putExtra( "permanent", permanent );
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );
        finish();
        overridePendingTransition( 0, 0 );

    }

    private void setPercentage() {

        mCircleDisplay = (CircleDisplay) findViewById( R.id.circleDisplay );
        mCircleDisplay.setAnimDuration( 2000 );
        mCircleDisplay.setValueWidthPercent( 55f );
        mCircleDisplay.setFormatDigits( 1 );
        mCircleDisplay.setDimAlpha( 80 );
        mCircleDisplay.setTouchEnabled( false );
        mCircleDisplay.setUnit( "%" );
        mCircleDisplay.setStepSize( 0.5f );
        mCircleDisplay.showValue( Float.parseFloat( percentage ), 100f,
                true );
        Float otehr = 100 - (Float.parseFloat( percentage ));
        appPerCentrage.setText( Float.parseFloat( percentage ) + "%\n"
                + appname );
        otherPerCentrage.setText( otehr + "%\n Others" );
    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        if (textView1.getText().equals( getResources().getString( R.string.analytics ) )) {
            finish();
            overridePendingTransition( 0, 0 );
        } else {
            finish();
            Activity_Analytics.getInstance().finish();
            overridePendingTransition( 0, 0 );

        }
        super.onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (textView1.getText().equals( getResources().getString( R.string.analytics ) )) {
            overridePendingTransition( 0, 0 );
        } else {
            Activity_Analytics.getInstance().finish();
            overridePendingTransition( 0, 0 );
        }
    }

}
