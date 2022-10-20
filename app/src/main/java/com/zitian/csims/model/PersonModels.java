package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class PersonModels {
    private int result;
    private String msg;
    private List<PersonModels.Bean> data;
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

    public List<PersonModels.Bean> getData() {
        return data;
    }
    public void setData(List<PersonModels.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        private int OID;

        public int getOID() {
            return OID;
        }

        public void setOID(int OID) {
            this.OID = OID;
        }

        public String getO_id() {
            return o_id;
        }

        public void setO_id(String o_id) {
            this.o_id = o_id;
        }

        private String o_id;


        private String o_name;

        public String getO_name() {
            return o_name;
        }

        public void setO_name(String o_name) {
            this.o_name = o_name;
        }

        @Override
        public String toString() {
            // 为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
            // TODO Auto-generated method stub
            return o_name;
        }

    }

}
