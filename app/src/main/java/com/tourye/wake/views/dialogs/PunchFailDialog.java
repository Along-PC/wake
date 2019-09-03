package com.tourye.wake.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.tourye.wake.utils.GlideCircleTransform;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:打卡失败窗口
 */

public class PunchFailDialog extends Dialog {
    private final Context mContext;
    private ImageView mImgDialogPunchFailHead;




    public PunchFailDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_punch_fail);
        mContext = context;

        mImgDialogPunchFailHead = (ImageView) findViewById(R.id.img_dialog_punch_fail_head);




    }


    public void showDialog() {

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
                Glide.with(BaseApplication.mApplicationContext).load(data.getAvatar()).transform(new GlideCircleTransform(BaseApplication.mApplicationContext)).into(mImgDialogPunchFailHead);
            }
        });
    }


}
