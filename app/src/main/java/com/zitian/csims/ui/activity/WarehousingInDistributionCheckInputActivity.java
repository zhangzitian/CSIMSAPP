package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
import com.zitian.csims.model.WarehousingInDistributionCheckInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;



public class WarehousingInDistributionCheckInputActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;

    private TextView m_t_prodNo;
    private TextView m_t_prodName;
    private TextView m_cPlateName;
    private TextView m_t_count;
    private TextView m_nPlateNum;
    private TextView m_t_nbox_num;
    private TextView m_t_npack_num;
    private TextView m_t_nbook_num;
    private TextView m_t_factory;
    private Button bt01;
    private Button bt02;

    private WarehousingInDistributionCheckInput.Bean bean = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_in_distribution_check_input);

        initView();
        setViewListener();
        //DataBind(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("【入库】-核对录入");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        m_t_prodNo = (TextView) findViewById(R.id.t_prodNo);
        m_t_prodName = (TextView) findViewById(R.id.t_prodName);
        m_cPlateName = (TextView) findViewById(R.id.cPlateName);
        m_t_count = (TextView) findViewById(R.id.t_count);
        m_nPlateNum = (TextView) findViewById(R.id.nPlateNum);
        m_t_nbox_num = (TextView) findViewById(R.id.t_nbox_num);
        m_t_npack_num = (TextView) findViewById(R.id.t_npack_num);
        m_t_nbook_num = (TextView) findViewById(R.id.t_nbook_num);
        m_t_factory = (TextView) findViewById(R.id.t_factory);

        bt01 = (Button) findViewById(R.id.bt01);
        bt02 = (Button) findViewById(R.id.bt02);

        bean =  (WarehousingInDistributionCheckInput.Bean)getIntent().getSerializableExtra("itemModel");
        m_t_prodNo.setText(bean.q_prodNo);
        m_t_prodName.setText(bean.q_prodName);
        m_cPlateName.setText(bean.q_rule);
        m_t_count.setText(String.valueOf(bean.q_inputNum));
        m_nPlateNum.setText(String.valueOf(bean.q_plateNum));
        m_t_nbox_num.setText(String.valueOf(bean.q_pieces));
        m_t_npack_num.setText(String.valueOf(bean.q_package));
        m_t_nbook_num.setText(String.valueOf(bean.q_book));
        m_t_factory.setText(bean.q_factory);
    }

    //private void DataBind(boolean isLoading) {
    //    String q_ID = getIntent().getStringExtra("q_ID");
    //    Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionCheckInput, RequestMethod.POST);
    //    SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
    //    SENDSMSRequest.add("q_ID", q_ID);
    //    CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    //}

    private void DataBind2(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionCheckInput2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("q_prodNo",bean.q_prodNo);//产品编码
        SENDSMSRequest.add("q_prodName",bean.q_prodName);//产品名称
        SENDSMSRequest.add("q_inputNum",String.valueOf(bean.q_inputNum));//入库数量
        SENDSMSRequest.add("q_plateNum",String.valueOf(bean.q_plateNum));//整盘个数
        SENDSMSRequest.add("q_pieces",String.valueOf(bean.q_pieces));//件
        SENDSMSRequest.add("q_package",String.valueOf(bean.q_package));//包
        SENDSMSRequest.add("q_book",String.valueOf(bean.q_book));//册
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());//录入人员
        SENDSMSRequest.add("groupname",CSIMSApplication.getAppContext().getUser().getGroupname());//组
        SENDSMSRequest.add("q_exception",bean.q_exception);//系列号
        SENDSMSRequest.add("q_exception2",bean.q_exception2);//整盘册数
        SENDSMSRequest.add("q_rule",bean.q_rule);
        SENDSMSRequest.add("q_factory",bean.q_factory);
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    private void setViewListener() {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01://修改
                Intent rIntent = new Intent();
                rIntent.putExtra("update", 0);//1是返回首页 0是修改
                setResult(2, getIntent());
                finish();
                break;
            case R.id.bt02:
                bt02.setEnabled(false);
                DataBind2(true);
                break;

        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
            switch (what) {
//            case 1000:
//                WarehousingInDistributionCheckInput warehousingInDistributionCheckInput = gson.fromJson(response.get(), WarehousingInDistributionCheckInput.class);
//                if (warehousingInDistributionCheckInput.getResult() == 1) {
//                    if (warehousingInDistributionCheckInput.getData() != null) {
//                        bean = warehousingInDistributionCheckInput.getData();
//                        m_t_prodNo.setText(bean.q_prodNo);
//                        m_t_prodName.setText(bean.q_prodName);
//                        m_cPlateName.setText(bean.q_rule);
//                        m_t_count.setText(String.valueOf(bean.q_inputNum));
//                        m_nPlateNum.setText(String.valueOf(bean.q_plateNum));
//                        m_t_nbox_num.setText(String.valueOf(bean.q_pieces));
//                        m_t_npack_num.setText(String.valueOf(bean.q_package));
//                        m_t_nbook_num.setText(String.valueOf(bean.q_book));
//                    } else {
//                    }
//                }
//                break;
                case 1001:
                    bt02.setEnabled(true);
                    WarehousingInDistributionCheckInput warehousingInDistributionCheckInput = gson.fromJson(response.get(), WarehousingInDistributionCheckInput.class);
                    if (warehousingInDistributionCheckInput.getResult() == 1) {
                        ToastUtil.showLong(this, warehousingInDistributionCheckInput.getMsg());
                        //openActivity(WarehousingInDistributionFullTaskActivity.class);
                        Intent intent = new Intent();
                        intent.putExtra("update", 1);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else
                    {
                        Alert("提示",warehousingInDistributionCheckInput.getMsg(),"知道了");
                    }
                    break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

}