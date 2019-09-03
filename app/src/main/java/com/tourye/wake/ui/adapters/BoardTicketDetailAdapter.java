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
 * Created by longlongren on 2018/8/16.
 * <p>
 * introduce:局票详细适配器
 */

public class BoardTicketDetailAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AccountDetailBean.DataBean> mDataBeans=new ArrayList<>();

    public BoardTicketDetailAdapter(Context context) {
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
        BoardTicketDetailHolder boardTicketDetailHolder;
        if (view==null) {
            view=mLayoutInflater.inflate(R.layout.item_board_ticket_detail,viewGroup,false);
            boardTicketDetailHolder=new BoardTicketDetailHolder(view);
            view.setTag(boardTicketDetailHolder);
        }else{
            boardTicketDetailHolder= (BoardTicketDetailHolder) view.getTag();
        }
        AccountDetailBean.DataBean dataBean = mDataBeans.get(i);
        boardTicketDetailHolder.mTvBoardTicketDetailTitle.setText(dataBean.getTitle());
        boardTicketDetailHolder.mTvBoardTicketDetailTime.setText(dataBean.getTime());
        boardTicketDetailHolder.mTvBoardTicketDetailCalculate.setText(dataBean.getCount()+"局票");
        return view;
    }

    public class BoardTicketDetailHolder{
        private TextView mTvBoardTicketDetailTitle;
        private TextView mTvBoardTicketDetailTime;
        private TextView mTvBoardTicketDetailCalculate;


        public BoardTicketDetailHolder(View view){
            mTvBoardTicketDetailTitle = (TextView) view.findViewById(R.id.tv_board_ticket_detail_title);
            mTvBoardTicketDetailTime = (TextView) view.findViewById(R.id.tv_board_ticket_detail_time);
            mTvBoardTicketDetailCalculate = (TextView) view.findViewById(R.id.tv_board_ticket_detail_calculate);

        }
    }
}
