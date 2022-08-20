package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-20 17:56
 *Description:
 *
 */

import android.app.Application;

public class NianHuiApplication extends Application {

    private static NianHuiApplication mInstance;

    public static NianHuiApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }
}
