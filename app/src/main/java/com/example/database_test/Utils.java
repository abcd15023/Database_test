package com.example.database_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static SQLiteDatabase db;
    public static List<nianhui_info> mDataList;
    public static List<nianhui_info> mDataList2;
    public static List<nianhui_info> mDataList3;
    public static BaseAdapter mAdapter;
    public static String andd;
    public static String and;
    public static String ands = "";
    public static String sql;
    public static int maxid,m;

    public Utils(SQLiteDatabase db, List<nianhui_info> mDataList,BaseAdapter mAdapter) {
        this.db = db;
        this.mDataList = mDataList;
        this.mAdapter = mAdapter;
    }

    //SQL数据库 插入数据
    public static void dbInsert(int id, String remark, String name, String size, String sizePlus, String sellingPrice, String purchasingPrice, String time, String supplier){
        //创建存放数据的ContentValues对象
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("remark",remark);
        values.put("name",name); //给键名/列名name赋予键值
        values.put("size",size);
        values.put("sizePlus",sizePlus);
        values.put("sellingPrice",sellingPrice);
        values.put("purchasingPrice",purchasingPrice);
        values.put("time",time);
        values.put("supplier",supplier);

        //数据库执行插入命令至表nianhui
        db.insert("nianhui", null, values);
        Log.i("zunxxx","Utils.dbInsert()："+mDataList);
    }
    //获得数据库id列的最大值，用于新增数据时设置非自增非主键的id为最大值+1
    public static int getdbMaxId(){
        Cursor cursor;
        //sql = "select max(id) from nianhui";
        sql = "SELECT MAX(id) AS idd FROM nianhui";
        //创建游标对象
        cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex("idd");
            maxid = cursor.getInt(index);
        }
        // 关闭游标，释放资源
        cursor.close();
        Log.i("zunmax","maxid："+maxid);
        return maxid;
    }
    //拖拽item直接改动数据库
    public static void DragChangeDb(List<nianhui_info> mDataList) {
        //把拖拽排好序的mDataList插入数据库中
        Log.i("zun", "删除数据表");
        ContentValues values = new ContentValues();
        for (nianhui_info nhi : mDataList) {
            values.put("id", nhi.getId());
            values.put("remark", nhi.getRemark());
            values.put("name", nhi.getName());
            values.put("size", nhi.getSize());
            values.put("sizePlus", nhi.getSizePlus());
            values.put("sellingPrice", nhi.getSellingPrice());
            values.put("purchasingPrice", nhi.getPurchasingPrice());
            values.put("time", nhi.getTime());
            values.put("supplier", nhi.getSupplier());
            db.insert("nianhui", null, values);
        }Log.i("zunl","DragChangeDb："+mDataList.toString());
    }
    //与拖拽item配套的删除 mDatalist对应数据表行的方法
    public static void delDragList(List<nianhui_info> mDataList){
        for(int i = 0; i < mDataList.size(); i++){
            String str = String.valueOf(mDataList.get(i).getId());
            db.delete("nianhui", "id=?", new String[]{str});
        }Log.i("zunl","delDragList："+mDataList.toString());
    }
    //滑动菜单的删除功能，从拖动得到的Position来删除对应SQL数据库id的单条数据
    public static void dbDelItem(int position){
        String str = String.valueOf(mDataList.get(position).getId());//通过Position得到item在数据库的主键id
        db.delete("nianhui", "id=?", new String[]{str});
        Log.i("zun","str"+str);
    }


    //SQL数据库 修改/更新数据
    public static void dbUpdate(String id, String remark, String name, String size, String sizePlus, String sellingPrice, String purchasingPrice, String time, String supplier){
//        gettext2 = et2.getText().toString();
//        gettext22 = et22.getText().toString();
//        gettext222 = et222.getText().toString();
        ContentValues values2 = new ContentValues();
        values2.put("id",id);
        values2.put("remark",remark);
        values2.put("name",name);
        values2.put("size",size);
        values2.put("sizePlus",sizePlus);
        values2.put("sellingPrice",sellingPrice);
        values2.put("purchasingPrice",purchasingPrice);
        values2.put("time",time);
        values2.put("supplier",supplier);
        Log.i("zunqwe","id="+id);
        db.update("nianhui", values2, "id = ?", new String[]{id});
    }
    //SQL数据库 删除数据
    public static void dbDelSingle(int id){
        db.delete("nianhui", "id=?", new String[]{String.valueOf(id)});
    }
    //SQL数据库 遍历数据，无筛选全展示
    public static void dbSearch(){
        Cursor cursor;
        Log.i("zun","dbSearch()");
        //创建游标对象
        cursor = db.query("nianhui", new String[]{"id","remark","name","size","sizePlus","sellingPrice","purchasingPrice","time","supplier"}, null, null, null, null, null);
        //利用游标遍历所有数据对象
        mDataList2 = new ArrayList<>();
        while(cursor.moveToNext()){
            int temp_id = cursor.getColumnIndex("id");
            int temp_remark = cursor.getColumnIndex("remark");
            int temp_name = cursor.getColumnIndex("name");
            int temp_size= cursor.getColumnIndex("size");
            int temp_sizePlus = cursor.getColumnIndex("sizePlus");
            int temp_sellingPrice = cursor.getColumnIndex("sellingPrice");
            int temp_purchasingPrice = cursor.getColumnIndex("purchasingPrice");
            int temp_time = cursor.getColumnIndex("time");
            int temp_supplier = cursor.getColumnIndex("supplier");
            int id = cursor.getInt(temp_id);
            String name = cursor.getString(temp_name);
            String remark = cursor.getString(temp_remark);
            String size = cursor.getString(temp_size);
            String sizePlus = cursor.getString(temp_sizePlus);
            String sellingPrice = cursor.getString(temp_sellingPrice);
            String purchasingPrice = cursor.getString(temp_purchasingPrice);
            String supplier = cursor.getString(temp_supplier);
            String time = cursor.getString(temp_time);
            nianhui_info nh = new nianhui_info(id,remark,name,size,sizePlus,sellingPrice,purchasingPrice,time,supplier);
            mDataList2.add(nh);
        }
        mDataList = mDataList2;
        mAdapter.notifyDataSetChanged(mDataList);
        // 关闭游标，释放资源
        cursor.close();
    }
    public static void dbNullReplace(){
        //使用上述如||的多字段搜索sql语句时，有Null的字段会导致整行不予显示，所以先用sql语句把3个字段下的Null替换为“”
        db.execSQL("UPDATE nianhui SET name = '' WHERE name IS NULL");
        db.execSQL("UPDATE nianhui SET size = '' WHERE size IS NULL");
        db.execSQL("UPDATE nianhui SET sizePlus = '' WHERE sizePlus IS NULL");
        db.execSQL("UPDATE nianhui SET remark = '' WHERE remark IS NULL");
    }
    //搜索框的数据库查询
    public static void dbETSearch(String sql){
        Cursor cursor;
        Log.i("zun","dbETSearch()");
        //使用上述如||的多字段搜索sql语句时，有Null的字段会导致整行不予显示，所以先用sql语句把3个字段下的Null替换为“”
//        db.execSQL("UPDATE nianhui SET name = '' WHERE name IS NULL");
//        db.execSQL("UPDATE nianhui SET size = '' WHERE size IS NULL");
//        db.execSQL("UPDATE nianhui SET sizePlus = '' WHERE sizePlus IS NULL");
        //sql = getSql(newText);
        //创建游标对象
        cursor = db.rawQuery(sql, null);
        ands = ""; //在每一次搜索后清空 and 累加，避免下次查询失败

        mDataList2 = new ArrayList<>();
        while(cursor.moveToNext()){
            int temp_id = cursor.getColumnIndex("id");
            int temp_remark = cursor.getColumnIndex("remark");
            int temp_name = cursor.getColumnIndex("name");
            int temp_size= cursor.getColumnIndex("size");
            int temp_sizePlus = cursor.getColumnIndex("sizePlus");
            int temp_sellingPrice = cursor.getColumnIndex("sellingPrice");
            int temp_purchasingPrice = cursor.getColumnIndex("purchasingPrice");
            int temp_time = cursor.getColumnIndex("time");
            int temp_supplier = cursor.getColumnIndex("supplier");
            int id = cursor.getInt(temp_id);
            String name = cursor.getString(temp_name);
            String remark = cursor.getString(temp_remark);
            String size = cursor.getString(temp_size);
            String sizePlus = cursor.getString(temp_sizePlus);
            String sellingPrice = cursor.getString(temp_sellingPrice);
            String purchasingPrice = cursor.getString(temp_purchasingPrice);
            String supplier = cursor.getString(temp_supplier);
            String time = cursor.getString(temp_time);
            nianhui_info nh = new nianhui_info(id,remark,name,size,sizePlus,sellingPrice,purchasingPrice,time,supplier);
            mDataList2.add(nh);
        }
        mDataList = mDataList2;
        setmDataList(mDataList);
        Log.i("zunxxx","Utils.dbETSearch()mDataList2："+mDataList2.toString());
        mAdapter.notifyDataSetChanged(mDataList);
        // 关闭游标，释放资源
        cursor.close();
    }
    //与dbETSearch()配套的刷新mDatalist方法，适用修改后list更新不及时调用
    public static List<nianhui_info> getmDataList() {
        return mDataList3;
    }

    public static void setmDataList(List<nianhui_info> mDataList3) {
        Utils.mDataList3 = mDataList3;
    }

    //生成sql语句
    public static String getSql(String newText){
        //把搜索框输入字符串 split("\\s+")去空格，分隔得到关键字并生成数组，但是首空格无法去掉，trim()可去前后空格作为补充
        String[] arr = newText.trim().split("\\s+");
        if(arr.length == 1){ //只有1个关键字时
            String sql1 = "select * from (select * from nianhui where (name||''||size||''||sizePlus) like '%"+arr[0]+"%' order by time ASC) group by name,size,sizePlus";
            return sql1;
        }else{  //不止1个关键字时
            for (int i = 1; i < arr.length; i++) {
                Log.i("zunn","arr.length："+arr.length);
                and = getAnd(arr[i].trim()); //arr从索引1开始
            }
            String sql2 = "select * from (select * from nianhui where (name||''||size||''||sizePlus) like '%"+arr[0]+"%' "+and+" order by time ASC) group by name,size,sizePlus";
            return sql2;
        }
    }
    //生成sql语句
    public static String getSql2(String newText){
        //把搜索框输入字符串 split("\\s+")去空格，分隔得到关键字并生成数组，但是首空格无法去掉，trim()可去前后空格作为补充
        String[] arr = newText.trim().split("\\s+");
        if(arr.length == 1){ //只有1个关键字时
            String sql1 = "SELECT * FROM nianhui WHERE (name||''||size||''||sizePlus) like '%"+arr[0]+"%'";
            return sql1;
        }else{  //不止1个关键字时
            for (int i = 1; i < arr.length; i++) {
                Log.i("zunn","arr.length："+arr.length);
                and = getAnd(arr[i].trim()); //arr从索引1开始
            }
            String sql2 = "SELECT * FROM nianhui WHERE (name||''||size||''||sizePlus) like '%"+arr[0]+"%'"+and;
            return sql2;
        }
    }
    //给多关键字加and语句
    public static String getAnd(String s) {
        andd = "and (name||''||size||''||sizePlus) like '%"+s+"%'";
        ands = ands + andd;
        Log.i("zunn","getAnd:"+ands);
        return ands;
    }
    //删除数据表(不删除表结构)，并且主键重新从1开始
    public static void deldb(){
        db.execSQL("delete from nianhui");
        db.execSQL("update sqlite_sequence set seq=0 where name='nianhui'");
    }
    //获取系统时间，并格式化为String
    public static String getTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//注意大小写
        return sdf.format(date);
    }

}
