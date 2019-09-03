package com.tourye.wake.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.utils.SaveUtil;
import com.tourye.wake.views.dialogs.QuitDialog;

/**
 * FunctionSetActivity
 * author:along
 * 2018/8/23 下午3:32
 *
 * 描述:功能设置页面
 */

public class FunctionSetActivity extends BaseActivity {
    private LinearLayout mLlFunctionSetRemind;
    private TextView mTvFunctionSetVersion;
    private TextView mTvFunctionSetQuit;



    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("功能设置");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTvFunctionSetQuit = (TextView) findViewById(R.id.tv_function_set_quit);


        mLlFunctionSetRemind = (LinearLayout) findViewById(R.id.ll_function_set_remind);
        mTvFunctionSetVersion = (TextView) findViewById(R.id.tv_function_set_version);
        mLlFunctionSetRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity,RemindSetActivity.class));
            }
        });
        mTvFunctionSetQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("退出")
                        .setMessage("确定退出吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SaveUtil.putString("Authorization","");
                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                //跳转之前清空activity栈中的activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

    }



    @Override
    public void initData() {
        PackageManager pm = mActivity.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(getPackageName(), 0);
            String versionName = pi.versionName;
//            int versionCode = pi.versionCode;
            mTvFunctionSetVersion.setText("v"+versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getRootView() {
        return R.layout.activity_function_set;
    }
}
