package com.schoolshieldparent_ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.app.MyApplication;
import com.schoolshieldparent_ui.controller.helper.custom_functions.CheckInternetConnection;
import com.schoolshieldparent_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldparent_ui.controller.utils.CustomToast;

public class Activity_Splash extends AppCompatActivity implements CheckInternetConnection.ConnectivityReceiverListener {
    static Activity_Splash instance;
    boolean loggedIn = false;
    private boolean isConnected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        getSupportActionBar().hide();
        checkInternetConnection();


    }

    private void checkInternetConnection() {
        boolean isConnected = CheckInternetConnection.isConnected();
        loadSplash( isConnected );
    }


    public static Activity_Splash getInstance() {
        return instance;
    }

    private void loadSplash(final boolean isConnected) {

        new Handler().postDelayed( new Runnable() {

            @Override
            public void run() {
                if (isConnected == false) {
                    CustomToast.showToast(Activity_Splash.this, getString(R.string.nointernetConnection));
                    finish();
                } else {
                    if (loggedIn == true) {
                        startActivity(
                                new Intent( Activity_Splash.this, Activity_Login.class ).addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP ) );
                        finish();
                        overridePendingTransition( 0, 0 );

                    } else {
                        if (!SharedPref.getString( MyApplication.PARENT_ID ).equalsIgnoreCase( "" )) {
                            startActivity(
                                    new Intent( Activity_Splash.this, Activity_Home.class ).addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP ) );
                            finish();
                            overridePendingTransition( 0, 0 );
                        } else {
                            startActivity(
                                    new Intent( Activity_Splash.this, Activity_Login.class ).addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP ) );
                            finish();
                            overridePendingTransition( 0, 0 );

                        }
                    }
                }

            }
        }, 3000 );


    }


    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener( this );
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        this.isConnected = isConnected;
        loadSplash( this.isConnected );

    }


}
