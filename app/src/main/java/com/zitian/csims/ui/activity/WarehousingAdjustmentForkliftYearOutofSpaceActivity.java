package com.zitian.csims.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.zitian.csims.model.WarehousingInDistributionFullTask;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class WarehousingAdjustmentForkliftYearOutofSpaceActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;

    private Button mbt01; //领取
    private Button mbt02; //完成

    private TextView mt_prodNo;
    private TextView mt_prodName;
    private TextView mcPlateName;
    private TextView mt_fromNo;
    private TextView mt_toNo;

    private LinearLayout line1; //整个任务
    private RelativeLayout all_no_data; //没有数据
    private TextView tipMsg; //提示的文字

    OutofSpaceForkliftList outofSpaceForkliftList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_adjustment_forklift_year_outof_space);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("叉车司机---调区上架任务");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        line1= (LinearLayout) findViewById(R.id.line1);
        line1.setVisibility(View.GONE);

        all_no_data = (RelativeLayout) findViewById(R.id.all_no_data);
        tipMsg = (TextView) findViewById(R.id.tipMsg);

        mbt01 = (Button) findViewById(R.id.bt01);
        mbt02 = (Button) findViewById(R.id.bt02);

        mt_prodNo = (TextView) findViewById(R.id.t_prodNo);
        mt_prodName = (TextView) findViewById(R.id.t_prodName);
        mcPlateName = (TextView) findViewById(R.id.cPlateName);
        mt_fromNo= (TextView) findViewById(R.id.mt_fromNo);
        mt_fromNo.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG);
        mt_toNo= (TextView) findViewById(R.id.mt_toNo);
        mt_toNo.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG);
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        mbt01.setOnClickListener(this);
        mbt02.setOnClickListener(this);
        mt_fromNo.setOnClickListener(this);
        mt_toNo.setOnClickListener(this);
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
            case R.id.bt02:
                DataBind2(true);
                break;
            case R.id.mt_fromNo:
                List<OutofSpaceForkliftList.Bean> list = outofSpaceForkliftList.getData();
                if(list.size()>0)
                {
                    OutofSpaceForkliftList.Bean itemModel = list.get(0);

                    Bundle mBundle = new Bundle();
                    mBundle.putString("sourceType","库位调整纠错");
                    //mBundle.putStringArray("spinnerItems",new String[]{"库位为空","库位被占用"});;
                    mBundle.putStringArray("spinnerItems",new String[]{"库位为空"});;
                    mBundle.putInt("mType",0);

                    WarehousingErrorForkliftDistributionList.Bean item = new WarehousingErrorForkliftDistributionList.Bean();
                    //"e_id": 79,
                    item.setE_wareHouseNo(itemModel.getT_fromNo()); //"e_wareHouseNo": "产品爆仓区",
                    item.setE_wareArea(itemModel.getT_fromArea()); //"e_wareArea": "",
                    item.setE_errType("库位为空"); //"e_errType": "库位为空",
                    item.setE_errMember(""); //"e_errMember": "",
                    item.setE_output_prodNo(itemModel.getT_prodNo()); //"e_output_prodNo": "T-13-06-01",
                    item.setE_output_prodName(itemModel.getT_prodName()); //"e_output_prodName": "“炫彩童书”小学生课外必读书系 彩图注音版 培养完美女孩的公主童话",
                    item.setE_output_wareHouseNo(itemModel.getT_fromNo()); //"e_output_wareHouseNo": "产品爆仓区",
                    item.setE_output_nbox_num(itemModel.getT_nbox_num()); // "e_output_nbox_num": 39,
                    item.setE_output_npack_num(itemModel.getT_npack_num()); //"e_output_npack_num": 0,
                    item.setE_output_nbook_num(itemModel.getT_nbook_num()); //"e_output_nbook_num": 0,
                    item.setE_output_total(itemModel.getT_count()); //"e_output_total": 3120,
                    item.setE_input_prodNo(itemModel.getT_prodNo()); //"e_input_prodNo": "",
                    item.setE_input_prodName(itemModel.getT_prodName()); //"e_input_prodName": "",
                    item.setE_input_wareHouseNo(itemModel.getT_toNo()); //"e_input_wareHouseNo": "产品纠错区",
                    item.setE_input_nbox_num(1); // "e_input_nbox_num": 0,
                    item.setE_input_npack_num(0); //"e_input_npack_num": 0,
                    item.setE_input_nbook_num(0); //"e_input_nbook_num": 0,
                    item.setE_input_total(0); //"e_input_total": 0,
                    item.setE_state("未审核"); //"e_state": "未审核",
                    item.setE_reviewer("未审核"); //"e_reviewer": "",
                    //item.setE_wareHouseNo(); //"e_createTime": "2021-03-04 21:30:37",
                    //item.setE_wareHouseNo(); //"e_reviewTime": "2021-03-04 21:30:37",
                    item.setE_exception(String.valueOf(itemModel.getT_ID())); //"e_exception": "1656",
                    //item.setE_wareHouseNo(); //"e_exception2": "userName",
                    item.setE_exception3(String.valueOf(itemModel.getT_exception())); //"e_exception3": "4205",
                    //item.setE_wareHouseNo(); //"e_exception4": "2021-03-04 21:30:37"
                    mBundle.putSerializable("itemModel",item);
                    openActivity(WarehousingErrorForkliftDistributionAddActivity.class,mBundle);
                }
                break;
            case R.id.mt_toNo:
                list = outofSpaceForkliftList.getData();
                if(list.size()>0)
                {
                    OutofSpaceForkliftList.Bean itemModel = list.get(0);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("sourceType","库位调整纠错");
                    //mBundle.putStringArray("spinnerItems",new String[]{"库位为空","库位被占用"});;
                    mBundle.putStringArray("spinnerItems",new String[]{"库位被占用"});;
                    mBundle.putInt("mType",0);

                    WarehousingErrorForkliftDistributionList.Bean item = new WarehousingErrorForkliftDistributionList.Bean();
                    //"e_id": 79,
                    item.setE_wareHouseNo(itemModel.getT_toNo()); //"e_wareHouseNo": "产品爆仓区",
                    item.setE_wareArea(itemModel.getT_toArea()); //"e_wareArea": "",
                    item.setE_errType("库位被占用"); //"e_errType": "库位为空",
                    item.setE_errMember(""); //"e_errMember": "",
                    item.setE_output_prodNo(itemModel.getT_prodNo()); //"e_output_prodNo": "T-13-06-01",
                    item.setE_output_prodName(itemModel.getT_prodName()); //"e_output_prodName": "“炫彩童书”小学生课外必读书系 彩图注音版 培养完美女孩的公主童话",
                    item.setE_output_wareHouseNo(itemModel.getT_fromNo()); //"e_output_wareHouseNo": "产品爆仓区",
                    item.setE_output_nbox_num(itemModel.getT_nbox_num()); // "e_output_nbox_num": 39,
                    item.setE_output_npack_num(itemModel.getT_npack_num()); //"e_output_npack_num": 0,
                    item.setE_output_nbook_num(itemModel.getT_nbook_num()); //"e_output_nbook_num": 0,
                    item.setE_output_total(itemModel.getT_count()); //"e_output_total": 3120,
                    item.setE_input_prodNo(itemModel.getT_prodNo()); //"e_input_prodNo": "",
                    item.setE_input_prodName(itemModel.getT_prodName()); //"e_input_prodName": "",
                    item.setE_input_wareHouseNo(itemModel.getT_toNo()); //"e_input_wareHouseNo": "产品纠错区",
                    item.setE_input_nbox_num(1); // "e_input_nbox_num": 0,
                    item.setE_input_npack_num(0); //"e_input_npack_num": 0,
                    item.setE_input_nbook_num(0); //"e_input_nbook_num": 0,
                    item.setE_input_total(0); //"e_input_total": 0,
                    item.setE_state("未审核"); //"e_state": "未审核",
                    item.setE_reviewer("未审核"); //"e_reviewer": "",
                    //item.setE_wareHouseNo(); //"e_createTime": "2021-03-04 21:30:37",
                    //item.setE_wareHouseNo(); //"e_reviewTime": "2021-03-04 21:30:37",
                    item.setE_exception(String.valueOf(itemModel.getT_ID())); //"e_exception": "1656",
                    //item.setE_wareHouseNo(); //"e_exception2": "userName",
                    item.setE_exception3(String.valueOf(itemModel.getT_exception())); //"e_exception3": "4205",
                    //item.setE_wareHouseNo(); //"e_exception4": "2021-03-04 21:30:37"
                    mBundle.putSerializable("itemModel",item);
                    openActivity(WarehousingErrorForkliftDistributionAddActivity.class,mBundle);
                }
                break;
        }
    }

    //领取
    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingAdjustmentForkliftYearOutofSpace(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    //完成
    private void DataBind2(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingAdjustmentForkliftYearOutofSpace2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID", outofSpaceForkliftList.getData().get(0).getT_ID());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    line1.setVisibility(View.VISIBLE);
                    mt_prodNo.setText(outofSpaceForkliftList.getData().get(0).getT_prodNo());
                    mt_prodName.setText(outofSpaceForkliftList.getData().get(0).getT_prodName());
                    mcPlateName.setText(outofSpaceForkliftList.getData().get(0).getT_trayType());
                    mt_fromNo.setText(String.valueOf(outofSpaceForkliftList.getData().get(0).getT_fromNo()));
                    mt_toNo.setText(String.valueOf(outofSpaceForkliftList.getData().get(0).getT_toNo()));
                    mbt01.setEnabled(false);
                    mbt02.setEnabled(true);
                }else
                {
                    //Alert("提示",outofSpaceForkliftList.getMsg(),"知道了");
                    //ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                    line1.setVisibility(View.GONE);
                    all_no_data.setVisibility(View.VISIBLE);
                    tipMsg.setText(outofSpaceForkliftList.getMsg());
                }
                break;
            case 1001://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    mbt01.setEnabled(true);
                    mbt02.setEnabled(false);
                }else
                {
                    //Alert("提示",outofSpaceForkliftList.getMsg(),"知道了");
                    //ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                    line1.setVisibility(View.GONE);
                    all_no_data.setVisibility(View.VISIBLE);
                    tipMsg.setText(outofSpaceForkliftList.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

}