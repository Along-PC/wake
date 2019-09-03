package com.tourye.wake.ui.fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.base.BaseFragment;
import com.tourye.wake.beans.AdvertisingBean;
import com.tourye.wake.beans.Commonbean;
import com.tourye.wake.beans.HomePageBean;
import com.tourye.wake.beans.OrderBean;
import com.tourye.wake.beans.PunchBean;
import com.tourye.wake.beans.PunchDetailBean;
import com.tourye.wake.beans.RankBean;
import com.tourye.wake.beans.SportRankBean;
import com.tourye.wake.beans.UserAccountBean;
import com.tourye.wake.beans.UserBasicBean;
import com.tourye.wake.beans.UserRankBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.pay.zfb.AuthResult;
import com.tourye.wake.pay.zfb.PayResult;
import com.tourye.wake.ui.activities.AchievementCardActivity;
import com.tourye.wake.ui.activities.BoardTicketActivity;
import com.tourye.wake.ui.activities.ResurrectionCardActivity;
import com.tourye.wake.ui.activities.WakeBannerActivity;
import com.tourye.wake.ui.adapters.MorningFragmentListAdapter;
import com.tourye.wake.utils.DensityUtils;
import com.tourye.wake.utils.NetStateUtils;
import com.tourye.wake.utils.NoneNetUtils;
import com.tourye.wake.utils.SaveUtil;
import com.tourye.wake.views.PunchToast;
import com.tourye.wake.views.dialogs.AttendDialog;
import com.tourye.wake.views.dialogs.PunchFailDialog;
import com.tourye.wake.views.dialogs.PunchSuccessDialog;
import com.tourye.wake.views.dialogs.ShareDialog;
import com.tourye.wake.views.dialogs.WakeRuleDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by longlongren on 2018/8/10.
 * <p>
 * introduce:早起打卡界面
 */

