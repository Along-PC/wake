package com.tourye.wake.ui.activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;

/**
 * BusinessTeamworkActivity
 * author:along
 * 2018/8/23 下午3:08
 *
 * 描述:商务合作页面
 */

public class BusinessTeamworkActivity extends BaseActivity {
    private TextView mTvBusinessTeamworkWechat;
    private TextView mTvBusinessTeamworkCopy;


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("商务合作");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTvBusinessTeamworkWechat = (TextView) findViewById(R.id.tv_business_teamwork_wechat);
        mTvBusinessTeamworkCopy = (TextView) findViewById(R.id.tv_business_teamwork_copy);
        mTvBusinessTeamworkCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(mTvBusinessTeamworkWechat.getText());
                Toast.makeText(mActivity, "复制成功,可以去粘贴了", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public int getRootView() {
        return R.layout.activity_business_teamwork;
    }
}
