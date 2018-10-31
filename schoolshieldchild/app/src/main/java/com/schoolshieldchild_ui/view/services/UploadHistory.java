package com.schoolshieldchild_ui.view.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.schoolshieldchild_ui.model.historyprp.HistoryPrp;

import java.util.ArrayList;


public class UploadHistory extends Service {
    private static UploadHistory instance;
    Handler mHandler;
    private int RESTART_UPLOAD_DURATION = (60 * 1000) * 3;
    ArrayList<HistoryPrp> historyList = new ArrayList<>();
    int currentIndex = 0;

    public static UploadHistory getinstance() {
        return instance;
    }

    public void useHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            currentIndex = 0;
            historyList.clear();
            getSystemSearches();
            uploadHistoryToServer(currentIndex);
            mHandler.postDelayed(this, RESTART_UPLOAD_DURATION);
        }
    };

    private void uploadHistoryToServer(final int position) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 200);
    }


    private void getSystemSearches() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        useHandler();
        return START_STICKY;
    }
}
