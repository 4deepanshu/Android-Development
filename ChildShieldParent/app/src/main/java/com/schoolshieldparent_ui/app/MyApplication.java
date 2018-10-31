package com.schoolshieldparent_ui.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.controller.helper.custom_functions.CheckInternetConnection;
import com.schoolshieldparent_ui.presenter.WebServiceConnection;
import com.schoolshieldparent_ui.pushnotification.NotificationListener;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;


public class MyApplication extends MultiDexApplication {

    public static MyApplication mInstance;

    //region Constant Variables
    public static String DEVICE_TOKEN = "TOKEN";
    public static String PARENT_ID = "PARENT_ID";
    public static int currentChildID = -1;
    public static String currentChildName = "";
    public static boolean isSubscriptionActivated = false;
    public static DisplayImageOptions options;
    public static DisplayImageOptions optionsGallrey;
    public static String NOTIFICATIONS = "";
    public List<String> allNotifications = new ArrayList<>();
    private NotificationListener nReceiver;

    //endregion

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        WebServiceConnection webServiceConnection = new WebServiceConnection();
        mInstance = this;
        initImageLoader(getApplicationContext());

    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(CheckInternetConnection.ConnectivityReceiverListener listener) {
        CheckInternetConnection.connectivityReceiverListener = listener;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .build();
        ImageLoader.getInstance().init(config);

        options = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(0))
                .imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true).cacheOnDisk(true)
                .resetViewBeforeLoading(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        optionsGallrey = new DisplayImageOptions.Builder().showImageForEmptyUri(mInstance.getResources().getDrawable(R.drawable.gallery_img_paceholder))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .delayBeforeLoading(1000)
                .displayer(new SimpleBitmapDisplayer()) // default
                .build();

    }
}