package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class Msg {
    private int result;
    private String msg;
    private List<Msg.Bean> data;
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

    public List<Msg.Bean> getData() {
        return data;
    }
    public void setData(List<Msg.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        private int w_id;
        private String w_type;
        private String w_message;
        private String w_status;
        private String w_receiver;
        private String w_createTime;
        private String w_complateTime;
        private String w_exception;
        private String w_exception2;

        public int getW_id() {
            return w_id;
        }

        public void setW_id(int w_id) {
            this.w_id = w_id;
        }

        public String getW_type() {
            return w_type;
        }

        public void setW_type(String w_type) {
            this.w_type = w_type;
        }

        public String getW_message() {
            return w_message;
        }

        public void setW_message(String w_message) {
            this.w_message = w_message;
        }

        public String getW_status() {
            return w_status;
        }

        public void setW_status(String w_status) {
            this.w_status = w_status;
        }

        public String getW_receiver() {
            return w_receiver;
        }

        public void setW_receiver(String w_receiver) {
            this.w_receiver = w_receiver;
        }

        public String getW_createTime() {
            return w_createTime;
        }

        public void setW_createTime(String w_createTime) {
            this.w_createTime = w_createTime;
        }

        public String getW_complateTime() {
            return w_complateTime;
        }

        public void setW_complateTime(String w_complateTime) {
            this.w_complateTime = w_complateTime;
        }

        public String getW_exception() {
            return w_exception;
        }

        public void setW_exception(String w_exception) {
            this.w_exception = w_exception;
        }

        public String getW_exception2() {
            return w_exception2;
        }

        public void setW_exception2(String w_exception2) {
            this.w_exception2 = w_exception2;
        }

    }

}
