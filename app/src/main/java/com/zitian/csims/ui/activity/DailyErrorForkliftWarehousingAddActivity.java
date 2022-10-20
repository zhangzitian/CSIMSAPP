package com.zitian.csims.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
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
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle2;
import com.zitian.csims.util.ToastUtil;

public class DailyErrorForkliftWarehousingAddActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    AutoCompleteTextView mProdNo;
    TextView  mProdName;
    EditText mNum;
    //EditText add_content;
    Button bt01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_error_forklift_warehousing_add);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("添加盘点入库单");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mProdNo = (AutoCompleteTextView) findViewById(R.id.mProdNo);
        mProdName = (TextView) findViewById(R.id.mWareHouseNo);
        mNum = (EditText) findViewById(R.id.mNum);
        //add_content = (EditText) findViewById(R.id.add_content);
        bt01 = (Button) findViewById(R.id.bt01);

        SetProdNo(mProdNo,1);

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
                CustomDialogStyle2.Builder builder = new CustomDialogStyle2.Builder(DailyErrorForkliftWarehousingAddActivity.this);
                builder.setTitle("温馨提示")
                        .setMessage("确定要产品编号【"+mProdNo.getText().toString()+"】数量是【"+mNum.getText().toString().trim()+"】吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositionButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataBind2(true);
                            }
                        })
                        .create()
                        .show();

                break;
        }
    }

    private void DataBind(boolean isLoading) {
        if(!mProdNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(mProdNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo", s);
            CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        }
    }

    private void DataBind2(boolean isLoading)
    {
        if(!mNum.getText().toString().equals("")  ) {
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.DailyErrorForkliftWarehousingAdd(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("sEcho", "1");
            SENDSMSRequest.add("t_prodNo", mProdNo.getText().toString());
            SENDSMSRequest.add("t_count", mNum.getText().toString().trim());
            SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
            //SENDSMSRequest.add("remark", add_content.getText().toString());
            CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
        }else
        {
            Alert("提示","数量不能等于空！！！","我知道了");
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                WarehousingInDistributionQualityInput warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    if (warehousingInDistributionQualityInput.getData() != null) {
                        mProdName.setText(warehousingInDistributionQualityInput.getData().h_name);
                    }
                }
                break;
            case 1001://查询商品
                OutofSpaceForkliftList outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this,outofSpaceForkliftList.getMsg());
                    finish();
                }  else {
                    ToastUtil.show(this,outofSpaceForkliftList.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

}