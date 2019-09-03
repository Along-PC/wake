package com.tourye.wake.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.mob.tools.network.HTTPPart;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.MessageListBean;
import com.tourye.wake.beans.ReadMessageBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.ui.adapters.PersonMessageAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * PersonMessageActivity
 * author:along
 * 2018/8/27 上午10:17
 *
 * 描述:消息
 */

public class PersonMessageActivity extends BaseActivity {
    private SmartRefreshLayout mRefreshLayoutActivityPersonMessage;
    private ListView mListActivityPersonMessage;

    private int mOffset=0;
    private int mCount=10;

    private List<MessageListBean.DataBean> mDataBeans=new ArrayList<>();
    private PersonMessageAdapter mPersonMessageAdapter;

    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("消息");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRefreshLayoutActivityPersonMessage = (SmartRefreshLayout) findViewById(R.id.refreshLayout_activity_person_message);
        mListActivityPersonMessage = (ListView) findViewById(R.id.list_activity_person_message);

//        mRefreshLayoutActivityPersonMessage.setEnableAutoLoadMore(false);
        mRefreshLayoutActivityPersonMessage.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mOffset=0;
                getList(true);
            }
        });
        mRefreshLayoutActivityPersonMessage.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mOffset+=10;
                getList(false);
            }
        });

    }

    @Override
    public void initData() {

        mPersonMessageAdapter = new PersonMessageAdapter(mActivity);
        mListActivityPersonMessage.setAdapter(mPersonMessageAdapter);

        getList(true);

        Intent intent = getIntent();
        boolean hasUnreadMessage = intent.getBooleanExtra("data", false);
        if (hasUnreadMessage) {
            readMessage();
        }

    }

    //设置所有消息已阅读
    private void readMessage() {
        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.READ_MESSAGE, map, new HttpCallback<ReadMessageBean>() {
            @Override
            public void onSuccessExecute(ReadMessageBean readMessageBean) {
                int status = readMessageBean.getStatus();
                if (BuildConfig.DEBUG) Log.d("PersonMessageActivity", "status:" + status);
            }
        });
    }


    /**
     * 获取消息列表
     * @param isRefresh
     */
    public void getList(final boolean isRefresh){
        if (isRefresh) {
            mRefreshLayoutActivityPersonMessage.setEnableLoadMore(true);
        }
        Map<String,String> map=new HashMap<>();
        map.put("offset",mOffset+"");
        map.put("count",mCount+"");
        HttpUtils.getInstance().get(Constants.MESSAGE_LIST, map, new HttpCallback<MessageListBean>() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isRefresh) {
                    if (mRefreshLayoutActivityPersonMessage.isRefreshing()) {
                        mRefreshLayoutActivityPersonMessage.finishRefresh();
                    }
                }else{
                    mRefreshLayoutActivityPersonMessage.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(MessageListBean messageListBean) {
                if (isRefresh) {
                    if (mRefreshLayoutActivityPersonMessage.isRefreshing()) {
                        mRefreshLayoutActivityPersonMessage.finishRefresh();
                    }
                }else{
                    mRefreshLayoutActivityPersonMessage.finishLoadMore();
                }
                List<MessageListBean.DataBean> data = messageListBean.getData();
                if (data==null || data.size()==0) {
                    return;
                }
                if (data.size()<10) {
                    mRefreshLayoutActivityPersonMessage.setEnableLoadMore(false);
                }
                if (isRefresh) {
                    mDataBeans=data;
                }else{
                    mDataBeans.addAll(data);
                }
                mPersonMessageAdapter.setDataBeans(mDataBeans);
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_person_message;
    }
}
