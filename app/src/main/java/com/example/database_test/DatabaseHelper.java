package com.example.database_test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库sql语句 并 执行
        //String sql = "create table nianhui(name varchar(20),size varchar(20))";

        //这里id设为自增主键，但实际上主键是看不见的rowid,有了主键就能删除整行和根据id修改字段，方便了滑动删除和拖拽排序实现      //name,size,sizePlus设为Not Null，但是没有作用，因为数据表是导入csv文件生成的
        String sql = "create table nianhui (id integer primary key autoincrement, remark text,name text not null,size text not null,sizePlus text not null,sellingPrice text,purchasingPrice text,time text,supplier text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
