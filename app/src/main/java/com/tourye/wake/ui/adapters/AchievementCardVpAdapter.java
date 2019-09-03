package com.tourye.wake.ui.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:
 */

public class AchievementCardVpAdapter extends PagerAdapter{


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
