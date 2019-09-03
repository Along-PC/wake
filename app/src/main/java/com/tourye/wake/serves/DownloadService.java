package com.tourye.wake.serves;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.beans.CheckUpdateBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * DownloadService
 * author:along
 * 2018/9/28 下午4:39
 * <p>
 * 描述:更新app服务
 */

public class DownloadService extends Service {
    private OnDownloadListener mOnDownloadListener;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(DownloadService.this, "下载失败，请检查网络", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(DownloadService.this, "网络请求失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(DownloadService.this, "下载成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private String mPath;
    private LayoutInflater mLayoutInflater;
    private NotificationManager notificationManager;

    public DownloadService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //文件下载文件夹
        mPath = this.getExternalFilesDir(null).getPath();

        if (intent!=null) {
            String url = intent.getStringExtra("url");

            initData();

            udpateApk(url);

        }

        return super.onStartCommand(intent, flags, startId);
    }

    //更新版本
    public void udpateApk(String url) {
        if (url==null) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().download(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File file = new File(mPath, "new.apk");
                        if (file.exists()) {
                            file.delete();
                        }
                        fos = new FileOutputStream(file);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            // 下载中
                            mOnDownloadListener.onDownloading(progress);
                        }
                        fos.flush();
                        // 下载完成
                        mOnDownloadListener.onDownloadSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                        mOnDownloadListener.onDownloadFailed();
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    mHandler.sendEmptyMessage(2);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (notificationManager!=null) {
            notificationManager.cancelAll();
        }
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if (notificationManager!=null) {
            notificationManager.cancelAll();
        }
    }

    private void initData() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final Notification.Builder mBuilder = new Notification.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10086", "wake", NotificationManager.IMPORTANCE_MAX);
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
            notificationManager.createNotificationChannel(channel);
            mBuilder.setSmallIcon(R.mipmap.icon_launcher);
            mBuilder.setChannelId("10086");
        } else {
            mBuilder.setSmallIcon(R.mipmap.icon_launcher);
            mBuilder.setVibrate(new long[]{0});
        }
        mBuilder.setProgress(100,0,false);

        mBuilder.setContentTitle("正在下载：");
        mBuilder.setContentText("进度：0");

        Notification build = mBuilder.build();
        notificationManager.notify(10, build);

        //进度监听
        mOnDownloadListener = new OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                mHandler.sendEmptyMessage(3);
                notificationManager.cancel(10);
                File file = new File(mPath, "new.apk");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= 24) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    Uri apkUri = FileProvider.getUriForFile(DownloadService.this, "com.tourye.wake.fileprovider", file);
                    //Granting Temporary Permissions to a URI
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    startActivity(intent);
                } else {
                    intent = new Intent(Intent.ACTION_VIEW);
                    //      安装完成后，启动app（源码中少了这句话）
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri uri = Uri.fromFile(file);
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity(intent);
                }

            }

            @Override
            public void onDownloading(int progress) {
                mBuilder.setContentTitle("正在下载：");
                mBuilder.setContentText("进度："+progress);
                mBuilder.setProgress(100,progress,false);
                Notification build = mBuilder.build();
                notificationManager.notify(10, build);

            }

            @Override
            public void onDownloadFailed() {

            }
        };
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}
