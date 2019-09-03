package com.tourye.wake.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.tac.TACApplication;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.beans.Commonbean;
import com.tourye.wake.beans.PushBean;
import com.tourye.wake.beans.UserTokenBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.utils.NetStateUtils;
import com.tourye.wake.utils.NoneNetUtils;
import com.tourye.wake.utils.PermissionDialogUtil;
import com.tourye.wake.utils.SaveUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * SplashActivity
 * author:along
 * 2018/8/21 下午2:40
 *
 * 描述:启动页
 */

public class SplashActivity extends Activity {

    private Activity mActivity;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String authorization = SaveUtil.getString("Authorization", "");
                    if (TextUtils.isEmpty(authorization)) {
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        int netState = NetStateUtils.getNetState(mActivity);
                        if (netState==NetStateUtils.NETWORK_STATE_NONE) {
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            updateToken();
                        }

                    }
                    break;
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        setContentView(R.layout.activity_splash);

        mHandler.sendEmptyMessageDelayed(0,100);

    }

    //更新用户token
    public void updateToken() {
        //更新用户token
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.UPDATE_TOKEN, map, new HttpCallback<UserTokenBean>() {

            @Override
            public void onFailureExecute() {
                super.onFailureExecute();
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSuccessExecute(UserTokenBean userTokenBean) {
                String token = userTokenBean.getData();
                if (token != null) {
                    SaveUtil.putString("Authorization", token);
                    long timeMillis = System.currentTimeMillis();
                }
                sendDeviceId();
            }

            @Override
            public void onErrorResponse(Response response) {
                super.onErrorResponse(response);
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 更新推送用的device_id
     */
    public void sendDeviceId() {
        Map<String, String> map = new HashMap<>();
        String device_id = TACApplication.getDeviceId();//信鸽推送token
        map.put("device_id", device_id);
        map.put("type", "android");
        HttpUtils.getInstance().post(Constants.UPDATE_DEVICE_ID, map, new HttpCallback<Commonbean>() {

            @Override
            public void onFailureExecute() {
                super.onFailureExecute();
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSuccessExecute(Commonbean commonbean) {
                int status = commonbean.getStatus();
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onErrorResponse(Response response) {
                Intent intent = new Intent(mActivity, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
