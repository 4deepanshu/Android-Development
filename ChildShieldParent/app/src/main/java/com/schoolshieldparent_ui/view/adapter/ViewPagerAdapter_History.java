package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.history.History;
import com.schoolshieldparent_ui.view.fragment.Fragment_History;

public class ViewPagerAdapter_History extends FragmentPagerAdapter {
    private final Activity activity;
    History resultHistory;

    public ViewPagerAdapter_History(FragmentManager manager, History resultHistory, Activity activity) {
        super( manager );
        this.resultHistory = resultHistory;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new Fragment_History( resultHistory.getResult().getCurrentDay() );
        } else if (position == 1) {
            return new Fragment_History( resultHistory.getResult().getWeek() );
        } else {
            return new Fragment_History( resultHistory.getResult().getMonth() );
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