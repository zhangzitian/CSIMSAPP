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
import android.widget.RadioButton;
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
import com.zitian.csims.model.InventoryContrastList;
import com.zitian.csims.model.InventoryInputWhole;
import com.zitian.csims.model.ProdNoModels;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.SearchPupItenAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class InventoryContrastEmptyActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Button bt01;
    private Button bt02;
    private TextView mWareHouseNo;
    private AutoCompleteTextView mProdNo;
    private TextView mProdName;
    private RadioButton radiobutton1;
    private RadioButton radiobutton2;
    InventoryContrastList.Bean itemModel = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_contrast_empty);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("空位盘点");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        bt01 = (Button) findViewById(R.id.bt01);
        bt02 = (Button) findViewById(R.id.bt02);

        mWareHouseNo = (TextView) findViewById(R.id.mWareHouseNo);
        mProdNo = (AutoCompleteTextView) findViewById(R.id.mProdNo);
        mProdName = (TextView) findViewById(R.id.mProdName);
        radiobutton1 = (RadioButton) findViewById(R.id.radiobutton1);
        radiobutton2 = (RadioButton) findViewById(R.id.radiobutton2);

        // 获取意图
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemModel = (InventoryContrastList.Bean) bundle.get("itemModel");

        mWareHouseNo.setText(itemModel.getWh_wareHouseNo());
        mProdNo.setText(itemModel.getWh_prodNo());
        mProdName.setText(itemModel.getWh_prodName());

        if(itemModel.getWh_prodNo().trim().equals(""))
        {
            radiobutton1.setChecked(true);
        }
        else
        {
            radiobutton2.setChecked(true);
        }
        SetProdNo(mProdNo,1);
        mProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                mProdNo.setText(s);
                mProdNo.setSelection(s.length());//set cursor to the end
                DataBind2(true);
            }
        });
        //if(itemModel.getU1_contrastResult() == 1 && itemModel.getU2_contrastResult() == 1)
        //{
        //    tv_title.setText("空位盘点-待确认产品");
        //    radiobutton2.setChecked(true);
        //}
        mProdNo.addTextChangedListener(new TextWatcher() {
                                           @Override
                                           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                           }
                                           @Override
                                           public void onTextChanged(CharSequence s, int start, int before, int count) {
                                           }
                                           //Request<String> SENDSMSRequest = null;
                                           @Override
                                           public void afterTextChanged(Editable s) {
                                               String str = mProdNo.getText().toString().trim();
                                               if (str.length() < 1) {
                                                   mProdName.setText("");
                                               }
                                           }
                                       }
         );

    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);

        radiobutton1.setOnClickListener(this);
        radiobutton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.radiobutton1:
                mProdNo.setText("");
                mProdName.setText("");
                break;
            case R.id.radiobutton2:
                break;
            case R.id.bt01:
                if(radiobutton2.isChecked() && (mProdNo.getText().toString().trim().equals("") || mProdName.getText().toString().trim().equals(""))  )
                {
                    ToastUtil.show(this, "满盘产品不能为空！");
                }
                else  if(radiobutton1.isChecked() && (!mProdNo.getText().toString().trim().equals("") || !mProdName.getText().toString().trim().equals("")) )
                {
                    ToastUtil.show(this, "空盘产品必须为空！");
                }else
                {
                    DataBind(true);
                }
                break;
            case R.id.bt02:
                //if(radiobutton1.isChecked())
                //{
                //    ToastUtil.show(this, "空盘不能纠错！");
                //}
                //else if( radiobutton2.isChecked() && (mProdNo.getText().toString().trim().equals("") || mProdName.getText().toString().trim().equals("")))
                //{
                //    ToastUtil.show(this, "满盘产品不能为空！");
                //}
                //else
                //{
                    Intent intent = new Intent(InventoryContrastEmptyActivity.this,InventoryContrastEmptyErrorActivity.class);
                    intent.putExtra("itemModel",itemModel);
                    startActivityForResult(intent,0);
                //}
                break;
        }
    }

    private void DataBind(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryContrastEmpty(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("wareHouseNo", mWareHouseNo.getText().toString());
        SENDSMSRequest.add("prodNo", mProdNo.getText().toString());
        SENDSMSRequest.add("prodName",mProdName.getText().toString());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("userid", CSIMSApplication.getAppContext().getUser().getO_id());
        int r_status = 0;
        if(radiobutton1.isChecked())
            r_status = 0;
        else  if(radiobutton2.isChecked())
            r_status = 2;

        SENDSMSRequest.add("status", r_status);
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading) {
        String s = ConvertToProdNo(mProdNo.getText().toString());
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("prodNo", s);
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                WarehousingInDistributionQualityInput warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    ToastUtil.show(this, warehousingInDistributionQualityInput.getMsg());
                    String result = itemModel.getWh_wareHouseNo().trim();
                    Intent intent = new Intent();
                    intent.putExtra("result", result);
                    /*
                     * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                     */
                    setResult(1001, intent);
                    finish();
                }else
                    ToastUtil.show(this, warehousingInDistributionQualityInput.getMsg());
                break;
            case 1001://查询商品
                warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    if (warehousingInDistributionQualityInput.getData() != null) {
                        mProdName.setText(warehousingInDistributionQualityInput.getData().h_name);
                    }
                }else
                    ToastUtil.show(this, warehousingInDistributionQualityInput.getMsg());
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
        if(requestCode==0 && data!=null){
            String result = itemModel.getWh_wareHouseNo().trim();
            Intent intent = new Intent();
            intent.putExtra("result", result);
            /*
             * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
             */
            setResult(1001, intent);
            finish();
        }
    }

}