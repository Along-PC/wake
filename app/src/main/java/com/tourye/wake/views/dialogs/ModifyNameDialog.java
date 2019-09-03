package com.tourye.wake.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tourye.wake.Constants;
import com.tourye.wake.R;
import com.tourye.wake.beans.UpdateUserBean;
import com.tourye.wake.net.HttpCallback;
import com.tourye.wake.net.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longlongren on 2018/8/21.
 * <p>
 * introduce:修改姓名弹框
 */

public class ModifyNameDialog extends Dialog implements View.OnClickListener {

    private final Context mContext;
    private ImageView mImgDialogModifyClose;
    private TextView mTvDialogModifyTitle;
    private EditText mEdtDialogModifyName;
    private TextView mTvDialogModifySave;
    private RenameCallback mRenameCallback;

    public void setRenameCallback(RenameCallback renameCallback) {
        mRenameCallback = renameCallback;
    }

    public ModifyNameDialog(@NonNull Context context) {
        super(context);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_modify_name);

        mContext = context;

        mImgDialogModifyClose = (ImageView) findViewById(R.id.img_dialog_modify_close);
        mTvDialogModifyTitle = (TextView) findViewById(R.id.tv_dialog_modify_title);
        mEdtDialogModifyName = (EditText) findViewById(R.id.edt_dialog_modify_name);
        mTvDialogModifySave = (TextView) findViewById(R.id.tv_dialog_modify_save);
        mImgDialogModifyClose.setOnClickListener(this);
        mTvDialogModifySave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_dialog_modify_close:
                dismiss();
                break;
            case R.id.tv_dialog_modify_save:
                String name = mEdtDialogModifyName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(mContext, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,String> map=new HashMap<>();
                map.put("nickname",name);
                map.put("avatar","");
                HttpUtils.getInstance().post(Constants.UPDATE_USER_INFO, map, new HttpCallback<UpdateUserBean>() {
                    @Override
                    public void onSuccessExecute(UpdateUserBean updateUserBean) {
                        int status = updateUserBean.getStatus();
                        if (status==0) {
                            mRenameCallback.rename(mEdtDialogModifyName.getText().toString());
                            Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }else{
                            Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    public interface RenameCallback{
        public void rename(String name);
    }
}
