package com.atguigu.p2p.invest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.p2p.R;
import com.atguigu.p2p.home.view.MyProgress;
import com.atguigu.p2p.invest.bean.InvestAllBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/14 0014.
 * 功能:
 */

public class InvestAllAdapter extends BaseAdapter {
    private final Context mContext;
    private List<InvestAllBean.DataBean> datas = new ArrayList<>();

    public InvestAllAdapter(Context mContext, List<InvestAllBean.DataBean> data) {
        this.mContext = mContext;
        if (data != null && data.size() > 0) {
            this.datas.clear();
            this.datas.addAll(data);
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.adapter_invest_all, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.pName.setText(datas.get(position).getName());
        viewHolder.pYearlv.setText(datas.get(position).getYearRate());
        viewHolder.pMinnum.setText(datas.get(position).getMinTouMoney());
        return convertView;
    }


    static class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
