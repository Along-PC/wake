package com.tourye.wake.views.dialogs;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.wake.R;
import com.tourye.wake.utils.CalendarReminderUtils;
import com.tourye.wake.utils.SaveUtil;
import com.tourye.wake.views.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by longlongren on 2018/8/24.
 * <p>
 * introduce:提醒设置时间弹框
 */

public class RemindDialog extends Dialog implements View.OnClickListener {
    private final Context mContext;
    private WheelView mWheelDialogRemindHour;
    private WheelView mWheelDialogRemindMinute;
    private TextView mTvDialogRemindCancel;
    private TextView mTvDialogRemindCertain;

    private CertainCallback mCertainCallback;//当选定时间后更新页面提示时间


    public RemindDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_remind);
        mContext = context;
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        getWindow().setWindowAnimations(R.style.HeadDialogStyle);

        mWheelDialogRemindHour = (WheelView) findViewById(R.id.wheel_dialog_remind_hour);
        mWheelDialogRemindMinute = (WheelView) findViewById(R.id.wheel_dialog_remind_minute);
        mTvDialogRemindCancel = (TextView) findViewById(R.id.tv_dialog_remind_cancel);
        mTvDialogRemindCertain = (TextView) findViewById(R.id.tv_dialog_remind_certain);

        mTvDialogRemindCancel.setOnClickListener(this);
        mTvDialogRemindCertain.setOnClickListener(this);

        ArrayList<String> listHour=new ArrayList<>();
        listHour.add("5");
        listHour.add("6");
        listHour.add("7");
        ArrayList<String> listMinute=new ArrayList<>();
        listMinute.add("00");
        listMinute.add("10");
        listMinute.add("20");
        listMinute.add("30");
        listMinute.add("40");
        listMinute.add("50");
        mWheelDialogRemindHour.setData(listHour);
        mWheelDialogRemindMinute.setData(listMinute);
    }

    public void setCertainCallback(CertainCallback certainCallback) {
        mCertainCallback = certainCallback;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_remind_cancel:
                dismiss();
                break;
            case R.id.tv_dialog_remind_certain:
                String selectedHour = mWheelDialogRemindHour.getSelectedText();
                String selectedMinute = mWheelDialogRemindMinute.getSelectedText();
                int hour = Integer.parseInt(selectedHour);
                int minute = Integer.parseInt(selectedMinute);

                String finalTime = selectedHour + " : " + selectedMinute;
                mCertainCallback.certain(finalTime);
                SaveUtil.putString("remind_time",finalTime);

                Calendar calendar = Calendar.getInstance();
                //是设置日历的时间，主要是让日历的年月日和当前同步
//                calendar.setTimeInMillis(System.currentTimeMillis());
                // 这里时区需要设置一下，不然可能个别手机会有8个小时的时间差
                calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//                calendar.add(Calendar.DATE, 1);
                //设置在几点提醒  设置的为15点
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                //设置在几分提醒  设置的为50分
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                //先取消上次的日程
                CalendarReminderUtils.deleteCalendarEvent(mContext,"wake");
                //再设置新的日程
                CalendarReminderUtils.addCalendarEvent(mContext,"wake","早起打卡",calendar.getTimeInMillis(),0);
                Toast.makeText(mContext, "设置提醒成功", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }


    public interface CertainCallback{
        public void certain(String time);
    }
}
