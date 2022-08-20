package com.zun.database_test.hua;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;


/**
 * log工具封装
 * <p>
 * 是否显示：分正式、测试、开发，在L.java里面过滤，不向外提供
 * 正式：出厂的机器（应该不会用到）
 * 测试：功能开发稳定（大多数log）
 * 开发：自己测试log，会打印很多的那种
 * <p>
 * 是否保存：保存至txt中，或保存在数据库中
 * 保存至txt：不同模块保存在不同txt
 * 保存在数据库中：建议数据少量
 * <p>
 * PS:有些设计是为了减少计算，因为log会有很多，减少计算是很有必要的
 * <p>
 * Created by zhangZhenhua on 2017/11/3.
 */

public class L {

    public static final String DEFAULT_TAG = "NianHuiLog";

    //logLevel
    public static final int LEVEL_RELEASE = 1;//理论上不需要，反正是一直显示
    public static final int LEVEL_TEST = 2;
    public static final int LEVEL_DEVELOP = 3;

    public static int mCurrentLogLevel = 3;//比上面的等级要高1，减少计算

    //在代码中取反，不需要log时直接return；减少计算
    private static final boolean NO_LOGGER = false;
    private static final boolean NO_STACK = false;

    private L() {
    }

    public static void v(int logLevel, String savePath, String tag, String msg) {
        if (NO_LOGGER) {
            return;
        }

        if (logLevel < mCurrentLogLevel) {
            Log.v(tag, printfClassLineStr(msg));
        }

        saveLog(savePath, msg);
    }

    public static void d(int logLevel, String savePath, String tag, String msg) {
        if (NO_LOGGER) {
            return;
        }

        if (logLevel < mCurrentLogLevel) {
            Log.d(tag, printfClassLineStr(msg));
        }

        saveLog(savePath, msg);
    }

    public static void i(int logLevel, String savePath, String tag, String msg) {
        if (NO_LOGGER) {
            return;
        }

        if (logLevel < mCurrentLogLevel) {
            Log.i(tag, printfClassLineStr(msg));
        }

        saveLog(savePath, msg);
    }

    public static void w(int logLevel, String savePath, String tag, String msg) {
        if (NO_LOGGER) {
            return;
        }

        if (logLevel < mCurrentLogLevel) {
            Log.w(tag, printfClassLineStr(msg));
        }

        saveLog(savePath, msg);
    }

    public static void e(int logLevel, String savePath, String tag, String msg) {
        if (NO_LOGGER) {
            return;
        }

        if (logLevel < mCurrentLogLevel) {
            Log.e(tag, printfClassLineStr(msg));
        }

        saveLog(savePath, msg);
    }

    public static void v(int logLevel, String savePath, String msg) {
        v(logLevel,savePath,DEFAULT_TAG, msg);
    }

    public static void d(int logLevel,String savePath, String msg) {
        d(logLevel,savePath,DEFAULT_TAG, msg);
    }

    public static void i(int logLevel,String savePath, String msg) {
        i(logLevel,savePath,DEFAULT_TAG, msg);
    }

    public static void w(int logLevel,String savePath, String msg) {
        w(logLevel,savePath,DEFAULT_TAG, msg);
    }

    public static void e(int logLevel,String savePath, String msg) {
        e(logLevel,savePath,DEFAULT_TAG, msg);
    }

    public static void v(int logLevel, String msg) {
        v(logLevel,null,DEFAULT_TAG, msg);
    }

    public static void d(int logLevel, String msg) {
        d(logLevel,null,DEFAULT_TAG, msg);
    }

    public static void i(int logLevel, String msg) {
        i(logLevel,null,DEFAULT_TAG, msg);
    }

    public static void w(int logLevel, String msg) {
        w(logLevel,null,DEFAULT_TAG, msg);
    }

    public static void e(int logLevel, String msg) {
        e(logLevel,null,DEFAULT_TAG, msg);
    }


    private static void saveLog(String savePath, String msg) {
        if (TextUtils.isEmpty(savePath)) {
            return;
        }
    }

    /**
     * 打印类名和行号字符串
     */
    private static String printfClassLineStr(String str) {
        if (NO_STACK) {
            return  str;
        }
        
        StringBuilder strBuffer = new StringBuilder();
        StackTraceElement[] mStackTrace = new Throwable().getStackTrace();

        strBuffer.append(str);
        strBuffer.append("，File:(").append(mStackTrace[3].getFileName());
        strBuffer.append(':').append(mStackTrace[3].getLineNumber());
        strBuffer.append(")，Method:").append(mStackTrace[3].getMethodName());

        return strBuffer.toString();
    }

    public static String formatStackTrace(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        String rtn = Arrays.toString(throwable.getStackTrace());
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            printWriter.flush();
            writer.flush();
            rtn = writer.toString();
            printWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ignored) {
        }
        return rtn;
    }
}
