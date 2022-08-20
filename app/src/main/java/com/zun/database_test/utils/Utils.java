package com.zun.database_test.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.ajts.androidmads.library.ExcelToSQLite;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.zun.database_test.BaseAdapter;
import com.zun.database_test.bean.NianHuiBean;
import com.zun.database_test.db.DatabaseHelper;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static SQLiteDatabase mDb;
    public static List<NianHuiBean> mDataList;
    public static BaseAdapter mAdapter;
    public static String mTempSql; //用于滑动菜单删除时更新列表，需要这个全局变量
    public static String mTempNewText; //避免无搜索时进入增加商品返回时报错

    private static String and; //给for循环外面引用所以设成员变量
    private static String ands = "";
    private static int maxId;

    //定义SD的根路径
    public static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     SQL数据库 插入数据
     */
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
        mDb.insert("nianhui", null, values);
        Log.i("zunxxx","Utils.dbInsert()："+mDataList);
    }

    /**
     * 获得数据库id列的最大值，用于新增数据时设置非自增非主键的id为最大值+1
     */
    public static int getdbMaxId(){
        Cursor cursor;
        //String sql = "SELECT MAX(id) AS idd FROM nianhui"; 有bug，Max()函数最大到999
        String sql = "SELECT MAX(id) AS idd FROM (select CAST(id AS BIGINT) id from nianhui)";
        //创建游标对象
        cursor = mDb.rawQuery(sql, null);
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex("idd");
            maxId = cursor.getInt(index);
        }
        // 关闭游标，释放资源
        cursor.close();
        Log.i("zunmax","maxid："+maxId);
        return maxId;
    }

    /**
     * 滑动菜单的删除功能，从拖动得到的Position来删除对应SQL数据库id的单条数据
     */
    public static void dbDelItem(int position){
        String str = String.valueOf(mDataList.get(position).getId());//通过Position得到item在数据库的主键id
        mDb.delete("nianhui", "id=?", new String[]{str});
        Log.i("zun","str"+str);
    }

    /**
     * SQL数据库 修改/更新数据
     */
    public static void dbUpdate(String id, String remark, String name, String size, String sizePlus, String sellingPrice, String purchasingPrice, String time, String supplier){
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
        mDb.update("nianhui", values2, "id = ?", new String[]{id});
    }

    /**
     * 把数据库各个字段的Null值替换为“”
     */
    public static void dbNullReplace(){
        //当sql语句使用||的多字段搜索时，有Null值的字段会导致整行不予显示，所以先用sql语句把3个字段下的Null替换为“”
        mDb.execSQL("UPDATE nianhui SET name = '' WHERE name IS NULL");
        mDb.execSQL("UPDATE nianhui SET size = '' WHERE size IS NULL");
        mDb.execSQL("UPDATE nianhui SET sizePlus = '' WHERE sizePlus IS NULL");
        mDb.execSQL("UPDATE nianhui SET remark = '' WHERE remark IS NULL");
    }

    /**
     * 搜索框的数据库查询
     */
    public static void dbETSearch(String sql){
        Cursor cursor;
        Log.i("zun","dbETSearch()");
        //创建游标对象
        cursor = mDb.rawQuery(sql, null);
        ands = ""; //在每一次搜索后清空 and 累加，避免下次查询失败

        mDataList = new ArrayList<>();
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
            NianHuiBean nh = new NianHuiBean(id,remark,name,size,sizePlus,sellingPrice,purchasingPrice,time,supplier);
            mDataList.add(nh);
        }
        Log.i("zunxxx","Utils.dbETSearch()mDataList2："+mDataList.toString());
        mAdapter.notifyDataSetChanged(mDataList);
        // 关闭游标，释放资源
        cursor.close();
    }

    /**
     * 生成sql语句
     */
    public static String getSql(String newText){
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

    /**
     * 给多关键字的sql加and语句
     */
    public static String getAnd(String s) {
        String andd = "and (name||''||size||''||sizePlus) like '%"+s+"%'";
        ands = ands + andd;
        Log.i("zunn","getAnd:"+ands);
        return ands;
    }

    /**
     * 获取系统时间，并格式化为String
     */
    public static String getTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//注意大小写
        return sdf.format(date);
    }
    /**
     * 把数据库导出为Excel表
     */
    public static void dbToExcel(Context context){
        //导出到默认位置-外置存储根目录
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(context, "nianhui"); //有.db后缀的要加后缀，没有的不用加
        //导出到指定位置
        //SQLiteToExcel sqliteToExcel = new SQLiteToExcel(this, "helloworld.db", directory_path);

        //导出指定数据库中的单个数据表
        sqliteToExcel.exportSingleTable("nianhui", "nianhui.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {
            }
            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(context,"导出成功",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(context,"导出失败",Toast.LENGTH_SHORT).show();
                Log.i("zunex","Exception e："+e.toString());
            }
        });
    }

    /**
     * 把Excel表导入数据库
     */
    public static void excelToDb(Context context){
        //初始化
        //ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), "helloworld.db");
        //如果你要先删除表，再导入Excel，使用以下声明方法
        ExcelToSQLite excelToSQLite = new ExcelToSQLite(context, "nianhui", true);

        //从文件夹中导入-填入绝对路径
        excelToSQLite.importFromFile(ROOT + "/nianhui.xls", new ExcelToSQLite.ImportListener() {
            @Override
            public void onStart() {
                //下面的onCompleted()方法没有起作用，故在这里加提示
                Toast.makeText(context,"导入成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCompleted(String dbName) {
                //不知为何，导入成功却没有调用该方法
                //Toast.makeText(BaseActivity.this,"导入成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context,"导入失败",Toast.LENGTH_SHORT).show();
                Log.i("zunex","Exception e："+e.toString());
            }
        });
    }
    //初始化SQL数据库
    public static void initDatabase(Context context){
        //依靠DatabaseHelper带全部参数的构造函数创建数据库，数据库名为nianhui_db
        DatabaseHelper dbHelper = new DatabaseHelper(context, "nianhui",null,1);
        mDb = dbHelper.getWritableDatabase();
    }

    /**
     * 拖拽item直接改动数据库
     */
    public static void DragChangeDb() {
        //把拖拽排好序的mDataList插入数据库中
        Log.i("zun", "删除数据表");
        ContentValues values = new ContentValues();
        Log.i("zunxxx","DragChangeDb："+Utils.mDataList.toString());
        for (NianHuiBean nhi : Utils.mDataList) {
            values.put("id", nhi.getId());
            values.put("remark", nhi.getRemark());
            values.put("name", nhi.getName());
            values.put("size", nhi.getSize());
            values.put("sizePlus", nhi.getSizePlus());
            values.put("sellingPrice", nhi.getSellingPrice());
            values.put("purchasingPrice", nhi.getPurchasingPrice());
            values.put("time", nhi.getTime());
            values.put("supplier", nhi.getSupplier());
            mDb.insert("nianhui", null, values);
        }
    }

    /**
     * 与拖拽item配套的删除 mDataList对应数据表行的方法
     */
    public static void delDragList(){
        for(int i = 0; i < Utils.mDataList.size(); i++){
            String str = String.valueOf(Utils.mDataList.get(i).getId());
            mDb.delete("nianhui", "id=?", new String[]{str});
        }Log.i("zunxxx","delDragList："+Utils.mDataList.toString());
    }

    /**
     * 滑动菜单的复制功能
     */
    public static void dbCopyItem(int position){
        int id = Utils.getdbMaxId()+1;
        String remark = String.valueOf(Utils.mDataList.get(position).getRemark());//通过Position得到item在数据库的remark
        String name = String.valueOf(Utils.mDataList.get(position).getName());
        String size = String.valueOf(Utils.mDataList.get(position).getSize());
        String sizePlus = String.valueOf(Utils.mDataList.get(position).getSizePlus());
        String sellingPrice = String.valueOf(Utils.mDataList.get(position).getSellingPrice());
        String purchasingPrice = String.valueOf(Utils.mDataList.get(position).getPurchasingPrice());
        //String time = String.valueOf(mDataList.get(position).getTime());
        String time = Utils.getTime();
        String supplier = String.valueOf(Utils.mDataList.get(position).getSupplier());

        Utils.dbInsert(id, remark, name, size, sizePlus, sellingPrice, purchasingPrice, time, supplier);
        //mDataList = Utils.getmDataList();
        Log.i("zunxxx","BaseActivity.dbCopyItem()："+Utils.mDataList);
    }
}
