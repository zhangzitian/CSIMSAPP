package com.zitian.csims.ui.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zitian.csims.model.BatchOffManagerTransferOrderList;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class BatchOffManagerTransferOrderAddActivity   extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Button bt01;

    private BatchOffManagerTransferOrderList.Bean itemModel;
    private int mType = 0;

    private AutoCompleteTextView mProdNo;
    private TextView mProdName;
    private Spinner mOutHouse;
    private Spinner mInHouse;
    private EditText mNum;
    private Spinner  mManufactor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_off_manager_transfer_order_add);

        initView();
        setViewListener();
        initData();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("调拨单");
        tv_title.setGravity(Gravity.CENTER);
        bt01= (Button) findViewById(R.id.bt01);
        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mProdNo = (AutoCompleteTextView) findViewById(R.id.mProdNo);
        mProdName = (TextView) findViewById(R.id.mWareHouseNo);
        mOutHouse = (Spinner) findViewById(R.id.mOutHouse);
        mInHouse = (Spinner) findViewById(R.id.mInHouse);
        mNum = (EditText) findViewById(R.id.mNum);
        mManufactor = (Spinner) findViewById(R.id.mManufactor);

        SetProdNo(mProdNo,1);
        SetFactory(mManufactor);

        mProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                mProdNo.setText(s);
                mProdNo.setSelection(s.length());//set cursor to the end
                DataBind(true);
            }
        });

        String[]  spinnerItems = new String[]{ "质量暂存区","公司待送库"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),  R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mOutHouse.setAdapter(spinnerAdapter);

        String[]  spinnerItems2 = new String[]{ "公司待送库","质量暂存区"};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(getApplication(),  R.layout.simple_spinner_item, spinnerItems2);
        spinnerAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mInHouse.setAdapter(spinnerAdapter2);
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    public void  initData()
    {
        mType = getIntent().getIntExtra("mType",0);
        if(mType==0)
        {

        }else
        {
            itemModel = (BatchOffManagerTransferOrderList.Bean)getIntent().getSerializableExtra("itemModel");
            mProdNo.setText(itemModel.getT_prodNo());
            mProdName.setText(itemModel.getT_prodNo());
            //-------------------------------------------------------
            //String[] outData = new String[]{
            //        itemModel.getT_outHouse()
            //};
            String[]  spinnerItems = new String[]{ "质量暂存区","公司待送库"};
            ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,spinnerItems);
            mOutHouse.setAdapter(adapter);

            if(itemModel.getT_outHouse().equals("质量暂存区"))
                mOutHouse.setSelection(0,true);
            else
                mOutHouse.setSelection(1,true);

            //-------------------------------------------------------
            //String[] inData = new String[]{
            //        itemModel.getT_inHouse()
            //};
            String[]  spinnerItems2 = new String[]{ "公司待送库","质量暂存区"};

            ArrayAdapter adapter2 = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,spinnerItems2);
            mInHouse.setAdapter(adapter2);

            if(itemModel.getT_inHouse().equals("公司待送库"))
                mInHouse.setSelection(0,true);
            else
                mInHouse.setSelection(1,true);

            //-------------------------------------------------------
            mNum.setText(String.valueOf(itemModel.getT_count()));
            //-------------------------------------------------------
            String[] FaData = new String[]{
                    itemModel.getT_factory()
            };
            ArrayAdapter adapter3 = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,FaData);
            mManufactor.setAdapter(adapter3);
        }
    }
    //mType=0 添加
    private void DataBind0(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.BatchOffManagerTransferOrderAdd2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_outHouse",mOutHouse.getSelectedItem().toString());
        SENDSMSRequest.add("t_inHouse",mInHouse.getSelectedItem().toString());
        SENDSMSRequest.add("t_prodNo",mProdNo.getText().toString());
        SENDSMSRequest.add("t_prodName",mProdName.getText().toString());
        SENDSMSRequest.add("t_count",mNum.getText().toString());
        SENDSMSRequest.add("t_factory",mManufactor.getSelectedItem().toString());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }
    //mType=1 修改
    private void DataBind1(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.BatchOffManagerTransferOrderAdd(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_id",itemModel.getT_id());//var t_id = Convert.ToInt32(context.Request["t_id"]);//调拨单ID
        SENDSMSRequest.add("t_outHouse",mOutHouse.getSelectedItem().toString());
        SENDSMSRequest.add("t_inHouse",mInHouse.getSelectedItem().toString());
        SENDSMSRequest.add("t_prodNo",mProdNo.getText().toString());
        SENDSMSRequest.add("t_prodName",mProdName.getText().toString());
        SENDSMSRequest.add("t_count",mNum.getText().toString());
        SENDSMSRequest.add("t_factory",mManufactor.getSelectedItem().toString());
        SENDSMSRequest.add("t_isPrint",itemModel.isT_isPrint());//table_Transfer.t_isPrint = Convert.ToBoolean(context.Request["t_isPrint"]);
        SENDSMSRequest.add("t_printcount",itemModel.getT_printcount());//table_Transfer.t_printcount = Convert.ToInt32(context.Request["t_printcount"]);
        SENDSMSRequest.add("t_state",itemModel.getT_state());//table_Transfer.t_state = context.Request["t_state"];
        SENDSMSRequest.add("t_operator",itemModel.getT_operator());//table_Transfer.t_operator = context.Request["t_operator"];
        SENDSMSRequest.add("t_reviewer",itemModel.getT_reviewer());//table_Transfer.t_reviewer = context.Request["t_reviewer"];
        SENDSMSRequest.add("t_createTime",itemModel.getT_createTime());//table_Transfer.t_createTime = Convert.ToDateTime(context.Request["t_createTime"]);
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind(boolean isLoading) {
        if(!mProdNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(mProdNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo", s);
            CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                if(mType == 0){
                    DataBind0(true);
                }else
                {
                    DataBind1(true);
                }
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
                    ToastUtil.show(this,outofSpaceForkliftList.getMsg());
                    finish();
                }else
                    ToastUtil.show(this,outofSpaceForkliftList.getMsg());
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
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

}