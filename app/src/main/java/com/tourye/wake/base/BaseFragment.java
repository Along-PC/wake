package com.tourye.wake.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.wake.R;

/**
 * Created by longlongren on 2018/8/10.
 * <p>
 * introduce:
 */

public abstract class BaseFragment extends Fragment {

    //标题栏控件
    protected ImageView mImgReturn;
    protected TextView mTvTitle;
    protected TextView mTvCertain;
    protected ImageView mImgCertain;


    public Activity mActivity;

    public static final String TAG = "BaseFragment";
    protected LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = this.getActivity();
        mInflater = inflater;

        //模板模式完成初始化
        if (isNeedTitle()) {
            LinearLayout linearLayout = new LinearLayout(mActivity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
            View inflateTitle = inflater.inflate(R.layout.title_top, linearLayout, false);
            mImgReturn = (ImageView) inflateTitle.findViewById(R.id.img_return);
            mTvTitle = (TextView) inflateTitle.findViewById(R.id.tv_title);
            mTvCertain = (TextView) inflateTitle.findViewById(R.id.tv_certain);
            mImgCertain = (ImageView) inflateTitle.findViewById(R.id.img_certain);

            View inflateContent = inflater.inflate(getRootView(), linearLayout, false);

            linearLayout.addView(inflateTitle);
            linearLayout.addView(inflateContent);


            initView(linearLayout);
            initData();

            return linearLayout;
        } else {
            View inflateContent = inflater.inflate(getRootView(), container, false);


            initView(inflateContent);
            initData();
            return inflateContent;
        }

    }

    //初始化控件
    public abstract void initView(View view);

    //初始化数据
    public abstract void initData();

    //获取页面布局
    public abstract int getRootView();

    //是否需要头部标题
    public boolean isNeedTitle() {
        return true;
    }
}
