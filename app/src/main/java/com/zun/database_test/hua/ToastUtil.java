package com.zun.database_test.hua;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具包
 */
public class ToastUtil {
    private static ToastUtil mToastEx;
    private static Toast mToast;

    protected ToastUtil(Context context) {
        if (null == mToast) {
            mToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_LONG);
            // mToast.setGravity(Gravity.CENTER, 0, 0);
        }
    }

    public static ToastUtil getInstance(Context context) {
        if (null == mToastEx) {
            mToastEx = new ToastUtil(context.getApplicationContext());
        }
        return mToastEx;
    }

    public void showByLongDuration(String text) {
        if (null == mToast) {
            return;
        }
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_LONG);
        // mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public void showByLongDuration(int textID) {
        if (null == mToast) {
            return;
        }
        mToast.setText(textID);
        // mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    public void showByShortDuration(String text) {
        if (null == mToast) {
            return;
        }
        mToast.setText(text);
        // mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showByShortDuration(int textID) {
        if (null == mToast) {
            return;
        }
        mToast.setText(textID);
        // mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * toast cancel ( Normally, it can be used when we exit the application )
     */
    public void cancel() {
        if (null == mToast) {
            return;
        }
        mToast.cancel();
    }
}
