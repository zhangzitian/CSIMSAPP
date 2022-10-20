package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.zitian.csims.model.InventoryInputDisperse;
import com.zitian.csims.model.InventoryInputWhole;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class InventoryInputDisperseActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Button bt01;
    private Button bt02;
    private Button bt03;
    private TextView mWareHouseNo;
    private TextView mProdNo;
    private TextView mProdName;
    private EditText mNum;

    private String wareHouseNo;
    private int sType ;  //0正常进入 1是传个固定库位进入 2暂停
    private String wareHouseNo2;
    private String sortNo;

    InventoryInputDisperse inventoryInputDisperse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_input_disperse);

        initView();
        setViewListener();
        DataBind(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("录入盘点散书区数据");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        bt01 = (Button) findViewById(R.id.bt01);
        bt02 = (Button) findViewById(R.id.bt02);
        bt03 = (Button) findViewById(R.id.bt03);

        mWareHouseNo = (TextView) findViewById(R.id.mWareHouseNo);
        mProdNo = (TextView) findViewById(R.id.mProdNo);
        mProdName = (TextView) findViewById(R.id.mProdName);
        mNum = (EditText) findViewById(R.id.mNum);

        sType =  getIntent().getIntExtra("sType",0);
        wareHouseNo2 = getIntent().getStringExtra("wareHouseNo2");
        sortNo = getIntent().getStringExtra("sortNo");
        wareHouseNo = getIntent().getStringExtra("wareHouseNo");
        if(sType==1)
        {
            mWareHouseNo.setText(wareHouseNo);
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
                Intent intent=new Intent();
                intent.putExtra("result","result");
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.bt01:
                sType = 2;
                DataBind(true);
                break;
            case R.id.bt02:
                intent = new Intent(InventoryInputDisperseActivity.this,InventoryInputDisperseErrorActivity.class);
                intent.putExtra("itemModel",inventoryInputDisperse.getData());
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
//            {
                sType = 0;
                DataBind(true);
//            }
            break;
        }
    }

    private void DataBind(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryInputDisperse(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        //if(mWareHouseNo.getText().toString().equals("预留") || mWareHouseNo.getText().toString().equals("作废")){
        //    SENDSMSRequest.add("wareHouseNo", inventoryInputDisperse.getData().getWh_wareHouseNo());
        //}else if (sType== 1){
        //    SENDSMSRequest.add("wareHouseNo", wareHouseNo);
        //}
        //else if (sType== 2){
        //    SENDSMSRequest.add("wareHouseNo", mWareHouseNo.getText().toString());
        //}else {
        //    SENDSMSRequest.add("wareHouseNo", mWareHouseNo.getText().toString());
        //}

        SENDSMSRequest.add("sType", sType);
        SENDSMSRequest.add("wareHouseNo", mWareHouseNo.getText().toString());
        SENDSMSRequest.add("wareHouseNo2", wareHouseNo2);
        SENDSMSRequest.add("prodNo", mProdNo.getText().toString());
        SENDSMSRequest.add("prodName",mProdName.getText().toString());
        SENDSMSRequest.add("nbook_num",mNum.getText().toString().trim().equals("")?"0":mNum.getText().toString().trim());
        SENDSMSRequest.add("sortNo", sortNo.contains("东到西") ? "asc" : "desc");
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("userid", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading) {
        if(!mProdNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(mProdNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo", s);
            CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
        }
    }
    //结束盘点
    //private void DataBind5(boolean isLoading)
    //{
    //    Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual8(), RequestMethod.POST);
    //    SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
    //    SENDSMSRequest.add("sEcho", "1");
    //    SENDSMSRequest.add("wareHouseNo",wareHouseNo2 );
    //    SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
    //    SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
    //    CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    //}

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                inventoryInputDisperse = gson.fromJson(response.get(), InventoryInputDisperse.class);
                if (inventoryInputDisperse.getResult() == 1) {
                    if(inventoryInputDisperse.getData().getWh_status()==3) // 0 是空 1占用 2有货 3预留
                    {
                        mWareHouseNo.setText(inventoryInputDisperse.getData().getWh_wareHouseNo());
                        mProdNo.setText("预留");
                        mProdName.setText("预留");
                        mNum.setText("");
                        mNum.setHint("0");
                        mNum.setEnabled(false);
                    }else if(inventoryInputDisperse.getData().getWh_isInvalid() == 1)//库位是否作废，默认false，true代表已经作废
                    {
                        mWareHouseNo.setText(inventoryInputDisperse.getData().getWh_wareHouseNo());
                        mProdNo.setText("作废");
                        mProdName.setText("作废");
                        mNum.setText("");
                        mNum.setHint("0");
                        mNum.setEnabled(false);
                    }else
                    {
                        mWareHouseNo.setText(inventoryInputDisperse.getData().getWh_wareHouseNo());
                        mProdNo.setText(inventoryInputDisperse.getData().getWh_prodNo());
                        mProdName.setText(inventoryInputDisperse.getData().getWh_prodName());
                        mNum.setEnabled(true);
                        mNum.setHint("0");
                        if(inventoryInputDisperse.getData().getU1_addDate().length() > 0 && inventoryInputDisperse.getData().getU1_userid().equals(CSIMSApplication.getAppContext().getUser().getO_id()))
                        {
                            mNum.setHint(String.valueOf(inventoryInputDisperse.getData().getU1_nbook_num()));
                        }else if(inventoryInputDisperse.getData().getU2_addDate().length() > 0 && inventoryInputDisperse.getData().getU2_userid().equals(CSIMSApplication.getAppContext().getUser().getO_id()))
                        {
                            mNum.setHint(String.valueOf(inventoryInputDisperse.getData().getU2_nbook_num()));
                        }else
                        {
                            mNum.setText("");
                        }
                    }
                    showSoftInputFromWindow(this,mNum);
                }else {
                    //DataBind5(false);
                    ToastUtil.show(this, inventoryInputDisperse.getMsg());
                    Intent intent=new Intent();
                    intent.putExtra("result","result");
                    setResult(RESULT_OK,intent);
                    finish();
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
            case 1002://查询商品
                warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    Intent intent=new Intent();
                    intent.putExtra("result","result");
                    setResult(RESULT_OK,intent);
                    finish();
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
            sType = 0;
            DataBind(true);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Intent intent=new Intent();
            intent.putExtra("result","result");
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}