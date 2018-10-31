package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.notification.Result;
import com.schoolshieldparent_ui.view.fragment.Fragment_Notification;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final Activity activity;
    Result resultNotifications;

    public ViewPagerAdapter(FragmentManager manager, Result result, Activity activity) {
        super( manager );
        this.resultNotifications = result;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new Fragment_Notification(resultNotifications.getCurrentDay());
        } else if (position == 1) {
            return new Fragment_Notification(resultNotifications.getWeek());
        } else {
            return new Fragment_Notification(resultNotifications.getMonth());
        }

    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return activity.getResources().getString( R.string.today );
        } else if (position == 1) {
            return activity.getResources().getString( R.string.lastweek );
        } else {
            return activity.getResources().getString( R.string.lastMonth );
        }

    }
}