package com.tourye.wake.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.beans.PunchBean;
import com.tourye.wake.beans.UserBasicBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.ui.activities.AchievementCardActivity;
import com.tourye.wake.utils.SaveUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:打卡成功窗口
 */

public class PunchSuccessDialog extends Dialog {
    private final Context mContext;
    private TextView mTvDialogPunchDays;
    private ImageView mImgDialogPunchHead;
    private TextView mTvDialogPunchWake;
    private TextView mTvDialogPunchBeat;
    private TextView mTvDialogPunchInsist;
    private TextView mTvDialogPunchShowOff;


    public PunchSuccessDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_punch);


        mContext = context;

        mTvDialogPunchDays = (TextView) findViewById(R.id.tv_dialog_punch_days);
        mImgDialogPunchHead = (ImageView) findViewById(R.id.img_dialog_punch_head);
        mTvDialogPunchWake = (TextView) findViewById(R.id.tv_dialog_punch_wake);
        mTvDialogPunchBeat = (TextView) findViewById(R.id.tv_dialog_punch_beat);
        mTvDialogPunchInsist = (TextView) findViewById(R.id.tv_dialog_punch_insist);
        mTvDialogPunchShowOff = (TextView) findViewById(R.id.tv_dialog_punch_show_off);

        mTvDialogPunchShowOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Intent intent = new Intent(mContext, AchievementCardActivity.class);
                mContext.startActivity(intent);
            }
        });

    }


    public void showDialog(PunchBean.DataBean data) {
        mTvDialogPunchWake.setText(data.getTime());
        mTvDialogPunchBeat.setText(data.getDefeat() + "%");
        mTvDialogPunchInsist.setText(data.getContinueX()+"");

        getUserBasicData();

        show();

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
                mTvDialogPunchDays.setText("第" + data.getTotal_days() + "天");
                Glide.with(BaseApplication.mApplicationContext).load(data.getAvatar()).into(mImgDialogPunchHead);
            }
        });
    }


}
