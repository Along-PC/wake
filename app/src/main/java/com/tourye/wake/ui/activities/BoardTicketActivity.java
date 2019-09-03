package com.tourye.wake.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.PrivilegeConfigBean;
import com.tourye.wake.beans.UserAccountBean;
import com.tourye.wake.beans.WallPaperBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.ui.adapters.BoardTicketPrivilegeAdapter;
import com.tourye.wake.ui.adapters.BoardTicketWallAdapter;
import com.tourye.wake.utils.PermissionDialogUtil;
import com.tourye.wake.views.MeasureGridView;
import com.tourye.wake.views.dialogs.ShareDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * BoardTicketActivity
 * author:along
 * 2018/8/15 上午11:17
 * <p>
 * 描述:局票页面
 */

public class BoardTicketActivity extends BaseActivity implements View.OnClickListener {
    private SmartRefreshLayout mRefreshLayoutBoardTicket;
    private MeasureGridView mGridBoardTicket;
    private TextView mTvBoardTicketTitle;
    private TextView mTvBoardTicketCount;
    private TextView mTvBoardTicketDetail;
    private LinearLayout mLlBoardTicketShop;
    private LinearLayout mLlBoardTicketTask;
    private MeasureGridView mGridBoardTicketPrivilege;

    private int offset = 0;//开始条数
    private int count = 12;//请求多少条
    private BoardTicketWallAdapter mBoardTicketWallAdapter;
    private List<WallPaperBean.DataBean> mWallData = new ArrayList<>();
    private String mUrl;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mActivity, "图片成功下载至相册应用wake文件夹下", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mActivity, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private int mCurrentPoint;//当前局票数

