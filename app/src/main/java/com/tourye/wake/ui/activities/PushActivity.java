package com.tourye.wake.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.tourye.wake.R;
import com.tourye.wake.beans.PushBean;
import com.tourye.wake.utils.SaveUtil;

/**
 * PushActivity
 * author:along
 * 2018/9/6 下午3:04
 * <p>
 * 描述:信鸽推送默认跳转界面
 */

public class PushActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        // 判断是否从推送通知栏打开的-----这个一定需要在这个位置，在oncreate方法特别靠前的位置
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            String authorization = SaveUtil.getString("Authorization", "");
            if (!TextUtils.isEmpty(authorization)) {
                //customcontent如果服务器不设置，平台默认返回一个空的数组！！！！
                String customContent = click.getCustomContent();
                if (!TextUtils.isEmpty(customContent)) {
                    try {

                        Gson gson = new Gson();
                        PushBean pushBean = gson.fromJson(customContent, PushBean.class);
                        String type = pushBean.getType();
                        if (!TextUtils.isEmpty(type)) {
                            switch (type) {
                                case "100"://首页
                                    Intent intent = new Intent(this, MainActivity.class);
                                    intent.putExtra("type", 1);
                                    startActivity(intent);
                                    break;
                                case "101"://局票页
                                    Intent boardTicketIntent = new Intent(this, BoardTicketActivity.class);
                                    startActivity(boardTicketIntent);
                                    break;
                                case "102"://影响页
                                    Intent mainIntent = new Intent(this, MainActivity.class);
                                    mainIntent.putExtra("type", 2);
                                    startActivity(mainIntent);
                                    break;
                                default:
                                    finish();
                                    break;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                    }

                }
            } else {
//                    Intent intent = new Intent(mActivity, LoginActivity.class);
//                    mActivity.startActivity(intent);
            }
        }
        finish();
    }
}
