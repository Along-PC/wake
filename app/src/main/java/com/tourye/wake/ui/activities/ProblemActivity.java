package com.tourye.wake.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
/**
 * ProblemActivity
 * author:along
 * 2018/8/23 上午10:50
 *
 * 描述:常见问题页面
 */

public class ProblemActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlActivityProblemPunch;
    private ImageView mImgActivityProblemPunch;
    private LinearLayout mLlActivityProblemChallenge;
    private ImageView mImgActivityProblemChallenge;
    private RelativeLayout mRlActivityProblemChallenge;
    private TextView mTvActivityProblemChallenge;
    private RelativeLayout mRlActivityProblemPunch;
    private TextView mTvActivityProblemPunch;
    private LinearLayout mLlActivityProblemPrize;
    private ImageView mImgActivityProblemPrize;
    private RelativeLayout mRlActivityProblemPrize;
    private TextView mTvActivityProblemPrize;
    private LinearLayout mLlActivityProblemReflect;
    private ImageView mImgActivityProblemReflect;
    private RelativeLayout mRlActivityProblemReflect;
    private TextView mTvActivityProblemReflect;
    private LinearLayout mLlActivityProblemInfluence;
    private ImageView mImgActivityProblemInfluence;
    private RelativeLayout mRlActivityProblemInfluence;
    private TextView mTvActivityProblemInfluence;
    private LinearLayout mLlActivityProblemTicket;
    private ImageView mImgActivityProblemTicket;
    private RelativeLayout mRlActivityProblemTicket;
    private TextView mTvActivityProblemTicket;
    private LinearLayout mLlActivityProblemForget;
    private ImageView mImgActivityProblemForget;
    private RelativeLayout mRlActivityProblemForget;
    private TextView mTvActivityProblemForget;


    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("常见问题");
        mImgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLlActivityProblemPunch = (LinearLayout) findViewById(R.id.ll_activity_problem_punch);
        mImgActivityProblemPunch = (ImageView) findViewById(R.id.img_activity_problem_punch);
        mLlActivityProblemChallenge = (LinearLayout) findViewById(R.id.ll_activity_problem_challenge);
        mImgActivityProblemChallenge = (ImageView) findViewById(R.id.img_activity_problem_challenge);
        mRlActivityProblemChallenge = (RelativeLayout) findViewById(R.id.rl_activity_problem_challenge);
        mTvActivityProblemChallenge = (TextView) findViewById(R.id.tv_activity_problem_challenge);
        mRlActivityProblemPunch = (RelativeLayout) findViewById(R.id.rl_activity_problem_punch);
        mTvActivityProblemPunch = (TextView) findViewById(R.id.tv_activity_problem_punch);
        mLlActivityProblemPrize = (LinearLayout) findViewById(R.id.ll_activity_problem_prize);
        mImgActivityProblemPrize = (ImageView) findViewById(R.id.img_activity_problem_prize);
        mRlActivityProblemPrize = (RelativeLayout) findViewById(R.id.rl_activity_problem_prize);
        mTvActivityProblemPrize = (TextView) findViewById(R.id.tv_activity_problem_prize);
        mLlActivityProblemReflect = (LinearLayout) findViewById(R.id.ll_activity_problem_reflect);
        mImgActivityProblemReflect = (ImageView) findViewById(R.id.img_activity_problem_reflect);
        mRlActivityProblemReflect = (RelativeLayout) findViewById(R.id.rl_activity_problem_reflect);
        mTvActivityProblemReflect = (TextView) findViewById(R.id.tv_activity_problem_reflect);
        mLlActivityProblemInfluence = (LinearLayout) findViewById(R.id.ll_activity_problem_influence);
        mImgActivityProblemInfluence = (ImageView) findViewById(R.id.img_activity_problem_influence);
        mRlActivityProblemInfluence = (RelativeLayout) findViewById(R.id.rl_activity_problem_influence);
        mTvActivityProblemInfluence = (TextView) findViewById(R.id.tv_activity_problem_influence);
        mLlActivityProblemTicket = (LinearLayout) findViewById(R.id.ll_activity_problem_ticket);
        mImgActivityProblemTicket = (ImageView) findViewById(R.id.img_activity_problem_ticket);
        mRlActivityProblemTicket = (RelativeLayout) findViewById(R.id.rl_activity_problem_ticket);
        mTvActivityProblemTicket = (TextView) findViewById(R.id.tv_activity_problem_ticket);
        mLlActivityProblemForget = (LinearLayout) findViewById(R.id.ll_activity_problem_forget);
        mImgActivityProblemForget = (ImageView) findViewById(R.id.img_activity_problem_forget);
        mRlActivityProblemForget = (RelativeLayout) findViewById(R.id.rl_activity_problem_forget);
        mTvActivityProblemForget = (TextView) findViewById(R.id.tv_activity_problem_forget);

        mLlActivityProblemPunch.setOnClickListener(this);
        mLlActivityProblemChallenge.setOnClickListener(this);
        mLlActivityProblemPrize.setOnClickListener(this);
        mLlActivityProblemReflect.setOnClickListener(this);
        mLlActivityProblemForget.setOnClickListener(this);
        mLlActivityProblemTicket.setOnClickListener(this);
        mLlActivityProblemInfluence.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getRootView() {
        return R.layout.activity_problem;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_activity_problem_punch:
                if (mImgActivityProblemPunch.isSelected()) {
                    mRlActivityProblemPunch.setVisibility(View.GONE);
                    mImgActivityProblemPunch.setSelected(false);
                }else{
                    mRlActivityProblemPunch.setVisibility(View.VISIBLE);
                    mImgActivityProblemPunch.setSelected(true);
                }
                break;
            case R.id.ll_activity_problem_challenge:
                if (mImgActivityProblemChallenge.isSelected()) {
                    mRlActivityProblemChallenge.setVisibility(View.GONE);
                    mImgActivityProblemChallenge.setSelected(false);
                }else{
                    mRlActivityProblemChallenge.setVisibility(View.VISIBLE);
                    mImgActivityProblemChallenge.setSelected(true);
                }
                break;
            case R.id.ll_activity_problem_prize:
                if (mImgActivityProblemPrize.isSelected()) {
                    mRlActivityProblemPrize.setVisibility(View.GONE);
                    mImgActivityProblemPrize.setSelected(false);
                }else{
                    mRlActivityProblemPrize.setVisibility(View.VISIBLE);
                    mImgActivityProblemPrize.setSelected(true);
                }
                break;
            case R.id.ll_activity_problem_reflect:
                if (mImgActivityProblemReflect.isSelected()) {
                    mRlActivityProblemReflect.setVisibility(View.GONE);
                    mImgActivityProblemReflect.setSelected(false);
                }else{
                    mRlActivityProblemReflect.setVisibility(View.VISIBLE);
                    mImgActivityProblemReflect.setSelected(true);
                }
                break;
            case R.id.ll_activity_problem_influence:
                if (mImgActivityProblemInfluence.isSelected()) {
                    mRlActivityProblemInfluence.setVisibility(View.GONE);
                    mImgActivityProblemInfluence.setSelected(false);
                }else{
                    mRlActivityProblemInfluence.setVisibility(View.VISIBLE);
                    mImgActivityProblemInfluence.setSelected(true);
                }
                break;
            case R.id.ll_activity_problem_ticket:
                if (mImgActivityProblemTicket.isSelected()) {
                    mRlActivityProblemTicket.setVisibility(View.GONE);
                    mImgActivityProblemTicket.setSelected(false);
                }else{
                    mRlActivityProblemTicket.setVisibility(View.VISIBLE);
                    mImgActivityProblemTicket.setSelected(true);
                }
                break;
            case R.id.ll_activity_problem_forget:
                if (mImgActivityProblemForget.isSelected()) {
                    mRlActivityProblemForget.setVisibility(View.GONE);
                    mImgActivityProblemForget.setSelected(false);
                }else{
                    mRlActivityProblemForget.setVisibility(View.VISIBLE);
                    mImgActivityProblemForget.setSelected(true);
                }
                break;
        }
    }
}
