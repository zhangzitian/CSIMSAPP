package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class WarehousingErrorManagerList {
    private int result;
    private String msg;
    private List<WarehousingErrorManagerList.Bean> data;
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

    public List<WarehousingErrorManagerList.Bean> getData() {
        return data;
    }
    public void setData(List<WarehousingErrorManagerList.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        /// <summary>
        /// 主键
        /// </summary>
        private int e_id;

        public int getE_id() {
            return e_id;
        }

        public void setE_id(int e_id) {
            this.e_id = e_id;
        }

        public String getE_wareHouseNo() {
            return e_wareHouseNo;
        }

        public void setE_wareHouseNo(String e_wareHouseNo) {
            this.e_wareHouseNo = e_wareHouseNo;
        }

        public String getE_wareArea() {
            return e_wareArea;
        }

        public void setE_wareArea(String e_wareArea) {
            this.e_wareArea = e_wareArea;
        }

        public String getE_errType() {
            return e_errType;
        }

        public void setE_errType(String e_errType) {
            this.e_errType = e_errType;
        }

        public String getE_errMember() {
            return e_errMember;
        }

        public void setE_errMember(String e_errMember) {
            this.e_errMember = e_errMember;
        }

        public String getE_output_prodNo() {
            return e_output_prodNo;
        }

        public void setE_output_prodNo(String e_output_prodNo) {
            this.e_output_prodNo = e_output_prodNo;
        }

        public String getE_output_prodName() {
            return e_output_prodName;
        }

        public void setE_output_prodName(String e_output_prodName) {
            this.e_output_prodName = e_output_prodName;
        }

        public String getE_output_wareHouseNo() {
            return e_output_wareHouseNo;
        }

        public void setE_output_wareHouseNo(String e_output_wareHouseNo) {
            this.e_output_wareHouseNo = e_output_wareHouseNo;
        }

        public int getE_output_nbox_num() {
            return e_output_nbox_num;
        }

        public void setE_output_nbox_num(int e_output_nbox_num) {
            this.e_output_nbox_num = e_output_nbox_num;
        }

        public int getE_output_npack_num() {
            return e_output_npack_num;
        }

        public void setE_output_npack_num(int e_output_npack_num) {
            this.e_output_npack_num = e_output_npack_num;
        }

        public int getE_output_nbook_num() {
            return e_output_nbook_num;
        }

        public void setE_output_nbook_num(int e_output_nbook_num) {
            this.e_output_nbook_num = e_output_nbook_num;
        }

        public int getE_output_total() {
            return e_output_total;
        }

        public void setE_output_total(int e_output_total) {
            this.e_output_total = e_output_total;
        }

        public String getE_input_prodNo() {
            return e_input_prodNo;
        }

        public void setE_input_prodNo(String e_input_prodNo) {
            this.e_input_prodNo = e_input_prodNo;
        }

        public String getE_input_prodName() {
            return e_input_prodName;
        }

        public void setE_input_prodName(String e_input_prodName) {
            this.e_input_prodName = e_input_prodName;
        }

        public String getE_input_wareHouseNo() {
            return e_input_wareHouseNo;
        }

        public void setE_input_wareHouseNo(String e_input_wareHouseNo) {
            this.e_input_wareHouseNo = e_input_wareHouseNo;
        }

        public int getE_input_nbox_num() {
            return e_input_nbox_num;
        }

        public void setE_input_nbox_num(int e_input_nbox_num) {
            this.e_input_nbox_num = e_input_nbox_num;
        }

        public int getE_input_npack_num() {
            return e_input_npack_num;
        }

        public void setE_input_npack_num(int e_input_npack_num) {
            this.e_input_npack_num = e_input_npack_num;
        }

        public int getE_input_nbook_num() {
            return e_input_nbook_num;
        }

        public void setE_input_nbook_num(int e_input_nbook_num) {
            this.e_input_nbook_num = e_input_nbook_num;
        }

        public int getE_input_total() {
            return e_input_total;
        }

        public void setE_input_total(int e_input_total) {
            this.e_input_total = e_input_total;
        }

        public String getE_state() {
            return e_state;
        }

        public void setE_state(String e_state) {
            this.e_state = e_state;
        }

        public String getE_reviewer() {
            return e_reviewer;
        }

        public void setE_reviewer(String e_reviewer) {
            this.e_reviewer = e_reviewer;
        }

        public String getE_exception() {
            return e_exception;
        }

        public void setE_exception(String e_exception) {
            this.e_exception = e_exception;
        }

        public String getE_exception2() {
            return e_exception2;
        }

        public void setE_exception2(String e_exception2) {
            this.e_exception2 = e_exception2;
        }

        public String getE_exception3() {
            return e_exception3;
        }

        public void setE_exception3(String e_exception3) {
            this.e_exception3 = e_exception3;
        }

        /// <summary>
        /// 库位号
        /// </summary>
        private String e_wareHouseNo;
        /// <summary>
        /// 库区
        /// </summary>
        private String e_wareArea;
        /// <summary>
        /// 纠错类型
        /// </summary>
        private String e_errType;
        /// <summary>
        /// 过失人
        /// </summary>
        private String e_errMember;

        public String getErrMemberName() {
            return errMemberName;
        }

        public void setErrMemberName(String errMemberName) {
            this.errMemberName = errMemberName;
        }

        private String errMemberName;

        /// <summary>
        /// 移出单产品编码
        /// </summary>
        private String e_output_prodNo;
        /// <summary>
        /// 移出单产品名称
        /// </summary>
        private String e_output_prodName;
        /// <summary>
        /// 移出单库位号
        /// </summary>
        private String e_output_wareHouseNo;
        /// <summary>
        /// 移出单件数
        /// </summary>
        private int e_output_nbox_num;
        /// <summary>
        /// 移出单包数
        /// </summary>
        private int e_output_npack_num;
        /// <summary>
        /// 移出单本数
        /// </summary>
        private int e_output_nbook_num;
        /// <summary>
        /// 移出单总数
        /// </summary>
        private int e_output_total;
        /// <summary>
        /// 移入单产品编码
        /// </summary>
        private String e_input_prodNo;
        /// <summary>
        /// 移入单产品名称
        /// </summary>
        private String e_input_prodName;
        /// <summary>
        /// 移入单库位号
        /// </summary>
        private String e_input_wareHouseNo;
        /// <summary>
        /// 移入单件数
        /// </summary>
        private int e_input_nbox_num;
        /// <summary>
        /// 移入单包数
        /// </summary>
        private int e_input_npack_num;
        /// <summary>
        /// 移入单本数
        /// </summary>
        private int e_input_nbook_num;
        /// <summary>
        /// 移入单总数
        /// </summary>
        private int e_input_total;
        /// <summary>
        /// 纠错单审核状态
        /// </summary>
        private String e_state;
        /// <summary>
        /// 审核人编号
        /// </summary>
        private String e_reviewer;
        /// <summary>
        /// 纠错单生成时间
        /// </summary>
        //private Date e_createTime;
        /// <summary>
        /// 纠错单审核时间
        /// </summary>
        //private Date e_reviewTime;
        private String e_exception;
        private String e_exception2;
        private String e_exception3;
        //private Date e_exception4;

        private String operatorName;

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }
    }

}
