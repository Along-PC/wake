package com.tourye.wake.ui.activities;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.beans.PrivilegeConfigBean;
import com.tourye.wake.beans.UserAccountBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.views.dialogs.ShareDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * BoardTicketPrivilegeActivity
 * author:along
 * 2018/8/15 下午5:09
 *
 * 描述:局票特权页面
 */

public class BoardTicketPrivilegeActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvTicketPrivilegeTitle;
    private TextView mTvTicketPrivilegeIntro;
    private TextView mTvTicketPrivilegeCount;
    private TextView mTvTicketPrivilegeExchange;
    private TextView mTvTicketPrivilegeRemaining;
    private ImageView mImgTicketPrivilegeCenter;
    private int mCurrentPoint;//当前局票数量
    private int mPrice;//特权价格
    private int mPrivilegeCount;//当前特权的数量
    private PrivilegeConfigBean.DataBean mData;


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("我的局票");
        mImgCertain.setBackgroundResource(R.drawable.icon_share);

        mTvTicketPrivilegeTitle = (TextView) findViewById(R.id.tv_ticket_privilege_title);
        mTvTicketPrivilegeIntro = (TextView) findViewById(R.id.tv_ticket_privilege_intro);
        mTvTicketPrivilegeCount = (TextView) findViewById(R.id.tv_ticket_privilege_count);
        mTvTicketPrivilegeExchange = (TextView) findViewById(R.id.tv_ticket_privilege_exchange);
        mTvTicketPrivilegeRemaining = (TextView) findViewById(R.id.tv_ticket_privilege_remaining);
        mImgTicketPrivilegeCenter = (ImageView) findViewById(R.id.img_ticket_privilege_center);

        mImgReturn.setOnClickListener(this);
        mImgCertain.setOnClickListener(this);


    }

    @Override
    public void initData() {


        getUserAccountData();


    }


    /**
     * 获取上个页面传过来的数据
     */
    private void getPrivilegeData() {
        Intent intent = getIntent();
        if (intent!=null) {
            mData = (PrivilegeConfigBean.DataBean) intent.getSerializableExtra("data");
            mTvTicketPrivilegeTitle.setText(mData.getName());
            mTvTicketPrivilegeCount.setText(mData.getCount()+"");
            mTvTicketPrivilegeIntro.setText(mData.getDesc());
            mTvTicketPrivilegeExchange.setText(mData.getPrice()+"局票兑换");
            mPrivilegeCount = mData.getCount();
            mPrice = mData.getPrice();
            if (mPrice>=mCurrentPoint) {
                mTvTicketPrivilegeExchange.setText("局票不足");
                mTvTicketPrivilegeExchange.setSelected(true);
                Glide.with(BaseApplication.mApplicationContext).load(mData.getCover_disable()).into(mImgTicketPrivilegeCenter);
                mTvTicketPrivilegeExchange.setOnClickListener(null);
            }else{
                mTvTicketPrivilegeExchange.setText(mData.getPrice()+"局票兑换");
                mTvTicketPrivilegeExchange.setSelected(false);
                Glide.with(BaseApplication.mApplicationContext).load(mData.getCover()).into(mImgTicketPrivilegeCenter);
                mTvTicketPrivilegeExchange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exchangePrivilege(mData.getType());
                    }
                });
            }
        }
    }

    /**
     *
     * @param type 当前特权类型
     */
    public void exchangePrivilege(String type){
        Map<String,String> map=new HashMap<>();
        map.put("type",type);
        HttpUtils.getInstance().post(Constants.PRIVILEGE_EXCHANGE, map, new HttpCallback<PrivilegeConfigBean>() {
            @Override
            public void onSuccessExecute(PrivilegeConfigBean privilegeConfigBean) {
                int status = privilegeConfigBean.getStatus();
                if (status==0) {
                    mPrivilegeCount+=1;
                    mTvTicketPrivilegeCount.setText(mPrivilegeCount +"");
                    mCurrentPoint-=mPrice;
                    mTvTicketPrivilegeRemaining.setText("剩余局票："+mCurrentPoint);
                    if (mData==null) {
                        return;
                    }
                    if (mPrice>=mCurrentPoint) {
                        mTvTicketPrivilegeExchange.setSelected(true);
                        mTvTicketPrivilegeExchange.setText("局票不足");
                        Glide.with(BaseApplication.mApplicationContext).load(mData.getCover_disable()).into(mImgTicketPrivilegeCenter);
                        mTvTicketPrivilegeExchange.setOnClickListener(null);
                    }else{
                        mTvTicketPrivilegeExchange.setSelected(false);
                        mTvTicketPrivilegeExchange.setText(mData.getPrice()+"局票兑换");
                        Glide.with(BaseApplication.mApplicationContext).load(mData.getCover()).into(mImgTicketPrivilegeCenter);
                        mTvTicketPrivilegeExchange.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                exchangePrivilege(mData.getType());
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 获取用户账户信息
     */
    private void getUserAccountData() {

        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.USER_ACCOUNT_DATA, map, new HttpCallback<UserAccountBean>() {
            @Override
            public void onSuccessExecute(UserAccountBean userAccountBean) {
                UserAccountBean.DataBean data = userAccountBean.getData();
                if (data==null) {
                    return;
                }
                mCurrentPoint = data.getPoint();
                mTvTicketPrivilegeRemaining.setText("剩余局票："+mCurrentPoint);

                getPrivilegeData();
            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.activity_board_ticket_privilege;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                finish();
                break;
            case R.id.img_certain:
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.showDialogTemp("/point/card");
                break;
        }
    }
}
