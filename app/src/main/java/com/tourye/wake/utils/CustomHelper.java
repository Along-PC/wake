package com.tourye.wake.utils;

import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TakePhotoOptions;
import com.tourye.wake.R;


import java.io.File;


/**
 * - 支持通过相机拍照获取图片
 * - 支持从相册选择图片
 * - 支持从文件选择图片
 * - 支持多图选择
 * - 支持批量图片裁切
 * - 支持批量图片压缩
 * - 支持对图片进行压缩
 * - 支持对图片进行裁剪
 * - 支持对裁剪及压缩参数自定义
 * - 提供自带裁剪工具(可选)
 * - 支持智能选取及裁剪异常处理
 * - 支持因拍照Activity被回收后的自动恢复
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:4.0.0
 * 技术博文：http://www.devio.org
 * GitHub:https://github.com/crazycodeboy
 * Email:crazycodeboy@gmail.com
 */
public class CustomHelper {

    private static CustomHelper mCustomHelper;

    private CustomHelper() {

    }

    public static CustomHelper getInstance(){
        if (mCustomHelper==null) {
            mCustomHelper=new CustomHelper();
        }
        return mCustomHelper;
    }


    public void takeFromCamera(TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/wake/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        //相册
//        takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
        //拍照
        takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
    }

    public void takeFromGallery(TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/wake/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        //相册
        takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
        //拍照
//        takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }

    private void configCompress(TakePhoto takePhoto) {
        int maxSize = Integer.parseInt("102400");
        int width = Integer.parseInt("800");
        int height = Integer.parseInt("800");
        CompressConfig config;
        config = new CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(false)
                .create();
        takePhoto.onEnableCompress(config, false);


    }

    private CropOptions getCropOptions() {
        int height = Integer.parseInt("800");
        int width = Integer.parseInt("800");
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(width).setOutputY(height);
        builder.setAspectX(800).setAspectY(800);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

}
