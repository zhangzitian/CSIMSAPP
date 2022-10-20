package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.common.NotificationHelper;
import com.zitian.csims.common.Utils;
import com.zitian.csims.model.Login;
import com.zitian.csims.model.WarehousingInForkliftList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.DeviceIdUtil;
import com.zitian.csims.util.ToastUtil;

import java.net.URLEncoder;

public class LoginActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private Button login_btn;
    private EditText username;
    private EditText pwd;
    private TextView version_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("登录");
        tv_title.setGravity(Gravity.CENTER);

        login_btn = (Button) findViewById(R.id.login_btn);
        username = (EditText) findViewById(R.id.username);
        pwd = (EditText) findViewById(R.id.pwd);
        version_text = (TextView) findViewById(R.id.version_text);
    }

    private void setViewListener() {
        login_btn.setOnClickListener(this);
        version_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (!username.getText().toString().equals("") && !pwd.getText().toString().equals("")) {
                   try {
                       login_btn.setEnabled(false);
                       Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.LOGIN(), RequestMethod.POST);
                       SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
                       SENDSMSRequest.add("userid",username.getText().toString());
                       SENDSMSRequest.add("password",pwd.getText().toString());
                       SENDSMSRequest.add("ID", DeviceIdUtil.getDeviceId(getApplicationContext()));
                       CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, true, true);
                   }catch (Exception ex)
                   {}
                } else {
                    ToastUtil.show(this,"请输入帐号或密码");
                }
                break;
            case  R.id.version_text:
                Intent intent = new Intent(LoginActivity.this, SetServerIPActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000:
                login_btn.setEnabled(true);
                com.zitian.csims.model.Login AppLogin = gson.fromJson(response.get(), com.zitian.csims.model.Login.class);
                if (AppLogin.getResult() == 1) {
                    Login.Bean User = new Login.Bean();
                    User.setOID(AppLogin.getData().getOID());
                    User.setO_id(AppLogin.getData().getO_id());
                    User.setO_name(AppLogin.getData().getO_name());
                    User.setPasswd(AppLogin.getData().getPasswd());
                    User.setR_id(AppLogin.getData().getR_id());
                    User.setGroupname(AppLogin.getData().getGroupname());
                    User.setZy_id(AppLogin.getData().getZy_id());
                    User.setZyName(AppLogin.getData().getZyName());

                    CSIMSApplication.getAppContext().putUser(User);
                    CSIMSApplication.getAppContext().putUserToken(AppLogin.getData().getO_id());

                    CSIMSApplication.getAppContext().putRole(AppLogin.getRole());

                    ToastUtil.show(this,AppLogin.getMsg());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else
                {
                    ToastUtil.show(this,AppLogin.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        login_btn.setEnabled(true);
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

}
