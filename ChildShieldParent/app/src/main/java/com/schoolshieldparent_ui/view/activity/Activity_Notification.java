package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.notification.Result;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Notification extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    public static Activity_Notification instance;


    public int LASTKNOWN_POSITION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notification );
        ButterKnife.bind( this );
        instance = this;
        getSupportActionBar().hide();
        getNotificationData( getString( R.string.refresh ) );

    }


    public void getNotificationData(String refresh) {
        if (refresh.equalsIgnoreCase( getString( R.string.refresh ))) {
            WebServiceResult.NotificationData( MyApplication.currentChildID, 0 );
        } else {
            DialogManager.startProgressDialog( getInstance() );
            WebServiceResult.NotificationData( MyApplication.currentChildID, 0 );
        }


    }

    public static Activity_Notification getInstance() {
        return instance;
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

    private void setupViewPager(Result result) {
        LASTKNOWN_POSITION = viewPager.getCurrentItem();
        ViewPagerAdapter adapter = new ViewPagerAdapter( getSupportFragmentManager(), result, this );
        viewPager.setAdapter( adapter );
        viewPager.setCurrentItem( LASTKNOWN_POSITION );
    }


    public void updateNotification(Result result) {
        try {
            setupViewPager( result );
            tabLayout.setupWithViewPager( viewPager );
        } catch (Exception e) {

        }
    }

    public void messageUpdateFromNotification(String message, String title) {


    }
}
