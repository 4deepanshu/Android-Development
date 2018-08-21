package com.rg.milkwala.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.rg.milkwala.R;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.Constants;
import com.rg.milkwala.view.controls.ButtonRegular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.button_getstarted)
    ButtonRegular button_getstarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPref.getInstance().getString(Constants.USER_ID).length() > 0) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivtiy.class));
                    finish();
                }
            }
        },5000);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick(R.id.button_getstarted)
    public void navigateToLogin() {

    }
}
