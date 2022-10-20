package com.zitian.csims.ui.activity;

import android.content.DialogInterface;
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
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.TaskCount;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle;
import com.zitian.csims.util.ToastUtil;

public class InventoryTaskCreateActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Button bt01;
    private Button bt02;
    private Button bt03;
    private Button bt04;
    private Button bt05;

    private TextView tv01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_task_create);

        initView();
        setViewListener();
        IsStatusInventory(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("生成库位纠错调整任务");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        tv01 = (TextView) findViewById(R.id.tv01);
        bt01 = (Button) findViewById(R.id.bt01);
        bt02 = (Button) findViewById(R.id.bt02);
        bt03 = (Button) findViewById(R.id.bt03);
        bt04 = (Button) findViewById(R.id.bt04);
        bt05 = (Button) findViewById(R.id.bt05);
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
        bt03.setOnClickListener(this);
        bt04.setOnClickListener(this);
        bt05.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                Intent intent = new Intent(InventoryTaskCreateActivity.this,InventoryTaskCreateManualWholeActivity.class);
                //intent.putExtra("sType",1);
                startActivityForResult(intent,0);
                break;
            case R.id.bt02:
                intent = new Intent(InventoryTaskCreateActivity.this,InventoryTaskCreateManualScatteredActivity.class);
                //intent.putExtra("sType",1);
                startActivityForResult(intent,0);
                break;
            case R.id.bt03:
                intent = new Intent(InventoryTaskCreateActivity.this,InventoryTaskCreateEmptyActivity.class);
                //intent.putExtra("sType",1);
                startActivityForResult(intent,0);
                break;
            case R.id.bt04:
                intent = new Intent(InventoryTaskCreateActivity.this,InventoryTaskCreateNuclearDiskActivity.class);
                //intent.putExtra("sType",1);
                startActivityForResult(intent,0);
                break;
            case R.id.bt05:
                intent = new Intent(InventoryTaskCreateActivity.this,InventoryTaskCreateManualWholeAndScatteredActivity.class);
                //intent.putExtra("sType",1);
                startActivityForResult(intent,0);
                break;
        }
    }

    private void DataBind1(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.Inventory1(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
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
                }else
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                break;
            case 1001://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    tv01.setText(outofSpaceForkliftList.getMsg());
                }else
                    tv01.setText(outofSpaceForkliftList.getMsg());
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }




}