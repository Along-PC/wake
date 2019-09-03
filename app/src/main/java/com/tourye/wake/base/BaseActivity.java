package com.tourye.wake.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.wake.R;
import com.tourye.wake.utils.NetStateUtils;
import com.tourye.wake.utils.NoneNetUtils;


public abstract class BaseActivity extends FragmentActivity {

    protected LayoutInflater mLayoutInflater;

    //标题栏控件
    protected ImageView mImgReturn;
    protected TextView mTvTitle;
    protected TextView mTvCertain;
    protected ImageView mImgCertain;


    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去除系统标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }else {
//
//            //沉浸式---通知栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            //沉浸式---状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//
//      沉浸式---通知栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//      沉浸式---状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //android6.0以后可以对状态栏文字颜色和图标进行修改
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mLayoutInflater = this.getLayoutInflater();

        //模板模式完成初始化
        if (isNeedTitle()) {
            LinearLayout linearLayout=new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(layoutParams);
            View inflateTitle = mLayoutInflater.inflate(R.layout.title_top, linearLayout, false);
            mImgReturn = (ImageView) inflateTitle.findViewById(R.id.img_return);
            mTvTitle = (TextView) inflateTitle.findViewById(R.id.tv_title);
            mTvCertain = (TextView) inflateTitle.findViewById(R.id.tv_certain);
            mImgCertain = (ImageView) inflateTitle.findViewById(R.id.img_certain);
            View inflateContent = mLayoutInflater.inflate(getRootView(), linearLayout, false);

            linearLayout.addView(inflateTitle);
            linearLayout.addView(inflateContent);
            setContentView(linearLayout);
        }else{
            setContentView(getRootView());
        }
        mActivity=this;

        initView();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkNet();
    }

    public void checkNet(){
        int netState = NetStateUtils.getNetState(mActivity);
        if (netState==NetStateUtils.NETWORK_STATE_NONE) {
            NoneNetUtils.showDialog(mActivity);
        }
    }

    /**
     * 禁止修改字体大小
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    //初始化控件
    public abstract void initView();

    //初始化数据
    public abstract void initData();

    //获取页面布局
    public abstract int getRootView();

    //是否需要头部标题
    public boolean isNeedTitle(){
        return true;
    }

}
