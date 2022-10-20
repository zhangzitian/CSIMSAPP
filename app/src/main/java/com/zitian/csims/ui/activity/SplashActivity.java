package com.zitian.csims.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.SearchPupItenAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.SharePreferenceUtil;
import com.zitian.csims.util.ToastUtil;

public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setImmerseLayout(findViewById(R.id.head_id));
//        BindAreaNo(true);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CSIMSApplication.getAppContext().getUser() != null) {
                    if (!CSIMSApplication.getAppContext().getUser().equals("")) {
                        openActivity(MainActivity.class);
                    }else
                        openActivity(LoginActivity.class);
                }
                else
                {
                    openActivity(LoginActivity.class);
                }
                finish();
            }
        }, 2000);

    }


    protected void setImmerseLayout(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			//透明导航栏
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            int statusBarHeight = getStatusBarHeight(this.getBaseContext());
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }
    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public void BindAreaNo(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.AppAreaNo(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //CallServer.getInstance().add(this, 100, SENDSMSRequest, this, false, true);
        CallServer.getInstance().add(this, 100, SENDSMSRequest, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Gson gson = new Gson();
                AreaNoModels areaNoModels = gson.fromJson(response.get(), AreaNoModels.class);
                if (areaNoModels.getResult() == 1) {
                    CSIMSApplication.getAppContext().setAreaNoModels(areaNoModels.getData());
                }else
                {
                    ToastUtil.show(getApplicationContext(),areaNoModels.getMsg());
                }
                if (CSIMSApplication.getAppContext().getUserToken() != null) {
                    openActivity(TestActivity.class);
                }
                else
                {
                    //ToastUtil.show(SplashActivity.this, "开始登录账号！");
                    openActivity(LoginActivity.class);
                }
                finish();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        boolean isFirst = SharePreferenceUtil.getBoolean(SplashActivity.this,"isFirst",true);
//                        if (isFirst) {
//                            SharePreferenceUtil.putBoolean(SplashActivity.this,"isFirst",false);
//                            openActivity(LoginActivity.class);
//                        } else {
//                            openActivity(TestActivity.class);
//                        }
                    }
                }, 0);
            }
            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
            }
        }, false, true);
    }

}
