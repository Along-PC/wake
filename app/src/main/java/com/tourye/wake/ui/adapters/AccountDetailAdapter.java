package com.tourye.wake.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tourye.wake.R;
import com.tourye.wake.beans.AccountDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/23.
 * <p>
 * introduce:账户详细适配器
 */

public class AccountDetailAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AccountDetailBean.DataBean> mDataBeans=new ArrayList<>();

    public AccountDetailAdapter(Context context) {
        mContext = context;
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    public void setDataBeans(List<AccountDetailBean.DataBean> dataBeans) {
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
        AccountDetailHolder accountDetailHolder;
        if (view==null) {
            view=mLayoutInflater.inflate(R.layout.item_activity_account_detail,viewGroup,false);
            accountDetailHolder=new AccountDetailHolder(view);
            view.setTag(accountDetailHolder);
        }else{
            accountDetailHolder= (AccountDetailHolder) view.getTag();
        }
        AccountDetailBean.DataBean dataBean = mDataBeans.get(i);
        accountDetailHolder.mTvItemAccountDetailTitle.setText(dataBean.getTitle()+"");
        accountDetailHolder.mTvItemAccountDetailTime.setText(dataBean.getTime()+"");
        float count = dataBean.getCount();
        accountDetailHolder.mTvItemAccountDetailCount.setText(count/100+"");
        return view;
    }

    public class AccountDetailHolder{
        private TextView mTvItemAccountDetailTitle;
        private TextView mTvItemAccountDetailTime;
        private TextView mTvItemAccountDetailCount;


        public AccountDetailHolder(View view){
            mTvItemAccountDetailTitle = (TextView) view.findViewById(R.id.tv_item_account_detail_title);
            mTvItemAccountDetailTime = (TextView) view.findViewById(R.id.tv_item_account_detail_time);
            mTvItemAccountDetailCount = (TextView) view.findViewById(R.id.tv_item_account_detail_count);

        }
    }
}
