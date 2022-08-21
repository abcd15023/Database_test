package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-21 14:27
 *Description:
 *
 */

import androidx.annotation.NonNull;

public abstract class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {
    private int mPriority;

    public PriorityRunnable(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException();
        }
        this.mPriority = priority;
    }

    @Override
    public int compareTo(@NonNull PriorityRunnable another) {
        int my = this.getPriority();
        int other = another.getPriority();

        return my < other ? 1 : my > other ? -1 : 0;
    }

    @Override
    public void run() {
        doSomeThing();
    }

    public abstract void doSomeThing();

    public int getPriority() {
        return mPriority;
    }
}
