package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-20 18:24
 *Description:
 *
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;

public class GoodInfoDAO extends BaseDAO {

    private final String TABLE_NAME = GoodInfo.TABLE_GOOD_INFO;

    @Override
    public long insertData(BaseInfo temp) {
        if (temp == null) {
            return -1;
        }

        ContentValues values = new ContentValues();

        GoodInfo goodInfo = (GoodInfo) temp;
        if (TextUtils.isEmpty(goodInfo.getName())) {
            L.e(L.LEVEL_TEST, "insert good info error,which Name is null");
            return -1;
        }

        //values.put(GoodInfo.ID, GoodInfo.getId());ID为自增id不需要插入
        values.put(GoodInfo.INSERT_TIME, String.valueOf(System.currentTimeMillis()));
        values.put(GoodInfo.UPDATE_TIME, String.valueOf(System.currentTimeMillis()));
        values.put(GoodInfo.NAME, goodInfo.getName());
        values.put(GoodInfo.SIZE, goodInfo.getSize());
        values.put(GoodInfo.SIZE_SON, goodInfo.getSizeSon());
        values.put(GoodInfo.SELL_PRICE, goodInfo.getSellPrice());
        values.put(GoodInfo.PURCHASE_PRICE, goodInfo.getPurchasePrice());
        values.put(GoodInfo.SUPPLIER, goodInfo.getSupplier());
        values.put(GoodInfo.REMARK, goodInfo.getRemark());
        values.put(GoodInfo.EXTEND, goodInfo.getExtend());

        return insertData(TABLE_NAME, values);
    }

    @Override
    public int deleteData(String key, String value) {
        return deleteData(TABLE_NAME, key, value);
    }

    @Override
    public int deleteData(String key, String condition, String[] whereArgs) {
        return 0;
    }

    @Override
    public int deleteAllData() {
        return deleteAllData(TABLE_NAME);
    }

    @Override
    public int updateData(String key, String value, String changeKey, String changeVal) {
        return updateData(TABLE_NAME, key, value, changeKey, changeVal);
    }

    @Override
    protected int selectCountBase(String sql) {
        //openDB();

        int count = 0;
        try {
            Cursor cursor = mDB.rawQuery(sql, null);
            count = cursor.getCount();
            cursor.close();
        } catch (Exception e) {
            L.e(L.LEVEL_TEST, "spl is " + sql + ";which has error : " + e.toString());
        }

        //closeDB();
        return count;
    }

    @Override
    protected ArrayList selectDataBase(String sql) {
        //openDB();

        ArrayList<GoodInfo> goodInfoArray = null;
        try {
            Cursor cursor = mDB.rawQuery(sql, null);

            //建立动态数组保存查询返回值
            goodInfoArray = new ArrayList<>();
            GoodInfo goodInfo;
            if (cursor.moveToFirst()) {
                do {
                    //遍历cursor对象，取出数据
                    goodInfo = new GoodInfo();//实例化

                    goodInfo.setId(String.valueOf(cursor.getString(GoodInfo.ID_INDEX)));
                    goodInfo.setInsertTime(cursor.getString(GoodInfo.INSERT_TIME_INDEX));
                    goodInfo.setUpdateTime(cursor.getString(GoodInfo.UPDATE_TIME_INDEX));
                    goodInfo.setName(cursor.getString(GoodInfo.NAME_INDEX));
                    goodInfo.setSize(cursor.getString(GoodInfo.SIZE_INDEX));
                    goodInfo.setSizeSon(cursor.getString(GoodInfo.SIZE_SON_INDEX));
                    goodInfo.setSellPrice(cursor.getString(GoodInfo.SELL_PRICE_INDEX));
                    goodInfo.setPurchasePrice(cursor.getString(GoodInfo.PURCHASE_PRICE_INDEX));
                    goodInfo.setSupplier(cursor.getString(GoodInfo.SUPPLIER_INDEX));
                    goodInfo.setRemark(cursor.getString(GoodInfo.REMARK_INDEX));
                    goodInfo.setExtend(cursor.getString(GoodInfo.EXTEND_INDEX));

                    //将对象添加到动态数组中
                    goodInfoArray.add(goodInfo);

                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            L.e(L.LEVEL_TEST, "spl is " + sql + ";which has error : " + e.toString());
        }

        //closeDB();

        return goodInfoArray;
    }
}
