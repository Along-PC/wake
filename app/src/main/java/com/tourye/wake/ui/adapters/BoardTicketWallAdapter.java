package com.tourye.wake.ui.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseApplication;
import com.tourye.wake.beans.WallPaperBean;
import com.tourye.wake.beans.WallPaperPurchaseBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by longlongren on 2018/8/15.
 * <p>
 * introduce:局票墙纸列表适配器
 */

public class BoardTicketWallAdapter  extends BaseAdapter{

    private Activity mActivity;
    private LayoutInflater mLayoutInflater;
    private List<WallPaperBean.DataBean> mDataBeans=new ArrayList<>();
    private DownloadCallback mDownloadCallback;
    private PurchaseCallback mPurchaseCallback;


    public BoardTicketWallAdapter(Activity context) {
        mActivity = context;
        mLayoutInflater=LayoutInflater.from(mActivity);
    }

    public void setDownloadCallback(DownloadCallback downloadCallback) {
        mDownloadCallback = downloadCallback;
    }

    public void setPurchaseCallback(PurchaseCallback purchaseCallback) {
        mPurchaseCallback = purchaseCallback;
    }

    public void setDataBeans(List<WallPaperBean.DataBean> dataBeans) {
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
        final BoardTicketWallHolder boardTicketWallHolder;
        if (view==null) {
            view=mLayoutInflater.inflate(R.layout.item_board_ticket_wall,viewGroup,false);
            boardTicketWallHolder=new BoardTicketWallHolder(view);
            view.setTag(boardTicketWallHolder);
        }else{
            boardTicketWallHolder= (BoardTicketWallHolder) view.getTag();
        }
        final WallPaperBean.DataBean dataBean = mDataBeans.get(i);
        Glide.with(BaseApplication.mApplicationContext).load(dataBean.getUrl()).into(boardTicketWallHolder.mImgItemBoardTicketWall);
        boardTicketWallHolder.mTvItemBoardTicketWallPrice.setText(dataBean.getPrice()+"局票");
        if (dataBean.isPurchased()) {
            boardTicketWallHolder.mTvItemBoardTicketWall.setText("下载");
            boardTicketWallHolder.mTvItemBoardTicketWall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDownloadCallback.download(dataBean.getUrl());
                }
            });
        }else{
            boardTicketWallHolder.mTvItemBoardTicketWall.setText("立即兑换");
            boardTicketWallHolder.mTvItemBoardTicketWall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    purchaseWallPaper(boardTicketWallHolder.mTvItemBoardTicketWall,dataBean);
                }
            });
        }
        return view;
    }

    /**
     *
     * @param textView 当前点击对象
     * @param dataBean 对象对应数据
     */
    public void purchaseWallPaper(final TextView textView, final WallPaperBean.DataBean dataBean){
        Map<String,String> map=new HashMap<>();
        map.put("id",dataBean.getId()+"");
        HttpUtils.getInstance().post(Constants.WALL_PAPER_PURCHASE, map, new HttpCallback<WallPaperPurchaseBean>() {
            @Override
            public void onSuccessExecute(WallPaperPurchaseBean wallPaperPurchaseBean) {
                if (wallPaperPurchaseBean.getStatus()==0) {
                    Toast.makeText(mActivity, "购买成功", Toast.LENGTH_SHORT).show();
                    mPurchaseCallback.Purchase(dataBean.getPrice());
                    textView.setText("下载");
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDownloadCallback.download(dataBean.getUrl());
                        }
                    });
                }else{
                    Toast.makeText(mActivity, "余额不足", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class BoardTicketWallHolder{
        private ImageView mImgItemBoardTicketWall;
        private TextView mTvItemBoardTicketWall;
        private TextView mTvItemBoardTicketWallPrice;


        public BoardTicketWallHolder(View view){
            mImgItemBoardTicketWall = (ImageView) view.findViewById(R.id.img_item_board_ticket_wall);
            mTvItemBoardTicketWall = (TextView) view.findViewById(R.id.tv_item_board_ticket_wall);
            mTvItemBoardTicketWallPrice = (TextView) view.findViewById(R.id.tv_item_board_ticket_wall_price);


        }
    }

    public interface DownloadCallback{
        public void download(String url);
    }

    //兑换之后更新局票页面的局票数
    public interface PurchaseCallback{
        public void Purchase(int price);
    }
}
