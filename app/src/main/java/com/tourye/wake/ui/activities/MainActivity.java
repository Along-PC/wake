package com.tourye.wake.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tac.TACApplication;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.CheckUpdateBean;
import com.tourye.wake.beans.Commonbean;
import com.tourye.wake.beans.UserTokenBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.serves.DownloadService;
import com.tourye.wake.ui.adapters.MainActivityVpAdapter;
import com.tourye.wake.ui.fragments.InfluenceFragment;
import com.tourye.wake.ui.fragments.MineFragment;
import com.tourye.wake.ui.fragments.WakeFragment;
import com.tourye.wake.utils.NetStateUtils;
import com.tourye.wake.utils.NoneNetUtils;
import com.tourye.wake.utils.PermissionDialogUtil;
import com.tourye.wake.utils.SaveUtil;
import com.tourye.wake.views.dialogs.UpdateAppDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * MainActivity
 * author:along
 * 2018/8/27 上午9:46
 * <p>
 * 描述:主页-----启动模式：singletask
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mVpActivityMain;
    private TextView mTvActivityLoginMorning;
    private TextView mTvActivityLoginInfluence;
    private TextView mTvActivityLoginMine;
    private TextView mCurrentTv;//当前选中底部导航
    private FragmentManager mSupportFragmentManager;
    private WakeFragment mWakeFragment;
    private InfluenceFragment mInfluenceFragment;
    private MineFragment mMineFragment;
    private ArrayList<Fragment> mFragments;
    private MainActivityVpAdapter mMainActivityVpAdapter;
    private RelativeLayout mRlActivityLoginMorning;
    private RelativeLayout mRlActivityLoginInfluence;
    private RelativeLayout mRlActivityLoginMine;


    @Override
    public void initView() {
        mVpActivityMain = (ViewPager) findViewById(R.id.vp_activity_main);
        mTvActivityLoginMorning = (TextView) findViewById(R.id.tv_activity_login_morning);
        mTvActivityLoginInfluence = (TextView) findViewById(R.id.tv_activity_login_influence);
        mTvActivityLoginMine = (TextView) findViewById(R.id.tv_activity_login_mine);
        mRlActivityLoginMorning = (RelativeLayout) findViewById(R.id.rl_activity_login_morning);
        mRlActivityLoginInfluence = (RelativeLayout) findViewById(R.id.rl_activity_login_influence);
        mRlActivityLoginMine = (RelativeLayout) findViewById(R.id.rl_activity_login_mine);


        mSupportFragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        mWakeFragment = new WakeFragment();
        mInfluenceFragment = new InfluenceFragment();
        mMineFragment = new MineFragment();

        mFragments = new ArrayList<>();
        mFragments.add(mWakeFragment);
        mFragments.add(mInfluenceFragment);
        mFragments.add(mMineFragment);

        mVpActivityMain.setOffscreenPageLimit(2);
        mMainActivityVpAdapter = new MainActivityVpAdapter(mSupportFragmentManager, mFragments);
        mVpActivityMain.setAdapter(mMainActivityVpAdapter);

        mRlActivityLoginMorning.setOnClickListener(this);
        mRlActivityLoginInfluence.setOnClickListener(this);
        mRlActivityLoginMine.setOnClickListener(this);

        mTvActivityLoginMorning.setSelected(true);
        mCurrentTv = mTvActivityLoginMorning;
        mVpActivityMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mCurrentTv.setSelected(false);
                    mTvActivityLoginMorning.setSelected(true);
                    mCurrentTv = mTvActivityLoginMorning;
                } else if (position == 1) {
                    mCurrentTv.setSelected(false);
                    mTvActivityLoginInfluence.setSelected(true);
                    mCurrentTv = mTvActivityLoginInfluence;
                } else if (position == 2) {
                    mCurrentTv.setSelected(false);
                    mTvActivityLoginMine.setSelected(true);
                    mCurrentTv = mTvActivityLoginMine;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void initData() {

        //开启监测更新服务
        Intent intent = getIntent();
        String isUpdate = intent.getStringExtra("isUpdate");
        if (TextUtils.isEmpty(isUpdate)) {
            checkUpdate();
        }else{

        }


        //推送需要的权限
//        if (Build.VERSION.SDK_INT >= 23) {
//            int i = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION);
//            if (i!= PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)){
//                    PermissionDialogUtil.showPermissionDialog(mActivity,"缺少位置信息相关权限，请前往手机设置开启");
//                }else{
//                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 10086);
//                }
//            }
//        }

    }

    //检查是否需要版本更新
    private void checkUpdate() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "android");
        HttpUtils.getInstance().getNoAuth(Constants.CHECK_UPDATE, map, new HttpCallback<CheckUpdateBean>() {
            @Override
            public void onSuccessExecute(CheckUpdateBean checkUpdateBean) {
                CheckUpdateBean.DataBean data = checkUpdateBean.getData();
                if (data == null) {
                    return;
                }
                int minVersion = data.getBaseline();
                int currentVersion = data.getNewest();
                int versionCode = BuildConfig.VERSION_CODE;
                final String packageX = data.getPackageX();
                if (versionCode < currentVersion) {
                    UpdateAppDialog updateAppDialog = new UpdateAppDialog(mActivity);
                    if (versionCode < minVersion) {
                        updateAppDialog.forceShowDialog(new UpdateAppDialog.ForceUpdateCallback() {
                            @Override
                            public void update() {
                                //开启监测更新服务
                                Intent intent = new Intent(mActivity, DownloadService.class);
                                intent.putExtra("url",packageX);
                                startService(intent);
                            }
                        });
                    } else {
                        updateAppDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                dialogInterface.dismiss();
                            }
                        });
                        updateAppDialog.showDialog(new UpdateAppDialog.UpdateCallback() {
                            @Override
                            public void update() {
                                //开启监测更新服务
                                Intent intent = new Intent(mActivity, DownloadService.class);
                                intent.putExtra("url",packageX);
                                startService(intent);
                            }
                        });
                    }
                }
            }
        });

    }


    @Override
    public int getRootView() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_activity_login_morning:
                mCurrentTv.setSelected(false);
                mTvActivityLoginMorning.setSelected(true);
                mCurrentTv = mTvActivityLoginMorning;
                mVpActivityMain.setCurrentItem(0,false);
                break;
            case R.id.rl_activity_login_influence:
                mCurrentTv.setSelected(false);
                mTvActivityLoginInfluence.setSelected(true);
                mCurrentTv = mTvActivityLoginInfluence;
                mVpActivityMain.setCurrentItem(1,false);
                break;
            case R.id.rl_activity_login_mine:
                mCurrentTv.setSelected(false);
                mTvActivityLoginMine.setSelected(true);
                mCurrentTv = mTvActivityLoginMine;
                mVpActivityMain.setCurrentItem(2,false);
                break;
        }
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    /**
     * 由于页面单例模式，所以启动之后再次跳转都会走这个方法
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        int type = intent.getIntExtra("type", 998);
        switch (type) {
            case 1:
                mVpActivityMain.setCurrentItem(0);
                break;
            case 2:
                mVpActivityMain.setCurrentItem(1);
                break;
        }

    }

}
