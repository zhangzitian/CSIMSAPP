package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class ReplenishManualDistributionAdd {


    private int result;
    private String msg;
    private ReplenishManualDistributionAdd.Bean data;
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

    public ReplenishManualDistributionAdd.Bean getData() {
        return data;
    }
    public void setData(ReplenishManualDistributionAdd.Bean  data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {

        private String h_name;

        public String getH_name() {
            return h_name;
        }

        public void setH_name(String h_name) {
            this.h_name = h_name;
        }

    }


}
