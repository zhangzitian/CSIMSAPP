package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.WarehousingErrorManagerList;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class WarehousingErrorForkliftDistributionActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    WarehousingErrorManagerList.Bean itemModel = null;

    private TextView mFromNo;//库位号
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

    private String sFromNo;//库位号
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
        setContentView(R.layout.activity_warehousing_error_forklift_distribution);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("纠错调整单");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mFromNo = (TextView) findViewById(R.id.mFromNo);
        mErrType = (Spinner) findViewById(R.id.mErrType);

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemModel = (WarehousingErrorManagerList.Bean ) bundle.get("itemModel");
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
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                OutofSpaceForkliftList outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    if (outofSpaceForkliftList.getData() != null) {

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