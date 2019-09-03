package com.tourye.wake.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tourye.wake.R;
import com.tourye.wake.beans.MessageListBean;
import com.tourye.wake.ui.activities.MessageDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by longlongren on 2018/8/27.
 * <p>
 * introduce:消息列表适配器
 */

public class PersonMessageAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MessageListBean.DataBean> mDataBeans = new ArrayList<>();

    public PersonMessageAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setDataBeans(List<MessageListBean.DataBean> dataBeans) {
        mDataBeans = dataBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PersonMessageHolder personMessageHolder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_activity_person_message, viewGroup, false);
            personMessageHolder = new PersonMessageHolder(view);
            view.setTag(personMessageHolder);
        } else {
            personMessageHolder = (PersonMessageHolder) view.getTag();
        }
        MessageListBean.DataBean dataBean = mDataBeans.get(i);
        personMessageHolder.mTvItemPersonMessageTitle.setText(dataBean.getTitle());
        personMessageHolder.mTvItemPersonMessageTime.setText(dataBean.getDate_time());
        String url = dataBean.getUrl();
        if (TextUtils.isEmpty(url)) {
            personMessageHolder.mImgItemPersonMessage.setVisibility(View.GONE);
        }else {
            personMessageHolder.mImgItemPersonMessage.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(url).into(personMessageHolder.mImgItemPersonMessage);
        }
        final String detail = dataBean.getDetail();
        if (TextUtils.isEmpty(detail)) {
            personMessageHolder.mLlItemPersonMessageTitle.setOnClickListener(null);
        }else{
            personMessageHolder.mLlItemPersonMessageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MessageDetailActivity.class);
                    intent.putExtra("data",detail);
                    mContext.startActivity(intent);
                }
            });
        }

        return view;
    }

    public class PersonMessageHolder {
        private TextView mTvItemPersonMessageTitle;
        private TextView mTvItemPersonMessageTime;
        private ImageView mImgItemPersonMessage;
        private LinearLayout mLlItemPersonMessageTitle;

        public PersonMessageHolder(View view) {
            mTvItemPersonMessageTitle = (TextView) view.findViewById(R.id.tv_item_person_message_title);
            mTvItemPersonMessageTime = (TextView) view.findViewById(R.id.tv_item_person_message_time);
            mImgItemPersonMessage = (ImageView) view.findViewById(R.id.img_item_person_message);
            mLlItemPersonMessageTitle = (LinearLayout) view.findViewById(R.id.ll_item_person_message_title);
        }
    }
}
