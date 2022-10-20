package com.zitian.csims.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.zitian.csims.model.InventoryEmpty;
import com.zitian.csims.model.InventoryManualList;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.InventoryManualListAdapter;
import com.zitian.csims.ui.adapter.OutofSpaceForkliftListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle2;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class InventoryManualListActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Spinner sp;
    private Spinner sp2;
    private Spinner sp3;
    private Button bt01;
    private Button bt02;
    private Button bt03;
    private Button bt04;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_manual_list);
        mCurrentPage = 0;
        initView();
        setViewListener();
        IsStatusInventory(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("手工盘点");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        sp = (Spinner) findViewById(R.id.sp);
        sp2 = (Spinner) findViewById(R.id.sp2);
        sp3 = (Spinner) findViewById(R.id.sp3);
        bt01 = (Button) findViewById(R.id.bt01);
        bt02 = (Button) findViewById(R.id.bt02);
        bt03 = (Button) findViewById(R.id.bt03);
        bt04 = (Button) findViewById(R.id.bt04);

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        mDataAdapter = new InventoryManualListAdapter(this);
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
        mRecyclerView.setHeaderViewColor(R.color.title_back_color, R.color.dark, android.R.color.white);
        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.title_back_color, R.color.dark, android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "我是有底线的", "网络不给力啊，点击再试一次吧");
        mRecyclerView.refresh();
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mDataAdapter.getDataList().size() > position) {
                    InventoryManualList.Bean item = mDataAdapter.getDataList().get(position);
                    //AppToast.showShortText(getApplicationContext(), item.title);
                    //mDataAdapter.remove(position);
                }
            }
        });
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                InventoryManualList.Bean item = mDataAdapter.getDataList().get(position);
                //AppToast.showShortText(getApplicationContext(), "onItemLongClick - " + item.title);
            }
        });

        String[]  spinnerItems = new String[]{ "选择区域", "整件整包区", "散书区"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinnerAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                //ToastUtil.show(getApplicationContext(),"选择了["+spinnerItems[pos]+"]");
                if(sp.getSelectedItem().toString().contains("整件整包"))
                {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                    DataBind(true);
                }else  if(sp.getSelectedItem().toString().contains("散书"))
                {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                    DataBind2(true);
                }else
                {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                }
                if(sp.getSelectedItem().toString().contains("散书") && !sp2.getSelectedItem().toString().contains("列号"))
                {
                    bt02.setEnabled(true);
                    bt03.setEnabled(true);
                    bt04.setEnabled(true);
                }else
                {
                    bt02.setEnabled(false);
                    bt03.setEnabled(false);
                    bt04.setEnabled(false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        String[]  spinnerItems2 = new String[]{ "列号"};
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, spinnerItems2);
        spinnerAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(spinnerAdapter2);
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                if(!sp.getSelectedItem().toString().contains("选择区域") && !sp2.getSelectedItem().toString().contains("列号") && !sp3.getSelectedItem().toString().contains("排序"))
                {
                    if(sp.getSelectedItem().toString().contains("整件整包"))
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                        DataBind3(true);
                    }
                    if(sp.getSelectedItem().toString().contains("散书"))
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                        DataBind4(true);
                    }
                }else
                {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                }
                if(sp.getSelectedItem().toString().contains("散书") && !sp2.getSelectedItem().toString().contains("列号"))
                {
                    bt02.setEnabled(true);
                    bt03.setEnabled(true);
                    bt04.setEnabled(true);
                }else
                {
                    bt02.setEnabled(false);
                    bt03.setEnabled(false);
                    bt04.setEnabled(false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String[]  spinnerItems3 = new String[]{ "排序", "东到西", "西到东"};
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, spinnerItems3);
        spinnerAdapter3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(spinnerAdapter3);
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                //ToastUtil.show(getApplicationContext(),"选择了["+spinnerItems[pos]+"]");
                if(!sp.getSelectedItem().toString().contains("选择区域") && !sp2.getSelectedItem().toString().contains("列号") && !sp3.getSelectedItem().toString().contains("排序"))
                {
                    if(sp.getSelectedItem().toString().contains("整件整包"))
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                        DataBind3(true);
                    }
                    else if(sp.getSelectedItem().toString().contains("散书"))
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                        DataBind4(true);
                    }else
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                    }
                }else
                {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void setViewListener() {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
        bt03.setOnClickListener(this);
        bt04.setOnClickListener(this);
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
                }else if(sp3.getSelectedItem().toString().contains("排序"))
                {
                    ToastUtil.show(this, "请选择排序" );
                }else
                {
                    if(sp.getSelectedItem().toString().contains("整件整包"))
                    {
                        DataBind7(true);
                    }else if(sp.getSelectedItem().toString().contains("散书")) {
                        DataBind8(true);
                    }
                }
                break;
            case R.id.bt02://增项
                DataBind12(true);
                break;
            case R.id.bt03://减项
                DataBind10(true);
                break;
            case R.id.bt04://保存
                if(sp.getSelectedItem().toString().contains("选择区域"))
                {
                    ToastUtil.show(this, "请选择区域" );
                }else if(sp2.getSelectedItem().toString().contains("列号"))
                {
                    ToastUtil.show(this, "请选择列号");
                }else if(sp.getSelectedItem().toString().contains("整件整包"))
                {
                    DataBind15(true);
                }
                else if(sp.getSelectedItem().toString().contains("散书"))
                {
                    DataBind14(true);
                }
                break;
        }
    }

    private static final String TAG = "zhangjian";
    /**
     * 服务器端一共多少条数据
     */
    private static int TOTAL_COUNTER = 0;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断
    /**
     * 每一页展示多少条数据
     */
    private static int REQUEST_COUNT = 10;
    /**
     * 已经获取到多少条数据了
     */
    private static int mCurrentPage = 0;

    private LRecyclerView mRecyclerView = null;
    private InventoryManualListAdapter mDataAdapter = null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    //WeakHandler必须是Activity的一个实例变量.原因详见：http://dk-exp.com/2015/11/11/weak-handler/
    private WeakHandler mHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    //DataBind(false);
                    if(!sp.getSelectedItem().toString().contains("选择区域") && !sp2.getSelectedItem().toString().contains("列号") && !sp3.getSelectedItem().toString().contains("排序"))
                    {
                        if(sp.getSelectedItem().toString().contains("整件整包"))
                            DataBind3(true);
                        if(sp.getSelectedItem().toString().contains("散书"))
                            DataBind4(true);
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

    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind3(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo", sp2.getSelectedItem().toString());
        //东到西", "西到东
        String sort = sp3.getSelectedItem().toString().contains("东到西") ? "ASC" : "DESC";
        SENDSMSRequest.add("condition", " [wh_wareHouseNo] like '"+sp2.getSelectedItem().toString()+"-%' order by [wh_wareHouseNo] " + sort);
        SENDSMSRequest.add("iDisplayStart", mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength", REQUEST_COUNT);
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
        //if (mCurrentPage == 0) {
          //  mRecyclerView.setPullRefreshEnabled(true);
        //}
        mCurrentPage++;
    }

    private void DataBind4(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual4(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo", sp2.getSelectedItem().toString());
        //东到西", "西到东
        String sort = sp3.getSelectedItem().toString().contains("东到西") ? "ASC" : "DESC";
        SENDSMSRequest.add("condition", " [wh_wareHouseNo] like '"+sp2.getSelectedItem().toString()+"-%' order by [wh_wareHouseNo] " + sort);
        SENDSMSRequest.add("iDisplayStart", mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength", REQUEST_COUNT);
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
        //if (mCurrentPage == 0) {
        //   mRecyclerView.setPullRefreshEnabled(true);
        //}
        mCurrentPage++;
    }

    private void DataBind5(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual5(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind6(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual6(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    //点击开始盘点 检测整件整包
    private void DataBind7(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.CheckManualWholeLog(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1003, SENDSMSRequest, this, false, isLoading);
    }
    //点击开始盘点 检测散书
    private void DataBind8(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.CheckManualScatteredLog(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1004, SENDSMSRequest, this, false, isLoading);
    }

    //点击开始盘点 检测散书 减项
    private void DataBind10(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.CheckManualScatteredLog(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1005, SENDSMSRequest, this, false, isLoading);
    }
    //点击开始盘点 检测散书 增项
    private void DataBind12(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.CheckManualScatteredLog(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1007, SENDSMSRequest, this, false, isLoading);
    }
    //点击开始盘点 检测散书 设置
    private void DataBind11(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.CheckManualScatteredLog(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1006, SENDSMSRequest, this, false, isLoading);
    }
    //点击开始盘点 检测整件 设置
    private void DataBind16(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.CheckManualWholeLog(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("wareHouseNo",sp2.getSelectedItem().toString() );
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1010, SENDSMSRequest, this, false, isLoading);
    }
    //减项
    private void DataBind13(boolean isLoading)
    {
        if(list.size()>0)
        {
            String wareHouseNo = "";
            for (int i = 0;i<list.size();i++)
            {
                wareHouseNo += ("'" + list.get(i).getWh_wareHouseNo()+ "',");
            }
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryInputDisperse(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("wareHouseNo", wareHouseNo);
            SENDSMSRequest.add("sType", 3);
            SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
            SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
            CallServer.getInstance().add(this, 1008, SENDSMSRequest, this, false, isLoading);
        }else
        {
            ToastUtil.show(this, "请选择要减的数据");
        }
    }

    //结束整件
    private void DataBind15(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual7(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("wareHouseNo", sp2.getSelectedItem().toString());
        SENDSMSRequest.add("sEcho", "sEcho");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1009, SENDSMSRequest, this, false, isLoading);
    }
    //结束散书
    private void DataBind14(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryManual8(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("wareHouseNo", sp2.getSelectedItem().toString());
        SENDSMSRequest.add("sEcho", "sEcho");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        CallServer.getInstance().add(this, 1009, SENDSMSRequest, this, false, isLoading);
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<InventoryManualList.Bean> list) {
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
                if (NetworkUtils.isNetAvailable(getApplicationContext())) {
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
                        ArrayAdapter<InventoryEmpty.Bean> spinnerAdapter = new ArrayAdapter<InventoryEmpty.Bean>(getApplication(),R.layout.simple_spinner_item, list);
                        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        sp2.setAdapter(spinnerAdapter);
                    }
                }
                break;
            case 1001://快速登录
                InventoryManualList outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    if (outofSpaceForkliftList2.getData() != null) {
                        addItems(outofSpaceForkliftList2.getData());
                        TOTAL_COUNTER = outofSpaceForkliftList2.getTotal();
                        mRecyclerView.refreshComplete(REQUEST_COUNT);
                    } else {
                    }
                }
                break;
            case 1002://快速登录
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    if(sp.getSelectedItem().toString().contains("选择区域"))
                    {
                        ToastUtil.show(this, "请选择区域" );
                    }else if(sp2.getSelectedItem().toString().contains("列号"))
                    {
                        ToastUtil.show(this, "请选择列号");
                    }else if(sp3.getSelectedItem().toString().contains("排序"))
                    {
                        ToastUtil.show(this, "请选择排序" );
                    }else
                    {
                        if(sp.getSelectedItem().toString().contains("整件整包"))
                        {
                            Intent intent = new Intent(InventoryManualListActivity.this,InventoryInputWholeActivity.class);
                            intent.putExtra("wareHouseNo2",sp2.getSelectedItem().toString());
                            intent.putExtra("sortNo",sp3.getSelectedItem().toString());
                            startActivityForResult(intent,0);
                        }else if(sp.getSelectedItem().toString().contains("散书"))
                        {
                            Intent intent = new Intent(InventoryManualListActivity.this,InventoryInputDisperseActivity.class);
                            intent.putExtra("wareHouseNo2",sp2.getSelectedItem().toString());
                            intent.putExtra("sortNo",sp3.getSelectedItem().toString());
                            startActivityForResult(intent,0);
                        }else
                            ToastUtil.show(this, "请选择区域");
                    }
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
            case 1003://快速登录
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    DataBind5(true);
                }else if (outofSpaceForkliftList2.getResult() == 2) {
                    CustomDialogStyle2.Builder builder = new CustomDialogStyle2.Builder(InventoryManualListActivity.this);
                    builder.setTitle("温馨提示")
                            .setMessage("用户【"+CSIMSApplication.getAppContext().getUser().getO_name()+"】确定要选择【"+sp.getSelectedItem().toString()+"】列号【"+sp2.getSelectedItem().toString()+"】 开始盘点吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositionButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if(sp.getSelectedItem().toString().contains("整件整包"))
                                    {
                                        DataBind5(true);
                                    }else if(sp.getSelectedItem().toString().contains("散书")) {
                                        DataBind6(true);
                                    }
                                }
                            })
                            .create()
                            .show();
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
            case 1004://快速登录
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    DataBind6(true);
                }else if (outofSpaceForkliftList2.getResult() == 2) {
                    CustomDialogStyle2.Builder builder = new CustomDialogStyle2.Builder(InventoryManualListActivity.this);
                    builder.setTitle("温馨提示")
                            .setMessage("用户【"+CSIMSApplication.getAppContext().getUser().getO_name()+"】确定要选择【"+sp.getSelectedItem().toString()+"】列号【"+sp2.getSelectedItem().toString()+"】 开始盘点吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositionButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if(sp.getSelectedItem().toString().contains("整件整包"))
                                    {
                                        DataBind5(true);
                                    }else if(sp.getSelectedItem().toString().contains("散书")) {
                                        DataBind6(true);
                                    }
                                }
                            })
                            .create()
                            .show();
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
            case 1005://减项检测
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    DataBind13(true);
                }else if (outofSpaceForkliftList2.getResult() == 2) {
                    Alert("提示","请先点击开始盘点，才可以操作","我知道了");
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
            case 1006://设置
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    Intent intent = new Intent(InventoryManualListActivity.this,InventoryInputDisperseActivity.class);
                    intent.putExtra("sType",1);
                    intent.putExtra("wareHouseNo2",sp2.getSelectedItem().toString());
                    intent.putExtra("wareHouseNo",itemModel.getWh_wareHouseNo());
                    intent.putExtra("sortNo",sp3.getSelectedItem().toString());
                    startActivityForResult(intent,0);
                }else if (outofSpaceForkliftList2.getResult() == 2) {
                    Alert("提示","请先点击开始盘点，才可以操作","我知道了");
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
            case 1007://增项
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    Intent intent = new Intent(InventoryManualListActivity.this,InventoryManualAddItemListActivity.class);
                    intent.putExtra("wareHouseNo",sp2.getSelectedItem().toString());
                    intent.putExtra("sortNo",sp3.getSelectedItem().toString());
                    startActivityForResult(intent,0);
                }else if (outofSpaceForkliftList2.getResult() == 2) {
                    Alert("提示","请先点击开始盘点，才可以操作","我知道了");
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
            case 1008://减项执行
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    if(!sp.getSelectedItem().toString().contains("选择区域") && !sp2.getSelectedItem().toString().contains("列号") && !sp3.getSelectedItem().toString().contains("排序"))
                    {
                        if(sp.getSelectedItem().toString().contains("整件整包"))
                        {
                            mRecyclerView.setNoMore(false);
                            mCurrentPage = 0;
                            mDataAdapter.clear();
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete(0);
                            DataBind3(true);
                        }
                        if(sp.getSelectedItem().toString().contains("散书"))
                        {
                            mRecyclerView.setNoMore(false);
                            list.clear();
                            //ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                            mCurrentPage = 0;
                            mDataAdapter.clear();
                            mLRecyclerViewAdapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete(0);
                            DataBind4(true);
                        }
                    }else
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                    }
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
            case 1009://减项执行
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    if(sp.getSelectedItem().toString().contains("整件整包"))
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                        DataBind(true);
                    }else  if(sp.getSelectedItem().toString().contains("散书"))
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                        DataBind2(true);
                    }else
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                    }
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
            case 1010://设置
                outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryManualList.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    Intent intent = new Intent(InventoryManualListActivity.this,InventoryInputWholeActivity.class);
                    intent.putExtra("sType",1);
                    intent.putExtra("wareHouseNo2",sp2.getSelectedItem().toString());
                    intent.putExtra("wareHouseNo",itemModel.getWh_wareHouseNo());
                    intent.putExtra("sortNo",sp3.getSelectedItem().toString());
                    startActivityForResult(intent,0);
                }else if (outofSpaceForkliftList2.getResult() == 2) {
                    Alert("提示","请先点击开始盘点，才可以操作","我知道了");
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList2.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }
    List<InventoryManualList.Bean> list = new ArrayList<>();
    InventoryManualList.Bean itemModel;
    /**
     * item＋item里的控件点击监听事件
     */
    private InventoryManualListAdapter.OnItemClickListener MyItemClickListener = new InventoryManualListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, InventoryManualListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.mWareHouseNo:
                    itemModel = mDataAdapter.getDataList().get(position);
                    CheckBox checkBox = (CheckBox)v;
                    if(checkBox.isChecked())
                    {
                        if(!list.contains(itemModel))
                            list.add(itemModel);
                    }else
                    {
                        if(list.contains(itemModel))
                            list.remove(itemModel);
                    }
                    break;
                case R.id.btnOperation:
                    itemModel = mDataAdapter.getDataList().get(position);
                    if(itemModel.getR_error5().contains("Table_InventoryManualWhole"))
                    {
                        DataBind16(true);
                    }
                    if(itemModel.getR_error5().contains("Table_InventoryManualScattered"))
                    {
                        DataBind11(true);
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
        if(requestCode==0 && data!=null){ //是否刷新  结束就刷新
            String result_value = data.getStringExtra("result");
            //if(itemModel.getWh_wareHouseNo().toString().equals(result_value))//移除当前项
            //{
                if(!sp.getSelectedItem().toString().contains("选择区域") && !sp2.getSelectedItem().toString().contains("列号") && !sp3.getSelectedItem().toString().contains("排序"))
                {
                    if(sp.getSelectedItem().toString().contains("整件整包"))
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                        if(result_value.equals("List"))
                        {
                            DataBind3(true);
                        }else  if(result_value.equals("RowName"))
                        {
                            DataBind(true);
                        }
                    }
                    if(sp.getSelectedItem().toString().contains("散书"))
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(0);
                        DataBind4(true);
                    }
                }else
                {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                }
            //}
        }
    }

}