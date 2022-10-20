package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class DailyErrorManagerTaskDetails {
    private int result;
    private String msg;
    private List<DailyErrorManagerTaskDetails.Bean> data;
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

    public List<DailyErrorManagerTaskDetails.Bean> getData() {
        return data;
    }
    public void setData(List<DailyErrorManagerTaskDetails.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        /// <summary>
        /// 主键
        /// </summary>
        private int wh_ID;
        /// <summary>
        /// 排序
        /// </summary>
        private int wh_sortNo;
        /// <summary>
        /// 库位号
        /// </summary>
        private String wh_wareHouseNo;
        /// <summary>
        /// 库区
        /// </summary>
        private String wh_wareArea;
        /// <summary>
        /// 产品编码
        /// </summary>
        private String wh_prodNo;
        /// <summary>
        /// 产品名称
        /// </summary>
        private String wh_prodName;
        /// <summary>
        /// 产品数量
        /// </summary>
        private int wh_prodNumber;
        /// <summary>
        /// 件
        /// </summary>
        private int wh_nbox_num;
        /// <summary>
        /// 包
        /// </summary>
        private int wh_npack_num;
        /// <summary>
        /// 本
        /// </summary>
        private int wh__nbook_num;
        /// <summary>
        /// 是否空库位,默认为true，非空false
        /// </summary>
        private boolean wh_isNull;
        /// <summary>
        /// 库位是否作废，默认false，true代表已经作废
        /// </summary>
        private boolean wh_isInvalid ;
        /// <summary>
        /// 系列号
        /// </summary>
        private String wh_catetory;
        /// <summary>
        ///
        /// </summary>
        private String wh_exception;
        /// <summary>
        /// 系列号
        /// </summary>
        private String wh_exception2;

        public int getWh_ID() {
            return wh_ID;
        }

        public void setWh_ID(int wh_ID) {
            this.wh_ID = wh_ID;
        }

        public int getWh_sortNo() {
            return wh_sortNo;
        }

        public void setWh_sortNo(int wh_sortNo) {
            this.wh_sortNo = wh_sortNo;
        }

        public String getWh_wareHouseNo() {
            return wh_wareHouseNo;
        }

        public void setWh_wareHouseNo(String wh_wareHouseNo) {
            this.wh_wareHouseNo = wh_wareHouseNo;
        }

        public String getWh_wareArea() {
            return wh_wareArea;
        }

        public void setWh_wareArea(String wh_wareArea) {
            this.wh_wareArea = wh_wareArea;
        }

        public String getWh_prodNo() {
            return wh_prodNo;
        }

        public void setWh_prodNo(String wh_prodNo) {
            this.wh_prodNo = wh_prodNo;
        }

        public String getWh_prodName() {
            return wh_prodName;
        }

        public void setWh_prodName(String wh_prodName) {
            this.wh_prodName = wh_prodName;
        }

        public int getWh_prodNumber() {
            return wh_prodNumber;
        }

        public void setWh_prodNumber(int wh_prodNumber) {
            this.wh_prodNumber = wh_prodNumber;
        }

        public int getWh_nbox_num() {
            return wh_nbox_num;
        }

        public void setWh_nbox_num(int wh_nbox_num) {
            this.wh_nbox_num = wh_nbox_num;
        }

        public int getWh_npack_num() {
            return wh_npack_num;
        }

        public void setWh_npack_num(int wh_npack_num) {
            this.wh_npack_num = wh_npack_num;
        }

        public int getWh__nbook_num() {
            return wh__nbook_num;
        }

        public void setWh__nbook_num(int wh__nbook_num) {
            this.wh__nbook_num = wh__nbook_num;
        }

        public boolean isWh_isNull() {
            return wh_isNull;
        }

        public void setWh_isNull(boolean wh_isNull) {
            this.wh_isNull = wh_isNull;
        }

        public boolean isWh_isInvalid() {
            return wh_isInvalid;
        }

        public void setWh_isInvalid(boolean wh_isInvalid) {
            this.wh_isInvalid = wh_isInvalid;
        }

        public String getWh_catetory() {
            return wh_catetory;
        }

        public void setWh_catetory(String wh_catetory) {
            this.wh_catetory = wh_catetory;
        }

        public String getWh_exception() {
            return wh_exception;
        }

        public void setWh_exception(String wh_exception) {
            this.wh_exception = wh_exception;
        }

        public String getWh_exception2() {
            return wh_exception2;
        }

        public void setWh_exception2(String wh_exception2) {
            this.wh_exception2 = wh_exception2;
        }

        public String getWh_exception3() {
            return wh_exception3;
        }

        public void setWh_exception3(String wh_exception3) {
            this.wh_exception3 = wh_exception3;
        }

        public String getWh_exception4() {
            return wh_exception4;
        }

        public void setWh_exception4(String wh_exception4) {
            this.wh_exception4 = wh_exception4;
        }

        public String getWh_exception5() {
            return wh_exception5;
        }

        public void setWh_exception5(String wh_exception5) {
            this.wh_exception5 = wh_exception5;
        }

        public String getWh_exception6() {
            return wh_exception6;
        }

        public void setWh_exception6(String wh_exception6) {
            this.wh_exception6 = wh_exception6;
        }

        public String getWh_exception7() {
            return wh_exception7;
        }

        public void setWh_exception7(String wh_exception7) {
            this.wh_exception7 = wh_exception7;
        }

        /// <summary>
        /// 入库时间
        /// </summary>
        private String wh_exception3;
        private String wh_exception4;
        private String wh_exception5;
        private String wh_exception6;
        private String wh_exception7;

    }

}
