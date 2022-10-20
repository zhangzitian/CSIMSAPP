package com.zitian.csims.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.zitian.csims.model.InventoryEmpty;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.WarehousingInDistributionFullTask;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class WarehousingAdjustmentDistributionAssortmentActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Spinner sp; //完成
    private Button mbt01; //领取
    private Button mbt02; //完成

    private TextView mt_prodNo;
    private TextView mt_prodName;
    private TextView mcPlateName;
    private TextView mt_fromNo;
    private TextView mt_toNo;

    private LinearLayout line1; //整个任务
    private RelativeLayout all_no_data; //没有数据
    private TextView tipMsg; //提示的文字

    OutofSpaceForkliftList.Bean bean = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_adjustment_distribution_assortment);

        initView();
        setViewListener();

        DataBind(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("配货员---调品种任务列表");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        sp= (Spinner) findViewById(R.id.sp);

        line1= (LinearLayout) findViewById(R.id.line1);
        line1.setVisibility(View.GONE);

        all_no_data = (RelativeLayout) findViewById(R.id.all_no_data);
        tipMsg = (TextView) findViewById(R.id.tipMsg);

        mbt01 = (Button) findViewById(R.id.bt01);
        mbt02 = (Button) findViewById(R.id.bt02);

        mt_prodNo = (TextView) findViewById(R.id.t_prodNo);
        mt_prodName = (TextView) findViewById(R.id.t_prodName);
        mcPlateName = (TextView) findViewById(R.id.cPlateName);
        mt_fromNo= (TextView) findViewById(R.id.mt_fromNo);
        mt_toNo= (TextView) findViewById(R.id.mt_toNo);
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        mbt01.setOnClickListener(this); //领取
        mbt02.setOnClickListener(this); //完成
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                DataBind2(true);
                break;
            case R.id.bt02:
                DataBind3(true);
                break;
        }
    }

    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingAdjustmentDistributionAssortment(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingAdjustmentDistributionAssortment2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("column", sp.getSelectedItem().toString().contains("列号") ? "0" : sp.getSelectedItem().toString());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind3(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingAdjustmentDistributionAssortment3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("t_ID", bean.getT_ID());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                InventoryEmpty InventoryEmpty = gson.fromJson(response.get(), InventoryEmpty.class);
                if (InventoryEmpty.getResult() == 1) {
                    if (InventoryEmpty.getData() != null) {
                        List<InventoryEmpty.Bean> list = InventoryEmpty.getData();
                        InventoryEmpty.Bean b = new InventoryEmpty.Bean();
                        b.setI_rowName("列号");
                        list.add(0,b);
                        ArrayAdapter<InventoryEmpty.Bean> spinnerAdapter = new ArrayAdapter<InventoryEmpty.Bean>(getApplication(),R.layout.simple_spinner_item, list);
                        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        sp.setAdapter(spinnerAdapter);
                        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                                if(!list.get(pos).getI_rowName().equals("列号"))
                                {
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                // Another interface callback
                            }
                        });
                    }
                }
                break;
            case 1001://快速登录
                OutofSpaceForkliftList outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    if (outofSpaceForkliftList.getData() != null) {
                        line1.setVisibility(View.VISIBLE);
                        all_no_data.setVisibility(View.GONE);
                        bean = outofSpaceForkliftList.getData().get(0);
                        mt_prodNo.setText(bean.getT_prodNo());
                        mt_prodName.setText(bean.getT_prodName());
                        mcPlateName.setText(bean.getT_trayType());
                        mt_fromNo.setText(bean.getT_fromNo());
                        mt_toNo.setText(bean.getT_toNo());

                        mbt01.setEnabled(false);

                        mbt02.setEnabled(true);
                    }
                }else
                {
                    //Alert("提示",outofSpaceForkliftList.getMsg(),"知道了");
                    //ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                    line1.setVisibility(View.GONE);
                    all_no_data.setVisibility(View.VISIBLE);
                    tipMsg.setText(outofSpaceForkliftList.getMsg());
                }
                break;
            case 1002://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }else
                {
                    ToastUtil.show(this,  outofSpaceForkliftList.getMsg());
                }
                mbt01.setEnabled(true);
                mbt02.setEnabled(false);
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }


}