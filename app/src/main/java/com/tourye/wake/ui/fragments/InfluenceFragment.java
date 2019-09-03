package com.tourye.wake.ui.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseFragment;
import com.tourye.wake.beans.InfluenceRankBean;
import com.tourye.wake.beans.InvitedDetailBean;
import com.tourye.wake.beans.InvitedUserBean;
import com.tourye.wake.beans.UserBasicBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.ui.activities.InviteCardActivity;
import com.tourye.wake.ui.adapters.InfluenceRankAdapter;
import com.tourye.wake.ui.adapters.InvitedPeopleAdapter;
import com.tourye.wake.utils.GlideCircleTransform;
import com.tourye.wake.utils.NetStateUtils;
import com.tourye.wake.views.MeasureGridView;
import com.tourye.wake.views.MeasureListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by longlongren on 2018/8/10.
 * <p>
 * introduce:导航页---影响
 */

public class InfluenceFragment extends BaseFragment implements View.OnClickListener {
    private SmartRefreshLayout mRefreshLayoutFragmentInfluence;//刷新控件
    private TextView mTvFragmentInfluenceShare;//分享邀请卡
    private LinearLayout mLlFragmentInfluencePeople;//直接影响
    private ImageView mImgFragmentInfluencePeople;//
    private MeasureGridView mGridFragmentInfluencePeople;//直接影响人列表
    private MeasureListView mListFragmentInfluenceRank;//排行榜列表
    private TextView mTvFragmentInfluenceLoadmore;//加载更多
    private TextView mTvFragmentInfluencePeopleNum;
    private ImageView mImgFragmentInfluenceBack;
    private RelativeLayout mRlFragmentInfluencePeople;
    private ScrollView mScrollFragmentInfluence;


    private int mOffsetPeople = 0;//影响的人开始条数
    private int mLimitPeople = 10;//影响的人请求条数
    private InvitedPeopleAdapter mInvitedPeopleAdapter;
    private List<InvitedUserBean.DataBean> mInvitedData = new ArrayList<>();
    private boolean isShowPeople = false;//是否显示影响的人

    private int mOffsetRank = 0;//排行榜开始条数
    private int mCountRank = 10;//排行榜请求条数
    private InfluenceRankAdapter mInfluenceRankAdapter;

    private List<InfluenceRankBean.DataBean.RankBean> mRanks = new ArrayList<>();

    private View footer;//listview的脚布局
    private int mLevel_1_count;//直接影响人数

    private String path="";//断网缓存数据位置

    private boolean mExcuteResume=true;//是否执行onresume
    private boolean mFirstVisible=true;//是否第一次执行setusetvisiblehint



    @Override
    public void initView(View view) {
        mTvTitle.setText("影响");

        mRefreshLayoutFragmentInfluence = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_fragment_influence);
        mTvFragmentInfluenceShare = (TextView) view.findViewById(R.id.tv_fragment_influence_share);
        mLlFragmentInfluencePeople = (LinearLayout) view.findViewById(R.id.ll_fragment_influence_people);
        mImgFragmentInfluencePeople = (ImageView) view.findViewById(R.id.img_fragment_influence_people);
        mGridFragmentInfluencePeople = (MeasureGridView) view.findViewById(R.id.grid_fragment_influence_people);
        mListFragmentInfluenceRank = (MeasureListView) view.findViewById(R.id.list_fragment_influence_rank);
        mTvFragmentInfluenceLoadmore = (TextView) view.findViewById(R.id.tv_fragment_influence_loadmore);
        mTvFragmentInfluencePeopleNum = (TextView) view.findViewById(R.id.tv_fragment_influence_people_num);
        mImgFragmentInfluenceBack = (ImageView) view.findViewById(R.id.img_fragment_influence_back);
        mRlFragmentInfluencePeople = (RelativeLayout) view.findViewById(R.id.rl_fragment_influence_people);
        mScrollFragmentInfluence = (ScrollView) view.findViewById(R.id.scroll_fragment_influence);


        footer = mInflater.inflate(R.layout.footer_item_fragment_morning, mListFragmentInfluenceRank, false);


        //避免listview、gridview抢占焦点
        mListFragmentInfluenceRank.setFocusable(false);
        mGridFragmentInfluencePeople.setFocusable(false);
        mInvitedPeopleAdapter = new InvitedPeopleAdapter(mActivity);
        mGridFragmentInfluencePeople.setAdapter(mInvitedPeopleAdapter);

        mInfluenceRankAdapter = new InfluenceRankAdapter(mActivity);
        mListFragmentInfluenceRank.setAdapter(mInfluenceRankAdapter);

