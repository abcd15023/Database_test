package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-20 18:07
 *Description:
 *
 */

public class BaseInfo {
    /** 数据库字段*/
    public static final String ID = "id";
    public static final String INSERT_TIME = "insertTime";
    public static final String UPDATE_TIME = "updateTime";

    /** 字段下标记*/
    public static final int ID_INDEX = 0;
    public static final int INSERT_TIME_INDEX = 1;
    public static final int UPDATE_TIME_INDEX = 2;

    /** 流水号*/
    private String id;

    /** 插入时间*/
    private String insertTime;

    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
