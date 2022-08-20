package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-20 18:24
 *Description:
 *
 */

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public abstract class BaseDAO {

    private DBHelper mDBHelper;
    public SQLiteDatabase mDB;

    public void openDB() {
        try {
            mDBHelper = DBHelper.getInstance();
            //mDB = mDBHelper.getWritableDatabase(DBConstant.DB_PASSWORD);
            mDB = mDBHelper.getWritableDatabase();
        } catch (Exception e) {
            L.e(L.LEVEL_TEST,"open db error : " + e);
        }
    }

    public void closeDB() {
        try {
            mDB.close();
            mDBHelper.close();
        } catch (Exception e) {
            L.e(L.LEVEL_TEST,"close db error : " + e);
        }
    }

    public boolean isOpen() {
        return mDB != null && mDB.isOpen();
    }

    protected long insertData(String tableName, ContentValues values) {

        //openDB();

        long newID = -1;
        try {
            newID = mDB.insert(tableName, null, values);
        } catch (Exception e) {
            L.e(L.LEVEL_TEST,tableName + "'s insertData method error : " + e);
        }

        //closeDB();

        return newID;
    }

    /**
     * 开启事务，批量更新
     */
    protected  long insertDateList(String tableName,ArrayList<ContentValues> contentValuesList) {
        long affectRows = 0;

        if (mDB == null) {
            L.e(L.LEVEL_TEST,"DB is null");
            return 0;
        }

        //开启事务
        mDB.beginTransaction();
        try{
            //批量插入
            for (ContentValues values : contentValuesList) {
                affectRows = affectRows + mDB.insert(tableName, null, values);
            }
            //设置事务标志为成功，当结束事务时就会提交事务
            mDB.setTransactionSuccessful();
        } catch(Exception e){
            L.e(L.LEVEL_TEST, "updateAppLimitList method transaction set error : " + e);
        } finally{
            //结束事务
            try {
                mDB.endTransaction();  //事务提交
            } catch (Exception e) {
                L.e(L.LEVEL_TEST, tableName + "'s insertDateList method transaction commit error : " + e);
            }
        }

        return affectRows;
    }


    /**
     * 设置开始事务
     */
    protected void beginTransaction(){
        mDB.beginTransaction();
    }

    /**
     * 事务处理成功，不设置会自动回滚不提交
     */
    protected void setTransactionSuccessful(){
        mDB.setTransactionSuccessful();
    }

    /**
     * 事务处理完成
     */
    protected void endTransaction(){
        mDB.endTransaction();
    }


    /**
     * 删除数据(想要删除非主键的一行，需要向查询相关主键)
     *
     * @param key   表示哪个属性
     * @param value 属性值等于它的
     * @return 返回被删除行数
     */
    protected int deleteData(String tableName, String key, String value) {

        String whereClause = key + " = ?";//删除的条件
        String[] whereArgs = {value};//删除的条件参数

        //openDB();

        int affectRow = -1;
        try {
            affectRow = mDB.delete(tableName, whereClause, whereArgs);
        } catch (Exception e) {
            L.e(L.LEVEL_TEST, tableName + "'s deleteData method error : " + e);
        }

        //closeDB();

        return affectRow;
    }

    /**
     * 使用事务批量删除
     *
     * @param tableName 表名字
     * @param key 关键字
     * @param whereArgs 关键字对应的值
     * @return 影响行数
     */
    protected  int deleteDateList(String tableName, String key, String[] whereArgs) {
        String whereClause = key + " = ?";//删除的条件,可以不用= ，可以用< 或 >

        int affectRows = 0;
        if (mDB == null) {
            L.e(L.LEVEL_TEST, "DB is null");
            return 0;
        }

        //开启事务
        mDB.beginTransaction();
        try{
            affectRows = mDB.delete(tableName, whereClause, whereArgs);
            //设置事务标志为成功，当结束事务时就会提交事务
            mDB.setTransactionSuccessful();
        } catch(Exception e){
            L.e(L.LEVEL_TEST, "deleteDateList method transaction set error : " + e);
        } finally{
            //结束事务
            try {
                mDB.endTransaction();  //事务提交
            } catch (Exception e) {
                L.e(L.LEVEL_TEST, tableName + "'s deleteDateList method transaction commit error : " + e);
            }
        }

        return affectRows;
    }

    /**
     * 开启事务，批量删除
     *
     * @param tableName 表名字
     * @param key 关键字
     * @param condition 比较符号，= < >
     * @param whereArgs 关键字对应的值
     * @return 影响行数
     */
    protected  int deleteData(String tableName, String key, String condition, String[] whereArgs) {
        if ("=".equals(condition) || "<".equals(condition) || ">".equals(condition)) {
            String whereClause = key + " " + condition + " ?";//删除的条件,可以不用= ，可以用< 或 >

            int affectRows = 0;

            if (mDB == null) {
                L.e(L.LEVEL_TEST, "DB is null");
                return 0;
            }

            //开启事务
            mDB.beginTransaction();
            try{
                affectRows = mDB.delete(tableName, whereClause, whereArgs);
                //设置事务标志为成功，当结束事务时就会提交事务
                mDB.setTransactionSuccessful();
            } catch(Exception e){
                L.e(L.LEVEL_TEST, "deleteData method transaction set error : " + e);
            } finally{
                //结束事务
                try {
                    mDB.endTransaction();  //事务提交
                } catch (Exception e) {
                    L.e(L.LEVEL_TEST, tableName + "'s deleteData method transaction commit error : " + e);
                }
            }

            return affectRows;
        }

        L.e(L.LEVEL_TEST, "deleteData method error : condition is wrong,it should be '=' , '<' or '>'");
        return 0;
    }


    /**
     * 删除表中所有数据
     *
     * @return 返回被删除行数
     */
    protected int deleteAllData(String tableName) {
        //openDB();

        int affectRow = -1;
        try {
            affectRow = mDB.delete(tableName, null, null);
        } catch (Exception e) {
            L.e(L.LEVEL_TEST, tableName + "'s deleteAllData method error : " + e);
        }

        //closeDB();


        return affectRow;
    }

    /**
     * 更新数据
     *
     * @param changeKey 需要修改的属性
     * @param changeVal 修改后的值
     * @param key       哪些行，若是主键则是哪一行
     * @param value     主键的值
     * @return 返回更新行数
     */
    protected int updateData(String tableName, String key, String value, String changeKey, String changeVal) {
        //实例化ContentValues
        ContentValues values = new ContentValues();
        values.put(changeKey, changeVal);//添加要更改的字段及内容
        String whereClause = key + " = ?";//修改条件
        String[] whereArgs = {value};//修改条件的参数

        //openDB();

        int affectRow = 0;
        try {
            affectRow = mDB.update(tableName, values, whereClause, whereArgs);//执行修改
        } catch (Exception e) {
            L.e(L.LEVEL_TEST, tableName + "'s updateData method error : " + e);
        }

        //closeDB();

        return affectRow;
    }

    protected  int updateData(String tableName, String key, String condition, String[] whereArgs,String changeKey, String changeVal) {
        if ("=".equals(condition) || "<".equals(condition) || ">".equals(condition) || "<>".equals(condition)) {
            String whereClause = key + " " + condition + " ?";//更新的条件，可以用 = ，< 或 >

            //实例化ContentValues
            ContentValues values = new ContentValues();
            values.put(changeKey, changeVal);//添加要更改的字段及内容

            int affectRows = 0;

            if (mDB == null) {
                L.e(L.LEVEL_TEST, "DB is null");
                return 0;
            }

            //开启事务
            mDB.beginTransaction();
            try{
                affectRows = mDB.update(tableName, values, whereClause, whereArgs);//执行修改
                //设置事务标志为成功，当结束事务时就会提交事务
                mDB.setTransactionSuccessful();
            } catch(Exception e){
                L.e(L.LEVEL_TEST, "updateData method transaction set error : " + e);
            } finally{
                //结束事务
                try {
                    mDB.endTransaction();  //事务提交
                } catch (Exception e) {
                    L.e(L.LEVEL_TEST, tableName + "'s updateData method transaction commit error : " + e);
                }
            }

            return affectRows;
        }


        L.e(L.LEVEL_TEST, "updateData error : condition is wrong,it should be '=' , '<' or '>'.");
        return 0;
    }

    protected int updateDataInTransaction(String tableName, String key, String value, String changeKey, String changeVal) {
        //实例化ContentValues
        ContentValues values = new ContentValues();
        values.put(changeKey, changeVal);//添加要更改的字段及内容
        String whereClause = key + " = ?";//修改条件
        String[] whereArgs = {value};//修改条件的参数

        int affectRow = 0;

        //开启事务
        mDB.beginTransaction();
        try{
            affectRow = mDB.update(tableName, values, whereClause, whereArgs);//执行修改
            //设置事务标志为成功，当结束事务时就会提交事务
            mDB.setTransactionSuccessful();
        } catch(Exception e){
            L.e(L.LEVEL_TEST, "updateData method transaction set error : " + e);
        } finally{
            //结束事务
            try {
                mDB.endTransaction();  //事务提交
            } catch (Exception e) {
                L.e(L.LEVEL_TEST, tableName + "'s updateData method transaction commit error : " + e);
            }
        }

        return affectRow;
    }

    /**
     * 开启事务，批量更新
     * @param tableName 表名
     * @param key 关键字
     * @param condition 条件< > =
     * @param whereArgs 关键值
     * @param values 需要被修改的键值对
     * @return 返回影响行数
     */
    protected  long updateDateList(String tableName, String key, String condition, String[] whereArgs, ContentValues values) {
        if ("=".equals(condition) || "<".equals(condition) || "<".equals(condition)) {
            String whereClause = key + " " + condition +" ?";//修改条件
            long affectRows = 0;

            if (mDB == null) {
                L.e(L.LEVEL_TEST, "DB is null");
                return 0;
            }

            //开启事务
            mDB.beginTransaction();
            try{
                affectRows = mDB.update(tableName, values, whereClause, whereArgs);//执行修改
                //设置事务标志为成功，当结束事务时就会提交事务
                mDB.setTransactionSuccessful();
            } catch(Exception e){
                L.e(L.LEVEL_TEST, "updateData method transaction set error : " + e);
            } finally{
                //结束事务
                try {
                    mDB.endTransaction();  //事务提交
                } catch (Exception e) {
                    L.e(L.LEVEL_TEST, tableName + "'s updateData method transaction commit error : " + e);
                }
            }

            return affectRows;
        }

        L.e(L.LEVEL_TEST, "updateData error : condition is wrong,it should be '=' , '<' or '>'.");
        return 0;
    }


    public abstract long insertData(BaseInfo temp);

    public abstract int deleteData(String key, String value);

    public abstract int deleteData(String key, String condition, String[] whereArgs);

    public abstract int deleteAllData();

    public abstract int updateData(String key, String value, String changeKey, String changeVal);

    protected abstract int selectCountBase(String sql);

    protected abstract ArrayList selectDataBase(String sql);
}
