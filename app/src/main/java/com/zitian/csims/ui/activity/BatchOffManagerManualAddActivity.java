package com.zitian.csims.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.zitian.csims.model.BatchOffManagerManualAdd2;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class BatchOffManagerManualAddActivity   extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    WarehousingInDistributionQualityInput warehousingInDistributionQualityInput1;
    BatchOffManagerManualAdd2 batchOffManagerManualAdd2 = null;
    String O_ID = "0";

    private ImageView icon_back;
    //private ImageView search_image;
    AutoCompleteTextView prodNo;
    TextView prodName;
    EditText t_nbox_num;
    EditText t_npack_num;
    EditText t_nbook_num;
    TextView Sum_num;

    Spinner mPerson;
    Button bt01;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_off_manager_manual_add);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("批量手动上架");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        //search_image = (ImageView) findViewById(R.id.search_image);
        prodNo = (AutoCompleteTextView) findViewById(R.id.mProdNo);
        prodName = (TextView) findViewById(R.id.mWareHouseNo);
        t_nbox_num = (EditText) findViewById(R.id.mNbox_num);
        t_npack_num = (EditText) findViewById(R.id.mNpack_num);
        t_nbook_num = (EditText) findViewById(R.id.mNbook_num);
        Sum_num = (TextView) findViewById(R.id.mSum_num);
        mPerson = (Spinner) findViewById(R.id.mPerson);
        bt01 = (Button) findViewById(R.id.bt01);
        SetProdNo(prodNo,1);
        DataBind3(true);

        prodNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                prodNo.setText(s);
                prodNo.setSelection(s.length());//set cursor to the end
                DataBind(true);
            }
        });

        prodName.addTextChangedListener(new TextWatcher() {
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

        t_nbox_num.addTextChangedListener(new TextWatcher() {
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

        t_npack_num.addTextChangedListener(new TextWatcher() {
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

        t_nbook_num.addTextChangedListener(new TextWatcher() {
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
                DataBind2(true);
                break;
        }
    }

    private void DataBind3(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.BatchOffManagerManualAdd2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.BatchOffManagerManualAdd(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("prodNo", prodNo.getText().toString());
        SENDSMSRequest.add("prodName", prodName.getText().toString());
        SENDSMSRequest.add("t_nbox_num", t_nbox_num.getText().toString().trim().equals("")? "0" : t_nbox_num.getText().toString());
        SENDSMSRequest.add("t_npack_num", t_npack_num.getText().toString().trim().equals("")? "0" : t_npack_num.getText().toString());
        SENDSMSRequest.add("t_nbook_num", t_nbook_num.getText().toString().trim().equals("")? "0" : t_nbook_num.getText().toString());
        SENDSMSRequest.add("person", O_ID);
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("prodNo",prodNo.getText().toString());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://查询商品
                warehousingInDistributionQualityInput1 = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput1.getResult() == 1) {
                    if (warehousingInDistributionQualityInput1.getData() != null) {
                        prodName.setText(warehousingInDistributionQualityInput1.getData().h_name);
                    } else if (warehousingInDistributionQualityInput1.getResult() == 0) {

                    }
                }
                break;
            case 1001://查询商品
                warehousingInDistributionQualityInput1 = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput1.getResult() == 1) {
                    ToastUtil.show(this,warehousingInDistributionQualityInput1.getMsg());
                    finish();
                }else
                    ToastUtil.show(this,warehousingInDistributionQualityInput1.getMsg());
                break;
            case 1002:
                batchOffManagerManualAdd2 = gson.fromJson(response.get(), BatchOffManagerManualAdd2.class);
                if (batchOffManagerManualAdd2.getResult() == 1) {
                    ArrayAdapter<BatchOffManagerManualAdd2.Bean> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, batchOffManagerManualAdd2.getData());
                    spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    mPerson.setAdapter(spinnerAdapter);
                    mPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(BatchOffManagerManualAddActivity.this,"你选择了："+batchOffManagerManualAdd2.getData().get(position).getO_id(), Toast.LENGTH_SHORT).show();
                            //O_ID = batchOffManagerManualAdd2.getData().get(position).getO_id();
                            O_ID = ((BatchOffManagerManualAdd2.Bean) mPerson.getSelectedItem()).getO_id();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }else
                    ToastUtil.show(this,batchOffManagerManualAdd2.getMsg());
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

    public void SumC()
    {
        if(prodName.getText().toString().length() > 0 )
        {
            if(warehousingInDistributionQualityInput1!=null)
            {
                int cNbox = Integer.valueOf(t_nbox_num.getText().toString().trim().equals("") ? "0" : t_nbox_num.getText().toString().trim()) * warehousingInDistributionQualityInput1.getData().nBoxNum;
                int cNpack = Integer.valueOf(t_npack_num.getText().toString().trim().equals("") ? "0" : t_npack_num.getText().toString().trim()) * warehousingInDistributionQualityInput1.getData().nPackNum;
                int cNbook = Integer.valueOf(t_nbook_num.getText().toString().trim().equals("") ? "0" : t_nbook_num.getText().toString().trim()) ;
                Sum_num.setText(String.valueOf(cNbox +cNpack+cNbook));
            }
        }
    }

}