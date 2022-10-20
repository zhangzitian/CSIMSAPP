package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class ManualManagerList {

    private int result;
    private String msg;
    private List<ManualManagerList.Bean> data;
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

    public List<ManualManagerList.Bean> getData() {
        return data;
    }
    public void setData(List<ManualManagerList.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        private int a_id;

        private String a_type;

        private String a_wareHouseNo;

        private String a_wareArea;

        private String a_prodNo;

        private String a_prodName;

        private int a_total;

        private int a_nbox_num;

        private int a_npack_num;

        private int a_nbook_num;

        private String a_state;

        private String a_operator;

        private String a_reviewer;

        private String a_createTime;

        private String a_reviewTime;

        private String a_exception;

        private String a_exception2;

        private String a_exception3;

        private String a_exception4;

        private String a_exception5;

        public void setA_id(int a_id){
            this.a_id = a_id;
        }
        public int getA_id(){
            return this.a_id;
        }
        public void setA_type(String a_type){
            this.a_type = a_type;
        }
        public String getA_type(){
            return this.a_type;
        }
        public void setA_wareHouseNo(String a_wareHouseNo){
            this.a_wareHouseNo = a_wareHouseNo;
        }
        public String getA_wareHouseNo(){
            return this.a_wareHouseNo;
        }
        public void setA_wareArea(String a_wareArea){
            this.a_wareArea = a_wareArea;
        }
        public String getA_wareArea(){
            return this.a_wareArea;
        }
        public void setA_prodNo(String a_prodNo){
            this.a_prodNo = a_prodNo;
        }
        public String getA_prodNo(){
            return this.a_prodNo;
        }
        public void setA_prodName(String a_prodName){
            this.a_prodName = a_prodName;
        }
        public String getA_prodName(){
            return this.a_prodName;
        }
        public void setA_total(int a_total){
            this.a_total = a_total;
        }
        public int getA_total(){
            return this.a_total;
        }
        public void setA_nbox_num(int a_nbox_num){
            this.a_nbox_num = a_nbox_num;
        }
        public int getA_nbox_num(){
            return this.a_nbox_num;
        }
        public void setA_npack_num(int a_npack_num){
            this.a_npack_num = a_npack_num;
        }
        public int getA_npack_num(){
            return this.a_npack_num;
        }
        public void setA_nbook_num(int a_nbook_num){
            this.a_nbook_num = a_nbook_num;
        }
        public int getA_nbook_num(){
            return this.a_nbook_num;
        }
        public void setA_state(String a_state){
            this.a_state = a_state;
        }
        public String getA_state(){
            return this.a_state;
        }
        public void setA_operator(String a_operator){
            this.a_operator = a_operator;
        }
        public String getA_operator(){
            return this.a_operator;
        }
        public void setA_reviewer(String a_reviewer){
            this.a_reviewer = a_reviewer;
        }
        public String getA_reviewer(){
            return this.a_reviewer;
        }
        public void setA_createTime(String a_createTime){
            this.a_createTime = a_createTime;
        }
        public String getA_createTime(){
            return this.a_createTime;
        }
        public void setA_reviewTime(String a_reviewTime){
            this.a_reviewTime = a_reviewTime;
        }
        public String getA_reviewTime(){
            return this.a_reviewTime;
        }
        public void setA_exception(String a_exception){
            this.a_exception = a_exception;
        }
        public String getA_exception(){
            return this.a_exception;
        }
        public void setA_exception2(String a_exception2){
            this.a_exception2 = a_exception2;
        }
        public String getA_exception2(){
            return this.a_exception2;
        }
        public void setA_exception3(String a_exception3){
            this.a_exception3 = a_exception3;
        }
        public String getA_exception3(){
            return this.a_exception3;
        }
        public void setA_exception4(String a_exception4){
            this.a_exception4 = a_exception4;
        }
        public String getA_exception4(){
            return this.a_exception4;
        }
        public void setA_exception5(String a_exception5){
            this.a_exception5 = a_exception5;
        }
        public String getA_exception5(){
            return this.a_exception5;
        }
    }
}
