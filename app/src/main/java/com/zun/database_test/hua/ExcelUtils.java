package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-21 14:19
 *Description:
 *
 */

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    private static final String FILE_EXT = ".xls";
    private static final String FILE_NAME = "NianHui";
    private static final String FILE_NAME_TRUE = FILE_NAME + FILE_EXT;

    private static final String FILE_PATH = Environment.getExternalStorageDirectory().toString()
            + File.separator;

    private static final String FILE_PATH_TRUE = FILE_PATH + FILE_NAME_TRUE;


    public static void readExcel(final IReadExcelListener listener) {
        ThreadManager.getInstance()
                .execute(new PriorityRunnable(ThreadManager.PRIORITY_READ_EXCEL) {
                    @Override
                    public void doSomeThing() {
                        try {
                            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(FILE_PATH_TRUE)));
                            //获取第一个工作表的对象
                            Sheet sheet = workbook.getSheetAt(0);

                            List<GoodInfo> resultList = new ArrayList<>();
                            Iterator<Row> rit = sheet.rowIterator();
                            while (rit.hasNext()) {
                                Row row = rit.next();
                                //ContentValues values = new ContentValues();
                                //id	remark	name	size	sizePlus	sellingPrice	purchasingPrice	time	supplier
                                int pos1 = 0;
                                Cell remarkCell = row.getCell(++pos1);//从remark开始
                                Cell nameCell = row.getCell(++pos1);
                                Cell sizeCell = row.getCell(++pos1);
                                Cell sizePlusCell = row.getCell(++pos1);
                                Cell sellPriceCell = row.getCell(++pos1);
                                Cell purchasingPriceCell = row.getCell(++pos1);
                                pos1++;//跳过time字段
                                Cell supplierCell = row.getCell(++pos1);

                                GoodInfo goodInfo = new GoodInfo();
                                goodInfo.setRemark(remarkCell.getStringCellValue());
                                goodInfo.setName(nameCell.getStringCellValue());
                                goodInfo.setSize(sizeCell.getStringCellValue());
                                goodInfo.setSizeSon(sizePlusCell.getStringCellValue());
                                goodInfo.setSellPrice(sellPriceCell.getStringCellValue());
                                goodInfo.setPurchasePrice(purchasingPriceCell.getStringCellValue());
                                goodInfo.setSupplier(supplierCell.getStringCellValue());

                                resultList.add(goodInfo);
                            }

                            //读取数据关闭
                            workbook.close();

                            if (listener != null) {
                                listener.onCompleted(resultList, "成功");
                            }

                        } catch (FileNotFoundException e) {
                            L.e(L.LEVEL_TEST, "error is " + e);
                            if (listener != null) {
                                listener.onCompleted(null, "\"文件不存在\"");
                            }

                        } catch (Exception e) {
                            L.e(L.LEVEL_TEST, "error is " + e);
                            if (listener != null) {
                                listener.onCompleted(null, e.getMessage());
                            }
                        }
                    }
                });
    }


    public interface IReadExcelListener {
        void onCompleted(List<GoodInfo> result, String resultStr);
    }

    public interface IWriteExcelListener {
        void onCompleted(String fileName, String path, String resultStr);
    }
}
