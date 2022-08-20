package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-20 17:53
 *Description:
 *
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static DBHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DBHelper INSTANCE = new DBHelper(NianHuiApplication.getInstance());
    }

    // 自带构造函数
    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 为了每次构造时不用传入dbName和版本号，自己得新定义一个构造方法
    private DBHelper(NianHuiApplication context) {
        this(context, DBConstant.DB_NAME, null, DBConstant.VERSION);// 调用上面的构造方法
    }


    /**
     * 创建表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        createDB(db);
        L.v(L.LEVEL_TEST, "DBHelper created database");

        // 若不是第一个版本安装，直接执行数据库升级
        // 请不要修改FIRST_DATABASE_VERSION的值，其为第一个数据库版本大小
        //final int FIRST_DATABASE_VERSION = 1;
        //onUpgrade(mDB, FIRST_DATABASE_VERSION, VERSION);
    }

    /**
     * 在事务中创建
     *
     * @param db 数据库
     */
    private void createDB(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(DBConstant.CREATE_GOOD_INFO);
            //设置事务标志为成功，当结束事务时就会提交事务
            db.setTransactionSuccessful();
        } catch (Exception e) {
            L.e(L.LEVEL_TEST, "create database error : " + e);
        } finally {
            //结束事务
            db.endTransaction();
        }
    }

    /**
     * 更新表
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        L.i(L.LEVEL_TEST, "update dataBase oldVersion = " + oldVersion + ", newVersion=" + newVersion);

        if (oldVersion < newVersion) {
            /*if (newVersion == 2) {
                updateDataBase(db,2);
            }

            //版本2到版本3
            if (oldVersion == 2 && newVersion == 3) {
                updateDataBase(db,3);
            }

            //版本2到版本4，版本3与版本4都更新
            if (oldVersion == 2 && newVersion == 4) {
                updateDataBase(db,3);
                updateDataBase(db,4);
            }

            //版本3到版本4
            if (oldVersion == 3 && newVersion == 4) {
                updateDataBase(db,4);
            }//*/

            //版本2到版本3
            //版本2到版本4，版本3与版本4都更新
            //版本3到版本4
            for (int versionPos = oldVersion; versionPos < newVersion; versionPos++) {
                updateDataBase(db, versionPos + 1);
            }
        }
    }

    /**
     * 各个版本的数据库更新操作
     */
    private void updateDataBase(SQLiteDatabase db, int version) {
       /* switch (version) {
            case 2:
                //数据库版本升级，v2在应用信息表添加上传状态
                addColumn(db, DBConstant.TABLE_APP_INFO, AppInfo.UPLOAD_STATUS, "integer");
                break;
            case 3:
                db.execSQL(DBConstant.CREATE_SETTING_INFO);
                break;
            case 4:
                db.execSQL(DBConstant.CREATE_LOG_INFO);
                break;
            case 5:
                db.execSQL(DBConstant.CREATE_PUSH_INFO);
                break;
            case 6:
                addColumn(db, DBConstant.TABLE_PUSH_INFO, PushInfo.PUSH_ACCOUNT_ID, "varchar");
                break;
            case 7:
                db.execSQL(DBConstant.CREATE_UNIQUE_PUSH);
                break;
            case 8:
                db.execSQL(DBConstant.CREATE_NOTICE);
                break;
            case 9:
                //新增字段--版本号
                addColumn(db, DBConstant.TABLE_RECOMMEND_APP_INFO, RecommendAppInfo.RECOMMEND_APP_VERSION_CODE, "varchar");
                break;
            case 10:
                db.execSQL(DBConstant.CREATE_RECOMMEND_APP_INFO);
                db.execSQL(DBConstant.CREATE_PUSH_INFO);
                db.execSQL(DBConstant.CREATE_UNIQUE_PUSH);
                db.execSQL(DBConstant.CREATE_NOTICE);
                break;
            default:
                break;
        }*/
    }


    /**
     * 版本变更时
     */
    public DBHelper(Context context, int version) {
        this(context, DBConstant.DB_NAME, null, version);
    }

    /**
     * 更新列
     */
    public void updateColumn(SQLiteDatabase db, String tableName, String oldColumn, String newColumn, String typeColumn) {

        String sql = "ALTER TABLE " + tableName + " CHANGE " + oldColumn + " " + newColumn + " " + typeColumn;

        try {
            db.execSQL(sql);
        } catch (Exception e) {
            L.e(L.LEVEL_TEST, "alter database column error : " + e + ",sql = " + sql);
        }
    }

    public void addColumn(SQLiteDatabase db, String tableName, String newColumn, String dataType) {

        String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + newColumn + " " + dataType;//这里默认为varchar类型
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            L.e(L.LEVEL_TEST, "alter database column error : " + e + ",sql = " + sql);
        }
    }


}
