package com.tourye.wake.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.beans.PrivilegeConfigBean;
import com.tourye.wake.ui.activities.BoardTicketPrivilegeActivity;
import com.tourye.wake.utils.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longlongren on 2018/8/15.
 * <p>
 * introduce:局票特权实体
 */

public class BoardTicketPrivilegeAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PrivilegeConfigBean.DataBean> mPrivilegeConfigBeans=new ArrayList<>();

    public BoardTicketPrivilegeAdapter(Context context, List<PrivilegeConfigBean.DataBean> privilegeConfigBeans) {
        mContext = context;
        mLayoutInflater=LayoutInflater.from(mContext);
        mPrivilegeConfigBeans = privilegeConfigBeans;
    }

    @Override
    public int getCount() {
        return mPrivilegeConfigBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return mPrivilegeConfigBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BoardTicketPrivilegeHolder boardTicketPrivilegeHolder;
        if (view==null) {
            view=mLayoutInflater.inflate(R.layout.item_board_ticket_privilege,viewGroup,false);
            boardTicketPrivilegeHolder=new BoardTicketPrivilegeHolder(view);
            view.setTag(boardTicketPrivilegeHolder);
        }else{
            boardTicketPrivilegeHolder= (BoardTicketPrivilegeHolder) view.getTag();
        }
        final PrivilegeConfigBean.DataBean privilegeConfigBean = mPrivilegeConfigBeans.get(i);
        boardTicketPrivilegeHolder.mTvItemBoardTicketPrivilegeCount.setText(privilegeConfigBean.getPrice()+"局票");
        boardTicketPrivilegeHolder.mTvItemBoardTicketPrivilegeTitle.setText(privilegeConfigBean.getName());

        Glide.with(BaseApplication.mApplicationContext).load(privilegeConfigBean.getThumbnail()).transform( new GlideRoundTransform(BaseApplication.mApplicationContext,5)).into(boardTicketPrivilegeHolder.mImgItemBoardTicketPrivilege);
        boardTicketPrivilegeHolder.mTvItemBoardTicketPrivilegeExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BoardTicketPrivilegeActivity.class);
                intent.putExtra("data",privilegeConfigBean);
                mContext.startActivity(intent);
            }
        });
        boardTicketPrivilegeHolder.mTvItemBoardTicketCount.setText(privilegeConfigBean.getCount()+"");

        return view;
    }

    public class BoardTicketPrivilegeHolder{
        private ImageView mImgItemBoardTicketPrivilege;
        private TextView mTvItemBoardTicketPrivilegeTitle;
        private TextView mTvItemBoardTicketPrivilegeCount;
        private TextView mTvItemBoardTicketPrivilegeExchange;
        private TextView mTvItemBoardTicketCount;


        public BoardTicketPrivilegeHolder(View view){
            mImgItemBoardTicketPrivilege = (ImageView) view.findViewById(R.id.img_item_board_ticket_privilege);
            mTvItemBoardTicketPrivilegeTitle = (TextView) view.findViewById(R.id.tv_item_board_ticket_privilege_title);
            mTvItemBoardTicketPrivilegeCount = (TextView) view.findViewById(R.id.tv_item_board_ticket_privilege_count);
            mTvItemBoardTicketPrivilegeExchange = (TextView) view.findViewById(R.id.tv_item_board_ticket_privilege_exchange);
            mTvItemBoardTicketCount = (TextView) view.findViewById(R.id.tv_item_board_ticket_count);


        }
    }
}
