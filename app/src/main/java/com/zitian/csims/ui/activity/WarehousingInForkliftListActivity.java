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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.zitian.csims.model.ManualForkliftList;
import com.zitian.csims.model.WarehousingErrorForkliftDistributionList;
import com.zitian.csims.model.WarehousingInForkliftList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.WarehousingInForkliftListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class WarehousingInForkliftListActivity  extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private AutoCompleteTextView search_edit;
    private ImageView search_image;
    private ImageView search_image2;
    private TextView tvProName;

    private RelativeLayout all_no_data;
    private LRecyclerView list;
    private TextView tipMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehousing_in_forklift_list);
        mCurrentPage = 0;
        initView();
        setViewListener();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("【入库】-入库上架任务");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        search_edit = (AutoCompleteTextView) findViewById(R.id.actvProdNo);
        search_image = (ImageView) findViewById(R.id.search_image);
        search_image2 = (ImageView) findViewById(R.id.search_image2);
        tvProName = (TextView) findViewById(R.id.tvProName);

        all_no_data = (RelativeLayout) findViewById(R.id.all_no_data);
        list = (LRecyclerView) findViewById(R.id.list);
        tipMsg = (TextView)findViewById(R.id.tipMsg);

        SetProdNo(search_edit,1);

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

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        mDataAdapter = new WarehousingInForkliftListAdapter(this);
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
                    WarehousingInForkliftList.Bean item = mDataAdapter.getDataList().get(position);
                    //AppToast.showShortText(getApplicationContext(), item.title);
                    //mDataAdapter.remove(position);
                }
            }
        });
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                WarehousingInForkliftList.Bean item = mDataAdapter.getDataList().get(position);
                //AppToast.showShortText(getApplicationContext(), "onItemLongClick - " + item.title);
            }
        });

    }

    private void setViewListener() {
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
                DataBind(true);
                break;
            case R.id.search_image2:
                //startActivityForResult(new Intent(WarehousingInForkliftListActivity.this, CaptureActivity.class),requestCode);
                this.cls = CaptureActivity.class;
                this.title = "默认扫码";
                checkCameraPermissions();
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
        if (requestCode == REQUEST_CODE_SCAN && resultCode!=0) {
            String result =  data.getStringExtra("SCAN_RESULT");
            search_edit.setText(result);
            mCurrentPage = 0;
            mDataAdapter.clear();
            DataBind(true);
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
    private WarehousingInForkliftListAdapter mDataAdapter = null;
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


    //查询
    private void DataBind(boolean isLoading) {
        String condition =  "[t_taskType] = '入库上架任务'  and t_trayType = '满盘' ";
        condition += " and (t_taskState =  '未领取' or t_taskState =  '已领取') ";
        //[t_taskType] = '入库上架任务'   and (t_taskState =  '未领取' or t_taskState =  '已领取') and t_trayType = '满盘' and [t_prodNo] =  'T-15-02-04'

        if(!search_edit.getText().toString().equals("") )
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
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInForkliftList(), RequestMethod.POST);
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
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInForkliftList2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID", id);
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
        mCurrentPage++;
    }

    //领取
    private void DataBind3(int id,boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInForkliftList3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID", id);
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
        mCurrentPage++;
    }

    //上架完成
    private void DataBind4(WarehousingInForkliftList.Bean itemModel,boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInForkliftList4(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("t_ID",itemModel.getT_ID());
        SENDSMSRequest.add("t_fromNo", itemModel.getT_fromNo());
        SENDSMSRequest.add("t_toNo", itemModel.getT_toNo());
        SENDSMSRequest.add("t_count",itemModel.getT_count());
        SENDSMSRequest.add("t_nbox_num", itemModel.getT_nbox_num());
        SENDSMSRequest.add("t_npack_num", itemModel.getT_npack_num());
        SENDSMSRequest.add("t_nbook_num",  itemModel.getT_nbook_num());
        SENDSMSRequest.add("t_prodNo", itemModel.getT_prodNo());
        SENDSMSRequest.add("t_prodName",  itemModel.getT_prodName());
        SENDSMSRequest.add("userName", CSIMSApplication.getAppContext().getUser().getO_id());
        CallServer.getInstance().add(this, 1003, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
        mCurrentPage++;
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<WarehousingInForkliftList.Bean> list) {
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
                WarehousingInForkliftList outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    if (outofSpaceForkliftList.getData() != null) {
                        if(!search_edit.getText().toString().equals("")) {
                            tvProName.setText(outofSpaceForkliftList.getData().get(0).getT_prodName());
                        }
                        addItems(outofSpaceForkliftList.getData());
                        TOTAL_COUNTER = outofSpaceForkliftList.getTotal();
                        mRecyclerView.refreshComplete(REQUEST_COUNT);

                        list.setVisibility(View.VISIBLE);
                        all_no_data.setVisibility(View.GONE);
                    }
                }else if(mDataAdapter.getItemCount() == 0)
                {
                    //没有数据
                    mRecyclerView.refreshComplete(0);
                    notifyDataSetChanged();
                    list.setVisibility(View.GONE);
                    all_no_data.setVisibility(View.VISIBLE);
                    tipMsg.setText(outofSpaceForkliftList.getMsg());
                }
                break;
            case 1001://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    itemModel = mDataAdapter.getDataList().get(pos);
                    itemModel.setT_taskState("未领取");
                    mDataAdapter.getDataList().set(pos, itemModel);
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, pos), "jdsjlzx");
                    search_edit.setText("");
                    tvProName.setText("");
                    Toast.makeText(WarehousingInForkliftListActivity.this,"【"+ itemModel.getT_prodNo() + "】取消任务成功！",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(WarehousingInForkliftListActivity.this,outofSpaceForkliftList.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 1002://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    itemModel.setT_taskState("已领取");
                    mDataAdapter.getDataList().set(pos, itemModel);
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, pos), "jdsjlzx");
                    search_edit.setText(itemModel.getT_prodNo());
                    tvProName.setText(itemModel.getT_prodName());
                    Toast.makeText(WarehousingInForkliftListActivity.this,"【"+ itemModel.getT_prodNo() + "】领取任务成功！", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(WarehousingInForkliftListActivity.this,outofSpaceForkliftList.getMsg(), Toast.LENGTH_SHORT).show();
                    DataBind(true);
                }
                break;
            case 1003://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), WarehousingInForkliftList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    itemModel = mDataAdapter.getDataList().get(pos);
                    itemModel.setT_taskState("已完成");
                    mDataAdapter.getDataList().set(pos, itemModel);
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, pos), "jdsjlzx");
                    mDataAdapter.remove(pos);
                    search_edit.setText("");
                    tvProName.setText("");
                    Toast.makeText(WarehousingInForkliftListActivity.this,"【"+ itemModel.getT_prodNo() + "】完成任务成功！", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(WarehousingInForkliftListActivity.this,outofSpaceForkliftList.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

    WarehousingInForkliftList.Bean itemModel = null;
    int pos = 0;
    /**
     * item＋item里的控件点击监听事件
     */
    private WarehousingInForkliftListAdapter.OnItemClickListener MyItemClickListener = new WarehousingInForkliftListAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, WarehousingInForkliftListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.tv_fromNoAndArea:
                    itemModel = mDataAdapter.getDataList().get(position);
                    Bundle mBundle = new Bundle();

                    mBundle.putString("sourceType","入库纠错");
                    mBundle.putStringArray("spinnerItems",new String[]{"库位被占用"});;
                    mBundle.putInt("mType",0);

                    WarehousingErrorForkliftDistributionList.Bean item = new WarehousingErrorForkliftDistributionList.Bean();
                    //"e_id": 79,
                    item.setE_wareHouseNo(itemModel.getT_toNo()); //"e_wareHouseNo": "产品爆仓区",
                    item.setE_wareArea(itemModel.getT_toArea()); //"e_wareArea": "",
                    item.setE_errType("库位被占用"); //"e_errType": "库位为空",
                    item.setE_errMember(""); //"e_errMember": "",
                    item.setE_output_prodNo(itemModel.getT_prodNo()); //"e_output_prodNo": "T-13-06-01",
                    item.setE_output_prodName(itemModel.getT_prodName()); //"e_output_prodName": "“炫彩童书”小学生课外必读书系 彩图注音版 培养完美女孩的公主童话",
                    item.setE_output_wareHouseNo(itemModel.getT_fromNo()); //"e_output_wareHouseNo": "产品爆仓区",
                    item.setE_output_nbox_num(itemModel.getT_nbox_num()); // "e_output_nbox_num": 39,
                    item.setE_output_npack_num(itemModel.getT_npack_num()); //"e_output_npack_num": 0,
                    item.setE_output_nbook_num(itemModel.getT_nbook_num()); //"e_output_nbook_num": 0,
                    item.setE_output_total(itemModel.getT_count()); //"e_output_total": 3120,
                    item.setE_input_prodNo(itemModel.getT_prodNo()); //"e_input_prodNo": "",
                    item.setE_input_prodName(itemModel.getT_prodName()); //"e_input_prodName": "",
                    item.setE_input_wareHouseNo(itemModel.getT_toNo()); //"e_input_wareHouseNo": "产品纠错区",
                    item.setE_input_nbox_num(1); // "e_input_nbox_num": 0,
                    item.setE_input_npack_num(0); //"e_input_npack_num": 0,
                    item.setE_input_nbook_num(0); //"e_input_nbook_num": 0,
                    item.setE_input_total(0); //"e_input_total": 0,
                    item.setE_state("未审核"); //"e_state": "未审核",
                    item.setE_reviewer("未审核"); //"e_reviewer": "",
                    //item.setE_wareHouseNo(); //"e_createTime": "2021-03-04 21:30:37",
                    //item.setE_wareHouseNo(); //"e_reviewTime": "2021-03-04 21:30:37",
                    item.setE_exception(String.valueOf(itemModel.getT_ID())); //"e_exception": "1656",
                    //item.setE_wareHouseNo(); //"e_exception2": "userName",
                    item.setE_exception3(String.valueOf(itemModel.getT_exception())); //"e_exception3": "4205",
                    //item.setE_wareHouseNo(); //"e_exception4": "2021-03-04 21:30:37"
                    mBundle.putSerializable("itemModel",item);
                    openActivity(WarehousingErrorForkliftDistributionAddActivity.class,mBundle);
                    break;
                case R.id.btnGain:
                    pos = position;
                    itemModel = mDataAdapter.getDataList().get(position);
//                    itemModel = mDataAdapter.getDataList().get(position);
//                    itemModel.setT_taskState("已领取");
//                    mDataAdapter.getDataList().set(position, itemModel);
//                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    DataBind3(itemModel.getT_ID(),true);
//                    search_edit.setText(itemModel.getT_prodNo());
//                    tvProName.setText(itemModel.getT_prodName());
//                    Toast.makeText(WarehousingInForkliftListActivity.this,"【"+ itemModel.getT_prodNo() + "】领取任务成功！", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnOperation:
                    pos = position;
                    itemModel = mDataAdapter.getDataList().get(position);
//                    itemModel = mDataAdapter.getDataList().get(position);
//                    itemModel.setT_taskState("已完成");
//                    mDataAdapter.getDataList().set(position, itemModel);
//                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    DataBind4(itemModel,true);
//                    mDataAdapter.remove(position);
//                    search_edit.setText("");
//                    tvProName.setText("");
//                    Toast.makeText(WarehousingInForkliftListActivity.this,"【"+ itemModel.getT_prodNo() + "】完成任务成功！", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnQuxiao:
                    pos = position;
                    itemModel = mDataAdapter.getDataList().get(position);
//                    itemModel = mDataAdapter.getDataList().get(position);
//                    itemModel.setT_taskState("未领取");
//                    mDataAdapter.getDataList().set(position, itemModel);
//                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    DataBind2(itemModel.getT_ID(),true);
//                    search_edit.setText("");
//                    tvProName.setText("");
//                    Toast.makeText(WarehousingInForkliftListActivity.this,"【"+ itemModel.getT_prodNo() + "】取消任务成功！",Toast.LENGTH_SHORT).show();
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