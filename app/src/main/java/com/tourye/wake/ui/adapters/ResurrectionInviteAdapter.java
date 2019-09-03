package com.tourye.wake.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.beans.ResurrectionInviteBean;
import com.tourye.wake.utils.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:复活卡邀请列表适配器
 */

public class ResurrectionInviteAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ResurrectionInviteBean.DataBean> mResurrectionInviteBeans=new ArrayList<>();

    public ResurrectionInviteAdapter(Context context, List<ResurrectionInviteBean.DataBean> resurrectionInviteBeans) {
        mContext = context;
        mLayoutInflater=LayoutInflater.from(mContext);
        mResurrectionInviteBeans = resurrectionInviteBeans;
    }

    @Override
    public int getCount() {
        return mResurrectionInviteBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return mResurrectionInviteBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ResurrectionInviteHolder resurrectionInviteHolder;
        if (view==null) {
            view=mLayoutInflater.inflate(R.layout.item_resurrection_card_invite,viewGroup,false);
            resurrectionInviteHolder=new ResurrectionInviteHolder(view);
            view.setTag(resurrectionInviteHolder);
        }else{
            resurrectionInviteHolder= (ResurrectionInviteHolder) view.getTag();
        }
        ResurrectionInviteBean.DataBean dataBean = mResurrectionInviteBeans.get(i);
        Glide.with(mContext).load(dataBean.getAvatar()).placeholder(R.drawable.icon_head_default).transform( new GlideRoundTransform(BaseApplication.mApplicationContext,5)).into(resurrectionInviteHolder.mImgItemResurrectionCardInvite);
        resurrectionInviteHolder.mTvItemResurrectionCardInviteName.setText(dataBean.getNickname());
        resurrectionInviteHolder.mTvItemResurrectionCardInviteTime.setText(dataBean.getTime());
        return view;
    }

    public class ResurrectionInviteHolder{
        private ImageView mImgItemResurrectionCardInvite;
        private TextView mTvItemResurrectionCardInviteName;
        private TextView mTvItemResurrectionCardInviteTime;


        public ResurrectionInviteHolder(View view){
            mImgItemResurrectionCardInvite = (ImageView) view.findViewById(R.id.img_item_resurrection_card_invite);
            mTvItemResurrectionCardInviteName = (TextView) view.findViewById(R.id.tv_item_resurrection_card_invite_name);
            mTvItemResurrectionCardInviteTime = (TextView) view.findViewById(R.id.tv_item_resurrection_card_invite_time);

        }
    }
}
