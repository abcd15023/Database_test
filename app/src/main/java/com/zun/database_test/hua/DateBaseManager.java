package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-20 17:03
 *Description: 封装数据库的基本操作
 *
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DateBaseManager {

    private SQLiteDatabase mSQLiteDatabase;
    private SQLiteOpenHelper mSQLiteOpenHelper;

    //数据库的开关
    //数据库的Create，建库与建表
    //多个库的情况；比如，A账户，有个聊天记录表、装备表、好友表等；此时切换用户B，可以直接切换数据库，而不是切表，或者根据id搜索
    //增删该查操作

}
