package com.tourye.wake.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;

/**
 * MessageDetailActivity
 * author:along
 * 2018/8/29 下午2:00
 *
 * 描述:消息详情
 */

public class MessageDetailActivity extends BaseActivity {
    private WebView mWebActivityMessageDetail;


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("消息详情");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mWebActivityMessageDetail = (WebView) findViewById(R.id.web_activity_message_detail);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        final String url = intent.getStringExtra("data");
        if (!TextUtils.isEmpty(url)) {
            mWebActivityMessageDetail.loadUrl(url);
            mWebActivityMessageDetail.getSettings().setJavaScriptEnabled(true);
            mWebActivityMessageDetail.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            mWebActivityMessageDetail.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    //7。0之上需要更改设置，要不webview不显示
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.loadUrl(request.getUrl().toString());
                    } else {
                        view.loadUrl(request.toString());
                    }
                    return true;
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        if (mWebActivityMessageDetail.canGoBack()) {
            mWebActivityMessageDetail.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public int getRootView() {
        return R.layout.activity_message_detail;
    }
}
