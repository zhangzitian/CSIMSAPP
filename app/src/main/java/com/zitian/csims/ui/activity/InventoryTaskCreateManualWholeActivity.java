package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.common.NotificationUtils;
import com.zitian.csims.model.Msg;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.DateUtil;
import com.zitian.csims.util.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryTaskCreateManualWholeActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Button bt01;
    ListView listView;
    private List<String> strList = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;
    public static final int UPDATE = 0x1;
    private Handler mHandler =  new Handler();
    public  int Count = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_task_create_manual_whole);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Count = 0;
        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("整件整包生成任务");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        bt01 = (Button) findViewById(R.id.bt01);

        adapter = new ArrayAdapter<String>(InventoryTaskCreateManualWholeActivity.this, android.R.layout.simple_list_item_1, strList);
        listView = (ListView) findViewById(R.id.ListLog);
        listView.setAdapter(adapter);
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    public long timeStamp ;
    public int isRun = 0;
    //消息处理者,创建一个Handler的子类对象,目的是重写Handler的处理消息的方法(handleMessage())
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE:
                    if(msg.obj.toString().contains("已完成任务数:"))
                    {
                        String[] str = msg.obj.toString().split("已完成任务数:");
                        if(str.length==2)
                        {
                            if(Count != Integer.parseInt(str[1]))
                            {
                                Count = Integer.parseInt(str[1]);
                                strList.add(String.valueOf(msg.obj));
                            }
                        }
                    }
                    else
                    {
                        strList.add(String.valueOf(msg.obj));
                    }
                    adapter.notifyDataSetChanged();
                    listView.setSelection(ListView.FOCUS_DOWN);

                    if(msg.obj.toString().contains("无需重复操作"))
                    {
                        bt01.setText("生成任务完成");
                    }
                    else  if(msg.obj.toString().contains("返回按钮"))
                    {
                        bt01.setText("生成任务完成");
                    }else  if(msg.obj.toString().contains("生成失败"))
                    {
                        bt01.setText("生成失败");
                    }
                    else
                    {
                        Long s = DateUtil.dateDiff(timeStamp);
                        StringBuilder sb = new StringBuilder();
                        bt01.setText(sb.append("生成任务中(").append(s).append("s)"));
                    }
                    break;
            }
        }

    };

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (isRun == 0) {
                isRun = 1;
                DataBind(true);
            }
            handler.postDelayed(this, 2000);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                bt01.setEnabled(false);
                Message msg1 = new Message();
                msg1.what = UPDATE;
                msg1.obj = "生成任务过程中耐心等待，请勿退出界面。";
                handler.sendMessage(msg1);
                handler.postDelayed(runnable, 2000);//每两秒执行一次runnable.
                timeStamp = DateUtil.getCurrentMillis();
                break;
        }
    }

    private void DataBind(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryTaskCreateManualWhole(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        CallServer.getInstance().add(InventoryTaskCreateManualWholeActivity.this, 1000, SENDSMSRequest, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Gson gson = new Gson();
                Msg msg = gson.fromJson(response.get(), Msg.class);
                Message msg1 = new Message();
                msg1.what = UPDATE;
                msg1.obj = msg.getMsg();
                handler.sendMessage(msg1);
                if (msg.getResult() == 1) {
                    try {
                        Thread.sleep(500);//休眠1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    isRun = 0;
                }else
                {
                    ToastUtil.show(getApplicationContext(),msg.getMsg());
                    mHandler.removeCallbacks(runnable);
                    bt01.setEnabled(false);
                }
            }
            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
            }
        }, false, false);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                OutofSpaceForkliftList outofSpaceForkliftList = gson.fromJson(response.get(), OutofSpaceForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }else
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        //ToastUtil.show(this, "访问失败" + response.getException().getMessage());
        Alert( "访问失败","访问失败，返回重新进入页面，点击生成任务。" + response.getException().getMessage(),"我知道了");
    }

    @Override
    protected void onDestroy() {
        //将线程销毁掉
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }



}
