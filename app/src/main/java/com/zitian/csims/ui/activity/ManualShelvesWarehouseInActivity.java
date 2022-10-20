package com.zitian.csims.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.zitian.csims.model.InventoryInputWhole;
import com.zitian.csims.model.InventoryManualList;
import com.zitian.csims.model.ManualShelvesWarehouseIn;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.ReplenishManualDistributionAdd;
import com.zitian.csims.model.WarehousingInDistributionFullTask2;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class ManualShelvesWarehouseInActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private AutoCompleteTextView mFromNo;
    private AutoCompleteTextView mProdNo;
    private TextView mProdName;
    private EditText mNbox_num;
    private EditText mNpack_num;
    private EditText mNbook_num;
    private TextView mCount;
    private WarehousingInDistributionFullTask2 warehousingInDistributionFullTask2 = null;

    //private ManualShelvesWarehouseIn manualShelvesWarehouseIn = null;
    private WarehousingInDistributionQualityInput warehousingInDistributionQualityInput = null;

    private Button bt01;
    private EditText add_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_shelves_warehouse_in);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("入库调整单");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mFromNo = (AutoCompleteTextView) findViewById(R.id.mFromNo);
        mProdNo = (AutoCompleteTextView) findViewById(R.id.mProdNo);
        mProdName = (TextView) findViewById(R.id.mWareHouseNo);
        mNbox_num = (EditText) findViewById(R.id.mNbox_num);
        mNpack_num = (EditText) findViewById(R.id.mNpack_num);
        mNbook_num = (EditText) findViewById(R.id.mNbook_num);
        mCount= (TextView) findViewById(R.id.mCount);
        bt01= (Button) findViewById(R.id.bt01);
        add_content= (EditText) findViewById(R.id.add_content);

        SetAreaNo(mFromNo,null);
        SetProdNo(mProdNo,0);

        mFromNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToAreaNo(obj.toString());
                if(!s.contains("区") && !s.contains("品"))
                {
                    mFromNo.setText(s);
                    mFromNo.setSelection(s.length());//set cursor to the end
                    mProdNo.setText("");
                    mProdName.setText("");
                    mProdNo.setEnabled(false);
                    DataBind2(true);
                }else {
                    mProdNo.setEnabled(true);
                }
            }
        });

        mProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                mProdNo.setText(s);
                mProdNo.setSelection(s.length());//set cursor to the end
                DataBind3(true);
            }
        });

        mProdName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumC();
            }
        });
        mNbox_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumC();
            }
        });
        mNpack_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumC();
            }
        });
        mNbook_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumC();
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
                if(mFromNo.getText().toString().trim().equals(""))
                {
                    Alert("提示","请输入库位号/库区","知道了");
                }else
                    DataBind(true);
                break;
        }
    }

    public void SumC()
    {
        if(warehousingInDistributionQualityInput!=null)
        {
            if(warehousingInDistributionQualityInput.getData()!=null)
            {
                int cNbox = Integer.valueOf(mNbox_num.getText().toString().trim().equals("") ? "0" : mNbox_num.getText().toString().trim()) * warehousingInDistributionQualityInput.getData().nBoxNum;
                int cNpack = Integer.valueOf(mNpack_num.getText().toString().trim().equals("") ? "0" : mNpack_num.getText().toString().trim()) * warehousingInDistributionQualityInput.getData().nPackNum;
                int cNbook = Integer.valueOf(mNbook_num.getText().toString().trim().equals("") ? "0" : mNbook_num.getText().toString().trim()) ;
                mCount.setText(String.valueOf(cNbox +cNpack+cNbook));
            }
        }
    }

    private void DataBind(boolean isLoading)
    {
        if(mFromNo.getText().toString().contains("爆仓区") || mFromNo.getText().toString().contains("备货区"))
        {
            if(mCount.getText().toString().length()>0 && warehousingInDistributionQualityInput!=null)
            {
                int num =  Integer.valueOf(mCount.getText().toString()).intValue();
                int num2 = Integer.valueOf(warehousingInDistributionQualityInput.getData().nPlateNum.intValue());
                if(num%num2  != 0)
                {
                    ToastUtil.show(this, "数量必须是整盘册数");
                    return;
                }
            }
        }

        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ManualShelvesWarehouseInActivity(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("a_type", "入库调整单");
        if(mFromNo.getText().toString().contains("区") || mFromNo.getText().toString().contains("品"))
        {
             SENDSMSRequest.add("a_wareHouseNo", mFromNo.getText().toString());
             SENDSMSRequest.add("a_wareArea", mFromNo.getText().toString());
        }else
        {
            SENDSMSRequest.add("a_wareHouseNo", warehousingInDistributionFullTask2.getData().getWh_wareHouseNo());
            SENDSMSRequest.add("a_wareArea", warehousingInDistributionFullTask2.getData().getWh_wareArea());
        }
        SENDSMSRequest.add("a_prodNo",  mProdNo.getText().toString());
        SENDSMSRequest.add("a_prodName",  mProdName.getText().toString());
        SENDSMSRequest.add("a_total",  mCount.getText().toString());
        SENDSMSRequest.add("a_nbox_num",  mNbox_num.getText().toString().trim().equals("") ? "0" : mNbox_num.getText().toString().trim());
        SENDSMSRequest.add("a_npack_num",  mNpack_num.getText().toString().trim().equals("") ? "0" : mNpack_num.getText().toString().trim());
        SENDSMSRequest.add("a_nbook_num", mNbook_num.getText().toString().trim().equals("") ? "0" : mNbook_num.getText().toString().trim());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("remark", add_content.getText().toString());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ManualShelvesWarehouseInActivity3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //SENDSMSRequest.add("condition", "wh_wareHouseNo='" +mFromNo.getText().toString() + "'" );
        SENDSMSRequest.add("condition",mFromNo.getText().toString());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind3(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //SENDSMSRequest.add("condition", "wh_prodNo='" + mProdNo.getText().toString() + "'  and wh_wareHouseNo='" +mFromNo.getText().toString() + "'" );
        //SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("prodNo", mProdNo.getText().toString());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000:
                OutofSpaceForkliftList outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                    finish();
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
            case 1001:
                warehousingInDistributionFullTask2 = gson.fromJson(response.get(), WarehousingInDistributionFullTask2.class);
                if (warehousingInDistributionFullTask2.getResult() == 1) {
                    if (warehousingInDistributionFullTask2.getData().getWh_prodName().length() > 0) {
                        mProdName.setText(warehousingInDistributionFullTask2.getData().getWh_prodName());
                        mProdNo.setText(warehousingInDistributionFullTask2.getData().getWh_prodNo());
                        mProdNo.setEnabled(false);
                        //if(manualShelvesWarehouseIn.getData().get(0).getWh_wareArea().equals("产品配货区"))
                        //{
                        //    mProdNo.setEnabled(false);
                        //}else
                        //{
                        //    mProdNo.setEnabled(true);
                        //}
                        DataBind3(true);
                    } else {
                        mProdNo.setEnabled(true);
                        mProdName.setText("");
                        mProdNo.setText("");
                    }
                }else
                {
                    mProdNo.setEnabled(true);
                    mProdName.setText("");
                    mProdNo.setText("");
                    ToastUtil.show(this, warehousingInDistributionFullTask2.getMsg());
                }
                break;
            case 1002:
                warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    if (warehousingInDistributionQualityInput.getData() != null) {
                        mProdName.setText(warehousingInDistributionQualityInput.getData().h_name);
                    } else{
                    }
                }else
                {
                    ToastUtil.show(this, warehousingInDistributionQualityInput.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

}