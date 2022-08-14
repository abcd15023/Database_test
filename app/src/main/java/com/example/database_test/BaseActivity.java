/*
 * Copyright 2017 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.database_test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database_test.BaseAdapter;
import com.example.database_test.MainAdapter;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by YanZhenjie on 2017/7/21.
 */
public class BaseActivity extends AppCompatActivity implements OnItemClickListener {

    protected Toolbar mToolbar;
    protected ActionBar mActionBar;
    protected SwipeRecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.ItemDecoration mItemDecoration;

    protected BaseAdapter mAdapter;
    protected List<nianhui_info> mDataList;
    protected List<nianhui_info> mDataList2;

    EditText et1, et11, et2, et22, et222, et3;
    EditText etSearchName;
    Button btn_insert, btn_clear1;
    Button btn_update, btn_clear2;
    Button btn_del, btn_clear3;
    Button btn_clearchaxun,btn_del_database,btn_read_database;
    Button btnadd;
    String gettext1,gettext11,gettext2,gettext22,gettext222,gettext3;
    SQLiteDatabase db;
    String andd;
    String and;
    String ands = "";
    String sql;
    public static String tempNewtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        mActionBar = getSupportActionBar();
//        if (displayHomeAsUpEnabled()) {
//            mActionBar.setDisplayHomeAsUpEnabled(true);
//        }
        //获取ReyeclerView对象
        mRecyclerView = findViewById(R.id.recycler_view);

        initView();
        initDatabase();
        //dbNullReplace();

        mLayoutManager = createLayoutManager();
        mItemDecoration = createItemDecoration();
        mDataList = createDataList();
        mAdapter = createAdapter();

        //清空mDataList，在搜索前不显示列表
        mDataList.clear();
        mAdapter.notifyDataSetChanged(mDataList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setOnItemClickListener(this);

        //创建一个LinearLayoutManager对象（线性布局），设置到ReyeclerView对象中。
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        Utils utils = new Utils(db, mDataList, mAdapter);
        utils.dbNullReplace();

        Utils.getdbMaxId();

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbInsert();
                //dbSearch();
            }
        });
        btn_clear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //et1.setText("");
                //et11.setText("");
                //dbSearch();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbUpdate();
                //dbSearch();
            }
        });
        btn_clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                et2.setText("");
//                et22.setText("");
//                et222.setText("");
//                dbSearch();
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dbDelSingle();
//                dbSearch();
            }
        });
        btn_clear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                et3.setText("");
//                dbSearch();
            }
        });
        btn_clearchaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDataList.clear();
//                mAdapter.notifyDataSetChanged(mDataList);
            }
        });
        btn_del_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                deldb();
