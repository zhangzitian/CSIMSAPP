package com.zitian.csims.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.DailyErrorManagerTaskList;
import com.zitian.csims.model.InventoryContrastManualAddItemList;
import com.zitian.csims.model.InventoryEmpty;
import com.zitian.csims.model.InventoryEmpty2;
import com.zitian.csims.model.InventoryManualList;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.model.WarehousingErrorForkliftDistributionList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.DailyErrorManagerTaskListAdapter;
import com.zitian.csims.ui.adapter.InventoryEmptyAdapter;
import com.zitian.csims.ui.adapter.OutofSpaceForkliftListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle;
import com.zitian.csims.ui.widget.CustomDialogStyle2;
import com.zitian.csims.ui.widget.CustomDialogStyle3;
import com.zitian.csims.ui.widget.CustomDialogStyle5;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import org.json.JSONArray;

import java.util.List;

public class InventoryEmptyActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Spinner sp;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;

    private RelativeLayout rl11;
    private RelativeLayout rl22;

    private RecyclerView mRecyclerView = null;
    private InventoryEmptyAdapter mDataAdapter = null;
    private List<InventoryEmpty2.Bean> list = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_empty);

        initView();
        setViewListener();
        IsStatusInventory(true);
        DataBind(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("空位盘点");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        sp = (Spinner) findViewById(R.id.sp);

        rl1 = (RelativeLayout) findViewById(R.id.RelativeLayout1);
        rl2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);
        rl3 = (RelativeLayout) findViewById(R.id.RelativeLayout3);

        rl11 = (RelativeLayout) findViewById(R.id.RelativeLayout11);
        rl22 = (RelativeLayout) findViewById(R.id.RelativeLayout22);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        LinearLayoutManager ms= new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        mRecyclerView.setLayoutManager(ms); //给RecyClerView 添加设置好的布局样式
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl11.setOnClickListener(this);
        rl22.setOnClickListener(this);
    }
    CustomDialogStyle5.Builder builder ;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.RelativeLayout1:
                if(!sp.getSelectedItem().toString().equals("选择列号"))
                {
                    //DataBind5(sp.getSelectedItem().toString(),0,true);
                }
                break;
            case R.id.RelativeLayout2:
                if(!sp.getSelectedItem().toString().equals("选择列号"))
                {
                    //DataBind5(sp.getSelectedItem().toString(),2,true);
                }
                break;
            case R.id.RelativeLayout3:
                if(!sp.getSelectedItem().toString().equals("选择列号"))
                {
                    DataBind3(sp.getSelectedItem().toString(),true);
                }
                break;
            case R.id.RelativeLayout11:
                builder = new CustomDialogStyle5.Builder(InventoryEmptyActivity.this);
                builder.setTitle("库位号:")
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
                                boolean one = builder.cb_message_one_dialog.isChecked();
                                boolean two = builder.cb_message_two_dialog.isChecked();
                                boolean three = builder.cb_message_three_dialog.isChecked();
                                boolean four = builder.cb_message_four_dialog.isChecked();
                                if(one == false &  two == false &  three == false & four == false)
                                {
                                }else
                                {
                                    String cengs = isBool(one) + "," +  isBool(two) + "," + isBool(three) + ","+ isBool(four);
                                    DataBind5(sp.getSelectedItem().toString(),0,true,cengs);
                                }
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.RelativeLayout22:
                builder = new CustomDialogStyle5.Builder(InventoryEmptyActivity.this);
                builder.setTitle("库位号:")
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
                                boolean one = builder.cb_message_one_dialog.isChecked();
                                boolean two = builder.cb_message_two_dialog.isChecked();
                                boolean three = builder.cb_message_three_dialog.isChecked();
                                boolean four = builder.cb_message_four_dialog.isChecked();
                                if(one == false &  two == false &  three == false & four == false)
                                {
                                }else
                                {
                                    String cengs = isBool(one) + "," +  isBool(two) + "," + isBool(three) + ","+ isBool(four);
                                    DataBind5(sp.getSelectedItem().toString(),2,true,cengs);
                                }
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }

    public  String isBool(boolean b)
    {
        if(b)
        {
            return  "1";
        }
        else
        {
            return  "0";
        }
    }

    //获取列名
    private void DataBind(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryEmpty(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }
    //检测
    private void DataBind22(String wareHouseNo,boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.CheckEmptyLog(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",wareHouseNo );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1006, SENDSMSRequest, this, false, isLoading);
    }
    //开始
    private void DataBind2(String wareHouseNo,boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryEmpty2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",wareHouseNo);
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }
    //结束 提交
    private void DataBind3(String wareHouseNo,boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryEmpty3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",wareHouseNo);
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    //获取数据
    private void DataBind4(String wareHouseNo,boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryEmpty4(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",wareHouseNo);
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1003, SENDSMSRequest, this, false, isLoading);
    }
    //提交数据 全选
    private void DataBind5(String wareHouseNo,int status,boolean isLoading,String cengs)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryEmpty5(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo",wareHouseNo);
        SENDSMSRequest.add("status",status);
        SENDSMSRequest.add("cengs",cengs);
        SENDSMSRequest.add("type",0);
        CallServer.getInstance().add(this, 1004, SENDSMSRequest, this, false, isLoading);
    }
    //提交数据 单选
    private void DataBind6(String wareHouseNo,int status,boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryEmpty5(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo",wareHouseNo.replace("留","").replace("废",""));
        SENDSMSRequest.add("status",status);
        SENDSMSRequest.add("type",1);
        CallServer.getInstance().add(this, 1005, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                InventoryEmpty outofSpaceForkliftList = gson.fromJson(response.get(), InventoryEmpty.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    List<InventoryEmpty.Bean> list = outofSpaceForkliftList.getData();
                    InventoryEmpty.Bean b = new InventoryEmpty.Bean();
                    b.setI_rowName("选择列号");
                    list.add(0,b);
                    ArrayAdapter<InventoryEmpty.Bean> spinnerAdapter = new ArrayAdapter<InventoryEmpty.Bean>(getApplication(), R.layout.simple_spinner_item, list);
                    spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    sp.setAdapter(spinnerAdapter);
                    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                            if(!sp.getSelectedItem().toString().contains("列号"))
                            {
                                DataBind22(sp.getSelectedItem().toString(),true);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Another interface callback
                        }
                    });
                }else
                    ToastUtil.show(this,outofSpaceForkliftList.getMsg());
                break;
            case 1001://快速登录
                InventoryEmpty2 outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryEmpty2.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    DataBind4(sp.getSelectedItem().toString(),true);
                    //ToastUtil.show(this,outofSpaceForkliftList2.getMsg());
                }
                break;
            case 1002:
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryEmpty2.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    DataBind(true);
                    mDataAdapter.mDataList.clear();
                    mDataAdapter.notifyDataSetChanged();
                    ToastUtil.show(this,outofSpaceForkliftList2.getMsg());
                }else
                    ToastUtil.show(this,outofSpaceForkliftList2.getMsg());
                break;
            case 1003:
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryEmpty2.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    list = outofSpaceForkliftList2.getData();
                    mDataAdapter = new InventoryEmptyAdapter(outofSpaceForkliftList2.getData());
                    mDataAdapter.setOnItemClickListener(MyItemClickListener);
                    mRecyclerView.setAdapter(mDataAdapter);
                }else
                    ToastUtil.show(this,outofSpaceForkliftList2.getMsg());
                break;
            case 1004:
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryEmpty2.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    list = outofSpaceForkliftList2.getData();
                    mDataAdapter = new InventoryEmptyAdapter(outofSpaceForkliftList2.getData());
                    mDataAdapter.setOnItemClickListener(MyItemClickListener);
                    mRecyclerView.setAdapter(mDataAdapter);
                }else
                    ToastUtil.show(this,outofSpaceForkliftList2.getMsg());
                break;
            case 1005:
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryEmpty2.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    //itemModel = mDataAdapter.mDataList.get(pos);
                    itemModel = outofSpaceForkliftList2.getData().get(0);
                    mDataAdapter.mDataList.set(pos,itemModel);
                    mDataAdapter.notifyItemChanged(pos, "jdsjlzx");
                }else
                    ToastUtil.show(this,outofSpaceForkliftList2.getMsg());
                break;
            case 1006:
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryEmpty2.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    DataBind2(sp.getSelectedItem().toString(),true);
                }else if (outofSpaceForkliftList2.getResult() == 2) {
                    CustomDialogStyle2.Builder builder = new CustomDialogStyle2.Builder(InventoryEmptyActivity.this);
                    builder.setTitle("温馨提示")
                            .setMessage("用户【"+CSIMSApplication.getAppContext().getUser().getO_name()+"】确定要选择列号【"+sp.getSelectedItem().toString()+"】 开始盘点吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    sp.setSelection(0,true);
                                }
                            })
                            .setPositionButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    DataBind2(sp.getSelectedItem().toString(),true);
                                }
                            })
                            .create()
                            .show();
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

    InventoryEmpty2.Bean itemModel  = null;
    int pos = 0;
    public int GetStatus(TextView tv)
    {
        int status = -1;
        if(tv.getCurrentTextColor() != Color.parseColor("#20A1FF"))
            status = 0;
        else if(tv.getCurrentTextColor() != Color.parseColor("#ffffff"))
            status = 2;

        return  status;
    }

    private InventoryEmptyAdapter.OnItemClickListener MyItemClickListener = new InventoryEmptyAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, InventoryEmptyAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            itemModel = list.get(position);
            pos = position;
            switch (v.getId()){
                case R.id.bt1:
                    TextView tv = (TextView)((RelativeLayout) v).getChildAt(0);
                    //ImageView iv = (ImageView)((RelativeLayout) v).getChildAt(1);
                    if(tv.getCurrentTextColor() != Color.parseColor("#cccccc"))
                    {
                        DataBind6(tv.getText().toString(),GetStatus(tv),true);
                    }
                    break;
                case R.id.bt2:
                    tv = (TextView)((RelativeLayout) v).getChildAt(0);
                    if(tv.getCurrentTextColor() != Color.parseColor("#cccccc"))
                    {
                        DataBind6(tv.getText().toString(),GetStatus(tv),true);
                    }
                    break;
                case R.id.bt3:
                    tv = (TextView)((RelativeLayout) v).getChildAt(0);
                    if(tv.getCurrentTextColor() != Color.parseColor("#cccccc"))
                    {
                        DataBind6(tv.getText().toString(),GetStatus(tv),true);
                    }
                    break;
                case R.id.bt4:
                    tv = (TextView)((RelativeLayout) v).getChildAt(0);
                    if(tv.getCurrentTextColor() != Color.parseColor("#cccccc"))
                    {
                        DataBind6(tv.getText().toString(),GetStatus(tv),true);
                    }
                    break;
                default:
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v, InventoryEmptyAdapter.ViewName viewName, int position) {
            itemModel = list.get(position);
            pos = position;
            switch (v.getId()){
                case R.id.bt1:
                    TextView tv = (TextView)((RelativeLayout) v).getChildAt(0);
                    if(tv.getCurrentTextColor() == Color.parseColor("#20A1FF"))
                    {
                        Toast.makeText(InventoryEmptyActivity.this,"满盘才可以纠错！", Toast.LENGTH_SHORT).show();
                    }
                    else if(tv.getCurrentTextColor() != Color.parseColor("#cccccc"))
                    {
                        //InventoryEmpty2.Bean itemModel = list.get(position);
                        Intent intent = new Intent(InventoryEmptyActivity.this,InventoryEmptyErrorActivity.class);
                        intent.putExtra("wareHouseNo",tv.getText());
                        startActivityForResult(intent,0);
                    }
                    break;
                case R.id.bt2:
                    tv = (TextView)((RelativeLayout) v).getChildAt(0);
                    if(tv.getCurrentTextColor() == Color.parseColor("#20A1FF"))
                    {
                        Toast.makeText(InventoryEmptyActivity.this,"满盘才可以纠错！", Toast.LENGTH_SHORT).show();
                    }
                    else if(tv.getCurrentTextColor() != Color.parseColor("#cccccc"))
                    {
                        //InventoryEmpty2.Bean itemModel = list.get(position);
                        Intent intent = new Intent(InventoryEmptyActivity.this,InventoryEmptyErrorActivity.class);
                        intent.putExtra("wareHouseNo",tv.getText());
                        startActivityForResult(intent,0);
                    }
                    break;
                case R.id.bt3:
                    tv = (TextView)((RelativeLayout) v).getChildAt(0);
                    if(tv.getCurrentTextColor() == Color.parseColor("#20A1FF"))
                    {
                        Toast.makeText(InventoryEmptyActivity.this,"满盘才可以纠错！", Toast.LENGTH_SHORT).show();
                    }
                    else if(tv.getCurrentTextColor() != Color.parseColor("#cccccc"))
                    {
                        //InventoryEmpty2.Bean itemModel = list.get(position);
                        Intent intent = new Intent(InventoryEmptyActivity.this,InventoryEmptyErrorActivity.class);
                        intent.putExtra("wareHouseNo",tv.getText());
                        startActivityForResult(intent,0);
                    }
                    break;
                case R.id.bt4:
                    tv = (TextView)((RelativeLayout) v).getChildAt(0);
                    if(tv.getCurrentTextColor() == Color.parseColor("#20A1FF"))
                    {
                        Toast.makeText(InventoryEmptyActivity.this,"满盘才可以纠错！", Toast.LENGTH_SHORT).show();
                    }
                    else if(tv.getCurrentTextColor() != Color.parseColor("#cccccc"))
                    {
                        //InventoryEmpty2.Bean itemModel = list.get(position);
                        Intent intent = new Intent(InventoryEmptyActivity.this,InventoryEmptyErrorActivity.class);
                        intent.putExtra("wareHouseNo",tv.getText());
                        startActivityForResult(intent,0);
                    }
                    //Toast.makeText(InventoryEmptyActivity.this,"你长按了item按钮"+(position+1), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    //结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 ){
            if(!sp.getSelectedItem().toString().equals("选择列号"))
            {
                if(data!=null)
                {
                    String result_value = data.getStringExtra("result");
                    DataBind6(result_value.toString(),2,true);
                }
            }
        }
    }


}