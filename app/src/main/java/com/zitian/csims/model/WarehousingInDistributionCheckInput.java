package com.zitian.csims.model;

import java.io.Serializable;

public class WarehousingInDistributionCheckInput {
    private int result;
    private String msg;
    private WarehousingInDistributionCheckInput.Bean data;
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

    public WarehousingInDistributionCheckInput.Bean getData() {
        return data;
    }
    public void setData(WarehousingInDistributionCheckInput.Bean data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        /// <summary>
        /// 标识
        /// </summary>
        public int q_id;
        /// <summary>
        /// 印厂
        /// </summary>
        public String q_factory;
        /// <summary>
        /// 产品编码
        /// </summary>
        public String q_prodNo;
        /// <summary>
        /// 产品名称
        /// </summary>
        public String q_prodName;
        /// <summary>
        /// 入库数量
        /// </summary>
        public int q_inputNum;
        /// <summary>
        /// 码盘规则
        /// </summary>
        public String q_rule;
        /// <summary>
        /// 整盘个数
        /// </summary>
        public int  q_plateNum;
        /// <summary>
        /// 件数
        /// </summary>
        public int  q_pieces;
        /// <summary>
        /// 包数
        /// </summary>
        public int  q_package;
        /// <summary>
        /// 册数
        /// </summary>
        public int  q_book;
        /// <summary>
        /// 操作者
        /// </summary>
        public String q_operator;
        /// <summary>
        /// 入库时间
        /// </summary>
        public String  q_inputTime;
        /// <summary>
        /// 入库状态
        /// </summary>
        public String q_state;
        /// <summary>
        /// 系列号
        /// </summary>
        public String q_exception;
        /// <summary>
        /// 整盘册数
        /// </summary>
        public String q_exception2;
        public String q_exception3;

    }

}
