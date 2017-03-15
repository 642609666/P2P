package com.atguigu.p2p.invest.adapter;

import android.content.Context;

import com.atguigu.p2p.base.BaseHolder;
import com.atguigu.p2p.base.BaseListAdapter;
import com.atguigu.p2p.invest.viewholder.InvestViewHolder;

import java.util.List;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/14 0014.
 * 功能:
 */

public class InvestAllAdapter extends BaseListAdapter {

    public InvestAllAdapter(List list) {
        super(list);
    }


    /**
     * new ViewHolder对象
     *
     * @return ViewHolder对象
     */
    @Override
    public BaseHolder getHolder(Context context) {
        return new InvestViewHolder(context);
    }
}
