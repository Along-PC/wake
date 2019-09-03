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
import com.tourye.wake.beans.InfluenceRankBean;
import com.tourye.wake.utils.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/20.
 * <p>
 * introduce:导航页影响适配器
 */

public class InfluenceRankAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<InfluenceRankBean.DataBean.RankBean> mInfluenceRankBeans=new ArrayList<>();

    public InfluenceRankAdapter(Context context) {
        mContext = context;
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    public void setInfluenceRankBeans(List<InfluenceRankBean.DataBean.RankBean> influenceRankBeans) {
        mInfluenceRankBeans = influenceRankBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mInfluenceRankBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return mInfluenceRankBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        InfluenceRankHolder influenceRankHolder;
        if (view==null) {
            view=mLayoutInflater.inflate(R.layout.item_fragment_influence_rank,viewGroup,false);
            influenceRankHolder=new InfluenceRankHolder(view);
            view.setTag(influenceRankHolder);
        }else{
            influenceRankHolder= (InfluenceRankHolder) view.getTag();
        }
        InfluenceRankBean.DataBean.RankBean influenceRankBean = mInfluenceRankBeans.get(i);
        if (i==0) {
            influenceRankHolder.mTvItemInfluenceRankRanking.setVisibility(View.INVISIBLE);
            influenceRankHolder.mImgItemInfluenceRankPrize.setVisibility(View.GONE);
            influenceRankHolder.mLlItemInfluenceRank.setVisibility(View.VISIBLE);
            influenceRankHolder.mTvItemInfluenceRankName.setVisibility(View.GONE);
            Glide.with(BaseApplication.mApplicationContext).load(influenceRankBean.getAvatar()).placeholder(R.drawable.icon_head_default).transform( new GlideRoundTransform(BaseApplication.mApplicationContext,5)).into(influenceRankHolder.mImgItemInfluenceRankHead);
            influenceRankHolder.mTvItemInfluenceRankSelfName.setText(influenceRankBean.getNickname());
            influenceRankHolder.mTvItemInfluenceRankSelfOrder.setText("第"+influenceRankBean.getRank()+"名");
            influenceRankHolder.mTvItemInfluenceRankCount.setText(influenceRankBean.getScore()+"人");
        }else if(i==1){
            influenceRankHolder.mTvItemInfluenceRankRanking.setVisibility(View.GONE);
            influenceRankHolder.mImgItemInfluenceRankPrize.setVisibility(View.VISIBLE);
            influenceRankHolder.mLlItemInfluenceRank.setVisibility(View.GONE);
            influenceRankHolder.mTvItemInfluenceRankName.setVisibility(View.VISIBLE);
            Glide.with(BaseApplication.mApplicationContext).load(influenceRankBean.getAvatar()).placeholder(R.drawable.icon_head_default).transform( new GlideRoundTransform(BaseApplication.mApplicationContext,5)).into(influenceRankHolder.mImgItemInfluenceRankHead);
            influenceRankHolder.mTvItemInfluenceRankName.setText(influenceRankBean.getNickname());
            Glide.with(BaseApplication.mApplicationContext).load(R.drawable.icon_gold_medal).into(influenceRankHolder.mImgItemInfluenceRankPrize);
            influenceRankHolder.mTvItemInfluenceRankCount.setText(influenceRankBean.getScore()+"人");

        }else if(i==2){
            influenceRankHolder.mTvItemInfluenceRankRanking.setVisibility(View.GONE);
            influenceRankHolder.mImgItemInfluenceRankPrize.setVisibility(View.VISIBLE);
            influenceRankHolder.mLlItemInfluenceRank.setVisibility(View.GONE);
            influenceRankHolder.mTvItemInfluenceRankName.setVisibility(View.VISIBLE);
            Glide.with(BaseApplication.mApplicationContext).load(influenceRankBean.getAvatar()).placeholder(R.drawable.icon_head_default).transform( new GlideRoundTransform(BaseApplication.mApplicationContext,5)).into(influenceRankHolder.mImgItemInfluenceRankHead);
            influenceRankHolder.mTvItemInfluenceRankName.setText(influenceRankBean.getNickname());
            Glide.with(BaseApplication.mApplicationContext).load(R.drawable.icon_silver_medal).into(influenceRankHolder.mImgItemInfluenceRankPrize);
            influenceRankHolder.mTvItemInfluenceRankCount.setText(influenceRankBean.getScore()+"人");
        }else if(i==3){
            influenceRankHolder.mTvItemInfluenceRankRanking.setVisibility(View.GONE);
            influenceRankHolder.mImgItemInfluenceRankPrize.setVisibility(View.VISIBLE);
            influenceRankHolder.mLlItemInfluenceRank.setVisibility(View.GONE);
            influenceRankHolder.mTvItemInfluenceRankName.setVisibility(View.VISIBLE);
            Glide.with(BaseApplication.mApplicationContext).load(influenceRankBean.getAvatar()).placeholder(R.drawable.icon_head_default).transform( new GlideRoundTransform(BaseApplication.mApplicationContext,5)).into(influenceRankHolder.mImgItemInfluenceRankHead);
            influenceRankHolder.mTvItemInfluenceRankName.setText(influenceRankBean.getNickname());
            Glide.with(BaseApplication.mApplicationContext).load(R.drawable.icon_bronze_medal).into(influenceRankHolder.mImgItemInfluenceRankPrize);
            influenceRankHolder.mTvItemInfluenceRankCount.setText(influenceRankBean.getScore()+"人");
        }else{
            influenceRankHolder.mTvItemInfluenceRankRanking.setVisibility(View.VISIBLE);
            influenceRankHolder.mImgItemInfluenceRankPrize.setVisibility(View.GONE);
            influenceRankHolder.mLlItemInfluenceRank.setVisibility(View.GONE);
            influenceRankHolder.mTvItemInfluenceRankName.setVisibility(View.VISIBLE);
            Glide.with(BaseApplication.mApplicationContext).load(influenceRankBean.getAvatar()).placeholder(R.drawable.icon_head_default).transform( new GlideRoundTransform(BaseApplication.mApplicationContext,5)).into(influenceRankHolder.mImgItemInfluenceRankHead);
            influenceRankHolder.mTvItemInfluenceRankName.setText(influenceRankBean.getNickname());
            influenceRankHolder.mTvItemInfluenceRankRanking.setText(i+"");
            influenceRankHolder.mTvItemInfluenceRankCount.setText(influenceRankBean.getScore()+"人");
        }
        return view;
    }

    public class InfluenceRankHolder{
        private TextView mTvItemInfluenceRankRanking;//排位文字
        private ImageView mImgItemInfluenceRankPrize;//排名奖杯
        private ImageView mImgItemInfluenceRankHead;//头像
        private LinearLayout mLlItemInfluenceRank;
        private TextView mTvItemInfluenceRankSelfName;//自己昵称
        private TextView mTvItemInfluenceRankSelfOrder;//自己排名
        private TextView mTvItemInfluenceRankName;//列表昵称
        private TextView mTvItemInfluenceRankCount;//影响人数


        public InfluenceRankHolder(View view){
            mTvItemInfluenceRankRanking = (TextView) view.findViewById(R.id.tv_item_influence_rank_ranking);
            mImgItemInfluenceRankPrize = (ImageView) view.findViewById(R.id.img_item_influence_rank_prize);
            mImgItemInfluenceRankHead = (ImageView) view.findViewById(R.id.img_item_influence_rank_head);
            mLlItemInfluenceRank = (LinearLayout) view.findViewById(R.id.ll_item_influence_rank);
            mTvItemInfluenceRankSelfName = (TextView) view.findViewById(R.id.tv_item_influence_rank_self_name);
            mTvItemInfluenceRankSelfOrder = (TextView) view.findViewById(R.id.tv_item_influence_rank_self_order);
            mTvItemInfluenceRankName = (TextView) view.findViewById(R.id.tv_item_influence_rank_name);
            mTvItemInfluenceRankCount = (TextView) view.findViewById(R.id.tv_item_influence_rank_count);

        }
    }
}
