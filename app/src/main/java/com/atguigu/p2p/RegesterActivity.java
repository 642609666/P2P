package com.atguigu.p2p;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.p2p.base.BaseAvtivity;
import com.atguigu.p2p.utils.AppNetConfig;
import com.atguigu.p2p.utils.LoadNet;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class RegesterActivity extends BaseAvtivity {

    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.et_register_number)
    EditText etRegisterNumber;
    @InjectView(R.id.et_register_name)
    EditText etRegisterName;
    @InjectView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @InjectView(R.id.et_register_pwdagain)
    EditText etRegisterPwdagain;
    @InjectView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regester;
    }

    @Override
    protected void initTitle() {
        baseSetting.setVisibility(View.INVISIBLE);
        baseTitle.setText("注册");
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
    }

    @OnClick({R.id.base_back, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_back:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_register:
                String name = etRegisterName.getText().toString().trim();
                final String phone = etRegisterNumber.getText().toString().trim();
                final String password = etRegisterPwd.getText().toString().trim();
                String pwdAgain = etRegisterPwdagain.getText().toString().trim();

                //对注册的选项判断不能为空
                //判断密码是否一致,判断密码的长度是否符合要求,判断是否注册过
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(password) || TextUtils.isEmpty(pwdAgain)) {
                    Toast.makeText(RegesterActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //请求服务器
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("password", password);
                map.put("phone", phone);

                LoadNet.getDataNet(AppNetConfig.REGISTER, map, new LoadNet.OnGetNet() {
                    @Override
                    public void onSuccess(String content) {
                        Log.e("TAG", "注册界面成功" + content);
                        //数据解析
                        JSONObject jsonObject = JSON.parseObject(content);

                        Boolean isExist = jsonObject.getBoolean("isExist");

                        if (isExist) {
                            Toast.makeText(RegesterActivity.this, "账号已经注册过", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegesterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                            //传递数据给登录界面
                            Intent intent = new Intent(RegesterActivity.this, LoginActivity.class);
                            intent.putExtra("phone_password", phone + "," + password);
                            startActivity(intent);
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(String content) {
                        Log.e("TAG", "注册界面失败" + content);
                    }
                });

                break;
        }
    }
}
