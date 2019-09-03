package com.tourye.wake.views.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.beans.OrderBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.pay.zfb.PayResult;
import com.tourye.wake.utils.SaveUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:早起详细规则
 */

public class WakeRuleDialog extends Dialog {
    private final Context mContext;
    private TextView mTvDialogWakeRule;

    //支付宝支付flag
    private static final int SDK_PAY_FLAG = 1;

    private SuccessCallback mSuccessCallback;//支付成功回调

    public void setSuccessCallback(SuccessCallback successCallback) {
        mSuccessCallback = successCallback;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        mTvDialogWakeRule.setText("已报名");
                        mTvDialogWakeRule.setSelected(true);
                        mTvDialogWakeRule.setOnClickListener(null);
                        SaveUtil.putBoolean("pay_state",true);
                        mSuccessCallback.success();
                    } else {
                        SaveUtil.putBoolean("pay_state",false);
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    public WakeRuleDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_wake_rule);
        mContext = context;

        mTvDialogWakeRule = (TextView) findViewById(R.id.tv_dialog_wake_rule);
        boolean pay_state = SaveUtil.getBoolean("pay_state", false);
        mTvDialogWakeRule.setSelected(pay_state);
        if (!pay_state) {
            mTvDialogWakeRule.setText("支付10元挑战金参与");
            mTvDialogWakeRule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pay();
                }
            });
        }else{
            mTvDialogWakeRule.setText("已报名");
            mTvDialogWakeRule.setOnClickListener(null);
        }

    }

    /**
     * 支付十元挑战金
     */
    private void pay() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().post(Constants.PAY_ATTEND, map, new HttpCallback<OrderBean>() {
            @Override
            public void onSuccessExecute(OrderBean orderBean) {
                OrderBean.DataBean data = orderBean.getData();
                if (data == null) {
                    return;
                }
                final String orderInfo = data.getParameters();
                if (!TextUtils.isEmpty(orderInfo)) {
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
                            PayTask alipay = new PayTask((Activity) mContext);
                            Map<String, String> result = alipay.payV2(orderInfo, true);
                            Log.i("msp", result.toString());

                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    };

                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }
            }
        });
    }


    public interface SuccessCallback{
        public void success();
    }
}
