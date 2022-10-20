package com.zitian.csims.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.zitian.csims.model.InventoryManualAddItemList;
import com.zitian.csims.model.InventoryNuclearDiskList;
import com.zitian.csims.model.InventoryNuclearDiskListError;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.InventoryManualAddItemListAdapter;
import com.zitian.csims.ui.adapter.InventoryNuclearDiskListErrorAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle3;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class InventoryManualAddItemListActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private TextView prodNo;
    private TextView tv01;
    private Button bt01;
    private Button bt02;
    private String wareHouseNo;

    List<InventoryManualAddItemList.Bean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_manual_add_item_list);
        mCurrentPage = 0;
        initView();
        setViewListener();
        DataBind3(true);
        //CreateCustomDialogStyle();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("增项数据");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);
        tv01 = (TextView) findViewById(R.id.tv01);
        prodNo = (TextView) findViewById(R.id.tv_prodNo);
        bt01 = (Button) findViewById(R.id.bt01);
        bt02 = (Button) findViewById(R.id.bt02);
        // 获取意图
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        wareHouseNo = (String) bundle.get("wareHouseNo");
        prodNo.setText("库区："+wareHouseNo);

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        mDataAdapter = new InventoryManualAddItemListAdapter(this);
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
                    InventoryManualAddItemList.Bean item = mDataAdapter.getDataList().get(position);
                    //AppToast.showShortText(getApplicationContext(), item.title);
                    //mDataAdapter.remove(position);
                }
            }
        });
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                InventoryManualAddItemList.Bean item = mDataAdapter.getDataList().get(position);
                //AppToast.showShortText(getApplicationContext(), "onItemLongClick - " + item.title);
            }
        });
    }

    private void setViewListener() {
        icon_back.setOnClickListener(this);
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
    }

    private void DataBind()
    {
        mDataAdapter.clear();
        addItems(list);
        TOTAL_COUNTER = list.size();
        mRecyclerView.refreshComplete(REQUEST_COUNT);
    }

    private void DataBind(boolean isLoading) {
        if(!builder.prodNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(builder.prodNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo", s);
            CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
        }
    }


    private void DataBind2(boolean isLoading) {
        Gson gson = new Gson();
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.DoInventoryDataAndManualScatteredAddItemEdit(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());

        SENDSMSRequest.add("addItemJson",  gson.toJson(list) );
        SENDSMSRequest.add("wareHouseNo",  wareHouseNo );

        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind3(boolean isLoading) {
        Gson gson = new Gson();
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.DoInventoryDataAndManualScatteredAddItemList(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo",wareHouseNo);

        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt01:
                CreateCustomDialogStyle();
                break;
            case R.id.bt02:
                DataBind2(true);
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
    private InventoryManualAddItemListAdapter mDataAdapter = null;
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


    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<InventoryManualAddItemList.Bean> list) {
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
                WarehousingInDistributionQualityInput warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    if (warehousingInDistributionQualityInput.getData() != null) {
                        builder.prodName.setText(warehousingInDistributionQualityInput.getData().h_name);
                    }
                }
                break;
            case 1001://快速登录
                warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    ToastUtil.show(this, warehousingInDistributionQualityInput.getMsg());
                    String result = wareHouseNo;
                    Intent intent = new Intent();
                    intent.putExtra("result", result);
                    setResult(1001, intent);
                    finish();
                }else
                {
                    ToastUtil.show(this, warehousingInDistributionQualityInput.getMsg());
                }
                break;
            case 1002://快速登录
                InventoryManualAddItemList inventoryManualAddItemList = gson.fromJson(response.get(), InventoryManualAddItemList.class);
                if (inventoryManualAddItemList.getResult() == 1) {
                    if(inventoryManualAddItemList.getData()!=null)
                    {
                        list = inventoryManualAddItemList.getData();
                        DataBind();
                    }
                }else
                {
                    ToastUtil.show(this, inventoryManualAddItemList.getMsg());
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
    private InventoryManualAddItemListAdapter.OnItemClickListener MyItemClickListener = new InventoryManualAddItemListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, InventoryManualAddItemListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.btnGain:
                    //openActivity(OutofSpaceForkliftErrorActivity.class);
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
                    InventoryManualAddItemList.Bean  itemModel = mDataAdapter.getDataList().get(position);
                    //itemModel.id = 100;
                    //itemModel.setT_taskState("已领取");
                    //mDataAdapter.getDataList().set(position, itemModel);
                    //mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"领取了任务！",Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    DataBind();
                    break;
                case R.id.btnOperation:
                    itemModel = mDataAdapter.getDataList().get(position);
                    UpdateCustomDialogStyle(itemModel,position);
                    //itemModel.id = 100;
                    //itemModel.setT_taskState("已完成");
                    //mDataAdapter.getDataList().set(position, itemModel);
                    //mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    //Toast.makeText(OutofSpaceForkliftListActivity.this,"完成了任务！",Toast.LENGTH_SHORT).show();
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

    CustomDialogStyle3.Builder builder = null;
    public void CreateCustomDialogStyle()
    {
        builder = new CustomDialogStyle3.Builder(InventoryManualAddItemListActivity.this);
        builder.setTitle("库位号:"+wareHouseNo)
                .setProdNo(list.size()!=0 ? "" : "")
                .setProdName(list.size()!=0 ? "" : "")

                .setNbox_num(list.size()!=0 ? "" : String.valueOf(0))
                .setNpack_num(list.size()!=0 ? "" : String.valueOf(0))
                .setNbook_num(list.size()!=0 ? "" : String.valueOf(0))
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
                        String prodNo = builder.prodNo.getText().toString();
                        String prodName = builder.prodName.getText().toString();
                        String nbox_num = builder.nbox_num.getText().toString();
                        String npack_num = builder.npack_num.getText().toString();
                        String nbook_num = builder.nbook_num.getText().toString();

                        if(prodNo.trim().equals("") ||  prodNo.trim().equals("") )
                        {
                            Toast.makeText(InventoryManualAddItemListActivity.this,"产品编码不能为空！",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        InventoryManualAddItemList.Bean bean = new InventoryManualAddItemList.Bean();
                        bean.setWh_wareHouseNo(wareHouseNo);
                        bean.setWh_prodNo(prodNo);
                        bean.setWh_prodName(prodName);
                        bean.setWh_nbox_num(Integer.valueOf(nbox_num.trim().equals("") ? "0" : nbox_num));
                        bean.setWh_npack_num(Integer.valueOf(npack_num.trim().equals("") ? "0" : npack_num));
                        bean.setWh_nbook_num(Integer.valueOf(nbook_num.trim().equals("") ? "0" : nbook_num));
                        list.add(bean);
                        DataBind();
                    }
                })
                .create()
                .show();
        //SetProdNo(builder.prodNo,1);
        //adapter.clear();
        SetProdNo(builder.prodNo,1);
        builder.prodNo.setSelection(builder.prodNo.getText().length());
        builder.prodNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                builder.prodNo.setText(s);
                builder.prodNo.setSelection(s.length());//set cursor to the end
                DataBind(true);
            }
        });
    }

    public void UpdateCustomDialogStyle(InventoryManualAddItemList.Bean bean,int pos)
    {
        builder = new CustomDialogStyle3.Builder(InventoryManualAddItemListActivity.this);
        builder.setTitle("库位号:"+wareHouseNo)
                //.setMessage("请输入产品编码")
                .setProdNo(bean.getWh_prodNo())
                .setProdName(bean.getWh_prodName())
                .setNbox_num(String.valueOf(bean.getWh_nbox_num()))
                .setNpack_num(String.valueOf(bean.getWh_npack_num()))
                .setNbook_num(String.valueOf(bean.getWh_nbook_num()))
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
                        String prodNo = builder.prodNo.getText().toString();
                        String prodName = builder.prodName.getText().toString();
                        String nbox_num = builder.nbox_num.getText().toString();
                        String npack_num = builder.npack_num.getText().toString();
                        String nbook_num = builder.nbook_num.getText().toString();

                        if(prodNo.trim().equals("") ||  prodNo.trim().equals("") )
                        {
                            Toast.makeText(InventoryManualAddItemListActivity.this,"产品编码不能为空！",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //InventoryInputWholeError.Bean bean = new InventoryInputWholeError.Bean();
                        //bean.setWh_prodNo(prodNo);
                        //bean.setWh_prodName(prodName);
                        //bean.setWh_nbox_num(Integer.valueOf(nbox_num.trim().equals("") ? "0" : nbox_num));
                        //bean.setWh_npack_num(Integer.valueOf(npack_num.trim().equals("") ? "0" : npack_num));
                        //bean.setWh_nbook_num(Integer.valueOf(nbook_num.trim().equals("") ? "0" : nbook_num));
                        //list.add(bean);
                        list.get(pos).setWh_prodNo(prodNo);
                        list.get(pos).setWh_prodName(prodName);

                        list.get(pos).setWh_nbox_num(Integer.valueOf(nbox_num.trim().equals("") ? "0" : nbox_num));
                        list.get(pos).setWh_npack_num(Integer.valueOf(npack_num.trim().equals("") ? "0" : npack_num));
                        list.get(pos).setWh_nbook_num(Integer.valueOf(nbook_num.trim().equals("") ? "0" : nbook_num));
                        DataBind();
                    }
                })
                .create()
                .show();
        SetProdNo(builder.prodNo,1);
        builder.prodNo.setSelection(builder.prodNo.getText().length());
        builder.prodNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                builder.prodNo.setText(s);
                builder.prodNo.setSelection(s.length());//set cursor to the end
                DataBind(true);
            }
        });
    }
}