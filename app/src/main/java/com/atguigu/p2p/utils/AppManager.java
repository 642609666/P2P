package com.atguigu.p2p.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/11 0011.
 * 功能:
 */

public class AppManager {
    /**
     * 统一应用程序中所有的Activity的栈管理（单例）
     * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
     */

    public AppManager() {
    }

    private static AppManager mAppManager = new AppManager();

    public static AppManager getInstance() {
        return mAppManager;
    }

    private Stack<Activity> mStack = new Stack<>();

    /**
     * 添加活动
     *
     * @param activity
     */
    public void addActivity(Activity activity) {

        if (activity != null) {
            mStack.add(activity);
        }

    }

    /**
     * 移除传入的活动
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {

        if (activity != null) {
            for (int i = mStack.size() - 1; i >= 0; i--) {

                if (activity.getClass().equals(mStack.get(i).getClass())) {

                    mStack.get(i).finish();

                    mStack.remove(mStack.get(i));
                }
            }
        }
    }

    /**
     * 移除所有的活动
     */
    public void removeAll() {

        for (int i = mStack.size() - 1; i >= 0; i--) {

            mStack.get(i).finish();

            mStack.remove(mStack.get(i));
        }
    }

    /**
     * 移除当前的活动
     *
     */
    public void removeatPresentActivity() {
            Activity activity1 = mStack.get(mStack.size() - 1);
            activity1.finish();
            mStack.remove(activity1);
    }
    /**
     * 获得所有数
     */
    public int getStack(){
        return mStack.size();
    }
}
