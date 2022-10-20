package com.zitian.csims.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.zitian.csims.model.BatchOffForkliftList;
import com.zitian.csims.model.WarehousingErrorForkliftDistributionList;
import com.zitian.csims.model.WarehousingInDistributionCheckInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.BatchOffForkliftListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class BasicSettingsCodeDiskRulesActivity  extends  BaseActivity implements  View.OnClickListener, HttpListener<String> {
    private ImageView icon_back;
    private AutoCompleteTextView mProdNo;
    private EditText mProdRule;
    private Button bt01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_settings_code_disk_rules);
        initView();
        setViewListener();
        initView();
    }
    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("码盘规则");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        mProdNo = (AutoCompleteTextView) findViewById(R.id.mProdNo);
        mProdRule = (EditText) findViewById(R.id.mProdRule);
        bt01 = (Button) findViewById(R.id.bt01);

        SetProdNo(mProdNo,1);
        mProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                mProdNo.setText(s);
                mProdNo.setSelection(s.length());//set cursor to the end
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
                if(mProdNo.getText().toString().trim().length()==0)
                {
                    Alert("提示","产品编码不能为空！","知道了");
                    return;
                }else if(mProdRule.getText().toString().trim().length()==0)
                {
                    Alert("提示","产品规则不能为空！","知道了");
                    return;
                }
                DataBind(true);
                break;
        }
    }
    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.BasicSettingsCodeDiskRules(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("o_id_lastmodify", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("prodNo", mProdNo.getText().toString());
        SENDSMSRequest.add("cPlateName", mProdRule.getText().toString());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000:
                WarehousingInDistributionCheckInput warehousingInDistributionCheckInput = gson.fromJson(response.get(), WarehousingInDistributionCheckInput.class);
                if (warehousingInDistributionCheckInput.getResult() == 1) {
                    Alert("提示", warehousingInDistributionCheckInput.getMsg(), "知道了");
                    finish();
                } else {
                    Alert("提示", warehousingInDistributionCheckInput.getMsg(), "知道了");
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,  response.getException().getMessage());
    }
}
