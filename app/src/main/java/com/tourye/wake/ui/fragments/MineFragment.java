package com.tourye.wake.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.base.BaseFragment;
import com.tourye.wake.beans.HomePageBean;
import com.tourye.wake.beans.UnreadMessageBean;
import com.tourye.wake.beans.UserBasicBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.ui.activities.BoardTicketActivity;
import com.tourye.wake.ui.activities.BusinessTeamworkActivity;
import com.tourye.wake.ui.activities.FunctionSetActivity;
import com.tourye.wake.ui.activities.InfluencePrizeActivity;
import com.tourye.wake.ui.activities.MainActivity;
import com.tourye.wake.ui.activities.PersonMessageActivity;
import com.tourye.wake.ui.activities.ProblemActivity;
import com.tourye.wake.ui.activities.PunchRecordActivity;
import com.tourye.wake.ui.activities.UpdateHeadActivity;
import com.tourye.wake.ui.activities.WakePrizeActivity;
import com.tourye.wake.utils.DensityUtils;
import com.tourye.wake.utils.GlideCircleTransform;
import com.tourye.wake.utils.NetStateUtils;
import com.tourye.wake.views.dialogs.ModifyNameDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by longlongren on 2018/8/10.
 * <p>
 * introduce:导航页---个人中心
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private ImageView mImgFragmentMineHead;
    private TextView mTvFragmentMineName;
    private ImageView mImgFragmentMineModify;
    private TextView mTvFragmentMineCode;
    private TextView mTvFragmentMineAccumulative;
    private TextView mTvFragmentMineContinue;
    private LinearLayout mLlFragmentMinePunch;
    private LinearLayout mLlFragmentMinePrize;
    private LinearLayout mLlFragmentMineInfluence;
    private LinearLayout mLlFragmentMineTicket;
    private LinearLayout mLlFragmentMineProblem;
    private LinearLayout mLlFragmentMineBusiness;
    private LinearLayout mLlFragmentMineFunction;
    private TextView mTvFragmentMineTitle;
    private ImageView mImgFragmentMineMessage;
    private ImageView mImgFragmentMinePoint;


    private String mAvatar;
    private int mMax_continue_days;//最高持续打卡天数

    private boolean hasUnreadMessage = false;//是否有未阅读的消息

    private String path = "";//断网缓存数据位置

    private boolean mExcuteResume=true;//是否执行onresume
    private boolean mFirstVisible=true;//是否第一次执行setuservisiblehint


    @Override
    public void initView(View view) {

        mImgFragmentMineHead = (ImageView) view.findViewById(R.id.img_fragment_mine_head);
        mTvFragmentMineName = (TextView) view.findViewById(R.id.tv_fragment_mine_name);
        mImgFragmentMineModify = (ImageView) view.findViewById(R.id.img_fragment_mine_modify);
        mTvFragmentMineCode = (TextView) view.findViewById(R.id.tv_fragment_mine_code);
        mTvFragmentMineAccumulative = (TextView) view.findViewById(R.id.tv_fragment_mine_accumulative);
        mTvFragmentMineContinue = (TextView) view.findViewById(R.id.tv_fragment_mine_continue);
        mLlFragmentMinePunch = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_punch);
        mLlFragmentMinePrize = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_prize);
        mLlFragmentMineInfluence = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_influence);
        mLlFragmentMineTicket = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_ticket);
        mLlFragmentMineProblem = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_problem);
        mLlFragmentMineBusiness = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_business);
        mLlFragmentMineFunction = (LinearLayout) view.findViewById(R.id.ll_fragment_mine_function);
        mTvFragmentMineTitle = (TextView) view.findViewById(R.id.tv_fragment_mine_title);
        mImgFragmentMineMessage = (ImageView) view.findViewById(R.id.img_fragment_mine_message);
        mImgFragmentMinePoint = (ImageView) view.findViewById(R.id.img_fragment_mine_point);

        mTvFragmentMineTitle.setText("个人中心");

        mImgFragmentMineModify.setOnClickListener(this);
        mImgFragmentMineHead.setOnClickListener(this);
        mLlFragmentMinePunch.setOnClickListener(this);
        mLlFragmentMinePrize.setOnClickListener(this);
        mLlFragmentMineInfluence.setOnClickListener(this);
        mLlFragmentMineTicket.setOnClickListener(this);
        mLlFragmentMineProblem.setOnClickListener(this);
        mLlFragmentMineBusiness.setOnClickListener(this);
        mLlFragmentMineFunction.setOnClickListener(this);
        mImgFragmentMineMessage.setOnClickListener(this);

        path = mActivity.getExternalFilesDir(null).getPath();

        Glide.with(BaseApplication.mApplicationContext).load(R.drawable.icon_default_head).transform(new GlideCircleTransform(BaseApplication.mApplicationContext)).into(mImgFragmentMineHead);

    }

    @Override
    public void initData() {
        if (NetStateUtils.getNetState(mActivity) == NetStateUtils.NETWORK_STATE_NONE) {
            //加载断网数据
            setMessage();
            setUserBasicData();
        }

    }


    /**
     * 断网加载用户基本信息
     */
    private void setUserBasicData() {
        //缓存数据---用户
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "user.txt")));
            UserBasicBean userBasicBean = (UserBasicBean) objectInputStream.readObject();
            objectInputStream.close();

            UserBasicBean.DataBean data = userBasicBean.getData();
            mAvatar = data.getAvatar();
            Glide.with(BaseApplication.mApplicationContext).load(mAvatar).transform(new GlideCircleTransform(BaseApplication.mApplicationContext)).into(mImgFragmentMineHead);
            mTvFragmentMineName.setText(data.getNickname());
            mTvFragmentMineCode.setText("编号：" + data.getSerial_number());
            mTvFragmentMineAccumulative.setText(data.getTotal_days() + "");
            mTvFragmentMineContinue.setText(data.getContinue_days() + "");
            mMax_continue_days = data.getMax_continue_days();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 断网加载未读消息数据
     */
    private void setMessage() {
        //缓存数据---未读消息
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(path, "unread.txt")));
            UnreadMessageBean unreadMessageBean = (UnreadMessageBean) objectInputStream.readObject();
            objectInputStream.close();

            boolean data = unreadMessageBean.isData();
            if (data) {
                mImgFragmentMinePoint.setVisibility(View.VISIBLE);
                hasUnreadMessage = true;
            } else {
                mImgFragmentMinePoint.setVisibility(View.GONE);
                hasUnreadMessage = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && !mFirstVisible) {
            getUserBasicData();
            getUnreadMessage();
            mExcuteResume=false;
        } else {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExcuteResume) {
            getUserBasicData();
            getUnreadMessage();
            mFirstVisible=false;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mExcuteResume=true;
    }

    public void getUnreadMessage() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.UNREAD_MESSAGE_LIST, map, new HttpCallback<UnreadMessageBean>() {
            @Override
            public void onSuccessExecute(UnreadMessageBean unreadMessageBean) {
                //缓存数据--断网使用
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path, "unread.txt")));
                    objectOutputStream.writeObject(unreadMessageBean);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boolean data = unreadMessageBean.isData();
                if (data) {
                    mImgFragmentMinePoint.setVisibility(View.VISIBLE);
                    hasUnreadMessage = true;
                } else {
                    mImgFragmentMinePoint.setVisibility(View.GONE);
                    hasUnreadMessage = false;
                }
            }
        });
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
                //缓存数据--断网使用
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(path, "user.txt")));
                    objectOutputStream.writeObject(userBasicBean);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mAvatar = data.getAvatar();
                Glide.with(BaseApplication.mApplicationContext).load(mAvatar).transform(new GlideCircleTransform(BaseApplication.mApplicationContext)).into(mImgFragmentMineHead);
                mTvFragmentMineName.setText(data.getNickname());
                mTvFragmentMineCode.setText("编号：" + data.getSerial_number());
                mTvFragmentMineAccumulative.setText(data.getTotal_days() + "");
                mTvFragmentMineContinue.setText(data.getContinue_days() + "");
                mMax_continue_days = data.getMax_continue_days();
            }
        });
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public int getRootView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_fragment_mine_modify:
                ModifyNameDialog modifyNameDialog = new ModifyNameDialog(mActivity);
                modifyNameDialog.show();
                modifyNameDialog.setRenameCallback(new ModifyNameDialog.RenameCallback() {
                    @Override
                    public void rename(String name) {
                        mTvFragmentMineName.setText(name);
                    }
                });
                break;
            case R.id.img_fragment_mine_head:
                Intent headIntent = new Intent(mActivity, UpdateHeadActivity.class);
                headIntent.putExtra("data", mAvatar);
                startActivity(headIntent);
                break;
            case R.id.ll_fragment_mine_punch:
                Intent punchIntent = new Intent(mActivity, PunchRecordActivity.class);
                punchIntent.putExtra("data", mMax_continue_days);
                startActivity(punchIntent);
                break;
            case R.id.ll_fragment_mine_prize:
                startActivity(new Intent(mActivity, WakePrizeActivity.class));
                break;
            case R.id.ll_fragment_mine_influence:
                startActivity(new Intent(mActivity, InfluencePrizeActivity.class));
                break;
            case R.id.ll_fragment_mine_ticket:
                startActivity(new Intent(mActivity, BoardTicketActivity.class));
                break;
            case R.id.ll_fragment_mine_problem:
                startActivity(new Intent(mActivity, ProblemActivity.class));
                break;
            case R.id.ll_fragment_mine_business:
                startActivity(new Intent(mActivity, BusinessTeamworkActivity.class));
                break;
            case R.id.ll_fragment_mine_function:
                startActivity(new Intent(mActivity, FunctionSetActivity.class));
                break;
            case R.id.img_fragment_mine_message:
                Intent messageIntent = new Intent(mActivity, PersonMessageActivity.class);
                messageIntent.putExtra("data", hasUnreadMessage);
                startActivity(messageIntent);
                break;
        }
    }
}
