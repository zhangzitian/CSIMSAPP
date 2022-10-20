package com.zitian.csims.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BatchOffManagerTransferOrderList {
    private int result;
    private String msg;
    private List<BatchOffManagerTransferOrderList.Bean> data;
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

    public List<BatchOffManagerTransferOrderList.Bean> getData() {
        return data;
    }
    public void setData(List<BatchOffManagerTransferOrderList.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        /// <summary>
        /// 标识
        /// </summary>
        private int t_id;
        /// <summary>
        /// 调出库区
        /// </summary>
        private String t_outHouse;
        /// <summary>
        /// 调入库区
        /// </summary>
        private String t_inHouse;
        /// <summary>
        /// 产品编码
        /// </summary>
        private String t_prodNo;
        /// <summary>
        /// 产品名称
        /// </summary>
        private String t_prodName;
        /// <summary>
        /// 印厂
        /// </summary>
        private String t_factory;
        /// <summary>
        /// 数量
        /// </summary>
        private int t_count;
        /// <summary>
        /// 是否已打印
        /// </summary>
        private boolean t_isPrint;
        /// <summary>
        /// 打印次数
        /// </summary>
        private int t_printcount;

        public int getT_id() {
            return t_id;
        }

        public void setT_id(int t_id) {
            this.t_id = t_id;
        }

        public String getT_outHouse() {
            return t_outHouse;
        }

        public void setT_outHouse(String t_outHouse) {
            this.t_outHouse = t_outHouse;
        }

        public String getT_inHouse() {
            return t_inHouse;
        }

        public void setT_inHouse(String t_inHouse) {
            this.t_inHouse = t_inHouse;
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

        public String getT_factory() {
            return t_factory;
        }

        public void setT_factory(String t_factory) {
            this.t_factory = t_factory;
        }

        public int getT_count() {
            return t_count;
        }

        public void setT_count(int t_count) {
            this.t_count = t_count;
        }

        public boolean isT_isPrint() {
            return t_isPrint;
        }

        public void setT_isPrint(boolean t_isPrint) {
            this.t_isPrint = t_isPrint;
        }

        public int getT_printcount() {
            return t_printcount;
        }

        public void setT_printcount(int t_printcount) {
            this.t_printcount = t_printcount;
        }

        public String getT_state() {
            return t_state;
        }

        public void setT_state(String t_state) {
            this.t_state = t_state;
        }

        public String getT_operator() {
            return t_operator;
        }

        public void setT_operator(String t_operator) {
            this.t_operator = t_operator;
        }

        public String getT_reviewer() {
            return t_reviewer;
        }

        public void setT_reviewer(String t_reviewer) {
            this.t_reviewer = t_reviewer;
        }

        public String getT_createTime() {
            return t_createTime;
        }

        public void setT_createTime(String t_createTime) {
            this.t_createTime = t_createTime;
        }

        public String getT_reviewTime() {
            return t_reviewTime;
        }

        public void setT_reviewTime(String t_reviewTime) {
            this.t_reviewTime = t_reviewTime;
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

        /// <summary>
        /// 状态
        /// </summary>
        private String t_state;
        /// <summary>
        /// 操作人
        /// </summary>
        private String t_operator;
        /// <summary>
        /// 审核人
        /// </summary>
        private String t_reviewer;
        /// <summary>
        /// 生成时间
        /// </summary>
        private String t_createTime;
        /// <summary>
        /// 完成时间
        /// </summary>
        private String t_reviewTime;

        private String t_exception;
        private String t_exception2;
        private String t_exception3;
        private String t_exception4;
        private String t_exception5;
    }

}
