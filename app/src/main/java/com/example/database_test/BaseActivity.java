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

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajts.androidmads.library.ExcelToSQLite;
import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.database_test.BaseAdapter;
import com.example.database_test.MainAdapter;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    EditText etGlobalSearch;
    LinearLayout ll;
    SQLiteDatabase db;
    ImageView img_clear,img_add;
    public static boolean s1,s2;
    public static String tempSql;
    public static String tempSql2;
    public static String tempNewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //在程序中动态获取写入权限，此操作将向系统索要权限，系统敏感权限有弹窗
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
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
        //mDataList = createDataList();
        mAdapter = createAdapter();

        //清空mDataList，在搜索前不显示列表
        //mDataList.clear();
        //mAdapter.notifyDataSetChanged(mDataList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setOnItemClickListener(this);

        //创建一个LinearLayoutManager对象（线性布局），设置到ReyeclerView对象中。
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        Utils utils = new Utils(db, mDataList, mAdapter);
        utils.dbNullReplace();

        Utils.getdbMaxId();

        etGlobalSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    s1 = false; //全局变量s1,s2是给其他Activity判断2个搜索框哪个在焦点上
                    s2 = true;
                    ll.setVisibility(View.GONE);
                }
            }
        });

        etGlobalSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = String.valueOf(s);
                tempSql2 = Utils.getSql2(newText); //用于滑动菜单删除时更新列表，需要这个全局变量
                if(!newText.trim().isEmpty()){
                    tempNewText = newText;
                    //如果字符串去掉前后空格不为空 则调用搜索
                    utils.dbETSearch(Utils.getSql2(newText));
                    mDataList = Utils.mDataList; //用了Utils的搜索方法，也要把BaseActivity的mDataList更新一下
                }else{
                    //搜索框为空则清空mDataList
                    tempSql2 = Utils.getSql2(""); //在搜索框为空时清空tempNewtext,避免别处调用dbETSearch(BaseActivity.tempNewtext)空指针
                    if(mDataList != null){
                        tempNewText = null;
                        mDataList.clear();
                        mAdapter.notifyDataSetChanged(mDataList);
                    }
                }
            }
        });
        img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etGlobalSearch.setText("");
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自定义下拉弹出窗口
                PopMain pop = new PopMain(BaseActivity.this);
                pop.showPopupWindow(img_add);
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

    @Override  //item行的点击事件
    public void onItemClick(View itemView, int position) {
        //Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
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
        etGlobalSearch = findViewById(R.id.etGlobalSearch);
        img_clear = findViewById(R.id.img_clear);
        img_add = findViewById(R.id.img_add);
        ll = findViewById(R.id.ll);
    }

    //初始化SQL数据库
    public void initDatabase(){
        //依靠DatabaseHelper带全部参数的构造函数创建数据库，数据库名为nianhui_db
        DatabaseHelper dbHelper = new DatabaseHelper(this, "nianhui",null,1);
        db = dbHelper.getWritableDatabase();
    }
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
    //滑动菜单的复制功能
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
}