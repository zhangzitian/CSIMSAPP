package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class InventoryEmpty2 {
    private int result;
    private String msg;
    private List<InventoryEmpty2.Bean> data;
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

    public List<InventoryEmpty2.Bean> getData() {
        return data;
    }
    public void setData(List<InventoryEmpty2.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWareHouseNo() {
            return wareHouseNo;
        }

        public void setWareHouseNo(String wareHouseNo) {
            this.wareHouseNo = wareHouseNo;
        }

        private String status;
        private String wareHouseNo;

    }
}
