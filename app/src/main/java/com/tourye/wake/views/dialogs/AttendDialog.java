package com.tourye.wake.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.beans.Commonbean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longlongren on 2018/9/3.
 * <p>
 * introduce:如果用户处于挑战模式，一进入首页就会出现弹窗让用户确认是否继续挑战模式（因为之前统一暂停了)
 */

public class AttendDialog extends Dialog {

    private final Context mContext;
    private TextView mTvDialogAttendCancel;
    private TextView mTvDialogAttendCertain;


    public AttendDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_attend);

        mContext = context;
        mTvDialogAttendCancel = (TextView) findViewById(R.id.tv_dialog_attend_cancel);
        mTvDialogAttendCertain = (TextView) findViewById(R.id.tv_dialog_attend_certain);

        mTvDialogAttendCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mTvDialogAttendCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> map=new HashMap<>();
                HttpUtils.getInstance().get(Constants.ACTIVE_CHALLENGE, map, new HttpCallback<Commonbean>() {
                    @Override
                    public void onSuccessExecute(Commonbean commonbean) {
                        int status = commonbean.getStatus();
                        if (status==0) {
                            Toast.makeText(mContext, "参与成功", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }else{
                            Toast.makeText(mContext, "参与失败", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }
                });
            }
        });
    }
}
