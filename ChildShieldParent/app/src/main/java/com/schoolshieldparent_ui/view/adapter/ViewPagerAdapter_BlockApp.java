package com.schoolshieldparent_ui.view.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.schoolshieldparent_ui.R;
import com.schoolshieldparent_ui.model.applications.Result;
import com.schoolshieldparent_ui.view.fragment.Fragment_BlockApps;

public class ViewPagerAdapter_BlockApp extends FragmentStatePagerAdapter {
    private final Activity activity;
    Result resultAllApps;


    public ViewPagerAdapter_BlockApp(FragmentManager manager, Result result, Activity activity) {
        super( manager );
        this.resultAllApps = null;
        this.resultAllApps = result;
        this.activity = activity;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new Fragment_BlockApps( resultAllApps.getLocked(), position );
        } else {
            return new Fragment_BlockApps( resultAllApps.getUnlocked(), position );
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return activity.getResources().getString( R.string.lockedApps ) + "(" + resultAllApps.getLocked().size() + ")";
        } else {
            return activity.getResources().getString( R.string.unlockedApps ) + "(" + resultAllApps.getUnlocked().size() + ")";
        }

    }
}