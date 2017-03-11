package com.atguigu.p2p.utils;

import android.util.Log;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/11 0011.
 * 功能:
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private CrashHandler() {
    }

    private static CrashHandler mCrashHandler = new CrashHandler();

    public static CrashHandler getInstance() {
        return mCrashHandler;
    }

    public void init() {
        //把当前的类设置成默认的处理未捕获异常
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e("uncaughtException", "uncaughtException: ");
    }
}
