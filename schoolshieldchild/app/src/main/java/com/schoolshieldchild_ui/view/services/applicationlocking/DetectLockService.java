package com.schoolshieldchild_ui.view.services.applicationlocking;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.schoolshieldchild_ui.app.MyApplication;
import com.schoolshieldchild_ui.controller.helper.prefs.SharedPref;
import com.schoolshieldchild_ui.model.lockprp.LockPrp;
import com.schoolshieldchild_ui.presenter.WebServiceResult;
import com.schoolshieldchild_ui.view.database.DataBaseHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by mb on 27/7/16.
 */
public class DetectLockService extends Service {

    Context context;
    Handler handler = new Handler();
    String runningApplicationPackageName = "";
    String[] defaultLaunchingApplications = new String[]{"com.android.systemui"};
    public static boolean isLockViewVisible = false;

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
                }
                else {
                    ActivityManager am = (ActivityManager) getBaseContext().getSystemService(ACTIVITY_SERVICE);
                    currentApp = am.getRunningTasks(1).get(0).topActivity.getPackageName();

                }
                checkApplicationLocked(currentApp);
                handler.postDelayed(this, 50);
            }
        };
        handler.postDelayed(r,50);
        return START_STICKY;
    }

    private void checkApplicationLocked(String currentApp) {

        System.out.println("CURRNT TASK   "+currentApp);

        if (!currentApp.equalsIgnoreCase("com.google.android.gms")) {

            if (!currentApp.equalsIgnoreCase(runningApplicationPackageName)) {

                LockScreenView.stopService();
                isLockViewVisible = false;
                runningApplicationPackageName = currentApp;

                boolean isLockExist = false;
                DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                List<LockPrp> applicationLocks = dataBaseHandler.getApplicationLocks(currentApp);

                for (int index = 0; index < applicationLocks.size(); index++) {
                    if (applicationLocks.get(index).getLOCK_PERMANENT().equalsIgnoreCase("1")) {
                        isLockExist = true;
                        break;
                    } else {
                        SimpleDateFormat dayFormater = new SimpleDateFormat("EEE");
                        Date d = new Date();
                        String currDay = dayFormater.format(d);
                        if (compareDates(applicationLocks.get(index).getLOCK_START_TIME(), applicationLocks.get(index).getLOCK_END_TIME())) {
                            if (applicationLocks.get(index).getLOCK_DAYS().contains(currDay)) {
                                isLockExist = true;
                                break;

                            }
                        }
                    }
                }

                if (isLockExist == true) {
                    if (isLockViewVisible == false) {
                        startService(new Intent(getApplicationContext(), LockScreenView.class));
                        isLockViewVisible = true;
                        runningApplicationPackageName = currentApp;
                    }
                }
            }
        }


        if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
            MyApplication.getInstance().startBackgroundServices();

        }

    }


    private Date date;
    private Date dateCompareOne;
    private Date dateCompareTwo;
    public static final String inputFormat = "hh : mm / a";
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

    private boolean compareDates(String compareStringOne, String compareStringTwo) {
        Calendar now = Calendar.getInstance();
        int hour1 = now.get(Calendar.HOUR);
        int minute1 = now.get(Calendar.MINUTE);
        String AM_PM = "";

        String timeString = "";
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour == 0) {
            AM_PM = "AM";
        } else if (hour < 12) {
            AM_PM = "AM";
        } else if (hour == 12) {
            AM_PM = "PM";
        } else {
            AM_PM = "PM";
        }
        date = parseDateCurrnet(hour1 + " : " + minute1 + " / " + AM_PM);
        dateCompareOne = parseDate1(compareStringOne);
        dateCompareTwo = parseDate2(compareStringTwo);

        if (dateCompareOne.before(date) && dateCompareTwo.after(date)) {

            return true;
        }
        return false;
    }


    private Date parseDateCurrnet(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }


    private Date parseDate1(String date) {

        try {
            Date dateUpdate = inputParser.parse(date);
            final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

            long curTimeInMs = dateUpdate.getTime();
            Date afterAddingMins = new Date(curTimeInMs - (1 * ONE_MINUTE_IN_MILLIS));
            return afterAddingMins;
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

    private Date parseDate2(String date) {

        try {
            Date dateUpdate = inputParser.parse(date);
            final long ONE_MINUTE_IN_MILLIS = 0;// millisecs

            long curTimeInMs = dateUpdate.getTime();
            Date afterAddingMins = new Date(curTimeInMs + (1 * ONE_MINUTE_IN_MILLIS));
            return afterAddingMins;
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
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