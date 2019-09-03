package com.tourye.wake.ui.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.TicketShopBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.utils.AndroidBug5497Workaround;

import java.util.HashMap;
import java.util.Map;

/**
 * TicketShopActivity
 * author:along
 * 2018/8/29 下午6:27
 *
 * 描述:局票商城页面
 */

public class TicketShopActivity extends BaseActivity {
    private WebView mWvActivityTicketShop;


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("局票商城");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWvActivityTicketShop.canGoBack()) {
                    mWvActivityTicketShop.goBack();
                    return;
                }
                finish();
            }
        });
        mWvActivityTicketShop = (WebView) findViewById(R.id.wv_activity_ticket_shop);


    }

    @Override
    public void initData() {
        AndroidBug5497Workaround.assistActivity(this);

        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.TICKET_SHOP, map, new HttpCallback<TicketShopBean>() {
            @Override
            public void onSuccessExecute(TicketShopBean ticketShopBean) {

                final String url = ticketShopBean.getData();
                if (!TextUtils.isEmpty(url)) {
                    mWvActivityTicketShop.loadUrl(url);
                    mWvActivityTicketShop.getSettings().setJavaScriptEnabled(true);
                    mWvActivityTicketShop.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    mWvActivityTicketShop.getSettings().setDomStorageEnabled(true);
                    //以下配置不适用8。0
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        mWvActivityTicketShop.getSettings().setSafeBrowsingEnabled(false);
//                    }
                    mWvActivityTicketShop.setWebViewClient(new WebViewClient(){
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
        });
    }

    @Override
    public void onBackPressed() {
        if (mWvActivityTicketShop.canGoBack()) {
            mWvActivityTicketShop.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public int getRootView() {
        return R.layout.activity_ticket_shop;
    }
}
