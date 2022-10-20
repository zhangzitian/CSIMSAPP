package com.zitian.csims.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
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
import com.zitian.csims.model.BatchOffManagerManualAdd2;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.PersonModels;
import com.zitian.csims.model.WarehousingErrorForkliftDistributionList;
import com.zitian.csims.model.WarehousingInDistributionFullTask2;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.WaitDialog;
import com.zitian.csims.util.ToastUtil;

public class WarehousingErrorForkliftDistributionAdd2Activity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    WarehousingInDistributionQualityInput warehousingInDistributionQualityInput;  //头部的值
    WarehousingInDistributionFullTask2 warehousingInDistributionFullTask2;

    WarehousingInDistributionQualityInput warehousingInDistributionQualityInput1; //移出单
    WarehousingInDistributionFullTask2 warehousingInDistributionFullTask21;

    WarehousingInDistributionQualityInput warehousingInDistributionQualityInput2; //移入单
    WarehousingInDistributionFullTask2 warehousingInDistributionFullTask22;

    int oper = -1;//是否操作的移除单和移入单的产品编码  数量会发生改变

    WarehousingErrorForkliftDistributionList.Bean itemModel;  //修改传过来的值
    //BatchOffManagerManualAdd2 batchOffManagerManualAdd2 = null;//过失人
    String O_ID = "0";
    private int isUpdate = 0; //0是添加 1是修改
    private String sourceType = "";
    private int mType = 0;

    RelativeLayout rl19;
    RelativeLayout rl18;
    RelativeLayout mRl3; //过失人

    private ImageView icon_back;

    private AutoCompleteTextView mFromNo;//库位号
    private TextView mFromArea;
    private Spinner mErrType;//纠错类型
    private Spinner mErrMember;//过失人

    private AutoCompleteTextView cProdNo;//移出单 产品编码
    private TextView cFromNo;//移出单 库区
    private TextView cFromNo2;//移出单 库位号
    private TextView cProdName;//移出单 产品名称
    private EditText cNbox_num;//移出单 件
    private EditText cNpack_num;//移出单 包
    private EditText cNbook_num;//移出单 册
    private TextView cPcount;//移出单 总数量

    private AutoCompleteTextView rProdNo;//移入单 产品编码
    private AutoCompleteTextView rFromNo;//移入单 库区
    private TextView rFromNo2;//移入单 库位号
    private TextView rProdName;//移入单 产品名称
    private EditText rNbox_num;//移入单 件
    private EditText rNpack_num;//移入单 包
    private EditText rNbook_num;//移入单 册
    private TextView rPcount;//移入单 总数量

    private Button bt01;

    private WaitDialog mWaitDialog2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_error_forklift_distribution_add2);

        initView();
        setViewListener();
        initData();
        //DataBind2(true);

        ArrayAdapter<PersonModels.Bean> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, CSIMSApplication.getAppContext().getPerson().getData());
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mErrMember.setAdapter(spinnerAdapter);
        mErrMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //O_ID = batchOffManagerManualAdd2.getData().get(position).getO_id();
                O_ID = ((PersonModels.Bean) mErrMember.getSelectedItem()).getO_id();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("纠错调整单");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        mRl3 = (RelativeLayout) findViewById(R.id.rl3);

        mFromNo = (AutoCompleteTextView) findViewById(R.id.mFromNo);
        mFromArea = (TextView) findViewById(R.id.mFromArea);

        mErrType = (Spinner) findViewById(R.id.mErrType);
        mErrMember = (Spinner) findViewById(R.id.mErrMember);

        cProdNo = (AutoCompleteTextView) findViewById(R.id.cProdNo);
        cFromNo = (TextView) findViewById(R.id.cFromNo);
        cFromNo2 = (TextView) findViewById(R.id.cFromNo2);
        cProdName = (TextView) findViewById(R.id.cProdName);
        cNbox_num = (EditText) findViewById(R.id.cNbox_num);
        cNpack_num = (EditText) findViewById(R.id.cNpack_num);
        cNbook_num = (EditText) findViewById(R.id.cNbook_num);
        cPcount = (TextView) findViewById(R.id.cPcount);

        rProdNo = (AutoCompleteTextView) findViewById(R.id.rProdNo);
        rFromNo = (AutoCompleteTextView) findViewById(R.id.rFromNo);
        rFromNo2 = (TextView) findViewById(R.id.rFromNo2);
        rProdName = (TextView) findViewById(R.id.rProdName);
        rNbox_num = (EditText) findViewById(R.id.rNbox_num);
        rNpack_num = (EditText) findViewById(R.id.rNpack_num);
        rNbook_num = (EditText) findViewById(R.id.rNbook_num);
        rPcount = (TextView) findViewById(R.id.rPcount);
        bt01= (Button) findViewById(R.id.bt01);

        rl19 = (RelativeLayout) findViewById(R.id.rl19);
        rl18 = (RelativeLayout) findViewById(R.id.rl18);

