package com.zitian.csims.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.AreaNoModels;
import com.zitian.csims.model.WarehousingInDistributionCheckInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class UpdatePassWordActivity  extends  BaseActivity implements  View.OnClickListener, HttpListener<String> {
    private ImageView icon_back;
    private TextView mId;
    private  TextView mName;
    private EditText mPassword;
    private EditText mPassword2;
    private Button bt01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        initView();
        setViewListener();
        initView();
    }
    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("修改密码");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        mId = (TextView) findViewById(R.id.mId);
        mName = (TextView) findViewById(R.id.mName);
        mPassword = (EditText) findViewById(R.id.mPassword);
        mPassword2 = (EditText) findViewById(R.id.mPassword2);
        bt01 = (Button) findViewById(R.id.bt01);

        mName.setText(CSIMSApplication.getAppContext().getUser().getO_name());
        mId.setText(CSIMSApplication.getAppContext().getUser().getO_id());
    }
    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                if(mPassword.getText().toString().equals(""))
                {
                    Alert("提示","密码不能等于空！","知道了");
                    return;
                }else if(mPassword.getText().toString().compareTo(mPassword2.getText().toString())!=0)
                {
                    Alert("提示","二次输入的密码必须相等！","知道了");
                    return;
                }
                DataBind(true);
                break;
        }
    }
    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.UpdatePassWord(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("OID", CSIMSApplication.getAppContext().getUser().getOID());
        SENDSMSRequest.add("passwd", mPassword.getText().toString());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000:
                WarehousingInDistributionCheckInput warehousingInDistributionCheckInput = gson.fromJson(response.get(), WarehousingInDistributionCheckInput.class);
                if (warehousingInDistributionCheckInput.getResult() == 1) {
                    ToastUtil.showLong(this, warehousingInDistributionCheckInput.getMsg());
                    finish();
                    //Alert("提示", warehousingInDistributionCheckInput.getMsg(), "知道了");
                } else {
                    Alert("提示", warehousingInDistributionCheckInput.getMsg(), "知道了");
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {

    }
}
