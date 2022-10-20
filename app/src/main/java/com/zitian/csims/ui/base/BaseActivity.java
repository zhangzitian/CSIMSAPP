package com.zitian.csims.ui.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.common.AppManager;
import com.zitian.csims.common.NotificationHelper;
import com.zitian.csims.common.NotificationUtils;
import com.zitian.csims.common.PushSmsService;
import com.zitian.csims.common.Utils;
import com.zitian.csims.model.AreaNoModels;
import com.zitian.csims.model.DailyErrorManagerTaskDetails;
import com.zitian.csims.model.FactoryModels;
import com.zitian.csims.model.Msg;
import com.zitian.csims.model.ProdNoModels;
import com.zitian.csims.model.TaskCount;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.activity.LoginActivity;
import com.zitian.csims.ui.activity.TestActivity;
import com.zitian.csims.ui.adapter.SearchPupItenAdapter;
import com.zitian.csims.ui.adapter.SearchPupItenAdapter2;
import com.zitian.csims.ui.widget.CustomDialogStyle;
import com.zitian.csims.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


public class BaseActivity extends AppCompatActivity  {
    public static final String TAG = BaseActivity.class.getSimpleName();

    //protected ImageView mBackImage;//返回按钮
    //protected TextView mTitle, mRight;
    public int requestCode = 0;
    //AutoCompleteTextView autoCompleteTextView;
    //AutoCompleteTextView autoCompleteTextView2;
    //Spinner spinner;
    Badge t1 ;
    Badge t2 ;
    Badge t3 ;
    Badge t4 ;
    Badge t5 ;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        //设置窗口无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        //GetTaskCount();
        //GetMsg();
        //AppProdNo();
        //AppAreaNo();
        //AppFactory();
    }

    public void GetTaskCount(TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5)
    {
        t1 = new QBadgeView(getApplicationContext()).bindTarget(tv1);
        t2 = new QBadgeView(getApplicationContext()).bindTarget(tv2);
        t3 = new QBadgeView(getApplicationContext()).bindTarget(tv3);
        t4 = new QBadgeView(getApplicationContext()).bindTarget(tv4);
        t5 = new QBadgeView(getApplicationContext()).bindTarget(tv5);
        TaskCount(tv1,tv2,tv3,tv4,tv5);

//        CountDownTimer cdt = new CountDownTimer(1000 * 60 * 60 * 24 * 365, 1000 * 30)//参数1：计时总时间，参数2：每次扣除时间数
//        {
//            @Override
//            public void onTick(long millisUntilFinished)
//            {
//                //Utils.isPermissionOpen(this);
//                //Utils.openPermissionSetting(this);
//                //NotificationHelper.show(BaseActivity.this);
//                TaskCount(tv1,tv2,tv3,tv4,tv5);
//                //etMsg();
//            }
//            @Override
//            public void onFinish() {
//            }
//        };
//        cdt.start();
    }

    public void TaskCount(TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.GetUnOpenTasks(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //CallServer.getInstance().add(this, 100, SENDSMSRequest, this, false, true);
        CallServer.getInstance().add(this, 100, SENDSMSRequest, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                //{"result":1,"msg":"ok","total":3,"data":[{"count":2,"t_taskType":"爆仓区上架"},{"count":1,"t_taskType":"补货任务"},{"count":4,"t_taskType":"入库上架任务"}]}
                Gson gson = new Gson();
                TaskCount taskCount = gson.fromJson(response.get(), TaskCount.class);

                if (taskCount.getResult() == 1) {
                    //new QBadgeView(getApplicationContext()).bindTarget(tv1).hide(true);
                    t1.hide(false);
                    t2.hide(false);
                    t3.hide(false);
                    t4.hide(false);
                    t5.hide(false);

                    for (int i = 0;i<taskCount.getData().size();i++)
                    {
                        if(taskCount.getData().get(i).getT_taskType().contains("质量问题"))
                        {
                            t1.setBadgeNumber(taskCount.getData().get(i).getCount());
                        }
                        else if(taskCount.getData().get(i).getT_taskType().contains("改版"))
                        {
                            t2.setBadgeNumber(taskCount.getData().get(i).getCount());
                        }
                        else if(taskCount.getData().get(i).getT_taskType().contains("补货"))
                        {
                            t3.setBadgeNumber(taskCount.getData().get(i).getCount());
                        }
                        else if(taskCount.getData().get(i).getT_taskType().contains("入库"))
                        {
                            t4.setBadgeNumber(taskCount.getData().get(i).getCount());
                        }
                        else if(taskCount.getData().get(i).getT_taskType().contains("爆仓"))
                        {
                            t5.setBadgeNumber(taskCount.getData().get(i).getCount());
                        }
                    }
                }
                else
                {
                    t1.hide(false);
                    t2.hide(false);
                    t3.hide(false);
                    t4.hide(false);
                    t5.hide(false);
                }
            }
            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
            }
        }, false, false);
    }

    public void GetMsg()
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.GetMessage(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //CallServer.getInstance().add(this, 100, SENDSMSRequest, this, false, true);
        CallServer.getInstance().add(this, 100, SENDSMSRequest, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Gson gson = new Gson();
                Msg msg = gson.fromJson(response.get(), Msg.class);
                if (msg.getResult() == 1) {
                    for (int i = 0;i<msg.getData().size();i++)
                    {
                        NotificationUtils notificationUtils = new NotificationUtils(getApplication());
                        notificationUtils.sendNotification(msg.getData().get(i).getW_type(), msg.getData().get(i).getW_message());
                    }
                }else
                {
                    ToastUtil.show(getApplicationContext(),msg.getMsg());
                }
            }
            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
            }
        }, false, false);
    }

