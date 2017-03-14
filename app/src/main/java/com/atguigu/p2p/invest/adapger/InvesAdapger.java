package com.atguigu.p2p.invest.adapger;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.p2p.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/14 0014.
 * 功能:
 */

/**
 * FragmentPagerAdapter
 * 会在内存进行保存,但是不适合fragment较多的情况下
 * FragmentStatePagerAdapter
 * 会在内存里定期回收掉,适合较多的fragment
 */
public class InvesAdapger extends FragmentPagerAdapter {

    private List<BaseFragment> mDatas = new ArrayList<>();

    public InvesAdapger(FragmentManager fm, List<BaseFragment> list) {
        super(fm);

        if (list != null && list.size() > 0) {
            this.mDatas = list;
        }
    }

    private String[] titles = {"全部理财", "热门", "推荐"};

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas == null ? null : mDatas.size();
    }
}
