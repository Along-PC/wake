package com.tourye.wake.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tourye.wake.R;
import com.tourye.wake.views.WheelView;

import java.util.ArrayList;

/**
 * Created by longlongren on 2018/9/1.
 * <p>
 * introduce:登陆页面区域选择
 */

public class RegionDialog extends Dialog {
    private TextView mTvDialogRegionCancel;
    private TextView mTvDialogRegionCertain;
    private WheelView mWheelDialogRegion;
    private UpdateCallback mUpdateCallback;


    public RegionDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_region);

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        getWindow().setWindowAnimations(R.style.HeadDialogStyle);


        mTvDialogRegionCancel = (TextView) findViewById(R.id.tv_dialog_region_cancel);
        mTvDialogRegionCertain = (TextView) findViewById(R.id.tv_dialog_region_certain);
        mWheelDialogRegion = (WheelView) findViewById(R.id.wheel_dialog_region);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("中国大陆  +86");
        strings.add("香港  +852");
        strings.add("澳门  +853");
        strings.add("台湾  +886");

        mWheelDialogRegion.setData(strings);

        mTvDialogRegionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mTvDialogRegionCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUpdateCallback.update(mWheelDialogRegion.getSelected());
                dismiss();
            }
        });

    }

    public void setUpdateCallback(UpdateCallback updateCallback) {
        mUpdateCallback = updateCallback;
    }

    public interface UpdateCallback{
        public void update(int region);
    }

}
