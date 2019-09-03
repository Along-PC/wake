package com.tourye.wake.ui.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.ResurrectionCardBean;
import com.tourye.wake.beans.ResurrectionInviteBean;
import com.tourye.wake.beans.ResurrectionRecordBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.ui.adapters.ResurrectionInviteAdapter;
import com.tourye.wake.ui.adapters.ResurrectionRecordAdapter;
import com.tourye.wake.utils.DensityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * ResurrectionCardActivity
 * author:along
 * 2018/8/14 下午3:38
 * <p>
 * 描述:复活卡页面
 */

public class ResurrectionCardActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvActivityResurrectionCardLine;
    private TextView mTvActivityResurrectionCardAvailable;
    private TextView mTvActivityResurrectionCardInvite;
    private TextView mTvActivityResurrectionCardRecord;
    private ListView mListActivityResurrectionCard;
    private TextView mTvCurrent;//当前选中导航栏
    private TextView mTvActivityResurrectionCardBottom;
    private TextView mTvActivityResurrectionCardShare;
    private SmartRefreshLayout mRefreshLayoutActivityResurrectionCard;
    private View footer;

    private List<ResurrectionInviteBean.DataBean> mInviteData =new ArrayList<>();
    private List<ResurrectionRecordBean.DataBean> mRecordData=new ArrayList<>();

    private int offset=0;
    private int count=10;
    private ResurrectionInviteAdapter mResurrectionInviteAdapter;


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("复活卡");

        mTvActivityResurrectionCardLine = (TextView) findViewById(R.id.tv_activity_resurrection_card_line);
        mTvActivityResurrectionCardAvailable = (TextView) findViewById(R.id.tv_activity_resurrection_card_available);
        mTvActivityResurrectionCardInvite = (TextView) findViewById(R.id.tv_activity_resurrection_card_invite);
        mTvActivityResurrectionCardRecord = (TextView) findViewById(R.id.tv_activity_resurrection_card_record);
        mListActivityResurrectionCard = (ListView) findViewById(R.id.list_activity_resurrection_card);
        mTvActivityResurrectionCardBottom = (TextView) findViewById(R.id.tv_activity_resurrection_card_bottom);
        mTvActivityResurrectionCardShare = (TextView) findViewById(R.id.tv_activity_resurrection_card_share);
        mRefreshLayoutActivityResurrectionCard = (SmartRefreshLayout) findViewById(R.id.refreshLayout_activity_resurrection_card);

        //listview的脚布局
        footer = mLayoutInflater.inflate(R.layout.footer_item_fragment_morning, mListActivityResurrectionCard, false);


        String text="成功邀请好友参与得<font color='#F6752D'>复活卡</font>";
        mTvActivityResurrectionCardBottom.setText(Html.fromHtml(text));

        //动态设置导航线的间距
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int margin = widthPixels / 2 - DensityUtils.dp2px(mActivity, 70);
        margin = margin / 2;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mTvActivityResurrectionCardLine.getLayoutParams();
        marginLayoutParams.setMargins(margin, 0, 0, 0);
        mTvActivityResurrectionCardLine.setLayoutParams(marginLayoutParams);

        mTvCurrent = mTvActivityResurrectionCardInvite;
        mTvActivityResurrectionCardInvite.setSelected(true);
        mTvActivityResurrectionCardInvite.setOnClickListener(this);
        mTvActivityResurrectionCardRecord.setOnClickListener(this);
        mImgReturn.setOnClickListener(this);
        mTvActivityResurrectionCardShare.setOnClickListener(this);

        mRefreshLayoutActivityResurrectionCard.setEnableAutoLoadMore(false);
        mRefreshLayoutActivityResurrectionCard.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                offset=0;
                if (mTvCurrent==mTvActivityResurrectionCardInvite) {
                    getInviteData(true);
                }else if(mTvCurrent==mTvActivityResurrectionCardRecord){
                    getRecordData(true);
                }
            }
        });
        mRefreshLayoutActivityResurrectionCard.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                offset+=10;
                if (mTvCurrent==mTvActivityResurrectionCardInvite) {
                    getInviteData(false);
                }else if(mTvCurrent==mTvActivityResurrectionCardRecord){
                    getRecordData(false);
                }
            }
        });
    }

    @Override
    public void initData() {
        getResurrectionData();
        getInviteData(true);
//        getRecordData(false);
    }

    /**
     * 获取复活卡数据
     */
    private void getResurrectionData() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.RECURRECTION_CARD_NUM, map, new HttpCallback<ResurrectionCardBean>() {
            @Override
            public void onSuccessExecute(ResurrectionCardBean resurrectionCardBean) {
                ResurrectionCardBean.DataBean data = resurrectionCardBean.getData();
                if (data==null) {
                    return;
                }
                mTvActivityResurrectionCardAvailable.setText("可用复活卡x"+data.getCard_count());
                mTvActivityResurrectionCardRecord.setText("卡片记录("+data.getRevive_count()+")");
                mTvActivityResurrectionCardInvite.setText("邀请记录("+data.getInvite_count()+")");

            }
        });
    }


    /**
     * 获取卡片记录列表
     * @param isRefresh 是否执行刷新操作
     */
    private void getRecordData(final boolean isRefresh) {
        if (isRefresh) {
            int footerViewsCount = mListActivityResurrectionCard.getFooterViewsCount();
            mRefreshLayoutActivityResurrectionCard.setEnableLoadMore(true);
            if (footerViewsCount!=0) {
                mListActivityResurrectionCard.removeFooterView(footer);
            }
        }

        Map<String, String> map = new HashMap<>();
        map.put("offset",offset+"");
        map.put("count",count+"");
        HttpUtils.getInstance().get(Constants.RECURRECTION_CARD_RECORD, map, new HttpCallback<ResurrectionRecordBean>() {

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isRefresh) {
                    mRefreshLayoutActivityResurrectionCard.finishRefresh();
                }else{
                    mRefreshLayoutActivityResurrectionCard.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(ResurrectionRecordBean resurrectionRecordBean) {
                List<ResurrectionRecordBean.DataBean> data = resurrectionRecordBean.getData();
                int status = resurrectionRecordBean.getStatus();
                if (isRefresh) {
                    if (mRefreshLayoutActivityResurrectionCard.isRefreshing()) {
                        mRefreshLayoutActivityResurrectionCard.finishRefresh();
                    }
                }else{
                    mRefreshLayoutActivityResurrectionCard.finishLoadMore();
                }
                if (data==null || data.size()==0) {
                    mListActivityResurrectionCard.addFooterView(footer);
                    mRefreshLayoutActivityResurrectionCard.setEnableLoadMore(false);
                    mRecordData=new ArrayList<>();
                    ResurrectionRecordAdapter resurrectionRecordAdapter = new ResurrectionRecordAdapter(mActivity, mRecordData);
                    mListActivityResurrectionCard.setAdapter(resurrectionRecordAdapter);
                    return;
                }
                if (isRefresh) {
                    mRecordData=data;
                }else{
                    mRecordData.addAll(data);
                }
                if (data.size()<10) {
                    mListActivityResurrectionCard.addFooterView(footer);
                    mRefreshLayoutActivityResurrectionCard.setEnableLoadMore(false);
                }
                ResurrectionRecordAdapter resurrectionRecordAdapter = new ResurrectionRecordAdapter(mActivity, mRecordData);
                mListActivityResurrectionCard.setAdapter(resurrectionRecordAdapter);
            }
        });
    }


    /**
     * 获取邀请列表信息
     * @param isRefresh 是否执行刷新操作
     */
    private void getInviteData(final boolean isRefresh) {
        //刷新时去掉底部脚布局
        if (isRefresh) {
            int footerViewsCount = mListActivityResurrectionCard.getFooterViewsCount();
            mRefreshLayoutActivityResurrectionCard.setEnableLoadMore(true);
            if (footerViewsCount!=0) {
                mListActivityResurrectionCard.removeFooterView(footer);
            }
        }

        Map<String, String> map = new HashMap<>();
        map.put("offset",offset+"");
        map.put("count",count+"");
        HttpUtils.getInstance().get(Constants.RECURRECTION_CARD_INVITE, map, new HttpCallback<ResurrectionInviteBean>() {

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isRefresh) {
                    if (mRefreshLayoutActivityResurrectionCard.isRefreshing()) {
                        mRefreshLayoutActivityResurrectionCard.finishRefresh();
                    }
                }else{
                    mRefreshLayoutActivityResurrectionCard.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(ResurrectionInviteBean resurrectionInviteBean) {
                List<ResurrectionInviteBean.DataBean> data = resurrectionInviteBean.getData();
                int status = resurrectionInviteBean.getStatus();
                if (isRefresh) {
                    if (mRefreshLayoutActivityResurrectionCard.isRefreshing()) {
                        mRefreshLayoutActivityResurrectionCard.finishRefresh();
                    }
                }else{
                    mRefreshLayoutActivityResurrectionCard.finishLoadMore();
                }
                if (data==null || data.size()==0) {
                    mListActivityResurrectionCard.addFooterView(footer);
                    mRefreshLayoutActivityResurrectionCard.setEnableLoadMore(false);
                    mInviteData=new ArrayList<>();
                    mResurrectionInviteAdapter = new ResurrectionInviteAdapter(mActivity, mInviteData);
                    mListActivityResurrectionCard.setAdapter(mResurrectionInviteAdapter);
                    return;
                }

                if (isRefresh) {
                    mInviteData=data;
                }else{
                    mInviteData.addAll(data);
                }

                if (data.size()<10) {
                    mListActivityResurrectionCard.addFooterView(footer);
                    mRefreshLayoutActivityResurrectionCard.setEnableLoadMore(false);
                }
                mResurrectionInviteAdapter = new ResurrectionInviteAdapter(mActivity, mInviteData);
                mListActivityResurrectionCard.setAdapter(mResurrectionInviteAdapter);

            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_resurrection_card;
    }

    public void translateLine(){
        float toX = mTvCurrent.getX();
        float toY = mTvCurrent.getY();
        float fromX = mTvActivityResurrectionCardLine.getX();
        float fromY = mTvActivityResurrectionCardLine.getY();

        ObjectAnimator tranlationX = ObjectAnimator.ofFloat(mTvActivityResurrectionCardLine, "translationX", fromX, toX);
        tranlationX.setDuration(200);
        tranlationX.start();
    }

    @Override
    public void onClick(View view) {
        offset=0;
        switch (view.getId()) {
            case R.id.tv_activity_resurrection_card_invite:
                mTvCurrent.setSelected(false);
                mTvActivityResurrectionCardInvite.setSelected(true);
                mTvCurrent = mTvActivityResurrectionCardInvite;
                getInviteData(true);
                translateLine();
                break;
            case R.id.tv_activity_resurrection_card_record:
                mTvCurrent.setSelected(false);
                mTvActivityResurrectionCardRecord.setSelected(true);
                mTvCurrent = mTvActivityResurrectionCardRecord;
                translateLine();
                getRecordData(true);
                break;
            case R.id.img_return:
                finish();
                break;
            case R.id.tv_activity_resurrection_card_share:
                Intent intent = new Intent(mActivity, InviteCardActivity.class);
                startActivity(intent);
                break;
        }



    }
}
