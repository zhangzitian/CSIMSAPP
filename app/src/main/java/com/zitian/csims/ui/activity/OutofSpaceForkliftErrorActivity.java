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
import com.zitian.csims.model.WarehousingInDistributionFullTask2;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class OutofSpaceForkliftErrorActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    WarehousingInDistributionQualityInput warehousingInDistributionQualityInput1;
    WarehousingInDistributionQualityInput warehousingInDistributionQualityInput2;

    RelativeLayout rl19;
    RelativeLayout rl18;

    TextView cFromNo2;
    TextView rFromNo2;

    private ImageView icon_back;
    private RelativeLayout mRl3;

    private int mType = 0;
    private OutofSpaceForkliftList.Bean itemModel;

    private TextView mFromNo;//库位号
    private Spinner mErrType;//纠错类型
    private Spinner mErrMember;//过失人

    private AutoCompleteTextView cProdNo;//移出单 产品编码
    private TextView cFromNo;//移出单 库位号
    private TextView cProdName;//移出单 产品名称
    private EditText cNbox_num;//移出单 件
    private EditText cNpack_num;//移出单 包
    private EditText cNbook_num;//移出单 册
    private TextView cPcount;//移出单 总数量

    private AutoCompleteTextView rProdNo;//移入单 产品编码
    private Spinner rFromNo;//移入单 库位号
    private TextView rProdName;//移入单 产品名称
    private EditText rNbox_num;//移入单 件
    private EditText rNpack_num;//移入单 包
    private EditText rNbook_num;//移入单 册
    private TextView rPcount;//移入单 总数量

    private String sFromNo;//库位号
    //private String sErrType;//纠错类型
    private String sProdNo;//移入单 产品编码
    //private String sFromNo;//移入单 库位区
    private String sProdName;//移入单 产品名称
    private int sNbox_num;//移入单 件
    private int sNpack_num;//移入单 包
    private int sNbook_num;//移入单 册
    private int sPcount;//移入单 总数量
    private String sexception;//爆仓区（库位表）主键ID
    private int t_ID;//任务主键ID
    private Button bt01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outof_space_forklift_error);

        initView();
        setViewListener();
        initData();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("纠错页面");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        mRl3 = (RelativeLayout) findViewById(R.id.rl3);

        mFromNo = (TextView) findViewById(R.id.mFromNo);
        mErrType = (Spinner) findViewById(R.id.mErrType);
        mErrMember = (Spinner) findViewById(R.id.mErrMember);

        cProdNo = (AutoCompleteTextView) findViewById(R.id.cProdNo);
        cFromNo = (TextView) findViewById(R.id.cFromNo);
        cProdName = (TextView) findViewById(R.id.cProdName);
        cNbox_num = (EditText) findViewById(R.id.cNbox_num);
        cNpack_num = (EditText) findViewById(R.id.cNpack_num);
        cNbook_num = (EditText) findViewById(R.id.cNbook_num);
        cPcount = (TextView) findViewById(R.id.cPcount);

        rProdNo = (AutoCompleteTextView) findViewById(R.id.rProdNo);
        rFromNo = (Spinner) findViewById(R.id.rFromNo);
        rProdName = (TextView) findViewById(R.id.rProdName);
        rNbox_num = (EditText) findViewById(R.id.rNbox_num);
        rNpack_num = (EditText) findViewById(R.id.rNpack_num);
        rNbook_num = (EditText) findViewById(R.id.rNbook_num);
        rPcount = (TextView) findViewById(R.id.rPcount);
        bt01= (Button) findViewById(R.id.bt01);

         rl19 = (RelativeLayout) findViewById(R.id.rl19);
         rl18 = (RelativeLayout) findViewById(R.id.rl18);

         cFromNo2 = (TextView) findViewById(R.id.cFromNo2);
         rFromNo2 = (TextView) findViewById(R.id.rFromNo2);

        String[]  spinnerItems = new String[]{ "产品纠错区", "产品配货区", "销售部残书区"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),  R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        rFromNo.setAdapter(spinnerAdapter);
        rFromNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                //ToastUtil.show(getApplicationContext(),"选择了["+spinnerItems[pos]+"]");
                if(spinnerItems[pos].equals("产品配货区"))
                {
                    DataBind5(true);
                    rl18.setVisibility(View.VISIBLE);
                }else
                {
                    rl18.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        final String[] spinnerItems2 = {"储运部","物流公司","张三","李四"};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(getApplication(),
                R.layout.simple_spinner_item, spinnerItems2);
        spinnerAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mErrMember.setAdapter(spinnerAdapter2);
        mErrMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
               //ToastUtil.show(getApplicationContext(),"选择了["+spinnerItems2[pos]+"]");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        rProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                rProdNo.setText(s);
                rProdNo.setSelection(s.length());//set cursor to the end

                DataBind2(true);
            }
        });

        rProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                rProdNo.setText(s);
                rProdNo.setSelection(s.length());//set cursor to the end

                DataBind3(true);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////////
        cProdName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                DataBind4(true);

                SumC();
            }
        });
        cNbox_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumC();
            }
        });
        cNpack_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumC();
            }
        });
        cNbook_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumC();
            }
        });

        rProdName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                DataBind5(true);

                SumR();
            }
        });
        rNbox_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumR();
            }
        });
        rNpack_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumR();
            }
        });
        rNbook_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                SumR();
            }
        });

    }


    public void SumC()
    {
        if(cProdName.getText().toString().length() > 0 && cNbox_num.getText().toString().length()>0 && cNpack_num.getText().toString().length()>0 && cNbook_num.getText().toString().length()>0)
        {
            if(warehousingInDistributionQualityInput1!=null)
            {
                int cNbox = Integer.valueOf(cNbox_num.getText().toString()) * warehousingInDistributionQualityInput1.getData().nBoxNum;
                int cNpack = Integer.valueOf(cNpack_num.getText().toString()) * warehousingInDistributionQualityInput1.getData().nBoxNum;
                int cNbook = Integer.valueOf(cNbook_num.getText().toString()) ;
                cPcount.setText(String.valueOf(cNbox +cNpack+cNbook));
            }
        }
    }

    public void SumR()
    {
        if(rProdName.getText().toString().length() > 0 && rNbox_num.getText().toString().length()>0 && rNpack_num.getText().toString().length()>0 && rNbook_num.getText().toString().length()>0)
        {
            if(warehousingInDistributionQualityInput2!=null)
            {
                int rNbox = Integer.valueOf(rNbox_num.getText().toString()) * warehousingInDistributionQualityInput2.getData().nBoxNum;
                int rNpack = Integer.valueOf(rNpack_num.getText().toString()) * warehousingInDistributionQualityInput2.getData().nBoxNum;
                int rNbook = Integer.valueOf(rNbook_num.getText().toString()) ;
                rPcount.setText(String.valueOf(rNbox +rNpack+rNbook));
            }
        }

    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    public void  initData()
    {
//        itemModel = (OutofSpaceForkliftList.Bean)getIntent().getSerializableExtra("itemModel");
//        mType = getIntent().getIntExtra("mType",0);
//
//        mFromNo.setText(itemModel.getT_fromNo());
//        //mErrType = (Spinner) findViewById(R.id.mErrType);
//        //mErrMember = (Spinner) findViewById(R.id.mErrMember);
//        cProdNo.setText(itemModel.getT_prodNo());
//        cFromNo.setText(itemModel.getT_fromNo());
//        cProdName.setText(itemModel.getT_prodName());
//        cNbox_num.setText(String.valueOf(itemModel.getT_nbox_num()));
//        cNpack_num.setText(String.valueOf(itemModel.getT_npack_num()));
//        cNbook_num.setText(String.valueOf(itemModel.getT_nbook_num()));
//        cPcount.setText(String.valueOf(itemModel.getT_count()));

//        rProdNo.setText(itemModel.getT_prodNo());
//        rFromNo.setText(itemModel.getT_fromNo());
//        rProdName.setText(itemModel.getT_prodName());
//        rNbox_num.setText(String.valueOf(itemModel.getT_nbox_num()));
//        rNpack_num.setText(String.valueOf(itemModel.getT_npack_num()));
//        rNbook_num.setText(String.valueOf(itemModel.getT_nbook_num()));
//        rPcount.setText(String.valueOf(itemModel.getT_count()));

        SetProdNo(cProdNo,1);
        SetProdNo(rProdNo,1);

        sFromNo = getIntent().getStringExtra("sFromNo");//库位号
        //sErrType = getIntent().getStringExtra("sErrType");;//纠错类型
        sProdNo = getIntent().getStringExtra("sProdNo");//移入单 产品编码
        //sFromNo = getIntent().getStringExtra("sFromNo");;//移入单 库位号
        sProdName = getIntent().getStringExtra("sProdName");;//移入单 产品名称
        sNbox_num = getIntent().getIntExtra("sNbox_num",0);;//移入单 件
        sNpack_num = getIntent().getIntExtra("sNpack_num",0);;//移入单 包
        sNbook_num = getIntent().getIntExtra("sNbook_num",0);;//移入单 册
        sPcount = getIntent().getIntExtra("sPcount",0);;//移入单 总数量
        sexception = getIntent().getStringExtra("sexception");//移入单 产品编码
        t_ID = getIntent().getIntExtra("t_ID",0);//移入单 产品编码

        mFromNo.setText(sFromNo);
        cFromNo.setText(sFromNo);
        if(sFromNo.equals("配货区"))
        {
            DataBind4(true);
            rl19.setVisibility(View.VISIBLE);
        }else
        {
            rl19.setVisibility(View.GONE);
        }

        mType = getIntent().getIntExtra("mType",0);
        String[] spinnerItems ;
        if(mType==0)
        {
            spinnerItems = new String[]{"库位为空"};
            ShowData(true);
            ShowDataEnabled(false);
            ShowData2(false);
            ShowDataEnabled2(false);
        }else  if(mType==1)
        {
            spinnerItems = new String[]{"库位被占用"};
            ShowData(false);
            ShowDataEnabled(false);

            ShowData2(false);
            ShowDataEnabled2(true);
        }else
        {
            spinnerItems = new String[]{ "多书", "少书", "混装", "破损", "调账"};
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),
               R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mErrType.setAdapter(spinnerAdapter);
        mErrType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                //ToastUtil.show(getApplicationContext(),"选择了["+spinnerItems[pos]+"]");
                if(spinnerItems[pos].equals("破损"))
                {
                    mRl3.setVisibility(View.VISIBLE);
                }else
                {
                    mRl3.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });




        //0
        //库位为空库位被占用
        //1
        //多书","少书","混装","破损","调账"
    }

    public void ShowData(boolean b)
    {
        if(b)
        {
            cProdNo.setText(sProdNo);
            //cFromNo.setText(sFromNo);
            cProdName.setText(sProdName);
            cNbox_num.setText(String.valueOf(sNbox_num));
            cNpack_num.setText(String.valueOf(sNpack_num));
            cNbook_num.setText(String.valueOf(sNbook_num));
            cPcount.setText(String.valueOf(sPcount));
        }else
        {
            cProdNo.setText("");
            //cFromNo.setText("");
            cProdName.setText("");
            cNbox_num.setText("");
            cNpack_num.setText("");
            cNbook_num.setText("");
            cPcount.setText("");
        }
    }

    public void ShowDataEnabled(boolean b)
    {
        if(b)
        {
            cProdNo.setEnabled(true);
            cFromNo.setEnabled(true);
            cProdName.setEnabled(true);
            cNbox_num.setEnabled(true);
            cNpack_num.setEnabled(true);
            cNbook_num.setEnabled(true);
            cPcount.setEnabled(true);
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
        if(b)
        {
            rProdNo.setText(sProdNo);
            //rFromNo.setText(sFromNo);
            rProdName.setText(sProdName);
            rNbox_num.setText(String.valueOf(sNbox_num));
            rNpack_num.setText(String.valueOf(sNpack_num));
            rNbook_num.setText(String.valueOf(sNbook_num));
            rPcount.setText(String.valueOf(sPcount));
        }else
        {
            rProdNo.setText("");
            //rFromNo.setText("");
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
            rNbox_num.setEnabled(true);
            rNpack_num.setEnabled(true);
            rNbook_num.setEnabled(true);
            rPcount.setEnabled(true);
        }else
        {
            rProdNo.setEnabled(false);
            rFromNo.setEnabled(false);
            rProdName.setEnabled(false);
            rNbox_num.setEnabled(false);
            rNpack_num.setEnabled(false);
            rNbook_num.setEnabled(false);
            rPcount.setEnabled(false);
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
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.OutofSpaceForkliftError(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //mFromNo
        SENDSMSRequest.add("e_wareHouseNo",mFromNo.getText().toString());
        //mErrType
        SENDSMSRequest.add("e_errType",mErrType.getSelectedItem().toString());
        //mErrMember
        if(mErrType.getSelectedItem().toString().equals("破损")) {
            SENDSMSRequest.add("e_errMember", mErrMember.getSelectedItem().toString());
        }else
        {
            SENDSMSRequest.add("e_errMember", "");
        }
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
        if(rFromNo.getSelectedItem().toString().equals("产品配货区"))
        {
            SENDSMSRequest.add("e_input_wareHouseNo", rFromNo2.getText().toString());
        }else {
            SENDSMSRequest.add("e_input_wareHouseNo", rFromNo.getSelectedItem().toString());
        }
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

        SENDSMSRequest.add("e_exception3",sexception);//爆仓区（库位表）主键ID

        SENDSMSRequest.add("t_ID",t_ID);//任务表主键

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

    private void DataBind4(boolean isLoading) {
        if(!cProdNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(cProdNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.OutofSpaceForkliftError2(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("q_prodNo", s);
            CallServer.getInstance().add(this, 1003, SENDSMSRequest, this, false, isLoading);
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
            case 1000://快速登录
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
                warehousingInDistributionQualityInput1 = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput1.getResult() == 1) {
                    if (warehousingInDistributionQualityInput1.getData() != null) {
                        cProdName.setText(warehousingInDistributionQualityInput1.getData().h_name);
                    } else if (warehousingInDistributionQualityInput1.getResult() == 0) {

                    }
                }
                break;
            case 1002://查询商品
                 warehousingInDistributionQualityInput2 = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput2.getResult() == 1) {
                    if (warehousingInDistributionQualityInput2.getData() != null) {
                        rProdName.setText(warehousingInDistributionQualityInput2.getData().h_name);
                    } else if (warehousingInDistributionQualityInput2.getResult() == 0) {

                    }
                }
                break;

            case 1003://出库单
                WarehousingInDistributionFullTask2 warehousingInDistributionFullTask2 = gson.fromJson(response.get(), WarehousingInDistributionFullTask2.class);
                if (warehousingInDistributionFullTask2.getResult() == 1) {
                    if (warehousingInDistributionFullTask2.getData() != null) {
                        cFromNo2.setText(warehousingInDistributionFullTask2.getData().getWh_wareHouseNo());
                    } else if (warehousingInDistributionFullTask2.getResult() == 0) {

                    }
                }
                break;
            case 1004://入库单
                warehousingInDistributionFullTask2 = gson.fromJson(response.get(), WarehousingInDistributionFullTask2.class);
                if (warehousingInDistributionFullTask2.getResult() == 1) {
                    if (warehousingInDistributionFullTask2.getData() != null) {
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


}