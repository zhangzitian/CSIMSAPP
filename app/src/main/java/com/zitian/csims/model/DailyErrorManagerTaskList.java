package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class DailyErrorManagerTaskList {
    private int result;
    private String msg;
    private List<DailyErrorManagerTaskList.Bean> data;
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

    public List<DailyErrorManagerTaskList.Bean> getData() {
        return data;
    }
    public void setData(List<DailyErrorManagerTaskList.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        private int d_ID;

        private String d_Type;

        private String d_prodNo;

        private String d_prodName;

        private String d_trayType;

        private int d_count;

        private boolean d_isPrint;

        private int d_printCount;

        private String d_operator;

        private String d_createTime;

        private String d_complateTime;

        public void setD_ID(int d_ID){
            this.d_ID = d_ID;
        }
        public int getD_ID(){
            return this.d_ID;
        }
        public void setD_Type(String d_Type){
            this.d_Type = d_Type;
        }
        public String getD_Type(){
            return this.d_Type;
        }
        public void setD_prodNo(String d_prodNo){
            this.d_prodNo = d_prodNo;
        }
        public String getD_prodNo(){
            return this.d_prodNo;
        }
        public void setD_prodName(String d_prodName){
            this.d_prodName = d_prodName;
        }
        public String getD_prodName(){
            return this.d_prodName;
        }
        public void setD_trayType(String d_trayType){
            this.d_trayType = d_trayType;
        }
        public String getD_trayType(){
            return this.d_trayType;
        }
        public void setD_count(int d_count){
            this.d_count = d_count;
        }
        public int getD_count(){
            return this.d_count;
        }
        public void setD_isPrint(boolean d_isPrint){
            this.d_isPrint = d_isPrint;
        }
        public boolean getD_isPrint(){
            return this.d_isPrint;
        }
        public void setD_printCount(int d_printCount){
            this.d_printCount = d_printCount;
        }
        public int getD_printCount(){
            return this.d_printCount;
        }
        public void setD_operator(String d_operator){
            this.d_operator = d_operator;
        }
        public String getD_operator(){
            return this.d_operator;
        }
        public void setD_createTime(String d_createTime){
            this.d_createTime = d_createTime;
        }
        public String getD_createTime(){
            return this.d_createTime;
        }
        public void setD_complateTime(String d_complateTime){
            this.d_complateTime = d_complateTime;
        }
        public String getD_complateTime(){
            return this.d_complateTime;
        }
    }

}
