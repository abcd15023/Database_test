package com.zun.database_test.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelToSQLite {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private Context mContext;
    private SQLiteDatabase database;
    private String mDbName;
    private boolean dropTable = false;

    public ExcelToSQLite(Context context, String dbName) {
        mContext = context;
        mDbName = dbName;
        try {
            database = SQLiteDatabase.openOrCreateDatabase(mContext.getDatabasePath(mDbName).getAbsolutePath(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ExcelToSQLite(Context context, String dbName, boolean dropTable) {
        mContext = context;
        mDbName = dbName;
        this.dropTable = dropTable;
        try {
            database = SQLiteDatabase.openOrCreateDatabase(mContext.getDatabasePath(mDbName).getAbsolutePath(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importFromAsset(final String assetFileName, final ImportListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    working(mContext.getAssets().open(assetFileName));
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(mDbName);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (database != null && database.isOpen()) {
                        database.close();
                    }
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    public void importFromFile(String filePath, ImportListener listener) {
        importFromFile(new File(filePath), listener);
    }

    private void importFromFile(final File file, final ImportListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //读取excel数据，只做了这件事
                    working(new FileInputStream(file));



                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(mDbName);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (database != null && database.isOpen()) {
                        database.close();
                    }
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                    }
                }
            }
        }).start();

    }

    private void working(InputStream stream) throws Exception {
        //一般excel库分为， workbook【整个表格】,sheet【单个表格选项】,cell【单元格】
        //拿到workbook
        HSSFWorkbook workbook = new HSSFWorkbook(stream);
        int sheetNumber = workbook.getNumberOfSheets();

        //遍历表格数量，建成不同的数据库表
        for (int i = 0; i < sheetNumber; i++) {
            createTable(workbook.getSheetAt(i));
        }

        //关闭数据库
        database.close();
    }

    private void createTable(Sheet sheet) {
        Cursor cursor = null;
        try {
            String tableName = sheet.getSheetName();
            //构建建表数据库SQL语句
            StringBuilder createTableSql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
            createTableSql.append(tableName);//表名
            createTableSql.append("(");

            //拿到sheet的迭代器，row的迭代器
            Iterator<Row> rowIterator = sheet.rowIterator();
            //这里只next了一次，所以取的是第一行，就我们的标题头
            Row titleRow = rowIterator.next();

            //建立一个 标题头的List容器
            List<String> titleNameList = new ArrayList<>();
            for (int index = 0; index < titleRow.getPhysicalNumberOfCells(); index++) {

                String titleName = titleRow.getCell(index).getStringCellValue();
                //添加到List容器
                titleNameList.add(titleName);

                //添加到SQL
                createTableSql.append(titleName);
                if (index == titleRow.getPhysicalNumberOfCells() - 1) {
                    createTableSql.append(" TEXT");//如果是行末，就不需要逗号
                } else {
                    createTableSql.append(" TEXT,");
                }
            }
            createTableSql.append(")");

            if (dropTable){
                //如果需要删除旧表，则执行
                database.execSQL("DROP TABLE IF EXISTS " + tableName);
            }
            //执行SQL
            database.execSQL(createTableSql.toString());

            //新增数据库字段
            for (String titleName : titleNameList) {
                // 根据 sheetName，获取到titleName的Index，如果Index小于0，则表示未找到
                cursor = database.rawQuery("SELECT * FROM " + tableName, null);
                int titleNameIndex = cursor.getColumnIndex(titleName);

                if (titleNameIndex < 0) {
                    //未找到，则添加
                    String type = "TEXT";
                    database.execSQL("ALTER TABLE " + tableName
                            + " ADD COLUMN " + titleName + " " + type + " NULL;");
                }
            }

            //通过迭代器，生成对应的ContentValues，然后执行插入语句
            while (rowIterator.hasNext()) {
                //第一次next()，得到的结果在上面，是title行；此处再次next()得到的是数据行
                Row dataRow = rowIterator.next();

                ContentValues valuesToInsert = new ContentValues();
                for (int index = 0; index < dataRow.getPhysicalNumberOfCells(); index++) {

                    if (dataRow.getCell(index).getCellTypeEnum() == CellType.NUMERIC) {
                        //使用put(String key, Double value)将cell的数据存入valuesToInsert
                        valuesToInsert.put(titleNameList.get(index), dataRow.getCell(index).getNumericCellValue());

                    } else {
                        valuesToInsert.put(titleNameList.get(index), dataRow.getCell(index).getStringCellValue());
                    }
                }
                //执行数据库插入操作
                long result = database.insertWithOnConflict(tableName, null, valuesToInsert, SQLiteDatabase.CONFLICT_IGNORE);

                //插入结果的判断
                if (result < 0) {
                    throw new RuntimeException("Insert value failed!");
                }
            }

        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public interface ImportListener {
        void onStart();

        void onCompleted(String dbName);

        void onError(Exception e);
    }

}