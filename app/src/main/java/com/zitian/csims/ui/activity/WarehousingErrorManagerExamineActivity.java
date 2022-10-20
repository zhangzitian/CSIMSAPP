package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.WarehousingErrorManagerList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class WarehousingErrorManagerExamineActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    RelativeLayout rl3;
    private TextView mFromNo;//库位号
    private TextView mErrType;//纠错类型
    private TextView mErrMember;//过失人

    private TextView cProdNo;//移出单 产品编码
    private TextView cFromNo;//移出单 库位号
    private TextView cProdName;//移出单 产品名称
    private TextView cNbox_num;//移出单 件
    private TextView cNpack_num;//移出单 包
    private TextView cNbook_num;//移出单 册
    private TextView cPcount;//移出单 总数量

    private TextView rProdNo;//移入单 产品编码
    private TextView rFromNo;//移入单 库位号
    private TextView rProdName;//移入单 产品名称
    private TextView rNbox_num;//移入单 件
    private TextView rNpack_num;//移入单 包
    private TextView rNbook_num;//移入单 册
    private TextView rPcount;//移入单 总数量

    private Button bt01;

    WarehousingErrorManagerList.Bean  itemModel = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_error_manager_examine);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("纠错审核");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        rl3 = (RelativeLayout) findViewById(R.id.rl3);

        mFromNo = (TextView) findViewById(R.id.mFromNo);
        mErrType = (TextView) findViewById(R.id.mErrType);
        mErrMember = (TextView) findViewById(R.id.mErrMember);

        cProdNo = (TextView) findViewById(R.id.cProdNo);
        cFromNo = (TextView) findViewById(R.id.cFromNo);
        cProdName = (TextView) findViewById(R.id.cProdName);
        cNbox_num = (TextView) findViewById(R.id.cNbox_num);
        cNpack_num = (TextView) findViewById(R.id.cNpack_num);
        cNbook_num = (TextView) findViewById(R.id.cNbook_num);
        cPcount = (TextView) findViewById(R.id.cPcount);

        rProdNo = (TextView) findViewById(R.id.rProdNo);
        rFromNo = (TextView) findViewById(R.id.rFromNo);
        rProdName = (TextView) findViewById(R.id.rProdName);
        rNbox_num = (TextView) findViewById(R.id.rNbox_num);
        rNpack_num = (TextView) findViewById(R.id.rNpack_num);
        rNbook_num = (TextView) findViewById(R.id.rNbook_num);
        rPcount = (TextView) findViewById(R.id.rPcount);
        bt01= (Button) findViewById(R.id.bt01);

        // 获取意图
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // 获取对象
        itemModel = (WarehousingErrorManagerList.Bean ) bundle.get("itemModel");

        mFromNo.setText(itemModel.getE_wareHouseNo());
        mErrType.setText(itemModel.getE_errType());
        mErrMember.setText(itemModel.getErrMemberName());

        cProdNo.setText(itemModel.getE_output_prodNo());
        cFromNo.setText(itemModel.getE_output_wareHouseNo());
        cProdName.setText(itemModel.getE_output_prodName());
        cNbox_num.setText(String.valueOf(itemModel.getE_output_nbox_num()));
        cNpack_num.setText(String.valueOf(itemModel.getE_output_npack_num()));
        cNbook_num.setText(String.valueOf(itemModel.getE_output_nbook_num()));
        cPcount.setText(String.valueOf(itemModel.getE_output_total()));

        rProdNo.setText(itemModel.getE_input_prodNo());
         rFromNo.setText(itemModel.getE_input_wareHouseNo());
        rProdName.setText(itemModel.getE_input_prodName());
        rNbox_num.setText(String.valueOf(itemModel.getE_input_nbox_num()));
        rNpack_num.setText(String.valueOf(itemModel.getE_input_npack_num()));
        rNbook_num.setText(String.valueOf(itemModel.getE_input_nbook_num()));
        rPcount.setText(String.valueOf(itemModel.getE_input_total()));

        if(itemModel.getE_errType().equals("破损"))
        {
            rl3.setVisibility(View.VISIBLE);
        }
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

    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingErrorManagerExamine(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID",itemModel.getE_exception());//任务表主键ID
        SENDSMSRequest.add("e_exception3",itemModel.getE_exception3());//爆仓区主键ID
        SENDSMSRequest.add("ID",itemModel.getE_id());//纠错单ID
        SENDSMSRequest.add("errType",itemModel.getE_errType());//纠错类型
        SENDSMSRequest.add("e_errMember",itemModel.getE_errMember());//过失人
        SENDSMSRequest.add("e_wareHouseNo",itemModel.getE_wareHouseNo());
        SENDSMSRequest.add("e_wareArea",itemModel.getE_wareArea());
        SENDSMSRequest.add("e_output_wareHouseNo",itemModel.getE_output_wareHouseNo());//移出单库位号
        SENDSMSRequest.add("e_output_prodNo",itemModel.getE_output_prodNo());//移出单产品编码
        SENDSMSRequest.add("e_output_prodName",itemModel.getE_output_prodName());//移出单产品名称
        SENDSMSRequest.add("e_output_nbox_num",itemModel.getE_output_nbox_num());;
        SENDSMSRequest.add("e_output_npack_num",itemModel.getE_output_npack_num());
        SENDSMSRequest.add("e_output_nbook_num",itemModel.getE_output_nbook_num());
        SENDSMSRequest.add("e_output_total",itemModel.getE_output_total());
        SENDSMSRequest.add("e_input_prodNo",itemModel.getE_input_prodNo());
        SENDSMSRequest.add("e_input_prodName",itemModel.getE_input_prodName());
        SENDSMSRequest.add("e_input_wareHouseNo",itemModel.getE_input_wareHouseNo());
        SENDSMSRequest.add("e_input_nbox_num",itemModel.getE_input_nbox_num());
        SENDSMSRequest.add("e_input_npack_num",itemModel.getE_input_npack_num());
        SENDSMSRequest.add("e_input_nbook_num",itemModel.getE_input_nbook_num());
        SENDSMSRequest.add("e_input_total",itemModel.getE_input_total());
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
                    if (outofSpaceForkliftList.getResult() == 1) {
                        ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                        finish();
                    } else {
                        ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                    }
                    break;
                }
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }


}