    @Override
    public void initView() {
        mImgReturn.setBackgroundResource(R.drawable.icon_return);
        mTvTitle.setText("我的局票");
        mImgCertain.setBackgroundResource(R.drawable.icon_share);

        mRefreshLayoutBoardTicket = (SmartRefreshLayout) findViewById(R.id.refreshLayout_board_ticket);
        mTvBoardTicketTitle = (TextView) findViewById(R.id.tv_board_ticket_title);
        mTvBoardTicketCount = (TextView) findViewById(R.id.tv_board_ticket_count);
        mTvBoardTicketDetail = (TextView) findViewById(R.id.tv_board_ticket_detail);
        mLlBoardTicketShop = (LinearLayout) findViewById(R.id.ll_board_ticket_shop);
        mLlBoardTicketTask = (LinearLayout) findViewById(R.id.ll_board_ticket_task);
        mGridBoardTicketPrivilege = (MeasureGridView) findViewById(R.id.grid_board_ticket_privilege);
        mGridBoardTicket = (MeasureGridView) findViewById(R.id.grid_board_ticket);

        mImgReturn.setOnClickListener(this);
        mImgCertain.setOnClickListener(this);
        mTvBoardTicketDetail.setOnClickListener(this);
        mLlBoardTicketTask.setOnClickListener(this);
        mLlBoardTicketShop.setOnClickListener(this);
        mRefreshLayoutBoardTicket.setEnableAutoLoadMore(false);
        mRefreshLayoutBoardTicket.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                offset=0;
                getUserAccountData();
                getPrivilegeConfig();
                getWallPagerData(true);
            }
        });
        mRefreshLayoutBoardTicket.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                offset += 10;
                getWallPagerData(false);
            }
        });

    }

    @Override
    public void initData() {

        getWallPagerData(true);

        mBoardTicketWallAdapter = new BoardTicketWallAdapter(mActivity);
        mGridBoardTicket.setAdapter(mBoardTicketWallAdapter);
        mBoardTicketWallAdapter.setDownloadCallback(new BoardTicketWallAdapter.DownloadCallback() {
            @Override
            public void download(String url) {
                mUrl = url;
                getPermission(url);
            }
        });
        mBoardTicketWallAdapter.setPurchaseCallback(new BoardTicketWallAdapter.PurchaseCallback() {
            @Override
            public void Purchase(int price) {
                mCurrentPoint-=price;
                mTvBoardTicketCount.setText(mCurrentPoint+"");
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        getPrivilegeConfig();

        getUserAccountData();

    }

    /**
     * 获取权限
     */
    public void getPermission(final String url) {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            downloadImage(url);
                        }else{
                            PermissionDialogUtil.showPermissionDialog(mActivity,"缺少存储权限，请前往手机设置开启");
                        }
                    }
                });
    }

    /**
     * 下载图片
     *
     * @param url
     */
    public void downloadImage(final String url) {

        new Thread() {
            @Override
            public void run() {
                final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wake";
                final File file = new File(path);
                FileOutputStream fileOutputStream = null;
                InputStream inputStream = null;
                if (!file.exists()) {
                    file.mkdirs();
                }
                try {
                    URL downloadUrl = new URL(url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) downloadUrl.openConnection();
                    inputStream = httpURLConnection.getInputStream();
                    long time = System.currentTimeMillis();
                    String imgAddress=path+"/"+time+".jpeg";
                    File finalFile = new File(imgAddress);
                    fileOutputStream = new FileOutputStream(finalFile);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    //保存图片后发送广播通知更新数据库
                    Uri uri = Uri.fromFile(finalFile);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(2);
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }.start();


    }

    /**
     * 获取壁纸列表
     *
     * @param isRefresh 是否执行刷新操作
     */
    private void getWallPagerData(final boolean isRefresh) {
        if (isRefresh) {
            mRefreshLayoutBoardTicket.setEnableLoadMore(true);
        }
        Map<String, String> map = new HashMap<>();
        map.put("offset", offset + "");
        map.put("count", count + "");
        HttpUtils.getInstance().get(Constants.WALL_PAPER_LIST, map, new HttpCallback<WallPaperBean>() {

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                if (isRefresh) {
                    mRefreshLayoutBoardTicket.finishRefresh();
                }else{
                    mRefreshLayoutBoardTicket.finishLoadMore();
                }
            }

            @Override
            public void onSuccessExecute(WallPaperBean wallPaperBean) {
                List<WallPaperBean.DataBean> data = wallPaperBean.getData();
                int status = wallPaperBean.getStatus();
                if (isRefresh) {
                    if (mRefreshLayoutBoardTicket.isRefreshing()) {
                        mRefreshLayoutBoardTicket.finishRefresh();
                    }
                }else{
                    mRefreshLayoutBoardTicket.finishLoadMore();
                }
                if (data == null || data.size() == 0) {
                    return;
                }
                if (data.size()<10) {
                    mRefreshLayoutBoardTicket.setEnableLoadMore(false);
                }
                if (isRefresh) {
                    mWallData = data;
                } else {
                    mWallData.addAll(data);
                }
                mBoardTicketWallAdapter.setDataBeans(mWallData);


            }
        });
    }

    /**
     * 获取局票特权信息
     */
    private void getPrivilegeConfig() {
        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.PRIVILEGE_CONFIG, map, new HttpCallback<PrivilegeConfigBean>() {
            @Override
            public void onSuccessExecute(PrivilegeConfigBean privilegeConfigBean) {
                List<PrivilegeConfigBean.DataBean> data = privilegeConfigBean.getData();
                if (data == null || data.size() == 0) {
                    return;
                }
                BoardTicketPrivilegeAdapter boardTicketPrivilegeAdapter = new BoardTicketPrivilegeAdapter(mActivity, data);
                mGridBoardTicketPrivilege.setAdapter(boardTicketPrivilegeAdapter);
            }
        });
    }

    /**
     * 获取用户账户信息
     */
    private void getUserAccountData() {

        Map<String, String> map = new HashMap<>();
        HttpUtils.getInstance().get(Constants.USER_ACCOUNT_DATA, map, new HttpCallback<UserAccountBean>() {
            @Override
            public void onSuccessExecute(UserAccountBean userAccountBean) {
                UserAccountBean.DataBean data = userAccountBean.getData();
                if (data == null) {
                    return;
                }
                mCurrentPoint = data.getPoint();
                mTvBoardTicketCount.setText(mCurrentPoint+ "");
            }
        });

    }

    @Override
    public int getRootView() {
        return R.layout.activity_board_ticket;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_return:
                finish();
                break;
            case R.id.tv_board_ticket_detail:
                Intent intentDetail = new Intent(mActivity, BoardTicketDetailActivity.class);
                startActivity(intentDetail);
                break;
            case R.id.ll_board_ticket_task:
                Intent intent = new Intent(mActivity, BoardTicketTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.img_certain:
                ShareDialog shareDialog = new ShareDialog(mActivity);
                shareDialog.showDialogTemp("/wake/point");
                break;
            case R.id.ll_board_ticket_shop:
                startActivity(new Intent(mActivity,TicketShopActivity.class));
                break;
        }
    }
}
