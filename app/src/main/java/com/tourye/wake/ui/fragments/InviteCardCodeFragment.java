package com.tourye.wake.ui.fragments;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.base.BaseFragment;
import com.tourye.wake.beans.InviteCardBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longlongren on 2018/8/15.
 * <p>
 * introduce:邀请卡邀请码页面
 */

public class InviteCardCodeFragment extends BaseInviteFragment {
    private ImageView mImgInviteCardCode;


    @Override
    public void initView(View view) {
        mImgInviteCardCode = (ImageView) view.findViewById(R.id.img_invite_card_code);

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initData();
                    break;
            }
        }
    };

    @Override
    public void initData() {
        Map<String,String> map=new HashMap<>();
        map.put("type","1");
        HttpUtils.getInstance().get(Constants.INVITE_CARD_DATA, map, new HttpCallback<InviteCardBean>() {
            @Override
            public void onSuccessExecute(InviteCardBean inviteCardBean) {
                String data = inviteCardBean.getData();
                if (TextUtils.isEmpty(data)) {
                    return;
                }
                if ("true".equals(data)) {
                    mHandler.sendEmptyMessageDelayed(1,1000);

                }else{
                    mDownloadUrl=data;
                    Glide.with(BaseApplication.mApplicationContext).load(data).placeholder(R.drawable.icon_invite_card_code).into(mImgInviteCardCode);
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_invite_card_code;
    }

    @Override
    public String getImageUrl() {
        return mDownloadUrl;
    }
}
