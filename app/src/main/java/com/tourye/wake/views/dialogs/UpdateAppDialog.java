package com.tourye.wake.views.dialogs;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.wake.R;
import com.tourye.wake.serves.DownloadService;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by longlongren on 2018/10/10.
 * <p>
 * introduce:更新app弹窗
 */

public class UpdateAppDialog extends Dialog {
    private final Context mContext;
    private TextView mTvDialogUpdateAppCancel;
    private View mTvDialogUpdateAppLine;
    private TextView mTvDialogUpdateAppCertain;
    private boolean mExitProcess=false;//点击返回键是否退出程序


    public UpdateAppDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_update_app);

        mTvDialogUpdateAppCancel = (TextView) findViewById(R.id.tv_dialog_update_app_cancel);
        mTvDialogUpdateAppLine = (View) findViewById(R.id.tv_dialog_update_app_line);
        mTvDialogUpdateAppCertain = (TextView) findViewById(R.id.tv_dialog_update_app_certain);
    }

    public void forceShowDialog(final ForceUpdateCallback forceUpdateCallback){
        mExitProcess=true;
        setCancelable(false);
        mTvDialogUpdateAppCancel.setVisibility(View.GONE);
        mTvDialogUpdateAppLine.setVisibility(View.GONE);
        final boolean[] isShowMessage = {false};
        mTvDialogUpdateAppCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowMessage[0]) {
                    Toast.makeText(mContext, "已经开始下载了", Toast.LENGTH_SHORT).show();
                }else{
                    forceUpdateCallback.update();
                    isShowMessage[0] =true;
                }
            }
        });
        show();
    }

    public void showDialog(final UpdateCallback updateCallback){
        mTvDialogUpdateAppCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mTvDialogUpdateAppCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean running = isRunning(mContext, DownloadService.class.getName());
                if (running) {
                    Toast.makeText(mContext, "正在下载中", Toast.LENGTH_SHORT).show();
                }else{
                    updateCallback.update();
                }
                dismiss();
            }
        });
        show();
    }



    @Override
    public void onBackPressed() {
        if (mExitProcess) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            mContext.startActivity(intent);
        }else{
            super.onBackPressed();
        }
    }

    /**
     * 判断服务是否启动
     * @param context
     * @param className
     * @return
     */
    public boolean isRunning(Context context,String className){
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 强制更新接口
     */
    public interface ForceUpdateCallback{
        public void update();
    }

    /**
     * 非强制更新接口
     */
    public interface UpdateCallback{
        public void update();
    }

}
