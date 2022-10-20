package com.zitian.csims.ui.activity;

import android.content.DialogInterface;
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
import com.zitian.csims.model.WarehousingErrorManagerList;
import com.zitian.csims.model.WarehousingInDistributionFullTask;
import com.zitian.csims.model.WarehousingInDistributionFullTask2;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle2;
import com.zitian.csims.util.ToastUtil;


public class WarehousingInDistributionFullTaskActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;

    private Button mbt01; //领取
    private Button mbt02; //完成
    private Button mbt03; //合盘
    private Button mbt04; //手动
    private Button mbt05; //暂停

    private TextView mt_prodNo;
    private TextView mt_prodName;
    private TextView mcPlateName;
    private TextView mt_fromNo;

    private LinearLayout line1; //整个任务
    private RelativeLayout all_no_data; //没有数据
    private TextView tipMsg; //提示的文字


    WarehousingInDistributionFullTask.Bean bean = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_in_distribution_full_task);
        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("【入库】-任务领取");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        line1= (LinearLayout) findViewById(R.id.line1);
        line1.setVisibility(View.GONE);

        all_no_data = (RelativeLayout) findViewById(R.id.all_no_data);
        tipMsg = (TextView) findViewById(R.id.tipMsg);

        mbt01 = (Button) findViewById(R.id.bt01);
        mbt02 = (Button) findViewById(R.id.bt02);
        mbt03 = (Button) findViewById(R.id.bt03);
        mbt04 = (Button) findViewById(R.id.bt04);
        mbt05 = (Button) findViewById(R.id.bt05);

        mt_prodNo = (TextView) findViewById(R.id.t_prodNo);
        mt_prodName = (TextView) findViewById(R.id.t_prodName);
        mcPlateName = (TextView) findViewById(R.id.cPlateName);
        mt_fromNo= (TextView) findViewById(R.id.mt_fromNo);
    }

    private void setViewListener() {
        icon_back.setOnClickListener(this);
        mbt01.setOnClickListener(this);
        mbt02.setOnClickListener(this);
        mbt03.setOnClickListener(this);
        mbt04.setOnClickListener(this);
        mbt05.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                DataBind(true);
                mbt01.setEnabled(false);
                break;
            case R.id.bt02:
                mbt01.setEnabled(true);
                mbt02.setEnabled(false);
                DataBind2(true);
                break;
            case R.id.bt03:
                DataBind3(true);
                break;
            case R.id.bt04:
                CustomDialogStyle2.Builder builder = new CustomDialogStyle2.Builder(WarehousingInDistributionFullTaskActivity.this);
                builder.setTitle("提示")
                        .setMessage("您确定要手动上架吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositionButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mbt01.setEnabled(true);
                                mbt03.setEnabled(false);
                                mbt04.setEnabled(false);
                                mbt05.setEnabled(false);
                                line1.setVisibility(View.VISIBLE);
                                //WarehousingInDistributionFullTask.Bean bean = null;
                                DataBind6(true);
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.bt05:
                mbt01.setEnabled(true);
                mbt03.setEnabled(false);
                mbt04.setEnabled(false);
                mbt05.setEnabled(false);
                line1.setVisibility(View.VISIBLE);
                DataBind5(true);
                break;
        }
    }

    //领取
    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionFullTask(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("t_team", CSIMSApplication.getAppContext().getUser().getGroupname());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }
    //完成
    private void DataBind2(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionFullTask2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        //SENDSMSRequest.add("t_ID",bean.getT_ID());
        //SENDSMSRequest.add("userName",CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("t_ID", bean.getT_ID());//任务ID
        SENDSMSRequest.add("userName",CSIMSApplication.getAppContext().getUser().getO_id());//用户名
        SENDSMSRequest.add("t_toNo",bean.getT_toNo());
        SENDSMSRequest.add("t_toArea",bean.getT_toArea());
        SENDSMSRequest.add("t_prodNo",bean.getT_prodNo());
        SENDSMSRequest.add("t_prodName",bean.getT_prodName());
        SENDSMSRequest.add("t_trayType",bean.getT_trayType());
        SENDSMSRequest.add("t_count",bean.getT_count());
        SENDSMSRequest.add("t_nbox_num",bean.getT_nbox_num());
        SENDSMSRequest.add("t_npack_num",bean.getT_npack_num());
        SENDSMSRequest.add("t_nbook_num",bean.getT_nbook_num());
        SENDSMSRequest.add("t_exception",bean.getT_exception());//来源库位表ID
        SENDSMSRequest.add("t_exception2",bean.getT_exception2());//产品系列号
        SENDSMSRequest.add("t_exception3",bean.getT_exception3());//产品系列号
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    //合盘
    private void DataBind3(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionFullTask3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID",bean.getT_ID());//任务ID
        SENDSMSRequest.add("userName",CSIMSApplication.getAppContext().getUser().getO_id());//用户名
        SENDSMSRequest.add("t_toNo",bean.getT_toNo());//配货区库位号
        SENDSMSRequest.add("t_count", bean.getT_count());//总数量
        SENDSMSRequest.add("t_nbox_num",bean.getT_nbox_num());//件
        SENDSMSRequest.add("t_npack_num",bean.getT_npack_num());//包
        SENDSMSRequest.add("t_nbook_num", bean.getT_nbook_num());//册
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    //暂停
    private void DataBind5(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionFullTask4(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID", bean.getT_ID());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1004, SENDSMSRequest, this, false, isLoading);
    }

    //手动上架 获取库位号
    private void DataBind6(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionFullTask5(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1005, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                WarehousingInDistributionFullTask outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInDistributionFullTask.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    if (outofSpaceForkliftList.getData() != null) {
                        line1.setVisibility(View.VISIBLE);
                        all_no_data.setVisibility(View.GONE);
                        bean = outofSpaceForkliftList.getData();
                        mt_prodNo.setText(bean.getT_prodNo());
                        mt_prodName.setText(bean.getT_prodName());
                        mcPlateName.setText(bean.getT_trayType());
                        mt_fromNo.setText(String.valueOf(bean.getT_toNo()));
                        if(bean.getT_trayType().equals("不满盘"))
                        {
                            mbt02.setVisibility(View.GONE);
                            //mbt03.setEnabled(true);
                            //mbt03.setVisibility(View.VISIBLE);
                            BtnTime(mbt03,"bt03");

                            mbt04.setEnabled(false);
                            mbt04.setVisibility(View.VISIBLE);
                            mbt05.setEnabled(true);
                            mbt05.setVisibility(View.VISIBLE);
                        }else
                        {
                            //mbt02.setEnabled(true);
                            //mbt02.setVisibility(View.VISIBLE);
                            BtnTime(mbt02,"bt02");

                            mbt03.setVisibility(View.GONE);
                            mbt04.setVisibility(View.GONE);
                            mbt05.setVisibility(View.GONE);
                        }
                    }
                }else
                {
                    //Alert("提示",outofSpaceForkliftList.getMsg(),"知道了");
                    //ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                    line1.setVisibility(View.GONE);
                    all_no_data.setVisibility(View.VISIBLE);
                    tipMsg.setText(outofSpaceForkliftList.getMsg());
                }
                break;
            case 1001:
                outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInDistributionFullTask.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
            case 1002:
                outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInDistributionFullTask.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    mbt01.setEnabled(true);
                    mbt03.setEnabled(false);
                    mbt04.setEnabled(true);
                    mbt05.setEnabled(false);
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
            case 1004:
                outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInDistributionFullTask.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
            case 1005:
                WarehousingInDistributionFullTask2 warehousingInDistributionFullTask2 = gson.fromJson(response.get(), WarehousingInDistributionFullTask2.class);
                if (warehousingInDistributionFullTask2.getResult() == 1) {
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("itemModel",bean);
                    mBundle.putSerializable("t_toNo",warehousingInDistributionFullTask2.getData().getWh_wareHouseNo());
                    mBundle.putSerializable("t_toArea",warehousingInDistributionFullTask2.getData().getWh_wareArea());
                    openActivity(WarehousingInDistributionManualActivity.class,mBundle);
                }else
                {
                    ToastUtil.show(this, warehousingInDistributionFullTask2.getMsg());
                }
                break;
            default:
                outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInDistributionFullTask.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

    private boolean tag = true;
    private int i = 30;

    public void BtnTime(Button btn,String id) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        if (WarehousingInDistributionFullTaskActivity.this == null) {
                            break;
                        }
                        WarehousingInDistributionFullTaskActivity.this
                                .runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        btn.setVisibility(View.VISIBLE);
                                        btn.setText("等待("  + i + "s)");
                                        btn.setEnabled(false);
                                    }
                                });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    tag = false;
                }
                i = 30;
                tag = true;
                if (WarehousingInDistributionFullTaskActivity.this != null) {
                    WarehousingInDistributionFullTaskActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(id.equals("bt02"))
                            {
                                btn.setText("完成");
                                btn.setEnabled(true);
                                btn.setVisibility(View.VISIBLE);
                            }else  if(id.equals("bt03"))
                            {
                                btn.setText("合盘完成");
                                btn.setEnabled(true);
                                btn.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            };
        };
        thread.start();
    }

}