package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-20 17:38
 *Description: 数据库常量
 *
 */

public class DBConstant {

    public static final String DB_NAME = "nian_hui.mDB";// 数据库名
    public static final int VERSION = 1;// 版本号


    public static final String CREATE_GOOD_INFO ="create table if not exists " + GoodInfo.TABLE_GOOD_INFO + " ("
            + GoodInfo.ID + " integer primary key autoincrement,"
            + GoodInfo.INSERT_TIME + " varchar(100),"
            + GoodInfo.UPDATE_TIME + " varchar(100),"
            + GoodInfo.NAME + " varchar(200),"
            + GoodInfo.SIZE + " varchar(100),"
            + GoodInfo.SIZE_SON + " varchar(100),"
            + GoodInfo.SELL_PRICE + " varchar(100),"
            + GoodInfo.PURCHASE_PRICE + " varchar(100),"
            + GoodInfo.SUPPLIER + " varchar(100),"
            + GoodInfo.REMARK + " varchar(300),"
            + GoodInfo.EXTEND + " varchar(300))";
}
