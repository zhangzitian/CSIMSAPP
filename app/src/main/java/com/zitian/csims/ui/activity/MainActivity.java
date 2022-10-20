package com.zitian.csims.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.common.PushSmsService;
import com.zitian.csims.model.AreaNoModels;
import com.zitian.csims.model.BatchOffManagerManualAdd2;
import com.zitian.csims.model.FactoryModels;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.PersonModels;
import com.zitian.csims.model.ProdNoModels;
import com.zitian.csims.model.TaskCount;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.SearchPupItenAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle2;
import com.zitian.csims.util.ToastUtil;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


public class MainActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private TextView TvUsername ;
    private TextView TvRid;
    private Button btnLoginOut;
    private Button btnPassword;
    private Intent pushIntent =null;

    private LinearLayout  LayoutContent;
    private LinearLayout task_count_layout;
    //private int[] mImageIds={R.mipmap.ic_sh,R.mipmap.ic_rk,R.mipmap.ic_ck,R.mipmap.ic_fh,R.mipmap.ic_yd,R.mipmap.ic_pd,R.mipmap.ic_sh,R.mipmap.ic_rk,R.mipmap.ic_ck,R.mipmap.ic_fh,R.mipmap.ic_yd};
    //private String[] mNames={"入库业务","补货业务","爆仓区上架","质量问题下架","改版调库下架","日常纠错盘点","手动业务","库位调整业务","库位纠错业务","盘点业务","基础设置"};

    TextView CountTv1  ;
    TextView CountTv2  ;
    TextView CountTv3  ;
    TextView CountTv4  ;
    TextView CountTv5  ;

    Badge t1 ;
    Badge t2 ;
    Badge t3 ;
    Badge t4 ;
    Badge t5 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setViewListener();
    }

    public void initViews() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("库位管理系统");
        tv_title.setGravity(Gravity.CENTER);
        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.GONE);

        //pushIntent = new Intent(this, PushSmsService.class);
        //startService(pushIntent);

        if(CSIMSApplication.getAppContext().getUser()!=null)
        {
            TvUsername = (TextView) findViewById(R.id.TvUsername);
            TvUsername.setText(CSIMSApplication.getAppContext().getUser().getO_name());

            TvRid = (TextView) findViewById(R.id.TvRid);
            TvRid.setText("角色："+CSIMSApplication.getAppContext().getUser().getZyName());

            btnLoginOut  = (Button) findViewById(R.id.btnLoginOut);
            btnPassword  = (Button) findViewById(R.id.btnPassword);
            LayoutContent = (LinearLayout) findViewById(R.id.LayoutContent);
            task_count_layout  = (LinearLayout) findViewById(R.id.task_count_layout);

             CountTv1 = (TextView) findViewById(R.id.task_count_tv1);
             CountTv2 = (TextView) findViewById(R.id.task_count_tv2);
             CountTv3 = (TextView) findViewById(R.id.task_count_tv3);
             CountTv4 = (TextView) findViewById(R.id.task_count_tv4);
             CountTv5 = (TextView) findViewById(R.id.task_count_tv5);

            t1 = new QBadgeView(getApplicationContext()).bindTarget(CountTv1);
            t2 = new QBadgeView(getApplicationContext()).bindTarget(CountTv2);
            t3 = new QBadgeView(getApplicationContext()).bindTarget(CountTv3);
            t4 = new QBadgeView(getApplicationContext()).bindTarget(CountTv4);
            t5 = new QBadgeView(getApplicationContext()).bindTarget(CountTv5);

            for(int i = 0;i<CSIMSApplication.getAppContext().getRole().size();i++)
            {
                if(CSIMSApplication.getAppContext().getRole().get(i).getS_url().equals("#"))
                {
                    View view = View.inflate(MainActivity.this, R.layout.activity_main_item, null);
                    int oderid = CSIMSApplication.getAppContext().getRole().get(i).getS_oderid();
                    String name = CSIMSApplication.getAppContext().getRole().get(i).getS_name();
                    //View view = View.inflate(MainActivity.this, R.layout.activity_main_item, null);
                    TextView tv = view.findViewById(R.id.tv);
                    tv.setText(name);
                    tv.setTag(oderid);
                    //第一个参数代表横向填充父窗体题，第二个参数纵向为0dp,第三个参数为权重
                    //view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
                    LayoutContent.addView(view);
                }
                else if(!CSIMSApplication.getAppContext().getRole().get(i).getS_url().equals("#") )
                //else if(!CSIMSApplication.getAppContext().getRole().get(i).getS_url().equals("#") && CSIMSApplication.getAppContext().getRole().get(i).getS_rid().contains(CSIMSApplication.getAppContext().getUser().getZy_id()))
                {
                    String base = CSIMSApplication.getAppContext().getRole().get(i).getS_base();
                    String name = CSIMSApplication.getAppContext().getRole().get(i).getS_name();
                    String iconName = CSIMSApplication.getAppContext().getRole().get(i).getS_excetion();
                    String url = CSIMSApplication.getAppContext().getRole().get(i).getS_url();
                    for (int j = 0;j<LayoutContent.getChildCount();j++)
                    {
                        LinearLayout linearLayout = (LinearLayout) LayoutContent.getChildAt(j);
                        TextView tv = linearLayout.findViewById(R.id.tv);
                        if(tv.getTag()!=null)
                        {
                            if(tv.getTag().toString().equals(base))
                            {
                                ImageView iv1 = linearLayout.findViewById(R.id.iv1);
                                TextView tv1 = linearLayout.findViewById(R.id.tv1);

                                ImageView iv2 = linearLayout.findViewById(R.id.iv2);
                                TextView tv2 = linearLayout.findViewById(R.id.tv2);

                                ImageView iv3 = linearLayout.findViewById(R.id.iv3);
                                TextView tv3 = linearLayout.findViewById(R.id.tv3);

                                ImageView iv4 = linearLayout.findViewById(R.id.iv4);
                                TextView tv4 = linearLayout.findViewById(R.id.tv4);

                                ImageView iv5 = linearLayout.findViewById(R.id.iv5);
                                TextView tv5 = linearLayout.findViewById(R.id.tv5);

                                ImageView iv6 = linearLayout.findViewById(R.id.iv6);
                                TextView tv6 = linearLayout.findViewById(R.id.tv6);

                                ImageView iv7 = linearLayout.findViewById(R.id.iv7);
                                TextView tv7 = linearLayout.findViewById(R.id.tv7);

                                ImageView iv8 = linearLayout.findViewById(R.id.iv8);
                                TextView tv8 = linearLayout.findViewById(R.id.tv8);

                                if(tv1.getTag()==null)
                                {
                                    if(!iconName.equals(""))
                                    {
                                        int icon = getResources().getIdentifier(iconName, "mipmap", getPackageName());
                                        iv1.setImageResource(icon);
                                    }
                                    else
                                    {
                                        int icon = getResources().getIdentifier("main_default", "mipmap", getPackageName());
                                        iv1.setImageResource(icon);
                                    }
                                    iv1.setTag(url);
                                    tv1.setText(name);
                                    tv1.setTag(url);
                                    SetTopCick((LinearLayout)iv1.getParent(),url);
                                }
                                else if(tv2.getTag()==null)
                                {
                                    if(!iconName.equals(""))
                                    {
                                        int icon = getResources().getIdentifier(iconName, "mipmap", getPackageName());
                                        iv2.setImageResource(icon);
                                    }
                                    else
                                    {
                                        int icon = getResources().getIdentifier("main_default", "mipmap", getPackageName());
                                        iv2.setImageResource(icon);
                                    }
                                    iv2.setTag(url);
                                    tv2.setText(name);
                                    tv2.setTag(url);
                                    SetTopCick((LinearLayout)iv2.getParent(),url);
                                }
                                else if(tv3.getTag()==null)
                                {
                                    if(!iconName.equals(""))
                                    {
                                        int icon = getResources().getIdentifier(iconName, "mipmap", getPackageName());
                                        iv3.setImageResource(icon);
                                    }
                                    else
                                    {
                                        int icon = getResources().getIdentifier("main_default", "mipmap", getPackageName());
                                        iv3.setImageResource(icon);
                                    }
                                    iv3.setTag(url);
                                    tv3.setText(name);
                                    tv3.setTag(url);
                                    SetTopCick((LinearLayout)iv3.getParent(),url);
                                }
                                else if(tv4.getTag()==null)
                                {
                                    if(!iconName.equals(""))
                                    {
                                        int icon = getResources().getIdentifier(iconName, "mipmap", getPackageName());
                                        iv4.setImageResource(icon);
                                    }
                                    else
                                    {
                                        int icon = getResources().getIdentifier("main_default", "mipmap", getPackageName());
                                        iv4.setImageResource(icon);
                                    }
                                    iv4.setTag(url);
                                    tv4.setText(name);
                                    tv4.setTag(url);
                                    SetTopCick((LinearLayout)iv4.getParent(),url);
                                }
                                else  if(tv5.getTag()==null)
                                {
                                    if(!iconName.equals(""))
                                    {
                                        int icon = getResources().getIdentifier(iconName, "mipmap", getPackageName());
                                        iv5.setImageResource(icon);
                                    }
                                    else
                                    {
                                        int icon = getResources().getIdentifier("main_default", "mipmap", getPackageName());
                                        iv5.setImageResource(icon);
                                    }
                                    LinearLayout lanolin2 = linearLayout.findViewById(R.id.layoutline2);
                                    lanolin2.setVisibility(View.VISIBLE);
                                    iv5.setTag(url);
                                    tv5.setText(name);
                                    tv5.setTag(url);
                                    SetTopCick((LinearLayout)iv5.getParent(),url);
                                }
                                else if(tv6.getTag()==null)
                                {
                                    if(!iconName.equals(""))
                                    {
                                        int icon = getResources().getIdentifier(iconName, "mipmap", getPackageName());
                                        iv6.setImageResource(icon);
                                    }
                                    else
                                    {
                                        int icon = getResources().getIdentifier("main_default", "mipmap", getPackageName());
                                        iv6.setImageResource(icon);
                                    }
                                    iv6.setTag(url);
                                    tv6.setText(name);
                                    tv6.setTag(url);
                                    SetTopCick((LinearLayout)iv6.getParent(),url);
                                }
                                else if(tv7.getTag()==null)
                                {
                                    if(!iconName.equals(""))
                                    {
                                        int icon = getResources().getIdentifier(iconName, "mipmap", getPackageName());
                                        iv7.setImageResource(icon);
                                    }
                                    else
                                    {
                                        int icon = getResources().getIdentifier("main_default", "mipmap", getPackageName());
                                        iv7.setImageResource(icon);
                                    }
                                    iv7.setTag(url);
                                    tv7.setText(name);
                                    tv7.setTag(url);
                                    SetTopCick((LinearLayout)iv7.getParent(),url);
                                }
                                else if(tv8.getTag()==null)
                                {
                                    if(!iconName.equals(""))
                                    {
                                        int icon = getResources().getIdentifier(iconName, "mipmap", getPackageName());
                                        iv8.setImageResource(icon);
                                    }
                                    else
                                    {
                                        int icon = getResources().getIdentifier("main_default", "mipmap", getPackageName());
                                        iv8.setImageResource(icon);
                                    }
                                    iv8.setTag(url);
                                    tv8.setText(name);
                                    tv8.setTag(url);
                                    SetTopCick((LinearLayout)iv8.getParent(),url);
                                }
                            }
                        }
                    }
                }
            }

            LinearLayout  linetask1 = (LinearLayout) findViewById(R.id.linetask1);
            LinearLayout  linetask2 = (LinearLayout) findViewById(R.id.linetask2);
            LinearLayout  linetask3 = (LinearLayout) findViewById(R.id.linetask3);
            LinearLayout  linetask4 = (LinearLayout) findViewById(R.id.linetask4);
            LinearLayout  linetask5 = (LinearLayout) findViewById(R.id.linetask5);

            if(CSIMSApplication.getAppContext().getUser().getZyName() != null)
            {
                if(CSIMSApplication.getAppContext().getUser().getZyName().contains("叉车"))
                {
                    SetTopCick(linetask1,"BatchOffForkliftListActivity");
                    SetTopCick(linetask2,"BookrevisionForkliftListActivity");
                    SetTopCick(linetask3,"ReplenishForkliftListActivity");
                    SetTopCick(linetask4,"WarehousingInForkliftListActivity");
                    SetTopCick(linetask5,"OutofSpaceForkliftListActivity");
                }
            }
        }
        if(CSIMSApplication.getAppContext().getProdNo()==null)
        {
            DataBind1(true);//产品编号1集合
        }
        if(CSIMSApplication.getAppContext().getFactory()==null)
        {
            DataBind2(true);//印厂集合
        }
        if(CSIMSApplication.getAppContext().getPerson()==null)
        {
            DataBind3(true);//过失人集合
        }
    }

    public void SetTopCick(LinearLayout linetask,String url)
    {
        linetask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classStr = "com.zitian.csims.ui.activity." + url;
                Class clazz = null;
                try {
                    clazz = Class.forName(classStr);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this,clazz);
                startActivity(intent);
            }
        });
    }

    private void setViewListener() {
        btnLoginOut.setOnClickListener(this);
        btnPassword.setOnClickListener(this);
        task_count_layout.setOnClickListener(this);
    }

    //@Override
    public void onClick(View v) {
        //super.onClick(v);
        switch (v.getId()){
            //注销
            case R.id.btnLoginOut:
                CustomDialogStyle2.Builder builder = new CustomDialogStyle2.Builder(MainActivity.this);
                builder.setTitle("温馨提示")
                        .setMessage("确定要退出吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositionButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataBind4(true);

                                dialog.dismiss();
                                //Toast.makeText(getActivity(), "点击了确定按钮", Toast.LENGTH_SHORT).show();
                                CSIMSApplication.getAppContext().clearUser();
                                //CSIMSApplication.getAppContext().clearUserToken();
                                ToastUtil.show(MainActivity.this, "退出成功");
                                //openActivity(LoginActivity.class);
                                finish();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.btnPassword:
                openActivity(UpdatePassWordActivity.class);
                break;
            case R.id.task_count_layout:
                DataBind(true);
                break;
        }
    }
    //@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //logcat("获取返回的监听");
        switch (requestCode){
            case 0:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DataBind(boolean isLoading)
    {
        Request<String> SENDSMSRequest  = NoHttp.createStringRequest(AppConstant.GetUnOpenTasks(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //SENDSMSRequest.add("searchKey", str);
        SENDSMSRequest.add("range", 0);
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    //产品编号
    private void DataBind1(boolean isLoading)
    {
        Request<String> SENDSMSRequest  = NoHttp.createStringRequest(AppConstant.AppProdNo(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "0");
        //SENDSMSRequest.add("range", 1);
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }
    //印厂
    private void DataBind2(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.AppFactory(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }
    //过失人集合
    private void DataBind3(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.BatchOffManagerManualAdd2(), RequestMethod.POST);
        SENDSMSRequest.add("type","过失人");
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        CallServer.getInstance().add(this, 1003, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind4(boolean isLoading)
    {
        Request<String> SENDSMSRequest  = NoHttp.createStringRequest(AppConstant.ClearMachine(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //SENDSMSRequest.add("searchKey", str);
        SENDSMSRequest.add("checklist", "[{id:"+CSIMSApplication.getAppContext().getUser().getOID()+"}]");
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                TaskCount taskCount = gson.fromJson(response.get(), TaskCount.class);
                if (taskCount.getResult() == 1) {
                    //{"result":1,"msg":"ok","total":3,"data":[{"count":2,"t_taskType":"爆仓区上架"},{"count":1,"t_taskType":"补货任务"},{"count":4,"t_taskType":"入库上架任务"}]}
                    if (taskCount.getResult() == 1) {
                        //new QBadgeView(getApplicationContext()).bindTarget(tv1).hide(true);
                        t1.hide(false);
                        t2.hide(false);
                        t3.hide(false);
                        t4.hide(false);
                        t5.hide(false);
                        for (int i = 0;i<taskCount.getData().size();i++)
                        {
                            if(taskCount.getData().get(i).getT_taskType().contains("质量问题"))
                            {
                                t1.setBadgeNumber(taskCount.getData().get(i).getCount());
                            }
                            else if(taskCount.getData().get(i).getT_taskType().contains("改版"))
                            {
                                t2.setBadgeNumber(taskCount.getData().get(i).getCount());
                            }
                            else if(taskCount.getData().get(i).getT_taskType().contains("补货"))
                            {
                                t3.setBadgeNumber(taskCount.getData().get(i).getCount());
                            }
                            else if(taskCount.getData().get(i).getT_taskType().contains("入库"))
                            {
                                t4.setBadgeNumber(taskCount.getData().get(i).getCount());
                            }
                            else if(taskCount.getData().get(i).getT_taskType().contains("爆仓"))
                            {
                                t5.setBadgeNumber(taskCount.getData().get(i).getCount());
                            }
                        }
                    }
                    else
                    {
                        t1.hide(false);
                        t2.hide(false);
                        t3.hide(false);
                        t4.hide(false);
                        t5.hide(false);
                    }
                    //ToastUtil.show(this, taskCount.getMsg());
                }else
                {
                    t1.hide(false);
                    t2.hide(false);
                    t3.hide(false);
                    t4.hide(false);
                    t5.hide(false);

                    ToastUtil.show(this, taskCount.getMsg());
                }
                break;
            case 1001://快速登录
                ProdNoModels prodNoModels = gson.fromJson(response.get(), ProdNoModels.class);
                if (prodNoModels.getResult() == 1) {
                    CSIMSApplication.getAppContext().putProdNo(prodNoModels);
                }else
                    ToastUtil.show(this,prodNoModels.getMsg());

                break;
            case 1002://快速登录
                FactoryModels factoryModels = gson.fromJson(response.get(), FactoryModels.class);
                if (factoryModels.getResult() == 1) {
                    FactoryModels.Bean bean = new FactoryModels.Bean();
                    bean.setF_department("请选择");
                    factoryModels.getData().add(0,bean);
                    CSIMSApplication.getAppContext().putFactory(factoryModels);
                }else
                    ToastUtil.show(this,factoryModels.getMsg());

                break;
            case 1003://快速登录
                PersonModels personModels = gson.fromJson(response.get(), PersonModels.class);
                if (personModels.getResult() == 1) {
                    PersonModels.Bean bean2 = new PersonModels.Bean();
                    bean2.setO_name("请选择");
                    personModels.getData().add(0,bean2);
                    CSIMSApplication.getAppContext().putPerson(personModels);
                }else
                    ToastUtil.show(this,personModels.getMsg());
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

}
