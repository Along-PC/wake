package com.tourye.wake.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tourye.wake.R;
import com.tourye.wake.beans.PunchRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/23.
 * <p>
 * introduce://打卡记录适配器
 */

public class PunchRecordAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PunchRecordBean.DataBean> mDataBeans=new ArrayList<>();

    public PunchRecordAdapter(Context context) {
        mContext = context;
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    public void setDataBeans(List<PunchRecordBean.DataBean> dataBeans) {
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
        PunchRecordHolder punchRecordHolder;
        if (view==null) {
            view=mLayoutInflater.inflate(R.layout.item_activity_punch_record,viewGroup,false);
            punchRecordHolder=new PunchRecordHolder(view);
            view.setTag(punchRecordHolder);
        }else{
            punchRecordHolder= (PunchRecordHolder) view.getTag();
        }
        PunchRecordBean.DataBean dataBean = mDataBeans.get(i);
        punchRecordHolder.mTvItemActivityPunchRecord.setText(dataBean.getDate()+"  "+dataBean.getTime());
        String profit = dataBean.getProfit();

        if (!TextUtils.isEmpty(profit)) {
            float prize = Integer.parseInt(profit);
            prize=prize/100;
            punchRecordHolder.mTvItemActivityPunchPrize.setText(prize+"元");
        }else{
            punchRecordHolder.mTvItemActivityPunchPrize.setText("");
        }
        return view;
    }

    public class PunchRecordHolder{
        private TextView mTvItemActivityPunchRecord;
        private TextView mTvItemActivityPunchPrize;


        public PunchRecordHolder(View view){
            mTvItemActivityPunchRecord = (TextView) view.findViewById(R.id.tv_item_activity_punch_record);
            mTvItemActivityPunchPrize = (TextView) view.findViewById(R.id.tv_item_activity_punch_prize);
        }
    }
}
