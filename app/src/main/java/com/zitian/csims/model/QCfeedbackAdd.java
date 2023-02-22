package com.zitian.csims.model;

import java.io.Serializable;
import java.util.Date;

public class QCfeedbackAdd {

    private int result;
    private String msg;
    private QCfeedbackAdd.Bean data;
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

    public void setData(QCfeedbackAdd.Bean  data) {
        this.data = data;
    }
    public QCfeedbackAdd.Bean getData() {
        return data;
    }

    public static class Bean  implements Serializable {
        ///<summary>
        ///主键
        ///</summary>
        private  int qc_id;
        ///<summary>
        ///工厂
        ///</summary>
        private  String factory_id;
        ///<summary>
        ///产品编码
        ///</summary>
        private  String product_no;
        ///<summary>
        ///产品名称
        ///</summary>
        private  String product_name;
        ///<summary>
        ///入库数量
        ///</summary>
        private  int product_count;
        ///<summary>
        ///应抽检数量（包数）
        ///</summary>
        private  int qc_package;
        ///<summary>
        ///实际抽检数量（包数）
        ///</summary>
        private  int aq_package;
        ///<summary>
        ///有问题数量（本）
        ///</summary>
        private  int problem_count;
        ///<summary>
        ///质量问题描述
        ///</summary>
        private  String qc_desc;
        ///<summary>
        ///质检员签字
        ///</summary>
        private  String qc_operator;
        ///<summary>
        ///处理意见
        ///</summary>
        private  String qc_review;
        ///<summary>
        ///生产主管
        ///</summary>
        private  String qc_supervisor;
        ///<summary>
        ///处理结果
        ///</summary>
        private  String qc_programme;
        ///<summary>
        ///经理意见
        ///</summary>
        private  String qc_manager_review;
        ///<summary>
        ///印务经理
        ///</summary>
        private  String qc_manager;
        ///<summary>
        ///是否需要特批
        ///</summary>
        private  int is_special;
        ///<summary>
        ///是否需要特批
        ///</summary>
        private Date createtime;
    }
}
