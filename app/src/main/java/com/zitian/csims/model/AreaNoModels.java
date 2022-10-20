package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class AreaNoModels {
    private int result;
    private String msg;
    private List<AreaNoModels.Bean> data;
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

    public List<AreaNoModels.Bean> getData() {
        return data;
    }
    public void setData(List<AreaNoModels.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {

        public String getWh_wareHouseNo() {
            return wh_wareHouseNo;
        }

        public void setWh_wareHouseNo(String wh_wareHouseNo) {
            this.wh_wareHouseNo = wh_wareHouseNo;
        }

        private String wh_wareHouseNo;

        private String wh_wareArea;

        public String getWh_wareArea() {
            return wh_wareArea;
        }

        public void setWh_wareArea(String wh_wareArea) {
            this.wh_wareArea = wh_wareArea;
        }

        @Override
        public String toString() {
            // 为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
            // TODO Auto-generated method stub
            if(wh_wareHouseNo.contains("区"))
            {
                return wh_wareHouseNo;
            }
            if(wh_wareHouseNo.contains("品"))
            {
                return wh_wareHouseNo;
            }
            return wh_wareHouseNo + "                                                                                         " + "@"+wh_wareHouseNo.replace("-","") +"@"+ wh_wareArea;
            //return wh_wareHouseNo;
        }

    }

}
