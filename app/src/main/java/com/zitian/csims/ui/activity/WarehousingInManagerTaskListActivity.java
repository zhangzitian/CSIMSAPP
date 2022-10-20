package com.zitian.csims.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.zitian.csims.model.WarehousingInForkliftList;
import com.zitian.csims.model.WarehousingInManagerTaskList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.WarehousingInManagerTaskListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class WarehousingInManagerTaskListActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Spinner search_status_edit;
    private AutoCompleteTextView search_edit;
    private ImageView search_image;

    private RelativeLayout all_no_data;
    private LRecyclerView list;
    private TextView tipMsg;

    final String[] spinnerItems = { "全部汇总","未领取", "已领取", "已暂停"};
    String spinnerItemsValue = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_in_manager_task_list);
        mCurrentPage = 0;
        initView();
        setViewListener();
        //DataBind(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("【入库】-详细任务列表");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        search_status_edit  = (Spinner) findViewById(R.id.search_status_edit);
        search_edit  = (AutoCompleteTextView) findViewById(R.id.search_edit);
        search_image  = (ImageView) findViewById(R.id.search_image);

        search_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                search_edit.setText(s);
                search_edit.setSelection(s.length());//set cursor to the end

                mCurrentPage = 0;
                mDataAdapter.clear();
                DataBind(true);
            }
        });

        all_no_data = (RelativeLayout) findViewById(R.id.all_no_data);
        list = (LRecyclerView) findViewById(R.id.list);
        tipMsg = (TextView)findViewById(R.id.tipMsg);

        SetProdNo(search_edit,1);

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        mDataAdapter = new WarehousingInManagerTaskListAdapter(this);
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
                    WarehousingInManagerTaskList.Bean item = mDataAdapter.getDataList().get(position);
                    //AppToast.showShortText(getApplicationContext(), item.title);
                    //mDataAdapter.remove(position);
                }
            }
        });
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                WarehousingInManagerTaskList.Bean item = mDataAdapter.getDataList().get(position);
                //AppToast.showShortText(getApplicationContext(), "onItemLongClick - " + item.title);
            }
        });

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplication(),
               R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        search_status_edit.setAdapter(spinnerAdapter);
        search_status_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //ToastUtil.show(getApplicationContext(), "选择了[" + spinnerItems[pos] + "]");
                spinnerItemsValue = spinnerItems[pos];
                mCurrentPage = 0;
                mDataAdapter.clear();
                DataBind(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void setViewListener() {
        icon_back.setOnClickListener(this);
        search_image.setOnClickListener(this);
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
                DataBind(true);
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
    private WarehousingInManagerTaskListAdapter mDataAdapter = null;
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
        String condition =  " ([t_taskType] = '入库任务' or  [t_taskType] = '入库上架任务' )  ";


        if(search_status_edit.getSelectedItem().toString().equals("全部汇总"))
        {
            condition += " and (t_taskState =  '未领取' or t_taskState =  '已领取' or t_taskState =  '已暂停')";
        }else
        {
            //if(!spinnerItemsValue.equals(""))
            condition += " and t_taskState =  '" + search_status_edit.getSelectedItem().toString() + "'";
        }

        if(!search_edit.getText().toString().trim().equals("")  )
        {
            String s = "";
            if(search_edit.getText().toString().length() == 6)
            {
                String s1 = search_edit.getText().toString().substring(0,2);
                String s2 = search_edit.getText().toString().substring(2,4);
                String s3 = search_edit.getText().toString().substring(4,6);
                s = "T-" + s1 + "-" +s2 + "-" + s3;
            }else
            {
                s = search_edit.getText().toString();
            }
            condition += " and [t_prodNo] =  '" + s + "'";
        }

        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInManagerTaskList(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("condition",condition);
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("iDisplayStart",mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength", "10");
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
        mCurrentPage++;
    }

    //取消
    private void DataBind2(int id,boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInManagerTaskList2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID", id);
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
        mCurrentPage++;
    }

    //恢复
    private void DataBind3(int id,boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInManagerTaskList3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID", id);
        SENDSMSRequest.add("t_taskType", "入库任务");
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
        mCurrentPage++;
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<WarehousingInManagerTaskList.Bean> list) {
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
                WarehousingInManagerTaskList outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInManagerTaskList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    if (outofSpaceForkliftList.getData() != null) {
                        addItems(outofSpaceForkliftList.getData());
                        TOTAL_COUNTER = outofSpaceForkliftList.getTotal();
                        mRecyclerView.refreshComplete(REQUEST_COUNT);
                        list.setVisibility(View.VISIBLE);
                        all_no_data.setVisibility(View.GONE);
                    }
                }else if(outofSpaceForkliftList.getResult() == 0 && mCurrentPage==1) {
                    //没有数据
                    mRecyclerView.refreshComplete(0);
                    notifyDataSetChanged();
                    list.setVisibility(View.GONE);
                    all_no_data.setVisibility(View.VISIBLE);
                    tipMsg.setText(outofSpaceForkliftList.getMsg());
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

    /**
     * item＋item里的控件点击监听事件
     */
    private WarehousingInManagerTaskListAdapter.OnItemClickListener MyItemClickListener = new WarehousingInManagerTaskListAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, WarehousingInManagerTaskListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.btnQuxiao:
                    WarehousingInManagerTaskList.Bean itemModel = mDataAdapter.getDataList().get(position);
                    itemModel.setT_taskState("取消");
                    mDataAdapter.getDataList().set(position, itemModel);
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    DataBind2(itemModel.getT_ID(),true);
                    mDataAdapter.remove(position);
                    Toast.makeText(WarehousingInManagerTaskListActivity.this,"【"+ itemModel.getT_prodNo() + "】取消任务成功！",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnOperation:
                    itemModel = mDataAdapter.getDataList().get(position);
                    itemModel.setT_taskState("未领取");
                    mDataAdapter.getDataList().set(position, itemModel);
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    mDataAdapter.remove(position);
                    DataBind3(itemModel.getT_ID(),true);//RestoreTaskJL
                    Toast.makeText(WarehousingInManagerTaskListActivity.this,"【"+ itemModel.getT_prodNo() + "】恢复任务成功！",Toast.LENGTH_SHORT).show();
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