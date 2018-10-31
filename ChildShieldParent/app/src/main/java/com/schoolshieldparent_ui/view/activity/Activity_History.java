package com.schoolshieldparent_ui.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.utils.DialogManager;
import com.schoolshieldparent_ui.model.history.History;
import com.schoolshieldparent_ui.presenter.WebServiceResult;
import com.schoolshieldparent_ui.view.adapter.ViewPagerAdapter_History;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_History extends AppCompatActivity {
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    public
    ViewPager viewPager;
    @BindView(R.id.textViewChildName)
    TextView textViewChildName;

    public static Activity_History instance;
    public int LASTKNOWN_POSITION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_history );
        ButterKnife.bind( this );
        instance = this;
        getSupportActionBar().hide();
        getHistoryData();
    }

    public void getHistoryData() {
        DialogManager.startProgressDialog( this );
        WebServiceResult.GetHistory( MyApplication.currentChildID );
    }

    public static Activity_History getInstance() {
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


    public void updateHistoryData(History body) {
        try {
            setupViewPager( body );
            tabLayout.setupWithViewPager( viewPager );

        } catch (Exception e) {

        }
    }
    private void setupViewPager(History body) {
        LASTKNOWN_POSITION = viewPager.getCurrentItem();
        ViewPagerAdapter_History adapter = new ViewPagerAdapter_History( getSupportFragmentManager(), body, this );
        viewPager.setAdapter( adapter );
        viewPager.setCurrentItem( LASTKNOWN_POSITION );
    }
}
