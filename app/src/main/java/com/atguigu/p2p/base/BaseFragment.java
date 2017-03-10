package com.atguigu.p2p.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/10 0010.
 * 功能:父类布局
 */

public abstract class BaseFragment extends Fragment {
    public Context mContext;

    /**
     * 获得上下文
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 由子类初始化视图
     * @return
     */
    public abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 1.由子类初始化数据
     * 2.由子类联网请求数据
     */
    protected abstract void initData();
}
