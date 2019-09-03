package com.tourye.wake.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.ui.adapters.InviteCardAdapter;
import com.tourye.wake.ui.fragments.BaseInviteFragment;
import com.tourye.wake.ui.fragments.InviteCardCodeFragment;
import com.tourye.wake.ui.fragments.InviteCardContinueFragment;
import com.tourye.wake.ui.fragments.InviteCardWakeFragment;
import com.tourye.wake.utils.PermissionDialogUtil;
import com.tourye.wake.views.dialogs.ShareDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * InviteCardActivity
 * author:along
 * 2018/8/15 上午9:47
 *
 * 描述:邀请卡页面
 */

public class InviteCardActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mVpActivityInviteCard;
    private RadioButton mRbActivityInviteCardCode;
    private RadioButton mRbActivityInviteCardContinue;
    private RadioButton mRbActivityInviteCardWake;
    private FragmentManager mSupportFragmentManager;
    private BaseInviteFragment mInviteCardCodeFragment;
    private BaseInviteFragment mInviteCardContinueFragment;
    private BaseInviteFragment mInviteCardWakeFragment;
    private List<BaseInviteFragment> mFragmentList=new ArrayList<>();

    private String mDownloadUrl="";//分享中要下载的图片
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mActivity, "图片成功下载至相册应用wake文件夹下", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mActivity, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("邀请卡");
        mImgCertain.setBackgroundResource(R.drawable.icon_share);

        mVpActivityInviteCard = (ViewPager) findViewById(R.id.vp_activity_invite_card);
        mRbActivityInviteCardCode = (RadioButton) findViewById(R.id.rb_activity_invite_card_code);
        mRbActivityInviteCardContinue = (RadioButton) findViewById(R.id.rb_activity_invite_card_continue);
        mRbActivityInviteCardWake = (RadioButton) findViewById(R.id.rb_activity_invite_card_wake);

        mImgReturn.setOnClickListener(this);
        mImgCertain.setOnClickListener(this);

    }

    @Override
    public void initData() {
        mSupportFragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();
        mInviteCardCodeFragment = new InviteCardCodeFragment();
        mInviteCardContinueFragment = new InviteCardContinueFragment();
        mInviteCardWakeFragment = new InviteCardWakeFragment();
        mFragmentList.add(mInviteCardCodeFragment);
        mFragmentList.add(mInviteCardContinueFragment);
        mFragmentList.add(mInviteCardWakeFragment);

        InviteCardAdapter inviteCardAdapter = new InviteCardAdapter(mSupportFragmentManager, mFragmentList);
        mVpActivityInviteCard.setAdapter(inviteCardAdapter);
        mVpActivityInviteCard.setCurrentItem(0);
        mVpActivityInviteCard.setOffscreenPageLimit(1);

        mVpActivityInviteCard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        mRbActivityInviteCardCode.setChecked(true);
                        break;
                    case 1:
                        mRbActivityInviteCardContinue.setChecked(true);
                        break;
                    case 2:
                        mRbActivityInviteCardWake.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 获取权限
     */
    public void getPermission(final String url) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            downloadImage(url);
                        }else{
                            PermissionDialogUtil.showPermissionDialog(mActivity,"缺少存储权限，请前往手机设置开启");
                        }
                    }
                });

    }

    /**
     * 下载图片
     *
     * @param url
     */
    public void downloadImage(final String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        new Thread() {
            @Override
            public void run() {
                final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wake";
                final File file = new File(path);
                FileOutputStream fileOutputStream = null;
                InputStream inputStream = null;
                if (!file.exists()) {
                    file.mkdirs();
                }
                try {
                    URL downloadUrl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) downloadUrl.openConnection();
                    inputStream = httpURLConnection.getInputStream();
                    long time = System.currentTimeMillis();
                    File along = new File(file, time+".jpeg");
                    fileOutputStream = new FileOutputStream(along);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    //保存图片后发送广播通知更新数据库
                    Uri uri = Uri.fromFile(along);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(2);
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }.start();


    }


    @Override
    public int getRootView() {
        return R.layout.activity_invite_card;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                finish();
                break;
            case R.id.img_certain:
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.showDialogTemp("/user/invite_card", new ShareDialog.DownloadCallback() {
                    @Override
                    public void download() {
                        int currentItem = mVpActivityInviteCard.getCurrentItem();
                        mDownloadUrl=mFragmentList.get(currentItem).getImageUrl();
                        getPermission(mDownloadUrl);
                    }
                });
                break;
        }
    }
}
