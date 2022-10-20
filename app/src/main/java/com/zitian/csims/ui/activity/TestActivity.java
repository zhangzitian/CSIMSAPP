package com.zitian.csims.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yolanda.nohttp.rest.Response;
import com.zitian.csims.R;
import com.zitian.csims.application.CSIMSApplication;
import com.zitian.csims.common.AppManager;
import com.zitian.csims.common.NotificationHelper;
import com.zitian.csims.common.NotificationUtils;
import com.zitian.csims.common.PushSmsService;
import com.zitian.csims.common.Utils;
import com.zitian.csims.network.HttpListener;
import com.zitian.csims.ui.base.BaseActivity;
import com.zitian.csims.ui.widget.CustomDialogStyle2;
import com.zitian.csims.util.ToastUtil;

public class TestActivity   extends BaseActivity implements  View.OnClickListener, HttpListener<String> {


    //爆仓区上架  OutofSpace
    //任务列表 activity_outof_space_forklift_list  OutofSpaceForkliftListActivity
    //activity_outof_space_forklift_list_item
    //库位纠错 activity_outof_space_forklift_error  OutofSpaceForkliftErrorActivity
    //任务列表 activity_outof_space_manager_list OutofSpaceManagerListActivity
    //activity_outof_space_manager_list_item
    //////////////////////////////////////////////////////////////////////////
    //质量问题下架  BatchOff
    //任务列表 activity_batch_off_forklift_list  BatchOffForkliftListActivity
    //批量手动上架任务 activity_batch_off_forklift_manual_list BatchOffForkliftManualListActivity
    //任务列表 activity_batch_off_manager_list  BatchOffManagerListActivity
    //新增任务 activity_batch_off_manager_add BatchOffManagerAddActivity
    //批量手动上架任务列表 activity_batch_off_manager_manual_list  BatchOffManagerManualListActivity
    //批量手动上架  activity_batch_off_manager_manual_add  BatchOffManagerManualAddActivity
    //调拨单列表 activity_batch_off_manager_transfer_order_list BatchOffManagerTransferOrderListActivity
    //调拨单 activity_batch_off_manager_transfer_order_add BatchOffManagerTransferOrderAddActivity
    //////////////////////////////////////////////////////////////////////////
    //改版调库下架
    //任务列表 activity_bookrevision_forklift_list BookrevisionForkliftListActivity
    //任务列表 activity_bookrevision_manager_list BookrevisionManagerListActivity
    //新增任务 activity_bookrevision_manager_add  BookrevisionManagerAddActivity

    //private ImageView icon_back;
//    private Button OutofSpaceForkliftListActivity;
//    private Button OutofSpaceForkliftErrorActivity;
//    private Button OutofSpaceManagerListActivity;
//    private Button BatchOffForkliftListActivity;
//    private Button BatchOffForkliftManualListActivity;
//    private Button BatchOffManagerListActivity;
//    private Button BatchOffManagerAddActivity;
//    private Button BatchOffManagerManualListActivity;
//    private Button BatchOffManagerManualAddActivity;
//    private Button BatchOffManagerTransferOrderListActivity;
//    private Button BatchOffManagerTransferOrderAddActivity;
//    private Button BookrevisionForkliftListActivity;
//    private Button BookrevisionManagerListActivity;
//    private Button BookrevisionManagerAddActivity;

    //入库业务  WareHousing
    //储运经理角色
    //详细任务列表 activity_warehousing_manager_task_list
    private Button WarehousingInManagerTaskListActivity;

    //叉车司机角色
    //入库上架任务 activity_warehousing_forklift_list
    private Button WarehousingInForkliftListActivity;

    //配货员
    //质检录入 activity_warehousing_distribution_quality_input
    private Button WarehousingInDistributionQualityInputActivity;
    //核对录入  activity_warehousing_distribution_check_input
    private Button WarehousingInDistributionCheckInputActivity;
    //满盘任务领取 activity_warehousing_distribution_full_task
    private Button WarehousingInDistributionFullTaskActivity;
    //手动上架  activity_warehousing_distribution_manual
    private Button WarehousingInDistributionManualActivity;

    //////////////////////////////////////////////////////////////////////////
    //补货业务  Replenish
    private Button ReplenishAutoDistributionListActivity;
    private Button ReplenishManualDistributionAddActivity;

    private Button ReplenishForkliftListActivity;
    //////////////////////////////////////////////////////////////////////////
    //爆仓区上架  OutofSpace

    //任务列表 activity_outof_space_forklift_list  OutofSpaceForkliftListActivity
    private Button OutofSpaceForkliftListActivity;
    //库位纠错 activity_outof_space_forklift_error  OutofSpaceForkliftErrorActivity
    //private Button OutofSpaceForkliftErrorActivity;
    //任务列表 activity_outof_space_manager_list OutofSpaceManagerListActivity
    private Button OutofSpaceManagerListActivity;

    //////////////////////////////////////////////////////////////////////////
    //质量问题下架  BatchOff

    //任务列表 activity_batch_off_forklift_list  BatchOffForkliftListActivity
    private Button BatchOffForkliftListActivity;
    //批量手动上架任务 activity_batch_off_forklift_manual_list BatchOffForkliftManualListActivity
    private Button BatchOffForkliftManualListActivity;
    //任务列表 activity_batch_off_manager_list  BatchOffManagerListActivity
    private Button BatchOffManagerListActivity;
    //新增任务 activity_batch_off_manager_add BatchOffManagerAddActivity
    private Button BatchOffManagerAddActivity;
    //批量手动上架任务列表 activity_batch_off_manager_manual_list  BatchOffManagerManualListActivity
    private Button BatchOffManagerManualListActivity;
    //批量手动上架  activity_batch_off_manager_manual_add  BatchOffManagerManualAddActivity
    private Button BatchOffManagerManualAddActivity;
    //调拨单列表 activity_batch_off_manager_transfer_order_list BatchOffManagerTransferOrderListActivity
    private Button BatchOffManagerTransferOrderListActivity;
    //调拨单 activity_batch_off_manager_transfer_order_add BatchOffManagerTransferOrderAddActivity
    private Button BatchOffManagerTransferOrderAddActivity;
    //////////////////////////////////////////////////////////////////////////
    //改版调库下架
    //任务列表 activity_bookrevision_forklift_list BookrevisionForkliftListActivity
    private Button BookrevisionForkliftListActivity;
    //任务列表 activity_bookrevision_manager_list BookrevisionManagerListActivity
    private Button BookrevisionManagerListActivity;
    //新增任务 activity_bookrevision_manager_add  BookrevisionManagerAddActivity
    private Button BookrevisionManagerAddActivity;
    //////////////////////////////////////////////////////////////////////////
    //日常纠错盘点   DailyError

