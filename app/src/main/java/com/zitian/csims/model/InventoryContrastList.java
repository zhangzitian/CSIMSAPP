package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class InventoryContrastList {
    private int result;
    private String msg;
    private List<InventoryContrastList.Bean> data;
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

    public List<InventoryContrastList.Bean> getData() {
        return data;
    }
    public void setData(List<InventoryContrastList.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        private int wh_ID;
        private int wh_sortNo;
        private String wh_wareHouseNo;
        private String wh_wareArea;

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

        public int getWh_nbook_num() {
            return wh_nbook_num;
        }

        public void setWh_nbook_num(int wh_nbook_num) {
            this.wh_nbook_num = wh_nbook_num;
        }

        public int getWh_status() {
            return wh_status;
        }

        public void setWh_status(int wh_status) {
            this.wh_status = wh_status;
        }

        public int getWh_isInvalid() {
            return wh_isInvalid;
        }

        public void setWh_isInvalid(int wh_isInvalid) {
            this.wh_isInvalid = wh_isInvalid;
        }

        public String getWh_catetory() {
            return wh_catetory;
        }

        public void setWh_catetory(String wh_catetory) {
            this.wh_catetory = wh_catetory;
        }

        public String getU1_userid() {
            return u1_userid;
        }

        public void setU1_userid(String u1_userid) {
            this.u1_userid = u1_userid;
        }

        public String getU1_username() {
            return u1_username;
        }

        public void setU1_username(String u1_username) {
            this.u1_username = u1_username;
        }

        public int getU1_prodNumber() {
            return u1_prodNumber;
        }

        public void setU1_prodNumber(int u1_prodNumber) {
            this.u1_prodNumber = u1_prodNumber;
        }

        public int getU1_nbox_num() {
            return u1_nbox_num;
        }

        public void setU1_nbox_num(int u1_nbox_num) {
            this.u1_nbox_num = u1_nbox_num;
        }

        public int getU1_npack_num() {
            return u1_npack_num;
        }

        public void setU1_npack_num(int u1_npack_num) {
            this.u1_npack_num = u1_npack_num;
        }

        public int getU1_nbook_num() {
            return u1_nbook_num;
        }

        public void setU1_nbook_num(int u1_nbook_num) {
            this.u1_nbook_num = u1_nbook_num;
        }

        public int getU1_status() {
            return u1_status;
        }

        public void setU1_status(int u1_status) {
            this.u1_status = u1_status;
        }

        public String getU1_addDate() {
            return u1_addDate;
        }

        public void setU1_addDate(String u1_addDate) {
            this.u1_addDate = u1_addDate;
        }

        public int getU1_isError() {
            return u1_isError;
        }

        public void setU1_isError(int u1_isError) {
            this.u1_isError = u1_isError;
        }

        public String getU1_errorType() {
            return u1_errorType;
        }

        public void setU1_errorType(String u1_errorType) {
            this.u1_errorType = u1_errorType;
        }

        public String getU1_errorJson() {
            return u1_errorJson;
        }

        public void setU1_errorJson(String u1_errorJson) {
            this.u1_errorJson = u1_errorJson;
        }

        public int getU1_contrastResult() {
            return u1_contrastResult;
        }

        public void setU1_contrastResult(int u1_contrastResult) {
            this.u1_contrastResult = u1_contrastResult;
        }

        public String getU1_error3() {
            return u1_error3;
        }

        public void setU1_error3(String u1_error3) {
            this.u1_error3 = u1_error3;
        }

        public String getU1_error4() {
            return u1_error4;
        }

        public void setU1_error4(String u1_error4) {
            this.u1_error4 = u1_error4;
        }

        public String getU1_error5() {
            return u1_error5;
        }

        public void setU1_error5(String u1_error5) {
            this.u1_error5 = u1_error5;
        }

        public String getU2_userid() {
            return u2_userid;
        }

        public void setU2_userid(String u2_userid) {
            this.u2_userid = u2_userid;
        }

        public String getU2_username() {
            return u2_username;
        }

        public void setU2_username(String u2_username) {
            this.u2_username = u2_username;
        }

        public int getU2_prodNumber() {
            return u2_prodNumber;
        }

        public void setU2_prodNumber(int u2_prodNumber) {
            this.u2_prodNumber = u2_prodNumber;
        }

        public int getU2_nbox_num() {
            return u2_nbox_num;
        }

        public void setU2_nbox_num(int u2_nbox_num) {
            this.u2_nbox_num = u2_nbox_num;
        }

        public int getU2_npack_num() {
            return u2_npack_num;
        }

        public void setU2_npack_num(int u2_npack_num) {
            this.u2_npack_num = u2_npack_num;
        }

        public int getU2_nbook_num() {
            return u2_nbook_num;
        }

        public void setU2_nbook_num(int u2_nbook_num) {
            this.u2_nbook_num = u2_nbook_num;
        }

        public int getU2_status() {
            return u2_status;
        }

        public void setU2_status(int u2_status) {
            this.u2_status = u2_status;
        }

        public String getU2_addDate() {
            return u2_addDate;
        }

        public void setU2_addDate(String u2_addDate) {
            this.u2_addDate = u2_addDate;
        }

        public int getU2_isError() {
            return u2_isError;
        }

        public void setU2_isError(int u2_isError) {
            this.u2_isError = u2_isError;
        }

        public String getU2_errorType() {
            return u2_errorType;
        }

        public void setU2_errorType(String u2_errorType) {
            this.u2_errorType = u2_errorType;
        }

        public String getU2_errorJson() {
            return u2_errorJson;
        }

        public void setU2_errorJson(String u2_errorJson) {
            this.u2_errorJson = u2_errorJson;
        }

        public int getU2_contrastResult() {
            return u2_contrastResult;
        }

        public void setU2_contrastResult(int u2_contrastResult) {
            this.u2_contrastResult = u2_contrastResult;
        }

        public String getU2_error3() {
            return u2_error3;
        }

        public void setU2_error3(String u2_error3) {
            this.u2_error3 = u2_error3;
        }

        public String getU2_error4() {
            return u2_error4;
        }

        public void setU2_error4(String u2_error4) {
            this.u2_error4 = u2_error4;
        }

        public String getU2_error5() {
            return u2_error5;
        }

        public void setU2_error5(String u2_error5) {
            this.u2_error5 = u2_error5;
        }

        public String getR_userid() {
            return r_userid;
        }

        public void setR_userid(String r_userid) {
            this.r_userid = r_userid;
        }

        public String getR_username() {
            return r_username;
        }

        public void setR_username(String r_username) {
            this.r_username = r_username;
        }

        public int getR_prodNumber() {
            return r_prodNumber;
        }

        public void setR_prodNumber(int r_prodNumber) {
            this.r_prodNumber = r_prodNumber;
        }

        public int getR_nbox_num() {
            return r_nbox_num;
        }

        public void setR_nbox_num(int r_nbox_num) {
            this.r_nbox_num = r_nbox_num;
        }

        public int getR_npack_num() {
            return r_npack_num;
        }

        public void setR_npack_num(int r_npack_num) {
            this.r_npack_num = r_npack_num;
        }

        public int getR_nbook_num() {
            return r_nbook_num;
        }

        public void setR_nbook_num(int r_nbook_num) {
            this.r_nbook_num = r_nbook_num;
        }

        public int getR_status() {
            return r_status;
        }

        public void setR_status(int r_status) {
            this.r_status = r_status;
        }

        public String getR_addDate() {
            return r_addDate;
        }

        public void setR_addDate(String r_addDate) {
            this.r_addDate = r_addDate;
        }

        public int getR_isError() {
            return r_isError;
        }

        public void setR_isError(int r_isError) {
            this.r_isError = r_isError;
        }

        public String getR_errorType() {
            return r_errorType;
        }

        public void setR_errorType(String r_errorType) {
            this.r_errorType = r_errorType;
        }

        public String getR_errorJson() {
            return r_errorJson;
        }

        public void setR_errorJson(String r_errorJson) {
            this.r_errorJson = r_errorJson;
        }

        public int getR_contrastResult() {
            return r_contrastResult;
        }

        public void setR_contrastResult(int r_contrastResult) {
            this.r_contrastResult = r_contrastResult;
        }

        public int getH_prodNumber() {
            return h_prodNumber;
        }

        public void setH_prodNumber(int h_prodNumber) {
            this.h_prodNumber = h_prodNumber;
        }

        public int getH_nbox_num() {
            return h_nbox_num;
        }

        public void setH_nbox_num(int h_nbox_num) {
            this.h_nbox_num = h_nbox_num;
        }

        public int getH_npack_num() {
            return h_npack_num;
        }

        public void setH_npack_num(int h_npack_num) {
            this.h_npack_num = h_npack_num;
        }

        public int getH_nbook_num() {
            return h_nbook_num;
        }

        public void setH_nbook_num(int h_nbook_num) {
            this.h_nbook_num = h_nbook_num;
        }

        public int getH_status() {
            return h_status;
        }

        public void setH_status(int h_status) {
            this.h_status = h_status;
        }

        public int getH_contrastResult() {
            return h_contrastResult;
        }

        public void setH_contrastResult(int h_contrastResult) {
            this.h_contrastResult = h_contrastResult;
        }

        public String getR_error3() {
            return r_error3;
        }

        public void setR_error3(String r_error3) {
            this.r_error3 = r_error3;
        }

        public String getR_error4() {
            return r_error4;
        }

        public void setR_error4(String r_error4) {
            this.r_error4 = r_error4;
        }

        public String getR_error5() {
            return r_error5;
        }

        public void setR_error5(String r_error5) {
            this.r_error5 = r_error5;
        }

        public String getWh_exception1() {
            return wh_exception1;
        }

        public void setWh_exception1(String wh_exception1) {
            this.wh_exception1 = wh_exception1;
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

        private String wh_prodNo;
        private String wh_prodName;
        private int wh_prodNumber;
        private int wh_nbox_num;
        private int wh_npack_num;
        private int wh_nbook_num;
        private int wh_status;  //0 是空 1占用 2有货 3预留
        private int wh_isInvalid;
        private String wh_catetory; //系列
        //用户1
        private String u1_userid;
        private String u1_username;
        private int u1_prodNumber;
        private int u1_nbox_num;
        private int u1_npack_num;
        private int u1_nbook_num;
        private int u1_status; //0 是空 1占用 2有货 3预留
        private String u1_addDate;
        private int u1_isError; //0未纠错 1已纠错
        private String u1_errorType; //纠错类型
        private String u1_errorJson; //纠错json数据
        private int u1_contrastResult; //比对状态结果 0未知 1对 2错
        private String u1_error3;
        private String u1_error4;
        private String u1_error5;
        //用户2
        private String u2_userid;
        private String u2_username;
        private int u2_prodNumber;
        private int u2_nbox_num;
        private int u2_npack_num;
        private int u2_nbook_num;
        private int u2_status;  //0 是空 1占用 2有货 3预留
        private String u2_addDate;
        private int u2_isError; //0未纠错 1已纠错
        private String u2_errorType ; //纠错类型
        private String u2_errorJson ;
        private int u2_contrastResult ; //比对状态结果 0未知 1对 2错
        private String u2_error3 ;
        private String u2_error4 ;
        private String u2_error5 ;
        //正确的
        private String r_userid ;
        private String r_username ;

        //public string r_prodNo { get; set; }
        //public string r_prodName { get; set; }
        //public string r_catetory { get; set; } //系列

        private int r_prodNumber;
        private int r_nbox_num;
        private int r_npack_num;
        private int r_nbook_num;
        private int r_status;  //0 是空 1占用 2有货 3预留
        private String r_addDate;
        private int r_isError ;  //0、未纠错 1、已纠错
        private String r_errorType; //纠错类型
        private String r_errorJson;
        private int r_contrastResult ;

        private int h_prodNumber ;
        private int h_nbox_num ;
        private int h_npack_num ;
        private int h_nbook_num ;
        private int h_status ;  //0 是空 1占用 2有货 3预留
        private int h_contrastResult ;

        private String r_error3 ;
        private String r_error4;
        private String r_error5 ; //表的类型

        private String wh_exception1 ;
        private String wh_exception2 ;
        private String wh_exception3 ;
        private String wh_exception4 ;
        private String wh_exception5 ;
    }
}
