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
import com.tourye.wake.beans.InvitedUserBean;
import com.tourye.wake.utils.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/20.
 * <p>
 * introduce:
 */

public class InvitedPeopleAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<InvitedUserBean.DataBean> mInvitedUserBeans=new ArrayList<>();

    public InvitedPeopleAdapter(Context context) {
        mContext = context;
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    public void setInvitedUserBeans(List<InvitedUserBean.DataBean> invitedUserBeans) {
        mInvitedUserBeans = invitedUserBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mInvitedUserBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return mInvitedUserBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        InvitedPeopleHolder invitedPeopleHolder;
        if (view==null) {
            view=mLayoutInflater.inflate(R.layout.item_fragment_invited_people,viewGroup,false);
            invitedPeopleHolder=new InvitedPeopleHolder(view);
            view.setTag(invitedPeopleHolder);
        }else{
            invitedPeopleHolder= (InvitedPeopleHolder) view.getTag();
        }
        InvitedUserBean.DataBean invitedUserBean = mInvitedUserBeans.get(i);
        invitedPeopleHolder.mTvInvitedPeopleName.setText(invitedUserBean.getNickname());
        Glide.with(BaseApplication.mApplicationContext).load(invitedUserBean.getAvatar()).placeholder(R.drawable.icon_head_default).transform(new GlideCircleTransform(BaseApplication.mApplicationContext)).into(invitedPeopleHolder.mImgInvitedPeopleHead);

        return view;
    }

    public class InvitedPeopleHolder{
        private ImageView mImgInvitedPeopleHead;
        private TextView mTvInvitedPeopleName;


        public InvitedPeopleHolder(View view){
            mImgInvitedPeopleHead = (ImageView) view.findViewById(R.id.img_invited_people_head);
            mTvInvitedPeopleName = (TextView) view.findViewById(R.id.tv_invited_people_name);

        }
    }
}
