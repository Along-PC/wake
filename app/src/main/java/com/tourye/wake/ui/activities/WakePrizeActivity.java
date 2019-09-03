package com.tourye.wake.ui.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.AliAuthBean;
import com.tourye.wake.beans.Commonbean;
import com.tourye.wake.beans.UserAccountBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.pay.zfb.AuthResult;
import com.tourye.wake.pay.zfb.PayResult;
import com.tourye.wake.pay.zfb.util.OrderInfoUtil2_0;

import java.util.HashMap;
import java.util.Map;

/**
 * WakePrizeActivity
 * author:along
 * 2018/8/23 上午11:36
 * <p>
 * 描述:早起奖金页面
 */

public class WakePrizeActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvWakePrizeDetail;
    private TextView mTvWakePrizeTitle;
    private TextView mTvWakePrizeCount;
    private EditText mEdtWakePrizeCount;
    private TextView mTvWakePrizeReflect;
    private float mPrize;//当前奖金

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户

                        String result = authResult.getResult();
                        String[] split = result.split("user_id=");
                        String[] splitFinal = split[1].split("&");
                        String user_id = splitFinal[0];
                        setUserId(user_id);
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(mActivity,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("早起奖金");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTvWakePrizeDetail = (TextView) findViewById(R.id.tv_wake_prize_detail);
        mTvWakePrizeTitle = (TextView) findViewById(R.id.tv_wake_prize_title);
        mTvWakePrizeCount = (TextView) findViewById(R.id.tv_wake_prize_count);
        mEdtWakePrizeCount = (EditText) findViewById(R.id.edt_wake_prize_count);
        mTvWakePrizeReflect = (TextView) findViewById(R.id.tv_wake_prize_reflect);

        mTvWakePrizeReflect.setOnClickListener(this);
        mTvWakePrizeDetail.setOnClickListener(this);

        mEdtWakePrizeCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CharSequence s=charSequence;
                //删除.后面超过两位的数字
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        mEdtWakePrizeCount.setText(s);
                        mEdtWakePrizeCount.setSelection(s.length());
                    }
                }

                //如果.在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mEdtWakePrizeCount.setText(s);
                    mEdtWakePrizeCount.setSelection(2);
                }

                //如果起始位置为0并且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mEdtWakePrizeCount.setText(s.subSequence(0, 1));
                        mEdtWakePrizeCount.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void initData() {
        getUserAccountData();
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
                mPrize = data.getPrize();
                mPrize = mPrize / 100;
                mTvWakePrizeCount.setText(mPrize + "");
            }
        });

    }

    public void withdrawAccount(final float count) {

        Map<String, String> map = new HashMap<>();
        map.put("type", "prize");
        int countFinal = (int) (count * 100);
        map.put("count", countFinal + "");
        HttpUtils.getInstance().post(Constants.ACCOUNT_WITHDRAW, map, new HttpCallback<Commonbean>() {
            @Override
            public void onSuccessExecute(Commonbean commonbean) {
                int status = commonbean.getStatus();
                if (status == 0) {
                    Toast.makeText(mActivity, "提现成功", Toast.LENGTH_SHORT).show();
                    mEdtWakePrizeCount.setText("");
                    mPrize=(float)(Math.round((mPrize - count) * 100)) / 100;
                    mTvWakePrizeCount.setText(mPrize+ "");
                } else if(status == 20001){
                    getAuthInfo();
                }else {
                    Toast.makeText(mActivity, "提现失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //设置支付宝用户id
    public void setUserId(String user_id) {
        Map<String, String> map = new HashMap<>();
        map.put("client_id", user_id);
        HttpUtils.getInstance().post(Constants.ZFB_AUTH_INFO, map, new HttpCallback<Commonbean>() {
            @Override
            public void onSuccessExecute(Commonbean commonbean) {
                int status = commonbean.getStatus();
                if (status == 0) {
                    //设置支付宝用户id成功
                    Toast.makeText(mActivity, "授权成功", Toast.LENGTH_SHORT).show();

                    String s = mEdtWakePrizeCount.getText().toString();
                    if (TextUtils.isEmpty(s)) {
                        Toast.makeText(mActivity, "请输入金额", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    float value = Float.parseFloat(s);
                    if (value > mPrize) {
                        Toast.makeText(mActivity, "请输入合法金额", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    withdrawAccount(value);
                }
            }
        });
    }


    public void getAuthInfo(){
        //获取支付宝授权需要的信息
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.ZFB_AUTH_INFO, map, new HttpCallback<AliAuthBean>() {
            @Override
            public void onSuccessExecute(AliAuthBean aliAuthBean) {
                AliAuthBean.DataBean data = aliAuthBean.getData();
                if (data != null) {
                    String parameters = data.getParameters();
                    if (!TextUtils.isEmpty(parameters)) {
                        authV2(parameters);
                    }
                }
            }
        });
    }


    /**
     * 支付宝账户授权业务
     *
     * @param parameters
     */
    public void authV2(final String parameters) {
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(mActivity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(parameters, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @Override
    public int getRootView() {
        return R.layout.activity_wake_prize;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wake_prize_reflect:
                String s = mEdtWakePrizeCount.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(mActivity, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                float value = Float.parseFloat(s);
                if(value<1){
                    Toast.makeText(mActivity, "单笔提现最低1元", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value > mPrize) {
                    Toast.makeText(mActivity, "不可超出可提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                withdrawAccount(value);
                break;
            case R.id.tv_wake_prize_detail:
                Intent intent = new Intent(this, AccountDetailActivity.class);
                intent.putExtra("data", "prize");
                startActivity(intent);
                break;
        }
    }
}