    //收发差错盘点申请
    //盘点纠错列表 activity_daily_error_manager_error_list  DailyErrorManagerErrorListActivity
    private Button DailyErrorManagerErrorListActivity;
    //任务列表 activity_daily_error_manager_task_list  DailyErrorManagerTaskListActivity
    private Button DailyErrorManagerTaskListActivity;
    //任务详情 activity_daily_error_manager_task_details  DailyErrorManagerTaskDetailsActivity
    private Button DailyErrorManagerTaskDetailsActivity;
    //盘点入库单审核 activity_daily_error_manager_warehousing_examine  DailyErrorManagerWarehousingExamineActivity
    //private Button DailyErrorManagerWarehousingExamineActivity;

    //盘点入库单列表 activity_daily_error_forklift_warehousing_list DailyErrorForkliftWarehousingListActivity
    private Button DailyErrorForkliftWarehousingListActivity;
    //盘点入库单  activity_daily_error_forklift_warehousing_add   DailyErrorForkliftWarehousingAddActivity
    private Button DailyErrorForkliftWarehousingAddActivity;
    //盘点入库任务列表 activity_daily_error_forklift_warehousing_task_list DailyErrorForkliftWarehousingTaskListActivity
    private Button DailyErrorForkliftWarehousingTaskListActivity;

    //////////////////////////////////////////////////////////////////////////
    //手动业务      Manual
    //手动上下架列表    activity_manual_forklift_list ManualForkliftListActivity
    private Button ManualForkliftListActivity;
    //手动上架          activity_manual_forklift_up ManualForkliftUpActivity
    private Button ManualForkliftUpActivity;
    //手动下架          activity_manual_forklift_dow ManualForkliftDowActivity
    private Button ManualForkliftDowActivity;

    //入库调整单  activity_manual_shelves_warehouse_in ManualShelvesWarehouseInActivity
    private Button ManualShelvesWarehouseInActivity;
    //出库调整单  activity_manual_shelves_warehouse_out ManualShelvesWarehouseOutActivity
    private Button ManualShelvesWarehouseOutActivity;

    //出入库调整单销售助理列表  activity_manual_shelves_list ManualShelvesListActivity
    private Button ManualShelvesListActivity;
    //出入库调整单销售助理修改  activity_manual_shelves_edit ManualShelvesEditActivity
    private Button ManualShelvesEditActivity;

    //出出库调整单经理审核列表  activity_manual_manager_list ManualManagerListActivity
    private Button ManualManagerListActivity;
    //出入库调整单经理审核  activity_manual_manager_examine ManualManagerExamineActivity
    //private Button ManualManagerExamineActivity;

    //////////////////////////////////////////////////////////////////////////
    //库位调整业务  WarehousingAdjustment

    //年度库位调区上架任务  activity_warehousing_adjustment_forklift_year_outof_space WarehousingAdjustmentForkliftYearOutofSpaceActivity
    private Button WarehousingAdjustmentForkliftYearOutofSpaceActivity;
    //配货员---调品种任务列表
    private Button WarehousingAdjustmentDistributionAssortmentActivity;
    //经理---库位调整
    private Button WarehousingAdjustmentManagerActivity;

    //////////////////////////////////////////////////////////////////////////
    //库位纠错业务 WarehousingError
    private Button WarehousingErrorForkliftDistributionListActivity;
    //纠错调整单 activity_warehousing_error_forklift_distribution WarehousingErrorForkliftDistributionActivity
    private Button WarehousingErrorForkliftDistributionActivity;

    //private Button WarehousingErrorForkliftDistributionAddActivity;

    private Button WarehousingErrorForkliftDistributionAdd2Activity;

    //纠错审核  activity_warehousing_error_manager_examine WarehousingErrorManagerExamineActivity
    private Button WarehousingErrorManagerExamineActivity;
    //纠错列表  activity_warehousing_error_manager_list WarehousingErrorManagerListActivity
    private Button WarehousingErrorManagerListActivity;
    //////////////////////////////////////////////////////////////////////////
    //盘点业务   Inventory
    private Button InventoryActivity;

    private Button InventoryContrastListActivity;
    //手工盘点   activity_inventory_manual InventoryManualActivity
    private Button InventoryManualActivity;
    //录入盘点整包整件数据  activity_inventory_input_whole  InventoryInputWholeActivity
    //private Button InventoryInputWholeActivity;
    //录入盘点散书数据  activity_inventory_input_disperse InventoryInputDisperseActivity
    //private Button InventoryInputDisperseActivity;
    //盘点纠错          activity_inventory_error  InventoryErrorActivity
    //private Button InventoryErrorActivity;
    //库位纠错调整任务  activity_inventory_error_list InventoryErrorListActivity
    private Button InventoryTaskCreateActivity;

    private Button InventoryErrorListActivity;
    //空位盘点         activity_inventory_empty  InventoryEmptyActivity
    private Button InventoryEmptyActivity;
    //数据核盘
    private Button InventoryNuclearDiskListActivity;
    //基础设置 Basic settings
    //码盘规则      activity_basic_settings_code_disk_rules BasicSettingsCodeDiskRulesActivity
    private Button BasicSettingsCodeDiskRulesActivity;
    //图书绝版      activity_basic_settings_books_out_of_print BasicSettingsBooksOutOfPrintActivity
    private Button BasicSettingsBooksOutOfPrintActivity;
    //修改密码
    private Button UpdatePassWordActivity;

    private TextView username ;
    private Button loginOut;
    Intent pushIntent =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        setViewListener();

        TextView CountTv1 = (TextView) findViewById(R.id.task_count_tv1);
        TextView CountTv2 = (TextView) findViewById(R.id.task_count_tv2);
        TextView CountTv3 = (TextView) findViewById(R.id.task_count_tv3);
        TextView CountTv4 = (TextView) findViewById(R.id.task_count_tv4);
        TextView CountTv5 = (TextView) findViewById(R.id.task_count_tv5);

