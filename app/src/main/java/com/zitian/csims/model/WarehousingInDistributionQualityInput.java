package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class WarehousingInDistributionQualityInput {
    private int result;
    private String msg;
    private WarehousingInDistributionQualityInput.Bean data;
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

    public WarehousingInDistributionQualityInput.Bean getData() {
        return data;
    }
    public void setData(WarehousingInDistributionQualityInput.Bean data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        /// <summary>
        /// --编码
        /// </summary>
        public String h_id;
        /// <summary>
        /// --书号
        /// </summary>
        public String h_isbn;
        /// <summary>
        /// --录入时间
        /// </summary>
        //public DateTime h_input_date;
        public String flag_sales_class;
        /// <summary>
        /// --商品名称
        /// </summary>
        public String h_name;
        public String d_id;
        public String inside_id;
        public String h_mytm;
        public String kb_id;
        public String isbn_class;
        public String zz_id;
        public String pub_id;
        public String h_media;
        public String h_type;
        public String NeiwenP;
        public String h_output_price;
        public double h_price_px;
        public String h_px_discount;
        public String h_seshu;
        public String is_yinzhang;
        public String is_zhuyin;
        public String h_maidian;
        public String h_Agegroup;
        public int product_up;
        public int product_down;
        public String h_bak1;
        public String h_bak2;
        public String h_bak3;
        public String h_bak4;
        public String h_bak5;
        public String h_bak6;
        public String my_help_input;
        /// <summary>
        /// --录入人
        /// </summary>
        public String o_id_input;
        /// <summary>
        /// ----修改人
        /// </summary>
        public String o_id_lastmodify;
        /// <summary>
        /// --修改时间
        /// </summary>
        //public DateTime? last_mod_date;
        public String station_id;
        public byte[] coverFile;
        public String coverFilename;
        public String h_run_style;
        public String u_id;
        public String bz_id;
        public int h_package_ammount;
        public String h_mem;
        public String h_factory;
        public String h_exist;
        public double h_input_price;
        public String h_writer;
        public String h_translator;
        public String h_banci;
        public String h_taozhuang;
        //public DateTime? h_publish_date;
        public int h_word_number;
        public String h_serial_book;
        public int h_yin_number;
        public double hy_price;
        public double h_weight;
        public double h_height;
        public String yylb_id;
        //public DateTime? h_hopesell;
        public String is_my;
        public String is_focus;
        public String yz_id;
        public String h_period_id;
        public String is_export;
        public String h_reader;
        public String exchange_id;
        public String notrigger;
        public String many_flag;
        public String copyright_id;
        public String sales_type;
        public String vom_class;
        public String vom_tj_flag;
        public int pagecount;
        public double immobility_cost;
        public double immobility_cost_other;
        public int isall;
        public String h_content;
        public String h_face;
        public String h_largeimage;
        public String h_smallimage;
        public String h_list;
        public double pri_sheetcount;
        public double pri_times;
        public int account_limit;
        public String o_id_lastgr;
        public String sales_level;
        public String Standard;
        public String producingarea;
        public String grade;
        public int baozhizhouqi;
        public double lianyingdiscount;
        public String isleiji;
        public String h_id_other;
        //public DateTime? pri_date;
        public String p_id;
        public double currency_id_value;
        public String web_use;
        public String h_id_ok;
        public String h_name_Foreign;
        public String currency_id;
        public String havepic_flag;
        public String pub_name_original;
        public Double T_unit_percent;
        public int h_package_piece;
        public String pre_zd;
        public Double single_price;
        public String manage_editor;
        public Double astrict_price;
        public String receipt_parm;
        public String grade_id;
        public String Subject_id;
        public String ceci_id;
        public String Object_class_id;
        public String arear_limit;
        public String is_lock;
        public int whiteness;
        public Double bulk_bulk;
        public String kaiben_right;
        public Double first_sale_date;
        public String h_level;
        public String arrear_h_id;
        public String h_year;
        public String yhdt_id;
        public String st_id;
        public String ds_product;
        public String ds_code;
        public String h_brand;
        public String erp_h_id;
        public Double ds_product_date;
        public String is_belong;
        public String h_brandtype;
        public String reader_span;
        public String isbn_class_item;
        public String h_name_other;
        public String series_name;
        /// <summary>
        /// --审核状态
        /// </summary>
        public String is_verify;
        /// <summary>
        /// --审核人
        /// </summary>
        public String o_id_verify;
        /// <summary>
        /// --审核时间
        /// </summary>
        //public DateTime? verify_date;
        public String is_Web;
        public Double npageNum;
        /// <summary>
        /// --系列包装名称
        /// </summary>
        public String cBoxName;
        /// <summary>
        /// --件册数
        /// </summary>
        public int nBoxNum;
        /// <summary>
        /// --包册数
        /// </summary>
        public int nPackNum;
        /// <summary>
        /// --件包数
        /// </summary>
        public int nPackNumOfB;
        /// <summary>
        /// --码放原则
        /// </summary>
        public String cPlateName;
        /// <summary>
        /// --满盘册数
        /// </summary>
        public Double nPlateNum;
        public String is_Close;
        public Double last_Close_date;
        public String o_id_Closemodify;
        public Boolean bshelf_Flag;
        public String s_id_in;
        public String CSizeSpec;
        public String TSOrederList;
    }

}
