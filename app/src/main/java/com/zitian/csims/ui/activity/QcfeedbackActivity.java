package com.zitian.csims.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.FactoryModels;
import com.zitian.csims.model.QCfeedbackAdd;
import com.zitian.csims.model.WarehousingInDistributionCheckInput;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.SearchPupItenAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.DeviceIdUtil;
import com.zitian.csims.util.HelperUtil;
import com.zitian.csims.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QcfeedbackActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    WarehousingInDistributionQualityInput warehousingInDistributionQualityInput1;
    private ImageView icon_back;
    private Button subButton;
    private  TextView tv_Calendar;
    private  Spinner factory_id;
    private  AutoCompleteTextView product_no;
    private  TextView product_name;
    private  EditText product_count;
    private  TextView qc_package;
    private  EditText aq_package;
    private  EditText problem_count;
    private  EditText qc_desc;
    private QCfeedbackAdd.Bean bean =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_qc);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("质检反馈单");
        tv_title.setGravity(Gravity.CENTER);
        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        //加载系统时间
        tv_Calendar = (TextView) findViewById(R.id.mCalendar);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        tv_Calendar.setText(date);
        //加载应抽检数量
        qc_package = (TextView) findViewById(R.id.mQnumber);
        qc_package.setText("2");
        subButton = (Button) findViewById(R.id.bt01);
        factory_id=(Spinner)findViewById(R.id.mManufactor);//印厂
        product_no = (AutoCompleteTextView)findViewById(R.id.mProdNo);
        product_name = (TextView) findViewById(R.id.mProdName);
        product_count = (EditText) findViewById(R.id.mSum_num);
        aq_package = (EditText) findViewById(R.id.mAnumber);
        problem_count = (EditText) findViewById(R.id.mQcnumber);
        qc_desc = (EditText) findViewById(R.id.mdesc);
        SetFactory(factory_id);
        SetProdNo(product_no,1);
        product_no.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                product_no.setText(s);
                product_no.setSelection(s.length());//set cursor to the end
                DataBind(true);
            }
        });
    }
    private void setViewListener() {
        icon_back.setOnClickListener(this);
        subButton.setOnClickListener(this);
    }

    private void DataBind(boolean isLoading) {
        if(!product_no.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(product_no.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo", s);
            CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:

                if(product_name.getText().toString().equals(""))
                {
                    Alert("提示","请选择一个商品","知道了");
                }else if(product_count.getText().toString().equals(""))
                {
                    Alert("提示","请填写数量","知道了");
                }
                else if(!HelperUtil.isInteger(product_count.getText().toString()))
                {
                    Alert("提示","商品数量必须是整数","知道了");
                }
                else if(Integer.valueOf(product_count.getText().toString()) < 1)
                {
                    Alert("提示","商品数量必须大于0","知道了");
                }
                else if(factory_id.getSelectedItem().toString().contains("选择"))
                {
                    Alert("提示","请选择印厂","知道了");
                }
                else if(!HelperUtil.isInteger(aq_package.getText().toString()))
                {
                    Alert("提示","实际抽检数量必须是整数","知道了");
                }
                else if(Integer.valueOf(aq_package.getText().toString()) < 1)
                {
                    Alert("提示","实际抽检数量必须大于0","知道了");
                }else{
                    subButton.setEnabled(false);
                    DataBind2(true);
                }
                break;
        }
    }

    private void DataBind2(boolean isLoading) {
        String s = ConvertToProdNo(product_no.getText().toString());
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.QcfeedbackAdd(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("product_no",  s);
        SENDSMSRequest.add("product_name",product_name.getText().toString());
        SENDSMSRequest.add("product_count", product_count.getText().toString());
        SENDSMSRequest.add("qc_operator", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("factory_id", factory_id.getSelectedItem().toString());
        SENDSMSRequest.add("qc_package", qc_package.getText().toString());
        SENDSMSRequest.add("aq_package", aq_package.getText().toString());
        SENDSMSRequest.add("problem_count", problem_count.getText().toString());
        SENDSMSRequest.add("qc_desc", qc_desc.getText().toString());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://查询商品
                warehousingInDistributionQualityInput1 = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput1.getResult() == 1) {
                    if (warehousingInDistributionQualityInput1.getData() != null) {
                        product_name.setText(warehousingInDistributionQualityInput1.getData().h_name);
                    } else if (warehousingInDistributionQualityInput1.getResult() == 0) {

                    }
                }
                break;
            case 1001://添加
                subButton.setEnabled(false);
                QCfeedbackAdd qCfeedbackAdd = gson.fromJson(response.get(), QCfeedbackAdd.class);
                if (qCfeedbackAdd.getResult() == 1) {
                    ToastUtil.show(this, qCfeedbackAdd.getMsg());
                    subButton.setEnabled(true);
                }else
                {
                    ToastUtil.show(this, qCfeedbackAdd.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }
}
