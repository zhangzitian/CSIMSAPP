package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.opengl.Visibility;
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
import android.widget.RelativeLayout;
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
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.WarehousingErrorForkliftDistributionList;
import com.zitian.csims.model.WarehousingErrorManagerList;
import com.zitian.csims.model.WarehousingInDistributionFullTask2;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class WarehousingErrorForkliftDistributionAddActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    WarehousingInDistributionQualityInput warehousingInDistributionQualityInput1;
    WarehousingInDistributionQualityInput warehousingInDistributionQualityInput2;
    WarehousingErrorForkliftDistributionList.Bean itemModel;

    //RelativeLayout rl19;
    //RelativeLayout rl18;

    private int isUpdate = 0;
    private String sourceType = "";
    private String[] spinnerItems;
    private int mType = 0;

    private ImageView icon_back;
    private AutoCompleteTextView mFromNo;//库位号
    private TextView mFromArea;
    private Spinner mErrType;//纠错类型

    private AutoCompleteTextView cProdNo;//移出单 产品编码
    private TextView cFromNo;//移出单 库位号
    private TextView cFromNo2;
    private TextView cProdName;//移出单 产品名称
    private EditText cNbox_num;//移出单 件
    private EditText cNpack_num;//移出单 包
    private EditText cNbook_num;//移出单 册
    private TextView cPcount;//移出单 总数量

    private AutoCompleteTextView rProdNo;//移入单 产品编码
    private Spinner rFromNo;//移入单 库位号
    private TextView rFromNo2;
    private TextView rProdName;//移入单 产品名称
    private EditText rNbox_num;//移入单 件
    private EditText rNpack_num;//移入单 包
    private EditText rNbook_num;//移入单 册
    private TextView rPcount;//移入单 总数量

    private Button bt01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_error_forklift_distribution_add);

        initView();
        setViewListener();
        initData();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("纠错调整单");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mFromNo = (AutoCompleteTextView) findViewById(R.id.mFromNo);
        mFromArea = (TextView) findViewById(R.id.mFromArea);
        mErrType = (Spinner) findViewById(R.id.mErrType);

        //rl19 = (RelativeLayout) findViewById(R.id.rl19);//移出单 库号
        //rl18 = (RelativeLayout) findViewById(R.id.rl18);//移入单 库号

        cProdNo = (AutoCompleteTextView) findViewById(R.id.cProdNo);
        cFromNo = (TextView) findViewById(R.id.cFromNo);
        cFromNo2 = (TextView) findViewById(R.id.cFromNo2);
        cProdName = (TextView) findViewById(R.id.cProdName);
        cNbox_num = (EditText) findViewById(R.id.cNbox_num);
        cNpack_num = (EditText) findViewById(R.id.cNpack_num);
        cNbook_num = (EditText) findViewById(R.id.cNbook_num);
        cPcount = (TextView) findViewById(R.id.cPcount);

        rProdNo = (AutoCompleteTextView) findViewById(R.id.rProdNo);
        rFromNo = (Spinner) findViewById(R.id.rFromNo);
        rFromNo2 = (TextView) findViewById(R.id.rFromNo2);
        rProdName = (TextView) findViewById(R.id.rProdName);
        rNbox_num = (EditText) findViewById(R.id.rNbox_num);
        rNpack_num = (EditText) findViewById(R.id.rNpack_num);
        rNbook_num = (EditText) findViewById(R.id.rNbook_num);
        rPcount = (TextView) findViewById(R.id.rPcount);
        bt01= (Button) findViewById(R.id.bt01);
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
        BdingTextChangedListener(rNbox_num);
        BdingTextChangedListener(rNpack_num);
        BdingTextChangedListener(rNbook_num);
    }

    public void  initData()
    {
        sourceType = getIntent().getStringExtra("sourceType");
        spinnerItems = getIntent().getStringArrayExtra("spinnerItems");
        mType = getIntent().getIntExtra("mType",0);
        isUpdate = getIntent().getIntExtra("isUpdate",0);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mErrType.setAdapter(spinnerAdapter);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemModel = (WarehousingErrorForkliftDistributionList.Bean) bundle.get("itemModel");

        mFromNo.setText(itemModel.getE_wareHouseNo());
        mFromArea.setText(itemModel.getE_wareArea());

        //禁止OnItemSelectedListener默认自动调用一次
        //mErrType.setSelection(0, true);
        mErrType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                if(spinnerItems[pos].equals("库位为空"))
                {
                    ShowData(true);
                    ShowDataEnabled(false);
                    ShowData2(false);
                    ShowDataEnabled2(false);

                    mFromNo.setEnabled(false);
                }
                if(spinnerItems[pos].equals("库位被占用"))
                {
                    if(sourceType.equals("补货纠错") || sourceType.equals("质量下架纠错") ||  sourceType.equals("改版下架纠错"))
                        ShowData(true);
                    else  if(sourceType.equals("入库纠错")  || sourceType.equals("质量上架纠错")  || sourceType.equals("爆仓区纠错") )
                        ShowData(false);

                    ShowDataEnabled(false);
                    if(isUpdate == 0)
                        ShowData2(false);
                    if(isUpdate == 1)
                        ShowData2(true);
                    ShowDataEnabled2(true);

                    mFromNo.setEnabled(false);
                    SetProdNo(rProdNo,1);
                    rProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object obj = parent.getItemAtPosition(position);
                            String s = ConvertToProdNo(obj.toString());
                            rProdNo.setText(s);
                            rProdNo.setSelection(s.length());//set cursor to the end
                            DataBind3(true);
                            //DataBind5(true);
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        mErrType.setSelection(mType,true);
    }

    public void ShowData(boolean b)
    {
        if(b && itemModel!=null)
        {
            cProdNo.setText(itemModel.getE_output_prodNo());
            cFromNo.setText(itemModel.getE_wareHouseNo());
            //rl19.setVisibility(View.VISIBLE);
            //if(itemModel.getE_wareHouseNo().contains("区"))
            //    rl19.setVisibility(View.GONE);
            //else
            //    rl19.setVisibility(View.VISIBLE);
            cFromNo2.setText(itemModel.getE_wareHouseNo());
            cProdName.setText(itemModel.getE_output_prodName());
            cNbox_num.setText(String.valueOf(itemModel.getE_output_nbox_num()));
            cNpack_num.setText(String.valueOf(itemModel.getE_output_npack_num()));
            cNbook_num.setText(String.valueOf(itemModel.getE_output_nbook_num()));
            cPcount.setText(String.valueOf(itemModel.getE_output_total()));
        }else
        {
            cProdNo.setText("");
            cFromNo.setText("");
            cFromNo2.setText("");
            cProdName.setText("");
            //cNbox_num.setText("");
            //cNpack_num.setText("");
            //cNbook_num.setText("");
            //cPcount.setText("");
        }
    }

    public void ShowDataEnabled(boolean b)
    {
        if(b )
        {
            cProdNo.setEnabled(true);
            cFromNo.setEnabled(true);
            cProdName.setEnabled(true);
            //cNbox_num.setEnabled(true);
            //cNpack_num.setEnabled(true);
            //cNbook_num.setEnabled(true);
            //cPcount.setEnabled(true);
        }else
        {
            cProdNo.setEnabled(false);
            cFromNo.setEnabled(false);
            cProdName.setEnabled(false);
            cNbox_num.setEnabled(false);
            cNpack_num.setEnabled(false);
            cNbook_num.setEnabled(false);
            cPcount.setEnabled(false);
        }
    }

    public void ShowData2(boolean b)
    {
        if(b && itemModel!=null)
        {
            rProdNo.setText(itemModel.getE_input_prodNo());
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, new String[]{"产品纠错区"});
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            rFromNo.setAdapter(spinnerAdapter);
            rProdName.setText(itemModel.getE_input_prodName());
            rNbox_num.setText(String.valueOf(itemModel.getE_input_nbox_num()));
            rNpack_num.setText(String.valueOf(itemModel.getE_input_npack_num()));
            rNbook_num.setText(String.valueOf(itemModel.getE_input_nbook_num()));
            rPcount.setText(String.valueOf(itemModel.getE_input_total()));
        }else
        {
            rProdNo.setText("");
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, new String[]{""});
            spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            rFromNo.setAdapter(spinnerAdapter);
            rProdName.setText("");
            rNbox_num.setText("");
            rNpack_num.setText("");
            rNbook_num.setText("");
            rPcount.setText("");
        }
    }

    public void ShowDataEnabled2(boolean b)
    {
        if(b)
        {
            rProdNo.setEnabled(true);
            rFromNo.setEnabled(true);
            rProdName.setEnabled(true);
            //rNbox_num.setEnabled(true);
            //rNpack_num.setEnabled(true);
            //rNbook_num.setEnabled(true);
            //rPcount.setEnabled(true);
        }else
        {
            rProdNo.setEnabled(false);
            rFromNo.setEnabled(false);
            rProdName.setEnabled(false);
            //rNbox_num.setEnabled(false);
            //rNpack_num.setEnabled(false);
            //rNbook_num.setEnabled(false);
            //rPcount.setEnabled(false);
        }
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

    private void DataBind(boolean isLoading)
    {
        if(mErrType.getSelectedItem().toString().equals("库位被占用")) {
            if (rProdName.getText().toString().trim().length() == 0) {
                ToastUtil.show(this, "移入单产品产品名称不能等于空");
                return;
            }
            if (rProdNo.getText().toString().trim().length() == 0) {
                ToastUtil.show(this, "移入单产品编码不能等于空");
                return;
            }
            if (rProdNo.getText().toString().trim().equals(cProdNo.getText().toString().trim())) {
                ToastUtil.show(this, "移入单和移除单的产品编号不能相同");
                return;
            }
            if (rProdNo.getText().toString().trim().equals("")) {
                ToastUtil.show(this, "移入单的产品编号不能为空");
                return;
            }
        }
        String url = "";
        if(isUpdate==1)
        {
            url = AppConstant.OutofSpaceForkliftError3();
        }else
        {
            url = AppConstant.OutofSpaceForkliftError();
        }
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("e_id",itemModel.getE_id());
        //mFromNo
        SENDSMSRequest.add("e_wareHouseNo",mFromNo.getText().toString());
        SENDSMSRequest.add("e_wareArea",mFromArea.getText().toString());
        //mErrType
        SENDSMSRequest.add("e_errType",mErrType.getSelectedItem().toString());
        //mErrMember
        SENDSMSRequest.add("e_errMember", "");
        //cProdNo
        SENDSMSRequest.add("e_output_prodNo",ConvertToProdNo(cProdNo.getText().toString()));
        //cProdName
        SENDSMSRequest.add("e_output_prodName",cProdName.getText().toString());
        //cFromNo
        SENDSMSRequest.add("e_output_wareHouseNo",cFromNo.getText().toString());

        //cNbox_num
        SENDSMSRequest.add("e_output_nbox_num",cNbox_num.getText().toString().equals("") ? "0" : cNbox_num.getText().toString());
        //cNpack_num
        SENDSMSRequest.add("e_output_npack_num",cNpack_num.getText().toString().equals("") ? "0" : cNpack_num.getText().toString());
        //cNbook_num
        SENDSMSRequest.add("e_output_nbook_num",cNbook_num.getText().toString().equals("") ? "0" : cNbook_num.getText().toString());
        //cPcount
        SENDSMSRequest.add("e_output_total",cPcount.getText().toString().equals("") ? "0" : cPcount.getText().toString());

        //rProdNo
        SENDSMSRequest.add("e_input_prodNo",ConvertToProdNo(rProdNo.getText().toString()));
        //rProdName
        SENDSMSRequest.add("e_input_prodName",rProdName.getText().toString());
        //rFromNo
        //SENDSMSRequest.add("e_input_wareHouseNo",rFromNo.getSelectedItem().toString());
        SENDSMSRequest.add("e_input_wareHouseNo", rFromNo.getSelectedItem().toString());
        //rNbox_num
        SENDSMSRequest.add("e_input_nbox_num",rNbox_num.getText().toString().equals("") ? "0" : rNbox_num.getText().toString());
        //rNpack_num
        SENDSMSRequest.add("e_input_npack_num",rNpack_num.getText().toString().equals("") ? "0" : rNpack_num.getText().toString());
        //rNbook_num
        SENDSMSRequest.add("e_input_nbook_num",rNbook_num.getText().toString().equals("") ? "0" : rNbook_num.getText().toString());
        //rPcount
        SENDSMSRequest.add("e_input_total",rPcount.getText().toString().equals("") ? "0" : rPcount.getText().toString());
        //userName
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());

        SENDSMSRequest.add("e_exception3",itemModel == null ? "0" : itemModel.getE_exception3());//爆仓区（库位表）主键ID

        SENDSMSRequest.add("t_ID",itemModel == null ? "0" : itemModel.getE_exception() );//任务表主键

        SENDSMSRequest.add("e_sourceType",sourceType);//任务表主键
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading) {
        if(!cProdNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(cProdNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo", s.replace("t","T"));
            CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
        }
    }
    private void DataBind3(boolean isLoading) {
        if(!rProdNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(rProdNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo", s.replace("t","T"));
            CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
        }
    }
    private void DataBind5(boolean isLoading) {
        if(!rProdNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(rProdNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.OutofSpaceForkliftError2(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("q_prodNo", s);
            CallServer.getInstance().add(this, 1004, SENDSMSRequest, this, false, isLoading);
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://提交数据
                OutofSpaceForkliftList outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this,outofSpaceForkliftList.getMsg());
                    finish();
                }else
                {
                    ToastUtil.show(this,outofSpaceForkliftList.getMsg());
                }
                break;
            case 1001://查询商品
//                warehousingInDistributionQualityInput1 = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
//                if (warehousingInDistributionQualityInput1.getResult() == 1) {
//                    if (warehousingInDistributionQualityInput1.getData() != null) {
//                        cProdName.setText(warehousingInDistributionQualityInput1.getData().h_name);
//                    } else if (warehousingInDistributionQualityInput1.getResult() == 0) {
//                    }
//                }
                break;
            case 1002://查询商品
                warehousingInDistributionQualityInput2 = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput2.getResult() == 1) {
                    if(mErrType.getSelectedItem().toString().equals("库位被占用")) {

                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, new String[]{"产品纠错区"});
                        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        rFromNo.setAdapter(spinnerAdapter);

                        rProdName.setText(warehousingInDistributionQualityInput2.getData().h_name);
                        double ii = warehousingInDistributionQualityInput2.getData().nPlateNum/warehousingInDistributionQualityInput2.getData().nBoxNum;
                        rNbox_num.setText(String.valueOf(Math.round(ii)));
                        rNpack_num.setText("0");
                        rNbook_num.setText("0");
                    }
                }
                break;
            case 1004://入库单
                WarehousingInDistributionFullTask2 warehousingInDistributionFullTask2 = gson.fromJson(response.get(), WarehousingInDistributionFullTask2.class);
                if (warehousingInDistributionFullTask2.getResult() == 1) {
                    if (warehousingInDistributionFullTask2.getData() != null) {
                        String[]  spinnerItems = new String[]{ warehousingInDistributionFullTask2.getData().getWh_wareArea()};
                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(), R.layout.simple_spinner_item, spinnerItems);
                        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        rFromNo.setAdapter(spinnerAdapter);
                        rFromNo2.setText(warehousingInDistributionFullTask2.getData().getWh_wareHouseNo());
                    } else if (warehousingInDistributionFullTask2.getResult() == 0) {
                    }
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

    //计算数量
    public void BdingTextChangedListener(EditText tv)
    {
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumR();
            }
        });
    }

    public void SumC()
    {
        if(cProdName.getText().toString().length() > 0 )
        {
            if(warehousingInDistributionQualityInput1!=null)
            {
                int cNbox = Integer.valueOf(cNbox_num.getText().toString().trim().equals("") ? "0" : cNbox_num.getText().toString().trim()) * warehousingInDistributionQualityInput1.getData().nBoxNum;
                int cNpack = Integer.valueOf(cNpack_num.getText().toString().trim().equals("") ? "0" : cNpack_num.getText().toString().trim()) * warehousingInDistributionQualityInput1.getData().nPackNum;
                int cNbook = Integer.valueOf(cNbook_num.getText().toString().trim().equals("") ? "0" : cNbook_num.getText().toString().trim()) ;
                cPcount.setText(String.valueOf(cNbox +cNpack+cNbook));
            }
        }
    }

    public void SumR()
    {
        if(rProdName.getText().toString().length() > 0)
        {
            if(warehousingInDistributionQualityInput2!=null)
            {
                int rNbox = Integer.valueOf(rNbox_num.getText().toString().trim().equals("") ? "0" : rNbox_num.getText().toString().trim()) * warehousingInDistributionQualityInput2.getData().nBoxNum;
                int rNpack = Integer.valueOf(rNpack_num.getText().toString().trim().equals("") ? "0" : rNpack_num.getText().toString().trim()) * warehousingInDistributionQualityInput2.getData().nPackNum;
                int rNbook = Integer.valueOf(rNbook_num.getText().toString().trim().equals("") ? "0" : rNbook_num.getText().toString().trim()) ;
                rPcount.setText(String.valueOf(rNbox +rNpack+rNbook));
            }
        }
    }


}