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
import android.text.TextUtils;
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
    SearchView searchView_name,searchView_size;
    RecyclerView recyclerView1;
    String tt = "";
    String n;
    String [] arr2={};
    int q = 0;
    String arri;
    String and="";
    String andd,andd2;
    String and1=" ",and2=" ";
    String andname = "";
    String andsize = "";
    int isName,isSize;
    String sql;
    String tempTextName,tempTextSize;
    int firstSearch = 0;
    String firstname,firstsize;
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
        initSearchView();
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
        searchView_size.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tempTextSize = newText;

                isSize = 2;
                if(!newText.trim().isEmpty()){  //如果字符串去掉前后空格不为空

                    String qqq = newText.substring(newText.length()-1); //获取字符串最后一个字符
                    Log.i("zunn","字符串最后："+qqq+",,");
                    dbSearch2(newText,isSize);
                }else{

                    dbSearch2(tempTextName,1); //如果规格删减为空则重新调用名称搜索
                    //mDataList.clear();
                    mAdapter.notifyDataSetChanged(mDataList);
                    //andsize = "";
                }

                return true;
            }
        });
        searchView_name.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override   //提交按钮监听事件
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(BaseActivity.this,"提交监听",Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override   //搜索框内容改变监听
            public boolean onQueryTextChange(String newText) {
                tempTextName = newText;
                //mDataList.clear();
//                if(!newText.trim().isEmpty()) {
//                    if (q != 0) {
//                        mDataList = getNewData(newText);
//                        mAdapter.notifyDataSetChanged(mDataList);
//                    } else {
//                        String t = newText.replace(" ", "&%&");
//                        dbSearch2(t);
//                        //mAdapter.notifyDataSetChanged();
//                        Toast.makeText(BaseActivity.this, "内容改变监听" + newText.trim(), Toast.LENGTH_SHORT).show();
//                        q = 1;
//                    }
//                }else{
//                    q = 0;
//                    dbSearch();
//                }
//                String t = newText.replace(" ", "%");
//                dbSearch2(t);

//                if(!newText.trim().isEmpty()) {
//                    String[] arr = newText.split("\\+\\s+");
//                    dbSearch2(arr[0]);
//
//                    for (String ss : arr) {
//                        if(!ss.equals(arr[0])){
//                            Log.i("zunn", "字符串：" + ss);
//                            dbSearch2(ss);
//                            //mDataList = getNewData(ss);
//                            //mAdapter.notifyDataSetChanged(mDataList);
//                        }
//                    }
//                }else{
//                    dbSearch();
//                }

                //                如果newText不是长度为0的字符串
//                if (TextUtils.isEmpty(newText)){
//                    //清除ListView的过滤
//                    newText.trim();
//                    mDataList.clearTextFilter();
//
//                }else {
//                    //使用用户输入的内容对ListView的列表项进行过滤
//                    mDataList.setFilterText(newText);
//                }
//                    if (arr2 == arr){
//
//                    }else{
//                        for(String ss : arr){
//                            if(!n.equals(ss)){
//                                Log.i("zunn","字符串："+ss);
//                                //dbSearch2(ss);
//                            }n = n+ss;
//                        }arr2 = arr;
//                    }
                    //dbSearch2(ss);

//                n = newText;
//                    String sss = "(^\\s*)|(\\s*$)";

                if(!newText.trim().isEmpty()){  //如果字符串去掉前后空格不为空
                    isName = 1;
                    String qqq = newText.substring(newText.length()-1); //获取字符串最后一个字符
                    Log.i("zunn","字符串最后："+qqq+",,");
                    //if(!qqq.equals(" ")) { //如果字符串最后一个字符 不是空格，方才进行搜索，因为在输入关键字后加的空格会造成重复搜索
                        dbSearch2(newText,isName);
//                    }else{
//                        dbSearch2(newText);
//                    }
                }else{

                    //and1 = null;
                    mDataList.clear();
                    mAdapter.notifyDataSetChanged(mDataList);
                    andname = "";
                }
                //and1 = null;
                return true;
            }
        });

    }
    private List<nianhui_info> getNewData(String input_info) {
        //mDataList.clear();
        for (int i = 0; i < mDataList.size(); i++) {
            nianhui_info nn = mDataList.get(i);
            if (nn.getName().contains(input_info)) {
                mDataList.add(nn);
            }
        }
        return mDataList;
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
//        text1 = findViewById(R.id.item1_text1);
//        text2 = findViewById(R.id.item1_text2);
//        text3 = findViewById(R.id.item1_text3);
        btn_insert = findViewById(R.id.btn1);
        btn_clear1 = findViewById(R.id.btn11);
        btn_update = findViewById(R.id.btn2);
        btn_clear2 = findViewById(R.id.btn22);
        btn_del = findViewById(R.id.btn3);
        btn_clear3 = findViewById(R.id.btn33);
        btn_clearchaxun = findViewById(R.id.btn_clear);
        btn_del_database = findViewById(R.id.btn_del_database);
        btn_read_database = findViewById(R.id.btn_read_database);
        searchView_name = findViewById(R.id.searchView_name);
        searchView_size = findViewById(R.id.searchView_size);
    }
    public void initSearchView(){
        searchView_name.setQueryHint("名称搜索");
        searchView_size.setQueryHint("规格搜索");
        searchView_name.setIconified(false);
        searchView_size.setIconified(false);
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
        //deldb();
        //delDragList();
        //再插入数据表，把前中后段加载的 mDataList3 遍历赋值给数据表
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
    public void delDragList(){
        for(int i = 0; i < mDataList.size(); i++){
            String str = String.valueOf(mDataList.get(i).getId());
            db.delete("nianhui", "id=?", new String[]{str});
        }Log.i("zunl","delDragList："+mDataList.toString());
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
        Cursor cursor = db.query("nianhui", new String[]{"id","remark","name","size","sizePlus","sellingPrice","purchasingPrice","time","supplier"}, null, null, null, null, null);

        //利用游标遍历所有数据对象
        //为了显示全部，把所有对象连接起来，放到TextView中
        Log.i("zun","dbSearch3()");

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
        Log.i("zun","1");
        // 关闭游标，释放资源
        cursor.close();
    }
    public void dbSearch2(String newText, int isWhat){
        Cursor cursor;

        Log.i("zun","dbSearch()");
        Log.i("zun","dbSearch2()");
        //String[] arr = s.trim().split("\\s+"); //字符串去掉前后空格 再进行分隔生成数组操作(把空格都过滤掉，但是首位的空格无法过滤)
        if(isWhat == 1){
            setandname(newText); //设置sql语句
            //isName = 0;
        }else if(isWhat == 2){
            setandsize(newText);
            //isSize = 0;
        }else{
            Toast.makeText(this,"输入无效",Toast.LENGTH_SHORT).show();
        }
        Log.i("zunnn","sql最终:"+sql);
        cursor = db.rawQuery(sql,null);

//        if(!s.trim().isEmpty()) {

//                if(arr.length == 1){
//                    sql = "select * from nianhui where name like '%"+arr[0]+"%' or size like '%"+arr[0]+"%'";
//                    //cursor = db.rawQuery("select * from nianhui where name like \"%"+arr[0]+"%\"",null);
//                    //cursor = db.rawQuery("select * from nianhui where name like \"%"+arr[0]+"%\" and size like \"%"+arr[0]+"%\"",null);
//                    cursor = db.rawQuery(sql,null);
//                    Log.i("zunn","length == 1");
//                }else{
//                    sql = "SELECT * FROM nianhui where name like '%"+arr[0]+"%' and size like '%"+arr[1]+"%'";
//                    String sql2 = "select * from nianhui where name like '%"+arr[0]+"%' and size like '%"+arr[1]+"%'";
//                    //String sql3 = "select * from nianhui where name = '%"+arr[0]+"%' and size = '%"+arr[1]+"%'";
//                    //cursor = db.rawQuery("select * from nianhui where name like \"%"+arr[0]+"%\" and name like \"%"+arr[1]+"%\"",null);
//                    //cursor = db.rawQuery("select * from nianhui where name like \"%"+arr[0]+"%\""+and1,null);
//                    //cursor = db.rawQuery("select * from nianhui where name like \"%"+arr[0]+"%\" and size like \"%"+arr[1]+"%\""+and1,null);
//                    //cursor = db.rawQuery(sql4 ,null);
//                    Log.i("zunn","length:"+arr.length);
//                    Log.i("zunn","arr[0]:"+arr[0]);
//                    Log.i("zunn","arr[1]:"+arr[1]);
//                    Log.i("zunn","and1成功:");
//                }
//                cursor = db.rawQuery(sql+and1 ,null);

//        }else{
//            cursor = db.query("nianhui", new String[]{"id","remark","name","size","sizePlus","sellingPrice","purchasingPrice","time","supplier"}, null, null, null, null, null);
//            and1 = null;
//            Log.i("zunn","empty");
//        }

        //String tt = "select * from nianhui where name like \"%"+arr[0]+"%\"";
        //String tt2 = "select * from nianhui where name like \"%"+arr[1]+"%\"";

        //String t = s.replace(" ", " AND %");
        //创建游标对象
        //Cursor cursor = db.query("nianhui", new String[]{"id","remark","name","size","sizePlus","sellingPrice","purchasingPrice","time","supplier"}, null, null, null, null, null);
        //Cursor cursor = db.rawQuery("select * from nianhui where name like \"%"+arr[0]+"%\"",null);//第2个参数为select语句中占位符参数的值，如果select语句没有使用占位符，该参数可以设置为null

        //cursor = db.rawQuery("select * from nianhui where name like \"%"+arr[0]+"%\" and name like \"%"+arr[1]+"%\"",null);
        //Cursor cursor2 = db.rawQuery("select * from nianhui where name in (select * from nianhui where name like \"%"+s+"%\")",null);
        //String strr = "select * from stu where name like \"%宁%\"";
        //Cursor cursor = db.rawQuery(tt,null);

        //利用游标遍历所有数据对象
        //为了显示全部，把所有对象连接起来，放到TextView中
        Log.i("zun","dbSearch3()");

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
        Log.i("zun","1");
        // 关闭游标，释放资源
        cursor.close();
    }
    public void setandname(String nameText){
        String[] arr = nameText.trim().split("\\s+"); //字符串去掉前后空格 再进行分隔生成数组操作(把空格都过滤掉，split("\\s+")无法过滤首空格，所以要先trim())
        firstname = arr[0];
        Log.i("zunn","arr:"+ Arrays.toString(arr));
        if(arr.length == 1){
            if(firstsize != null){
                sql = "select * from nianhui where name like '%"+arr[0]+"%'"+" and size like '%"+firstsize+"%'";
                and1 = " ";
            }else{
                sql = "select * from nianhui where name like '%"+arr[0]+"%'";
            }
            Log.i("zunnn","sql1:"+sql);
        }else{
            for (int i = 1; i < arr.length; i++) {
                Log.i("zunn","arr.length："+arr.length);
                and1 = getandname(arr[i].trim(),arr.length); //arr从索引1开始
            }
            sql = "SELECT * FROM nianhui where name like '%"+arr[0]+"%'"+and1+and2;
            Log.i("zunnn","sql2:"+sql);
        }
        Log.i("zunn","name and1:"+and1);
    }
    public void setandsize(String sizeText){
        String[] arr = sizeText.trim().split("\\s+"); //字符串去掉前后空格 再进行分隔生成数组操作(把空格都过滤掉，但是首位的空格无法过滤)
        firstsize = arr[0];
        Log.i("zunn","arr:"+ Arrays.toString(arr));
        if(arr.length == 1){
            if(firstname != null){
                sql = "select * from nianhui where size like '%"+arr[0]+"%'"+" and name like '%"+firstname+"%'"+and1;
                and2 = " ";
                Log.i("zunnn","sql3:"+sql);
            }else{
                sql = "select * from nianhui where size like '%"+arr[0]+"%'";
            }
            and2 = " ";
        }else{
            for (int i = 1; i < arr.length; i++) {
                Log.i("zunn","arr.length："+arr.length);
                and2 = getandsize(arr[i].trim(),arr.length);
            }
            sql = "SELECT * FROM nianhui where size like '%"+arr[0]+"%'"+and2+and1;
        }
        Log.i("zunn","size and1:"+and2);
    }
    public String getandname(String s, int arrlength){
        andd = "and name like '%"+s+"%'";
        //andd = "and name like \"%"+s+"%\" and size like \"%"+s+"%\"";
        //for(int i = 1; i < arrlength; i++){
            andname = andname + andd;
            Log.i("zunn","getandname:"+andname);
        return andname;
    }
    public String getandsize(String s, int arrlength){
        andd2 = "and size like '%"+s+"%'";
        //andd = "and name like \"%"+s+"%\" and size like \"%"+s+"%\"";
        //for(int i = 1; i < arrlength; i++){
        andsize = andsize + andd2;
        Log.i("zunn","getandsize:"+andsize);
        return andsize;
    }
    public void dbSearchSize(String s) {
        Cursor cursor;
        Log.i("zun", "dbSearch()");
        Log.i("zun", "dbSearch2()");

        String[] arr = s.trim().split("\\s+"); //字符串去掉前后空格 再进行分隔生成数组操作(把空格都过滤掉，但是首位的空格无法过滤)
        //Log.i("zunn","arr.length："+arr.length);
        Log.i("zunn", "arr:" + Arrays.toString(arr));

        for (int i = 1; i < arr.length; i++) {
            Log.i("zunn", "arr.length：" + arr.length);
            and1 = getandname(arr[i].trim(), arr.length);
        }
        Log.i("zunn", "and1:" + and1);
        if (arr.length == 1) {
            cursor = db.rawQuery("select * from nianhui where size like \"%" + arr[0] + "%\"", null);
            Log.i("zunn", "length == 1");
        } else {
            //cursor = db.rawQuery("select * from nianhui where name like \"%"+arr[0]+"%\" and name like \"%"+arr[1]+"%\"",null);
            cursor = db.rawQuery("select * from nianhui where size like \"%" + arr[0] + "%\"" + and1, null);
            Log.i("zunn", "length:" + arr.length);
            Log.i("zunn", "arr[0]:" + arr[0]);
            Log.i("zunn", "arr[1]:" + arr[1]);
            Log.i("zunn", "and1成功:");
            and = "";
        }
        Log.i("zun", "dbSearch3()");

        mDataList2 = new ArrayList<>();
        while (cursor.moveToNext()) {
            int temp_id = cursor.getColumnIndex("id");
            int temp_remark = cursor.getColumnIndex("remark");
            int temp_name = cursor.getColumnIndex("name");
            int temp_size = cursor.getColumnIndex("size");
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
            nianhui_info nh = new nianhui_info(id, remark, name, size, sizePlus, sellingPrice, purchasingPrice, time, supplier);
            mDataList2.add(nh);
        }
        mDataList = mDataList2;
        mAdapter.notifyDataSetChanged(mDataList);
        Log.i("zun", "1");
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