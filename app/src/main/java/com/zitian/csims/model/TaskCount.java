package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class TaskCount {
    private int result;
    private String msg;
    private List<TaskCount.Bean> data;
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

    public List<TaskCount.Bean> getData() {
        return data;
    }
    public void setData(List<TaskCount.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        private int count;

        public String getT_taskType() {
            return t_taskType;
        }

        public void setT_taskType(String t_taskType) {
            this.t_taskType = t_taskType;
        }

        private String t_taskType;
    }

}
