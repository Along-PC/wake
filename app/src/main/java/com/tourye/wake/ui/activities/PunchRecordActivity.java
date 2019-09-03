package com.tourye.wake.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.PunchRecordBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.ui.adapters.PunchRecordAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
/**
 * PunchRecordActivity
 * author:along
 * 2018/8/23 上午10:50
 *
 * 描述:打卡记录页面
 */

public class PunchRecordActivity extends BaseActivity {
    private TextView mTvActivityPunchRecordDays;
    private SmartRefreshLayout mRefreshLayoutActivityPunchRecord;
    private ListView mListActivityPunchRecord;

    private int mOffset=0;//开始条数
    private int mCount=10;//请求多少条
    private PunchRecordAdapter mPunchRecordAdapter;
    private List<PunchRecordBean.DataBean> mDataBeans;//列表数据


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("打卡记录");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTvActivityPunchRecordDays = (TextView) findViewById(R.id.tv_activity_punch_record_days);
        mRefreshLayoutActivityPunchRecord = (SmartRefreshLayout) findViewById(R.id.refreshLayout_activity_punch_record);
        mListActivityPunchRecord = (ListView) findViewById(R.id.list_activity_punch_record);



        mRefreshLayoutActivityPunchRecord.setEnableAutoLoadMore(false);
        mRefreshLayoutActivityPunchRecord.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mOffset=0;
                getPunchList(true);

            }
        });
        mRefreshLayoutActivityPunchRecord.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mOffset+=10;
                getPunchList(false);
            }
        });

    }

    public void getPunchList(final boolean isRefresh){
        Map<String,String> map=new HashMap<>();
        map.put("offset",mOffset+"");
        map.put("count",mCount+"");
        HttpUtils.getInstance().get(Constants.PUNCH_RECORD, map, new HttpCallback<PunchRecordBean>() {

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isRefresh) {
                    if (mRefreshLayoutActivityPunchRecord.isRefreshing()) {
                        mRefreshLayoutActivityPunchRecord.finishRefresh();
                    }
                }else{
                    mRefreshLayoutActivityPunchRecord.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(PunchRecordBean punchRecordBean) {
                if (isRefresh) {
                    if (mRefreshLayoutActivityPunchRecord.isRefreshing()) {
                        mRefreshLayoutActivityPunchRecord.finishRefresh();
                    }
                }else{
                    mRefreshLayoutActivityPunchRecord.finishLoadMore();
                }
                List<PunchRecordBean.DataBean> data = punchRecordBean.getData();
                if (data==null) {
                    return;
                }

                if (isRefresh) {
                    mDataBeans=data;
                }else{
                    mDataBeans.addAll(data);
                }
                mPunchRecordAdapter.setDataBeans(mDataBeans);

            }
        });
    }
    @Override
    public void initData() {
        mPunchRecordAdapter = new PunchRecordAdapter(mActivity);
        mListActivityPunchRecord.setAdapter(mPunchRecordAdapter);

        getPunchList(true);

        Intent intent = getIntent();
        if (intent!=null) {
            int data = intent.getIntExtra("data", 0);
            mTvActivityPunchRecordDays.setText(data+"天");
        }
    }

    @Override
    public int getRootView() {
        return R.layout.activity_punch_record;
    }
}
