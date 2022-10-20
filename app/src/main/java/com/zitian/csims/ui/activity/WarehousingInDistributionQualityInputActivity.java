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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.zitian.csims.model.WarehousingInDistributionCheckInput;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.HelperUtil;
import com.zitian.csims.util.ToastUtil;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class WarehousingInDistributionQualityInputActivity   extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private AutoCompleteTextView mProdNo;
    private TextView mProdName;
    private TextView mCSizeSpec;
    private EditText mNum;
    private Spinner mManufactor;
    private ImageView search_image;
    private ImageView search_image2;
    private Button bt01;

    private LinearLayout mrl5;
    private TextView mTSOrederList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_in_distribution_quality_input);
        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("【入库】-质检录入");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mProdNo = (AutoCompleteTextView) findViewById(R.id.mProdNo);
        mProdName = (TextView) findViewById(R.id.mWareHouseNo);
        mCSizeSpec = (TextView) findViewById(R.id.mCSizeSpec);
        mNum = (EditText) findViewById(R.id.mNum);
        mManufactor= (Spinner) findViewById(R.id.mManufactor);
        search_image = (ImageView) findViewById(R.id.search_image);
        search_image2 = (ImageView) findViewById(R.id.search_image2);
        bt01 = (Button) findViewById(R.id.bt01);

        mrl5= (LinearLayout) findViewById(R.id.rl5);
        mTSOrederList= (TextView) findViewById(R.id.mTSOrederList);

        SetProdNo(mProdNo,1);
        SetFactory(mManufactor);

        mProdNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                mProdNo.setText(s);
                mProdNo.setSelection(s.length());//set cursor to the end
                DataBind(true);
            }
        });
    }

    private void setViewListener() {
        icon_back.setOnClickListener(this);
        search_image.setOnClickListener(this);
        search_image2.setOnClickListener(this);
        bt01.setOnClickListener(this);
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
            case R.id.bt01:
                if(mProdName.getText().toString().equals(""))
                {
                    Alert("提示","请选择一个商品","知道了");
                }else if(mNum.getText().toString().equals(""))
                {
                    Alert("提示","请填写数量","知道了");
                }
                else if(!HelperUtil.isInteger(mNum.getText().toString()))
                {
                    Alert("提示","商品数量必须是整数","知道了");
                }
                else if(Integer.valueOf(mNum.getText().toString()) < 1)
                {
                    Alert("提示","商品数量必须大于0","知道了");
                }
                else if(mManufactor.getSelectedItem().toString().contains("选择"))
                {
                    Alert("提示","请选择印厂","知道了");
                }
                else {
                    bt01.setEnabled(false);
                    DataBind2(true);
                }
                break;
            case R.id.search_image2:
                //startActivityForResult(new Intent(WarehousingInDistributionQualityInputActivity.this, CaptureActivity.class),requestCode);
                this.cls = CaptureActivity.class;
                this.title = "默认扫码";
                checkCameraPermissions();
                //跳转的默认扫码界面
                //startActivityForResult(new Intent(WarehousingInDistributionQualityInputActivity.this,CaptureActivity.class),requestCode);
                break;
        }
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
                mProdNo.setText(result);
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

    private void DataBind(boolean isLoading) {
        if(!mProdNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(mProdNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo", s);
            CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        }
    }

    private void DataBind2(boolean isLoading) {
        String s = ConvertToProdNo(mProdNo.getText().toString());
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("q_prodNo",  s);
        SENDSMSRequest.add("q_prodName",mProdName.getText().toString());
        SENDSMSRequest.add("q_inputNum", mNum.getText().toString());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("q_factory", mManufactor.getSelectedItem().toString());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    private WarehousingInDistributionCheckInput.Bean bean = null;

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://查询商品
                WarehousingInDistributionQualityInput warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    if (warehousingInDistributionQualityInput.getData() != null) {
                        mProdName.setText(warehousingInDistributionQualityInput.getData().h_name);
                        mCSizeSpec.setText(warehousingInDistributionQualityInput.getData().CSizeSpec);
                        if(warehousingInDistributionQualityInput.getData().TSOrederList!=null)
                        {
                            if(!warehousingInDistributionQualityInput.getData().TSOrederList.equals(""))
                            {
                                mTSOrederList.setText(warehousingInDistributionQualityInput.getData().TSOrederList);
                                mrl5.setVisibility(View.VISIBLE);
                            }else
                                mrl5.setVisibility(View.GONE);
                        }else
                            mrl5.setVisibility(View.GONE);

                    } else if (warehousingInDistributionQualityInput.getResult() == 0) {
                    }
                }
                break;
            case 1001://提交质检
//                warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
//                if (warehousingInDistributionQualityInput.getResult() == 1) {
//                    //ToastUtil.show(this, warehousingInDistributionQualityInput.getMsg());
//                    Intent intent = new Intent(this,WarehousingInDistributionCheckInputActivity.class);
//                    intent.putExtra("q_ID",String.valueOf(warehousingInDistributionQualityInput.getTotal()));
//                    startActivityForResult(intent,2);//
//                }
                bt01.setEnabled(true);
                WarehousingInDistributionCheckInput warehousingInDistributionCheckInput = gson.fromJson(response.get(), WarehousingInDistributionCheckInput.class);
                if (warehousingInDistributionCheckInput.getResult() == 1) {
                    if (warehousingInDistributionCheckInput.getData() != null) {
                        bean = warehousingInDistributionCheckInput.getData();
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("itemModel", bean);
                        Intent intent = new Intent(this,WarehousingInDistributionCheckInputActivity.class);
                        intent.putExtras(mBundle);
                        startActivityForResult(intent,2);//
                    }
                }else
                {
                    ToastUtil.show(this, warehousingInDistributionCheckInput.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

}