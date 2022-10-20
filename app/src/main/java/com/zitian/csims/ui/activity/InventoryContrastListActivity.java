package com.zitian.csims.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.BookrevisionManagerList;
import com.zitian.csims.model.InventoryContrastList;
import com.zitian.csims.model.InventoryEmpty;
import com.zitian.csims.model.ManualShelvesList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.BookrevisionManagerListAdapter;
import com.zitian.csims.ui.adapter.InventoryContrastListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle3;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class InventoryContrastListActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Spinner sp;
    private Spinner sp2;
    private Button bt01;
    private Button bt02;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_contrast_list);

        mCurrentPage = 0;
        initView();
        setViewListener();
        //DataBind(true);
        IsStatusInventory(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("对比");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        sp = (Spinner) findViewById(R.id.sp);
        sp2 = (Spinner) findViewById(R.id.sp2);

        bt01 = (Button) findViewById(R.id.bt01);
        bt02 = (Button) findViewById(R.id.bt02);

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        mDataAdapter = new InventoryContrastListAdapter(this);
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
                    InventoryContrastList.Bean item = mDataAdapter.getDataList().get(position);
                    //AppToast.showShortText(getApplicationContext(), item.title);
                    //mDataAdapter.remove(position);
                }
            }
        });
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                InventoryContrastList.Bean item = mDataAdapter.getDataList().get(position);
                //AppToast.showShortText(getApplicationContext(), "onItemLongClick - " + item.title);
            }
        });

        String[]  spinnerItems = new String[]{ "选择区域", "配货区整件整包区", "配货区散书区","备货区"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(), R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinnerAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                if(spinnerItems[pos].contains("整件整包区"))
                {
                    DataBind(true);
                }else  if(spinnerItems[pos].contains("散书区"))
                {
                    DataBind2(true);
                }else  if(spinnerItems[pos].contains("备货区"))
                {
                    DataBind3(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        String[]  spinnerItems2 = new String[]{ "列号"};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(getApplication(),  R.layout.simple_spinner_item, spinnerItems2);
        spinnerAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(spinnerAdapter2);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                if(!sp2.getSelectedItem().toString().contains("列号"))
                {
                    if(sp.getSelectedItem().toString().contains("整件整包区"))
                    {
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        DataBindInventoryManualWhole(true);
                    }else  if(sp.getSelectedItem().toString().contains("散书区"))
                    {
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        DataBindInventoryManualScattered(true);
                    }else  if(sp.getSelectedItem().toString().contains("备货区"))
                    {
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        DataBindInventoryEmpty(true);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setViewListener()
    {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                if(sp.getSelectedItem().toString().contains("选择区域"))
                {
                    ToastUtil.show(this, "请选择区域" );
                }else if(sp2.getSelectedItem().toString().contains("列号"))
                {
                    ToastUtil.show(this, "请选择列号");
                }else
                {
                    if(sp.getSelectedItem().toString().contains("整件整包"))
                    {
                        RowNameLockManualWhole(true);
                    }else if(sp.getSelectedItem().toString().contains("散书")) {
                        RowNameLockManualScattered(true);
                    }else if(sp.getSelectedItem().toString().contains("备货")) {
                        RowNameLockEmpty(true);
                    }
                }
                break;
            case R.id.bt02:
                if(sp.getSelectedItem().toString().contains("散书区") && !sp2.getSelectedItem().toString().contains("列号") )
                {
                    Intent intent = new Intent(InventoryContrastListActivity.this,InventoryContrastManualAddItemListActivity.class);
                    intent.putExtra("wareHouseNo",sp2.getSelectedItem().toString());
                    startActivityForResult(intent,0);
                }else
                {
                    ToastUtil.show(this,"请选择散书区和列号");
                }
                break;
        }
    }

    private static final String TAG = "zhangjian";
    /**服务器端一共多少条数据*/
    private static  int TOTAL_COUNTER = 0;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断
    /**每一页展示多少条数据*/
    private static  int REQUEST_COUNT = 10;
    /**已经获取到多少条数据了*/
    private static int mCurrentPage =0;

    private LRecyclerView mRecyclerView = null;
    private InventoryContrastListAdapter mDataAdapter = null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    //WeakHandler必须是Activity的一个实例变量.原因详见：http://dk-exp.com/2015/11/11/weak-handler/
    private WeakHandler mHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    //DataBind(false);
                    if(sp.getSelectedItem().toString().contains("整件整包区"))
                    {
                        DataBindInventoryManualWhole(true);
                    }else  if(sp.getSelectedItem().toString().contains("散书区"))
                    {
                        DataBindInventoryManualScattered(true);
                    }else  if(sp.getSelectedItem().toString().contains("备货区"))
                    {
                        DataBindInventoryEmpty(true);
                    }
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
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryContrastList(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryContrastList2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind3(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryContrastList3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void InventoryContrastList7(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryContrastList7(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString());
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1003, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBindInventoryManualWhole(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryContrastList4(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString());
        SENDSMSRequest.add("iDisplayStart",mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength",REQUEST_COUNT);
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);

        mCurrentPage++;
    }

    private void DataBindInventoryManualScattered(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryContrastList5(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString());
        SENDSMSRequest.add("iDisplayStart",mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength", REQUEST_COUNT);
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);

        mCurrentPage++;
    }

    private void DataBindInventoryEmpty(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryContrastList6(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString());
        SENDSMSRequest.add("iDisplayStart",mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength", REQUEST_COUNT);
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);

        mCurrentPage++;
    }

    //查询
    private void RowNameLockManualWhole(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual9(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    private void RowNameLockManualScattered(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual10(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    private void RowNameLockEmpty(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual11(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    private void RowNameLockNuclearDisk(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual12(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<InventoryContrastList.Bean> list) {
        mDataAdapter.addAll(list);
        //mCurrentCounter += list.size();
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
                InventoryEmpty outofSpaceForkliftList = gson.fromJson(response.get(), InventoryEmpty.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    if (outofSpaceForkliftList.getData() != null) {
                        List<InventoryEmpty.Bean> list = outofSpaceForkliftList.getData();
                        InventoryEmpty.Bean b = new InventoryEmpty.Bean();
                        b.setI_rowName("列号");
                        list.add(0,b);
                        ArrayAdapter<InventoryEmpty.Bean> spinnerAdapter = new ArrayAdapter<InventoryEmpty.Bean>(getApplication(),   R.layout.simple_spinner_item, list);
                        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        sp2.setAdapter(spinnerAdapter);
                        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                                if(!sp2.getSelectedItem().toString().contains("列号"))
                                {
                                    if(sp.getSelectedItem().toString().contains("整件整包区"))
                                    {
                                        mCurrentPage = 0;
                                        mDataAdapter.clear();
                                        mLRecyclerViewAdapter.notifyDataSetChanged();
                                        DataBindInventoryManualWhole(true);
                                    }else  if(sp.getSelectedItem().toString().contains("散书区"))
                                    {
                                        mCurrentPage = 0;
                                        mDataAdapter.clear();
                                        mLRecyclerViewAdapter.notifyDataSetChanged();
                                        DataBindInventoryManualScattered(true);
                                    }
                                    else  if(sp.getSelectedItem().toString().contains("备货区"))
                                    {
                                        mCurrentPage = 0;
                                        mDataAdapter.clear();
                                        mLRecyclerViewAdapter.notifyDataSetChanged();
                                        DataBindInventoryEmpty(true);
                                    }
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                // Another interface callback
                            }
                        });
                    }
                }
                break;
            case 1001://快速登录
                InventoryContrastList inventoryContrastList = gson.fromJson(response.get(), InventoryContrastList.class);
                if (inventoryContrastList.getResult() == 1) {
                    addItems(inventoryContrastList.getData());
                    TOTAL_COUNTER = inventoryContrastList.getTotal();
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                }else if (inventoryContrastList.getResult() == 2)
                {
                    //ToastUtil.show(this,inventoryContrastList.getMsg());
                    sp2.setSelection(0,true);
                    Alert("提示",inventoryContrastList.getMsg(),"知道了");
                }else
                {
                    //ToastUtil.show(this,inventoryContrastList.getMsg());
                    Alert("提示",inventoryContrastList.getMsg(),"知道了");
                }
                //增项按钮是否可用
                if(sp.getSelectedItem().toString().contains("散书区") && !sp2.getSelectedItem().toString().contains("列号") )
                {
                    InventoryContrastList7(true);
                }
                break;
            case 1002://快速登录
                inventoryContrastList = gson.fromJson(response.get(), InventoryContrastList.class);
                if (inventoryContrastList.getResult() == 1) {
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    ToastUtil.show(this,inventoryContrastList.getMsg());
                    if(!sp2.getSelectedItem().toString().contains("列号"))
                    {
                        if(sp.getSelectedItem().toString().contains("整件整包区"))
                        {
                            mCurrentPage = 0;
                            mDataAdapter.clear();
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            DataBind(true);
                        }else  if(sp.getSelectedItem().toString().contains("散书区"))
                        {
                            mCurrentPage = 0;
                            mDataAdapter.clear();
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            DataBind2(true);
                        }else  if(sp.getSelectedItem().toString().contains("备货区"))
                        {
                            mCurrentPage = 0;
                            mDataAdapter.clear();
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            DataBind3(true);
                        }
                    }
                }else
                {
                    ToastUtil.show(this,inventoryContrastList.getMsg());
                }
                break;
            case 1003://快速登录
                inventoryContrastList = gson.fromJson(response.get(), InventoryContrastList.class);
                if (inventoryContrastList.getResult() == 1) {
                    bt02.setEnabled(false);
                }else
                {
                    bt02.setEnabled(true);//ToastUtil.show(this,inventoryContrastList.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

    InventoryContrastList.Bean itemModel = null;
    //int pos = 0;
    /**
     * item＋item里的控件点击监听事件
     */
    private InventoryContrastListAdapter.OnItemClickListener MyItemClickListener = new InventoryContrastListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, InventoryContrastListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.btnGain:
                    //DataBind2(itemModel.getWh_wareHouseNo(),0,true);
                    break;
                case R.id.btnOperation:
                    itemModel = mDataAdapter.getDataList().get(position);
                    //pos = position;
                    if(sp.getSelectedItem().toString().contains("整件整包区") && !sp2.getSelectedItem().toString().contains("列号"))
                    {
                        Intent intent = new Intent(InventoryContrastListActivity.this,InventoryContrastInputWholeActivity.class);
                        intent.putExtra("itemModel",itemModel);
                        startActivityForResult(intent,0);
                    }else  if(sp.getSelectedItem().toString().contains("散书区") && !sp2.getSelectedItem().toString().contains("列号"))
                    {
                        Intent intent = new Intent(InventoryContrastListActivity.this,InventoryContrastInputDisperseActivity.class);
                        intent.putExtra("itemModel",itemModel);
                        startActivityForResult(intent,0);
                    }else  if(sp.getSelectedItem().toString().contains("备货区") && !sp2.getSelectedItem().toString().contains("列号"))
                    {
                        Intent intent = new Intent(InventoryContrastListActivity.this,InventoryContrastEmptyActivity.class);
                        intent.putExtra("itemModel",itemModel);
                        startActivityForResult(intent,0);
                    }
                    break;
                default:
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {
        }
    };

    //结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && data!=null){
            String result_value = data.getStringExtra("result");
            if(itemModel!=null)
            {
                //if(itemModel.getWh_wareHouseNo().toString().equals(result_value))//移除当前项
                //    mDataAdapter.remove(pos);
                if(itemModel.getWh_wareHouseNo().toString().equals(result_value))
                {
                    //itemModel.setR_userid(CSIMSApplication.getAppContext().getUser().getO_id());
                    //mDataAdapter.getDataList().set(pos,itemModel);
                    //mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false,pos) , "jdsjlzx");
                    if(!sp2.getSelectedItem().toString().contains("列号"))
                    {
                        if(sp.getSelectedItem().toString().contains("整件整包区"))
                        {
                            mRecyclerView.setNoMore(false);
                            mCurrentPage = 0;
                            mDataAdapter.clear();
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            DataBindInventoryManualWhole(true);
                            //if(pos!=0)
                            //mDataAdapter.remove(pos);
                        }else  if(sp.getSelectedItem().toString().contains("散书区"))
                        {
                            mRecyclerView.setNoMore(false);
                            mCurrentPage = 0;
                            mDataAdapter.clear();
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            DataBindInventoryManualScattered(true);
                            //if(pos!=0)
                            //mDataAdapter.remove(pos);
                        }else  if(sp.getSelectedItem().toString().contains("备货区"))
                        {
                            mRecyclerView.setNoMore(false);
                            mCurrentPage = 0;
                            mDataAdapter.clear();
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            DataBindInventoryEmpty(true);
                            //if(pos!=0)
                            //mDataAdapter.remove(pos);
                        }
                        //pos = 0;
                    }
                }
            }
        }
    }

}