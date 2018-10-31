package com.schoolshieldparent_ui.controller.helper.custom_functions;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.schoolshieldparent_ui.app.MyApplication;

/**
 * Created by root on 22/8/16.
 */
public class CheckInternetConnection extends BroadcastReceiver {

    public static ConnectivityReceiverListener connectivityReceiverListener;

    public CheckInternetConnection() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged( isConnected );
        }



    }


    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) MyApplication.getInstance().getApplicationContext()
                .getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }


    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

    public class NotifySMSReceived extends Activity {
        private static final String LOG_TAG = "SMSReceiver";
        public static final int NOTIFICATION_ID_RECEIVED = 0x1221;
        static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );

            IntentFilter filter = new IntentFilter( ACTION );
            this.registerReceiver( broadcastReceiver, filter );

        }

        CheckInternetConnection broadcastReceiver = new CheckInternetConnection();


    }
}
