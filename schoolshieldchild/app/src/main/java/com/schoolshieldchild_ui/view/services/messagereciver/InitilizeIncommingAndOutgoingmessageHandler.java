package com.schoolshieldchild_ui.view.services.messagereciver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.presenter.WebServiceResult;
import com.tuenti.smsradar.Sms;
import com.tuenti.smsradar.SmsListener;
import com.tuenti.smsradar.SmsRadar;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mb on 22/8/16.
 */
public class InitilizeIncommingAndOutgoingmessageHandler extends Service {
    private Handler mHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            mHandler = new Handler();
            mHandler.postDelayed(mRunnable, 1000);
        } catch (NullPointerException e) {

        }

        return START_STICKY;
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {

            startSMSService();
            mHandler.postDelayed(this, 1000 * 10);
        }
    };

    private void startSMSService() {


        try {
            SmsRadar.stopSmsRadarService(getApplicationContext());
        } catch (NullPointerException e) {

        }

        SmsRadar.initializeSmsRadarService(getApplicationContext(), new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {
                try {
                    if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(Long.parseLong(sms.getDate()));
                        String date = DateFormat.format("Y-m-d H:i:s", cal).toString();
                        WebServiceResult.saveMessage(SharedPref.getString(MyApplication.STUDENT_ID), sms.getMsg(), sms.getAddress(), "OUT", date);
                    }
                } catch (NullPointerException e) {

                } catch (Exception e) {

                }
            }

            @Override
            public void onSmsReceived(Sms sms) {

                try {
                    if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(Long.parseLong(sms.getDate()));
                        String date = DateFormat.format("Y-m-d H:i:s", cal).toString();
                        WebServiceResult.saveMessage(SharedPref.getString(MyApplication.STUDENT_ID), sms.getMsg(), sms.getAddress(), "IN", date);
                    }
                } catch (NullPointerException e) {

                } catch (Exception e) {

                }
            }
        });
    }


}
