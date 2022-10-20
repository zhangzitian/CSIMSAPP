package com.zitian.csims.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
import com.zitian.csims.model.WarehousingErrorForkliftDistributionList;
import com.zitian.csims.model.WarehousingErrorManagerList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.WarehousingErrorForkliftDistributionListAdapter;
import com.zitian.csims.ui.adapter.WarehousingErrorManagerListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class WarehousingErrorForkliftDistributionListActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_error_forklift_distribution_list);
        mCurrentPage = 0;
        initView();
        setViewListener();
        DataBind(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("纠错列表");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        mDataAdapter = new WarehousingErrorForkliftDistributionListAdapter(this);
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
                    WarehousingErrorForkliftDistributionList.Bean item = mDataAdapter.getDataList().get(position);
                    //AppToast.showShortText(getApplicationContext(), item.title);
                    //mDataAdapter.remove(position);
                }
            }
        });
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                WarehousingErrorForkliftDistributionList.Bean item = mDataAdapter.getDataList().get(position);
                //AppToast.showShortText(getApplicationContext(), "onItemLongClick - " + item.title);
            }
        });

    }

    private void setViewListener() {
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
    private WarehousingErrorForkliftDistributionListAdapter mDataAdapter = null;
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


    private void DataBind(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingErrorForkliftDistributionListActivity(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("iDisplayStart",mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength", REQUEST_COUNT);
        SENDSMSRequest.add("condition", "e_state='未审核' and e_exception2='" + CSIMSApplication.getAppContext().getUser().getO_id() +"'");
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);

        mCurrentPage++;
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<WarehousingErrorForkliftDistributionList.Bean> list) {
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
                WarehousingErrorForkliftDistributionList outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingErrorForkliftDistributionList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    if (outofSpaceForkliftList.getData() != null) {
                        addItems(outofSpaceForkliftList.getData());
                        TOTAL_COUNTER = outofSpaceForkliftList.getTotal();
                        mRecyclerView.refreshComplete(REQUEST_COUNT);
                    }  else {
                    }
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

    //结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 ){
            mCurrentPage = 0;
            mDataAdapter.clear();
            DataBind(true);
        }
    }


    /**
     * item＋item里的控件点击监听事件
     */
    private WarehousingErrorForkliftDistributionListAdapter.OnItemClickListener MyItemClickListener = new WarehousingErrorForkliftDistributionListAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, WarehousingErrorForkliftDistributionListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.tv_fromNoAndArea:
                    WarehousingErrorForkliftDistributionList.Bean itemModel = mDataAdapter.getDataList().get(position);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("itemModel", itemModel);
                    mBundle.putInt("mType", 0);
                    openActivity(OutofSpaceForkliftErrorActivity.class, mBundle);
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_toNoAndArea:
                    itemModel = mDataAdapter.getDataList().get(position);
                    mBundle = new Bundle();
                    mBundle.putSerializable("itemModel", itemModel);
                    mBundle.putInt("mType", 1);
                    openActivity(OutofSpaceForkliftErrorActivity.class, mBundle);
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnGain:
                    //openActivity(OutofSpaceForkliftErrorActivity.class);
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    itemModel = mDataAdapter.getDataList().get(position);
                    //itemModel.id = 100;
                    //itemModel.setT_taskState("已领取");
                    mDataAdapter.getDataList().set(position, itemModel);
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"领取了任务！",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnOperation:
                    itemModel = mDataAdapter.getDataList().get(position);
                    if(itemModel.getE_errType().equals("库位为空") || itemModel.getE_errType().equals("库位被占用"))
                    {
                        Intent intent = new Intent(WarehousingErrorForkliftDistributionListActivity.this,WarehousingErrorForkliftDistributionAddActivity.class);
                        intent.putExtra("itemModel",itemModel);
                        intent.putExtra("sourceType",itemModel.getE_sourceType());
                        if(itemModel.getE_sourceType().contains("入库"))
                        {
                            intent.putExtra("spinnerItems",new String[]{"库位被占用"});
                            intent.putExtra("mType",0);
                        }else
                        {
                            intent.putExtra("spinnerItems",new String[]{"库位为空","库位被占用"});
                            intent.putExtra("mType",itemModel.getE_errType().equals("库位为空") ? 0:1);
                        }
                        intent.putExtra("isUpdate",1);
                        startActivityForResult(intent,0);
                    }else
                    {
                        //Intent intent = new Intent(WarehousingErrorForkliftDistributionListActivity.this,WarehousingErrorForkliftDistributionAdd2Activity.class);
                        //intent.putExtra("itemModel",itemModel);
                        //intent.putExtra("mType",mType);
                        //startActivityForResult(intent,0);

                        Intent intent = new Intent(WarehousingErrorForkliftDistributionListActivity.this,WarehousingErrorForkliftDistributionAdd2Activity.class);
                        intent.putExtra("itemModel",itemModel);
                        //intent.putExtra("sourceType",itemModel.getE_sourceType());
                        //intent.putExtra("mType",itemModel.getE_errType().equals("库位为空") ? 0:1);
                        intent.putExtra("isUpdate",1);
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


}