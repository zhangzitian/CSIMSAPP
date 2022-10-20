package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class FactoryModels {
    private int result;
    private String msg;
    private List<FactoryModels.Bean> data;
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

    public List<FactoryModels.Bean> getData() {
        return data;
    }
    public void setData(List<FactoryModels.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {

        public String getF_department() {
            return f_department;
        }

        public void setF_department(String f_department) {
            this.f_department = f_department;
        }

        private String f_department;


        @Override
        public String toString() {
            // 为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
            // TODO Auto-generated method stub
            return f_department;
        }
    }

}
