package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class WarehousingInDistributionFullTask {
    private int result;
    private String msg;
    private WarehousingInDistributionFullTask.Bean data;
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WarehousingInDistributionFullTask.Bean getData() {
        return data;
    }
    public void setData(WarehousingInDistributionFullTask.Bean data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        /// <summary>
        /// 标识
        /// </summary>
        private int t_ID;
        /// <summary>
        /// 任务类型
        /// </summary>
        private String t_taskType;
        /// <summary>
        /// 来源货位
        /// </summary>
        private String t_fromNo;
        /// <summary>
        /// 来源库区
        /// </summary>
        private String t_fromArea;
        /// <summary>
        /// 目的地货位
        /// </summary>
        private String t_toNo;
        /// <summary>
        /// 目的地库区
        /// </summary>
        private String t_toArea;
        /// <summary>
        /// 产品编码
        /// </summary>
        private String t_prodNo;

        public int getT_ID() {
            return t_ID;
        }

        public void setT_ID(int t_ID) {
            this.t_ID = t_ID;
        }

        public String getT_taskType() {
            return t_taskType;
        }

        public void setT_taskType(String t_taskType) {
            this.t_taskType = t_taskType;
        }

        public String getT_fromNo() {
            return t_fromNo;
        }

        public void setT_fromNo(String t_fromNo) {
            this.t_fromNo = t_fromNo;
        }

        public String getT_fromArea() {
            return t_fromArea;
        }

        public void setT_fromArea(String t_fromArea) {
            this.t_fromArea = t_fromArea;
        }

        public String getT_toNo() {
            return t_toNo;
        }

        public void setT_toNo(String t_toNo) {
            this.t_toNo = t_toNo;
        }

        public String getT_toArea() {
            return t_toArea;
        }

        public void setT_toArea(String t_toArea) {
            this.t_toArea = t_toArea;
        }

        public String getT_prodNo() {
            return t_prodNo;
        }

        public void setT_prodNo(String t_prodNo) {
            this.t_prodNo = t_prodNo;
        }

        public String getT_prodName() {
            return t_prodName;
        }

        public void setT_prodName(String t_prodName) {
            this.t_prodName = t_prodName;
        }

        public String getT_trayType() {
            return t_trayType;
        }

        public void setT_trayType(String t_trayType) {
            this.t_trayType = t_trayType;
        }

        public int getT_count() {
            return t_count;
        }

        public void setT_count(int t_count) {
            this.t_count = t_count;
        }

        public int getT_nbox_num() {
            return t_nbox_num;
        }

        public void setT_nbox_num(int t_nbox_num) {
            this.t_nbox_num = t_nbox_num;
        }

        public int getT_npack_num() {
            return t_npack_num;
        }

        public void setT_npack_num(int t_npack_num) {
            this.t_npack_num = t_npack_num;
        }

        public int getT_nbook_num() {
            return t_nbook_num;
        }

        public void setT_nbook_num(int t_nbook_num) {
            this.t_nbook_num = t_nbook_num;
        }

        public String getT_sponsor() {
            return t_sponsor;
        }

        public void setT_sponsor(String t_sponsor) {
            this.t_sponsor = t_sponsor;
        }

        public String getT_receiver() {
            return t_receiver;
        }

        public void setT_receiver(String t_receiver) {
            this.t_receiver = t_receiver;
        }

        public String getT_reviewer() {
            return t_reviewer;
        }

        public void setT_reviewer(String t_reviewer) {
            this.t_reviewer = t_reviewer;
        }

        public String getT_taskState() {
            return t_taskState;
        }

        public void setT_taskState(String t_taskState) {
            this.t_taskState = t_taskState;
        }

//        public Date getT_createTime() {
//            return t_createTime;
//        }

//        public void setT_createTime(Date t_createTime) {
//            this.t_createTime = t_createTime;
//        }

//        public Date getT_completeTime() {
//            return t_completeTime;
//        }

//        public void setT_completeTime(Date t_completeTime) {
//            this.t_completeTime = t_completeTime;
//        }

        public String getT_team() {
            return t_team;
        }

        public void setT_team(String t_team) {
            this.t_team = t_team;
        }

        public String getT_member() {
            return t_member;
        }

        public void setT_member(String t_member) {
            this.t_member = t_member;
        }

        public String getT_exception() {
            return t_exception;
        }

        public void setT_exception(String t_exception) {
            this.t_exception = t_exception;
        }

        public String getT_exception2() {
            return t_exception2;
        }

        public void setT_exception2(String t_exception2) {
            this.t_exception2 = t_exception2;
        }

        public String getT_exception3() {
            return t_exception3;
        }

        public void setT_exception3(String t_exception3) {
            this.t_exception3 = t_exception3;
        }

        public String getT_exception4() {
            return t_exception4;
        }

        public void setT_exception4(String t_exception4) {
            this.t_exception4 = t_exception4;
        }

        public String getT_exception5() {
            return t_exception5;
        }

        public void setT_exception5(String t_exception5) {
            this.t_exception5 = t_exception5;
        }

        public String getT_exception6() {
            return t_exception6;
        }

        public void setT_exception6(String t_exception6) {
            this.t_exception6 = t_exception6;
        }

        /// <summary>
        /// 产品名称
        /// </summary>
        private String t_prodName;
        /// <summary>
        /// 托盘类型
        /// </summary>
        private String t_trayType;
        /// <summary>
        /// 数量
        /// </summary>
        private int t_count;
        /// <summary>
        /// 件
        /// </summary>
        private int t_nbox_num;
        /// <summary>
        /// 包
        /// </summary>
        private int t_npack_num;
        /// <summary>
        /// 本
        /// </summary>
        private int t_nbook_num;
        /// <summary>
        /// 发起人
        /// </summary>
        private String t_sponsor;
        /// <summary>
        /// 领取人
        /// </summary>
        private String t_receiver;
        /// <summary>
        /// 审核人
        /// </summary>
        private String t_reviewer;
        /// <summary>
        /// 任务状态,默认未开始，已领取，已完成，已审核，已关闭
        /// </summary>
        private String t_taskState;
        /// <summary>
        /// 创建时间
        /// </summary>
        //private Date t_createTime;
        /// <summary>
        /// 完成时间
        /// </summary>
        //private Date t_completeTime;
        /// <summary>
        /// 小组
        /// </summary>
        private String t_team;
        /// <summary>
        /// 组员，多人员用逗号分割
        /// </summary>
        private String t_member;
        private String t_exception;
        private String t_exception2;
        private String t_exception3;
        private String t_exception4;
        private String t_exception5;
        private String t_exception6;
    }
}
