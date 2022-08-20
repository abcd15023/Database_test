package com.zun.database_test.hua;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


/**
 * SharePreference操作类
 */
public class UserPreferencesUtil {
    private static final String ENCRYPT_KEY = "&G(D#d*t";

    private static final int DEFAULT_INTEGER_VALUE = -1;
    //private static SharedPreferences preferences;
    private final static String SP_FILE_NAME = "Setting";
    public final static String SHARED_PREFERENCES_FILE_NAME = SP_FILE_NAME + ".xml";

    public final static String KEY_UPLOAD_USE_STATUS_TIME = "uploadUseStatusTime";

    private static final boolean IS_ENCRYPT = false;//是否加密
    private static final boolean IS_SHOW_LOG = true;

    private UserPreferencesUtil() {
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getApplicationContext().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    private static void showLog(String logStr) {
        if (IS_SHOW_LOG) {
            L.v(L.LEVEL_DEVELOP, logStr);
        }
    }

    public static void putBoolean(Context context, String key, boolean value) {
        showLog("Preferences putInt key = " + key + ",value = " + value);
        if (IS_ENCRYPT) {
            putBooleanEncrypt(context, key, value);
        } else {
            putBooleanNormal(context, key, value);
        }
    }

    public static boolean getBoolean(Context context, String key) {
        showLog("Preferences getInt key = " + key);
        if (IS_ENCRYPT) {
            return getBooleanEncrypt(context, key);
        } else {
            return getBooleanNormal(context, key);
        }
    }

    public static void putInt(Context context, String key, int value) {
        showLog("Preferences putInt key = " + key + ",value = " + value);
        if (IS_ENCRYPT) {
            //putIntEncrypt(context, key, value);
        } else {
        }
        putIntNormal(context, key, value);
    }

    public static int getInt(Context context, String key) {
        showLog("Preferences getInt key = " + key);
        if (IS_ENCRYPT) {
            //return getIntEncrypt(context, key);
        } else {
        }
        return getIntNormal(context, key);
    }

    public static void putLong(Context context, String key, long value) {
        showLog("Preferences putLong key = " + key + ",value = " + value);
        if (IS_ENCRYPT) {
            //putLongEncrypt(context, key, value);
        } else {
        }
        putLongNormal(context, key, value);
    }

    public static long getLong(Context context, String key) {
        showLog("Preferences getLong key = " + key);
        if (IS_ENCRYPT) {
            //return getLongEncrypt(context, key);
        } else {
        }
        return getLongNormal(context, key);
    }

    public static void putString(Context context, String key, String value) {
        showLog("Preferences putString key = " + key + ",value = " + value);
        if (IS_ENCRYPT) {
            //putStringEncrypt(context, key, value);
        } else {
        }
        putStringNormal(context, key, value);
    }

    public static String getString(Context context, String key, String defValue) {
        showLog("Preferences getString key = " + key);
        if (IS_ENCRYPT) {
            //return getStringEncrypt(context, key, defValue);
        } else {
        }
        return getStringNormal(context, key, defValue);
    }

    private static void putBooleanEncrypt(Context context, String key, boolean value) {
    }

    private static void putBooleanNormal(Context context, String key, boolean value) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(key, value);
        //edit.apply();
        boolean result = edit.commit();
        showLog("Preferences putBooleanNormal end key = " + key + ",result = " + result);
    }

    private static boolean getBooleanEncrypt(Context context, String key) {
        return false;
    }

    private static boolean getBooleanNormal(Context context, String key) {
        SharedPreferences preferences = getPreferences(context);
        boolean result = preferences.getBoolean(key, false);
        showLog("Preferences getBooleanNormal end key = " + key + ",result = " + result);
        return result;
    }


    private static void putIntNormal(Context context, String key, int value) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(key, value);
        //edit.apply();
        boolean result = edit.commit();
        showLog("Preferences putIntNormal end key = " + key + ",result = " + result);
    }

    private static int getIntNormal(Context context, String key) {
        SharedPreferences preferences = getPreferences(context);
        int result = preferences.getInt(key, DEFAULT_INTEGER_VALUE);
        showLog("Preferences getIntNormal end key = " + key + ",result = " + result);
        return result;

    }

    private static void putLongNormal(Context context, String key, long value) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong(key, value);
        //edit.apply();
        boolean result = edit.commit();
        showLog("Preferences putLongNormal end key = " + key + ",result = " + result);
    }

    private static long getLongNormal(Context context, String key) {
        SharedPreferences preferences = getPreferences(context);
        long result = preferences.getLong(key, DEFAULT_INTEGER_VALUE);
        showLog("Preferences getLongNormal end key = " + key + ",result = " + result);
        return result;
    }

    private static void putStringNormal(Context context, String key, String value) {
        SharedPreferences preferences = getPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        //edit.apply();
        boolean result = edit.commit();
        showLog("Preferences putStringNormal end key = " + key + ",result = " + result);
    }

    private static String getStringNormal(Context context, String key, String defValue) {
        SharedPreferences preferences = getPreferences(context);
        String result = preferences.getString(key, defValue);
        showLog("Preferences getStringNormal end key = " + key + ",result = " + result);
        return result;
    }
}
