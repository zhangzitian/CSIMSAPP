package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;

import com.zitian.csims.model.QcfeedbackList;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

public class QcfeedbackDetailActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {
    private ImageView icon_back;
    QcfeedbackList.Bean itemModel =null;

    private TextView tv_factoryid;

    private TextView mproduct_no;

    private TextView mproduct_name;

    private TextView mproduct_count;

    private TextView mqc_package;

    private TextView maq_package;

    private TextView mproblem_count;

    private TextView mqc_desc;

    private TextView mqc_review;

    private TextView mqc_programme;

    private TextView mqc_manager_review;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qc_list_detail);

        initView();
        setViewListener();
    }
    private void initView() {

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("质检反馈单详情");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        tv_factoryid = (TextView) findViewById(R.id.tv_factoryid);
        mproduct_no = (TextView) findViewById(R.id.mproduct_no);
        mproduct_name = (TextView) findViewById(R.id.mproduct_name);
        mproduct_count = (TextView) findViewById(R.id.mproduct_count);
        mqc_package = (TextView) findViewById(R.id.mqc_package);
        maq_package = (TextView) findViewById(R.id.maq_package);
        mproblem_count = (TextView) findViewById(R.id.mproblem_count);
        mqc_desc = (TextView) findViewById(R.id.mqc_desc);
        mqc_review = (TextView) findViewById(R.id.mqc_review);
        mqc_programme = (TextView) findViewById(R.id.mqc_programme);
        mqc_manager_review = (TextView) findViewById(R.id.mqc_manager_review);

        // 获取意图
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemModel = (QcfeedbackList.Bean)bundle.get("itemModel");
        tv_factoryid.setText(itemModel.getfactory_id());
        mproduct_no.setText(itemModel.getproduct_no());
        mproduct_name.setText(itemModel.getproduct_name());
        mproduct_count.setText(itemModel.getproduct_count()+"");
        mqc_package.setText(itemModel.getqc_package()+"");
        maq_package.setText(itemModel.getaq_package()+"");
        mproblem_count.setText(itemModel.getproblem_count()+"");
        mqc_desc.setText(itemModel.getqc_desc());
        mqc_review.setText(itemModel.getqc_review());
        mqc_programme.setText(itemModel.getqc_programme());
        mqc_manager_review.setText(itemModel.getqc_manager_review());
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {

    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

}
