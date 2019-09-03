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
 * WakeBannerActivity
 * author:along
 * 2018/9/17 下午6:00
 *
 * 描述:首页banner跳转页面
 */

public class WakeBannerActivity extends BaseActivity {
    private WebView mWebActivityWakeBanner;


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mWebActivityWakeBanner = (WebView) findViewById(R.id.web_activity_wake_banner);


    }

//    @Override
//    public void onBackPressed() {
//        if (mWebActivityWakeBanner.canGoBack()) {
//            mWebActivityWakeBanner.goBack();
//            return;
//        }
//        super.onBackPressed();
//    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (!TextUtils.isEmpty(data)) {
            mWebActivityWakeBanner.loadUrl(data);
            mWebActivityWakeBanner.getSettings().setJavaScriptEnabled(true);
            mWebActivityWakeBanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            mWebActivityWakeBanner.getSettings().setDomStorageEnabled(true);
            //以下配置不适用8。0
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        mWvActivityTicketShop.getSettings().setSafeBrowsingEnabled(false);
//                    }
            mWebActivityWakeBanner.setWebViewClient(new WebViewClient(){
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
    public int getRootView() {
        return R.layout.activity_wake_banner;
    }
}
