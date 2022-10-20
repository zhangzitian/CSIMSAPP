package com.zitian.csims.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.zitian.csims.model.InventoryErrorList;
import com.zitian.csims.model.OutofSpaceForkliftList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.InventoryErrorListAdapter;
import com.zitian.csims.ui.adapter.OutofSpaceForkliftListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class InventoryErrorListActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Spinner sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_error_list);

        mCurrentPage = 0;
        initView();
        setViewListener();
        IsStatusInventory(true);
        DataBind(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("任务");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        sp = (Spinner) findViewById(R.id.sp);
        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        mDataAdapter = new InventoryErrorListAdapter(this);
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
                    InventoryErrorList.Bean item = mDataAdapter.getDataList().get(position);
                    //AppToast.showShortText(getApplicationContext(), item.title);
                    //mDataAdapter.remove(position);
                }
            }
        });
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                InventoryErrorList.Bean item = mDataAdapter.getDataList().get(position);
                //AppToast.showShortText(getApplicationContext(), "onItemLongClick - " + item.title);
            }
        });

        //整件整包区
        //散书区
        //散书区-增项
        //数据合并
        //数据核盘

        //配货区满盘上架-整件整包区
        //配货区满盘上架-散书区
        //配货区满盘上架-散书区-增项
        //配货区满盘上架-数据合并
        //配货区满盘上架-数据核盘
        //String[]  spinnerItems = new String[]{ "全部任务", "整件整包区", "散书区", "散书区-增项","数据合并","数据核盘","配货区满盘上架-整件整包区", "配货区满盘上架-散书区", "配货区满盘上架-散书区-增项","配货区满盘上架-数据合并","配货区满盘上架-数据核盘"};
        String[]  spinnerItems = new String[]{ "全部任务", "整件整包区", "散书区", "数据合并","备货区","数据核盘"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinnerAdapter);
        sp.setSelection(0,true);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
                //ToastUtil.show(getApplicationContext(),"选择了["+spinnerItems[pos]+"]");
                if(sp.getSelectedItem().toString().contains("全部任务"))
                {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                    DataBind(true);
                }
                else
                {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                    DataBind(true);
                }
                //else if(sp.getSelectedItem().toString().contains("整件整包"))
                //{
                //    mCurrentPage = 0;
                //    mDataAdapter.clear();
                //    mLRecyclerViewAdapter.notifyDataSetChanged();
                //    mRecyclerView.refreshComplete(0);
                //    DataBind(true);
                //}
                // else  if(sp.getSelectedItem().toString().contains("散书"))
                //{
                //    mCurrentPage = 0;
                //    mDataAdapter.clear();
                //   mLRecyclerViewAdapter.notifyDataSetChanged();
                //    mRecyclerView.refreshComplete(0);
                //    DataBind(true);
                //}
                //else if(sp.getSelectedItem().toString().contains("空位"))
                //{
                //    mCurrentPage = 0;
                //    mDataAdapter.clear();
                //    mLRecyclerViewAdapter.notifyDataSetChanged();
                //    mRecyclerView.refreshComplete(0);
                //    DataBind(true);
                //}
                //else if(sp.getSelectedItem().toString().contains("数据核盘"))
                //{
                //    mCurrentPage = 0;
                //    mDataAdapter.clear();
                //    mLRecyclerViewAdapter.notifyDataSetChanged();
                //    mRecyclerView.refreshComplete(0);
                //    DataBind(true);
                //}
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
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
        }
    }

    private static final String TAG = "zhangjian";
    /**服务器端一共多少条数据*/
    private static  int TOTAL_COUNTER = 0;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断
    /**每一页展示多少条数据*/
    private static  int REQUEST_COUNT = 10;
    /**已经获取到多少条数据了*/
    private static int mCurrentPage = 0;

    private LRecyclerView mRecyclerView = null;
    private InventoryErrorListAdapter mDataAdapter = null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    //WeakHandler必须是Activity的一个实例变量.原因详见：http://dk-exp.com/2015/11/11/weak-handler/
    private WeakHandler mHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    //DataBind(false);
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

    //查询
    private void DataBind(boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryTaskHandler(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id());
        //SENDSMSRequest.add("TableName", "");
        if(!sp.getSelectedItem().toString().contains("全部"))
            SENDSMSRequest.add("condition", " t_taskType like '%" + sp.getSelectedItem().toString() + "%' and t_taskState != '已完成' ");
        else
            SENDSMSRequest.add("condition", " t_taskState!='已完成' ");

        SENDSMSRequest.add("iDisplayStart",mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength", REQUEST_COUNT);

        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        mCurrentPage++;
    }
    //领取
    private void DataBind2(int Id,boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryTaskHandler2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("Id", Id);
        //SENDSMSRequest.add("TableName", "1");
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }
    //完成
    private void DataBind3(int Id,boolean isLoading)
    {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryTaskHandler3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("Id", Id);
        //SENDSMSRequest.add("TableName", "1");
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<InventoryErrorList.Bean> list) {
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
                InventoryErrorList outofSpaceForkliftList = gson.fromJson(response.get(), InventoryErrorList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    addItems(outofSpaceForkliftList.getData());
                    TOTAL_COUNTER = outofSpaceForkliftList.getTotal();
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                }else
                {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                    Toast.makeText(InventoryErrorListActivity.this,outofSpaceForkliftList.getMsg(),Toast.LENGTH_SHORT).show();
                }
                break;
            case 1001://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), InventoryErrorList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    itemModel.setT_taskState("已领取");
                    mDataAdapter.getDataList().set(pos,itemModel);
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false,pos) , "jdsjlzx");
                }else
                {
                    Toast.makeText(InventoryErrorListActivity.this,outofSpaceForkliftList.getMsg(),Toast.LENGTH_SHORT).show();
                }
                break;
            case 1002://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), InventoryErrorList.class);
                if (outofSpaceForkliftList.getResult() == 1) {

                    //itemModel = mDataAdapter.getDataList().get(pos);
                    //itemModel.setT_taskState("已完成");
                    //mDataAdapter.getDataList().set(pos,itemModel);
                    //mDataAdapter.remove(pos);
                    //mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false,pos) , "jdsjlzx");
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete(0);
                    DataBind(true);
                }else
                {
                    Toast.makeText(InventoryErrorListActivity.this,outofSpaceForkliftList.getMsg(),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

    InventoryErrorList.Bean itemModel;
    int pos = 0;
    /**
     * item＋item里的控件点击监听事件
     */
    private InventoryErrorListAdapter.OnItemClickListener MyItemClickListener = new InventoryErrorListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, InventoryErrorListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()){
                case R.id.tv_fromNoAndArea:
                    itemModel = mDataAdapter.getDataList().get(position);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("itemModel",itemModel);
                    mBundle.putInt("mType",0);
                    openActivity(OutofSpaceForkliftErrorActivity.class,mBundle);
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_toNoAndArea:
                    itemModel = mDataAdapter.getDataList().get(position);
                    mBundle = new Bundle();
                    mBundle.putSerializable("itemModel",itemModel);
                    mBundle.putInt("mType",1);
                    openActivity(OutofSpaceForkliftErrorActivity.class,mBundle);
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnGain:
                    //openActivity(OutofSpaceForkliftErrorActivity.class);
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    itemModel = mDataAdapter.getDataList().get(position);
                    pos = position;
                    //itemModel.id = 100;
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"领取了任务！",Toast.LENGTH_SHORT).show();
                    DataBind2(itemModel.getT_ID(),true);
                    break;
                case R.id.btnOperation:
                    itemModel = mDataAdapter.getDataList().get(position);
                    pos = position;
                    //itemModel.id = 100;
                    DataBind3(itemModel.getT_ID(),true);
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

}