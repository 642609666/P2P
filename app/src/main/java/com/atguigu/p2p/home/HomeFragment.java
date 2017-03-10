package com.atguigu.p2p.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/10 0010.
 * 功能:主页
 */

public class HomeFragment extends BaseFragment {

    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        Log.e("TAG", "主页加载视图成功");
        return view;
    }

    @Override
    protected void initData() {

        Log.e("TAG", "主页加载数据成功");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.base_back, R.id.base_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_back:

                break;
            case R.id.base_setting:
                Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
