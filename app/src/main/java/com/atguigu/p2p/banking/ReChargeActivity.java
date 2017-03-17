package com.atguigu.p2p.banking;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.p2p.R;
import com.atguigu.p2p.base.BaseAvtivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class ReChargeActivity extends BaseAvtivity {
    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.chongzhi_text)
    TextView chongzhiText;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.et_chongzhi)
    EditText etChongzhi;
    @InjectView(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @InjectView(R.id.yue_tv)
    TextView yueTv;
    @InjectView(R.id.btn_chongzhi)
    Button btnChongzhi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_re_charge;
    }

    @Override
    protected void initTitle() {
        baseSetting.setVisibility(View.INVISIBLE);
        baseTitle.setText("充值");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        //添加文本更改监听器
        etChongzhi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("TAG", "ReChargeActivity beforeTextChanged()");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("TAG", "ReChargeActivity onTextChanged()");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("TAG", "ReChargeActivity afterTextChanged()");

                //获得金钱的数量
                String money = s.toString().trim();

                if (TextUtils.isEmpty(money)) {
                    //设置点击事件不可点击

                    btnChongzhi.setClickable(false);
                    //设置充值按钮的背景颜色
                    btnChongzhi.setBackgroundResource(R.drawable.btn_02);
                } else {

                    btnChongzhi.setClickable(true);
                    btnChongzhi.setBackgroundResource(R.drawable.btn_01);

                }
            }
        });
    }

    @OnClick({R.id.base_back, R.id.btn_chongzhi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_back:
                finish();
                break;
            case R.id.btn_chongzhi:
                //调用支付宝
                Toast.makeText(ReChargeActivity.this, "支付宝", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
