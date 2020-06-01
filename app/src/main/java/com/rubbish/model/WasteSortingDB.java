package com.rubbish.model;

import android.content.Context;
import android.util.Log;

import com.rubbish.model.DBHelper;
import com.rubbish.model.WasteSorting;

import java.util.ArrayList;
import java.util.HashMap;

public class WasteSortingDB {

    DBHelper helper;

    public WasteSortingDB(Context context){
        helper = DBHelper.getInstance(context);
    }

    /**【插入数据】**/
    public WasteSorting insert(WasteSorting wasteSorting) {

        Log.i("TAG:", "插入数据到数据库表：rubbish中:" + wasteSorting.toString());
        ArrayList<WasteSorting> wasteSortings = query(wasteSorting.rubbishName);
        if (wasteSortings.size() > 0) {
            Log.e("1111111111111111", "该垃圾已经存在，请确认");
            return null;
        }
        helper.getWritableDatabase();
        String sql = "insert into rubbish(rubbishId,rubbishName,rubbishCategory,rubbishChoose,comment) values (?,?,?,?,?)";
        Object[] bindArgs = {wasteSorting.rubbishId, wasteSorting.rubbishName, wasteSorting.rubbishCategory,wasteSorting.rubbishChoose,wasteSorting.comment};
        helper.updateSQLite(sql, bindArgs);
        ArrayList<WasteSorting> wasteSorting2 = query(wasteSorting.rubbishName);
        if (wasteSorting2.size() <= 0) {
            Log.e("1111111111111111", "垃圾信息保存失败");
            return null;
        }
        return wasteSorting2.get(0);
    }

    /**【模糊查询】**/
    public ArrayList<WasteSorting>query_name_type(String rubbishName , String rubbishChoose) {
        ArrayList<HashMap<String, String>> list;
        ArrayList<WasteSorting> wastesortingList = new ArrayList<>();
        String sql = "select * from rubbish where rubbishName like ? and rubbishChoose like ?";
        if (rubbishName== null) {
            rubbishName = "%%";
        } else {
            rubbishName = "%" + rubbishName + "%";
        }
        rubbishChoose = "%" + rubbishChoose + "%";
        list = helper.querySQLite(sql, new String[]{rubbishName , rubbishChoose});
        Log.i("TAG:", "查询完毕，返回数据：" + list.size());
        for (HashMap<String, String> dataOne :
                list) {
            WasteSorting wasteSorting = new WasteSorting();
            wasteSorting.rubbishId = Integer.parseInt(dataOne.get("rubbishId"));
            wasteSorting.rubbishName = dataOne.get("rubbishName");
            wasteSorting.rubbishCategory = dataOne.get("rubbishCategory");
            wasteSorting.rubbishChoose = Integer.parseInt(dataOne.get("rubbishChoose"));
            wasteSorting.comment = dataOne.get("comment");
            wastesortingList.add(wasteSorting);
        }
        return wastesortingList;
    }



    /**【模糊查询】**/
    public ArrayList<WasteSorting>query(String rubbishName) {
        ArrayList<HashMap<String, String>> list;
        ArrayList<WasteSorting> wastesortingList = new ArrayList<>();
        String sql = "select * from rubbish where rubbishName like ?";
        if (rubbishName== null) {
            list = helper.querySQLite(sql, new String[]{"%"});
        } else {
            rubbishName = "%" + rubbishName + "%";
            list = helper.querySQLite(sql, new String[]{rubbishName});
        }
        Log.i("TAG:", "查询完毕，返回数据：" + list.size());
        for (HashMap<String, String> dataOne :
                list) {
            WasteSorting wasteSorting = new WasteSorting();
            wasteSorting.rubbishId = Integer.parseInt(dataOne.get("rubbishId"));
            wasteSorting.rubbishName = dataOne.get("rubbishName");
            wasteSorting.rubbishCategory = dataOne.get("rubbishCategory");
            wasteSorting.rubbishChoose = Integer.parseInt(dataOne.get("rubbishChoose"));
            wasteSorting.comment = dataOne.get("comment");
            wastesortingList.add(wasteSorting);
        }
        return wastesortingList;
    }



    /**【模糊查询】**/
    public ArrayList<WasteSorting>query_type(String rubbishChoose) {
        ArrayList<HashMap<String, String>> list;
        ArrayList<WasteSorting> wastesortingList = new ArrayList<>();
        String sql = "select * from rubbish where rubbishChoose like ?";
        if (rubbishChoose== null) {
            list = helper.querySQLite(sql, new String[]{"%"});
        } else {
            rubbishChoose = "%" + rubbishChoose + "%";
            list = helper.querySQLite(sql, new String[]{rubbishChoose});
        }
        Log.i("TAG:", "查询完毕，返回数据：" + list.size());
        for (HashMap<String, String> dataOne :
                list) {
            WasteSorting wasteSorting = new WasteSorting();
            wasteSorting.rubbishId = Integer.parseInt(dataOne.get("rubbishId"));
            wasteSorting.rubbishName = dataOne.get("rubbishName");
            wasteSorting.rubbishCategory = dataOne.get("rubbishCategory");
            wasteSorting.rubbishChoose = Integer.parseInt(dataOne.get("rubbishChoose"));
            wasteSorting.comment = dataOne.get("comment");
            wastesortingList.add(wasteSorting);
        }
        return wastesortingList;
    }

    /**【根据ID查询】**/
    public ArrayList<WasteSorting>query_fromID(int rubbishId) {
        ArrayList<HashMap<String, String>> list;
        ArrayList<WasteSorting> wastesortingList = new ArrayList<>();
        String sql = "select * from rubbish where rubbishId = ?";
        if (rubbishId == 0) {
            list = helper.querySQLite(sql, new String[]{"%"});
        } else {
            list = helper.querySQLite(sql, new String[]{String.valueOf(rubbishId)});
        }
        Log.i("TAG:", "查询完毕，返回数据：" + list.size());
        for (HashMap<String, String> dataOne :
                list) {
            WasteSorting wasteSorting = new WasteSorting();
            wasteSorting.rubbishId = Integer.parseInt(dataOne.get("rubbishId"));
            wasteSorting.rubbishName = dataOne.get("rubbishName");
            wasteSorting.rubbishCategory = dataOne.get("rubbishCategory");
            wasteSorting.rubbishChoose = Integer.parseInt(dataOne.get("rubbishChoose"));
            wasteSorting.comment = dataOne.get("comment");
            wastesortingList.add(wasteSorting);
        }

        return wastesortingList;
    }

    /**【删除数据】**/
    public void delete(WasteSorting wasteSorting){
        String sql = "delete from rubbish where rubbishName =  ? ";
        Object [] bindArgs = {wasteSorting.rubbishName};
        helper.updateSQLite(sql , bindArgs);
    }

    /**【更新数据】**/
    public  void update(WasteSorting wasteSorting){
        helper.getReadableDatabase();
        String sql = "update rubbish set rubbishCategory=?,rubbishCategory=?,rubbishChoose=?,comment = ? where rubbishName=?";
        Object [] bindArgs = {wasteSorting.rubbishCategory, wasteSorting.rubbishCategory,
                wasteSorting.rubbishChoose, wasteSorting.comment, wasteSorting.rubbishName};
        helper.updateSQLite(sql,bindArgs);
    }
}
