package com.atguigu.p2p.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/15 0015.
 * 功能:
 */

public abstract class BaseAvtivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);

        //初始化布局
        initTitle();
        //初始化fragment
        initData();
        //设置监听
        initListener();
    }

    protected abstract int getLayoutId();

    /**
     * 设置标头的数据
     */
    protected abstract void initTitle();

    /**
     * 设置数据
     */
    protected abstract void initData();

    /**
     * 设置监听
     */
    protected abstract void initListener();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
