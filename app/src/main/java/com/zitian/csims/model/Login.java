package com.zitian.csims.model;

import java.io.Serializable;
import java.util.List;

public class Login {
    private int result;
    private String msg;
    private Login.Bean data;
    private int total;

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    private List<Login.Role> role;

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

    public Login.Bean getData() {
        return data;
    }
    public void setData(Login.Bean data) {
        this.data = data;
    }

    public static class Bean  implements Serializable {
        private String OID;
        private String o_id;
        private String o_name;
        private String passwd;
        private String r_id;
        private String groupname;
        private String zy_id;
        private String zyName;

        public String getZyName() {
            return zyName;
        }

        public void setZyName(String zyName) {
            this.zyName = zyName;
        }

        public String getZy_id() {
            return zy_id;
        }

        public void setZy_id(String zy_id) {
            this.zy_id = zy_id;
        }

        public String getOID() {
            return OID;
        }

        public void setOID(String OID) {
            this.OID = OID;
        }

        public String getO_id() {
            return o_id;
        }

        public void setO_id(String o_id) {
            this.o_id = o_id;
        }

        public String getO_name() {
            return o_name;
        }

        public void setO_name(String o_name) {
            this.o_name = o_name;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getR_id() {
            return r_id;
        }

        public void setR_id(String r_id) {
            this.r_id = r_id;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }
    }

    public static class RoleList  implements Serializable {
        private List<Login.Role> role;
    }
    //role
    public static class Role  implements Serializable {
        private int s_id;
        private String s_name;

        public int getS_id() {
            return s_id;
        }

        public void setS_id(int s_id) {
            this.s_id = s_id;
        }

        public String getS_name() {
            return s_name;
        }

        public void setS_name(String s_name) {
            this.s_name = s_name;
        }

        public String getS_url() {
            return s_url;
        }

        public void setS_url(String s_url) {
            this.s_url = s_url;
        }

        public int getS_oderid() {
            return s_oderid;
        }

        public void setS_oderid(int s_oderid) {
            this.s_oderid = s_oderid;
        }

        public String getS_rid() {
            return s_rid;
        }

        public void setS_rid(String s_rid) {
            this.s_rid = s_rid;
        }

        public String getS_base() {
            return s_base;
        }

        public void setS_base(String s_base) {
            this.s_base = s_base;
        }

        public String getS_excetion() {
            return s_excetion;
        }

        public void setS_excetion(String s_excetion) {
            this.s_excetion = s_excetion;
        }

        public String getS_excetion2() {
            return s_excetion2;
        }

        public void setS_excetion2(String s_excetion2) {
            this.s_excetion2 = s_excetion2;
        }

        public String getS_excetion3() {
            return s_excetion3;
        }

        public void setS_excetion3(String s_excetion3) {
            this.s_excetion3 = s_excetion3;
        }

        private String s_url;
        private int s_oderid;
        private String s_rid;
        private String s_base;
        private String s_excetion;
        private String s_excetion2;
        private String s_excetion3;
        // s_id":63,"
        // s_name":"库位纠错业务(WarehousingError)  ","
        // s_url":"#","
        // s_oderid":108,"
        // s_rid":"01,03,04,","
        // s_base":"","
        // s_excetion":"","
        // s_excetion2":"","
        // s_excetion3":""}
    }

}
