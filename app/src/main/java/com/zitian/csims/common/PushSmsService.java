package com.zitian.csims.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.model.Msg;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.util.ToastUtil;

public class PushSmsService  extends Service {
    private MyThread myThread;
    private NotificationManager manager;
    private Notification notification;
    private PendingIntent pi;
    //private AsyncHttpClient client;
    private boolean flag = true;
    NotificationUtils notificationUtils = null;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("oncreate()");
        //this.client = new AsyncHttpClient();
        if(notificationUtils==null)
            notificationUtils = new NotificationUtils(getApplication());

        this.myThread = new MyThread();
        this.myThread.start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        this.flag = false;
        super.onDestroy();
    }

    private void notification(String content, String number, String date) {
        // 获取系统的通知管理器
//        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notification = new Notification(R.drawable.ic_menu_compose, content,
//                System.currentTimeMillis());
//        notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
//        notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
//        notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除
//
//        Intent intent = new Intent(getApplicationContext(),
//                ContentActivity.class);
//        intent.putExtra("content", content);
//        intent.putExtra("number", number);
//        intent.putExtra("date", date);
//
//        pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
//
//        notification.setLatestEventInfo(getApplicationContext(), number
//                + "发来短信", content, pi);
//        // 将消息推送到状态栏
//        manager.notify(0, notification);
    }
    public void GetMsg()
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.GetMessage(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("zy_id", CSIMSApplication.getAppContext().getUser().getZy_id());
        //CallServer.getInstance().add(this, 100, SENDSMSRequest, this, false, true);
        CallServer.getInstance().add(this, 100, SENDSMSRequest, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Gson gson = new Gson();
                Msg msg = gson.fromJson(response.get(), Msg.class);
                if (msg.getResult() == 1) {
                    for (int i = 0;i<msg.getData().size();i++)
                    {
                        //NotificationHelper.show(getApplicationContext());
                        notificationUtils.sendNotification(msg.getData().get(i).getW_type(), msg.getData().get(i).getW_message());
                        try{
                            Thread.sleep(10 * 1000);
                        }catch (Exception ex)
                        {}
                    }
                }else
                {
                    //ToastUtil.show(getApplicationContext(),msg.getMsg());
                }
            }
            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
            }
        }, false, false);
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (flag) {
                System.out.println("发送请求");
                try {
                    if(CSIMSApplication.getAppContext().getUser()!=null)
                    {
                        if(CSIMSApplication.getAppContext().getUser().getZy_id()!=null)
                            GetMsg();
                    }
                    // 每个10秒向服务器发送一次请求
                    Thread.sleep(90 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
