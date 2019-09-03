package com.tourye.wake.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourye.wake.R;

/**
 * Created by longlongren on 2018/9/6.
 * <p>
 * introduce:退出登陆提示弹框
 */

public class QuitDialog extends Dialog {
    private LinearLayout mLlDialogQuit;
    private TextView mTvDialogQuitCancel;
    private TextView mTvDialogQuitCertain;

    private QuitCallback mQuitCallback;
    public QuitDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_quit);

        mLlDialogQuit = (LinearLayout) findViewById(R.id.ll_dialog_quit);
        mTvDialogQuitCancel = (TextView) findViewById(R.id.tv_dialog_quit_cancel);
        mTvDialogQuitCertain = (TextView) findViewById(R.id.tv_dialog_quit_certain);

        mTvDialogQuitCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mTvDialogQuitCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuitCallback.quit();
                dismiss();
            }
        });

    }

    public void setQuitCallback(QuitCallback quitCallback) {
        mQuitCallback = quitCallback;
    }

    public interface QuitCallback{
        public void quit();
    }
}