//    public void setHeader() {
//        mBackImage = findViewById(R.id.back);
//        mTitle = findViewById(R.id.tv_title);
//        mRight = findViewById(R.id.tv_right);
//        mBackImage.setOnClickListener(this);
//    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.back:
//                finish();
//                break;
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: "+this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: "+this.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: "+this.getClass().getSimpleName());
        AppManager.getInstance().finishActivity(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: "+this.getClass().getSimpleName());
        return super.dispatchTouchEvent(ev);
    }

    protected void openActivity(Class<?> mClass) {
        Log.d(TAG, "openActivity: open "+mClass.getSimpleName());
        openActivity(mClass,null);
    }

    protected void openActivity(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(this,mClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivity with bundle: open "+mClass.getSimpleName());
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    protected void openActivityWithoutAnim(Class<?> mClass) {
        Log.d(TAG, "openActivityWithoutAnim: "+mClass.getSimpleName());
        openActivityWithoutAnim(mClass,null);
    }

    protected void openActivityWithoutAnim(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(this,mClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivityWithoutAnim with bundle: "+mClass.getSimpleName());
        startActivity(intent);
    }

    protected void openActivity(String action) {
        openActivity(action,null);
    }

    protected void openActivity(String action, Bundle bundle) {
        Intent intent = new Intent(action);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        Log.d(TAG, "openActivity by action: action----"+action);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish: "+this.getClass().getSimpleName());
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    public SearchPupItenAdapter<ProdNoModels.Bean> adapter = null;
    public void SetProdNo(AutoCompleteTextView autoCompleteTextView,int range)
    {
        adapter = new SearchPupItenAdapter<com.zitian.csims.model.ProdNoModels.Bean>(BaseActivity.this, android.R.layout.simple_list_item_1, CSIMSApplication.getAppContext().getProdNo().getData());
        autoCompleteTextView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

//        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.AppProdNo, RequestMethod.POST);
//        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
//        SENDSMSRequest.add("sEcho", "1");
//        //CallServer.getInstance().add(this, 100, SENDSMSRequest, this, false, true);
//        CallServer.getInstance().add(this, 100, SENDSMSRequest, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Gson gson = new Gson();
//                ProdNoModels prodNoModels = gson.fromJson(response.get(), ProdNoModels.class);
//                if (prodNoModels.getResult() == 1) {
//                    SearchPupItenAdapter<ProdNoModels.Bean> adapter = new SearchPupItenAdapter<ProdNoModels.Bean>(getApplicationContext(),android.R.layout.simple_list_item_1, prodNoModels.getData());
//                    autoCompleteTextView.setAdapter(adapter);
//                }else
//                {
//                    ToastUtil.show(getApplicationContext(),prodNoModels.getMsg());
//                }
//            }
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
//            }
//        }, false, true);
//        if(adapter==null){
//            List<ProdNoModels.Bean> list = new ArrayList<>();
//            adapter = new SearchPupItenAdapter<ProdNoModels.Bean>(BaseActivity.this, android.R.layout.simple_list_item_1, list);
//        }

//        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//            //Request<String> SENDSMSRequest = null;
//            @Override
//            public void afterTextChanged(Editable s) {
//                String str = autoCompleteTextView.getText().toString().trim();
//                if(str.length()>1)
//                {

//                    Request<String> SENDSMSRequest  = NoHttp.createStringRequest(AppConstant.AppProdNo(), RequestMethod.POST);
//                    SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
//                    SENDSMSRequest.add("sEcho", "1");
//                    SENDSMSRequest.add("searchKey", str);
//                    SENDSMSRequest.add("range", range);
//                    CallServer.getInstance().add(BaseActivity.this, 100, SENDSMSRequest, new HttpListener<String>() {
//                        @Override
//                        public void onSucceed(int what, Response<String> response) {
//                            Gson gson = new Gson();
//                            ProdNoModels prodNoModels = gson.fromJson(response.get(), ProdNoModels.class);
//                            if (prodNoModels.getResult() == 1) {
//                                adapter = new SearchPupItenAdapter<com.zitian.csims.model.ProdNoModels.Bean>(BaseActivity.this, android.R.layout.simple_list_item_1, prodNoModels.getData());
//                                autoCompleteTextView.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                            }else
//                            {
//
//                            }
//                        }
//                        @Override
//                        public void onFailed(int what, Response<String> response) {
//                            ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
//                        }
//                    }, true, false);
//                }else
//                {
//                    if(adapter==null)
//                    {
//                        List<ProdNoModels.Bean> list = new ArrayList<>();
//                        adapter = new SearchPupItenAdapter<ProdNoModels.Bean>(BaseActivity.this, android.R.layout.simple_list_item_1,list);
//                    }
//                    //if(adapter2!=null)
//                    adapter.clear();
//                }
//            }
//        } );

    }

    SearchPupItenAdapter2<AreaNoModels.Bean> adapter2 = null;
    public void SetAreaNo(AutoCompleteTextView autoCompleteTextView,String key)
    {
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            //Request<String> SENDSMSRequest = null;
            @Override
            public void afterTextChanged(Editable s) {
                String str = autoCompleteTextView.getText().toString().trim();
                if(str.length()>2)
                {
                    Request<String> SENDSMSRequest  = NoHttp.createStringRequest(AppConstant.AppAreaNo(), RequestMethod.POST);
                    SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
                    SENDSMSRequest.add("sEcho", "1");
                    SENDSMSRequest.add("searchKey", str);
                    SENDSMSRequest.add("area", key);

                    CallServer.getInstance().add(BaseActivity.this, 100, SENDSMSRequest, new HttpListener<String>() {
                        @Override
                        public void onSucceed(int what, Response<String> response) {
                            Gson gson = new Gson();
                            AreaNoModels areaNoModels = gson.fromJson(response.get(), AreaNoModels.class);
                            if (areaNoModels.getResult() == 1) {
                                adapter2 = new SearchPupItenAdapter2<com.zitian.csims.model.AreaNoModels.Bean>(BaseActivity.this, android.R.layout.simple_list_item_1, areaNoModels.getData());
                                //adapter.clear();
                                autoCompleteTextView.setAdapter(adapter2);
                                adapter2.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onFailed(int what, Response<String> response) {
                            ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
                        }
                    }, true, false);
                }else
                {
                    if(adapter2==null)
                    {
                        List<AreaNoModels.Bean> list = new ArrayList<>();
                        adapter2 = new SearchPupItenAdapter2<AreaNoModels.Bean>(BaseActivity.this, android.R.layout.simple_list_item_1,list);
                    }
                    adapter2.clear();
                    //if(adapter2!=null)
                    //    adapter2.clear();
                }
            }
        } );
    }


    public void SetFactory(Spinner spinner)
    {
        //this.spinner = spinner;
        //SearchPupItenAdapter<String> spinnerAdapter = new SearchPupItenAdapter<>(getApplication(),android.R.layout.simple_spinner_item, AppFactory.COUNTRIES);
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(spinnerAdapter);
        //Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.AppFactory, RequestMethod.POST);
        //SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        //SENDSMSRequest.add("sEcho", "1");
        //CallServer.getInstance().add(this, 102, SENDSMSRequest, this, false, true);

//        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.AppFactory(), RequestMethod.POST);
//        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
//        SENDSMSRequest.add("sEcho", "1");
//        //CallServer.getInstance().add(this, 100, SENDSMSRequest, this, false, true);
//        CallServer.getInstance().add(this, 100, SENDSMSRequest, new HttpListener<String>() {
//            @Override
//            public void onSucceed(int what, Response<String> response) {
//                Gson gson = new Gson();
//                FactoryModels factoryModels = gson.fromJson(response.get(), FactoryModels.class);
//                if (factoryModels.getResult() == 1) {
//                    SearchPupItenAdapter<FactoryModels.Bean> spinnerAdapter = new SearchPupItenAdapter<>(getApplication(),R.layout.simple_spinner_item, factoryModels.getData());
//                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner.setAdapter(spinnerAdapter);
//                }else
//                {
//                    ToastUtil.show(getApplicationContext(),factoryModels.getMsg());
//                }
//            }
//            @Override
//            public void onFailed(int what, Response<String> response) {
//                ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
//            }
//        }, false, true);

        SearchPupItenAdapter<FactoryModels.Bean> spinnerAdapter = new SearchPupItenAdapter<>(getApplication(),R.layout.simple_spinner_item, CSIMSApplication.getAppContext().getFactory().getData());
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

    }

    public void Alert(String title,String msg,String btntxt)
    {
        CustomDialogStyle.Builder builder = new CustomDialogStyle.Builder(BaseActivity.this);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositionButton(btntxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public String ConvertToProdNo(String mProdNo)
    {
//        String s = "";
//        if(mProdNo.length() == 6)
//        {
//            String s1 = mProdNo.toString().substring(0,2);
//            String s2 = mProdNo.toString().substring(2,4);
//            String s3 = mProdNo.toString().substring(4,6);
//            s = "T-" + s1 + "-" +s2 + "-" + s3;
//        }else
//        {
//            s = mProdNo.toString();
//        }
        if(mProdNo.indexOf("@")!=-1)
            return  mProdNo.split("@")[0].trim();

        return mProdNo;
    }

    public String ConvertToAreaNo (String mAreaNo)
    {
//        String s = "";
//        if(mAreaNo.length() == 5)
//        {
//            String s1 = mAreaNo.toString().substring(0,2);
//            String s2 = mAreaNo.toString().substring(2,4);
//            String s3 = mAreaNo.toString().substring(4,5);
//            s =  s1 + "-" +s2 + "-" + s3;
//        }else if(mAreaNo.length() == 6)
//        {
//            String s1 = mAreaNo.toString().substring(0,3);
//            String s2 = mAreaNo.toString().substring(3,5);
//            String s3 = mAreaNo.toString().substring(5,6);
//            s =  s1 + "-" +s2 + "-" + s3;
//        }else
//        {
//            s = mAreaNo.toString();
//        }
        if(mAreaNo.indexOf("@")!=-1)
            return  mAreaNo.split("@")[0].trim();

        return  mAreaNo;
    }

    public String ConvertToAreaNo2(String mAreaNo)
    {
//        String s = "";
//        if(mAreaNo.length() == 5)
//        {
//            String s1 = mAreaNo.toString().substring(0,2);
//            String s2 = mAreaNo.toString().substring(2,4);
//            String s3 = mAreaNo.toString().substring(4,5);
//            s =  s1 + "-" +s2 + "-" + s3;
//        }else if(mAreaNo.length() == 6)
//        {
//            String s1 = mAreaNo.toString().substring(0,3);
//            String s2 = mAreaNo.toString().substring(3,5);
//            String s3 = mAreaNo.toString().substring(5,6);
//            s =  s1 + "-" +s2 + "-" + s3;
//        }else
//        {
//            s = mAreaNo.toString();
//        }
        if(mAreaNo.indexOf("@")!=-1)
        {
            if(mAreaNo.split("@").length==3)
                return  mAreaNo.split("@")[2].trim();
        }

        return  mAreaNo;
    }

    public void IsStatusInventory(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.Inventory4(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        //CallServer.getInstance().add(this, 100, SENDSMSRequest, this, false, true);
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                Gson gson = new Gson();
                TaskCount taskCount = gson.fromJson(response.get(), TaskCount.class);
                if (taskCount.getResult() == 0) {
                    CustomDialogStyle.Builder builder = new CustomDialogStyle.Builder(BaseActivity.this);
                    builder.setTitle("提示")
                            .setMessage("当前盘点状态未开始，请先封库。")
                            .setPositionButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .create()
                            .show();
                }else
                {
                }
            }
            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtil.show(getApplicationContext(),"访问失败"+response.getException().getMessage());
            }
        }, false, false);
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


}