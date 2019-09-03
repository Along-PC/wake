package com.tourye.wake.ui.fragments;

import com.tourye.wake.base.BaseFragment;

/**
 * Created by longlongren on 2018/8/20.
 * <p>
 * introduce:邀请卡fagment基类
 */

public abstract class BaseInviteFragment extends BaseFragment {
    protected String mDownloadUrl="";//分享中下载的图片

    public abstract String getImageUrl();
}
