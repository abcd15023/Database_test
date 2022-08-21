package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-21 14:26
 *Description:
 *
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {

    private static final int CORE_POOL_SIZE = 1;
    private static final int MAXIMUM_POOL_SIZE = 1;
    private static final long KEEP_ALIVE_TIME = 0;

    //定义各线程的优先级
    public static final int MAX_PRIORITY = 100000000;
    public static final int MIN_PRIORITY = 1;

    /** 可根据需要自定义线程的优先级，暂定所有优先级为5*/

    //应用信息
    public static final int PRIORITY_SEARCH_GOOD = 5;
    public static final int PRIORITY_GET_GOOD_COUNT = 5;
    public static final int PRIORITY_READ_EXCEL = 5;
    public static final int PRIORITY_OUTPUT_EXCEL = 5;
    public static final int PRIORITY_UPDATE_GOOD = 5;

    private ExecutorService mPriorityThreadPool;

    private ThreadManager() {
        mPriorityThreadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<Runnable>());
    }

    public void execute(PriorityRunnable runnable){
        mPriorityThreadPool.execute(runnable);
    }

    public static ThreadManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ThreadManager INSTANCE = new ThreadManager();
    }

}
