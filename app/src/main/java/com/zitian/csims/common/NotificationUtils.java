package com.zitian.csims.common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.zitian.csims.R;
import com.zitian.csims.ui.activity.SplashActivity;

import java.io.File;

public class NotificationUtils   extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";
    public Notification notification;

    public int idd = 0;

    public NotificationUtils(Context context){
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content){
        if(title.contains("继电器状态")){
            return new Notification.Builder(getApplicationContext(),id)
                    .setContentTitle(title)
                    .setContentText("设置继电器状态："+content)
                    .setSmallIcon(android.R.drawable.stat_notify_more)
                    .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg"))) //通知声音设置
                    .setVibrate(new long[]{0,1000,1000,1000})   //设置震动
                    .setLights(Color.GREEN,1000,1000)   //设置闪光灯提示
                    .setAutoCancel(true);
        }else {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.setPackage(getPackageName());
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            return new Notification.Builder(getApplicationContext(),id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg"))) //通知声音设置
                    .setVibrate(new long[]{0,1000,1000,1000})   //设置震动
                    .setLights(Color.GREEN,1000,1000)   //设置闪光灯提示
                    .setContentIntent(pi)
                    .setSmallIcon(android.R.drawable.stat_notify_more)
                    .setAutoCancel(true);
        }

    }

    public NotificationCompat.Builder getNotification_25(String title, String content){
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setPackage(getPackageName());
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        //RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),R.layout.activity_alarm);
        //remoteViews.setOnClickPendingIntent(R.id.alarmView,pi);
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon( R.mipmap.ic_launcher).setLargeIcon( BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg"))) //通知声音设置
                .setVibrate(new long[]{0,1000,1000,1000})   //设置震动
                .setLights(Color.GREEN,1000,1000)   //设置闪光灯提示
                .setAutoCancel(true);

//        return new Notification.Builder(this).setTicker("123").
//                setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//                .setContentText("123").setContentTitle( "你有最新的报警信息请点击查看" );

    }

    //发送通知
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(String title, String content){
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel();
            this.notification = getChannelNotification
                    (title, content).build();

            //这通知的其他属性，比如：声音和振动
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            getManager().notify(1,notification);
        }else{
            this.notification = getNotification_25(title, content).build();

            //这通知的其他属性，比如：声音和振动
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            getManager().notify(1,notification);
        }
    }

    //获取通知
    public Notification getNotification(){

        return this.notification;
    }
}
