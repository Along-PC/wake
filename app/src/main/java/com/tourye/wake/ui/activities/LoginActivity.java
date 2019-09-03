package com.tourye.wake.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.tac.TACApplication;
import com.tourye.wake.BuildConfig;
import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.base.BaseActivity;
import com.tourye.wake.beans.CheckUpdateBean;
import com.tourye.wake.beans.Commonbean;
import com.tourye.wake.beans.LoginBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;
import com.tourye.wake.serves.DownloadService;
import com.tourye.wake.utils.SaveUtil;
import com.tourye.wake.views.dialogs.RegionDialog;
import com.tourye.wake.views.dialogs.UpdateAppDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEdtActivityLoginPhone;
    private EditText mEdtActivityLoginCode;
    private TextView mTvActivityLoginCode;
    private Switch mSwitchActivityLogin;
    private EditText mEdtActivityLoginInvite;
    private Button mBtActivityLogin;
    private TextView mTvActivityLoginVoice;
    private LinearLayout mLlActivityLoginRegion;
    private TextView mTvActivityLoginRegion;
    private TextView mTvActivityLoginRegionNumber;

    private String mPrefixNumber = "86";//地区手机号前缀

    private boolean mIsTrigger=false;//是否触发过更新
    private String mIsUpdate="0";//是否进行过更新操作--0 没有   1有

    private int mCountdown = 60;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mCountdown > 0) {
                        mCountdown--;
                        mHandler.sendEmptyMessageDelayed(1, 1000);
                        mTvActivityLoginCode.setText("剩余" + mCountdown + "s");
                        mTvActivityLoginCode.setOnClickListener(null);
                    } else {
                        mCountdown = 60;
                        mHandler.removeMessages(1);
                        mTvActivityLoginCode.setText("获取动态密码");
                        mTvActivityLoginCode.setOnClickListener(LoginActivity.this);
                    }

                    break;
                case 2:
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    intent.putExtra("isUpdate",mIsUpdate);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };


    @Override
    public void initView() {
        mEdtActivityLoginPhone = (EditText) findViewById(R.id.edt_activity_login_phone);
        mEdtActivityLoginCode = (EditText) findViewById(R.id.edt_activity_login_code);
        mTvActivityLoginCode = (TextView) findViewById(R.id.tv_activity_login_code);
        mSwitchActivityLogin = (Switch) findViewById(R.id.switch_activity_login);
        mEdtActivityLoginInvite = (EditText) findViewById(R.id.edt_activity_login_invite);
        mBtActivityLogin = (Button) findViewById(R.id.bt_activity_login);
        mTvActivityLoginVoice = (TextView) findViewById(R.id.tv_activity_login_voice);
        mLlActivityLoginRegion = (LinearLayout) findViewById(R.id.ll_activity_login_region);
        mTvActivityLoginRegion = (TextView) findViewById(R.id.tv_activity_login_region);
        mTvActivityLoginRegionNumber = (TextView) findViewById(R.id.tv_activity_login_regionNumber);

        mSwitchActivityLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mEdtActivityLoginInvite.setVisibility(View.VISIBLE);
                } else {
                    mEdtActivityLoginInvite.setVisibility(View.GONE);
                }
            }
        });

        mTvActivityLoginCode.setOnClickListener(this);
        mLlActivityLoginRegion.setOnClickListener(this);
        mEdtActivityLoginCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!TextUtils.isEmpty(s)) {
                    String phone = mEdtActivityLoginPhone.getText().toString();
                    if (!TextUtils.isEmpty(phone)) {
                        mBtActivityLogin.setSelected(true);
                        mBtActivityLogin.setOnClickListener(LoginActivity.this);
                    } else {
                        mBtActivityLogin.setSelected(false);
                        mBtActivityLogin.setOnClickListener(null);
                    }
                } else {
                    mBtActivityLogin.setSelected(false);
                    mBtActivityLogin.setOnClickListener(null);
                }
            }
        });
        mTvActivityLoginVoice.setOnClickListener(this);
    }

    @Override
    public void initData() {

        checkUpdate();

    }

    //检查是否需要版本更新
    private void checkUpdate() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "android");
        HttpUtils.getInstance().getNoAuth(Constants.CHECK_UPDATE, map, new HttpCallback<CheckUpdateBean>() {
            @Override
            public void onSuccessExecute(CheckUpdateBean checkUpdateBean) {
                CheckUpdateBean.DataBean data = checkUpdateBean.getData();
                if (data == null) {
                    return;
                }
                int minVersion = data.getBaseline();
                int currentVersion = data.getNewest();
                int versionCode = BuildConfig.VERSION_CODE;
                final String packageX = data.getPackageX();
                if (versionCode < currentVersion) {
                    UpdateAppDialog updateAppDialog = new UpdateAppDialog(mActivity);
                    if (versionCode < minVersion) {
                        updateAppDialog.forceShowDialog(new UpdateAppDialog.ForceUpdateCallback() {
                            @Override
                            public void update() {
                                //开启监测更新服务
                                Intent intent = new Intent(mActivity, DownloadService.class);
                                intent.putExtra("url",packageX);
                                startService(intent);
                            }
                        });
                    } else {
                        updateAppDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                dialogInterface.dismiss();
                            }
                        });
                        updateAppDialog.showDialog(new UpdateAppDialog.UpdateCallback() {
                            @Override
                            public void update() {
                                //开启监测更新服务
                                Intent intent = new Intent(mActivity, DownloadService.class);
                                intent.putExtra("url",packageX);
                                startService(intent);
                            }
                        });
                    }
                }

            }
        });

    }

    public void login() {
        Map<String, String> map = new HashMap<>();
        String phone = mEdtActivityLoginPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mActivity, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        String code = mEdtActivityLoginCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(mActivity, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        map.put("phone", mPrefixNumber + phone);
        map.put("code", code);
        String device_id = TACApplication.getDeviceId();//信鸽推送token
        map.put("device_id", device_id);
        map.put("type", "android");

        HttpUtils.getInstance().get(Constants.USER_LOGIN, map, new HttpCallback<LoginBean>() {
            @Override
            public void onFailureExecute() {
                super.onFailureExecute();
                mBtActivityLogin.setOnClickListener(LoginActivity.this);
            }

            @Override
            public void onPreExcute() {
                super.onPreExcute();
                mBtActivityLogin.setOnClickListener(LoginActivity.this);
            }

            @Override
            public void onSuccessExecute(LoginBean loginBean) {
                int status = loginBean.getStatus();
                if (status == 0) {
                    String token = loginBean.getData();
                    if (!TextUtils.isEmpty(token)) {
                        SaveUtil.putString("Authorization", token);

                        if (mSwitchActivityLogin.isChecked()) {
                            String code = mEdtActivityLoginInvite.getText().toString();
                            if (TextUtils.isEmpty(code)) {
                                Toast.makeText(mActivity, "请填写邀请码", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            getRelationCode(code);
                        } else {
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            intent.putExtra("isUpdate",mIsUpdate);
                            startActivity(intent);
                            finish();
                        }

                    }
                } else {
                    Toast.makeText(mActivity, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void getVerifyCode() {
        Map<String, String> map = new HashMap<>();
        String phone = mEdtActivityLoginPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mActivity, "请填写手机号", Toast.LENGTH_SHORT).show();
            return;
        }


        map.put("phone", mPrefixNumber + phone);


        HttpUtils.getInstance().get(Constants.GET_VERIFY_CODE, map, new HttpCallback<Commonbean>() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
            }

            @Override
            public void onSuccessExecute(Commonbean commonbean) {

                int status = commonbean.getStatus();
                if (status != 0) {
                    Toast.makeText(mActivity, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                } else {
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                    mTvActivityLoginCode.setText("剩余" + mCountdown + "s");
                }
            }
        });
    }

    /**
     * 验证邀请码是否合法
     *
     * @param code
     */
    public void getRelationCode(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        HttpUtils.getInstance().get(Constants.INVITE_CODE_RELATION, map, new HttpCallback<Commonbean>() {
            @Override
            public void onSuccessExecute(Commonbean commonbean) {
                int status = commonbean.getStatus();
                if (status == 0) {
                    mHandler.sendEmptyMessage(2);
                } else {
                    Toast.makeText(LoginActivity.this, "邀请码无效", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public int getRootView() {
        return R.layout.activity_login;
    }

    @Override
    public boolean isNeedTitle() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_activity_login:
                mBtActivityLogin.setOnClickListener(null);
                login();
                break;
            case R.id.tv_activity_login_code:
                getVerifyCode();
                break;
            case R.id.ll_activity_login_region:
                RegionDialog regionDialog = new RegionDialog(mActivity);
                regionDialog.show();
                regionDialog.setUpdateCallback(new RegionDialog.UpdateCallback() {
                    @Override
                    public void update(int region) {
                        ArrayList<String> strings = new ArrayList<>();
                        strings.add("中国大陆");
                        strings.add("香港");
                        strings.add("澳门");
                        strings.add("台湾");

                        ArrayList<String> numbers = new ArrayList<>();
                        numbers.add("86");
                        numbers.add("852");
                        numbers.add("853");
                        numbers.add("886");
                        mTvActivityLoginRegion.setText(strings.get(region));
                        mTvActivityLoginRegionNumber.setText("+" + numbers.get(region));
                        mPrefixNumber = numbers.get(region);
                    }
                });
                break;
            case R.id.tv_activity_login_voice:
                Intent intent = new Intent(mActivity, ProblemActivity.class);
                startActivity(intent);
                break;
        }
    }


}
