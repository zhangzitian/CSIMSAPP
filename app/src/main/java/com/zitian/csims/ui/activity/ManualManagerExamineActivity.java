package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.zitian.csims.model.ManualManagerList;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class ManualManagerExamineActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private TextView mFromNo;
    private TextView mProdNo;
    private TextView mProdName;
    private TextView mNbox_num;
    private TextView mNpack_num;
    private TextView mNbook_num;
    private TextView mCount;
    private ManualManagerList.Bean itemModel = null;
    private Button bt01;
    private TextView add_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_manager_examine);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("出入库调整单经理审核");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mFromNo = (TextView) findViewById(R.id.mFromNo);
        mProdNo = (TextView) findViewById(R.id.mProdNo);
        mProdName = (TextView) findViewById(R.id.mWareHouseNo);
        mNbox_num = (TextView) findViewById(R.id.mNbox_num);
        mNpack_num = (TextView) findViewById(R.id.mNpack_num);
        mNbook_num = (TextView) findViewById(R.id.mNbook_num);
        mCount= (TextView) findViewById(R.id.mCount);
        bt01= (Button) findViewById(R.id.bt01);
        add_content= (TextView) findViewById(R.id.add_content);

        // 获取意图
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemModel = (ManualManagerList.Bean) bundle.get("itemModel");

        mFromNo.setText(itemModel.getA_wareArea() +"("+ itemModel.getA_wareHouseNo()+")");
        mProdNo.setText(itemModel.getA_prodNo());
        mProdName.setText(itemModel.getA_prodName());
        mNbox_num.setText(String.valueOf(itemModel.getA_nbox_num()));
        mNpack_num.setText(String.valueOf(itemModel.getA_npack_num()));
        mNbook_num.setText(String.valueOf(itemModel.getA_nbook_num()));
        mCount.setText(String.valueOf(itemModel.getA_total()));
        add_content.setText(String.valueOf(itemModel.getA_exception()));
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
                DataBind(true);
                break;
        }
    }


    private void DataBind(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ManualManagerExamineActivity(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("ID", itemModel.getA_id());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                OutofSpaceForkliftList outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                    finish();
                }else
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                break;
        }
    }


    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

}