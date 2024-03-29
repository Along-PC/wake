package com.tourye.wake.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/10.
 * <p>
 * introduce:
 */

public class MainActivityVpAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments=new ArrayList<>();
    public MainActivityVpAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