//        String[]  spinnerItems = new String[]{ "产品纠错区", "产品配货区", "销售部残书区"};
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),  android.R.layout.simple_spinner_item, spinnerItems);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        rFromNo.setAdapter(spinnerAdapter);
//        rFromNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
//                //ToastUtil.show(getApplicationContext(),"选择了["+spinnerItems[pos]+"]");
//                if(spinnerItems[pos].equals("产品配货区"))
//                {
//                    rl18.setVisibility(View.VISIBLE);
//                }else
//                {
//                    rl18.setVisibility(View.GONE);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Another interface callback
//            }
//        });

    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    public void  initData()
    {
        SetAreaNo(mFromNo,"P_area");//SetAreaNo(mFromNo,null);
        SetProdNo(cProdNo,1);
        SetProdNo(rProdNo,1);
        SetAreaNo(rFromNo,null);

        TextChanged();

        mFromNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                if(!obj.toString().contains("区") || !obj.toString().contains("品"));
                {
                    String s = ConvertToAreaNo(obj.toString());
                    mFromNo.setText(s);
                    mFromNo.setSelection(s.length());//set cursor to the end
                    //DataBind3(true,s);
                    if(isUpdate!=1)
                        DataBind3(false);
                }
                mFromArea.setText(ConvertToAreaNo2(obj.toString()));
            }
        });

        cProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                cProdNo.setText(s);
                cProdNo.setSelection(s.length());//set cursor to the end
                if(isUpdate!=1)
                    DataBind3(false);

                oper= 1;//移出单临时标记
            }
        });

        rProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                rProdNo.setText(s);
                rProdNo.setSelection(s.length());//set cursor to the end
                if(isUpdate!=1)
                    DataBind3(false);

                oper= 2; //移入单临时标记
            }
        });


        rFromNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToAreaNo(obj.toString());
                rFromNo.setText(s);
                rFromNo.setSelection(s.length());//set cursor to the end
            }
        });

        isUpdate = getIntent().getIntExtra("isUpdate",0);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mType = getIntent().getIntExtra("mType",2);
        String[] spinnerItems = new String[]{ "多书", "少书", "混装", "破损", "调账"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        mErrType.setAdapter(spinnerAdapter);
        //禁止OnItemSelectedListener默认自动调用一次
        mErrType.setSelection(0, true);

        if(isUpdate==1)
        {
            itemModel = (WarehousingErrorForkliftDistributionList.Bean) bundle.get("itemModel");
            int sel = 0;
            if(itemModel.getE_errType().equals("多书"))
                sel = 0;
            if(itemModel.getE_errType().equals("少书"))
                sel = 1;
            if(itemModel.getE_errType().equals("混装"))
                sel = 2;
            if(itemModel.getE_errType().equals("破损"))
                sel = 3;
            if(itemModel.getE_errType().equals("调账"))
                sel = 4;

            mErrType.setSelection(sel, true);
            mFromNo.setText(itemModel.getE_wareHouseNo());
            mFromArea.setText(itemModel.getE_wareArea());
            cProdNo.setText(itemModel.getE_output_prodNo());
            rProdNo.setText(itemModel.getE_input_prodNo());
            isUpdate = isUpdate+1;
            DataBind3(false);
        }

        mErrType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                if(isUpdate!=1)
                    mErrType();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void mErrType()
    {
        if(mErrType.getSelectedItem().toString().equals("多书"))
        {
            warehousingInDistributionQualityInput2 = warehousingInDistributionQualityInput; //移出单
            warehousingInDistributionFullTask22 = warehousingInDistributionFullTask2;
            //多书","
            //移出单  不可操作
            ShowData(false);
            ShowDataEnabled(false);
            //移入单  填写
            ShowData2(true);
            ShowDataEnabled2(true);
            rProdNo.setEnabled(false);
        }
        if(mErrType.getSelectedItem().toString().equals("少书"))
        {
            warehousingInDistributionQualityInput1 = warehousingInDistributionQualityInput; //移出单
            warehousingInDistributionFullTask21 = warehousingInDistributionFullTask2;
            //少书","
            //移出单  填写
            ShowData(true);
            ShowDataEnabled(true);
            //移入单  不可操作
            ShowData2(false);
            ShowDataEnabled2(false);
            cProdNo.setEnabled(false);
        }
        if(mErrType.getSelectedItem().toString().equals("混装"))
        {
            //if(warehousingInDistributionQualityInput1==null && warehousingInDistributionFullTask21==null)
            if(oper == 0)
            {
                warehousingInDistributionQualityInput1 = warehousingInDistributionQualityInput; //移出单
                warehousingInDistributionFullTask21 = warehousingInDistributionFullTask2;
            }
            if(oper==-1)
            {
                //混装","
                ShowData(true);
                ShowDataEnabled(true);
                //移出单  填写  ---移出填写多少件   移入多少件   （带上 件包本  产品不一样 计算的总数是不一样的）
                ShowData2(true);
                ShowDataEnabled2(true);
                //移入单  填写
            }
            if(oper==0)
            {
                //混装","
                ShowData(true);
                ShowDataEnabled(true);
                //移出单  填写  ---移出填写多少件   移入多少件   （带上 件包本  产品不一样 计算的总数是不一样的）
                ShowData2(true);
                ShowDataEnabled2(true);
                //移入单  填写
            }
            else if(oper==1)
            {
                //混装","
                ShowData(true);
                ShowDataEnabled(true);
                //移出单  填写  ---移出填写多少件   移入多少件   （带上 件包本  产品不一样 计算的总数是不一样的）
                //ShowData2(false);
                ShowDataEnabled2(true);
                //移入单  填写
            }
            else if(oper==2) {
                //混装","
                //ShowData(false);
                //ShowData(true);
                ShowDataEnabled(true);
                //移出单  填写  ---移出填写多少件   移入多少件   （带上 件包本  产品不一样 计算的总数是不一样的）
                ShowData2(true);
                ShowDataEnabled2(true);
                //移入单  填写
            }
        }
        if(mErrType.getSelectedItem().toString().equals("破损"))
        {
            //if(warehousingInDistributionQualityInput1==null && warehousingInDistributionFullTask21==null)
            if(oper == 0)
            {
                warehousingInDistributionQualityInput1 = warehousingInDistributionQualityInput; //移出单
                warehousingInDistributionFullTask21 = warehousingInDistributionFullTask2;
            }
            if(oper==-1)
            {
                //混装","
                ShowData(true);
                ShowDataEnabled(true);
                //移出单  填写  ---移出填写多少件   移入多少件   （带上 件包本  产品不一样 计算的总数是不一样的）
                ShowData2(true);
                ShowDataEnabled2(true);
                //移入单  填写
                rFromNo.setText("销售部残书区");
                rFromNo.clearFocus();
            }
            if(oper==0)
            {
                //破损","
                ShowData(true);
                ShowDataEnabled(true);
                //移出单  填写  ---移出什么  移入就是什么  产品带过去
                ShowData2(true);
                ShowDataEnabled2(true);
                rFromNo.setText("销售部残书区");
                rFromNo.clearFocus();
            }
            else if(oper==1)
            {
                //破损","
                ShowData(true);
                ShowDataEnabled(true);
                //移出单  填写  ---移出什么  移入就是什么  产品带过去
                //ShowData2(false);
                ShowDataEnabled2(true);
                rFromNo.setText("销售部残书区");
                rFromNo.clearFocus();
            }
            else if(oper==2) {
                //破损","
                //ShowData(false);
                //ShowData(true);
                ShowDataEnabled(true);
                //移出单  填写  ---移出什么  移入就是什么  产品带过去
                ShowData2(true);
                ShowDataEnabled2(true);
                rFromNo.setText("销售部残书区");
                rFromNo.clearFocus();
            }
            //移入单  填写
            mRl3.setVisibility(View.VISIBLE);
        }else
        {
            mRl3.setVisibility(View.GONE);
        }
        if(mErrType.getSelectedItem().toString().equals("调账"))
        {
            //if(warehousingInDistributionQualityInput1==null && warehousingInDistributionFullTask21==null)
            if(oper == 0)
            {
                warehousingInDistributionQualityInput1 = warehousingInDistributionQualityInput; //移出单
                warehousingInDistributionFullTask21 = warehousingInDistributionFullTask2;
            }
            if(oper==-1)
            {
                //混装","
                ShowData(true);
                ShowDataEnabled(true);
                //移出单  填写  ---移出填写多少件   移入多少件   （带上 件包本  产品不一样 计算的总数是不一样的）
                ShowData2(true);
                ShowDataEnabled2(true);
                //移入单  填写
            }
            if(oper==0)
            {
                //调账"
                //移出单  填写
                ShowData(true);
                ShowDataEnabled(true);
                //移入单  填写
                ShowData2(true);
                ShowDataEnabled2(true);
            }
            else if(oper==1)
            {
                //调账"
                //移出单  填写
                ShowData(true);
                ShowDataEnabled(true);
                //移入单  填写
                //ShowData2(false);
                ShowDataEnabled2(true);
            }
            else if(oper==2) {
                //调账"
                //移出单  填写
                //ShowData(false);
                //ShowData(true);
                ShowDataEnabled(true);
                //移入单  填写
                ShowData2(true);
                ShowDataEnabled2(true);
            }
        }

        if(isUpdate!=0)
        {
            if(itemModel.getE_errType().equals(mErrType.getSelectedItem().toString()))
            {
                if(oper==-1)
                {
                    for(int i = 0;i<CSIMSApplication.getAppContext().getPerson().getData().size();i++)
                    {
                        if(CSIMSApplication.getAppContext().getPerson().getData().get(i).getO_id()!=null)
                        {
                            if(CSIMSApplication.getAppContext().getPerson().getData().get(i).getO_id().equals(itemModel.getE_errMember()))
                            {
                                mErrMember.setSelection(i);
                                break;
                            }
                        }
                    }

                    cFromNo.setText(String.valueOf(itemModel.getE_output_wareHouseNo()));
                    cNbox_num.setText(String.valueOf(itemModel.getE_output_nbox_num()));
                    cNpack_num.setText(String.valueOf(itemModel.getE_output_npack_num()));
                    cNbook_num.setText(String.valueOf(itemModel.getE_output_nbook_num()));
                    cPcount.setText(String.valueOf(itemModel.getE_output_total()));

                    rFromNo.setText(String.valueOf(itemModel.getE_input_wareHouseNo()));
                    rNbox_num.setText(String.valueOf(itemModel.getE_input_nbox_num()));
                    rNpack_num.setText(String.valueOf(itemModel.getE_input_npack_num()));
                    rNbook_num.setText(String.valueOf(itemModel.getE_input_nbook_num()));
                    rPcount.setText(String.valueOf(itemModel.getE_input_total()));
                }
                else if(oper==1)
                {
                }
                else if(oper==2) {
//                    cFromNo.setText(String.valueOf(itemModel.getE_output_wareHouseNo()));
//                    cNbox_num.setText(String.valueOf(itemModel.getE_output_nbox_num()));
//                    cNpack_num.setText(String.valueOf(itemModel.getE_output_npack_num()));
//                    cNbook_num.setText(String.valueOf(itemModel.getE_output_nbook_num()));
//                    cPcount.setText(String.valueOf(itemModel.getE_output_total()));
//
//                    rFromNo.setText(String.valueOf(itemModel.getE_input_wareHouseNo()));
//                    rNbox_num.setText(String.valueOf(itemModel.getE_input_nbox_num()));
//                    rNpack_num.setText(String.valueOf(itemModel.getE_input_npack_num()));
//                    rNbook_num.setText(String.valueOf(itemModel.getE_input_nbook_num()));
//                    rPcount.setText(String.valueOf(itemModel.getE_input_total()));
                }
            }
        }
        oper = 0;
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

    private void DataBind(boolean isLoading)//提交数据
    {
        if(mErrType.getSelectedItem().toString().equals("多书"))
        {
            if(mFromNo.getText().toString().trim().length()==0){
                ToastUtil.show(this,"库位号/库区不能为空！！！");
                return;
            }else  if(rProdNo.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移入单产品编号不能为空！！！");
                return;
            }else if(rFromNo.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移入单库位号/库区不能为空！！！");
                return;
            }else  if(rPcount.getText().toString().trim().length()==0) {
                ToastUtil.show(this, "移入单数量不能为空！！！");
                return;
            }
        }
        else if(mErrType.getSelectedItem().toString().equals("少书"))
        {
            if(mFromNo.getText().toString().trim().length()==0) {
                ToastUtil.show(this, "库位号/库区不能为空！！！");
                return;
            }else  if(cProdNo.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移出单产品编号不能为空！！！");
                return;
            }else if(cFromNo.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移出单库位号/库区不能为空！！！");
                return;
            }else  if(cPcount.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移出单数量不能为空！！！");
                return;
            }
        }
        else if(mErrType.getSelectedItem().toString().equals("混装") || mErrType.getSelectedItem().toString().equals("破损") | mErrType.getSelectedItem().toString().equals("调账"))
        {
            if(mFromNo.getText().toString().trim().length()==0) {
                ToastUtil.show(this, "库位号/库区不能为空！！！");
                return;
            }else  if(cProdNo.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移出单产品编号不能为空！！！");
                return;
            }else if(cFromNo.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移出单库位号/库区不能为空！！！");
                return;
            }else  if(cPcount.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移出单数量不能为空！！！");
                return;
            }

            if(rProdNo.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移入单产品编号不能为空！！！");
                return;
            }else if(rFromNo.getText().toString().trim().length()==0){
                ToastUtil.show(this,"移入单库位号/库区不能为空！！！");
                return;
            }else  if(rPcount.getText().toString().trim().length()==0) {
                ToastUtil.show(this, "移入单数量不能为空！！！");
                return;
            }

            if(mErrType.getSelectedItem().toString().equals("破损") ) {
                if(mErrMember.getSelectedItem().toString().contains("选择")) {
                    ToastUtil.show(this, "请选择过失人！！！");
                    return;
                }
            }

        }


        String url = "";
        if(isUpdate==0)
            url = AppConstant.OutofSpaceForkliftError();
        else
            url = AppConstant.OutofSpaceForkliftError3();
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //mFromNo
        SENDSMSRequest.add("e_wareHouseNo",mFromNo.getText().toString());
        //mFromArea
        SENDSMSRequest.add("e_wareArea",mFromArea.getText().toString());
        //mErrType
        SENDSMSRequest.add("e_errType",mErrType.getSelectedItem().toString());
        //mErrMember
        if(mErrType.getSelectedItem().toString().equals("破损")) {
            SENDSMSRequest.add("e_errMember", O_ID);
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
        SENDSMSRequest.add("e_input_wareHouseNo",rFromNo.getText().toString());
        //if(rFromNo.getSelectedItem().toString().equals("产品配货区"))
        //{
        //    SENDSMSRequest.add("e_input_wareHouseNo", rFromNo2.getText().toString());
        //}else {
        //    SENDSMSRequest.add("e_input_wareHouseNo", rFromNo.getSelectedItem().toString());
        //}
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
        SENDSMSRequest.add("e_id",itemModel == null ? 0 : itemModel.getE_id() );//任务表主键
        SENDSMSRequest.add("e_sourceType",sourceType);//任务表主键

        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }
    //过失人
//    private void DataBind2(boolean isLoading)
//    {
//        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.BatchOffManagerManualAdd2(), RequestMethod.POST);
//        SENDSMSRequest.add("type","过失人");
//        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
//        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
//    }
    //头部查询
    private void DataBind3(boolean isLoading) //根据库位号获取产品信息
    {
        //if(!mWaitDialog2.isShowing())
        //    mWaitDialog2.show();
        handler.sendEmptyMessage(1);
        if(!mFromNo.getText().toString().trim().equals(""))
        {
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.OutofSpaceForkliftError4(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("wh_wareHouseNo", mFromNo.getText().toString());
            CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
        }else
        {
            warehousingInDistributionFullTask2 = null;
            warehousingInDistributionQualityInput = null;
            DataBind5(false);
        }
    }
    private void DataBind4(boolean isLoading) //移除单 根据产品编号获取产品信息
    {
        if(warehousingInDistributionFullTask2==null)
        {
            warehousingInDistributionQualityInput = null;
            DataBind5(false);
        }else if(warehousingInDistributionFullTask2.getData()==null)
        {
            warehousingInDistributionQualityInput = null;
            DataBind5(false);
        }else if(warehousingInDistributionFullTask2.getData().getWh_prodNo()==null)
        {
            warehousingInDistributionQualityInput = null;
            DataBind5(false);
        }
        else if(!warehousingInDistributionFullTask2.getData().getWh_prodNo().trim().equals(""))
        {
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo",warehousingInDistributionFullTask2.getData().getWh_prodNo());
            CallServer.getInstance().add(this, 1003, SENDSMSRequest, this, false, isLoading);
        }else
        {
            warehousingInDistributionQualityInput = null;
            DataBind5(false);
        }
    }
    //移出单查询
    private void DataBind5(boolean isLoading) //移入单 根据产品编号获取产品信息
    {
        if(!cProdNo.getText().toString().trim().equals(""))
        {
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo",cProdNo.getText().toString());
            CallServer.getInstance().add(this, 1004, SENDSMSRequest, this, false, isLoading);
        }else {
            warehousingInDistributionQualityInput1 = null;
            DataBind6(false);
        }
    }
    private void DataBind6(boolean isLoading) //根据库位号获取产品信息
    {
        if(!cProdNo.getText().toString().trim().equals("")) {
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.OutofSpaceForkliftError2(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("q_prodNo", cProdNo.getText().toString());
            CallServer.getInstance().add(this, 1005, SENDSMSRequest, this, false, isLoading);
        }else {
            warehousingInDistributionFullTask21 = null;
            DataBind7(false);
        }
    }
    //移入单查询
    private void DataBind7(boolean isLoading) //移入单 根据产品编号获取产品信息
    {
        if(!rProdNo.getText().toString().trim().equals(""))
        {
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo",rProdNo.getText().toString());
            CallServer.getInstance().add(this, 1006, SENDSMSRequest, this, false, isLoading);
        }else {
            warehousingInDistributionQualityInput2 = null;
            DataBind8(false);
        }

    }
    private void DataBind8(boolean isLoading) //根据库位号获取产品信息
    {
        if(!rProdNo.getText().toString().trim().equals(""))
        {
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.OutofSpaceForkliftError2(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("q_prodNo", rProdNo.getText().toString());
            CallServer.getInstance().add(this, 1007, SENDSMSRequest, this, false, isLoading);
        }else {
            mErrType();
            warehousingInDistributionFullTask22 = null;
            handler.sendEmptyMessage(0);
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
            case 1001://过失人
//                batchOffManagerManualAdd2 = gson.fromJson(response.get(), BatchOffManagerManualAdd2.class);
//                if (batchOffManagerManualAdd2.getResult() == 1) {
//                    ArrayAdapter<BatchOffManagerManualAdd2.Bean> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, batchOffManagerManualAdd2.getData());
//                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    mErrMember.setAdapter(spinnerAdapter);
//                    mErrMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            //O_ID = batchOffManagerManualAdd2.getData().get(position).getO_id();
//                            O_ID = ((BatchOffManagerManualAdd2.Bean) mErrMember.getSelectedItem()).getO_id();
//                        }
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//                        }
//                    });
//                }else
//                    ToastUtil.show(this,batchOffManagerManualAdd2.getMsg());
//                break;
            case 1002:
                warehousingInDistributionFullTask2 = gson.fromJson(response.get(), WarehousingInDistributionFullTask2.class);
                DataBind4(false);
                break;
            case 1003:
                warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                DataBind5(false);
                break;
            case 1004:
                warehousingInDistributionQualityInput1 = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                DataBind6(false);
                break;
            case 1005:
                warehousingInDistributionFullTask21 = gson.fromJson(response.get(), WarehousingInDistributionFullTask2.class);
                DataBind7(false);
                break;
            case 1006:
                warehousingInDistributionQualityInput2 = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                DataBind8(false);
                break;
            case 1007:
                warehousingInDistributionFullTask22 = gson.fromJson(response.get(), WarehousingInDistributionFullTask2.class);
                mErrType();
                handler.sendEmptyMessage(0);
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

    public void ShowData(boolean b)
    {
        if(b && warehousingInDistributionFullTask21 != null)
        {
            cProdNo.setText(warehousingInDistributionFullTask21.getData().getWh_prodNo());
            cProdNo.clearFocus();
            cProdName.setText(warehousingInDistributionFullTask21.getData().getWh_prodName());
            cFromNo.setText(warehousingInDistributionFullTask21.getData().getWh_wareHouseNo());
            cFromNo2.setText(warehousingInDistributionFullTask21.getData().getWh_wareHouseNo());
            cNbox_num.setText("");
            cNpack_num.setText("");
            cNbook_num.setText("");
            cPcount.setText("");
        }else
        {
            cProdNo.setText("");
            cProdNo.clearFocus();
            cProdName.setText("");
            cFromNo.setText("");
            cFromNo2.setText("");
            cNbox_num.setText("");
            cNpack_num.setText("");
            cNbook_num.setText("");
            cPcount.setText("");
        }
    }

    public void ShowDataEnabled(boolean b)
    {
        cProdNo.setEnabled(b);
        cProdNo.clearFocus();
        cProdName.setEnabled(b);
        cFromNo.setEnabled(b);
        cFromNo2.setEnabled(b);
        cNbox_num.setEnabled(b);
        cNpack_num.setEnabled(b);
        cNbook_num.setEnabled(b);
        cPcount.setEnabled(b);
    }

    public void ShowData2(boolean b)
    {
//        rProdNo.setText(_rProdNo);
//        rProdName.setText(_rProdName);
//
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item, _rFromNo);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        rFromNo.setAdapter(spinnerAdapter);
//
//        rFromNo2.setText(_rFromNo2);
//        rNbox_num.setText(_rNbox_num);
//        rNpack_num.setText(_rNpack_num);
//        rNbook_num.setText(_rNbook_num);
//        rPcount.setText(_rPcount);
        if(b && warehousingInDistributionFullTask22 != null)
        {
            rProdNo.setText(warehousingInDistributionFullTask22.getData().getWh_prodNo());
            rProdNo.clearFocus();
            rProdName.setText(warehousingInDistributionFullTask22.getData().getWh_prodName());
//            String[]  spinnerItems = new String[]{ "产品纠错区", "产品配货区", "销售部残书区"};
//            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item, spinnerItems);
//            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            rFromNo.setAdapter(spinnerAdapter);
//            if(mErrType.getSelectedItem().toString().equals("多书"))
//            {
//                rFromNo.setSelection(1,true);
//            }
//            if(mErrType.getSelectedItem().toString().equals("少书"))
//            {
//            }
//            if(mErrType.getSelectedItem().toString().equals("混装"))
//            {
//                rFromNo.setSelection(1,true);
//            }
//            if(mErrType.getSelectedItem().toString().equals("破损"))
//            {
//                mRl3.setVisibility(View.VISIBLE);
//                rFromNo.setSelection(2,true);
//            }else
//            {
//                mRl3.setVisibility(View.GONE);
//            }
//            if(mErrType.getSelectedItem().toString().equals("调账"))
//            {
//                rFromNo.setSelection(0,true);
//            }
            ///ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item, new String[]{warehousingInDistributionFullTask22.getData().getWh_wareArea()});
            //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //rFromNo.setAdapter(spinnerAdapter);
            rFromNo.setText(warehousingInDistributionFullTask22.getData().getWh_wareHouseNo());
            rProdNo.clearFocus();
            rFromNo2.setText(warehousingInDistributionFullTask22.getData().getWh_wareHouseNo());
            rNbox_num.setText("");
            rNpack_num.setText("");
            rNbook_num.setText("");
            rPcount.setText("");
        }else
        {
            rProdNo.setText("");
            rProdNo.clearFocus();
            rProdName.setText("");

            //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item, new String[]{""});
            //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            rFromNo.setText(""); //.setAdapter(spinnerAdapter);
            rFromNo2.setText("");
            rNbox_num.setText("");
            rNpack_num.setText("");
            rNbook_num.setText("");
            rPcount.setText("");
        }

    }

    public void ShowDataEnabled2(boolean b)
    {
        rProdNo.setEnabled(b);
        rFromNo.setEnabled(b);
        rProdName.setEnabled(b);
        rNbox_num.setEnabled(b);
        rNpack_num.setEnabled(b);
        rNbook_num.setEnabled(b);
        rPcount.setEnabled(b);
    }

    public void TextChanged()
    {
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
        //if(cProdName.getText().toString().length() > 0 && cNbox_num.getText().toString().length()>0 && cNpack_num.getText().toString().length()>0 && cNbook_num.getText().toString().length()>0)
        if(cProdName.getText().toString().length() > 0 )
        {
            if(warehousingInDistributionQualityInput1!=null)
            {
                if(warehousingInDistributionQualityInput1.getData()!=null)
                {
                    //cNbox_num.setText(cNbox_num.getText().toString().trim().equals("") ? "0" : cNbox_num.getText().toString().trim());
                    //cNpack_num.setText(cNpack_num.getText().toString().trim().equals("") ? "0" : cNpack_num.getText().toString().trim());
                    //cNbook_num.setText(cNbook_num.getText().toString().trim().equals("") ? "0" : cNbook_num.getText().toString().trim());
                    int cNbox = Integer.valueOf(cNbox_num.getText().toString().trim().equals("") ? "0" : cNbox_num.getText().toString().trim()) * warehousingInDistributionQualityInput1.getData().nBoxNum;
                    int cNpack = Integer.valueOf(cNpack_num.getText().toString().trim().equals("") ? "0" : cNpack_num.getText().toString().trim()) * warehousingInDistributionQualityInput1.getData().nPackNum;
                    int cNbook = Integer.valueOf(cNbook_num.getText().toString().trim().equals("") ? "0" : cNbook_num.getText().toString().trim()) ;
                    cPcount.setText(String.valueOf(cNbox +cNpack+cNbook));
                }
            }
        }
    }

    public void SumR()
    {
        //if(rProdName.getText().toString().length() > 0 && rNbox_num.getText().toString().length()>0 && rNpack_num.getText().toString().length()>0 && rNbook_num.getText().toString().length()>0)
        if(rProdName.getText().toString().length() > 0)
        {
            if(warehousingInDistributionQualityInput2!=null)
            {
                if(warehousingInDistributionQualityInput2.getData()!=null)
                {
                    //rNbox_num.setText(rNbox_num.getText().toString().trim().equals("") ? "0" : rNbox_num.getText().toString().trim());
                    //rNpack_num.setText(rNpack_num.getText().toString().trim().equals("") ? "0" : rNpack_num.getText().toString().trim());
                    //rNbook_num.setText(rNbook_num.getText().toString().trim().equals("") ? "0" : rNbook_num.getText().toString().trim());
                    int rNbox = Integer.valueOf(rNbox_num.getText().toString().trim().equals("") ? "0" : rNbox_num.getText().toString().trim()) * warehousingInDistributionQualityInput2.getData().nBoxNum;
                    int rNpack = Integer.valueOf(rNpack_num.getText().toString().trim().equals("") ? "0" : rNpack_num.getText().toString().trim()) * warehousingInDistributionQualityInput2.getData().nPackNum;
                    int rNbook = Integer.valueOf(rNbook_num.getText().toString().trim().equals("") ? "0" : rNbook_num.getText().toString().trim()) ;
                    rPcount.setText(String.valueOf(rNbox +rNpack+rNbook));
                }
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case 0:
                    if(mWaitDialog2 ==null)
                    {
                        mWaitDialog2 = new WaitDialog(WarehousingErrorForkliftDistributionAdd2Activity.this);
                        mWaitDialog2.setCancelable(false);
                        mWaitDialog2.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                mWaitDialog2.cancel();
                            }
                        });
                    }
                    if(mWaitDialog2.isShowing())
                        mWaitDialog2.dismiss();
                    break;
                case 1:
                    if(mWaitDialog2 ==null)
                    {
                        mWaitDialog2 = new WaitDialog(WarehousingErrorForkliftDistributionAdd2Activity.this);
                        mWaitDialog2.setCancelable(false);
                        mWaitDialog2.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                mWaitDialog2.cancel();
                            }
                        });
                    }
                    if(!mWaitDialog2.isShowing())
                        mWaitDialog2.show();
                    break;
            }
        }
    };


}