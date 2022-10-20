package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class BatchOffManagerManualList {
    private int result;
    private String msg;
    private List<BatchOffManagerManualList.Bean> data;
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

    public List<BatchOffManagerManualList.Bean> getData() {
        return data;
    }
    public void setData(List<BatchOffManagerManualList.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        private int b_id;
        private String b_wareHoseNo;
        private String b_wareArea;
        private String receiverName;

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public int getB_id() {
            return b_id;
        }

        public void setB_id(int b_id) {
            this.b_id = b_id;
        }

        public String getB_wareHoseNo() {
            return b_wareHoseNo;
        }

        public void setB_wareHoseNo(String b_wareHoseNo) {
            this.b_wareHoseNo = b_wareHoseNo;
        }

        public String getB_wareArea() {
            return b_wareArea;
        }

        public void setB_wareArea(String b_wareArea) {
            this.b_wareArea = b_wareArea;
        }

        public String getB_prodNo() {
            return b_prodNo;
        }

        public void setB_prodNo(String b_prodNo) {
            this.b_prodNo = b_prodNo;
        }

        public String getB_prodName() {
            return b_prodName;
        }

        public void setB_prodName(String b_prodName) {
            this.b_prodName = b_prodName;
        }

        public int getB_piecesNum() {
            return b_piecesNum;
        }

        public void setB_piecesNum(int b_piecesNum) {
            this.b_piecesNum = b_piecesNum;
        }

        public int getB_packNum() {
            return b_packNum;
        }

        public void setB_packNum(int b_packNum) {
            this.b_packNum = b_packNum;
        }

        public int getB_bookNum() {
            return b_bookNum;
        }

        public void setB_bookNum(int b_bookNum) {
            this.b_bookNum = b_bookNum;
        }

        public int getB_total() {
            return b_total;
        }

        public void setB_total(int b_total) {
            this.b_total = b_total;
        }

        public String getB_receiver() {
            return b_receiver;
        }

        public void setB_receiver(String b_receiver) {
            this.b_receiver = b_receiver;
        }

        public String getB_state() {
            return b_state;
        }

        public void setB_state(String b_state) {
            this.b_state = b_state;
        }

        private String b_prodNo;
        private String b_prodName;
        private int b_piecesNum;
        private int b_packNum;
        private int b_bookNum;
        private int b_total;
        private String b_receiver;
        private String b_state;
        private String b_createTime;

        public String getB_createTime() {
            return b_createTime;
        }

        public void setB_createTime(String b_createTime) {
            this.b_createTime = b_createTime;
        }
    }

}
