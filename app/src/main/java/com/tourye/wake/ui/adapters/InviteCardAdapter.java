package com.tourye.wake.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tourye.wake.ui.fragments.BaseInviteFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/15.
 * <p>
 * introduce:邀请卡页面适配器
 */

public class InviteCardAdapter extends FragmentPagerAdapter {
    private List<BaseInviteFragment> mFragments=new ArrayList<>();
    public InviteCardAdapter(FragmentManager fm,List<BaseInviteFragment> fragments) {
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