//                dbSearch();
            }
        });
        btn_read_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dbSearch();
            }
        });
        //搜索框文本输入监听事件
        etSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("zunet", "beforeTextChanged: charSequence=" + s + ", start=" + start + ", count=" + count + ", after=" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("zunet", "onTextChanged: charSequence=" + s + ", start=" + start + ", before=" + before + ", count=" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("zunet", "afterTextChanged: editable=" + s);
                String newText = String.valueOf(s);
                tempNewtext = newText; //用于滑动菜单删除时更新列表，需要这个全局变量
                if(!newText.trim().isEmpty()){
                    //如果字符串去掉前后空格不为空 则调用搜索
                    utils.dbETSearch(newText);
                    mDataList = Utils.mDataList; //用了Utils的搜索方法，也要把BaseActivity的mDataList更新一下
                }else{
                    //搜索框为空则清空mDataList
                    tempNewtext = null; //在搜索框为空时清空tempNewtext,避免别处调用dbETSearch(BaseActivity.tempNewtext)空指针
                    mDataList.clear();
                    mAdapter.notifyDataSetChanged(mDataList);
                }
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BaseActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }

    public List<nianhui_info> getmDataList() {
        return mDataList;
    }

    public void setmDataList(List<nianhui_info> mDataList) {
        this.mDataList = mDataList;
    }

    protected int getContentView() {
        return R.layout.activity_main;
    }

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color));
    }
    //创建数据库映射的数据集合List
    protected List<nianhui_info> createDataList() {
        //创建游标对象
        Cursor cursor = db.query("nianhui", new String[]{"id","remark","name","size","sizePlus","sellingPrice","purchasingPrice","time","supplier"}, null, null, null, null, null);
        //利用游标遍历所有数据对象
        //为了显示全部，把所有对象连接起来，放到TextView中

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
            nianhui_info nh = new nianhui_info(id,remark,name,size,sizePlus,sellingPrice,purchasingPrice,time,supplier);
            mDataList.add(nh);
            Log.i("zun","mDataList最初打印"+mDataList.toString());
        }

        // 关闭游标，释放资源
        cursor.close();
        return mDataList;
    }

    protected BaseAdapter createAdapter() {
        return new MainAdapter(this);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    protected boolean displayHomeAsUpEnabled() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    public void initView(){
        et1 = findViewById(R.id.et1);
        et11 = findViewById(R.id.et11);
        et2 = findViewById(R.id.et2);
        et22 = findViewById(R.id.et22);
        et222 = findViewById(R.id.et222);
        et3 = findViewById(R.id.et3);
        btn_insert = findViewById(R.id.btn1);
        btn_clear1 = findViewById(R.id.btn11);
        btn_update = findViewById(R.id.btn2);
        btn_clear2 = findViewById(R.id.btn22);
        btn_del = findViewById(R.id.btn3);
        btn_clear3 = findViewById(R.id.btn33);
        btn_clearchaxun = findViewById(R.id.btn_clear);
        btn_del_database = findViewById(R.id.btn_del_database);
        btn_read_database = findViewById(R.id.btn_read_database);
        etSearchName = findViewById(R.id.etSearchName);
        btnadd = findViewById(R.id.btnadd);
    }

    //初始化SQL数据库
    public void initDatabase(){
        //依靠DatabaseHelper带全部参数的构造函数创建数据库，数据库名为nianhui_db
        DatabaseHelper dbHelper = new DatabaseHelper(this, "nianhui",null,1);
        db = dbHelper.getWritableDatabase();
    }
    //SQL数据库 插入数据
//    public void dbInsert(){
//        gettext1 = et1.getText().toString();
//        gettext11 = et11.getText().toString();
//        //创建存放数据的ContentValues对象
//        ContentValues values = new ContentValues();
//        values.put("name",gettext1); //给键名/列名name赋予键值
//        values.put("size",gettext11); //给键名/列名size赋予键值
//
//        //数据库执行插入命令至表nianhui
//        db.insert("nianhui", null, values);
//    }
    //拖拽item直接改动数据库
    public void DragChangeDb() {
        //把拖拽排好序的mDataList插入数据库中
        Log.i("zun", "删除数据表");
        ContentValues values = new ContentValues();
        Log.i("zunxxx","DragChangeDb："+mDataList.toString());
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
        }
    }
    //与拖拽item配套的删除 mDatalist对应数据表行的方法
    public void delDragList(){
        for(int i = 0; i < mDataList.size(); i++){
            String str = String.valueOf(mDataList.get(i).getId());
            db.delete("nianhui", "id=?", new String[]{str});
        }Log.i("zunxxx","delDragList："+mDataList.toString());
    }
    //滑动菜单的复制功能，从拖动得到的Position来删除对应SQL数据库id的单条数据
    public void dbCopyItem(int position){
        mDataList = Utils.getmDataList();
        int id = Utils.getdbMaxId()+1;
        String remark = String.valueOf(mDataList.get(position).getRemark());//通过Position得到item在数据库的remark
        String name = String.valueOf(mDataList.get(position).getName());
        String size = String.valueOf(mDataList.get(position).getSize());
        String sizePlus = String.valueOf(mDataList.get(position).getSizePlus());
        String sellingPrice = String.valueOf(mDataList.get(position).getSellingPrice());
        String purchasingPrice = String.valueOf(mDataList.get(position).getPurchasingPrice());
        //String time = String.valueOf(mDataList.get(position).getTime());
        String time = Utils.getTime();
        String supplier = String.valueOf(mDataList.get(position).getSupplier());

        Utils.dbInsert(id, remark, name, size, sizePlus, sellingPrice, purchasingPrice, time, supplier);
        //mDataList = Utils.getmDataList();
        Log.i("zunxxx","BaseActivity.dbCopyItem()："+mDataList);
    }
