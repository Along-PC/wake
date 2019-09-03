package com.tourye.wake.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.beans.RankBean;
import com.tourye.wake.beans.SportRankBean;
import com.tourye.wake.utils.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/13.
 * <p>
 * introduce:早起排行适配器
 */

public class MorningFragmentListAdapter extends BaseAdapter {

    private List<RankBean> mRank = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    //类型-continue,thumb_up,wake_time
    public static final int TYPE_WAKE_UP = 10010;//早起
    public static final int TYPE_INSIST = 10011;//坚持
    public static final int TYPE_THUMB_UP = 10012;//膜拜
    public static final int TYPE_PRIZE = 10013;//奖金
    private int mType;

    public MorningFragmentListAdapter( Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }


    public void setData(List<RankBean> rank, int type) {
        mRank = rank;
        mType = type;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return mRank.size();
    }

    @Override
    public Object getItem(int i) {
        return mRank.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListFragmentMorningHolder listFragmentMorningHolder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_fragment_morning_rank, viewGroup, false);
            listFragmentMorningHolder = new ListFragmentMorningHolder(view);
            view.setTag(listFragmentMorningHolder);
        } else {
            listFragmentMorningHolder = (ListFragmentMorningHolder) view.getTag();
        }
        RankBean rankBean = mRank.get(i);

        if (i == 0) {
            listFragmentMorningHolder.mTvItemFragMorningRankOrder.setVisibility(View.GONE);
            Glide.with(mContext).load(rankBean.getAvatar()).placeholder(R.drawable.icon_head_default).transform( new GlideRoundTransform(BaseApplication.mApplicationContext,5)).into(listFragmentMorningHolder.mImgItemFragMorningRankHead);
            listFragmentMorningHolder.mTvItemFragMorningRankFirstName.setText(rankBean.getNickname());
            listFragmentMorningHolder.mTvItemFragMorningRankFirstOder.setText("第"+rankBean.getRank() + "名");
            listFragmentMorningHolder.mLlItemFragmentMorningRank.setVisibility(View.VISIBLE);
            listFragmentMorningHolder.mImgItemFragMorningRankOrder.setVisibility(View.GONE);
            listFragmentMorningHolder.mTvItemFragMorningRankName.setVisibility(View.GONE);
        } else {
            listFragmentMorningHolder.mLlItemFragmentMorningRank.setVisibility(View.GONE);
            listFragmentMorningHolder.mTvItemFragMorningRankName.setVisibility(View.VISIBLE);
            if (i == 1) {
                listFragmentMorningHolder.mImgItemFragMorningRankOrder.setVisibility(View.VISIBLE);
                Glide.with(BaseApplication.mApplicationContext).load(R.drawable.icon_gold_medal).into(listFragmentMorningHolder.mImgItemFragMorningRankOrder);
                listFragmentMorningHolder.mTvItemFragMorningRankOrder.setVisibility(View.GONE);
            } else if (i == 2) {
                listFragmentMorningHolder.mImgItemFragMorningRankOrder.setVisibility(View.VISIBLE);
                Glide.with(BaseApplication.mApplicationContext).load(R.drawable.icon_silver_medal).into(listFragmentMorningHolder.mImgItemFragMorningRankOrder);
                listFragmentMorningHolder.mTvItemFragMorningRankOrder.setVisibility(View.GONE);
            } else if (i == 3) {
                listFragmentMorningHolder.mImgItemFragMorningRankOrder.setVisibility(View.VISIBLE);
                Glide.with(BaseApplication.mApplicationContext).load(R.drawable.icon_bronze_medal).into(listFragmentMorningHolder.mImgItemFragMorningRankOrder);
                listFragmentMorningHolder.mTvItemFragMorningRankOrder.setVisibility(View.GONE);
            } else {
                listFragmentMorningHolder.mTvItemFragMorningRankOrder.setVisibility(View.VISIBLE);
                listFragmentMorningHolder.mTvItemFragMorningRankOrder.setText(i + "");
                listFragmentMorningHolder.mImgItemFragMorningRankOrder.setVisibility(View.GONE);
            }

            listFragmentMorningHolder.mTvItemFragMorningRankName.setText(rankBean.getNickname());
            listFragmentMorningHolder.mLlItemFragmentMorningRank.setVisibility(View.GONE);
            Glide.with(BaseApplication.mApplicationContext).load(rankBean.getAvatar()).placeholder(R.drawable.icon_head_default).transform( new GlideRoundTransform(BaseApplication.mApplicationContext,5)).into(listFragmentMorningHolder.mImgItemFragMorningRankHead);


        }

        switch (mType) {
            case TYPE_WAKE_UP:
                listFragmentMorningHolder.mTvItemFragMorning.setText(rankBean.getScore() + "");
                break;
            case TYPE_INSIST:
                listFragmentMorningHolder.mTvItemFragMorning.setText(rankBean.getScore() + "天");
                break;
            case TYPE_THUMB_UP:
                listFragmentMorningHolder.mTvItemFragMorning.setText(rankBean.getScore() + "人");
                break;
            case TYPE_PRIZE:
                String score = rankBean.getScore();
                float prize = Float.parseFloat(score);
                prize=prize/100;
                listFragmentMorningHolder.mTvItemFragMorning.setText(prize + "元");
                break;
        }
        return view;
    }

    public class ListFragmentMorningHolder {
        private TextView mTvItemFragMorningRankOrder;
        private ImageView mImgItemFragMorningRankHead;
        private TextView mTvItemFragMorningRankName;
        private TextView mTvItemFragMorningRankFirstName;
        private TextView mTvItemFragMorningRankFirstOder;
        private TextView mTvItemFragMorning;
        private ImageView mImgItemFragMorningRankOrder;
        private LinearLayout mLlItemFragmentMorningRank;


        public ListFragmentMorningHolder(View view) {
            mTvItemFragMorningRankOrder = (TextView) view.findViewById(R.id.tv_item_frag_morning_rank_order);
            mImgItemFragMorningRankHead = (ImageView) view.findViewById(R.id.img_item_frag_morning_rank_head);
            mTvItemFragMorningRankName = (TextView) view.findViewById(R.id.tv_item_frag_morning_rank_name);
            mTvItemFragMorningRankFirstName = (TextView) view.findViewById(R.id.tv_item_frag_morning_rank_first_name);
            mTvItemFragMorningRankFirstOder = (TextView) view.findViewById(R.id.tv_item_frag_morning_rank_first_oder);
            mTvItemFragMorning = (TextView) view.findViewById(R.id.tv_item_frag_morning);
            mImgItemFragMorningRankOrder = (ImageView) view.findViewById(R.id.img_item_frag_morning_rank_order);
            mLlItemFragmentMorningRank = (LinearLayout) view.findViewById(R.id.ll_item_fragment_morning_rank);

        }
    }
}
