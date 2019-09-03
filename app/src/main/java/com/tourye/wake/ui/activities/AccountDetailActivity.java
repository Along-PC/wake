package com.tourye.wake.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.AccountDetailBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.ui.adapters.AccountDetailAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * AccountDetailActivity
 * author:along
 * 2018/8/23 下午2:00
 * <p>
 * 描述:账户明细页面
 */

public class AccountDetailActivity extends BaseActivity {
    private SmartRefreshLayout mRefreshLayoutActivityAccountDetail;
    private ListView mListActivityAccountDetail;
    private String mLastId;//上次获取的数据最后一条的id
    private List<AccountDetailBean.DataBean> mDataBeans;//列表显示的数据
    private AccountDetailAdapter mAccountDetailAdapter;
    private String mType;


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("账户明细");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRefreshLayoutActivityAccountDetail = (SmartRefreshLayout) findViewById(R.id.refreshLayout_activity_account_detail);
        mListActivityAccountDetail = (ListView) findViewById(R.id.list_activity_account_detail);

        mRefreshLayoutActivityAccountDetail.setEnableAutoLoadMore(false);
        mRefreshLayoutActivityAccountDetail.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getAccountdata("",true);
            }
        });
        mRefreshLayoutActivityAccountDetail.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getAccountdata(mLastId,false);
            }
        });

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent!=null) {
            mType = intent.getStringExtra("data");
        }

        mAccountDetailAdapter = new AccountDetailAdapter(mActivity);
        mListActivityAccountDetail.setAdapter(mAccountDetailAdapter);

        getAccountdata("",true);
    }

    /**
     * 获取账户信息
     *
     * @param last_id   上次所取的数据最后一条的id
     * @param isRefresh 是否执行刷新操作
     */
    private void getAccountdata(String last_id, final boolean isRefresh) {
        if (isRefresh) {
            mRefreshLayoutActivityAccountDetail.setEnableLoadMore(true);
        }
        Map<String, String> map = new HashMap<>();
        map.put("type", mType);
        map.put("last_id", last_id);
        map.put("count", 10 + "");
        HttpUtils.getInstance().get(Constants.ACCOUNT_DETAIL_DATA, map, new HttpCallback<AccountDetailBean>() {

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isRefresh) {
                    if (mRefreshLayoutActivityAccountDetail.isRefreshing()) {
                        mRefreshLayoutActivityAccountDetail.finishRefresh();
                    }
                } else {
                    mRefreshLayoutActivityAccountDetail.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(AccountDetailBean accountDetailBean) {
                if (isRefresh) {
                    if (mRefreshLayoutActivityAccountDetail.isRefreshing()) {
                        mRefreshLayoutActivityAccountDetail.finishRefresh();
                    }
                } else {
                    mRefreshLayoutActivityAccountDetail.finishLoadMore();
                }
                List<AccountDetailBean.DataBean> data = accountDetailBean.getData();
                int status = accountDetailBean.getStatus();
                if (data == null || data.size() == 0) {
                    mRefreshLayoutActivityAccountDetail.setEnableLoadMore(false);
                    return;
                }
                if (data.size()<10) {
                    mRefreshLayoutActivityAccountDetail.setEnableLoadMore(false);
                }

                AccountDetailBean.DataBean dataBean = data.get(data.size() - 1);
                mLastId = dataBean.getId() + "";

                if (isRefresh) {
                    mDataBeans = data;
                } else {
                    mDataBeans.addAll(data);
                }
                mAccountDetailAdapter.setDataBeans(mDataBeans);
            }

        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_account_detail;
    }
}
