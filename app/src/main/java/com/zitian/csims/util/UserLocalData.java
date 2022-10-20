package com.zitian.csims.util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.zitian.csims.common.AppConstant;
import com.zitian.csims.model.BatchOffManagerManualAdd2;
import com.zitian.csims.model.FactoryModels;
import com.zitian.csims.model.Login;
import com.zitian.csims.model.PersonModels;
import com.zitian.csims.model.ProdNoModels;

import java.util.List;

public class UserLocalData {

    public static void putProdNo(Context context , ProdNoModels ProdNo){
        String ProdNo_JSON = JsonUtil.toJSON(ProdNo) ;
        SharePreferenceUtil.putString(context , AppConstant.ProdNo_JSON , ProdNo_JSON);
    }

    public static ProdNoModels getProdNo(Context context){
        String ProdNo_JSON = SharePreferenceUtil.getString(context , AppConstant.ProdNo_JSON , null);
        if (!TextUtils.isEmpty(ProdNo_JSON)){
            return JsonUtil.fromJson(ProdNo_JSON , ProdNoModels.class) ;
        }
        return null ;
    }

    public static void clearProdNo(Context context){
        SharePreferenceUtil.putString(context , AppConstant.ProdNo_JSON , "");
    }

    public static void putFactory(Context context , FactoryModels Factory){
        String FactoryJSON = JsonUtil.toJSON(Factory) ;
        SharePreferenceUtil.putString(context , AppConstant.Factory_JSON , FactoryJSON);
    }

    public static FactoryModels getFactory(Context context){
        String Factory_JSON = SharePreferenceUtil.getString(context , AppConstant.Factory_JSON , null);
        if (!TextUtils.isEmpty(Factory_JSON)){
            return JsonUtil.fromJson(Factory_JSON , FactoryModels.class) ;
        }
        return null ;
    }

    public static void clearFactory(Context context){
        SharePreferenceUtil.putString(context , AppConstant.Factory_JSON , "");
    }


    public static void putPerson(Context context , PersonModels Person){
        String Person_JSON = JsonUtil.toJSON(Person) ;
        SharePreferenceUtil.putString(context , AppConstant.Person_JSON , Person_JSON);
    }

    public static PersonModels getPerson(Context context){
        String Person_JSON = SharePreferenceUtil.getString(context , AppConstant.Person_JSON , null);
        if (!TextUtils.isEmpty(Person_JSON)){
            return JsonUtil.fromJson(Person_JSON , PersonModels.class) ;
        }
        return null ;
    }

    public static void clearPerson(Context context){
        SharePreferenceUtil.putString(context , AppConstant.Person_JSON , "");
    }

    public static void putUser(Context context , Login.Bean user){
        String user_json = JsonUtil.toJSON(user) ;
        SharePreferenceUtil.putString(context , AppConstant.USER_JSON , user_json);
    }

    public static void putUserToken(Context context , String token){
        SharePreferenceUtil.putString(context , AppConstant.USER_TOKEN , token);
    }

    public static Login.Bean getUser(Context context){
        String user_json = SharePreferenceUtil.getString(context , AppConstant.USER_JSON , null);
        if (!TextUtils.isEmpty(user_json)){
            return JsonUtil.fromJson(user_json , Login.Bean.class) ;
        }
        return null ;
    }

    public static String getUserToken(Context context){
        String token = SharePreferenceUtil.getString(context , AppConstant.USER_TOKEN , null);
        if (!TextUtils.isEmpty(token)){
            return JsonUtil.fromJson(token , String.class) ;
        }
        return null ;
    }

    public static void clearUser(Context context){
        SharePreferenceUtil.putString(context , AppConstant.USER_JSON , "");
    }

    public static void clearUserToken(Context context){
        SharePreferenceUtil.putString(context , AppConstant.USER_TOKEN , "");
    }

    public static void putServerIp(Context context , String string){
        String server_ip = JsonUtil.toJSON(string) ;
        SharePreferenceUtil.putString(context , AppConstant.SERVER_IP , server_ip);
    }

    public static String getServerIp(Context context){
        String server_ip = SharePreferenceUtil.getString(context , AppConstant.SERVER_IP ,"");
        if (!TextUtils.isEmpty(server_ip)){
            return JsonUtil.fromJson(server_ip , String.class) ;
        }
        return null;
    }

    public static void putRole(Context context , List<Login.Role> role){
        String role_json = JsonUtil.toJSON(role) ;
        SharePreferenceUtil.putString(context , AppConstant.ROLE_JSON , role_json);
    }

    public static List<Login.Role> getRole(Context context){
        String role_json = SharePreferenceUtil.getString(context , AppConstant.ROLE_JSON , null);
        if (!TextUtils.isEmpty(role_json)){
            return JsonUtil.fromJson(role_json,new TypeToken<List<Login.Role>>(){}.getType()) ;
        }
        return null;
    }

    public static void clearRole(Context context){
        SharePreferenceUtil.putString(context , AppConstant.ROLE_JSON , "");
    }

}
