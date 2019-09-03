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
import com.tourye.wake.beans.ResurrectionInviteBean;
import com.tourye.wake.beans.ResurrectionRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/14.
 * <p>
 * introduce:复活卡卡片记录列表适配器
 */

public class ResurrectionRecordAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ResurrectionRecordBean.DataBean> mResurrectionRecordBeans=new ArrayList<>();

    public ResurrectionRecordAdapter(Context context, List<ResurrectionRecordBean.DataBean> resurrectionInviteBeans) {
        mContext = context;
        mLayoutInflater=LayoutInflater.from(mContext);
        mResurrectionRecordBeans = resurrectionInviteBeans;
    }

    @Override
    public int getCount() {
        return mResurrectionRecordBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return mResurrectionRecordBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ResurrectionRecordHolder resurrectionRecordHolder;
        if (view==null) {
            view=mLayoutInflater.inflate(R.layout.item_resurrection_card_record,viewGroup,false);
            resurrectionRecordHolder=new ResurrectionRecordHolder(view);
            view.setTag(resurrectionRecordHolder);
        }else{
            resurrectionRecordHolder= (ResurrectionRecordHolder) view.getTag();
        }
        ResurrectionRecordBean.DataBean dataBean = mResurrectionRecordBeans.get(i);
        resurrectionRecordHolder.mTvItemResurrectionCardRecordTime.setText(dataBean.getTime());
        return view;
    }

    public class ResurrectionRecordHolder{
        private TextView mTvItemResurrectionCardRecordName;
        private TextView mTvItemResurrectionCardRecordTime;



        public ResurrectionRecordHolder(View view){
            mTvItemResurrectionCardRecordName = (TextView) view.findViewById(R.id.tv_item_resurrection_card_record_name);
            mTvItemResurrectionCardRecordTime = (TextView) view.findViewById(R.id.tv_item_resurrection_card_record_time);

        }
    }
}
