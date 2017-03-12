package com.atguigu.p2p.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/12 0012.
 * 功能:
 */

public class LoadNet {

    /**
     * 二次封装网络下载请求
     *
     * @param url 网络请求地址
     */
    public static void getDataNet(String url, final OnGetNet mOnGetNet) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, new AsyncHttpResponseHandler() {
            /**
             * 请求成功
             * @param content
             */
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                //  Log.e("TAG", "主页请求数据成功" + content);
                if (mOnGetNet != null) {
                    mOnGetNet.onSuccess(content);
                }
            }

            /**
             * 请求失败
             * @param error
             * @param content
             */
            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                // Log.e("TAG", "主页请求数据成功" + content);
                mOnGetNet.onFailure(content);
            }
        });
    }

    private OnGetNet mOnGetNet;

    public void setOnGetNet(OnGetNet onGetNet) {
        mOnGetNet = onGetNet;
    }

    public interface OnGetNet {
        /**
         * 成功接口
         *
         * @param content
         */
        void onSuccess(String content);

        /**
         * 失败接口
         *
         * @param content
         */
        void onFailure(String content);
    }
}
