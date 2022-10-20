package com.zitian.csims.common;

import com.zitian.csims.application.CSIMSApplication;

public class AppConstant {

    /**
     * SharePreference存储路径
     */
    public static final String SHARE_PATH = "CSIMS";
    public static final String USER_JSON = "user_json";
    public static final String ROLE_JSON = "role_json";
    public static final String USER_TOKEN = "user_token";
    public static final String SERVER_IP = "SERVER_IP";

    public static final String ProdNo_JSON = "ProdNo_JSON";
    public static final String Factory_JSON = "Factory_JSON";
    public static final String Person_JSON = "Person_JSON";

    /**
     * 数据请求基地址
     */
    //public static final String BASE_URL = "http://192.168.123.171:51596/";
    //public static final String BASE_URL = "http://192.168.1.104:51596/";
    //public static final String BASE_URL = "http://localhost:4550/";
    //public static final String BASE_URL = "http://47.105.97.110/";
    //public static final String BASE_URL = "http://192.168.31.65:51596/";
    //public  static final String BASE_URL = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/";
    //public static final String BASE_URL = "http://localhost:4550/";
    /**
     * 登录地址
     */
    public static final String LOGIN()
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/LoginHandler.ashx?action=Login" ;
    }
    //产品编号
    public static final String AppProdNo()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetProductList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetProductList";
    }
    //库位号
    public static final String AppAreaNo()//= "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/baseInfoHandler.ashx?action=List_WareHouseGroup";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/baseInfoHandler.ashx?action=List_WareHouseGroup";
    }
    //印厂
    public static final String AppFactory()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/baseInfoHandler.ashx?action=GetBase_Factories";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/baseInfoHandler.ashx?action=GetBase_Factories";
    }
    //Forklift 叉车
    //Manager 经理
    //Distribution 配货
    //sale 销售助理
    //////////////////////////////////////////////////////////////////////////
    //入库业务  WareHousingIn

    //储运经理角色
    //详细任务列表 activity_warehousing_in_manager_task_list  WarehousingInManagerTaskListActivity
    public static final String WarehousingInManagerTaskList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String WarehousingInManagerTaskList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=CancelTaskJL";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=CancelTaskJL";
    }
    public static final String WarehousingInManagerTaskList3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=RestoreTaskJL";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=RestoreTaskJL";
    }
    //叉车司机角色
    //入库上架任务 activity_warehousing_in_forklift_list WarehousingInForkliftListActivity
    public static final String WarehousingInForkliftList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String WarehousingInForkliftList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=CancelTaskSj";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=CancelTaskSj";
    }
    public static final String WarehousingInForkliftList3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ReceiveTaskSJ";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ReceiveTaskSJ";
    }
    public static final String WarehousingInForkliftList4()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ComplateTaskSJ";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ComplateTaskSJ";
    }

    //配货员
    //质检录入 activity_warehousing_in_distribution_quality_input WarehousingInDistributionQualityInputActivity
    public static final String WarehousingInDistributionQualityInput()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetName";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetName";
    }
    public static final String WarehousingInDistributionQualityInput2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=CheckWareHouse";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=CheckWareHouse";
    }
    //核对录入  activity_warehousing_in_distribution_check_input WarehousingInDistributionCheckInputActivity
    //public static final String WarehousingInDistributionCheckInput = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=GetInputWareHouse";
    public static final String WarehousingInDistributionCheckInput2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ClickInputWareHouse";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ClickInputWareHouse";
    }
    //满盘任务领取 activity_warehousing_in_distribution_full_task WarehousingInDistributionFullTaskActivity
    public static final String WarehousingInDistributionFullTask()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ReceiveTaskPH";   //领取
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ReceiveTaskPH";
    }
    public static final String WarehousingInDistributionFullTask2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ComplateTaskPH"; //完成
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ComplateTaskPH";
    }
    public static final String WarehousingInDistributionFullTask3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ComplatePlatePH"; //合盘
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ComplatePlatePH";
    }
    public static final String WarehousingInDistributionFullTask4()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=PauseTaskPH"; //暂停
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=PauseTaskPH";
    }
    public static final String WarehousingInDistributionFullTask5()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ManualloadingNo"; //获取库位号
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=ManualloadingNo";
    }
    //手动上架  activity_warehousing_in_distribution_manual WarehousingInDistributionManualActivity
    public static final String WarehousingInDistributionManual()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=Manualloading"; //手动
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/wareHousingHandler.ashx?action=Manualloading";
    }

    //////////////////////////////////////////////////////////////////////////
    //补货业务  Replenish

    //自动补货列表 activity_replenish_auto_distribution_list  ReplenishAutoDistributionListActivity
    public static final String ReplenishAutoDistributionList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=GetTaskListPH";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=GetTaskListPH";
    }
    public static final String ReplenishAutoDistributionList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=AutoReplenish";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=AutoReplenish";
    }
    public static final String ReplenishAutoDistributionList3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=CompleteTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=CompleteTask";
    }

    public static final String RodNoList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=ProdNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=ProdNoList";
    }

    //手动补货  activity_replenish_manual_distribution_add    ReplenishManualDistributionAddActivity
    public static final String ReplenishManualDistributionAdd()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetName";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetName";
    }
    public static final String ReplenishManualDistributionAdd2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=ManualReplenish";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=ManualReplenish";
    }
    //public static final String ReplenishManualDistributionAdd3 = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=ProdNoList";
    //补货任务列表 activity_replenish_forklift_list           ReplenishForkliftListActivity
    public static final String ReplenishForkliftList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=RecieveTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=RecieveTask";
    }
    //public static final String ReplenishForkliftList2 = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ReplenishHandler.ashx?action=RecieveTask";

    //////////////////////////////////////////////////////////////////////////
    //爆仓区上架  OutofSpace

    //任务列表 activity_outof_space_forklift_list  OutofSpaceForkliftListActivity
    public static final String OutofSpaceForkliftList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String OutofSpaceForkliftList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/OutofSpaceHandler.ashx?action=RecieveTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/OutofSpaceHandler.ashx?action=RecieveTask";
    }
    public static final String OutofSpaceForkliftList3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/OutofSpaceHandler.ashx?action=EditWareHouse";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/OutofSpaceHandler.ashx?action=EditWareHouse";
    }

    //库位纠错 activity_outof_space_forklift_error  OutofSpaceForkliftErrorActivity
    public static final String OutofSpaceForkliftError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=AddTable_errList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=AddTable_errList";
    }
    public static final String OutofSpaceForkliftError2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=Get_DareaByProdNo";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=Get_DareaByProdNo";
    }
    public static final String OutofSpaceForkliftError3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=EditTable_ErrLists";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=EditTable_ErrLists";
    }
    public static final String OutofSpaceForkliftError4()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=GetWareHouseByNum";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=GetWareHouseByNum";
    }

    //任务列表 activity_outof_space_manager_list OutofSpaceManagerListActivity
    public static final String OutofSpaceManagerList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String OutofSpaceManagerList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    //////////////////////////////////////////////////////////////////////////
    //质量问题下架  BatchOff

    //任务列表 activity_batch_off_forklift_list  BatchOffForkliftListActivity
    public static final String BatchOffForkliftList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String BatchOffForkliftList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=RecieveTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=RecieveTask";
    }
    //批量手动上架任务 activity_batch_off_forklift_manual_list BatchOffForkliftManualListActivity
    public static final String BatchOffForkliftManualList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String BatchOffForkliftManualList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=RecieveTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=RecieveTask";
    }
    public static final String BatchOffForkliftManualList3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=CompleteBatchUp";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=CompleteBatchUp";
    }

    //任务列表 activity_batch_off_manager_list  BatchOffManagerListActivity
    public static final String BatchOffManagerList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String BatchOffManagerList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=CompleteTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=CompleteTask";
    }

    //新增任务 activity_batch_off_manager_add BatchOffManagerAddActivity
    public static final String BatchOffManagerAdd()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=GetWareHouseByprodNo";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=GetWareHouseByprodNo";
    }
    public static final String BatchOffManagerAdd2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=AddBatchOff";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=AddBatchOff";
    }
    //批量手动上架任务列表 activity_batch_off_manager_manual_list  BatchOffManagerManualListActivity
    public static final String BatchOffManagerManualList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=BatchReleaseList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=BatchReleaseList";
    }
    public static final String BatchOffManagerManualList2()//= "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=CancelTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=CancelTask";
    }
    //批量手动上架  activity_batch_off_manager_manual_add  BatchOffManagerManualAddActivity
    public static final String BatchOffManagerManualAdd()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=BatchUp";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=BatchUp";
    }
    public static final String BatchOffManagerManualAdd2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/base_operatorsHandler.ashx?action=GetPersonList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/base_operatorsHandler.ashx?action=GetPersonList";
    }
    //调拨单列表 activity_batch_off_manager_transfer_order_list BatchOffManagerTransferOrderListActivity
    public static final String BatchOffManagerTransferOrderList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=TransferList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=TransferList";
    }
    //调拨单 activity_batch_off_manager_transfer_order_add BatchOffManagerTransferOrderAddActivity
    public static final String BatchOffManagerTransferOrderAdd()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=EditTransfer";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=EditTransfer";
    }
    public static final String BatchOffManagerTransferOrderAdd2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=AddTransfer";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/batchOffHandler.ashx?action=AddTransfer";
    }

    //////////////////////////////////////////////////////////////////////////
    //改版调库下架
    //任务列表 activity_bookrevision_forklift_list BookrevisionForkliftListActivity
    public static final String BookrevisionForkliftList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String BookrevisionForkliftList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/BookrevisionHandler.ashx?action=RecieveTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/BookrevisionHandler.ashx?action=RecieveTask";
    }
    //任务列表 activity_bookrevision_manager_list BookrevisionManagerListActivity
    public static final String BookrevisionManagerList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String BookrevisionManagerList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/BookrevisionHandler.ashx?action=CompleteRevision";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/BookrevisionHandler.ashx?action=CompleteRevision";
    }
    //新增任务 activity_bookrevision_manager_add  BookrevisionManagerAddActivity
    public static final String BookrevisionManagerAdd()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/BookrevisionHandler.ashx?action=GetWareHouseByprodNo";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/BookrevisionHandler.ashx?action=GetWareHouseByprodNo";
    }
    public static final String BookrevisionManagerAdd2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/BookrevisionHandler.ashx?action=AddRevisionTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/BookrevisionHandler.ashx?action=AddRevisionTask";
    }
    //////////////////////////////////////////////////////////////////////////
    //日常纠错盘点   DailyError
    //收发差错盘点申请
    //盘点纠错列表 activity_daily_error_manager_error_list  DailyErrorManagerErrorListActivity
    //public static final String DailyErrorManagerErrorList = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=DailyInventoryList";
    //任务列表 activity_daily_error_manager_task_list  DailyErrorManagerTaskListActivity
    public static final String DailyErrorManagerTaskList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=DailyInventoryList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=DailyInventoryList";
    }
    //任务详情 activity_daily_error_manager_task_details  DailyErrorManagerTaskDetailsActivity
    public static final String DailyErrorManagerTaskDetails()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=GetwareHouseByProd";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=GetwareHouseByProd";
    }
    public static final String DailyErrorManagerTaskDetails2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=InventoryClosure";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=InventoryClosure";
    }
    public static final String DailyErrorManagerTaskDetails3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=InventoryPause";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=InventoryPause";
    }
    public static final String DailyErrorManagerTaskDetails4()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=UrgentGeneration";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=UrgentGeneration";
    }
    public static final String DailyErrorManagerTaskDetails5()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=InventoryClear";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=InventoryClear";
    }
    //盘点入库单审核 activity_daily_error_manager_warehousing_examine  DailyErrorManagerWarehousingExamineActivity

    //盘点入库单列表 activity_daily_error_forklift_warehousing_list DailyErrorForkliftWarehousingListActivity
    public static final String DailyErrorForkliftWarehousingList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    public static final String DailyErrorForkliftWarehousingList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=CompleteTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=CompleteTask";
    }
    //盘点入库单  activity_daily_error_forklift_warehousing_add   DailyErrorForkliftWarehousingAddActivity
    public static final String DailyErrorForkliftWarehousingAdd()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=DailyInventoryInput";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/DailyInventoryHandler.ashx?action=DailyInventoryInput";
    }

    //盘点入库任务列表 activity_daily_error_forklift_warehousing_task_list DailyErrorForkliftWarehousingTaskListActivity
    public static final String DailyErrorForkliftWarehousingTaskList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=Table_tasksByUser";
    }
    //////////////////////////////////////////////////////////////////////////
    //手动业务      Manual

    //手动上下架列表    activity_manual_forklift_list ManualForkliftListActivity
    public static final String ManualForkliftListActivity()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=RecieveTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=RecieveTask";
    }
    public static final String ManualForkliftListActivity2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=CompleteTask";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=CompleteTask";
    }

    //手动上架          activity_manual_forklift_up ManualForkliftUpActivity
    public static final String ManualForkliftUpActivity()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=ManualLoading";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=ManualLoading";
    }
    //手动下架          activity_manual_forklift_dow ManualForkliftDowActivity
    public static final String ManualForkliftDowActivity()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=ManualDown";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=ManualDown";
    }

    //入库调整单  activity_manual_shelves_warehouse_in ManualShelvesWarehouseInActivity
    public static final String ManualShelvesWarehouseInActivity()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=Adjustment";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=Adjustment";
    }
    public static final String ManualShelvesWarehouseInActivity2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetProductByCondition";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetProductByCondition";
    }
    public static final String ManualShelvesWarehouseInActivity3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetWareHose";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetWareHose";
    }
    //出库调整单  activity_manual_shelves_warehouse_out ManualShelvesWarehouseOutActivity
    public static final String ManualShelvesWarehouseOutActivity()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=Adjustment";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=Adjustment";
    }
    public static final String ManualShelvesWarehouseOutActivity2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetProductByCondition";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/productHandler.ashx?action=GetProductByCondition";
    }

    //出入库调整单销售助理列表  activity_manual_shelves_list ManualShelvesListActivity
    public static final String ManualShelvesList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=AdjustmentList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=AdjustmentList";
    }
    //出入库调整单销售助理修改  activity_manual_shelves_edit ManualShelvesEditActivity
    public static final String ManualShelvesEdit()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=UpdateAdjustment";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=UpdateAdjustment";
    }

    //出出库调整单经理审核列表  activity_manual_manager_list ManualManagerListActivity
    public static final String ManualManagerList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=AdjustmentList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=AdjustmentList";
    }
    //出入库调整单经理审核  activity_manual_manager_examine ManualManagerExamineActivity
    public static final String ManualManagerExamineActivity()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=ReviewAdjustment";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/ManualHandler.ashx?action=ReviewAdjustment";
    }
    //////////////////////////////////////////////////////////////////////////
    //库位调整业务  WarehousingAdjustment

    //叉车司机---库位调区上架任务  activity_warehousing_adjustment_forklift_year_outof_space WarehousingAdjustmentForkliftYearOutofSpaceActivity
    public static final String WarehousingAdjustmentForkliftYearOutofSpace()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=RecieveTaskTQ";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=RecieveTaskTQ";
    }
    public static final String WarehousingAdjustmentForkliftYearOutofSpace2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=CompleteTaskTQ";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=CompleteTaskTQ";
    }

    //配货员---调品种任务列表
    public static final String WarehousingAdjustmentDistributionAssortment()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=GetColumn";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=GetColumn";
    }
    public static final String WarehousingAdjustmentDistributionAssortment2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=RecieveTaskTPZ";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=RecieveTaskTPZ";
    }
    public static final String WarehousingAdjustmentDistributionAssortment3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=CompleteTaskTPZ";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=CompleteTaskTPZ";
    }
    //经理---库位调整
    public static final String WarehousingAdjustmentManager()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=InventoryClosure";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=InventoryClosure";
    }
    public static final String WarehousingAdjustmentManager2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=InventoryPause";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=InventoryPause";
    }
    public static final String WarehousingAdjustmentManager3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=InventoryClear";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/AdjustmentWareHouse.ashx?action=InventoryClear";
    }

    //////////////////////////////////////////////////////////////////////////
    //库位纠错业务 WarehousingError
    //纠错调整单 activity_warehousing_error_forklift_distribution_list WarehousingErrorForkliftDistributionListActivity
    //纠错调整单 activity_warehousing_error_forklift_distribution WarehousingErrorForkliftDistributionActivity
    public static final String WarehousingErrorForkliftDistributionListActivity()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=GetTable_errList";
    {
        return  "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=GetTable_errList";
    }

    //经理纠错列表  activity_warehousing_error_manager_list WarehousingErrorManagerListActivity
    public static final String WarehousingErrorManagerList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=GetTable_errList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=GetTable_errList";
    }
    //经理纠错审核  activity_warehousing_error_manager_examine WarehousingErrorManagerExamineActivity
    public static final String WarehousingErrorManagerExamine()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=ReviewTable_ErrLists";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_errHandler.ashx?action=ReviewTable_ErrLists";
    }

    //////////////////////////////////////////////////////////////////////////
    //盘点业务   Inventory
    public static final String Inventory1()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryHandler.ashx?action=StartInventory";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryHandler.ashx?action=StartInventory";
    }
    public static final String Inventory2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryHandler.ashx?action=PauseInventory";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryHandler.ashx?action=PauseInventory";
    }
    public static final String Inventory3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryHandler.ashx?action=ComplateInventory";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryHandler.ashx?action=ComplateInventory";
    }
    public static final String Inventory4()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryHandler.ashx?action=StatusInventory";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryHandler.ashx?action=StatusInventory";
    }

    //手工盘点
    //整件整包
    //散书
    //手工   activity_inventory InventoryActivity
    //手工盘点   activity_inventory_manual InventoryManualActivity
    public static final String InventoryManual()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameManualWholeNo";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameManualWholeNo";
    }
    public static final String InventoryManual2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameManualScatteredNo";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameManualScatteredNo";
    }
    public static final String InventoryManual3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndManualWhole";
    {
        return  "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndManualWhole";
    }
    public static final String InventoryManual4()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndManualScattered";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndManualScattered";
    }
    public static final String InventoryManual5()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=StartManualWholeLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=StartManualWholeLog";
    }
    public static final String InventoryManual6()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=StartManualScatteredLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=StartManualScatteredLog";
    }

    public static final String InventoryManual7()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndManualWholeLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndManualWholeLog";
    }
    public static final String InventoryManual8()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndManualScatteredLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndManualScatteredLog";
    }

    public static final String InventoryManual9()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndManualScatteredLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameLockManualWhole";
    }
    public static final String InventoryManual10()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndManualScatteredLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameLockManualScattered";
    }
    public static final String InventoryManual11()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndManualScatteredLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameLockEmpty";
    }
    public static final String InventoryManual12()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndManualScatteredLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameLockNuclearDisk";
    }

    //录入盘点整包整件数据  activity_inventory_input_whole  InventoryInputWholeActivity
    public static final String CheckManualWholeLog()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=CheckManualWholeLog";
    }

    public static final String InventoryInputWhole()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    }

    public static final String InventoryInputWholeError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWholeError";
    }
    public static final String GetInventoryInputWholeError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndManualWholeError";
    }
    //录入盘点散书数据  activity_inventory_input_disperse InventoryInputDisperseActivity
    public static final String CheckManualScatteredLog()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=CheckManualScatteredLog";
    }

    public static final String InventoryInputDisperse()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualScattered";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualScattered";
    }
    public static final String GetInventoryInputDisperseError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndManualScatteredError";
    }
    public static final String InventoryInputDisperseError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualScatteredError";
    }
    public static final String DoInventoryDataAndManualScatteredAddItemList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=DoInventoryDataAndManualScatteredAddItemList";
    }
    public static final String DoInventoryDataAndManualScatteredAddItemEdit()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=DoInventoryDataAndManualScatteredAddItemEdit";
    }

    //空位
    public static final String CheckEmptyLog()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=CheckEmptyLog";
    }

    public static final String InventoryEmpty()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameEmptyNo";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameEmptyNo";
    }
    public static final String InventoryEmpty2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=StartEmptyLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=StartEmptyLog";
    }
    public static final String InventoryEmpty3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndEmptyLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndEmptyLog";
    }
    public static final String InventoryEmpty4()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndEmpty";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndEmpty";
    }
    public static final String InventoryEmpty5()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndEmpty";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndEmpty";
    }

    public static final String InventoryEmptyError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndEmpty";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndEmptyAndCondition";
    }

    public static final String InventoryEmptyError2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndEmpty";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndEmptyError";
    }
    //public static final String InventoryEmpty2 = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndEmpty";
    //public static final String InventoryEmpty3 = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=UpdateWareHouseNoStatus";
    //public static final String InventoryEmpty4 = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=UpdateWareHouseNoStatusByOne";
    //public static final String InventoryEmpty5 = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=SavaData";


    //核盘
    public static final String CheckNuclearDiskLog()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=CheckNuclearDiskLog";
    }

    public static final String InventoryNuclearDiskList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameNuclearDiskNo";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameNuclearDiskNo";
    }
    public static final String InventoryNuclearDiskList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndNuclearDisk";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndNuclearDisk";
    }
    public static final String InventoryNuclearDiskList3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndNuclearDisk";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndNuclearDisk";
    }

    public static final String InventoryNuclearDiskList4()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=StartNuclearDiskLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=StartNuclearDiskLog";
    }
    public static final String InventoryNuclearDiskList5()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndNuclearDiskLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndNuclearDiskLog";
    }
    public static final String InventoryNuclearDiskList6()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=EndNuclearDiskLog";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetInventoryDataAndNuclearDiskByOne";
    }

    //数据对比
    public static final String InventoryContrastList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameManualWholeYes";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameManualWholeYes";
    }
    public static final String InventoryContrastList2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameManualScatteredYes";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameManualScatteredYes";
    }
    public static final String InventoryContrastList3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameEmptyYes";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=RowNameEmptyYes";
    }
    public static final String InventoryContrastList4()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndManualWhole";
    }
    public static final String InventoryContrastList5()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndManualScattered";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndManualScattered";
    }
    public static final String InventoryContrastList6()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndEmpty";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndEmpty";
    }
    public static final String InventoryContrastList7()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndEmpty";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=DoInventoryContrastDataAndManualScatteredAddItemStatus";
    }
    //整件整包
    public static final String InventoryContrastInputWhole()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoContrastInventoryDataAndManualWhole";
    }

    public static final String InventoryContrastInputWholeError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoContrastInventoryDataAndManualWholeError";
    }
    public static final String GetContrastInventoryDataAndManualWholeError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndManualWholeError";
    }
    //散书
    public static final String InventoryContrastInputDisperse()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoContrastInventoryDataAndManualScattered";
    }

    public static final String InventoryContrastInputDisperseError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoContrastInventoryDataAndManualScatteredError";
    }

    public static final String GetContrastInventoryDataAndManualScatteredError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndManualScatteredError";
    }

    public static final String DoInventoryContrastDataAndManualScatteredAddItemList()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=DoInventoryContrastDataAndManualScatteredAddItemList";
    }
    public static final String DoInventoryContrastDataAndManualScatteredAddItemEdit()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoInventoryDataAndManualWhole";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryLogHandler.ashx?action=DoInventoryContrastDataAndManualScatteredAddItemEdit";
    }

    //备货
    public static final String InventoryContrastEmpty()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoContrastInventoryDataAndEmpty";
    }

    public static final String InventoryContrastEmptyError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=DoContrastInventoryDataAndEmptyError";
    }
    public static final String GetContrastInventoryDataAndEmptyError()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryDataHandler.ashx?action=GetContrastInventoryDataAndEmptyError";
    }

    //盘点生成任务
    public static final String InventoryTaskHandler()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryTaskHandler.ashx?action=TaskList";
    }

    public static final String InventoryTaskHandler2()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryTaskHandler.ashx?action=ReceiveTask";
    }

    public static final String InventoryTaskHandler3()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryTaskHandler.ashx?action=ComplateTask";
    }


    public static final String InventoryTaskCreateManualWhole()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryTaskCreateManualWholeHandler.ashx?action=CreateTask";
    }

    public static final String InventoryTaskCreateManualScattered()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryTaskCreateManualScatteredHandler.ashx?action=CreateTask";
    }
    public static final String InventoryTaskCreateManualWholeAndScattered()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryTaskCreateManualWholeAndScatteredHandler.ashx?action=CreateTask";
    }
    public static final String InventoryTaskCreateEmpty()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryTaskCreateEmptyHandler.ashx?action=CreateTask";
    }

    public static final String InventoryTaskCreateNuclearDisk()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryEmptyHandler.ashx?action=GetWareHouseNoList";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/InventoryTaskCreateNuclearDiskHandler.ashx?action=CreateTask";
    }

    //////////////////////////////////////////////////////////////////////////
    //基础设置 Basic settings

    //储运部经理
    //码盘规则      activity_basic_settings_code_disk_rules BasicSettingsCodeDiskRulesActivity
    //图书绝版      activity_basic_settings_books_out_of_print BasicSettingsBooksOutOfPrintActivity
    public static final String GetUnOpenTasks()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=GetUnOpenTasks";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/Table_tasksHandler.ashx?action=GetUnOpenTasks";
    }
    public static final String GetMessage()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/MessageHandler.ashx?action=GetMessage";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/MessageHandler.ashx?action=GetMessage";
    }
    public static final String BasicSettingsCodeDiskRules()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/baseInfoHandler.ashx?action=Edit_CodeRule";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/baseInfoHandler.ashx?action=Edit_CodeRule";
    }
    public static final String BasicSettingsBooksOutOfPrint()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/baseInfoHandler.ashx?action=Edit_IsClose";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/baseInfoHandler.ashx?action=Edit_IsClose";
    }
    //activity_update_password UpdatePassWordActivity
    public static final String UpdatePassWord()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/base_operatorsHandler.ashx?action=ChangePassword";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/base_operatorsHandler.ashx?action=ChangePassword";
    }
    public static final String ClearMachine()// = "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/base_operatorsHandler.ashx?action=ChangePassword";
    {
        return "http://"+ CSIMSApplication.getAppContext().getServerIp()+"/" + "ajax/base_operatorsHandler.ashx?action=ClearMachine";
    }

}
