package com.tourye.wake.ui.activities;

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
import com.tourye.wake.ui.adapters.BoardTicketDetailAdapter;
import com.tourye.wake.views.dialogs.ShareDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * BoardTicketDetailActivity
 * author:along
 * 2018/8/15 下午7:31
 *
 * 描述:局票详细页面
 */

public class BoardTicketDetailActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout mRefreshLayoutBoardTicketDetail;
    private ListView mListBoardTicketDetail;
    private List<AccountDetailBean.DataBean> mAccountData=new ArrayList<>();

    private String mLastId="";
    private BoardTicketDetailAdapter mBoardTicketDetailAdapter;

    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("局票明细");
        mImgCertain.setBackgroundResource(R.drawable.icon_share);

        mRefreshLayoutBoardTicketDetail = (SmartRefreshLayout) findViewById(R.id.refreshLayout_board_ticket_detail);
        mListBoardTicketDetail = (ListView) findViewById(R.id.list_board_ticket_detail);

//        mRefreshLayoutBoardTicketDetail.setEnableAutoLoadMore(false);
        mRefreshLayoutBoardTicketDetail.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mLastId="";
                getAccountdata(mLastId,true);
            }
        });
        mRefreshLayoutBoardTicketDetail.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getAccountdata(mLastId,false);
            }
        });
        mImgReturn.setOnClickListener(this);
        mImgCertain.setOnClickListener(this);
    }

    @Override
    public void initData(){

        getAccountdata(mLastId,true);

        mBoardTicketDetailAdapter = new BoardTicketDetailAdapter(mActivity);
        mListBoardTicketDetail.setAdapter(mBoardTicketDetailAdapter);

    }

    /**
     * 获取账户信息
     * @param last_id 上次所取的数据最后一条的id
     * @param isRefresh  是否执行刷新操作
     */
    private void getAccountdata(String last_id, final boolean isRefresh) {
        if (isRefresh) {
            mRefreshLayoutBoardTicketDetail.setEnableLoadMore(true);
        }
        Map<String,String> map=new HashMap<>();
        map.put("type","point");
        map.put("last_id",last_id);
        map.put("count",15+"");
        HttpUtils.getInstance().get(Constants.ACCOUNT_DETAIL_DATA, map, new HttpCallback<AccountDetailBean>() {

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isRefresh) {
                    if (mRefreshLayoutBoardTicketDetail.isRefreshing()) {
                        mRefreshLayoutBoardTicketDetail.finishRefresh();
                    }
                }else{
                    mRefreshLayoutBoardTicketDetail.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(AccountDetailBean accountDetailBean) {
                List<AccountDetailBean.DataBean> data = accountDetailBean.getData();
                int status = accountDetailBean.getStatus();
                if (isRefresh) {
                    if (mRefreshLayoutBoardTicketDetail.isRefreshing()) {
                        mRefreshLayoutBoardTicketDetail.finishRefresh();
                    }
                }else{
                    mRefreshLayoutBoardTicketDetail.finishLoadMore();
                }

                if (data==null || data.size()==0) {
                    return;
                }
                if (data.size()<10) {
                    mRefreshLayoutBoardTicketDetail.setEnableLoadMore(false);
                }

                AccountDetailBean.DataBean dataBean = data.get(data.size() - 1);
                mLastId=dataBean.getId()+"";

                if (isRefresh) {
                    mAccountData=data;
                    if (mRefreshLayoutBoardTicketDetail.isRefreshing()) {
                        mRefreshLayoutBoardTicketDetail.finishRefresh();
                    }

                }else{
                    mRefreshLayoutBoardTicketDetail.finishLoadMore();
                    mAccountData.addAll(data);

                }
                mBoardTicketDetailAdapter.setDataBeans(mAccountData);

            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_board_ticket_detail;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_return:
                finish();
                break;
            case R.id.img_certain:
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.showDialogTemp("/wake/point_detail");
                break;
        }

    }
}
