package com.schoolshieldchild_ui.app.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import android.annotation.TargetApi;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

public class UStats {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
	public static final String TAG = UStats.class.getSimpleName();

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@SuppressWarnings("ResourceType")
	public static void getStats(Context context) {
		UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
		int interval = UsageStatsManager.INTERVAL_YEARLY;
		Calendar calendar = Calendar.getInstance();
		long endTime = calendar.getTimeInMillis();
		calendar.add(Calendar.YEAR, -1);
		long startTime = calendar.getTimeInMillis();

		Log.d(TAG, "Range start:" + dateFormat.format(startTime));
		Log.d(TAG, "Range end:" + dateFormat.format(endTime));

		UsageEvents uEvents = usm.queryEvents(startTime, endTime);
		while (uEvents.hasNextEvent()) {
			UsageEvents.Event e = new UsageEvents.Event();
			uEvents.getNextEvent(e);

			if (e != null) {
				Log.d(TAG, "Event: " + e.getPackageName() + "\t" + e.getTimeStamp());
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static List<UsageStats> getUsageStatsList(Context context) {
		UsageStatsManager usm = getUsageStatsManager(context);
		Calendar calendar = Calendar.getInstance();
		long endTime = calendar.getTimeInMillis();
		calendar.add(Calendar.DAY_OF_WEEK, -1);
		long startTime = calendar.getTimeInMillis();

		List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime);
		return usageStatsList;
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static String printUsageStats(List<UsageStats> usageStatsList) {
		String package_name = "";
		// System.out.println("Start EEEE");
		/*
		 * 
		 * 
		 * 
		 * for (UsageStats u : usageStatsList){ Log.d(TAG, "Pkg: " +
		 * u.getPackageName() + "\t" + "ForegroundTime: " +
		 * u.getTotalTimeInForeground()) ; package_name=u.getPackageName();
		 * 
		 * }
		 */

		SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
		for (UsageStats usageStats : usageStatsList) {
			// mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
			mySortedMap.put(usageStats.getLastTimeStamp(), usageStats);
		}
		if (mySortedMap != null && !mySortedMap.isEmpty()) {
			package_name = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
		}
		return package_name;
	}

	public static String printCurrentUsageStatus(Context context) {
		return printUsageStats(getUsageStatsList(context));
	}

	@SuppressWarnings("ResourceType")
	private static UsageStatsManager getUsageStatsManager(Context context) {
		UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
		return usm;
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public static void onlyDummy(Context context) {
		UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
		long endTime = System.currentTimeMillis();
		long beginTime = endTime - 1000 * 60;

		// We get usage stats for the last minute
		List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginTime,
				endTime);

		// Sort the stats by the last time used
		if (stats != null) {
			SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
			for (UsageStats usageStats : stats) {
				mySortedMap.put(usageStats.getLastTimeStamp(), usageStats);
			}
			if (mySortedMap != null && !mySortedMap.isEmpty()) {
				String topActivity = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
				System.out.println("NNNN " + topActivity);
			}
		}
	}
}
