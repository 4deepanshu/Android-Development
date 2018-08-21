package com.rg.milkwala;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.multidex.MultiDex;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.rg.milkwala.controller.utils.Dialogs;
import com.rg.milkwala.controller.utils.SharedPref;
import com.rg.milkwala.model.user.Orders;
import com.rg.milkwala.model.user.product.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobile on 1/9/2017.
 */

public class MyApplication extends Application {


    public static Product SELECTED_PRODUCT;
    public static List<Orders> CART_ITEMS = new ArrayList<>();
    public static PayPalConfiguration config;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Dialogs();
        new SharedPref(this);


        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId("AfdR-wWgvQQu4DJmeY8SfNijZNKEToj_0zvjEboW_M6Mr5B7xA5Enp2zV81BZwEbGyJRiRKGmrfC6ubI")
                .merchantName("Hipster Store")
                .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
                .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    }
}
