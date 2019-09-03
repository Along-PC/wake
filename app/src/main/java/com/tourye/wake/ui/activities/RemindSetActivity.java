package com.tourye.wake.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.Commonbean;
import com.tourye.wake.beans.RemindSetBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.utils.CalendarReminderUtils;
import com.tourye.wake.utils.PermissionDialogUtil;
import com.tourye.wake.utils.SaveUtil;
import com.tourye.wake.views.dialogs.RemindDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * RemindSetActivity
 * author:along
 * 2018/8/23 下午3:50
 * <p>
 * 描述:提醒设置页面
 */

public class RemindSetActivity extends BaseActivity {

    private Switch mSwitchActivityRemindSetWake;
    private RelativeLayout mRlActivityRemindSet;
    private TextView mTvActivityRemindSetTime;
    private Switch mSwitchActivityRemindSetSettleAccounts;
    private Switch mSwitchActivityRemindSetInvite;

    private int mType=0;//添加或者删除本地提醒标识
    private boolean mCanChange=false;//是否触发事件

    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("提醒设置");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mSwitchActivityRemindSetWake = (Switch) findViewById(R.id.switch_activity_remind_set_wake);
        mRlActivityRemindSet = (RelativeLayout) findViewById(R.id.rl_activity_remind_set);

        mTvActivityRemindSetTime = (TextView) findViewById(R.id.tv_activity_remind_set_time);
        mSwitchActivityRemindSetSettleAccounts = (Switch) findViewById(R.id.switch_activity_remind_set_settleAccounts);
        mSwitchActivityRemindSetInvite = (Switch) findViewById(R.id.switch_activity_remind_set_invite);

        mTvActivityRemindSetTime.setText(SaveUtil.getString("remind_time","5 : 00"));

        boolean remind_wake = SaveUtil.getBoolean("remind_wake", false);
        mSwitchActivityRemindSetWake.setChecked(remind_wake);
        if (remind_wake) {
            mRlActivityRemindSet.setVisibility(View.VISIBLE);
        } else {
            mRlActivityRemindSet.setVisibility(View.GONE);
        }

        mSwitchActivityRemindSetWake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mType=0;

                    mRlActivityRemindSet.setVisibility(View.VISIBLE);
                    requestPermissionRx(mType);
                } else {
                    if (mCanChange) {
                        return;
                    }
                    mType=1;
                    SaveUtil.putBoolean("remind_wake", false);
                    mRlActivityRemindSet.setVisibility(View.GONE);

                    //取消掉设置的闹钟
                    requestPermissionRx(mType);
                }
            }
        });
        mSwitchActivityRemindSetSettleAccounts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    setReceiveMessage("settle", 1, 1);
                } else {
                    setReceiveMessage("settle", 0, 1);
                }
            }
        });
        mSwitchActivityRemindSetInvite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    setReceiveMessage("invite", 1, 0);
                } else {
                    setReceiveMessage("invite", 0, 0);
                }
            }
        });
        mRlActivityRemindSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemindDialog remindDialog = new RemindDialog(mActivity);
                remindDialog.setCertainCallback(new RemindDialog.CertainCallback() {
                    @Override
                    public void certain(String time) {
                        mTvActivityRemindSetTime.setText(time);
                    }
                });
                remindDialog.show();
            }
        });
    }

    /**
     * 设置是否接收消息
     *
     * @param type
     * @param state 0-关闭   1-开启
     * @param who   哪个switch 1-早起结算  0-邀请成功
     */
    public void setReceiveMessage(String type, final int state, final int who) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("switch", state + "");
        HttpUtils.getInstance().post(Constants.SET_MESSAGE_RECEIVE, map, new HttpCallback<Commonbean>() {
            @Override
            public void onSuccessExecute(Commonbean commonbean) {
                int status = commonbean.getStatus();
                if (status != 0) {
                    Toast.makeText(mActivity, "设置提醒失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 申请本地提醒所需要的权限
     * type 1-取消闹钟  0-设置闹钟
     */
    public void requestPermissionRx(final int type) {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            if (type==0) {
                                SaveUtil.putBoolean("remind_wake", true);
                                setAlarm();
                            }else{
                                SaveUtil.putBoolean("remind_wake", false);
                                CalendarReminderUtils.deleteCalendarEvent(mActivity,"wake");
                            }
                        }else{
                            mCanChange=true;
                            mSwitchActivityRemindSetWake.setChecked(false);
                            SaveUtil.putBoolean("remind_wake", false);
                            PermissionDialogUtil.showPermissionDialog(mActivity,"缺少日历相关权限，请前往手机设置开启");
                            mCanChange=false;
                        }
                    }
                });
    }

    //设置定时提醒---默认五点
    public void setAlarm() {
        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.MINUTE,1);
        instance.set(Calendar.HOUR_OF_DAY, 5);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        CalendarReminderUtils.deleteCalendarEvent(mActivity,"wake");
        CalendarReminderUtils.addCalendarEvent(mActivity, "wake", "早起打卡", instance.getTimeInMillis(), 0);
    }

    @Override
    public void initData() {
        Map<String,String> map=new HashMap<>();
        HttpUtils.getInstance().get(Constants.SET_MESSAGE_RECEIVE, map, new HttpCallback<RemindSetBean>() {
            @Override
            public void onSuccessExecute(RemindSetBean remindSetBean) {
                RemindSetBean.DataBean data = remindSetBean.getData();
                if (data==null) {
                    return;
                }
                String invite = data.getInvite();
                if (!TextUtils.isEmpty(invite)) {
                    if ("1".equals(invite)) {
                        mSwitchActivityRemindSetInvite.setChecked(true);
                    }else if("0".equals(invite)){
                        mSwitchActivityRemindSetInvite.setChecked(false);
                    }
                }
                String settle = data.getSettle();
                if (!TextUtils.isEmpty(settle)) {
                    if ("1".equals(settle)) {
                        mSwitchActivityRemindSetSettleAccounts.setChecked(true);
                    }else if("0".equals(settle)){
                        mSwitchActivityRemindSetSettleAccounts.setChecked(false);
                    }
                }
            }
        });
    }

    @Override
    public int getRootView() {
        return R.layout.activity_remind_set;
    }

}
