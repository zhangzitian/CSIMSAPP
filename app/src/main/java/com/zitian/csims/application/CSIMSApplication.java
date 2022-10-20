package com.zitian.csims.application;

import android.app.Application;
import android.os.Build;

import com.yolanda.nohttp.NoHttp;
import com.zitian.csims.model.AreaNoModels;
import com.zitian.csims.model.FactoryModels;
import com.zitian.csims.model.Login;
import com.zitian.csims.model.PersonModels;
import com.zitian.csims.model.ProdNoModels;
import com.zitian.csims.util.UserLocalData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class CSIMSApplication extends Application {

    private static CSIMSApplication appContext;

    private ProdNoModels prodNo;

    public void initProdNo(){
        this.prodNo = UserLocalData.getProdNo(this );
    }

    public ProdNoModels getProdNo(){
        return prodNo ;
    }

    public void putProdNo(ProdNoModels prodNo){
        this.prodNo = prodNo ;
        UserLocalData.putProdNo(this , prodNo);
    }


    private FactoryModels factory;

    public void initFactory(){
        this.factory = UserLocalData.getFactory(this );
    }

    public FactoryModels getFactory(){
        return factory ;
    }

    public void putFactory(FactoryModels factory){
        this.factory = factory ;
        UserLocalData.putFactory(this , factory);
    }

    private PersonModels person;

    public void initPerson(){
        this.person = UserLocalData.getPerson(this );
    }

    public PersonModels getPerson(){
        return person ;
    }

    public void putPerson(PersonModels person){
        this.person = person ;
        UserLocalData.putPerson(this , person);
    }

    private Login.Bean user;

    public void initUser(){
        this.user = UserLocalData.getUser(this );
    }

    public Login.Bean getUser(){
        return user ;
    }

    public void putUser(Login.Bean user){
        this.user = user ;
        UserLocalData.putUser(this , user);
    }

    public void clearUser(){
        this.user = null ;
        this.userToken = null;
        this.role = null;

        this.prodNo = null ;
        this.factory = null;
        this.person = null;

        UserLocalData.clearUser(this);
        UserLocalData.clearUserToken(this);
        UserLocalData.clearRole(this);

        UserLocalData.clearProdNo(this);
        UserLocalData.clearFactory(this);
        UserLocalData.clearPerson(this);
    }

    private String userToken;

    public void initUserToken(){
        this.userToken = UserLocalData.getUserToken(this );
    }

    public String getUserToken(){
        return userToken ;
    }

    public void putUserToken(String userToken ){
        this.userToken = userToken ;
        UserLocalData.putUserToken(this , userToken);
    }

    public void clearUserToken(){
        this.user = null ;
        this.userToken = null ;
        this.role = null;
        UserLocalData.clearUser(this);
        UserLocalData.clearUserToken(this);
        UserLocalData.clearRole(this);
    }

    private List<Login.Role> role;

    public void initRole(){
        this.role = UserLocalData.getRole(this );
    }

    public List<Login.Role> getRole(){
        return role;
    }

    public void putRole(List<Login.Role> role){
        this.role = role;
        UserLocalData.putRole(this , role);
    }

    public void clearRole(){
        this.user = null ;
        this.userToken = null ;
        this.role = null;
        UserLocalData.clearUser(this);
        UserLocalData.clearUserToken(this);
        UserLocalData.clearRole(this);
    }

    private List<AreaNoModels.Bean> AreaNoModels;

    public List<com.zitian.csims.model.AreaNoModels.Bean> getAreaNoModels() {
        return AreaNoModels;
    }

    public void setAreaNoModels(List<com.zitian.csims.model.AreaNoModels.Bean> areaNoModels) {
        AreaNoModels = areaNoModels;
    }

    private String serverIp;

    public void initServerIp(){
        this.serverIp = UserLocalData.getServerIp(this );
        if(serverIp==null)
        {
            serverIp = "192.168.123.65:10078";
        }
    }

    public String getServerIp(){
        return serverIp ;
    }

    public void putServerIp(String serverIp ){
        this.serverIp = serverIp;
        UserLocalData.putServerIp(this , serverIp);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;
        initUser();
        initUserToken();
        initServerIp();
        initRole();
        //NoHttp初始化
        NoHttp.initialize(this);
        //NoHttp.setEnableCookie(false);
    }

    public static CSIMSApplication getAppContext() {
        return appContext;
    }

}