package com.zitian.csims.ui.activity;

import android.content.Intent;
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
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.ManualShelvesEdit;
import com.zitian.csims.model.ManualShelvesList;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class ManualShelvesEditActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private TextView mFromNo;
    private AutoCompleteTextView mProdNo;
    private TextView mProdName;
    private EditText mNbox_num;
    private EditText mNpack_num;
    private EditText mNbook_num;
    private TextView mCount;
    private Button bt01;
    private EditText add_content;

    ManualShelvesList.Bean itemModel = null;
    ManualShelvesEdit manualShelvesEdit = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_shelves_edit);

        initView();
        setViewListener();

        DataBind2(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("出入库调整单销售助理修改");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mFromNo = (TextView) findViewById(R.id.mFromNo);
        mProdNo = (AutoCompleteTextView) findViewById(R.id.mProdNo);
        mProdName = (TextView) findViewById(R.id.mWareHouseNo);
        mNbox_num = (EditText) findViewById(R.id.mNbox_num);
        mNpack_num = (EditText) findViewById(R.id.mNpack_num);
        mNbook_num = (EditText) findViewById(R.id.mNbook_num);
        mCount= (TextView) findViewById(R.id.mCount);
        bt01= (Button) findViewById(R.id.bt01);
        add_content= (EditText) findViewById(R.id.add_content);

        //SetAreaNo(mFromNo);
        SetProdNo(mProdNo,1);

        // 获取意图
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemModel = (ManualShelvesList.Bean) bundle.get("itemModel");

        mFromNo.setText(itemModel.getA_wareArea() +"("+ itemModel.getA_wareHouseNo()+")");
        mProdNo.setText(itemModel.getA_prodNo());
        mProdName.setText(itemModel.getA_prodName());
        mNbox_num.setText(String.valueOf(itemModel.getA_nbox_num()));
        mNpack_num.setText(String.valueOf(itemModel.getA_npack_num()));
        mNbook_num.setText(String.valueOf(itemModel.getA_nbook_num()));
        mCount.setText(String.valueOf(itemModel.getA_total()));
        add_content.setText(itemModel.getA_exception());

        mProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                mProdNo.setText(s);
                DataBind2(true);
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

    public void SumC()
    {
        if(manualShelvesEdit!=null)
        {
            int cNbox = Integer.valueOf(mNbox_num.getText().toString().trim().equals("") ? "0" : mNbox_num.getText().toString().trim()) * manualShelvesEdit.getData().get(0).getnPiecesNum();
            int cNpack = Integer.valueOf(mNpack_num.getText().toString().trim().equals("") ? "0" : mNpack_num.getText().toString().trim()) * manualShelvesEdit.getData().get(0).getnPackageNum();
            int cNbook = Integer.valueOf(mNbook_num.getText().toString().trim().equals("") ? "0" : mNbook_num.getText().toString().trim()) ;
            mCount.setText(String.valueOf(cNbox +cNpack+cNbook));
        }
    }

    private void DataBind2(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ManualShelvesWarehouseInActivity2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("condition", "wh_prodNo='" + mProdNo.getText().toString() + "'  and wh_wareHouseNo='" +mFromNo.getText().toString() + "'" );
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    private void DataBind(boolean isLoading) {
        if(mFromNo.getText().toString().contains("爆仓区") || mFromNo.getText().toString().contains("备货区"))
        {
            if(mCount.getText().toString().length()>0 && manualShelvesEdit!=null)
            {
                int num =  Integer.valueOf(mCount.getText().toString()).intValue();
                int num2 = manualShelvesEdit.getData().get(0).getnPlateNum();
                if(num%num2  != 0)
                {
                    ToastUtil.show(this, "数量必须是整盘册数");
                    return;
                }
            }
        }
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ManualShelvesEdit(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("a_id", itemModel.getA_id());//var a_id = Convert.ToInt32(context.Request["a_id"]);
        SENDSMSRequest.add("a_prodNo", mProdNo.getText().toString());//var a_prodNo = context.Request["a_prodNo"];
        SENDSMSRequest.add("a_prodName", mProdName.getText().toString());//var a_prodName = context.Request["a_prodName"];
        SENDSMSRequest.add("a_total", mCount.getText().toString());//var a_total = Convert.ToInt32(context.Request["a_total"]);
        SENDSMSRequest.add("a_nbox_num", mNbox_num.getText().toString());//var a_nbox_num = Convert.ToInt32(context.Request["a_nbox_num"]);
        SENDSMSRequest.add("a_npack_num", mNpack_num.getText().toString());//var a_npack_num = Convert.ToInt32(context.Request["a_npack_num"]);
        SENDSMSRequest.add("a_nbook_num", mNbook_num.getText().toString());//var a_nbook_num = Convert.ToInt32(context.Request["a_nbook_num"]);
        SENDSMSRequest.add("remark", add_content.getText().toString());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
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
                {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
            case 1001:
                manualShelvesEdit = gson.fromJson(response.get(), ManualShelvesEdit.class);
                if (manualShelvesEdit.getResult() == 1) {
                    if (manualShelvesEdit.getData() != null) {
                        mProdName.setText(manualShelvesEdit.getData().get(0).getProdName());
                        mProdNo.setText(manualShelvesEdit.getData().get(0).getProdNo());
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

}