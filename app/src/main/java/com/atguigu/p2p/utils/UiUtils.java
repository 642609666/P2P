package com.atguigu.p2p.utils;

import android.content.Context;
import android.view.View;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/11 0011.
 * 功能:和UI相关的一些操作类
 */

public class UiUtils {

    /**
     * 获得上下文
     *
     * @return
     */
    public static Context getContext() {
        return MyApplication.getmContext();
    }

    /**
     * 获得布局
     *
     * @param layoutId
     * @return
     */
    public static View getView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    /**
     * 得到颜色
     *
     * @param color
     * @return
     */
    public static int getColor(int color) {
        return getContext().getResources().getColor(color);
    }

    /**
     * 得到字符串数组
     *
     * @param stringId
     * @return
     */
    public static String[] getStringArray(int stringId) {
        return getContext().getResources().getStringArray(stringId);
    }

    /**
     * dp转换成px  密度转换像素
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
    }

    /**
     * px转换成dp 像素转换密度
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }
}
