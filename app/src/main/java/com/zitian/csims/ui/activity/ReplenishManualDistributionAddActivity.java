package com.zitian.csims.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.gson.Gson;
import com.king.zxing.CaptureActivity;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.ReplenishManualDistributionAdd;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.ToastUtil;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ReplenishManualDistributionAddActivity   extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private AutoCompleteTextView actvProdNo;
    private TextView tvProName2;
    private ImageView search_image;
    private ImageView search_image2;
    private Button bt01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replenish_manual_distribution_add);

        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("【补货】- 领取补货任务");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        search_image = (ImageView) findViewById(R.id.search_image);
        search_image2 = (ImageView) findViewById(R.id.search_image2);
        actvProdNo  = (AutoCompleteTextView) findViewById(R.id.actvProdNo);
        tvProName2  = (TextView) findViewById(R.id.tvProName2);
        bt01 = (Button) findViewById(R.id.bt01);

        SetProdNo(actvProdNo,1);
        actvProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                actvProdNo.setText(s);
                actvProdNo.setSelection(s.length());//set cursor to the end
                DataBind(true);
            }
        });

    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
        search_image.setOnClickListener(this);
        search_image2.setOnClickListener(this);
        //找到AutoCompleteTextView控件
        //final AutoCompleteTextView autoTextView = (AutoCompleteTextView) findViewById(R.id.actvProdNo);

        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        //actvProdNo.setThreshold(2); //设置最小提示字符数量为2
        //actvProdNo.setAdapter(adapter); //为AutoCompleteTextView控件设置适配器
        //actvProdNo.setDropDownHeight(800); //设置下拉框的高度
        //ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,AppProdNo.COUNTRIES);
        //actvProdNo.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_image:
                DataBind(true);
                break;
            case R.id.search_image2:
                //startActivityForResult(new Intent(WarehousingInDistributionQualityInputActivity.this, CaptureActivity.class),requestCode);
                this.cls = CaptureActivity.class;
                this.title = "默认扫码";
                checkCameraPermissions();
                //跳转的默认扫码界面
                //startActivityForResult(new Intent(ReplenishManualDistributionAddActivity.this, CaptureActivity.class),requestCode);
                break;
            case R.id.bt01:
                DataBind2(true);
                break;
        }
    }

    private void DataBind(boolean isLoading)
    {
        if(actvProdNo.getText().toString().equals(""))
        {
            Toast.makeText(ReplenishManualDistributionAddActivity.this,"搜索内容不能等于空！",Toast.LENGTH_SHORT).show();
        }else
        {
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ReplenishManualDistributionAdd(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo",actvProdNo.getText().toString());
            CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        }

    }

    private void DataBind2(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ReplenishManualDistributionAdd2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_prodNo",actvProdNo.getText().toString());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                ReplenishManualDistributionAdd outofSpaceForkliftList = gson.fromJson(response.get(), ReplenishManualDistributionAdd.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    tvProName2.setText(outofSpaceForkliftList.getData().getH_name());
                }else
                {
                    Toast.makeText(ReplenishManualDistributionAddActivity.this,outofSpaceForkliftList.getMsg(),Toast.LENGTH_SHORT).show();
                }
                break;
            case 1001://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), ReplenishManualDistributionAdd.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    Toast.makeText(ReplenishManualDistributionAddActivity.this,outofSpaceForkliftList.getMsg(),Toast.LENGTH_SHORT).show();
                    finish();
                }else
                {
                    Toast.makeText(ReplenishManualDistributionAddActivity.this,outofSpaceForkliftList.getMsg(),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

    public static final int RC_CAMERA = 0X01;
    private Class<?> cls;
    private String title;
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";
    private boolean isContinuousScan;
    public static final int REQUEST_CODE_SCAN = 0X01;
    /**
     * 检测拍摄权限
     */
    @AfterPermissionGranted(RC_CAMERA)
    private void checkCameraPermissions(){
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            startScan(cls,title);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera),
                    RC_CAMERA, perms);
        }
    }
    /**
     * 扫码
     * @param cls
     * @param title
     */
    private void startScan(Class<?> cls,String title){
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this,R.anim.push_left_in,R.anim.push_left_out);
        Intent intent = new Intent(this, cls);
        intent.putExtra(KEY_TITLE,title);
        intent.putExtra(KEY_IS_CONTINUOUS,isContinuousScan);
        ActivityCompat.startActivityForResult(this,intent,REQUEST_CODE_SCAN,optionsCompat.toBundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult, requestCode: " + requestCode + ", resultCode: " + resultCode);
        if(requestCode == REQUEST_CODE_SCAN  && resultCode!=0)
        {
            if( data.getStringExtra("SCAN_RESULT")!=null)
            {
                String result =  data.getStringExtra("SCAN_RESULT");
                actvProdNo.setText(result);
                DataBind(true);
            }
        }else
        {
            if(data==null)//返回按钮操作
            {
                finish();
            }else
            {
                //1是返回首页 0是修改
                int update = data.getIntExtra("update",0);
                if(update == 1)
                    finish();
            }

        }
    }


}