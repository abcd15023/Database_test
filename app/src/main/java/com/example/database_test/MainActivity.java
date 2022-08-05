package com.example.database_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText et1, et11, et2, et22, et222, et3;
    Button btn_insert, btn_clear1;
    Button btn_update, btn_clear2;
    Button btn_del, btn_clear3;
    Button btn_clearchaxun,btn_del_database;
    TextView text_show;
    TextView text1,text2,text3;
    String gettext1,gettext11,gettext2,gettext22,gettext222,gettext3;
    SQLiteDatabase db;
    RecyclerView recyclerView1;
    ArrayList<nianhui_info> nianhuidata;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initDatabase();

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
                adapter.clearList();
                adapter.notifyDataSetChanged();
            }
        });
        btn_del_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deldb();
                dbSearch();
            }
        });
        //获取ReyeclerView对象
        recyclerView1 = findViewById(R.id.recycler_view);
        //创建一个LinearLayoutManager对象（线性布局），设置到ReyeclerView对象中。
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);


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
    }
    //初始化SQL数据库
    public void initDatabase(){
        //依靠DatabaseHelper带全部参数的构造函数创建数据库，数据库名为nianhui_db
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this, "nianhui",null,1);
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

        nianhuidata = new ArrayList<>();
        while(cursor.moveToNext()){
            int temp_name = cursor.getColumnIndex("name");
            int temp_size= cursor.getColumnIndex("size");
            int temp_id = cursor.getColumnIndex("id");

            int id = cursor.getInt(temp_id);
            String name = cursor.getString(temp_name);
            if (name.equals("")) {
                name = "空";
            }
            String size = cursor.getString(temp_size);
            if (size.equals("")) {
                size = "空";
            }
            textview_data = textview_data + "\n" + id +"       "+ name + "       "+size;
            nianhui_info nh = new nianhui_info(id,name,size);
            nianhuidata.add(nh);
        }
        adapter = new MyAdapter(nianhuidata); //然后获取MyAdapter实例，将数据传入构造方法，
        recyclerView1.setAdapter(adapter); //最后ReyeclerView对象实现setAdaper方法传入MyAdatper实例。
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