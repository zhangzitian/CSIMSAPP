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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.zitian.csims.model.InventoryContrastManualAddItemList;
import com.zitian.csims.model.InventoryEmpty;
import com.zitian.csims.model.InventoryEmpty2;
import com.zitian.csims.model.InventoryInputWhole;
import com.zitian.csims.model.InventoryManualList;
import com.zitian.csims.model.InventoryNuclearDiskList;
import com.zitian.csims.model.InventoryNuclearDiskList2;
import com.zitian.csims.model.WarehousingInDistributionQualityInput;
import com.zitian.csims.model.WarehousingInManagerTaskList;
import com.zitian.csims.network.CallServer;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.adapter.InventoryManualListAdapter;
import com.zitian.csims.ui.adapter.InventoryNuclearDiskListAdapter;
import com.zitian.csims.ui.adapter.WarehousingInManagerTaskListAdapter;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle2;
import com.zitian.csims.ui.widget.CustomDialogStyle3;
import com.zitian.csims.util.NetworkUtils;
import com.zitian.csims.util.ToastUtil;

import java.util.List;

public class InventoryNuclearDiskListActivity extends BaseActivity implements  View.OnClickListener, HttpListener<String> {

    private ImageView icon_back;
    private Spinner search_status_edit;
    private AutoCompleteTextView search_edit;
    private ImageView search_image;

    private RelativeLayout all_no_data;
    private LRecyclerView list;
    private TextView tipMsg;

