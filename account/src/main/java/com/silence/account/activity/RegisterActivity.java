package com.silence.account.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.silence.account.R;
import com.silence.account.model.User;
import com.silence.account.utils.MD5Encrypt;
import com.silence.account.utils.StringUtils;
import com.silence.account.utils.T;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.et_reg_username)
    EditText mRegUsername;
    @Bind(R.id.et_reg_email)
    EditText mRegEmail;
    @Bind(R.id.et_reg_pass)
    EditText mRegPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.text_register);
        showBackwardView(true);
    }

    @Override
    public Activity getSubActivity() {
        return this;
    }

    @OnClick(R.id.btn_register)
    //注册按钮，执行的操作
    public void registerClick() {
        //获取输入框中用户输入的信息
        final String name = mRegUsername.getText().toString().trim();
        final String pass = mRegPass.getText().toString().trim();
        final String email = mRegEmail.getText().toString().trim();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(email)) {
            //检测邮箱号是否符合格式
            if (StringUtils.checkEmail(email)) {
                //开启异步线程，避免阻塞UI主线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BmobQuery<User> query = new BmobQuery<>();
                        query.addWhereEqualTo("email", email);
                        query.findObjects(RegisterActivity.this, new FindListener<User>() {
                            @Override
                            public void onSuccess(List<User> list) {
                                if (list.size() > 0) {
                                    T.showShort(getApplicationContext(), getString(R.string.email_exist));
                                } else {
                                    User user = new User();
                                    user.setEmail(email);
                                    //密码要经过md5加密
                                    user.setPassword(MD5Encrypt.MD5Encode(pass));
                                    user.setUsername(name);
                                    user.signUp(RegisterActivity.this, new SaveListener() {
                                        @Override
                                        public void onSuccess() {
                                            T.showShort(getApplicationContext(), getString(R.string.regiter_succeed));
                                            RegisterActivity.this.finish();
                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            T.showShort(getApplicationContext(), getString(R.string.username_exist));
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onError(int i, String s) {
                                T.showShort(getApplicationContext(), getString(R.string.verify_fail));
                            }
                        });
                    }
                }).start();

            } else {
                T.showShort(this, getString(R.string.input_right_email));
            }
        } else {
            T.showShort(this, getString(R.string.input_full_info));
        }
    }
}
