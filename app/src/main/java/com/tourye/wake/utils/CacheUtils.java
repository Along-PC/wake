package com.tourye.wake.utils;

import android.content.Context;
import android.os.Environment;

import com.tourye.wake.base.BaseApplication;

/**
 *
 * @ClassName:   CacheUtils
 *
 * @Author:   along
 *
 * @Description:    缓存工具类
 *
 * @CreateDate:   2019/4/16 2:09 PM
 *
 */
public class CacheUtils {
    private static CacheUtils mCacheUtils;

    private CacheUtils(){

    }

    public static CacheUtils getInstance(){
        if (mCacheUtils==null) {
            mCacheUtils=new CacheUtils();
        }
        return mCacheUtils;
    }

    /**
     * 获取app缓存路径
     * @return
     */
    private String getCachePath(){
        String cachePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            cachePath = BaseApplication.mApplicationContext.getExternalCacheDir().getPath() ;
        }else {
            //外部存储不可用
            cachePath = BaseApplication.mApplicationContext.getCacheDir().getPath() ;
        }
        return cachePath ;
    }

}