    private Button btn01;
    private Button btn02;
    private Button btn03;
    private Button btn04;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_nuclear_disk_list);
        mCurrentPage = 0;
        initView();
        setViewListener();
        DataBind(true);
        IsStatusInventory(true);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("【盘点业务】-数据核盘");
        tv_title.setGravity(Gravity.CENTER);

        icon_back = (ImageView) findViewById(R.id.back);
        icon_back.setVisibility(View.VISIBLE);

        search_status_edit  = (Spinner) findViewById(R.id.search_status_edit);
        search_edit  = (AutoCompleteTextView) findViewById(R.id.search_edit);
        search_edit.clearFocus();
        search_image  = (ImageView) findViewById(R.id.search_image);

        search_edit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                search_edit.setText(s);
                search_edit.setSelection(s.length());//set cursor to the end
                mRecyclerView.setNoMore(false);
                mCurrentPage = 0;
                mDataAdapter.clear();
                DataBind2(true);
            }
        });

        all_no_data = (RelativeLayout) findViewById(R.id.all_no_data);
        list = (LRecyclerView) findViewById(R.id.list);
        tipMsg = (TextView)findViewById(R.id.tipMsg);

        btn01 = (Button) findViewById(R.id.btn01);
        btn02 = (Button)findViewById(R.id.btn02);
        btn03 = (Button)findViewById(R.id.btn03);
        btn04 = (Button)findViewById(R.id.btn04);

        SetProdNo(search_edit,1);

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
        mDataAdapter = new InventoryNuclearDiskListAdapter(this);
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
                mRecyclerView.setNoMore(false);
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
                    InventoryNuclearDiskList.Bean item = mDataAdapter.getDataList().get(position);
                    //AppToast.showShortText(getApplicationContext(), item.title);
                    //mDataAdapter.remove(position);
                }
            }
        });
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                InventoryNuclearDiskList.Bean item = mDataAdapter.getDataList().get(position);
                //AppToast.showShortText(getApplicationContext(), "onItemLongClick - " + item.title);
            }
        });
    }

    private void setViewListener() {
        icon_back.setOnClickListener(this);
        search_image.setOnClickListener(this);
        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn03.setOnClickListener(this);
        btn04.setOnClickListener(this);
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
                DataBind2(true);
                break;
            case R.id.btn01:
                if(!search_status_edit.getSelectedItem().toString().equals("请选择区域"))
                {
                    DataBind5(true);
                }else
                {
                    ToastUtil.show(this, "请选择区域" );
                }
                break;
            case R.id.btn02:
                if(!search_status_edit.getSelectedItem().toString().equals("请选择区域"))
                {
                    CreateCustomDialogStyle(1);
                }else
                {
                    ToastUtil.show(this, "请选择区域" );
                }
                break;
            case R.id.btn03:
                if(!search_status_edit.getSelectedItem().toString().equals("请选择区域"))
                {
                    CreateCustomDialogStyle(2);
                }else
                {
                    ToastUtil.show(this, "请选择区域" );
                }
                break;
            case R.id.btn04:
                CustomDialogStyle2.Builder builder = new CustomDialogStyle2.Builder(InventoryNuclearDiskListActivity.this);
                builder.setTitle("温馨提示")
                        .setMessage("用户【"+CSIMSApplication.getAppContext().getUser().getO_name()+"】确定要选择列号【"+search_status_edit.getSelectedItem().toString()+"】 全部确定吗？")
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
                                itemModel = null;
                                mRecyclerView.setNoMore(false);
                                mCurrentPage = 0;
                                mDataAdapter.clear();
                                DataBind3(true,3);
                            }
                        })
                        .create()
                        .show();
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
    private InventoryNuclearDiskListAdapter mDataAdapter = null;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    //WeakHandler必须是Activity的一个实例变量.原因详见：http://dk-exp.com/2015/11/11/weak-handler/
    private WeakHandler mHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    DataBind2(false);
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
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryNuclearDiskList(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("sEcho", "1");
        CallServer.getInstance().add(this, 1000, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind2(boolean isLoading) {
        //search_status_edit
        //search_edit
        //String condition =  "  Len(u1_addDate) < 4   ";
        String condition  =  " 1 = 1";
        if(!search_status_edit.getSelectedItem().toString().contains("请选择区域"))
        {
            condition += " and wh_wareArea =  '" + search_status_edit.getSelectedItem().toString() + "'";
        }
        if(!search_edit.getText().toString().trim().equals(""))
        {
            condition += " and [wh_prodNo] =  '" + search_edit.getText().toString().trim() + "'";
        }
        condition += " order by   REPLACE( REPLACE([wh_prodNo],'T',''),'-','') ";

        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryNuclearDiskList2(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("condition",condition);
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo", search_status_edit.getSelectedItem().toString());
        SENDSMSRequest.add("condition",condition);
        SENDSMSRequest.add("iDisplayStart",mCurrentPage * REQUEST_COUNT);
        SENDSMSRequest.add("iDisplayLength", REQUEST_COUNT);

        CallServer.getInstance().add(this, 1001, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
        mCurrentPage++;
    }

    private void DataBind3(boolean isLoading,int mType,int wh_ID,String wareHouseNo,String prodNo,String prodName,int nbox_num,int npack_num,int nbook_num,int prodNumber) {
        //search_status_edit
        //search_edit
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryNuclearDiskList3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("ID",wh_ID);
        SENDSMSRequest.add("wareHouseNo",wareHouseNo);
        SENDSMSRequest.add("mType",mType);

        SENDSMSRequest.add("prodNo",prodNo);
        SENDSMSRequest.add("prodName",prodName);
        SENDSMSRequest.add("nbox_num",nbox_num);
        SENDSMSRequest.add("npack_num",npack_num);
        SENDSMSRequest.add("nbook_num",nbook_num);
        SENDSMSRequest.add("prodNumber",prodNumber);

        //确定 传入 件 包 本 数量
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
    }

    private void DataBind4(boolean isLoading) {
        //search_status_edit
        //search_edit
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryNuclearDiskList4(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo",search_status_edit.getSelectedItem().toString());
        SENDSMSRequest.add("sEcho", "1");
        CallServer.getInstance().add(this, 1003, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
    }

    private void DataBind5(boolean isLoading) {
        //search_status_edit
        //search_edit
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryNuclearDiskList5(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo",search_status_edit.getSelectedItem().toString());
        SENDSMSRequest.add("sEcho", "1");
        CallServer.getInstance().add(this, 1004, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind6(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.CheckNuclearDiskLog(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo",search_status_edit.getSelectedItem().toString());
        SENDSMSRequest.add("sEcho", "1");
        CallServer.getInstance().add(this, 1005, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind7(boolean isLoading) {
        if(!builder.prodNo.getText().toString().equals("")  )
        {
            String s = ConvertToProdNo(builder.prodNo.getText().toString());
            Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.WarehousingInDistributionQualityInput(), RequestMethod.POST);
            SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
            SENDSMSRequest.add("prodNo", s);
            CallServer.getInstance().add(this, 1006, SENDSMSRequest, this, false, isLoading);
        }
    }

    private void DataBind8(boolean isLoading) {
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryNuclearDiskList6(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo", itemModel.getWh_wareHouseNo());
        SENDSMSRequest.add("prodNo", itemModel.getWh_prodNo());
        CallServer.getInstance().add(this, 1007, SENDSMSRequest, this, false, isLoading);
    }

    private void DataBind3(boolean isLoading,int mType) {
        //search_status_edit
        //search_edit
        Request<String> SENDSMSRequest = NoHttp.createStringRequest(AppConstant.InventoryNuclearDiskList3(), RequestMethod.POST);
        SENDSMSRequest.addHeader("token", "79FAF822-7194-4FE3-8C4F-1D99BE71BC9C");
        SENDSMSRequest.add("sEcho", "1");
        SENDSMSRequest.add("UserId", CSIMSApplication.getAppContext().getUser().getO_id() );
        SENDSMSRequest.add("UserName", CSIMSApplication.getAppContext().getUser().getO_name());
        SENDSMSRequest.add("wareHouseNo",search_status_edit.getSelectedItem().toString());
        SENDSMSRequest.add("mType",mType);
        //确定 传入 件 包 本 数量
        CallServer.getInstance().add(this, 1002, SENDSMSRequest, this, false, isLoading);
        mRecyclerView.setPullRefreshEnabled(true);
    }

    private void notifyDataSetChanged() {
        mLRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void addItems(List<InventoryNuclearDiskList.Bean> list) {
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
                InventoryEmpty outofSpaceForkliftList2 = gson.fromJson(response.get(), InventoryEmpty.class);
                if (outofSpaceForkliftList2.getResult() == 1) {
                    if (outofSpaceForkliftList2.getData() != null) {
                        List<InventoryEmpty.Bean> list = outofSpaceForkliftList2.getData();
                        InventoryEmpty.Bean b = new InventoryEmpty.Bean();
                        b.setI_rowName("请选择区域");
                        list.add(0,b);
                        ArrayAdapter<InventoryEmpty.Bean> spinnerAdapter = new ArrayAdapter<InventoryEmpty.Bean>(getApplication(),R.layout.simple_spinner_item, list);
                        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                        search_status_edit.setAdapter(spinnerAdapter);
                        search_status_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                                                                     {
                                                                         @Override
                                                                         public void onItemSelected(AdapterView<?> parent, View view,int pos, long id)
                                                                         {
                                                                             if(!list.get(pos).getI_rowName().equals("请选择区域"))
                                                                             {
                                                                                 DataBind6(true);
                                                                             }
                                                                             if(list.get(pos).getI_rowName().contains("残书"))
                                                                             {
                                                                                 btn03.setEnabled(true);
                                                                             }else
                                                                             {
                                                                                 btn03.setEnabled(false);
                                                                             }
                                                                             if(list.get(pos).getI_rowName().contains("残书"))
                                                                             {
                                                                                 btn04.setEnabled(true);
                                                                             }else
                                                                             {
                                                                                 btn04.setEnabled(false);
                                                                             }
                                                                         }
                                                                         @Override
                                                                         public void onNothingSelected(AdapterView<?> parent)
                                                                         {
                                                                         }
                                                                     }
                        );
                    }
                }
                break;
            case 1001://快速登录
                InventoryNuclearDiskList outofSpaceForkliftList = gson.fromJson(response.get(), InventoryNuclearDiskList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    addItems(outofSpaceForkliftList.getData());
                    TOTAL_COUNTER = outofSpaceForkliftList.getTotal();
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    list.setVisibility(View.VISIBLE);
                    all_no_data.setVisibility(View.GONE);
                }else if(outofSpaceForkliftList.getResult() == 0 && mCurrentPage==1) {
                    //没有数据
                    mRecyclerView.refreshComplete(0);
                    notifyDataSetChanged();
                    list.setVisibility(View.GONE);
                    all_no_data.setVisibility(View.VISIBLE);
                    tipMsg.setText(outofSpaceForkliftList.getMsg());
                }

                break;
            case 1002:
                outofSpaceForkliftList = gson.fromJson(response.get(), InventoryNuclearDiskList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    //mDataAdapter.remove(pos);
                    //mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false,pos) , "jdsjlzx");
                    //mCurrentPage = 0;
                    //mDataAdapter.clear();
                    //DataBind2(true);
                    if(itemModel!=null)
                    {
                        if(itemModel.getWh_wareHouseNo() !=null)
                            DataBind8(true);
                    }else
                    {
                        mRecyclerView.setNoMore(false);
                        mCurrentPage = 0;
                        mDataAdapter.clear();
                        DataBind2(true);
                    }
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }else   {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());

                }
                break;
            case 1003://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), InventoryNuclearDiskList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    DataBind2(true);
                    //ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }else {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
            case 1004://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), InventoryNuclearDiskList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    notifyDataSetChanged();
                    DataBind(true);//刷新库位号列表
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }else {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
            case 1005://快速登录
                outofSpaceForkliftList = gson.fromJson(response.get(), InventoryNuclearDiskList.class);
                if (outofSpaceForkliftList.getResult() == 1) {
                    mRecyclerView.setNoMore(false);
                    mCurrentPage = 0;
                    mDataAdapter.clear();
                    DataBind4(true);
                }else if (outofSpaceForkliftList.getResult() == 2) {
                    CustomDialogStyle2.Builder builder = new CustomDialogStyle2.Builder(InventoryNuclearDiskListActivity.this);
                    builder.setTitle("温馨提示")
                            .setMessage("用户【"+CSIMSApplication.getAppContext().getUser().getO_name()+"】确定要选择列号【"+search_status_edit.getSelectedItem().toString()+"】 开始盘点吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    search_status_edit.setSelection(0,true);
                                }
                            })
                            .setPositionButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    mRecyclerView.setNoMore(false);
                                    mCurrentPage = 0;
                                    mDataAdapter.clear();
                                    DataBind4(true);
                                }
                            })
                            .create()
                            .show();
                }else
                {
                    ToastUtil.show(this, outofSpaceForkliftList.getMsg());
                }
                break;
            case 1006://快速登录
                WarehousingInDistributionQualityInput warehousingInDistributionQualityInput = gson.fromJson(response.get(), WarehousingInDistributionQualityInput.class);
                if (warehousingInDistributionQualityInput.getResult() == 1) {
                    if (warehousingInDistributionQualityInput.getData() != null) {
                        builder.prodName.setText(warehousingInDistributionQualityInput.getData().h_name);
                    }
                }
                break;
            case 1007://快速登录
                InventoryNuclearDiskList2 inventoryNuclearDiskList2 = gson.fromJson(response.get(), InventoryNuclearDiskList2.class);
                if (inventoryNuclearDiskList2.getResult() == 1) {
                    mDataAdapter.getDataList().set(pos,inventoryNuclearDiskList2.getData());
                    mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false,pos) , "jdsjlzx");
                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this, "访问失败" + response.getException().getMessage());
    }

    InventoryNuclearDiskList.Bean itemModel = null;
    int pos = 0;
    /**
     * item＋item里的控件点击监听事件
     */
    private WarehousingInManagerTaskListAdapter.OnItemClickListener MyItemClickListener = new WarehousingInManagerTaskListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, WarehousingInManagerTaskListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.btnGain:
                    itemModel = mDataAdapter.getDataList().get(position);
                    pos = position;
                    if(itemModel.getU1_addDate().equals(""))
                    {
                        DataBind3(true,0,itemModel.getWh_ID(),itemModel.getWh_wareHouseNo(),itemModel.getWh_prodNo(),itemModel.getWh_prodName(),itemModel.getWh_nbox_num(),itemModel.getWh_npack_num(),itemModel.getWh_nbook_num(),itemModel.getWh_prodNumber());
                    }else
                    {
                        DataBind3(true,0,itemModel.getWh_ID(),itemModel.getWh_wareHouseNo(),itemModel.getWh_prodNo(),itemModel.getWh_prodName(),itemModel.getU1_nbox_num(),itemModel.getU1_npack_num(),itemModel.getU1_nbook_num(),itemModel.getU1_prodNumber());
                    }

                    //itemModel.setT_taskState("取消");
                    //mDataAdapter.getDataList().set(position, itemModel);
                    //mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    //DataBind2(itemModel.getT_ID(),true);
                    //mDataAdapter.remove(position);
                    //Toast.makeText(InventoryNuclearDiskListActivity.this,"【"+ itemModel.getT_prodNo() + "】取消任务成功！",Toast.LENGTH_SHORT).show();
                    //String wareHouseNo,String prodNo,String prodName,String prodNumber,String nbox_num,String npack_num,String nbook_num
                    break;
                case R.id.btnOperation:
                    itemModel = mDataAdapter.getDataList().get(position);
                    pos = position;
                    UpdateCustomDialogStyle(itemModel);
                    //Intent intent = new Intent(InventoryNuclearDiskListActivity.this,InventoryNuclearDiskListErrorActivity.class);
                    //intent.putExtra("itemModel",itemModel);
                    //startActivityForResult(intent,0);
                    //itemModel.setT_taskState("未领取");
                    //mDataAdapter.getDataList().set(position, itemModel);
                    //mLRecyclerViewAdapter.notifyItemChanged(mLRecyclerViewAdapter.getAdapterPosition(false, position), "jdsjlzx");
                    //mDataAdapter.remove(position);
                    //DataBind3(itemModel.getT_ID(),true);//RestoreTaskJL
                    //Toast.makeText(InventoryNuclearDiskListActivity.this,"【"+ itemModel.getT_prodNo() + "】恢复任务成功！",Toast.LENGTH_SHORT).show();
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

    public void CreateCustomDialogStyle(int mType)
    {
        builder = new CustomDialogStyle3.Builder(InventoryNuclearDiskListActivity.this);
        builder.setTitle("库位号:"+search_status_edit.getSelectedItem().toString())
                .setProdNo("")
                .setProdName("")
                //.setNbox_num(String.valueOf(0))
                //.setNpack_num(String.valueOf(0))
                //.setNbook_num(String.valueOf(0))
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
                        String nbox_num = builder.nbox_num.getText().toString().trim().equals("") ? "0" : builder.nbox_num.getText().toString();
                        String npack_num = builder.npack_num.getText().toString().trim().equals("") ? "0" : builder.npack_num.getText().toString();
                        String nbook_num = builder.nbook_num.getText().toString().trim().equals("") ? "0" : builder.nbook_num.getText().toString();

                        if(nbox_num.trim().equals("0") &&  npack_num.trim().equals("0") &&  nbook_num.trim().equals("0") )
                        {
                            Toast.makeText(InventoryNuclearDiskListActivity.this,"产品数量不能等于0！",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(prodNo.trim().equals("") ||  prodNo.trim().equals("") )
                        {
                            Toast.makeText(InventoryNuclearDiskListActivity.this,"产品编码不能为空！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        itemModel = null;
                        DataBind3(true,mType,0,search_status_edit.getSelectedItem().toString(),prodNo,prodName,Integer.valueOf(nbox_num),Integer.valueOf(npack_num),Integer.valueOf(nbook_num),0);
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
                DataBind7(true);
            }
        });
    }

    public void UpdateCustomDialogStyle(InventoryNuclearDiskList.Bean bean)
    {
        int nbox_num = 0;
        int npack_num = 0;
        int nbook_num = 0;
        if(bean.getU1_addDate().equals(""))
        {
            nbox_num = bean.getWh_nbox_num();
            npack_num = bean.getWh_npack_num();
            nbook_num = bean.getWh_nbook_num();
        }else
        {
            nbox_num = bean.getU1_nbox_num();
            npack_num = bean.getU1_npack_num();
            nbook_num = bean.getU1_nbook_num();
        }

        builder = new CustomDialogStyle3.Builder(InventoryNuclearDiskListActivity.this);
        builder.setTitle("库位号:"+bean.getWh_wareHouseNo())
                //.setMessage("请输入产品编码")
                .setProdNo(bean.getWh_prodNo())
                .setProdName(bean.getWh_prodName())
                .setNbox_num(String.valueOf(nbox_num))
                .setNpack_num(String.valueOf(npack_num))
                .setNbook_num(String.valueOf(nbook_num))
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
                        //String nbox_num = builder.nbox_num.getText().toString();
                        //String npack_num = builder.npack_num.getText().toString();
                        //String nbook_num = builder.nbook_num.getText().toString();
                        String nbox_num = builder.nbox_num.getText().toString().trim().equals("") ? "0" : builder.nbox_num.getText().toString();
                        String npack_num = builder.npack_num.getText().toString().trim().equals("") ? "0" : builder.npack_num.getText().toString();
                        String nbook_num = builder.nbook_num.getText().toString().trim().equals("") ? "0" : builder.nbook_num.getText().toString();
                        if(prodNo.trim().equals("") ||  prodNo.trim().equals("") )
                        {
                            Toast.makeText(InventoryNuclearDiskListActivity.this,"产品编码不能为空！",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //pos = 0;
                        DataBind3(true,0,itemModel.getWh_ID(),itemModel.getWh_wareHouseNo(),itemModel.getWh_prodNo(),itemModel.getWh_prodName(), Integer.valueOf(nbox_num),Integer.valueOf(npack_num),Integer.valueOf(nbook_num),0);
                    }
                })
                .create()
                .show();
        SetProdNo(builder.prodNo,1);
        builder.prodNo.setEnabled(false);
        builder.prodNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                String s = ConvertToProdNo(obj.toString());
                builder.prodNo.setText(s);
                builder.prodNo.setSelection(s.length());//set cursor to the end
                DataBind7(true);
            }
        });
    }

}