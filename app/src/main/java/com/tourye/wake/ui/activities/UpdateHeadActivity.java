package com.tourye.wake.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.beans.UpdateUserBean;
import com.tourye.wake.beans.UploadHeadBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.utils.CameraUtils;
import com.tourye.wake.utils.CustomHelper;
import com.tourye.wake.views.dialogs.ModifyHeadDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * UpdateHeadActivity
 * author:along
 * 2018/8/21 下午3:09
 * <p>
 * 描述:修改头像页面
 */

public class UpdateHeadActivity extends TakePhotoActivity implements View.OnClickListener {
    private ImageView mTvActivityUpdateHeadClose;
    private ImageView mImgActivityUpdateHead;
    private ImageView mImgActivityUpdateHeadCamera;

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_head);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        mTvActivityUpdateHeadClose = (ImageView) findViewById(R.id.tv_activity_update_head_close);
        mImgActivityUpdateHead = (ImageView) findViewById(R.id.img_activity_update_head);
        mImgActivityUpdateHeadCamera = (ImageView) findViewById(R.id.img_activity_update_head_camera);

        mActivity=this;

        mTvActivityUpdateHeadClose.setOnClickListener(this);
        mImgActivityUpdateHeadCamera.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            String headUrl = intent.getStringExtra("data");
            Glide.with(BaseApplication.mApplicationContext).load(headUrl).error(R.drawable.icon_default_head).into(mImgActivityUpdateHead);
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        ArrayList<TImage> images = result.getImages();
        if (images!=null && images.size()>0) {
            TImage tImage = images.get(0);
            String compressPath = tImage.getCompressPath();
            String originalPath = tImage.getOriginalPath();
            if (BuildConfig.DEBUG) Log.d("UpdateHeadActivity", "压缩地址："+compressPath);
            if (BuildConfig.DEBUG) Log.d("UpdateHeadActivity", "原始地址："+originalPath);

            uploadHead(compressPath);
            Glide.with(BaseApplication.mApplicationContext).load(new File(compressPath)).error(R.drawable.icon_default_head).into(mImgActivityUpdateHead);
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    /**
     * 请求权限
     */
    public void requestPermissionRx() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            showCameraDialog();
                        } else {
                            showPermissionDialog();
                        }
                    }
                });
    }

    /**
     * 拍照对话框
     */
    public void showCameraDialog() {
        ModifyHeadDialog modifyHeadDialog = new ModifyHeadDialog(this);
        modifyHeadDialog.setCameraCallback(new ModifyHeadDialog.CameraCallback() {
            @Override
            public void openCamera() {
//                CameraUtils.openCamera(UpdateHeadActivity.this);
                CustomHelper.getInstance().takeFromCamera(getTakePhoto());
            }

            @Override
            public void openGallery() {
//                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
//                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(albumIntent, CameraUtils.GET_PHOTO);
                CustomHelper.getInstance().takeFromGallery(getTakePhoto());
            }
        });
        modifyHeadDialog.show();
    }

    /**
     * 缺少权限时的弹框
     */
    public void showPermissionDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("缺少拍照或存储相关权限，请前往手机设置开启");
        builder.setTitle("权限");
        builder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                }
                startActivity(localIntent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_update_head_close:
                finish();
                break;
            case R.id.img_activity_update_head_camera:
//                requestPermission();
                requestPermissionRx();

                break;
        }
    }

    /**
     * 上传新的头像
     */
    public void uploadHead(String address) {
        //base64转换图片
        FileInputStream in = null;//必须用fileinputStream，不能用inputstream，否则available()为0
        byte[] data = null;
        //读取图片字节数组
        try {
            File file = new File(address);
            if (BuildConfig.DEBUG) Log.d("UpdateHeadActivity", "file.exists():" + file.exists());
            if (BuildConfig.DEBUG) Log.d("UpdateHeadActivity", "file.length():" + file.length());

            in = new FileInputStream(file);
            int available = in.available();
            Log.e("along", available + "");
//            data = new byte[in.available()];
            data = new byte[(int) file.length()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = new String(Base64.encode(data, Base64.DEFAULT));
        if (BuildConfig.DEBUG) Log.d("UpdateHeadActivity", "data.length:" + data.length);
        if (BuildConfig.DEBUG) Log.d("UpdateHeadActivity", s);

        Map<String, String> map = new HashMap<>();
        map.put("type", "avatar");
        map.put("file", s);
        HttpUtils.getInstance().post(Constants.UPLOAD_HEAD, map, new HttpCallback<UploadHeadBean>() {
            @Override
            public void onSuccessExecute(UploadHeadBean uploadHeadBean) {
                if (uploadHeadBean.getStatus() == 0) {
                    int id = uploadHeadBean.getData();
                    updateHead(id);
                }

            }
        });

    }

    /**
     * 更新服务器头像状态
     */
    public void updateHead(int id) {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "");
        map.put("avatar", id + "");
        HttpUtils.getInstance().post(Constants.UPDATE_USER_INFO, map, new HttpCallback<UpdateUserBean>() {
            @Override
            public void onSuccessExecute(UpdateUserBean updateUserBean) {
                int status = updateUserBean.getStatus();
                if (status == 0) {
                    Toast.makeText(mActivity, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mActivity, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
