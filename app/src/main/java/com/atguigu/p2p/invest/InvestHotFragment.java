package com.atguigu.p2p.invest;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Random;

import butterknife.InjectView;

/**
 * Created by ${
 * 李岩
 * QQ/微信: 642609666} on 3/14 0014.
 * 功能:
 */

public class InvestHotFragment extends BaseFragment {
    @InjectView(R.id.ivest_hot_fl)
    TagFlowLayout ivestHotFl;

    private Random mRandom = new Random();
    private String[] datas = new String[]{"新手福利计划", "财神道90天计划", "硅谷钱包计划", "30天理财计划(加息2%)", "180天理财计划(加息5%)", "月月升理财计划(加息10%)",
            "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "铁路局回款计划", "屌丝迎娶白富美计划"
    };

    @Override
    protected void initData(String json) {

        //设置适配器
        ivestHotFl.setAdapter(new TagAdapter<String>(datas) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {

                final TextView textView = new TextView(getActivity());

                textView.setText(o);

                //给字体设置背景颜色
                textView.setBackgroundResource(R.drawable.hot_shape);
                //获取shape资源管理
                GradientDrawable background = (GradientDrawable) textView.getBackground();
                //给字体设置随机颜色
                int red = mRandom.nextInt(255);
                int blue = mRandom.nextInt(255);
                int greed = mRandom.nextInt(255);
                if (red >= 220 && blue >= 220 && greed >= 220) {
                    red = 155;
                    blue = 155;
                    greed = 155;
                }
                // textView.setTextColor(Color.rgb(red, blue, greed));
                //给字体背景shape设置背景随机颜色
                background.setColor(Color.rgb(red, blue, greed));
                //设置点击事件
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "" + textView.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                return textView;
            }
        });
    }

    @Override
    public int getLayoutid() {
        return R.layout.fragmemt_invest_hot;
    }

    @Override
    public String getChildUrl() {
        return null;
    }
}
