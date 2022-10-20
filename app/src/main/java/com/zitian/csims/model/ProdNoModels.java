package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class ProdNoModels {
    private int result;
    private String msg;
    private List<ProdNoModels.Bean> data;
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

    public List<ProdNoModels.Bean> getData() {
        return data;
    }
    public void setData(List<ProdNoModels.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {

        private String h_isbn;

        public String getH_isbn() {
            return h_isbn;
        }

        public void setH_isbn(String h_isbn) {
            this.h_isbn = h_isbn;
        }

//        @Override
       public String toString() {
//            // 为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
//            return h_isbn + "                                                                                 @"+h_isbn.replace("-","");
                return h_isbn;
        }
    }

}
