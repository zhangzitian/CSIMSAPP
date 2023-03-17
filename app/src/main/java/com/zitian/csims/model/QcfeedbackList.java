package com.zitian.csims.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QcfeedbackList {
    private int result;
    private String msg;
    private List<QcfeedbackList.Bean> data;
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

    public List<QcfeedbackList.Bean> getData() {
        return data;
    }
    public void setData(List<QcfeedbackList.Bean> data) {
        this.data = data;
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
        private  String qc_manager_idea;
        ///<summary>
        ///问题定性
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
        ///状态
        ///</summary>
        private int qc_state;


        public String getfactory_id() {
            return factory_id;
        }
        public void setfactory_id(String factory_id) {
            this.factory_id = factory_id;
        }

        public String getproduct_no() {
            return product_no;
        }
        public void setproduct_no(String product_no) {
            this.product_no = product_no;
        }

        public String getproduct_name(){return product_name;}
        public void setproduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getproduct_count(){return product_count;}
        public void setproduct_count(int product_count) {
            this.product_count = product_count;
        }

        public int getqc_package(){return qc_package;}
        public void setqc_package(int qc_package) {
            this.qc_package = qc_package;
        }

        public int getaq_package(){return aq_package;}
        public void setaq_package(int aq_package) {
            this.aq_package = aq_package;
        }

        public int getproblem_count(){return problem_count;}
        public void setproblem_count(int problem_count) {
            this.problem_count = problem_count;
        }

        public String getqc_desc(){return qc_desc;}
        public void setqc_desc(String qc_desc) {
            this.qc_desc = qc_desc;
        }

        public String getqc_review(){return qc_review;}
        public void setqc_review(String qc_review) {
            this.qc_review = qc_review;
        }
        public String getqc_programme(){return qc_programme;}
        public void setqc_programme(String qc_programme) {
            this.qc_programme = qc_programme;
        }

        public String getqc_manager_review(){return qc_manager_review;}
        public void setqc_manager_review(String qc_manager_review) {
            this.qc_manager_review = qc_manager_review;
        }

    }
}
