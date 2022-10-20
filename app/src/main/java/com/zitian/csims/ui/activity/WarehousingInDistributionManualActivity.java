package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.WeakHandler;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.WarehousingErrorManagerList;
import com.zitian.csims.model.WarehousingInDistributionFullTask;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.WarehousingErrorManagerListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class WarehousingInDistributionManualActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Button bt01;

    private TextView t_prodNo;
    private TextView t_prodName;
    private TextView cPlateName;
    private TextView t_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_in_distribution_manual);
        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("【入库】-手动上架");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        bt01 = (Button) findViewById(R.id.bt01);

        t_prodNo = (TextView) findViewById(R.id.t_prodNo);
        t_prodName = (TextView) findViewById(R.id.t_prodName);
        cPlateName = (TextView) findViewById(R.id.cPlateName);
        t_count = (TextView) findViewById(R.id.t_count);

        WarehousingInDistributionFullTask.Bean bean  = (WarehousingInDistributionFullTask.Bean)getIntent().getSerializableExtra("itemModel");
        t_prodNo.setText(bean.getT_prodNo());
        t_prodName.setText(bean.getT_prodName());
        //cPlateName.setText(bean.getT_fromNo());
        t_count.setText(String.valueOf(bean.getT_count()));

        Intent intent = getIntent();
        String t_toNo=intent.getStringExtra("t_toNo");
        String t_toArea=intent.getStringExtra("t_toArea");
        t_count.setText(t_toNo + "("+t_toArea+")");
    }

    private void setViewListener() {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    //手动
    private void DataBind4(boolean isLoading) {
        Intent intent=getIntent();
        String t_toNo=intent.getStringExtra("t_toNo");
        String t_toArea=intent.getStringExtra("t_toArea");

        WarehousingInDistributionFullTask.Bean bean  = (WarehousingInDistributionFullTask.Bean)getIntent().getSerializableExtra("itemModel");
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionManual(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("q_prodNo",bean.getT_prodNo());//产品编码
        SENDSMSRequest.add("q_prodName",bean.getT_prodName());//产品名称
        SENDSMSRequest.add("wh_wareHouseNo", bean.getT_toNo());//配货区库位号
        SENDSMSRequest.add("wh_ID",bean.getT_exception());//配货区主键ID
        SENDSMSRequest.add("wh_catetory", bean.getT_exception2());//系列号
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());//录入人员
        SENDSMSRequest.add("t_toNo",t_toNo);
        SENDSMSRequest.add("t_toArea",t_toArea);
        SENDSMSRequest.add("t_exception3",bean.getT_exception3());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                bt01.setEnabled(false);
                DataBind4(true);
                break;
        }
    }

    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.OutofSpaceForkliftList(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                WarehousingErrorManagerList outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingErrorManagerList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                    finish();
                    break;
                }
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

}