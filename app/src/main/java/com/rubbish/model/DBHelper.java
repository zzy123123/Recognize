package com.rubbish.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "wastersorting";
    public final static int VERSION = 1;
    private static DBHelper instance = null;
    private SQLiteDatabase db;

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    private void openDatabase() {
        if (db == null) {
            db = getWritableDatabase();
        }
    }
    private void openReadableDatabase() {
        if (db == null) {
            db = getReadableDatabase();
        }
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    /** 第一次安装程序后创建数据库 */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS rubbish(rubbishId integer PRIMARY KEY AUTOINCREMENT,rubbishName text,rubbishCategory text,rubbishChoose integer,comment text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * execSQL()方法可以执行insert，update，delete语句
     * 实现对数据库的 增，删，改 功能
     * sql为操作语句 ， bindArgs为操作传递参数
     * **/
    public boolean updateSQLite(String sql , Object [] bindArgs){
        openDatabase();
        boolean isSuccess = false;
        try {
            db.execSQL( sql , bindArgs );
            isSuccess = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            if (db!=null){
//                db.close();
//            }
            Log.i("TAG:","数据插入数据库中状态：" + isSuccess);
        }
        return isSuccess;
    }

    /**
     * rawQuery()方法可以执行select语句
     * 实现查询功能
     * sql为操作语句 ， bindArgs为操作传递参数
     * **/
    public ArrayList<HashMap<String , String>> querySQLite(String sql , String [] bindArgs){
        openReadableDatabase();
        ArrayList<HashMap<String ,String>> list = new ArrayList<HashMap<String, String>>();

        /**Cursor是结果集游标，使用Cursou.moveToNext()方法可以从当前行移动到下一行**/
        Cursor cursor = db.rawQuery(sql , bindArgs);
        int clos_len = cursor.getColumnCount();                 //获取数据所有列数

        Log.i("TAG:","querySQLite()方法中获得总列数clos_len：" + clos_len);

//        boolean isfals = cursor.moveToNext();
//        Log.i("TAG:","isfals值为：" + isfals);

        while(cursor.moveToNext()) {                            //循环表格中的每一行
            Log.i("TAG:","进入到while循环中");

            HashMap<String , String> map = new HashMap<>();
            for(int i = 0;i<clos_len;i++){                      //循环表格中的每一列
                String clos_name = cursor.getColumnName(i);     //从给定的索引i返回列名
                String clos_value = cursor.getString(cursor.getColumnIndex(clos_name));//返回指定的名称，没有就返回-1
                if(clos_value==null){
                    clos_value = "";
                }

                Log.i("TAG:","while循环下面的for循环拿到的数据clos_value为："
                        + cursor.getString(cursor.getColumnIndex(clos_name)));

                map.put(clos_name , clos_value);
            }
            list.add(map);
        }
        return list;
    }
}
