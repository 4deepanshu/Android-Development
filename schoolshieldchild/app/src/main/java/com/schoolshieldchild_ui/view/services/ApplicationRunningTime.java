package com.schoolshieldchild_ui.view.services;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.presenter.WebServiceResult;

import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Created by mb on 28/7/16.
 */
public class ApplicationRunningTime extends Service {

    Context context;
    Handler handler = new Handler();
    String[] defaultLaunchingApplications = new String[]{"com.android.systemui"};
    public static boolean isLockViewVisible = false;

    String trackingApplicationPackage="";
    int totalTimeTracked=0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();

        Runnable r = new Runnable() {
            public void run() {
                String currentApp = "";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    UsageStatsManager usm = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
                    long time = System.currentTimeMillis();
                    List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                            time - 1000 * 1000, time);
                    if (appList != null && appList.size() > 0) {
                        SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                        for (UsageStats usageStats : appList) {
                            mySortedMap.put(usageStats.getLastTimeUsed(),
                                    usageStats);
                        }
                        if (mySortedMap != null && !mySortedMap.isEmpty()) {
                            currentApp = mySortedMap.get(
                                    mySortedMap.lastKey()).getPackageName();
                        }
                    }
                } else {
                    ActivityManager am = (ActivityManager) getBaseContext().getSystemService(ACTIVITY_SERVICE);
                    currentApp = am.getRunningTasks(1).get(0).topActivity.getPackageName();
                }

                canTrackedForTime(currentApp);

                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);
        return START_STICKY;

    }

    private void canTrackedForTime(String currentApp) {

        if(!currentApp.equalsIgnoreCase(trackingApplicationPackage) && !trackingApplicationPackage.equalsIgnoreCase("")) {
            System.out.println("Upload Time To server "+totalTimeTracked);
            WebServiceResult.addNotifications(SharedPref.getString(MyApplication.STUDENT_ID),MyApplication.TYPE_TIME,totalTimeTracked+"","",trackingApplicationPackage);
            totalTimeTracked=0;
            trackingApplicationPackage="";
            stopTimertask();
        }

        if(currentApp.equalsIgnoreCase(getHomeLauncher()) || currentApp.equalsIgnoreCase("") || currentApp.equalsIgnoreCase("com.android.systemui"))
        {
            totalTimeTracked=0;
            trackingApplicationPackage="";
        }
        else
        {
            startTimer(currentApp);
        }
    }


    Timer timer;
    TimerTask timerTask;



    public void startTimer(String currentApp) {
        if (timer == null)
        {
            trackingApplicationPackage=currentApp;
            timer = new Timer();
            initializeTimerTask();
            timer.schedule(timerTask, 1000, 1000);
         }
    }

    public void stopTimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        totalTimeTracked++;
                    }
                });
            }
        };
    }




    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    private String getHomeLauncher() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;
        return currentHomePackage;
    }
}
