package com.zitian.csims.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DeliveryNoteModels {
    private int result;
    private String msg;
    private List<DeliveryNoteModels.Bean> data;
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

    public List<DeliveryNoteModels.Bean> getData() {
        return data;
    }
    public void setData(List<DeliveryNoteModels.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        /// <summary>
        /// 主键
        /// </summary>
        private int d_id ;
        /// <summary>
        /// 工厂
        /// </summary>
        private String factory_id ;
        /// <summary>
        /// 送货类型
        /// </summary>
        private String order_type ;
        /// <summary>
        /// 车号
        /// </summary>
        private String license_number ;
        /// <summary>
        /// 车型
        /// </summary>
        private String license_type ;
        /// <summary>
        /// 产品编码
        /// </summary>
        private String product_no ;
        /// <summary>
        /// 产品名称
        /// </summary>
        private String product_name ;
        /// <summary>
        /// 预约数量
        /// </summary>
        private int product_count ;
        /// <summary>
        /// 入库时长
        /// </summary>
        private double warehosing_time ;
        /// <summary>
        /// 创建时间
        /// </summary>
        private Date create_time ;
        /// <summary>
        /// 修改时间
        /// </summary>
        private Date modify_time ;
        /// <summary>
        /// 订单编号
        /// </summary>
        private String order_id ;
        /// <summary>
        /// 预约入库产品ID
        /// </summary>
        private int m_id ;
        /// <summary>
        /// 品种数
        /// </summary>
        private  int count;

        public int getD_ID() {
            return d_id;
        }

        public void setD_ID(int d_id) {
            this.d_id = d_id;
        }

        public String getfactory_id() {
            return factory_id;
        }

        public void setfactory_id(String factory_id) {
            this.factory_id = factory_id;
        }

        public String getorder_type() {
            return order_type;
        }

        public void setorder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getlicense_number() {
            return license_number;
        }

        public void setlicense_number(String license_number) {
            this.license_number = license_number;
        }

        public String getlicense_type() {
            return license_type;
        }

        public void setlicense_type(String license_type) {
            this.license_type = license_type;
        }

        public String getproduct_no() {
            return product_no;
        }

        public void setproduct_no(String product_no) {
            this.product_no = product_no;
        }

        public String getproduct_name() {
            return product_name;
        }

        public void setproduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getproduct_count() {
            return product_count;
        }

        public void setproduct_count(int product_count) {
            this.product_count = product_count;
        }

        public double getwarehosing_time() {
            return warehosing_time;
        }

        public void setwarehosing_time(double warehosing_time) {
            this.warehosing_time = warehosing_time;
        }

        public Date getcreate_time() {
            return create_time;
        }

        public void setcreate_time(Date create_time) {
            this.create_time = create_time;
        }

        public Date getmodify_time() {
            return modify_time;
        }

        public void setmodify_time(Date modify_time) {
            this.modify_time = modify_time;
        }

        public String getorder_id() {
            return order_id;
        }

        public void setorder_id(String order_id) {
            this.order_id = order_id;
        }

        public int getm_id() {
            return m_id;
        }

        public void setm_id(int m_id) {
            this.m_id = m_id;
        }

        public int get_Count() {
            return count;
        }

        public void set_Count(int Count) {
            this.count = Count;
        }
    }
}
