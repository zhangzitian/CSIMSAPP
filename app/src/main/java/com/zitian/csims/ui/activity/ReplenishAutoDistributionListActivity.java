package com.zitian.csims.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.WeakHandler;
import com.google.gson.Gson;
import com.king.zxing.CaptureActivity;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.ReplenishAutoDistributionList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.OutofSpaceForkliftListAdapter;
import com.zitian.csims.ui.adapter.ReplenishAutoDistributionListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ReplenishAutoDistributionListActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private AutoCompleteTextView search_edit;
    private int mDisplayLength = 10;
    private boolean mFirst = true;
    private ImageView search_image;
    private ImageView search_image2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replenish_auto_distribution_list);
        mCurrentPage = 0;
        initView();
        setViewListener();
        DataBind(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("【补货】-自动补货列表");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        search_image = (ImageView) findViewById(R.id.search_image);
        search_image2 = (ImageView) findViewById(R.id.search_image2);
        search_edit = (AutoCompleteTextView) findViewById(R.id.search_edit);
        SetProdNo(search_edit,1);
        search_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                search_edit.setText(s);
                search_edit.setSelection(s.length());//set cursor to the end
                mFirst = false;

                mCurrentPage = 0;
                mDataAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();

                DataBind(true);
            }
        });

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        mDataAdapter = new ReplenishAutoDistributionListAdapter(this);
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        // 设置item及item中控件的点击事件
        mDataAdapter.setOnItemClickListener(MyItemClickListener);

        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.split)
                .build();

        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        //add a HeaderView
        //final View header = LayoutInflater.from(this).inflate(R.layout.sample_header,(ViewGroup)findViewById(android.R.id.content), false);
        //mLRecyclerViewAdapter.addHeaderView(header);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 0;
                mDataAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();//fix bug:crapped or attached views may not be recycled. isScrap:false isAttached:true
                requestData();
            }
        });

        //是否禁用自动加载更多功能,false为禁用, 默认开启自动加载更多功能
        mRecyclerView.setLoadMoreEnabled(true);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mDataAdapter.getItemCount() < TOTAL_COUNTER) {
                    // loading more
                    requestData();
                } else {
                    //the end
                    mRecyclerView.setNoMore(true);
                }
            }
        });
        mRecyclerView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {
            }
            @Override
            public void onScrollDown() {
            }
            @Override
            public void onScrolled(int distanceX, int distanceY) {
            }
            @Override
            public void onScrollStateChanged(int state) {
            }
        });

        //设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.title_back_color, R.color.dark ,android.R.color.white);
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.title_back_color, R.color.dark ,android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中","我是有底线的","网络不给力啊，点击再试一次吧");
        mRecyclerView.refresh();
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mDataAdapter.getDataList().size() > position) {
                    ReplenishAutoDistributionList.Bean item = mDataAdapter.getDataList().get(position);
                    //AppToast.showShortText(getApplicationContext(), item.title);
                    //mDataAdapter.remove(position);
                }
            }
        });
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                ReplenishAutoDistributionList.Bean item = mDataAdapter.getDataList().get(position);
                //AppToast.showShortText(getApplicationContext(), "onItemLongClick - " + item.title);
            }
        });
    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        search_image.setOnClickListener(this);
        search_image2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_image:
                mCurrentPage = 0;
                mDataAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();
                DataBind(true);
                break;
            case R.id.search_image2:
                //startActivityForResult(new Intent(WarehousingInDistributionQualityInputActivity.this, CaptureActivity.class),requestCode);
                this.cls = CaptureActivity.class;
                this.title = "默认扫码";
                checkCameraPermissions();
                //跳转的默认扫码界面
                //startActivityForResult(new Intent(ReplenishAutoDistributionListActivity.this, CaptureActivity.class),requestCode);
                break;
        }
    }

    private static final String TAG = "zhangjian";
    /**服务器端一共多少条数据*/
    private static  int TOTAL_COUNTER = 10000;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断
    /**每一页展示多少条数据*/
    private static  int REQUEST_COUNT = 10;
    /**已经获取到多少条数据了*/
    private static int mCurrentPage =0;

    private LRecyclerView mRecyclerView = null;
    private ReplenishAutoDistributionListAdapter mDataAdapter = null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    //WeakHandler必须是Activity的一个实例变量.原因详见：http://dk-exp.com/2015/11/11/weak-handler/
    private WeakHandler mHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    DataBind(false);
                    break;
                case -3:
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                    mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            requestData();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    private void DataBind(boolean isLoading)
    {
        String condition =  "(t_taskType='补货任务' or t_taskType='手动补货') and t_taskState!='已完成' and t_taskState!='已取消'  ";
        if(mFirst)
        {
            mDisplayLength = 10;
            condition += " and [t_sponsor] =  '"+CSIMSApplication.getAppContext().getUser().getO_id()+"'   order by t_ID asc";
        }else
        {
            mDisplayLength = 1;
            String prodNo = search_edit.getText().toString();
            condition += " and [t_prodNo] =  '" + prodNo + "'   order by t_ID asc";
        }
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ReplenishAutoDistributionList(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        //SENDSMSRequest.add("prodNo",search_edit.getText().toString());
        SENDSMSRequest.add("condition",condition);
        SENDSMSRequest.add("iDisplayStart",mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength", mDisplayLength);
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
    }

    private void DataBind(int t_ID,boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ReplenishAutoDistributionList2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID", t_ID);
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(int id,boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.ReplenishAutoDistributionList3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("ID", id);
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<ReplenishAutoDistributionList.Bean> list) {
        mDataAdapter.addAll(list);
    }

    /**
     * 模拟请求网络
     */
    private void requestData() {
        Log.d(TAG, "requestData");
        new Thread() {

            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //模拟一下网络请求失败的情况
                if(NetworkUtils.isNetAvailable(getApplicationContext())) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);
                }
            }
        }.start();
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        Gson gson = new Gson();
        switch (what) {
            case 1000://快速登录
                ReplenishAutoDistributionList outofSpaceForkliftList = gson.fromJson(response.get(), ReplenishAutoDistributionList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    if (outofSpaceForkliftList.getData() != null) {
                        //itemModel.setT_taskState("未领取");
                        //mDataAdapter.getDataList().set(pos,itemModel);
                        //mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false,pos) , "jdsjlzx");
                        addItems(outofSpaceForkliftList.getData());
                        TOTAL_COUNTER = outofSpaceForkliftList.getTotal();
                        mRecyclerView.refreshComplete(REQUEST_COUNT);
                    }
                }else
                {
                    Toast.makeText(ReplenishAutoDistributionListActivity.this,outofSpaceForkliftList.getMsg(),Toast.LENGTH_SHORT).show();
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                }
                break;
            case 1001://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), ReplenishAutoDistributionList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    itemModel.setT_taskState("未领取");
                    mDataAdapter.getDataList().set(pos,itemModel);
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false,pos) , "jdsjlzx");
                    Toast.makeText(ReplenishAutoDistributionListActivity.this,"已发送补货任务！",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(ReplenishAutoDistributionListActivity.this,outofSpaceForkliftList.getMsg(),Toast.LENGTH_SHORT).show();
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                }
                break;
            case 1002://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), ReplenishAutoDistributionList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    itemModel.setT_taskState("已完成");
                    mDataAdapter.getDataList().set(pos,itemModel);
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false,pos) , "jdsjlzx");
                    Toast.makeText(ReplenishAutoDistributionListActivity.this,"完成了任务！",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(ReplenishAutoDistributionListActivity.this,outofSpaceForkliftList.getMsg(),Toast.LENGTH_SHORT).show();
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

    ReplenishAutoDistributionList.Bean  itemModel = null;
    int pos= 0;

    /**
     * item＋item里的控件点击监听事件
     */
    private ReplenishAutoDistributionListAdapter.OnItemClickListener MyItemClickListener = new ReplenishAutoDistributionListAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, ReplenishAutoDistributionListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()){
                case R.id.btnGain:
                    //openActivity(OutofSpaceForkliftErrorActivity.class);
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    itemModel = mDataAdapter.getDataList().get(position);
                    pos = position;

                    DataBind(itemModel.getT_ID(),true);
                    break;
                case R.id.btnOperation:
                    itemModel = mDataAdapter.getDataList().get(position);
                    pos = position;

                    DataBind2(itemModel.getT_ID(),true);
                    break;
                default:
                    // Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {
        }
    };

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
                search_edit.setText(result);

                mCurrentPage = 0;
                mDataAdapter.clear();
                mLRecyclerViewAdapter.notifyDataSetChanged();

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