        GetTaskCount(CountTv1,CountTv2,CountTv3,CountTv4,CountTv5);
        pushIntent = new Intent(this, PushSmsService.class);
        startService(pushIntent);
    }

    private void initView() {

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("库位管理系统");
        tv_title.setGravity(Gravity.CENTER);

        //icon_back = (ImageView) findViewById(R.id.icon_back);

//        OutofSpaceForkliftListActivity = (Button) findViewById(R.id.OutofSpaceForkliftListActivity);
//        OutofSpaceForkliftErrorActivity  = (Button) findViewById(R.id.OutofSpaceForkliftErrorActivity);
//        OutofSpaceManagerListActivity  = (Button) findViewById(R.id.OutofSpaceManagerListActivity);
//        BatchOffForkliftListActivity  = (Button) findViewById(R.id.BatchOffForkliftListActivity);
//        BatchOffForkliftManualListActivity  = (Button) findViewById(R.id.BatchOffForkliftManualListActivity);
//        BatchOffManagerListActivity  = (Button) findViewById(R.id.BatchOffManagerListActivity);
//        BatchOffManagerAddActivity  = (Button) findViewById(R.id.BatchOffManagerAddActivity);
//        BatchOffManagerManualListActivity  = (Button) findViewById(R.id.BatchOffManagerManualListActivity);
//        BatchOffManagerManualAddActivity  = (Button) findViewById(R.id.BatchOffManagerManualAddActivity);
//        BatchOffManagerTransferOrderListActivity  = (Button) findViewById(R.id.BatchOffManagerTransferOrderListActivity);
//        BatchOffManagerTransferOrderAddActivity  = (Button) findViewById(R.id.BatchOffManagerTransferOrderAddActivity);
//        BookrevisionForkliftListActivity  = (Button) findViewById(R.id.BookrevisionForkliftListActivity);
//        BookrevisionManagerListActivity  = (Button) findViewById(R.id.BookrevisionManagerListActivity);
//        BookrevisionManagerAddActivity  = (Button) findViewById(R.id.BookrevisionManagerAddActivity);

        //入库业务  WareHousing
        //储运经理角色
        //详细任务列表 activity_warehousing_manager_task_list
        WarehousingInManagerTaskListActivity = (Button) findViewById(R.id.WarehousingInManagerTaskListActivity);
        //叉车司机角色
        //入库上架任务 activity_warehousing_forklift_list
        WarehousingInForkliftListActivity = (Button) findViewById(R.id.WarehousingInForkliftListActivity);

        //配货员
        //质检录入 activity_warehousing_distribution_quality_input
        WarehousingInDistributionQualityInputActivity = (Button) findViewById(R.id.WarehousingInDistributionQualityInputActivity);
        //核对录入  activity_warehousing_distribution_check_input
        WarehousingInDistributionCheckInputActivity = (Button) findViewById(R.id.WarehousingInDistributionCheckInputActivity);
        //满盘任务领取 activity_warehousing_distribution_full_task
        WarehousingInDistributionFullTaskActivity = (Button) findViewById(R.id.WarehousingInDistributionFullTaskActivity);
        //手动上架  activity_warehousing_distribution_manual
        WarehousingInDistributionManualActivity = (Button) findViewById(R.id.WarehousingInDistributionManualActivity);

        //////////////////////////////////////////////////////////////////////////
        //补货业务  Replenish
        ReplenishAutoDistributionListActivity = (Button) findViewById(R.id.ReplenishAutoDistributionListActivity);
        ReplenishManualDistributionAddActivity = (Button) findViewById(R.id.ReplenishManualDistributionAddActivity);

        ReplenishForkliftListActivity = (Button) findViewById(R.id.ReplenishForkliftListActivity);
        //////////////////////////////////////////////////////////////////////////
        //爆仓区上架  OutofSpace

        //任务列表 activity_outof_space_forklift_list  OutofSpaceForkliftListActivity
        OutofSpaceForkliftListActivity = (Button) findViewById(R.id.OutofSpaceForkliftListActivity);
        //库位纠错 activity_outof_space_forklift_error  OutofSpaceForkliftErrorActivity
        //OutofSpaceForkliftErrorActivity = (Button) findViewById(R.id.OutofSpaceForkliftErrorActivity);
        //任务列表 activity_outof_space_manager_list OutofSpaceManagerListActivity
        OutofSpaceManagerListActivity = (Button) findViewById(R.id.OutofSpaceManagerListActivity);

        //////////////////////////////////////////////////////////////////////////
        //质量问题下架  BatchOff

        //任务列表 activity_batch_off_forklift_list  BatchOffForkliftListActivity
        BatchOffForkliftListActivity = (Button) findViewById(R.id.BatchOffForkliftListActivity);
        //批量手动上架任务 activity_batch_off_forklift_manual_list BatchOffForkliftManualListActivity
        BatchOffForkliftManualListActivity = (Button) findViewById(R.id.BatchOffForkliftManualListActivity);
        //任务列表 activity_batch_off_manager_list  BatchOffManagerListActivity
        BatchOffManagerListActivity = (Button) findViewById(R.id.BatchOffManagerListActivity);
        //新增任务 activity_batch_off_manager_add BatchOffManagerAddActivity
        BatchOffManagerAddActivity = (Button) findViewById(R.id.BatchOffManagerAddActivity);
        //批量手动上架任务列表 activity_batch_off_manager_manual_list  BatchOffManagerManualListActivity
        BatchOffManagerManualListActivity = (Button) findViewById(R.id.BatchOffManagerManualListActivity);
        //批量手动上架  activity_batch_off_manager_manual_add  BatchOffManagerManualAddActivity
        BatchOffManagerManualAddActivity = (Button) findViewById(R.id.BatchOffManagerManualAddActivity);
        //调拨单列表 activity_batch_off_manager_transfer_order_list BatchOffManagerTransferOrderListActivity
        BatchOffManagerTransferOrderListActivity = (Button) findViewById(R.id.BatchOffManagerTransferOrderListActivity);
        //调拨单 activity_batch_off_manager_transfer_order_add BatchOffManagerTransferOrderAddActivity
        BatchOffManagerTransferOrderAddActivity = (Button) findViewById(R.id.BatchOffManagerTransferOrderAddActivity);
        //////////////////////////////////////////////////////////////////////////
        //改版调库下架
        //任务列表 activity_bookrevision_forklift_list BookrevisionForkliftListActivity
        BookrevisionForkliftListActivity = (Button) findViewById(R.id.BookrevisionForkliftListActivity);
        //任务列表 activity_bookrevision_manager_list BookrevisionManagerListActivity
        BookrevisionManagerListActivity = (Button) findViewById(R.id.BookrevisionManagerListActivity);
        //新增任务 activity_bookrevision_manager_add  BookrevisionManagerAddActivity
        BookrevisionManagerAddActivity = (Button) findViewById(R.id.BookrevisionManagerAddActivity);
        //////////////////////////////////////////////////////////////////////////
        //日常纠错盘点   DailyError

        //收发差错盘点申请
        //盘点纠错列表 activity_daily_error_manager_error_list  DailyErrorManagerErrorListActivity
        DailyErrorManagerErrorListActivity = (Button) findViewById(R.id.DailyErrorManagerErrorListActivity);
        //任务列表 activity_daily_error_manager_task_list  DailyErrorManagerTaskListActivity
        DailyErrorManagerTaskListActivity = (Button) findViewById(R.id.DailyErrorManagerTaskListActivity);
        //任务详情 activity_daily_error_manager_task_details  DailyErrorManagerTaskDetailsActivity
        DailyErrorManagerTaskDetailsActivity = (Button) findViewById(R.id.DailyErrorManagerTaskDetailsActivity);
        //盘点入库单审核 activity_daily_error_manager_warehousing_examine  DailyErrorManagerWarehousingExamineActivity
        //DailyErrorManagerWarehousingExamineActivity = (Button) findViewById(R.id.DailyErrorManagerWarehousingExamineActivity);

        //盘点入库单列表 activity_daily_error_forklift_warehousing_list DailyErrorForkliftWarehousingListActivity
        DailyErrorForkliftWarehousingListActivity = (Button) findViewById(R.id.DailyErrorForkliftWarehousingListActivity);
        //盘点入库单  activity_daily_error_forklift_warehousing_add   DailyErrorForkliftWarehousingAddActivity
        DailyErrorForkliftWarehousingAddActivity = (Button) findViewById(R.id.DailyErrorForkliftWarehousingAddActivity);
        //盘点入库任务列表 activity_daily_error_forklift_warehousing_task_list DailyErrorForkliftWarehousingTaskListActivity
        DailyErrorForkliftWarehousingTaskListActivity = (Button) findViewById(R.id.DailyErrorForkliftWarehousingTaskListActivity);

        //////////////////////////////////////////////////////////////////////////
        //手动业务      Manual
        //手动上架  activity_manual_forklift_up_shelves ManualForkliftUpShelvesActivity
        //ManualForkliftUpShelvesActivity = (Button) findViewById(R.id.ManualForkliftUpShelvesActivity);
        //手动下架  activity_manual_forklift_down_shelves ManualForkliftDownShelvesActivity
        //ManualForkliftDownShelvesActivity = (Button) findViewById(R.id.ManualForkliftDownShelvesActivity);
        //入库调整单  activity_manual_forklift_warehousing ManualForkliftWarehousingActivity
        //ManualForkliftWarehousingActivity = (Button) findViewById(R.id.ManualForkliftWarehousingActivity);
        //出库调整单  activity_manual_forklift_warehouse_out ManualForkliftWarehouseOutActivity
        //ManualForkliftWarehouseOutActivity = (Button) findViewById(R.id.ManualForkliftWarehouseOutActivity);

        //手动上下架列表    activity_manual_forklift_list ManualForkliftListActivity
        ManualForkliftListActivity = (Button) findViewById(R.id.ManualForkliftListActivity);
        //手动上架          activity_manual_forklift_up ManualForkliftUpActivity
        ManualForkliftUpActivity = (Button) findViewById(R.id.ManualForkliftUpActivity);
        //手动下架          activity_manual_forklift_dow ManualForkliftDowActivity
        ManualForkliftDowActivity = (Button) findViewById(R.id.ManualForkliftDowActivity);

        //入库调整单  activity_manual_shelves_warehouse_in ManualShelvesWarehouseInActivity
        ManualShelvesWarehouseInActivity = (Button) findViewById(R.id.ManualShelvesWarehouseInActivity);
        //出库调整单  activity_manual_shelves_warehouse_out ManualShelvesWarehouseOutActivity
        ManualShelvesWarehouseOutActivity = (Button) findViewById(R.id.ManualShelvesWarehouseOutActivity);

        //出入库调整单销售助理列表  activity_manual_shelves_list ManualShelvesListActivity
        ManualShelvesListActivity = (Button) findViewById(R.id.ManualShelvesListActivity);
        //出入库调整单销售助理修改  activity_manual_shelves_edit ManualShelvesEditActivity
        //ManualShelvesEditActivity = (Button) findViewById(R.id.ManualShelvesEditActivity);

        //出入库调整单经理审核列表  activity_manual_manager_list ManualManagerListActivity
        ManualManagerListActivity = (Button) findViewById(R.id.ManualManagerListActivity);
        //出入库调整单经理审核  activity_manual_manager_examine ManualManagerExamineActivity
        //ManualManagerExamineActivity = (Button) findViewById(R.id.ManualManagerExamineActivitybup);

        //////////////////////////////////////////////////////////////////////////
        //库位调整业务  WarehousingAdjustment

        //年度库位调区上架任务  activity_warehousing_adjustment_forklift_year_outof_space WarehousingAdjustmentForkliftYearOutofSpaceActivity
        WarehousingAdjustmentForkliftYearOutofSpaceActivity = (Button) findViewById(R.id.WarehousingAdjustmentForkliftYearOutofSpaceActivity);

        WarehousingAdjustmentDistributionAssortmentActivity = (Button) findViewById(R.id.WarehousingAdjustmentDistributionAssortmentActivity);

        WarehousingAdjustmentManagerActivity = (Button) findViewById(R.id.WarehousingAdjustmentManagerActivity);

        //////////////////////////////////////////////////////////////////////////
        //库位纠错业务 WarehousingError

        WarehousingErrorForkliftDistributionListActivity = (Button) findViewById(R.id.WarehousingErrorForkliftDistributionListActivity);
        //纠错调整单 activity_warehousing_error_forklift_distribution WarehousingErrorForkliftDistributionActivity
        WarehousingErrorForkliftDistributionActivity = (Button) findViewById(R.id.WarehousingErrorForkliftDistributionActivity);
        //WarehousingErrorForkliftDistributionAddActivity = (Button) findViewById(R.id.WarehousingErrorForkliftDistributionAddActivity);
        WarehousingErrorForkliftDistributionAdd2Activity = (Button) findViewById(R.id.WarehousingErrorForkliftDistributionAdd2Activity);

        //纠错审核  activity_warehousing_error_manager_examine WarehousingErrorManagerExamineActivity
        WarehousingErrorManagerExamineActivity = (Button) findViewById(R.id.WarehousingErrorManagerExamineActivity);
        //纠错列表  activity_warehousing_error_manager_list WarehousingErrorManagerListActivity
        WarehousingErrorManagerListActivity = (Button) findViewById(R.id.WarehousingErrorManagerListActivity);
        //////////////////////////////////////////////////////////////////////////
        //盘点业务   Inventory
        InventoryActivity = (Button) findViewById(R.id.InventoryActivity);
        //数据对比
        InventoryContrastListActivity = (Button) findViewById(R.id.InventoryContrastListActivity);

        //手工盘点   activity_inventory_manual InventoryManualActivity
        InventoryManualActivity = (Button) findViewById(R.id.InventoryManualActivity);
        //录入盘点整包整件数据  activity_inventory_input_whole  InventoryInputWholeActivity
       //InventoryInputWholeActivity = (Button) findViewById(R.id.InventoryInputWholeActivity);
        //录入盘点散书数据  activity_inventory_input_disperse InventoryInputDisperseActivity
        //InventoryInputDisperseActivity = (Button) findViewById(R.id.InventoryInputDisperseActivity);
        //盘点纠错          activity_inventory_error  InventoryErrorActivity
        //InventoryErrorActivity = (Button) findViewById(R.id.InventoryErrorActivity);
        //库位纠错调整任务  activity_inventory_error_list InventoryErrorListActivity
        InventoryTaskCreateActivity = (Button) findViewById(R.id.InventoryTaskCreateActivity);

        InventoryErrorListActivity = (Button) findViewById(R.id.InventoryErrorListActivity);

        //空位盘点         activity_inventory_empty  InventoryEmptyActivity
        InventoryEmptyActivity = (Button) findViewById(R.id.InventoryEmptyActivity);
        //数据核盘
        InventoryNuclearDiskListActivity = (Button) findViewById(R.id.InventoryNuclearDiskListActivity);

        BasicSettingsCodeDiskRulesActivity = (Button) findViewById(R.id.BasicSettingsCodeDiskRulesActivity);
        BasicSettingsBooksOutOfPrintActivity = (Button) findViewById(R.id.BasicSettingsBooksOutOfPrintActivity);
        UpdatePassWordActivity = (Button) findViewById(R.id.UpdatePassWordActivity);

        username = (TextView) findViewById(R.id.username);
        username.setText("用户账号：" + CSIMSApplication.getAppContext().getUser().getO_id() +"("+CSIMSApplication.getAppContext().getUser().getO_name()+")");
        loginOut  = (Button) findViewById(R.id.loginOut);
    }

    private void setViewListener() {
        //icon_back.setOnClickListener(this);

//        OutofSpaceForkliftListActivity.setOnClickListener(this);
//        OutofSpaceForkliftErrorActivity.setOnClickListener(this);
//        OutofSpaceManagerListActivity.setOnClickListener(this);
//        BatchOffForkliftListActivity.setOnClickListener(this);
//        BatchOffForkliftManualListActivity.setOnClickListener(this);
//        BatchOffManagerListActivity.setOnClickListener(this);
//        BatchOffManagerAddActivity.setOnClickListener(this);
//        BatchOffManagerManualListActivity.setOnClickListener(this);
//        BatchOffManagerManualAddActivity.setOnClickListener(this);
//        BatchOffManagerTransferOrderListActivity.setOnClickListener(this);
//        BatchOffManagerTransferOrderAddActivity.setOnClickListener(this);
//        BookrevisionForkliftListActivity.setOnClickListener(this);
//        BookrevisionManagerListActivity.setOnClickListener(this);
//        BookrevisionManagerAddActivity.setOnClickListener(this);


        //入库业务  WareHousing
        WarehousingInManagerTaskListActivity.setOnClickListener(this);
        //叉车司机角色
        //入库上架任务 activity_warehousing_forklift_list
        WarehousingInForkliftListActivity.setOnClickListener(this);

        //配货员
        //质检录入 activity_warehousing_distribution_quality_input
        WarehousingInDistributionQualityInputActivity.setOnClickListener(this);
        //核对录入  activity_warehousing_distribution_check_input
        WarehousingInDistributionCheckInputActivity.setOnClickListener(this);
        //满盘任务领取 activity_warehousing_distribution_full_task
        WarehousingInDistributionFullTaskActivity.setOnClickListener(this);
        //手动上架  activity_warehousing_distribution_manual
        WarehousingInDistributionManualActivity.setOnClickListener(this);

        //////////////////////////////////////////////////////////////////////////
        //补货业务  Replenish
        ReplenishAutoDistributionListActivity.setOnClickListener(this);
        ReplenishManualDistributionAddActivity.setOnClickListener(this);

        ReplenishForkliftListActivity.setOnClickListener(this);
        //////////////////////////////////////////////////////////////////////////
        //爆仓区上架  OutofSpace

        //任务列表 activity_outof_space_forklift_list  OutofSpaceForkliftListActivity
        OutofSpaceForkliftListActivity.setOnClickListener(this);
        //库位纠错 activity_outof_space_forklift_error  OutofSpaceForkliftErrorActivity
        //OutofSpaceForkliftErrorActivity.setOnClickListener(this);
        //任务列表 activity_outof_space_manager_list OutofSpaceManagerListActivity
        OutofSpaceManagerListActivity.setOnClickListener(this);

        //////////////////////////////////////////////////////////////////////////
        //质量问题下架  BatchOff

        //任务列表 activity_batch_off_forklift_list  BatchOffForkliftListActivity
        BatchOffForkliftListActivity.setOnClickListener(this);
        //批量手动上架任务 activity_batch_off_forklift_manual_list BatchOffForkliftManualListActivity
        BatchOffForkliftManualListActivity.setOnClickListener(this);
        //任务列表 activity_batch_off_manager_list  BatchOffManagerListActivity
        BatchOffManagerListActivity.setOnClickListener(this);
        //新增任务 activity_batch_off_manager_add BatchOffManagerAddActivity
        BatchOffManagerAddActivity.setOnClickListener(this);
        //批量手动上架任务列表 activity_batch_off_manager_manual_list  BatchOffManagerManualListActivity
        BatchOffManagerManualListActivity.setOnClickListener(this);
        //批量手动上架  activity_batch_off_manager_manual_add  BatchOffManagerManualAddActivity
        BatchOffManagerManualAddActivity.setOnClickListener(this);
        //调拨单列表 activity_batch_off_manager_transfer_order_list BatchOffManagerTransferOrderListActivity
        BatchOffManagerTransferOrderListActivity.setOnClickListener(this);
        //调拨单 activity_batch_off_manager_transfer_order_add BatchOffManagerTransferOrderAddActivity
        BatchOffManagerTransferOrderAddActivity.setOnClickListener(this);
        //////////////////////////////////////////////////////////////////////////
        //改版调库下架
        //任务列表 activity_bookrevision_forklift_list BookrevisionForkliftListActivity
        BookrevisionForkliftListActivity.setOnClickListener(this);
        //任务列表 activity_bookrevision_manager_list BookrevisionManagerListActivity
        BookrevisionManagerListActivity.setOnClickListener(this);
        //新增任务 activity_bookrevision_manager_add  BookrevisionManagerAddActivity
        BookrevisionManagerAddActivity.setOnClickListener(this);
        //////////////////////////////////////////////////////////////////////////
        //日常纠错盘点   DailyError

        //收发差错盘点申请
        //盘点纠错列表 activity_daily_error_manager_error_list  DailyErrorManagerErrorListActivity
        DailyErrorManagerErrorListActivity.setOnClickListener(this);
        //任务列表 activity_daily_error_manager_task_list  DailyErrorManagerTaskListActivity
        DailyErrorManagerTaskListActivity.setOnClickListener(this);
        //任务详情 activity_daily_error_manager_task_details  DailyErrorManagerTaskDetailsActivity
        DailyErrorManagerTaskDetailsActivity.setOnClickListener(this);
        //盘点入库单审核 activity_daily_error_manager_warehousing_examine  DailyErrorManagerWarehousingExamineActivity
        //DailyErrorManagerWarehousingExamineActivity.setOnClickListener(this);

        //盘点入库单列表 activity_daily_error_forklift_warehousing_list DailyErrorForkliftWarehousingListActivity
        DailyErrorForkliftWarehousingListActivity.setOnClickListener(this);
        //盘点入库单  activity_daily_error_forklift_warehousing_add   DailyErrorForkliftWarehousingAddActivity
        DailyErrorForkliftWarehousingAddActivity.setOnClickListener(this);
        //盘点入库任务列表 activity_daily_error_forklift_warehousing_task_list DailyErrorForkliftWarehousingTaskListActivity
        DailyErrorForkliftWarehousingTaskListActivity.setOnClickListener(this);

        //////////////////////////////////////////////////////////////////////////
        //手动业务      Manual

        //手动上下架列表    activity_manual_forklift_list ManualForkliftListActivity
        ManualForkliftListActivity.setOnClickListener(this);
        //手动上架          activity_manual_forklift_up ManualForkliftUpActivity
        ManualForkliftUpActivity.setOnClickListener(this);
        //手动下架          activity_manual_forklift_dow ManualForkliftDowActivity
        ManualForkliftDowActivity.setOnClickListener(this);

        //入库调整单  activity_manual_shelves_warehouse_in ManualShelvesWarehouseInActivity
        ManualShelvesWarehouseInActivity.setOnClickListener(this);
        //出库调整单  activity_manual_shelves_warehouse_out ManualShelvesWarehouseOutActivity
        ManualShelvesWarehouseOutActivity.setOnClickListener(this);

        //出入库调整单销售助理列表  activity_manual_shelves_list ManualShelvesListActivity
        ManualShelvesListActivity.setOnClickListener(this);
        //出入库调整单销售助理修改  activity_manual_shelves_edit ManualShelvesEditActivity
        //ManualShelvesEditActivity.setOnClickListener(this);

        //出入库调整单经理审核列表  activity_manual_manager_list ManualManagerListActivity
        ManualManagerListActivity.setOnClickListener(this);
        //出入库调整单经理审核  activity_manual_manager_examine ManualManagerExamineActivity
        //w sManualManagerExamineActivity.setOnClickListener(this);

        //////////////////////////////////////////////////////////////////////////
        //库位调整业务  WarehousingAdjustment

        //年度库位调区上架任务  activity_warehousing_adjustment_forklift_year_outof_space WarehousingAdjustmentForkliftYearOutofSpaceActivity
        WarehousingAdjustmentForkliftYearOutofSpaceActivity.setOnClickListener(this);
        WarehousingAdjustmentDistributionAssortmentActivity.setOnClickListener(this);
        WarehousingAdjustmentManagerActivity.setOnClickListener(this);

        //新品入库调整
        //视为同系列库位调整
        //后台封库页面
        //////////////////////////////////////////////////////////////////////////
        //库位纠错业务 WarehousingError

        WarehousingErrorForkliftDistributionListActivity.setOnClickListener(this);

        //纠错调整单 activity_warehousing_error_forklift_distribution WarehousingErrorForkliftDistributionActivity
        WarehousingErrorForkliftDistributionActivity.setOnClickListener(this);
        //WarehousingErrorForkliftDistributionAddActivity.setOnClickListener(this);
        WarehousingErrorForkliftDistributionAdd2Activity.setOnClickListener(this);

        //纠错审核  activity_warehousing_error_manager_examine WarehousingErrorManagerExamineActivity
        WarehousingErrorManagerExamineActivity.setOnClickListener(this);
        //纠错列表  activity_warehousing_error_manager_list WarehousingErrorManagerListActivity
        WarehousingErrorManagerListActivity.setOnClickListener(this);
        //////////////////////////////////////////////////////////////////////////
        //盘点业务   Inventory
        InventoryActivity.setOnClickListener(this);
        InventoryContrastListActivity.setOnClickListener(this);
        //手工盘点   activity_inventory_manual InventoryManualActivity
        InventoryManualActivity.setOnClickListener(this);
        //录入盘点整包整件数据  activity_inventory_input_whole  InventoryInputWholeActivity
        //InventoryInputWholeActivity.setOnClickListener(this);
        //录入盘点散书数据  activity_inventory_input_disperse InventoryInputDisperseActivity
        //InventoryInputDisperseActivity.setOnClickListener(this);
        //盘点纠错          activity_inventory_error  InventoryErrorActivity
        //InventoryErrorActivity.setOnClickListener(this);
        //库位纠错调整任务  activity_inventory_error_list InventoryErrorListActivity
        InventoryErrorListActivity.setOnClickListener(this);
        InventoryTaskCreateActivity.setOnClickListener(this);
        //空位盘点         activity_inventory_empty  InventoryEmptyActivity
        InventoryEmptyActivity.setOnClickListener(this);
        InventoryNuclearDiskListActivity.setOnClickListener(this);
        //码盘规则      activity_basic_settings_code_disk_rules BasicSettingsCodeDiskRulesActivity
        BasicSettingsCodeDiskRulesActivity.setOnClickListener(this);
        //图书绝版      activity_basic_settings_books_out_of_print BasicSettingsBooksOutOfPrintActivity
        BasicSettingsBooksOutOfPrintActivity.setOnClickListener(this);
        //修改密码
        UpdatePassWordActivity.setOnClickListener(this);

        loginOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.icon_back:
            //    finish();
            //    break;
            //入库业务  WareHousing
            case R.id.WarehousingInManagerTaskListActivity:
                openActivity(WarehousingInManagerTaskListActivity.class);
                break;
            case R.id.WarehousingInForkliftListActivity:
                openActivity(WarehousingInForkliftListActivity.class);
                break;

            case R.id.WarehousingInDistributionQualityInputActivity:
                openActivity(WarehousingInDistributionQualityInputActivity.class);
                break;
            case R.id.WarehousingInDistributionCheckInputActivity:
                openActivity(WarehousingInDistributionCheckInputActivity.class);
                break;
            case R.id.WarehousingInDistributionFullTaskActivity:
                openActivity(WarehousingInDistributionFullTaskActivity.class);
                break;
            case R.id.WarehousingInDistributionManualActivity:
                openActivity(WarehousingInDistributionManualActivity.class);
                break;
            //////////////////////////////////////////////////////////////////////////
            //补货业务  Replenish
            case R.id.ReplenishAutoDistributionListActivity:
                openActivity(ReplenishAutoDistributionListActivity.class);
                break;
            case R.id.ReplenishManualDistributionAddActivity:
                openActivity(ReplenishManualDistributionAddActivity.class);
                break;
            case R.id.ReplenishForkliftListActivity:
                openActivity(ReplenishForkliftListActivity.class);
                break;
            //////////////////////////////////////////////////////////////////////////
            //爆仓区上架  OutofSpace
            case R.id.OutofSpaceForkliftListActivity:
            //任务列表 activity_outof_space_forklift_list  OutofSpaceForkliftListActivity
                openActivity(OutofSpaceForkliftListActivity.class);
                break;
            //case R.id.OutofSpaceForkliftErrorActivity:
            //库位纠错 activity_outof_space_forklift_error  OutofSpaceForkliftErrorActivity
            //    openActivity(OutofSpaceForkliftErrorActivity.class);
            //   break;
            case R.id.OutofSpaceManagerListActivity:
            //任务列表 activity_outof_space_manager_list OutofSpaceManagerListActivity
                openActivity(OutofSpaceManagerListActivity.class);
                break;
            //////////////////////////////////////////////////////////////////////////
            //质量问题下架  BatchOff
            case R.id.BatchOffForkliftListActivity:
            //任务列表 activity_batch_off_forklift_list  BatchOffForkliftListActivity
                openActivity(BatchOffForkliftListActivity.class);
                break;
            case R.id.BatchOffForkliftManualListActivity:
            //批量手动上架任务 activity_batch_off_forklift_manual_list BatchOffForkliftManualListActivity
                openActivity(BatchOffForkliftManualListActivity.class);
                break;
            case R.id.BatchOffManagerListActivity:
            //任务列表 activity_batch_off_manager_list  BatchOffManagerListActivity
                openActivity(BatchOffManagerListActivity.class);
                break;
            case R.id.BatchOffManagerAddActivity:
            //新增任务 activity_batch_off_manager_add BatchOffManagerAddActivity
                openActivity(BatchOffManagerAddActivity.class);
                break;
            case R.id.BatchOffManagerManualListActivity:
            //批量手动上架任务列表 activity_batch_off_manager_manual_list  BatchOffManagerManualListActivity
                openActivity(BatchOffManagerManualListActivity.class);
                break;
            case R.id.BatchOffManagerManualAddActivity:
            //批量手动上架  activity_batch_off_manager_manual_add  BatchOffManagerManualAddActivity
                openActivity(BatchOffManagerManualAddActivity.class);
                break;
            case R.id.BatchOffManagerTransferOrderListActivity:
            //调拨单列表 activity_batch_off_manager_transfer_order_list BatchOffManagerTransferOrderListActivity
                openActivity(BatchOffManagerTransferOrderListActivity.class);
                break;
            case R.id.BatchOffManagerTransferOrderAddActivity:
            //调拨单 activity_batch_off_manager_transfer_order_add BatchOffManagerTransferOrderAddActivity
                openActivity(BatchOffManagerTransferOrderAddActivity.class);
                break;
            //////////////////////////////////////////////////////////////////////////
            //改版调库下架
            case R.id.BookrevisionForkliftListActivity:
            //任务列表 activity_bookrevision_forklift_list BookrevisionForkliftListActivity
                openActivity(BookrevisionForkliftListActivity.class);
                break;
            case R.id.BookrevisionManagerListActivity:
            //任务列表 activity_bookrevision_manager_list BookrevisionManagerListActivity
                openActivity(BookrevisionManagerListActivity.class);
                break;
            case R.id.BookrevisionManagerAddActivity:
            //新增任务 activity_bookrevision_manager_add  BookrevisionManagerAddActivity
                openActivity(BookrevisionManagerAddActivity.class);
                break;
            //////////////////////////////////////////////////////////////////////////
            //日常纠错盘点   DailyError

            //收发差错盘点申请
            case R.id.DailyErrorManagerErrorListActivity:
            //盘点纠错列表 activity_daily_error_manager_error_list  DailyErrorManagerErrorListActivity
                //openActivity(DailyErrorManagerErrorListActivity.class);
                //break;
            case R.id.DailyErrorManagerTaskListActivity:
            //任务列表 activity_daily_error_manager_task_list  DailyErrorManagerTaskListActivity
                openActivity(DailyErrorManagerTaskListActivity.class);
                break;
            case R.id.DailyErrorManagerTaskDetailsActivity:
            //任务详情 activity_daily_error_manager_task_details  DailyErrorManagerTaskDetailsActivity
                openActivity(DailyErrorManagerTaskDetailsActivity.class);
                break;
            case R.id.DailyErrorManagerWarehousingExamineActivity:
            //盘点入库单审核 activity_daily_error_manager_warehousing_examine  DailyErrorManagerWarehousingExamineActivity
                //openActivity(DailyErrorManagerWarehousingExamineActivity.class);
                break;
            case R.id.DailyErrorForkliftWarehousingListActivity:
            //盘点入库单列表 activity_daily_error_forklift_warehousing_list DailyErrorForkliftWarehousingListActivity
                openActivity(DailyErrorForkliftWarehousingListActivity.class);
                break;
            case R.id.DailyErrorForkliftWarehousingAddActivity:
            //盘点入库单  activity_daily_error_forklift_warehousing_add   DailyErrorForkliftWarehousingAddActivity
                openActivity(DailyErrorForkliftWarehousingAddActivity.class);
                break;
            case R.id.DailyErrorForkliftWarehousingTaskListActivity:
            //盘点入库任务列表 activity_daily_error_forklift_warehousing_task_list DailyErrorForkliftWarehousingTaskListActivity
                openActivity(DailyErrorForkliftWarehousingTaskListActivity.class);
                break;
            //////////////////////////////////////////////////////////////////////////
            //手动业务      Manual
            //case R.id.ManualForkliftUpShelvesActivity:
            //手动上架  activity_manual_forklift_up_shelves ManualForkliftUpShelvesActivity
                //openActivity(ManualForkliftUpShelvesActivity.class);
                //break;
            //case R.id.ManualForkliftDownShelvesActivity:
            //手动下架  activity_manual_forklift_down_shelves ManualForkliftDownShelvesActivity
                //openActivity(ManualForkliftDownShelvesActivity.class);
                //break;
            //case R.id.ManualForkliftWarehousingActivity:
            //入库调整单  activity_manual_forklift_warehousing ManualForkliftWarehousingActivity
                //openActivity(ManualForkliftWarehousingActivity.class);
                //break;
            //case R.id.ManualForkliftWarehouseOutActivity:
            //出库调整单  activity_manual_forklift_warehouse_out ManualForkliftWarehouseOutActivity
                //openActivity(ManualForkliftWarehouseOutActivity.class);
                //break;

            //手动上下架列表    activity_manual_forklift_list ManualForkliftListActivity
            case R.id.ManualForkliftListActivity:
                openActivity(ManualForkliftListActivity.class);
                break;
            //手动上架          activity_manual_forklift_up ManualForkliftUpActivity
            case R.id.ManualForkliftUpActivity:
                openActivity(ManualForkliftUpActivity.class);
                break;
            //手动下架          activity_manual_forklift_dow ManualForkliftDowActivity
            case R.id.ManualForkliftDowActivity:
                openActivity(ManualForkliftDowActivity.class);
                break;

            //入库调整单  activity_manual_shelves_warehouse_in ManualShelvesWarehouseInActivity
            case R.id.ManualShelvesWarehouseInActivity:
                openActivity(ManualShelvesWarehouseInActivity.class);
                break;
            //出库调整单  activity_manual_shelves_warehouse_out ManualShelvesWarehouseOutActivity
            case R.id.ManualShelvesWarehouseOutActivity:
                openActivity(ManualShelvesWarehouseOutActivity.class);
                break;

            //出入库调整单销售助理列表  activity_manual_shelves_list ManualShelvesListActivity
            case R.id.ManualShelvesListActivity:
                openActivity(ManualShelvesListActivity.class);
                break;
            //出入库调整单销售助理修改  activity_manual_shelves_edit ManualShelvesEditActivity

            //出出库调整单经理审核列表  activity_manual_manager_list ManualManagerListActivity
            case R.id.ManualManagerListActivity:
                openActivity(ManualManagerListActivity.class);
                break;
            //出入库调整单经理审核  activity_manual_manager_examine ManualManagerExamineActivity

            //////////////////////////////////////////////////////////////////////////
            //库位调整业务  WarehousingAdjustment
            case R.id.WarehousingAdjustmentForkliftYearOutofSpaceActivity:
            //年度库位调区上架任务  activity_warehousing_adjustment_forklift_year_outof_space WarehousingAdjustmentForkliftYearOutofSpaceActivity
                openActivity(WarehousingAdjustmentForkliftYearOutofSpaceActivity.class);
                break;
            case R.id.WarehousingAdjustmentDistributionAssortmentActivity:
                //年度库位调区上架任务  activity_warehousing_adjustment_forklift_year_outof_space WarehousingAdjustmentForkliftYearOutofSpaceActivity
                openActivity(WarehousingAdjustmentDistributionAssortmentActivity.class);
                break;
            case R.id.WarehousingAdjustmentManagerActivity:
                //年度库位调区上架任务  activity_warehousing_adjustment_forklift_year_outof_space WarehousingAdjustmentForkliftYearOutofSpaceActivity
                openActivity(WarehousingAdjustmentManagerActivity.class);
                break;
            //新品入库调整
            //视为同系列库位调整
            //后台封库页面
            //////////////////////////////////////////////////////////////////////////
            //库位纠错业务 WarehousingError
            case R.id.WarehousingErrorForkliftDistributionListActivity:
                //纠错调整单 activity_warehousing_error_forklift_distribution WarehousingErrorForkliftDistributionActivity
                openActivity(WarehousingErrorForkliftDistributionListActivity.class);
                break;
            //case R.id.WarehousingErrorForkliftDistributionAddActivity:
                //纠错调整单 activity_warehousing_error_forklift_distribution WarehousingErrorForkliftDistributionActivity
                //openActivity(WarehousingErrorForkliftDistributionAddActivity.class);
                //break;
            case R.id.WarehousingErrorForkliftDistributionAdd2Activity:
                //纠错调整单 activity_warehousing_error_forklift_distribution WarehousingErrorForkliftDistributionActivity
                openActivity(WarehousingErrorForkliftDistributionAdd2Activity.class);
                break;
            case R.id.WarehousingErrorForkliftDistributionActivity:
            //纠错调整单 activity_warehousing_error_forklift_distribution WarehousingErrorForkliftDistributionActivity
                openActivity(WarehousingErrorForkliftDistributionActivity.class);
                break;
            case R.id.WarehousingErrorManagerExamineActivity:
            //纠错审核  activity_warehousing_error_manager_examine WarehousingErrorManagerExamineActivity
                openActivity(WarehousingErrorManagerExamineActivity.class);
                break;
            case R.id.WarehousingErrorManagerListActivity:
            //纠错列表  activity_warehousing_error_manager_list WarehousingErrorManagerListActivity
                openActivity(WarehousingErrorManagerListActivity.class);
                break;
            //////////////////////////////////////////////////////////////////////////
            //盘点业务   Inventory
            case R.id.InventoryActivity:
                //手工盘点   activity_inventory_manual InventoryManualActivity
                openActivity(InventoryActivity.class);
                break;
            case R.id.InventoryContrastListActivity:
                //手工盘点   activity_inventory_manual InventoryManualActivity
                openActivity(InventoryContrastListActivity.class);
                break;
            case R.id.InventoryManualActivity:
            //手工盘点   activity_inventory_manual InventoryManualActivity
                openActivity(InventoryManualListActivity.class);
                break;
            //case R.id.InventoryInputWholeActivity:
            //录入盘点整包整件数据  activity_inventory_input_whole  InventoryInputWholeActivity
                //openActivity(InventoryInputWholeActivity.class);
                //break;
            //case R.id.InventoryInputDisperseActivity:
            //录入盘点散书数据  activity_inventory_input_disperse InventoryInputDisperseActivity
                //openActivity(InventoryInputDisperseActivity.class);
                //break;
            //case R.id.InventoryErrorActivity:
            //盘点纠错          activity_inventory_error  InventoryErrorActivity
            //    openActivity(InventoryErrorActivity.class);
            //    break;

            case R.id.InventoryTaskCreateActivity:
                //库位纠错调整任务  activity_inventory_error_list InventoryErrorListActivity
                openActivity(InventoryTaskCreateActivity.class);
                break;
            case R.id.InventoryErrorListActivity:
            //库位纠错调整任务  activity_inventory_error_list InventoryErrorListActivity
                openActivity(InventoryErrorListActivity.class);
                break;
            case R.id.InventoryEmptyActivity:
            //空位盘点         activity_inventory_empty  InventoryEmptyActivity
                openActivity(InventoryEmptyActivity.class);
                break;
            case R.id.InventoryNuclearDiskListActivity:
                //数据核盘         activity_inventory_empty  InventoryEmptyActivity
                openActivity(InventoryNuclearDiskListActivity.class);
                break;
            //基础设置 Basic settings
            case R.id.BasicSettingsCodeDiskRulesActivity:
                openActivity(BasicSettingsCodeDiskRulesActivity.class);
                break;
            case R.id.BasicSettingsBooksOutOfPrintActivity:
                openActivity(BasicSettingsBooksOutOfPrintActivity.class);
                break;
            case R.id.UpdatePassWordActivity:
                openActivity(UpdatePassWordActivity.class);
                break;
            case R.id.loginOut:
                CustomDialogStyle2.Builder builder = new CustomDialogStyle2.Builder(TestActivity.this);
                builder.setTitle("温馨提示")
                        .setMessage("确定要退出吗？")
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
                                //Toast.makeText(getActivity(), "点击了确定按钮", Toast.LENGTH_SHORT).show();
                                CSIMSApplication.getAppContext().clearUser();
                                CSIMSApplication.getAppContext().clearUserToken();
                                ToastUtil.show(TestActivity.this, "退出成功");
                                //openActivity(LoginActivity.class);
                                finish();
                            }
                        })
                        .create()
                        .show();
                break;

        }
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        ToastUtil.show(this,"访问失败"+response.getException().getMessage());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: "+this.getClass().getSimpleName());
        stopService(pushIntent);
    }

}