        mLlFragmentInfluencePeople.setOnClickListener(this);
        mTvFragmentInfluenceLoadmore.setOnClickListener(this);
        mTvFragmentInfluenceShare.setOnClickListener(this);

//        mRefreshLayoutFragmentInfluence.setEnableAutoLoadMore(false);
        mRefreshLayoutFragmentInfluence.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mOffsetPeople=0;
                getRankdata(true);
                getInvitedUser(true);
                getInvitedDetail();

            }
        });
        mRefreshLayoutFragmentInfluence.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

                getRankdata(false);

            }
        });

        path=mActivity.getExternalFilesDir(null).getPath();

        //影响的人
        mGridFragmentInfluencePeople.setVisibility(View.GONE);
        mTvFragmentInfluenceLoadmore.setVisibility(View.GONE);
        mImgFragmentInfluencePeople.setBackgroundResource(R.drawable.icon_arrow_right);
        mRlFragmentInfluencePeople.setVisibility(View.GONE);

    }

    @Override
    public void initData() {
        if(NetStateUtils.getNetState(mActivity)==NetStateUtils.NETWORK_STATE_NONE) {
            restoreData();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && !mFirstVisible) {
            getInvitedUser(true);
            getRankdata(true);
            getInvitedDetail();
            mExcuteResume=false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExcuteResume) {
            getInvitedUser(true);
            getRankdata(true);
            getInvitedDetail();
            mFirstVisible=false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mExcuteResume=true;
    }

    /**
     * 断网加载缓存数据
     */
    private void restoreData() {
        setRankData();
        setInviteDetail();
        setInvitedPeople();
    }

    /**
     * 断网加载影响人列表
     */
    public void setInvitedPeople(){

        try {
            ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(new File(path,"invite_people.txt")));
            List<InvitedUserBean.DataBean> invitedData= (List<InvitedUserBean.DataBean>) objectInputStream.readObject();
            objectInputStream.close();
            if (invitedData==null) {
                return;
            }
            mInvitedPeopleAdapter.setInvitedUserBeans(invitedData);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 断网加载邀请详细数据
     */
    private void setInviteDetail() {
        //缓存数据---用户
        try {
            ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(new File(path,"invite_detail.txt")));
            InvitedDetailBean invitedDetailBean = (InvitedDetailBean) objectInputStream.readObject();
            objectInputStream.close();

            InvitedDetailBean.DataBean data = invitedDetailBean.getData();
            mLevel_1_count = data.getLevel_1_count();
            mTvFragmentInfluencePeopleNum.setText("直接影响"+ mLevel_1_count +"人");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 断网加载排行榜数据
     */
    private void setRankData() {
        //缓存数据---用户
        try {
            ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(new File(path,"influence.txt")));
            List<InfluenceRankBean.DataBean.RankBean> ranks = (List<InfluenceRankBean.DataBean.RankBean>) objectInputStream.readObject();
            objectInputStream.close();

            mInfluenceRankAdapter.setInfluenceRankBeans(ranks);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求排行榜数据
     *
     * @param isRefresh 是否进行下啦刷新操作
     */
    public void getRankdata(final boolean isRefresh) {
        if (isRefresh) {
            if (mListFragmentInfluenceRank.getFooterViewsCount()>0) {
                mListFragmentInfluenceRank.removeFooterView(footer);
            }
            mRefreshLayoutFragmentInfluence.setEnableLoadMore(true);
        }
        if (isRefresh) {
            mOffsetRank=0;
        }else{
            mOffsetRank += 10;
        }
        Map<String, String> map = new HashMap<>();
        map.put("type", "count");
        map.put("offset", mOffsetRank + "");
        map.put("count", mCountRank + "");
        HttpUtils.getInstance().get(Constants.INFLUENCE_RANK, map, new HttpCallback<InfluenceRankBean>() {

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isRefresh) {
                    if (mRefreshLayoutFragmentInfluence.isRefreshing()) {
                        mRefreshLayoutFragmentInfluence.finishRefresh();
                    }
                } else {
                    mRefreshLayoutFragmentInfluence.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(InfluenceRankBean influenceRankBean) {
                int status = influenceRankBean.getStatus();
                if (isRefresh) {
                    if (mRefreshLayoutFragmentInfluence.isRefreshing()) {
                        mRefreshLayoutFragmentInfluence.finishRefresh();
                    }
                } else {
                    mRefreshLayoutFragmentInfluence.finishLoadMore();
                }
                InfluenceRankBean.DataBean data = influenceRankBean.getData();
                if (data == null) {
                    return;
                }
                List<InfluenceRankBean.DataBean.RankBean> ranks = data.getRank();
                if (ranks == null) {
                    return;
                }
                if (ranks.size() < 10) {
                    mListFragmentInfluenceRank.addFooterView(footer);
                    mRefreshLayoutFragmentInfluence.setEnableLoadMore(false);
                }
                if (isRefresh) {
                    InfluenceRankBean.DataBean.RankBean selfRank = data.getSelfRank();
                    if (selfRank != null) {
                        ranks.add(0,selfRank);
                    }
                    mRanks.clear();
                    mRanks.addAll(ranks);
                } else {
                    mRanks.addAll(ranks);
                }

                //缓存数据--断网使用
                try {
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(new File(path,"influence.txt")));
                    objectOutputStream.writeObject(mRanks);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mInfluenceRankAdapter.setInfluenceRankBeans(mRanks);

            }
        });
    }

    /**
     * 获取邀请用户详细情况
     */
    public void getInvitedDetail(){
        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.INVITED_USER_DETAIL, map, new HttpCallback<InvitedDetailBean>() {
            @Override
            public void onSuccessExecute(InvitedDetailBean invitedDetailBean) {
                InvitedDetailBean.DataBean data = invitedDetailBean.getData();
                if (data==null) {
                    return;
                }
                //缓存数据--断网使用
                try {
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(new File(path,"invite_detail.txt")));
                    objectOutputStream.writeObject(invitedDetailBean);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mLevel_1_count = data.getLevel_1_count();
                mTvFragmentInfluencePeopleNum.setText("直接影响"+ mLevel_1_count +"人");

            }
        });
    }

    /**
     * 是否进行下啦刷新操作
     *
     * @param isRefresh
     */
    public void getInvitedUser(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("level", "1");
        map.put("offset", mOffsetPeople + "");
        map.put("limit", mLimitPeople + "");
        HttpUtils.getInstance().get(Constants.INVITED_USER_LIST, map, new HttpCallback<InvitedUserBean>() {
            @Override
            public void onSuccessExecute(InvitedUserBean invitedUserBean) {
                List<InvitedUserBean.DataBean> data = invitedUserBean.getData();

                if (data == null) {
                    mTvFragmentInfluenceLoadmore.setVisibility(View.GONE);
                    mInvitedPeopleAdapter.setInvitedUserBeans(mInvitedData);
                    return;
                }
                if (data.size()<10) {
                    mTvFragmentInfluenceLoadmore.setVisibility(View.GONE);
                }else{
                    mTvFragmentInfluenceLoadmore.setVisibility(View.VISIBLE);
                }
                if (isRefresh) {
                    mInvitedData=data;
                } else {
                    mInvitedData.addAll(data);
                }

                //缓存数据--断网使用
                try {
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(new File(path,"invite_people.txt")));
                    objectOutputStream.writeObject(mInvitedData);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mInvitedPeopleAdapter.setInvitedUserBeans(mInvitedData);

            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_influence;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fragment_influence_people:
                if (isShowPeople) {
                    isShowPeople = false;
                    mGridFragmentInfluencePeople.setVisibility(View.GONE);
                    mTvFragmentInfluenceLoadmore.setVisibility(View.GONE);
                    mImgFragmentInfluencePeople.setBackgroundResource(R.drawable.icon_arrow_right);
                    mRlFragmentInfluencePeople.setVisibility(View.GONE);
                } else {
                    isShowPeople = true;
                    if (mLevel_1_count==0) {
                        mGridFragmentInfluencePeople.setVisibility(View.GONE);
                        mTvFragmentInfluenceLoadmore.setVisibility(View.GONE);
                        mImgFragmentInfluencePeople.setBackgroundResource(R.drawable.icon_arrow_bottom);
                        mRlFragmentInfluencePeople.setVisibility(View.VISIBLE);
                    }else{
                        mRlFragmentInfluencePeople.setVisibility(View.GONE);
                        mGridFragmentInfluencePeople.setVisibility(View.VISIBLE);
                        mImgFragmentInfluencePeople.setBackgroundResource(R.drawable.icon_arrow_bottom);
                        mOffsetPeople=0;
                        getInvitedUser(true);
                    }

                }
                break;
            case R.id.tv_fragment_influence_loadmore:
                mOffsetPeople += 10;
                getInvitedUser(false);
                break;
            case R.id.tv_fragment_influence_share:
                Intent intent = new Intent(mActivity, InviteCardActivity.class);
                startActivity(intent);
                break;
        }
    }
}