public class WakeFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTvFragmentMorningTicket;//局票数量
    private TextView mTvFragmentMorningPool;
    private TextView mTvFragmentMorningMoney;//当前奖金池奖金
    private TextView mTvFragmentMorningRule;//规则
    private TextView mTvFragmentMorningPay;//支付参与
    private TextView mTvFragmentMorningPunch;//打卡
    private TextView mTvFragMorningAward;//单人最高奖
    private TextView mTvFragMorningSuccess;//打卡成功人数
    private TextView mTvFragMorningFail;//打卡失败人数
    private ImageView mImgFragmentMorningTop;//顶部背景图
    private ImageView mImgFragmentMorningBanner;
    private View footer;//排行榜列表footer
    private ImageView mImgFragmentMorningResurrection;//复活卡图标
    private TextView mTvFragmentWakeBattle;//打卡战况
    private TabLayout mTabFragMoringWake;//排行榜导航栏


    private TextView mCurretSelected;//当前导航栏选中条目
    private boolean isRefresh;//是否进行刷新操作
    private int offset = 0;//从第几条开始取
    private int count = 10;//每次取几条数据
    private List<RankBean> mSportRank = new ArrayList<>();//运动排行榜数据
    private List<RankBean> mUserRank = new ArrayList<>();//奖金榜排行

    private SmartRefreshLayout mRefreshLayoutFragmentMorning;//刷新控件
    private ListView mListFragmentMorning;//排行榜列表
    private MorningFragmentListAdapter mMorningFragmentListAdapter;
    private PunchSuccessDialog mPunchSuccessDialog;

    private boolean isChallengeMode = false;//是否处于挑战模式


    private String path = "";//缓存数据的路径

    private boolean isPunchSuccess = false;//是否打卡成功

    private boolean mExcuteResume = true;//是否执行onresume生命周期
    private boolean mFirstVisible = true;//是否第一次执行setuserVisibility
    private boolean mIsDestroy = false;//该fragment是否销毁

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    getPunchData();
                    break;
            }
        }
    };

    @Override
    public void initView(View view) {
        mTvTitle.setText("早起打卡");
        mImgReturn.setVisibility(View.GONE);
        mImgCertain.setBackgroundResource(R.drawable.icon_share);

        mRefreshLayoutFragmentMorning = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout_fragment_morning);
        mListFragmentMorning = (ListView) view.findViewById(R.id.list_fragment_morning);
        mImgFragmentMorningResurrection = (ImageView) view.findViewById(R.id.img_fragment_morning_resurrection);

        //header view
        View inflate = mInflater.inflate(R.layout.head_fragment_morning, mListFragmentMorning, false);
        mTvFragmentMorningTicket = (TextView) inflate.findViewById(R.id.tv_fragment_morning_ticket);
        mTvFragmentMorningPool = (TextView) inflate.findViewById(R.id.tv_fragment_morning_pool);
        mTvFragmentMorningMoney = (TextView) inflate.findViewById(R.id.tv_fragment_morning_money);
        mTvFragmentMorningRule = (TextView) inflate.findViewById(R.id.tv_fragment_morning_rule);
        mTvFragmentMorningPay = (TextView) inflate.findViewById(R.id.tv_fragment_morning_pay);
        mTvFragmentMorningPunch = (TextView) inflate.findViewById(R.id.tv_fragment_morning_punch);
        mTvFragMorningAward = (TextView) inflate.findViewById(R.id.tv_frag_morning_award);
        mTvFragMorningSuccess = (TextView) inflate.findViewById(R.id.tv_frag_morning_success);
        mTvFragMorningFail = (TextView) inflate.findViewById(R.id.tv_frag_morning_fail);
        mImgFragmentMorningTop = (ImageView) inflate.findViewById(R.id.img_fragment_morning_top);
        mImgFragmentMorningBanner = (ImageView) inflate.findViewById(R.id.img_fragment_morning_banner);
        mTvFragmentWakeBattle = (TextView) inflate.findViewById(R.id.tv_fragment_wake_battle);
        mTabFragMoringWake = (TabLayout) inflate.findViewById(R.id.tab_frag_moring_wake);

        //listview的脚布局
        footer = mInflater.inflate(R.layout.footer_item_fragment_morning, mListFragmentMorning, false);
        mListFragmentMorning.addHeaderView(inflate);

        initTab();

        mTvFragmentMorningPunch.setOnClickListener(this);
        mTvFragmentMorningRule.setOnClickListener(this);
        mImgFragmentMorningResurrection.setOnClickListener(this);
        mTvFragmentMorningTicket.setOnClickListener(this);
        mImgCertain.setOnClickListener(this);

        mRefreshLayoutFragmentMorning.setEnableLoadMore(false);
        mRefreshLayoutFragmentMorning.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                offset = 0;
                count = 10;
                switch (mTabFragMoringWake.getSelectedTabPosition()) {
                    case 0:
                        getSportRankeData(MorningFragmentListAdapter.TYPE_WAKE_UP, offset, count, false);
                        break;
                    case 1:
                        getSportRankeData(MorningFragmentListAdapter.TYPE_INSIST, offset, count, false);
                        break;
                    case 2:
                        getSportRankeData(MorningFragmentListAdapter.TYPE_THUMB_UP, offset, count, false);
                        break;
                    case 3:
                        getUserRankData(offset, count, false);
                        break;
                }
                getAdvertiseData();
                getHomePageData();
                getUserAccountData();
                getUserBasicData();
            }
        });
        mRefreshLayoutFragmentMorning.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                offset += 10;
                count = 10;
                switch (mTabFragMoringWake.getSelectedTabPosition()) {
                    case 0:
                        getSportRankeData(MorningFragmentListAdapter.TYPE_WAKE_UP, offset, count, true);
                        break;
                    case 1:
                        getSportRankeData(MorningFragmentListAdapter.TYPE_INSIST, offset, count, true);
                        break;
                    case 2:
                        getSportRankeData(MorningFragmentListAdapter.TYPE_THUMB_UP, offset, count, true);
                        break;
                    case 3:
                        getUserRankData(offset, count, true);
                        break;
                }
            }
        });

        mListFragmentMorning.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            //firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
            //visibleItemCount：当前能看见的列表项个数（小半个也算）
            //totalItemCount：列表项共数
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                if (i > 0) {

                } else {
                    int index = mListFragmentMorning.getFirstVisiblePosition();
                    View v = mListFragmentMorning.getChildAt(0);
                    int top = (v == null) ? 0 : v.getTop();

                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mImgFragmentMorningResurrection.getLayoutParams();
                    int rightMargin = DensityUtils.dp2px(mActivity, 10);
                    int instance = rightMargin + top / 10;
                    layoutParams.setMargins(0, DensityUtils.dp2px(mActivity, 200), instance, 0);
                    mImgFragmentMorningResurrection.setLayoutParams(layoutParams);
                }

            }
        });
        path = mActivity.getExternalFilesDir(null).getPath();

    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && !mFirstVisible) {
            offset = 0;
            count = 10;
            switch (mTabFragMoringWake.getSelectedTabPosition()) {
                case 0:
                    getSportRankeData(MorningFragmentListAdapter.TYPE_WAKE_UP, offset, count, false);
                    break;
                case 1:
                    getSportRankeData(MorningFragmentListAdapter.TYPE_INSIST, offset, count, false);
                    break;
                case 2:
                    getSportRankeData(MorningFragmentListAdapter.TYPE_THUMB_UP, offset, count, false);
                    break;
                case 3:
                    getUserRankData(offset, count, false);
                    break;
            }
            getAdvertiseData();
            getHomePageData();
            getUserAccountData();
            getUserBasicData();
            mExcuteResume = false;
        } else {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExcuteResume) {
            offset = 0;
            count = 10;
            switch (mTabFragMoringWake.getSelectedTabPosition()) {
                case 0:
                    getSportRankeData(MorningFragmentListAdapter.TYPE_WAKE_UP, offset, count, false);
                    break;
                case 1:
                    getSportRankeData(MorningFragmentListAdapter.TYPE_INSIST, offset, count, false);
                    break;
                case 2:
                    getSportRankeData(MorningFragmentListAdapter.TYPE_THUMB_UP, offset, count, false);
                    break;
                case 3:
                    getUserRankData(offset, count, false);
                    break;
            }
            getAdvertiseData();
            getHomePageData();
            getUserAccountData();
            getUserBasicData();
            mFirstVisible = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mExcuteResume = true;
    }

    @Override
    public void initData() {
        mMorningFragmentListAdapter = new MorningFragmentListAdapter(mActivity);
        mListFragmentMorning.setAdapter(mMorningFragmentListAdapter);

        if (NetStateUtils.getNetState(mActivity) == NetStateUtils.NETWORK_STATE_NONE) {
            restoreData();
        }
    }

    /**
     * 当断网时加载缓存的数据
     */
    private void restoreData() {

        //设置首页信息
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "homepage.txt")));
            HomePageBean homePageBean = (HomePageBean) objectInputStream.readObject();
            objectInputStream.close();
            setHomepageData(homePageBean);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //断网加载数据--早起排行榜
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "wake.txt")));
            List<RankBean> sportRank = (List<RankBean>) objectInputStream.readObject();
            objectInputStream.close();
            setSportRankData(sportRank, MorningFragmentListAdapter.TYPE_WAKE_UP);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //广告数据
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "advertise.txt")));
            AdvertisingBean advertisingBean = (AdvertisingBean) objectInputStream.readObject();
            objectInputStream.close();
            setAdvertiseData(advertisingBean);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //用户账户数据--局票
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "user_account.txt")));
            UserAccountBean userAccountBean = (UserAccountBean) objectInputStream.readObject();
            objectInputStream.close();
            setUserAccountData(userAccountBean);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 断网设置用户账户数据
     */
    public void setUserAccountData(UserAccountBean userAccountBean) {
        UserAccountBean.DataBean data = userAccountBean.getData();
        if (data == null) {
            return;
        }
        mTvFragmentMorningTicket.setText(data.getPoint() + "局票");
    }


    /**
     * 获取用户基本信息数据
     */
    private void getUserBasicData() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.USER_BASIC_DATA, map, new HttpCallback<UserBasicBean>() {
            @Override
            public void onSuccessExecute(UserBasicBean userBasicBean) {
                UserBasicBean.DataBean data = userBasicBean.getData();
                if (data == null) {
                    return;
                }
                SaveUtil.putString("user_id", data.getId() + "");
                SaveUtil.putString("user_head", data.getAvatar() + "");
                SaveUtil.putString("punch_continue", data.getContinue_days() + "");
            }
        });
    }

    /**
     * 获取奖金排行榜
     *
     * @param offset     从第几条开始
     * @param count      获取多少数据
     * @param isLoadmore 是否执行加载更多操作
     */
    private void getUserRankData(final int offset, int count, final boolean isLoadmore) {
        //当刷新数据时开启加载更多功能
        if (offset == 0) {
            int footerViewsCount = mListFragmentMorning.getFooterViewsCount();
            mRefreshLayoutFragmentMorning.setEnableLoadMore(true);
            if (footerViewsCount != 0) {
                mListFragmentMorning.removeFooterView(footer);
            }
        }
        HashMap<String, String> userRankMap = new HashMap<>();
        userRankMap.put("type", "total_prize");
        userRankMap.put("offset", offset + "");
        userRankMap.put("count", count + "");
        HttpUtils.getInstance().get(Constants.RANK_PRIZE, userRankMap, new HttpCallback<UserRankBean>() {

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isLoadmore) {
                    mRefreshLayoutFragmentMorning.finishLoadMore();
                } else {
                    if (mRefreshLayoutFragmentMorning.isRefreshing()) {
                        mRefreshLayoutFragmentMorning.finishRefresh();
                    }

                }
            }

            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadmore) {
                    mRefreshLayoutFragmentMorning.finishLoadMore();

                } else {
                    if (mRefreshLayoutFragmentMorning.isRefreshing()) {
                        mRefreshLayoutFragmentMorning.finishRefresh();
                    }
                }
            }

            @Override
            public void onSuccessExecute(UserRankBean userRankBean) {
                UserRankBean.DataBean data = userRankBean.getData();
                int status = userRankBean.getStatus();

                if (data == null) {
                    return;
                }

                if (isLoadmore) {
                    List<RankBean> rank = data.getRank();

                    if (rank != null) {
                        if (rank.size() < 10) {
                            mRefreshLayoutFragmentMorning.setEnableLoadMore(false);
                            mListFragmentMorning.addFooterView(footer);
                        }
                        mUserRank.addAll(rank);
                    } else {
                        Toast.makeText(mActivity, "没有更多了", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mUserRank = data.getRank();
                    RankBean selfRank = data.getSelfRank();
                    if (mUserRank != null) {
                        //当请求回来的数据小于10条，关闭加载更多功能
                        if (mUserRank.size() < 10) {
                            mRefreshLayoutFragmentMorning.setEnableLoadMore(false);
                            mListFragmentMorning.addFooterView(footer);
                        }
                        if (selfRank != null) {
                            mUserRank.add(0, selfRank);
                        }

                    }
                }

                //记录列表当前位置，更新数据之后滑动列表
                int index = mListFragmentMorning.getFirstVisiblePosition();
                View v = mListFragmentMorning.getChildAt(0);
                int top = (v == null) ? 0 : v.getTop();

                //缓存数据
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path, "prize.txt")));
                    objectOutputStream.writeObject(mUserRank);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mMorningFragmentListAdapter.setData(mUserRank, MorningFragmentListAdapter.TYPE_PRIZE);
                mListFragmentMorning.setAdapter(mMorningFragmentListAdapter);

                //滑动列表到原来位置
                mListFragmentMorning.setSelectionFromTop(index, top);


            }
        });
    }

    /**
     * 获取打卡详情信息
     */
    public void getPunchDetail(String punch_id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", punch_id);
        HttpUtils.getInstance().get(Constants.PUNCH_DETAIL_DATA, map, new HttpCallback<PunchDetailBean>() {
            @Override
            public void onSuccessExecute(PunchDetailBean punchDetailBean) {
                PunchDetailBean.DataBean data = punchDetailBean.getData();
                if (data == null) {
                    return;
                }
                SaveUtil.putString("punch_time", data.getTime() + "");
            }
        });
    }

    /**
     * 断网之后加载用户排行榜的数据
     *
     * @param rankData
     */
    private void setUserRankData(List<RankBean> rankData) {
        if (rankData == null) {
            return;
        }
        //记录列表当前位置，更新数据之后滑动列表
        int index = mListFragmentMorning.getFirstVisiblePosition();
        View v = mListFragmentMorning.getChildAt(0);
        int top = (v == null) ? 0 : v.getTop();

        mMorningFragmentListAdapter.setData(rankData, MorningFragmentListAdapter.TYPE_PRIZE);
        mListFragmentMorning.setAdapter(mMorningFragmentListAdapter);

        //滑动列表到原来位置
        mListFragmentMorning.setSelectionFromTop(index, top);
    }

    /**
     * 获取用户账户信息
     */
    private void getUserAccountData() {

        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.USER_ACCOUNT_DATA, map, new HttpCallback<UserAccountBean>() {
            @Override
            public void onSuccessExecute(UserAccountBean userAccountBean) {
                UserAccountBean.DataBean data = userAccountBean.getData();
                if (data == null) {
                    return;
                }

                //缓存数据
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path, "user_account.txt")));
                    objectOutputStream.writeObject(userAccountBean);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mTvFragmentMorningTicket.setText(data.getPoint() + "局票");
            }
        });

    }


    /**
     * 获取打卡数据
     */
    private void getPunchData() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().post(Constants.PUNCH_DATA, map, new HttpCallback<PunchBean>() {
            @Override
            public void onSuccessExecute(PunchBean punchBean) {
                int status = punchBean.getStatus();
                if (status != 0) {
//                    PunchFailDialog punchFailDialog = new PunchFailDialog(mActivity);
//                    punchFailDialog.showDialog();
                    PunchToast.showToast(BaseApplication.mApplicationContext, "打卡失败");
                } else {
                    PunchBean.DataBean data = punchBean.getData();
                    if (data == null) {
                        return;
                    }
                    if (data.isWaiting()) {
                        mHandler.sendEmptyMessageDelayed(10086, 1000);
                    } else {
                        int continueX = data.getContinueX();
                        String time = data.getTime();
                        SaveUtil.putString("punch_id", data.getId() + "");
                        SaveUtil.putString("punch_time", time);
                        SaveUtil.putString("punch_continue", continueX + "");
                        isPunchSuccess = true;
                        mTvFragmentMorningPunch.setText("已打卡成功，领取成就卡");
                        mTvFragmentMorningPunch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(mActivity, AchievementCardActivity.class);
                                startActivity(intent);
                            }
                        });
                        if (!mIsDestroy) {
                            if (mPunchSuccessDialog == null) {
                                mPunchSuccessDialog = new PunchSuccessDialog(mActivity);
                            }
                            if (!mPunchSuccessDialog.isShowing()) {
                                mPunchSuccessDialog.showDialog(data);
                            }
                        }

                        //刷新打卡相关数据，保证分享数据的准确性
                        getUserBasicData();
                        getHomePageData();
                        offset = 0;
                        count = 10;
                        switch (mTabFragMoringWake.getSelectedTabPosition()) {
                            case 0:
                                getSportRankeData(MorningFragmentListAdapter.TYPE_WAKE_UP, offset, count, false);
                                break;
                            case 1:
                                getSportRankeData(MorningFragmentListAdapter.TYPE_INSIST, offset, count, false);
                                break;
                            case 2:
                                getSportRankeData(MorningFragmentListAdapter.TYPE_THUMB_UP, offset, count, false);
                                break;
                            case 3:
                                getUserRankData(offset, count, false);
                                break;
                        }

                    }
                }

            }
        });
    }

    /**
     * 断网时设置排行榜数据
     *
     * @param sportRank
     */
    public void setSportRankData(List<RankBean> sportRank, int type) {
        if (sportRank == null) {
            return;
        }

        //记录列表当前位置，更新数据之后滑动列表
        int index = mListFragmentMorning.getFirstVisiblePosition();
        View v = mListFragmentMorning.getChildAt(0);
        int top = (v == null) ? 0 : v.getTop();

        mMorningFragmentListAdapter.setData(sportRank, type);
        mListFragmentMorning.setAdapter(mMorningFragmentListAdapter);

        //滑动列表到原来位置
        mListFragmentMorning.setSelectionFromTop(index, top);
    }


    /**
     * 获取运动排行榜
     *
     * @param type       类型-continue,thumb_up,wake_time
     * @param offset     从第几条开始
     * @param count      取多少条
     * @param isLoadmore 是否是加载更多
     */

    private void getSportRankeData(final int type, final int offset, int count, final boolean isLoadmore) {
        //当刷新数据时开启加载更多功能
        if (offset == 0) {
            int footerViewsCount = mListFragmentMorning.getFooterViewsCount();
            mRefreshLayoutFragmentMorning.setEnableLoadMore(true);
            if (footerViewsCount != 0) {
                mListFragmentMorning.removeFooterView(footer);
            }
        }

        HashMap<String, String> sprotRankMap = new HashMap<>();
        if (type == MorningFragmentListAdapter.TYPE_WAKE_UP) {
            sprotRankMap.put("type", "wake_time");
        } else if (type == MorningFragmentListAdapter.TYPE_INSIST) {
            sprotRankMap.put("type", "continue");
        } else if (type == MorningFragmentListAdapter.TYPE_THUMB_UP) {
            sprotRankMap.put("type", "thumb_up");
        }

        sprotRankMap.put("offset", offset + "");
        sprotRankMap.put("count", count + "");
        HttpUtils.getInstance().get(Constants.RANK_SPORT, sprotRankMap, new HttpCallback<SportRankBean>() {

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isLoadmore) {
                    mRefreshLayoutFragmentMorning.finishLoadMore();
                } else {
                    if (mRefreshLayoutFragmentMorning.isRefreshing()) {
                        mRefreshLayoutFragmentMorning.finishRefresh();
                    }
                }
            }

            @Override
            public void onPreExcute() {
                super.onPreExcute();
                if (isLoadmore) {
                    mRefreshLayoutFragmentMorning.finishLoadMore();
                } else {
                    if (mRefreshLayoutFragmentMorning.isRefreshing()) {
                        mRefreshLayoutFragmentMorning.finishRefresh();
                    }
                }
            }

            @Override
            public void onSuccessExecute(SportRankBean sportRankBean) {
                SportRankBean.DataBean data = sportRankBean.getData();
                int status = sportRankBean.getStatus();

                if (data == null) {
                    return;
                }
                if (isLoadmore) {
                    List<RankBean> rank = data.getRank();
                    if (rank != null) {
                        if (rank.size() < 10) {
                            mRefreshLayoutFragmentMorning.setEnableLoadMore(false);
                            mListFragmentMorning.addFooterView(footer);
                        }
                        mSportRank.addAll(rank);
                    } else {
                        Toast.makeText(mActivity, "没有更多了", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mSportRank = data.getRank();
                    if (mSportRank != null) {
                        //当请求回来的数据小于10条，关闭加载更多功能
                        if (mSportRank.size() < 10) {
                            mRefreshLayoutFragmentMorning.setEnableLoadMore(false);
                            mListFragmentMorning.addFooterView(footer);
                        }
                        RankBean selfRank = data.getSelfRank();
                        if (selfRank != null) {
                            mSportRank.add(0, selfRank);
                        }
                    }

                }
                if (mSportRank == null) {
                    return;
                }


                //记录列表当前位置，更新数据之后滑动列表
                int index = mListFragmentMorning.getFirstVisiblePosition();
                View v = mListFragmentMorning.getChildAt(0);
                int top = (v == null) ? 0 : v.getTop();

                //缓存数据
                try {
                    ObjectOutputStream objectOutputStream = null;
                    if (type == MorningFragmentListAdapter.TYPE_WAKE_UP) {
                        objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path, "wake.txt")));
                    } else if (type == MorningFragmentListAdapter.TYPE_INSIST) {
                        objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path, "insist.txt")));
                    } else if (type == MorningFragmentListAdapter.TYPE_THUMB_UP) {
                        objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path, "thumb.txt")));
                    }
                    objectOutputStream.writeObject(mSportRank);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mMorningFragmentListAdapter.setData(mSportRank, type);
                mListFragmentMorning.setAdapter(mMorningFragmentListAdapter);

                //滑动列表到原来位置
                mListFragmentMorning.setSelectionFromTop(index, top);


            }
        });

    }

    /**
     * 获取首页信息数据
     */
    private void getHomePageData() {
        HashMap<String, String> homePageMap = new HashMap<>();
        HttpUtils.getInstance().get(Constants.HOME_PAGE_INFO, homePageMap, new HttpCallback<HomePageBean>() {
            @Override
            public void onSuccessExecute(HomePageBean homePageBean) {

                HomePageBean.DataBean data = homePageBean.getData();
                if (data == null) {
                    return;
                }

                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path, "homepage.txt")));
                    objectOutputStream.writeObject(homePageBean);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                setHomepageData(homePageBean);

            }
        });
    }

    /**
     * 设置首页数据
     *
     * @param homePageBean
     */
    private void setHomepageData(HomePageBean homePageBean) {
        if (homePageBean == null) {
            return;
        }
        HomePageBean.DataBean data = homePageBean.getData();
        //如果处于挑战模式，弹窗提醒
        boolean sleep = data.isSleep();
        if (sleep && !isChallengeMode) {
            isChallengeMode = true;
            AttendDialog attendDialog = new AttendDialog(mActivity);
            attendDialog.show();
        }

        float prize_pool = data.getPrize_pool();
        mTvFragmentMorningMoney.setText(prize_pool / 100 + "");
        float max_prize = data.getMax_prize();
        mTvFragMorningAward.setText(max_prize / 100 + "");
        mTvFragMorningSuccess.setText(data.getPass_people() + "");
        mTvFragMorningFail.setText(data.getFail_people() + "");
        if (data.isJoined()) {
            SaveUtil.putBoolean("pay_state", true);
            mTvFragmentMorningPay.setSelected(true);
            mTvFragmentMorningPay.setText("已报名");
            mTvFragmentMorningPay.setOnClickListener(null);

        } else {
            SaveUtil.putBoolean("pay_state", false);
            mTvFragmentMorningPay.setSelected(false);
            mTvFragmentMorningPay.setText("支付10元挑战金参与");
            mTvFragmentMorningPay.setOnClickListener(WakeFragment.this);
        }
        if (data.isSigned()) {
            SaveUtil.putString("punch_id", data.getSign_in_id() + "");
            //刷新打卡详情
            getPunchDetail(data.getSign_in_id() + "");
            isPunchSuccess = true;
            mTvFragmentMorningPunch.setText("已打卡成功，领取成就卡");
            mTvFragmentMorningPunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mActivity, AchievementCardActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            isPunchSuccess = false;
            mTvFragmentMorningPunch.setText("打卡");
            mTvFragmentMorningPunch.setOnClickListener(WakeFragment.this);
        }
        if (data.isIs_today()) {
            mTvFragmentWakeBattle.setText("今日打卡战况");
        } else {
            mTvFragmentWakeBattle.setText("昨日打卡战况");
        }
    }

    /**
     * 断网设置广告内容
     *
     * @param advertisingBean
     */
    public void setAdvertiseData(AdvertisingBean advertisingBean) {
        AdvertisingBean.DataBean data = advertisingBean.getData();
        if (data == null) {
            return;
        }
        AdvertisingBean.DataBean.HomePageBackgroundBean home_page_background = data.getHome_page_background();
        if (home_page_background != null) {
            Glide.with(mActivity).load(home_page_background.getImage()).into(mImgFragmentMorningTop);
        }
        final AdvertisingBean.DataBean.HomePageBannerBean home_page_banner = advertisingBean.getData().getHome_page_banner();
        if (home_page_banner != null) {
            mImgFragmentMorningBanner.setVisibility(View.VISIBLE);
            mImgFragmentMorningBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (home_page_banner.getLink() != null) {
                        Intent intent = new Intent(mActivity, WakeBannerActivity.class);
                        intent.putExtra("data", home_page_banner.getLink());
                        startActivity(intent);
                    }
                }
            });
            Glide.with(BaseApplication.mApplicationContext).load(home_page_banner.getImage()).into(mImgFragmentMorningBanner);
        } else {
            mImgFragmentMorningBanner.setVisibility(View.GONE);
            mImgFragmentMorningBanner.setOnClickListener(null);
        }
    }

    /**
     * 获取广告数据
     */
    private void getAdvertiseData() {
        HashMap<String, String> advertiseMap = new HashMap<>();
        HttpUtils.getInstance().get(Constants.OBTAIN_ADVERTISING, advertiseMap, new HttpCallback<AdvertisingBean>() {

            @Override
            public void onSuccessExecute(AdvertisingBean advertisingBean) {
                AdvertisingBean.DataBean data = advertisingBean.getData();
                if (data == null) {
                    return;
                }

                //缓存数据
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path, "advertise.txt")));
                    objectOutputStream.writeObject(advertisingBean);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                AdvertisingBean.DataBean.HomePageBackgroundBean home_page_background = data.getHome_page_background();
                if (home_page_background != null) {

                    Glide.with(BaseApplication.mApplicationContext).load(home_page_background.getImage()).into(mImgFragmentMorningTop);
                }
                final AdvertisingBean.DataBean.HomePageBannerBean home_page_banner = advertisingBean.getData().getHome_page_banner();
                if (home_page_banner != null) {
                    mImgFragmentMorningBanner.setVisibility(View.VISIBLE);
                    Glide.with(BaseApplication.mApplicationContext).load(home_page_banner.getImage()).into(mImgFragmentMorningBanner);
                    mImgFragmentMorningBanner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (home_page_banner.getLink() != null) {
                                Intent intent = new Intent(mActivity, WakeBannerActivity.class);
                                intent.putExtra("data", home_page_banner.getLink());
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    mImgFragmentMorningBanner.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_morning;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsDestroy = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fragment_morning_punch:
                getPunchData();
                break;
            case R.id.tv_fragment_morning_rule:
                WakeRuleDialog wakeRuleDialog = new WakeRuleDialog(mActivity);
                wakeRuleDialog.setSuccessCallback(new WakeRuleDialog.SuccessCallback() {
                    @Override
                    public void success() {
                        mTvFragmentMorningPay.setSelected(true);
                        mTvFragmentMorningPay.setText("已报名");
                        mTvFragmentMorningPay.setOnClickListener(null);
                    }
                });
                wakeRuleDialog.show();
                break;
            case R.id.img_fragment_morning_resurrection:
                Intent intent = new Intent(mActivity, ResurrectionCardActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_fragment_morning_ticket:
                Intent intentTicket = new Intent(mActivity, BoardTicketActivity.class);
                startActivity(intentTicket);
                break;
            case R.id.img_certain:
                ShareDialog shareDialog = new ShareDialog(mActivity);
                if (isPunchSuccess) {
                    shareDialog.showMainDialogTemp(true);
                } else {
                    shareDialog.showMainDialogTemp(false);
                }
                break;
            case R.id.tv_fragment_morning_pay:
                WakeRuleDialog ruleDialog = new WakeRuleDialog(mActivity);
                ruleDialog.setSuccessCallback(new WakeRuleDialog.SuccessCallback() {
                    @Override
                    public void success() {
                        mTvFragmentMorningPay.setSelected(true);
                        mTvFragmentMorningPay.setText("已报名");
                        mTvFragmentMorningPay.setOnClickListener(null);
                    }
                });
                ruleDialog.show();
                break;
        }

    }

    /**
     * 设置导航栏
     */
    private void initTab() {
        mTabFragMoringWake.addTab(mTabFragMoringWake.newTab().setText("早起榜"));
        mTabFragMoringWake.addTab(mTabFragMoringWake.newTab().setText("坚持榜"));
        mTabFragMoringWake.addTab(mTabFragMoringWake.newTab().setText("膜拜榜"));
        mTabFragMoringWake.addTab(mTabFragMoringWake.newTab().setText("奖金榜"));
        mTabFragMoringWake.getTabAt(0).select();
        setTabLine(mTabFragMoringWake,20,20);
        mTabFragMoringWake.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                offset=0;
                switch (position) {
                    case 0:
                        if (NetStateUtils.getNetState(mActivity) == NetStateUtils.NETWORK_STATE_NONE) {
                            //断网加载数据
                            try {
                                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "wake.txt")));
                                List<RankBean> sportRank = (List<RankBean>) objectInputStream.readObject();
                                objectInputStream.close();
                                setSportRankData(sportRank, MorningFragmentListAdapter.TYPE_WAKE_UP);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        //联网
                        getSportRankeData(MorningFragmentListAdapter.TYPE_WAKE_UP, offset, count, false);
                        break;
                    case 1:
                        if (NetStateUtils.getNetState(mActivity) == NetStateUtils.NETWORK_STATE_NONE) {
                            //断网加载数据
                            try {
                                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "insist.txt")));
                                List<RankBean> sportRank = (List<RankBean>) objectInputStream.readObject();
                                objectInputStream.close();
                                setSportRankData(sportRank, MorningFragmentListAdapter.TYPE_INSIST);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        //联网
                        getSportRankeData(MorningFragmentListAdapter.TYPE_INSIST, offset, count, false);

                        break;
                    case 2:
                        if (NetStateUtils.getNetState(mActivity) == NetStateUtils.NETWORK_STATE_NONE) {
                            //断网加载数据
                            try {
                                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "thumb.txt")));
                                List<RankBean> sportRank = (List<RankBean>) objectInputStream.readObject();
                                objectInputStream.close();
                                setSportRankData(sportRank, MorningFragmentListAdapter.TYPE_THUMB_UP);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        //联网
                        getSportRankeData(MorningFragmentListAdapter.TYPE_THUMB_UP, offset, count, false);

                        break;
                    case 3:
                        if (NetStateUtils.getNetState(mActivity) == NetStateUtils.NETWORK_STATE_NONE) {
                            //加载缓存奖金排行榜信息
                            try {
                                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "prize.txt")));
                                List<RankBean> rankData = (List<RankBean>) objectInputStream.readObject();
                                objectInputStream.close();
                                setUserRankData(rankData);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        //网络请求
                        getUserRankData(offset, count, false);

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 设置tablayout下划线长度
     * @param tabs
     * @param leftMargin
     * @param rightMargin
     */
    public void setTabLine(TabLayout tabs,int leftMargin,int rightMargin){
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            //通过反射得到tablayout的下划线的Field
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            //得到承载下划线的LinearLayout   //源码可以看到SlidingTabStrip继承得到承载下划线的LinearLayout
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftMargin, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightMargin, Resources.getSystem().getDisplayMetrics());
        //循环设置下划线的左边距和右边距
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

}
