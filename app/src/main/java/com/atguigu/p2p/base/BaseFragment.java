package com.atguigu.p2p.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.p2p.home.view.LoadingPager;

import butterknife.ButterKnife;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/10 0010.
 * 功能:父类布局
 */

public abstract class BaseFragment extends Fragment {
    private LoadingPager loadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadingPager = new LoadingPager(getActivity()) {
            @Override
            protected void onSuccess(ResultState resultState, View sucessView) {
                ButterKnife.inject(BaseFragment.this, sucessView);
                initData(resultState.getJson());
            }

            @Override
            protected String getUrl() {
                return getChildUrl();
            }

            @Override
            public int getViewId() {
                return getLayoutid();
            }
        };

        return loadingPager;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据
        //initData();

        loadingPager.loadData();
    }


    protected abstract void initData(String json);


    public abstract int getLayoutid();

    //每一个fragment返回的地址
    public abstract String getChildUrl();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
