package com.zitian.csims.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Complete_Db {

    public static SQLiteDatabase db;
    public static String filepath;
    public static void CreateDB(String dbName, Context context){
        filepath = context.getFilesDir().toString()+"/"+dbName;
        db = SQLiteDatabase.openOrCreateDatabase(filepath, null);
    }
    public static boolean Insert(String name){
        boolean result = false;
        try{
            System.out.println("db state:"+db.isOpen());
            if(!db.isOpen())
                db.openOrCreateDatabase(filepath, null);
            ContentValues value = new ContentValues();
            value.put("name", name);
            db.insertOrThrow("input", null, value);
            //db.execSQL("insert into input values(null,?)",new String[]{name});
        }catch(SQLException e){
            e.printStackTrace();
            db.execSQL("create table input(id integer primary key autoincrement,name varchar(100))");
            ContentValues value = new ContentValues();
            value.put("name", name);
            db.insertOrThrow("input", null, value);
            //db.execSQL("insert into input values(null,?)",new String[]{name});
        }
        finally{
            Log.i("insert into db",name);
        }
        return result;
    }
    public static Cursor Query(String query){

        if(query != null){
            try{
                if(!db.isOpen())
                    db.openOrCreateDatabase(filepath, null);
                String selection = "name like \'" + query +"%\'";
                return db.query("input",new String[]{"name"}, selection, null, null, null, null);
            }
            catch(SQLException e)
            {
                db.execSQL("create table input(id integer primary key autoincrement,name varchar(100))");
                String selection = "name like \'" + query +"%\'";
                return db.query("input",new String[]{"name"}, selection, null, null, null, null);
            }
        }
        else
            return null;
    }
    public static void Close(){
        if(db!=null && db.isOpen())
            db.close();
    }
    public static void Clear(){
    //清空搜索数据库
        try{
            if(!db.isOpen())
                db.openOrCreateDatabase(filepath, null);
            db.execSQL("delete * from input");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
