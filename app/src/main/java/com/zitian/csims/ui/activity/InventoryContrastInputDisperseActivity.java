package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
import com.zitian.csims.model.InventoryContrastList;
import com.zitian.csims.model.InventoryInputWhole;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class InventoryContrastInputDisperseActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Button bt01;
    private Button bt02;
    private Button bt03;
    private TextView mWareHouseNo;
    private TextView mProdNo;
    private TextView mProdName;
    private EditText mNum;

    InventoryContrastList.Bean itemModel = null;
    InventoryInputWhole inventoryInputWhole;
    int sType=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_contrast_input_disperse);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("录入盘点散书数据");
        tv_title.setGravity(Gravity.CENTER);

        // 获取意图
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemModel = (InventoryContrastList.Bean) bundle.get("itemModel");

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        bt01 = (Button) findViewById(R.id.bt01);
        bt02 = (Button) findViewById(R.id.bt02);
        bt03 = (Button) findViewById(R.id.bt03);

        mWareHouseNo = (TextView) findViewById(R.id.mWareHouseNo);
        mProdNo = (TextView) findViewById(R.id.mProdNo);
        mProdName = (TextView) findViewById(R.id.mProdName);
        mNum = (EditText) findViewById(R.id.mNum);

        if(itemModel.getWh_status()==3) // 0 是空 1占用 2有货 3预留
        {
            mWareHouseNo.setText(itemModel.getWh_wareHouseNo());
            mProdNo.setText("预留");
            mProdName.setText("预留");
            mNum.setEnabled(false);
        }else if(itemModel.getWh_isInvalid() == 1)//库位是否作废，默认false，true代表已经作废
        {
            mWareHouseNo.setText(itemModel.getWh_wareHouseNo());
            mProdNo.setText("作废");
            mProdName.setText("作废");
            mNum.setText("");
            mNum.setEnabled(false);
        }else
        {
            mWareHouseNo.setText(itemModel.getWh_wareHouseNo());
            mProdNo.setText(itemModel.getWh_prodNo());
            mProdName.setText(itemModel.getWh_prodName());
            mNum.setText("");
            mNum.setHint("0");
            mNum.setEnabled(true);
        }
        showSoftInputFromWindow(this,mNum);
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
        bt03.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                sType = 1;
                DataBind(true);
                break;
            case R.id.bt02:
                Intent intent = new Intent(InventoryContrastInputDisperseActivity.this,InventoryContrastInputDisperseErrorActivity.class);
                intent.putExtra("itemModel",itemModel);
                startActivityForResult(intent,0);
                break;
            case R.id.bt03:
//                if(mProdName.getText().toString().trim().equals(""))
//                {
//                    ToastUtil.show(this, "产品不能为空");
//                }
//                else  if(mNum.getText().toString().trim().equals(""))
//                {
//                    ToastUtil.show(this, "件不能为空");
//                }
//                else   if(mNum2.getText().toString().trim().equals(""))
//                {
//                    ToastUtil.show(this, "件不能为空");
//                }else
//                {
                sType=0;
                DataBind(true);
//                }
                break;
        }
    }

    private void DataBind(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryContrastInputDisperse(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        if(mWareHouseNo.getText().toString().equals("预留") || mWareHouseNo.getText().toString().equals("作废")){
            SENDSMSRequest.add("wareHouseNo", inventoryInputWhole.getData().getWh_wareHouseNo());
        }else {
            SENDSMSRequest.add("wareHouseNo", mWareHouseNo.getText().toString());
        }
        SENDSMSRequest.add("sType", sType);
        SENDSMSRequest.add("prodNo", mProdNo.getText().toString());
        SENDSMSRequest.add("prodName",mProdName.getText().toString());
        SENDSMSRequest.add("nbook_num",mNum.getText().toString().trim().equals("")?"0":mNum.getText().toString().trim());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("userid", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }
 
    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                inventoryInputWhole = gson.fromJson(response.get(), InventoryInputWhole.class);
                if (inventoryInputWhole.getResult() == 1) {
                    ToastUtil.show(this, inventoryInputWhole.getMsg());

                    String result = itemModel.getWh_wareHouseNo().trim();
                    Intent intent = new Intent();
                    intent.putExtra("result", result);
                    /*
                     * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                     */
                    setResult(1001, intent);
                    finish();
                }else {
                    ToastUtil.show(this, inventoryInputWhole.getMsg());
                }
                break;
            case 1001://查询商品
                WarehousingInDistributionQualityInput warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    if (warehousingInDistributionQualityInput.getData() != null) {
                        mProdName.setText(warehousingInDistributionQualityInput.getData().h_name);
                    } else if (warehousingInDistributionQualityInput.getResult() == 0) {

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