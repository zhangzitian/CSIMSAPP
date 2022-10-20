package com.zitian.csims.ui.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.AreaNoModels;
import com.zitian.csims.model.BatchOffManagerList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.BatchOffManagerListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class SetServerIPActivity extends  BaseActivity implements  View.OnClickListener  {

    private ImageView icon_back;
    private EditText etIp;
    private Button bt01;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_server_ip);
        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("服务器IP地址设置");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        etIp = (EditText) findViewById(R.id.etIp);
        bt01 = (Button) findViewById(R.id.bt01);

        etIp.setText(CSIMSApplication.getAppContext().getServerIp());
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                CSIMSApplication.getAppContext().putServerIp(etIp.getText().toString());
                ToastUtil.show(this, "设置IP地址成功！！！");
                finish();
                break;
        }
    }

}
