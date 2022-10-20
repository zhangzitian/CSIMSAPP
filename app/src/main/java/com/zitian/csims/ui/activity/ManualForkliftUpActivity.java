package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
import com.zitian.csims.model.ManualShelvesWarehouseIn;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class ManualForkliftUpActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private AutoCompleteTextView mFromNo;
    private TextView mProdNo;
    private TextView mProdName;
    private Button bt01;
    private ManualShelvesWarehouseIn manualShelvesWarehouseIn = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_forklift_up);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("手动上架");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mFromNo = (AutoCompleteTextView)findViewById(R.id.mFromNo);
        mProdNo = (TextView)findViewById(R.id.mProdNo);
        mProdName = (TextView)findViewById(R.id.mWareHouseNo);
        bt01 = (Button)findViewById(R.id.bt01);
        SetAreaNo(mFromNo,null);

        mFromNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToAreaNo(obj.toString());
                if(s.contains("区") )
                {
                    mFromNo.setText("");
                    Alert("提示","只能选择备货区","我知道了");
                }
                else if(s.contains("品"))
                {
                    mFromNo.setText("");
                    Alert("提示","只能选择备货区","我知道了");
                }
                else
                {
                    mFromNo.setText(s);
                    mFromNo.setSelection(s.length());//set cursor to the end
                    DataBind2(true);
                }
            }
        });

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
                if(manualShelvesWarehouseIn==null)
                {

                }else if (manualShelvesWarehouseIn.getData()==null)
                {

                }else if (manualShelvesWarehouseIn.getData().size()>0)
                {
                    bt01.setEnabled(false);
                    DataBind(true);
                }
                break;
        }
    }

    private void DataBind(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ManualForkliftUpActivity(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_fromNo",manualShelvesWarehouseIn.getData().get(0).getWh_wareHouse());
        SENDSMSRequest.add("t_fromArea", manualShelvesWarehouseIn.getData().get(0).getWh_wareArea());
        SENDSMSRequest.add("t_prodNo", mProdNo.getText().toString());
        SENDSMSRequest.add("t_prodName", mProdName.getText().toString());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ManualShelvesWarehouseInActivity2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("condition", "wh_wareHouseNo='" +mFromNo.getText().toString() + "'" );
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                OutofSpaceForkliftList outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                    //finish();
                    Intent intent = new Intent(ManualForkliftUpActivity.this,ManualForkliftListActivity.class);
                    startActivityForResult(intent,0);
                } else {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
            case 1001:
                manualShelvesWarehouseIn = gson.fromJson(response.get(), ManualShelvesWarehouseIn.class);
                if (manualShelvesWarehouseIn.getResult() == 1) {
                    if (manualShelvesWarehouseIn.getData() != null) {
                        mProdName.setText(manualShelvesWarehouseIn.getData().get(0).getProdName());
                        mProdNo.setText(manualShelvesWarehouseIn.getData().get(0).getProdNo());
                    } else {
                    }
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

    //结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 ){
            finish();
        }
    }

}