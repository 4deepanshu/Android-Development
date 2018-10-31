package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.applications.Result;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.ViewPagerAdapter_BlockApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Applications extends AppCompatActivity {
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    public static Activity_Applications instance;
    private int LASTKNOWN_POSITION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_applications );
        ButterKnife.bind( this );
        getSupportActionBar().hide();
        instance = this;
        getAllApps( "" );
    }

    public void getAllApps(String refresh) {
        if (refresh.equalsIgnoreCase( getString( R.string.refresh ) )) {
            WebServiceResult.GetAllApplication( MyApplication.currentChildID );
        } else {
            DialogManager.startProgressDialog( this );
            WebServiceResult.GetAllApplication( MyApplication.currentChildID );
        }
    }

    public static Activity_Applications getInstance() {
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
        ViewPagerAdapter_BlockApp adapter = new ViewPagerAdapter_BlockApp( getSupportFragmentManager(), result, this );
        viewPager.setAdapter( adapter );
        viewPager.setCurrentItem( LASTKNOWN_POSITION );
    }


    public void updateAllApps(Result result) {
        try {
            setupViewPager( result );
            tabLayout.setupWithViewPager( viewPager );
        } catch (Exception e) {

        }
    }
}
