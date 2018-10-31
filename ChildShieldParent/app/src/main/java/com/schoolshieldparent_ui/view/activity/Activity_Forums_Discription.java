package com.schoolshieldparent_ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.schoolshieldparent_ui.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Forums_Discription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forums_discription );
        ButterKnife.bind( this );
        getSupportActionBar().hide();
    }

    @OnClick(R.id.buttonNext)
    public void leavePage() {
        startActivity( new Intent( this, Activity_ForumsAllApps.class ) );
        finish();
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