//    //滑动菜单的删除功能，从拖动得到的Position来删除对应SQL数据库id的单条数据
//    public void dbDelItem(int position){
//        String str = String.valueOf(mDataList.get(position).getId());//通过Position得到item在数据库的主键id
//        db.delete("nianhui", "id=?", new String[]{str});
//        Log.i("zun","str"+str);
//    }
//    //SQL数据库 修改/更新数据
//    public void dbUpdate(int id, String remark, String name, String size, String sizePlus, String sellingPrice, String purchasingPrice, String time, String supplier){
////        gettext2 = et2.getText().toString();
////        gettext22 = et22.getText().toString();
////        gettext222 = et222.getText().toString();
//        ContentValues values2 = new ContentValues();
//        values2.put("id",id);
//        values2.put("remark",remark);
//        values2.put("name",name);
//        values2.put("size",size);
//        values2.put("sizePlus",sizePlus);
//        values2.put("sellingPrice",sellingPrice);
//        values2.put("purchasingPrice",purchasingPrice);
//        values2.put("time",time);
//        values2.put("supplier",supplier);
//        db.update("nianhui", values2, "id = ?", new String[]{String.valueOf(id)});
//    }
//    //SQL数据库 删除数据
//    public void dbDelSingle(){
//        gettext3 = et3.getText().toString();
//        db.delete("nianhui", "id=?", new String[]{gettext3});
//    }
    //SQL数据库 遍历数据，无筛选全展示
    public void dbSearch(){
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
//    public void dbNullReplace(){
//        //使用上述如||的多字段搜索sql语句时，有Null的字段会导致整行不予显示，所以先用sql语句把3个字段下的Null替换为“”
//        db.execSQL("UPDATE nianhui SET name = '' WHERE name IS NULL");
//        db.execSQL("UPDATE nianhui SET size = '' WHERE size IS NULL");
//        db.execSQL("UPDATE nianhui SET sizePlus = '' WHERE sizePlus IS NULL");
//        db.execSQL("UPDATE nianhui SET remark = '' WHERE remark IS NULL");
//    }
//    //搜索框的数据库查询
//    public void dbETSearch(String newText){
//        Cursor cursor;
//        Log.i("zun","dbETSearch()");
//        //使用上述如||的多字段搜索sql语句时，有Null的字段会导致整行不予显示，所以先用sql语句把3个字段下的Null替换为“”
////        db.execSQL("UPDATE nianhui SET name = '' WHERE name IS NULL");
////        db.execSQL("UPDATE nianhui SET size = '' WHERE size IS NULL");
////        db.execSQL("UPDATE nianhui SET sizePlus = '' WHERE sizePlus IS NULL");
//        sql = getSql(newText);
//        //创建游标对象
//        cursor = db.rawQuery(sql, null);
//        ands = ""; //在每一次搜索后清空 and 累加，避免下次查询失败
//
//        mDataList2 = new ArrayList<>();
//        while(cursor.moveToNext()){
//            int temp_id = cursor.getColumnIndex("id");
//            int temp_remark = cursor.getColumnIndex("remark");
//            int temp_name = cursor.getColumnIndex("name");
//            int temp_size= cursor.getColumnIndex("size");
//            int temp_sizePlus = cursor.getColumnIndex("sizePlus");
//            int temp_sellingPrice = cursor.getColumnIndex("sellingPrice");
//            int temp_purchasingPrice = cursor.getColumnIndex("purchasingPrice");
//            int temp_time = cursor.getColumnIndex("time");
//            int temp_supplier = cursor.getColumnIndex("supplier");
//            int id = cursor.getInt(temp_id);
//            String name = cursor.getString(temp_name);
//            String remark = cursor.getString(temp_remark);
//            String size = cursor.getString(temp_size);
//            String sizePlus = cursor.getString(temp_sizePlus);
//            String sellingPrice = cursor.getString(temp_sellingPrice);
//            String purchasingPrice = cursor.getString(temp_purchasingPrice);
//            String supplier = cursor.getString(temp_supplier);
//            String time = cursor.getString(temp_time);
//            nianhui_info nh = new nianhui_info(id,remark,name,size,sizePlus,sellingPrice,purchasingPrice,time,supplier);
//            mDataList2.add(nh);
//        }
//        mDataList = mDataList2;
//        mAdapter.notifyDataSetChanged(mDataList);
//        // 关闭游标，释放资源
//        cursor.close();
//    }
//    //生成sql语句
//    public String getSql(String newText){
//        //把搜索框输入字符串 split("\\s+")去空格，分隔得到关键字并生成数组，但是首空格无法去掉，trim()可去前后空格作为补充
//        String[] arr = newText.trim().split("\\s+");
//        if(arr.length == 1){ //只有1个关键字时
//            String sql1 = "SELECT * FROM nianhui WHERE (name||''||size||''||sizePlus) like '%"+arr[0]+"%'";
//            return sql1;
//        }else{  //不止1个关键字时
//            for (int i = 1; i < arr.length; i++) {
//                Log.i("zunn","arr.length："+arr.length);
//                and = getAnd(arr[i].trim()); //arr从索引1开始
//            }
//            String sql2 = "SELECT * FROM nianhui WHERE (name||''||size||''||sizePlus) like '%"+arr[0]+"%'"+and;
//            return sql2;
//        }
//    }
//    //给多关键字加and语句
//    public String getAnd(String s) {
//        andd = "and (name||''||size||''||sizePlus) like '%"+s+"%'";
//        ands = ands + andd;
//        Log.i("zunn","getAnd:"+ands);
//        return ands;
//    }
//    //删除数据表(不删除表结构)，并且主键重新从1开始
//    public void deldb(){
//        db.execSQL("delete from nianhui");
//        db.execSQL("update sqlite_sequence set seq=0 where name='nianhui'");
//    }
}