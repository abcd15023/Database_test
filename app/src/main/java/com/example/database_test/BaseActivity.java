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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
    protected List<nianhui_info> mDataList3;

    EditText et1, et11, et2, et22, et222, et3;
    Button btn_insert, btn_clear1;
    Button btn_update, btn_clear2;
    Button btn_del, btn_clear3;
    Button btn_clearchaxun,btn_del_database,btn_read_database;
    TextView text_show;
    TextView text1,text2,text3;
    String gettext1,gettext11,gettext2,gettext22,gettext222,gettext3;
    SQLiteDatabase db;
    RecyclerView recyclerView1;
    //ArrayList<nianhui_info> nianhuidata;
    //MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        //mToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);


//        setSupportActionBar(mToolbar);
//        mActionBar = getSupportActionBar();
//        if (displayHomeAsUpEnabled()) {
//            mActionBar.setDisplayHomeAsUpEnabled(true);
//        }
        initView();
        initDatabase();
        mLayoutManager = createLayoutManager();
        mItemDecoration = createItemDecoration();
        mDataList = createDataList();
        mAdapter = createAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setOnItemClickListener(this);

        //获取ReyeclerView对象
        //recyclerView1 = findViewById(R.id.recycler_view);
        //创建一个LinearLayoutManager对象（线性布局），设置到ReyeclerView对象中。
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbInsert();
                dbSearch();
            }
        });
        btn_clear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("");
                et11.setText("");
                dbSearch();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbUpdate();
                dbSearch();
            }
        });
        btn_clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et2.setText("");
                et22.setText("");
                et222.setText("");
                dbSearch();
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbDelSingle();
                dbSearch();
            }
        });
        btn_clear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et3.setText("");
                dbSearch();
            }
        });
        btn_clearchaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataList.clear();
                mAdapter.notifyDataSetChanged(mDataList);
            }
        });
        btn_del_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deldb();
                dbSearch();
            }
        });
        btn_read_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbSearch();
            }
        });
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
        Cursor cursor = db.query("nianhui", new String[]{"id","name","size"}, null, null, null, null, null);
        //利用游标遍历所有数据对象
        //为了显示全部，把所有对象连接起来，放到TextView中
        String textview_data = "";

        mDataList = new ArrayList<>();
        while(cursor.moveToNext()){
            int temp_name = cursor.getColumnIndex("name");
            int temp_size= cursor.getColumnIndex("size");
            int temp_id = cursor.getColumnIndex("id");

            int id = cursor.getInt(temp_id);
            String name = cursor.getString(temp_name);
//            if (name.equals("")) {
//                name = "空";
//            }
            String size = cursor.getString(temp_size);
//            if (size.equals("")) {
//                size = "空";
//            }
            textview_data = textview_data + "\n" + id +"       "+ name + "       "+size;
            nianhui_info nh = new nianhui_info(id,name,size);
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
        text1 = findViewById(R.id.item1_text1);
        text2 = findViewById(R.id.item1_text2);
        text3 = findViewById(R.id.item1_text3);
        btn_insert = findViewById(R.id.btn1);
        btn_clear1 = findViewById(R.id.btn11);
        btn_update = findViewById(R.id.btn2);
        btn_clear2 = findViewById(R.id.btn22);
        btn_del = findViewById(R.id.btn3);
        btn_clear3 = findViewById(R.id.btn33);
        btn_clearchaxun = findViewById(R.id.btn_clear);
        btn_del_database = findViewById(R.id.btn_del_database);
        btn_read_database = findViewById(R.id.btn_read_database);
    }
    //初始化SQL数据库
    public void initDatabase(){
        //依靠DatabaseHelper带全部参数的构造函数创建数据库，数据库名为nianhui_db
        DatabaseHelper dbHelper = new DatabaseHelper(this, "nianhui",null,1);
        db = dbHelper.getWritableDatabase();
    }
    //SQL数据库 插入数据
    public void dbInsert(){
        gettext1 = et1.getText().toString();
        gettext11 = et11.getText().toString();
        //创建存放数据的ContentValues对象
        ContentValues values = new ContentValues();
        values.put("name",gettext1); //给键名/列名name赋予键值
        values.put("size",gettext11); //给键名/列名size赋予键值

        //数据库执行插入命令至表nianhui
        db.insert("nianhui", null, values);
    }
    //拖拽item直接改动数据库
    public void DragChangeDb() {
        //先删除数据表
        deldb();
        //再插入数据表，把前中后段加载的 mDataList3 遍历赋值给数据表
        Log.i("zun", "删除数据表");
        ContentValues values = new ContentValues();
        for (nianhui_info nhi : mDataList) {
            values.put("name", nhi.getName());
            values.put("size", nhi.getSize());
            db.insert("nianhui", null, values);
        }
    }

    //SQL数据库 修改/更新数据
    public void dbUpdate(){
        gettext2 = et2.getText().toString();
        gettext22 = et22.getText().toString();
        gettext222 = et222.getText().toString();
        ContentValues values2 = new ContentValues();
        values2.put("name", gettext22);
        values2.put("size", gettext222);
        db.update("nianhui", values2, "id = ?", new String[]{gettext2});
    }
    //SQL数据库 删除数据
    public void dbDelSingle(){
        gettext3 = et3.getText().toString();
        db.delete("nianhui", "id=?", new String[]{gettext3});
    }
    //从item点击事件删除 SQL数据库单条数据
    public void dbDelItem(int position){
        String str = String.valueOf(mDataList.get(position).getId());
        db.delete("nianhui", "id=?", new String[]{str});
        Log.i("zun","str"+str);
    }
    //SQL数据库 查找数据
    public void dbSearch(){
        Log.i("zun","dbSearch()");
        Log.i("zun","dbSearch2()");
        //创建游标对象
        Cursor cursor = db.query("nianhui", new String[]{"id","name","size"}, null, null, null, null, null);
        //利用游标遍历所有数据对象
        //为了显示全部，把所有对象连接起来，放到TextView中
        Log.i("zun","dbSearch3()");
        String textview_data = "";

        mDataList2 = new ArrayList<>();
        while(cursor.moveToNext()){
            int temp_name = cursor.getColumnIndex("name");
            int temp_size= cursor.getColumnIndex("size");
            int temp_id = cursor.getColumnIndex("id");

            int id = cursor.getInt(temp_id);
            String name = cursor.getString(temp_name);
//            if (name.equals("")) {
//                name = "空";
//            }
            String size = cursor.getString(temp_size);
//            if (size.equals("")) {
//                size = "空";
//            }
            textview_data = textview_data + "\n" + id +"       "+ name + "       "+size;
            nianhui_info nh = new nianhui_info(id,name,size);
            mDataList2.add(nh);
        }
        mDataList = mDataList2;
        mAdapter.notifyDataSetChanged(mDataList);
        Log.i("zun","1");
        // 关闭游标，释放资源
        cursor.close();
    }
    //删除数据表
    public void deldb(){
        //sqlite删除表全部数据(不删除表结构)主键重新从1开始
        db.execSQL("delete from nianhui");
        db.execSQL("update sqlite_sequence set seq=0 where name='nianhui'");
    }
}