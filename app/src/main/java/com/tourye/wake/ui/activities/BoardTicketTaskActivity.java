package com.tourye.wake.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.views.dialogs.ShareDialog;

/**
 * BoardTicketTaskActivity
 * author:along
 * 2018/8/15 下午7:30
 *
 * 描述:局票任务页面
 */

public class BoardTicketTaskActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvBoardTicketTaskPunch;
    private TextView mTvBoardTicketTaskInvite;
    private TextView mTvBoardTicketTaskAttend;


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("局票任务");
        mImgCertain.setBackgroundResource(R.drawable.icon_share);

        mTvBoardTicketTaskPunch = (TextView) findViewById(R.id.tv_board_ticket_task_punch);
        mTvBoardTicketTaskInvite = (TextView) findViewById(R.id.tv_board_ticket_task_invite);
        mTvBoardTicketTaskAttend = (TextView) findViewById(R.id.tv_board_ticket_task_attend);

        mTvBoardTicketTaskPunch.setOnClickListener(this);
        mTvBoardTicketTaskInvite.setOnClickListener(this);
        mTvBoardTicketTaskAttend.setOnClickListener(this);
        mImgCertain.setOnClickListener(this);
        mImgReturn.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public int getRootView() {
        return R.layout.activity_board_ticket_task;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mActivity,MainActivity.class);
        switch (view.getId()) {
            case R.id.tv_board_ticket_task_punch:
                intent.putExtra("type",1);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_board_ticket_task_invite:
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case R.id.tv_board_ticket_task_attend:
                intent.putExtra("type",1);
                startActivity(intent);
                finish();
                break;
            case R.id.img_return:
                finish();
                break;
            case R.id.img_certain:
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.showDialogTemp("/wake/point_task");
                break;
        }
    }
}
