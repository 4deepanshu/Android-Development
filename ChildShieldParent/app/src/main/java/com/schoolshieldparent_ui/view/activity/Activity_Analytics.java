package com.schoolshieldparent_ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.analytics.Result;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.ViewPagerAdapter_Analytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Analytics extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    public
    ViewPager viewPager;
    @BindView(R.id.textViewChildName)
    TextView textViewChildName;

    public static Activity_Analytics instance;
    public int LASTKNOWN_POSITION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_analytics );
        ButterKnife.bind( this );
        instance = this;
        getSupportActionBar().hide();
        textViewChildName.setText( MyApplication.currentChildName );
        getAnalyticsData();

    }

    public static Activity_Analytics getInstance() {
        return instance;
    }


    private void setupViewPager(Result result) {
        LASTKNOWN_POSITION = viewPager.getCurrentItem();
        ViewPagerAdapter_Analytics adapter = new ViewPagerAdapter_Analytics( getSupportFragmentManager(), result, this );
        viewPager.setAdapter( adapter );
        viewPager.setCurrentItem( LASTKNOWN_POSITION );
    }

    public void updateAnalytics(Result result) {
        try {
            setupViewPager( result );
            tabLayout.setupWithViewPager( viewPager );
            if (result.getBest() != null) {
                Intent intent = new Intent( this, Activity_TopRunnungApp.class );
                intent.putExtra( "From", "" );
                intent.putExtra( "light", result.getBest().getLight() );
                intent.putExtra( "appname", result.getBest().getAppName() );
                intent.putExtra( "package_name", result.getBest().getPackage() );
                intent.putExtra( "permanent", result.getBest().getPermanentLock() );
                intent.putExtra( "icon", result.getBest().getAppIcon() );
                intent.putExtra( "count", result.getBest().getCountTotal() );
                intent.putExtra( "age", result.getBest().getAgeRange() );
                intent.putExtra( "duration", result.getBest().getTotalDuration() );
                intent.putExtra( "locked", result.getBest().getLocked() + "" );
                intent.putExtra( "percentage", result.getBest().getPercentage() + "" );
                startActivity( intent );
                overridePendingTransition( 0, 0 );

            }
        } catch (Exception e) {

        }

    }


    public void getAnalyticsData() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.AnalyticsAllApplications();

    }

    @OnClick(R.id.imageButton_Back)
    public void goBack() {
        finish();
        overridePendingTransition( 0, 0 );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, 0 );
    }


}
