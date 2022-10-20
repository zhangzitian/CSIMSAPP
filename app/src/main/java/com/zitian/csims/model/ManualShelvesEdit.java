package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class ManualShelvesEdit {
    private int result;
    private String msg;
    private List<ManualShelvesWarehouseIn.Bean> data;
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

    public List<ManualShelvesWarehouseIn.Bean> getData() {
        return data;
    }
    public void setData(List<ManualShelvesWarehouseIn.Bean> data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        private String ProdNo;//"ProdNo":"T-13-06-01",
        private String ProdName;//"ProdName":"“炫彩童书”小学生课外必读书系 彩图注音版 培养完美女孩的公主童话",
        private int nPiecesNum;//"nPiecesNum":80,
        private int nPackageNum;//"nPackageNum":20,
        private int nPackNumOfB;//"nPackNumOfB":4,
        private int nPlateNum;//"nPlateNum":3120,

        public String getProdNo() {
            return ProdNo;
        }

        public void setProdNo(String prodNo) {
            ProdNo = prodNo;
        }

        public String getProdName() {
            return ProdName;
        }

        public void setProdName(String prodName) {
            ProdName = prodName;
        }

        public int getnPiecesNum() {
            return nPiecesNum;
        }

        public void setnPiecesNum(int nPiecesNum) {
            this.nPiecesNum = nPiecesNum;
        }

        public int getnPackageNum() {
            return nPackageNum;
        }

        public void setnPackageNum(int nPackageNum) {
            this.nPackageNum = nPackageNum;
        }

        public int getnPackNumOfB() {
            return nPackNumOfB;
        }

        public void setnPackNumOfB(int nPackNumOfB) {
            this.nPackNumOfB = nPackNumOfB;
        }

        public int getnPlateNum() {
            return nPlateNum;
        }

        public void setnPlateNum(int nPlateNum) {
            this.nPlateNum = nPlateNum;
        }

        public int getPiecesNum() {
            return PiecesNum;
        }

        public void setPiecesNum(int piecesNum) {
            PiecesNum = piecesNum;
        }

        public int getPackageNum() {
            return PackageNum;
        }

        public void setPackageNum(int packageNum) {
            PackageNum = packageNum;
        }

        public int getBookNum() {
            return BookNum;
        }

        public void setBookNum(int bookNum) {
            BookNum = bookNum;
        }

        public String getcBoxName() {
            return cBoxName;
        }

        public void setcBoxName(String cBoxName) {
            this.cBoxName = cBoxName;
        }

        public String getWh_wareHouse() {
            return wh_wareHouse;
        }

        public void setWh_wareHouse(String wh_wareHouse) {
            this.wh_wareHouse = wh_wareHouse;
        }

        public String getWh_wareArea() {
            return wh_wareArea;
        }

        public void setWh_wareArea(String wh_wareArea) {
            this.wh_wareArea = wh_wareArea;
        }

        public Float getH_output_price() {
            return h_output_price;
        }

        public void setH_output_price(Float h_output_price) {
            this.h_output_price = h_output_price;
        }

        public Float getH_price_px() {
            return h_price_px;
        }

        public void setH_price_px(Float h_price_px) {
            this.h_price_px = h_price_px;
        }

        public String getcPlateName() {
            return cPlateName;
        }

        public void setcPlateName(String cPlateName) {
            this.cPlateName = cPlateName;
        }

        private int PiecesNum;//"PiecesNum":22,
        private int PackageNum;//"PackageNum":2,
        private int BookNum;//"BookNum":0,
        private String cBoxName;//"cBoxName":"T1306",
        private String wh_wareHouse;//"wh_wareHouse":"A1-01-1",
        private String wh_wareArea;//"wh_wareArea":"产品配货区",
        private Float h_output_price;//"h_output_price":0.0,
        private Float  h_price_px;//"h_price_px":1.0,
        private String cPlateName;//"cPlateName":"13*3"}
    }

}
