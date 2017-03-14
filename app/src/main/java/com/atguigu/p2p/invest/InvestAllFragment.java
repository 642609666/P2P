package com.atguigu.p2p.invest;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseFragment;
import com.atguigu.p2p.invest.adapter.InvestAllAdapter;
import com.atguigu.p2p.invest.bean.InvestAllBean;
import com.atguigu.p2p.utils.AppNetConfig;

import butterknife.InjectView;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/14 0014.
 * 功能:
 */

public class InvestAllFragment extends BaseFragment {

    @InjectView(R.id.invest_all_lv)
    ListView investAllLv;
    private InvestAllBean mInvestAllBean;
    private InvestAllAdapter mInvestAllAdapter;

    @Override
    protected void initData(String json) {
        mInvestAllBean = JSON.parseObject(json, InvestAllBean.class);

        if (mInvestAllBean != null && mInvestAllBean.getData().size() > 0) {
            mInvestAllAdapter = new InvestAllAdapter(getContext(),mInvestAllBean.getData());
            investAllLv.setAdapter(mInvestAllAdapter);
        }
    }

    @Override
    public int getLayoutid() {
        return R.layout.fragmemt_invest_all;
    }

    @Override
    public String getChildUrl() {
        return AppNetConfig.PRODUCT;
    }

}
