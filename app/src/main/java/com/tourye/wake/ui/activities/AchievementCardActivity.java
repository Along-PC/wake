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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.beans.AchievementCardBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.utils.PermissionDialogUtil;
import com.tourye.wake.views.dialogs.ShareDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * AchievementCardActivity
 * author:along
 * 2018/8/20 上午10:36
 * <p>
 * 描述:成就卡页面
 */

public class AchievementCardActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgActivityAchievementCard;
    private boolean mCanLoad = true;
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
                case 998:
//                    if (BuildConfig.DEBUG) Log.d("AchievementCardActivity", "998~~~~~~");
                    initData();
                    break;
            }
        }
    };

    private String mImgUrl = "";//分享中下载的图片


    @Override
    public void initView() {

        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("成就卡");
        mImgCertain.setBackgroundResource(R.drawable.icon_share);


        mImgActivityAchievementCard = (ImageView) findViewById(R.id.img_activity_achievement_card);
        mImgReturn.setOnClickListener(this);
        mImgCertain.setOnClickListener(this);


    }

    @Override
    public void initData() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.ACHIEVEMENT_CARD_DATA, map, new HttpCallback<AchievementCardBean>() {
            @Override
            public void onSuccessExecute(AchievementCardBean achievementCardBean) {
                String data = achievementCardBean.getData();
                if (TextUtils.isEmpty(data)) {
                    return;
                }
//                if (BuildConfig.DEBUG) Log.d("AchievementCardActivity", "网络请求~~~~~~~~");
                if ("true".equals(data)) {
                    mHandler.sendEmptyMessageDelayed(998, 1000);
                } else {
                    mImgUrl = data;
                    Glide.with(BaseApplication.mApplicationContext).load(data).into(mImgActivityAchievementCard);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCanLoad = false;
        mHandler.removeCallbacksAndMessages(998);
    }

    @Override
    public int getRootView() {
        return R.layout.activity_achievement_card;
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
                        } else {
                            PermissionDialogUtil.showPermissionDialog(mActivity, "缺少存储权限，请前往手机设置开启");
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
                    File imageFile = new File(file, time + ".jpeg");
                    fileOutputStream = new FileOutputStream(imageFile);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    //保存图片后发送广播通知更新数据库
                    Uri uri = Uri.fromFile(imageFile);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_certain:
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.showDialogTemp("/user/achieve", new ShareDialog.DownloadCallback() {
                    @Override
                    public void download() {
                        getPermission(mImgUrl);
                    }
                });
                break;
            case R.id.img_return:
                finish();
                break;
        }
    }
}
