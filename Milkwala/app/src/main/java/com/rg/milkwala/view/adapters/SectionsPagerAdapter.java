package com.rg.milkwala.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rg.milkwala.view.fragments.DebitCard;
import com.rg.milkwala.view.fragments.NetBanking;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new DebitCard();
        } else {
            return new NetBanking();
        }
    }
    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Debit/Credit Card";
            case 1:
                return "Net Banking";
        }
        return null;
    }
}