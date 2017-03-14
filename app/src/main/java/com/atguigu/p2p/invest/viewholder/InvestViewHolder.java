package com.atguigu.p2p.invest.viewholder;

import android.view.View;
import android.widget.TextView;

import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseHolder;
import com.atguigu.p2p.home.view.MyProgress;
import com.atguigu.p2p.invest.bean.InvestAllBean;
import com.atguigu.p2p.utils.UiUtils;

import butterknife.InjectView;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/15 0015.
 * 功能:
 */

public class InvestViewHolder extends BaseHolder<InvestAllBean.DataBean> {
    @InjectView(R.id.p_name)
    TextView pName;
    @InjectView(R.id.p_money)
    TextView pMoney;
    @InjectView(R.id.p_yearlv)
    TextView pYearlv;
    @InjectView(R.id.p_suodingdays)
    TextView pSuodingdays;
    @InjectView(R.id.p_minzouzi)
    TextView pMinzouzi;
    @InjectView(R.id.p_minnum)
    TextView pMinnum;
    @InjectView(R.id.p_progresss)
    MyProgress pProgresss;


    /**
     * 设置控件数据,
     */
    @Override
    protected void setChildData() {
        InvestAllBean.DataBean dataBean = getT();
        pName.setText(dataBean.getName());
        pYearlv.setText(dataBean.getYearRate());
        pMoney.setText(dataBean.getMoney());
        pSuodingdays.setText(dataBean.getSuodingDays());
        pMinzouzi.setText(dataBean.getMinTouMoney());
        pMinnum.setText(dataBean.getMinTouMoney());
        int integer = Integer.parseInt(dataBean.getProgress());
        pProgresss.setProgress(integer);
    }

    /**
     * 初始化view布局
     * UiUtils.getView(R.layout.adapter_invest_all);
     * @return View
     */
    @Override
    protected View initView() {
        //return View.inflate(mContext,R.layout.adapter_invest_all,null);
        return UiUtils.getView(R.layout.adapter_invest_all);
    }
}
