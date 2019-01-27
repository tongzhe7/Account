package com.silence.account.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

import com.silence.account.R
import com.silence.account.model.User
import com.silence.account.utils.Constant
import com.silence.account.utils.StringUtils
import com.silence.account.utils.T
import com.silence.account.view.NormalEditText

//import butterknife.Bind;
//import butterknife.OnClick;
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.ResetPasswordByEmailListener

import kotlinx.android.synthetic.main.activity_forget_pass.*

class ForgetPassActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pass)
        setTitle(getString(R.string.find_password))
        showBackwardView(true)

        btn_pass_next.setOnClickListener { onClick() }
    }

    override val subActivity: Activity
        get() = this


    fun onClick() {
        val email = forget_email.text.toString().trim()
        if (!TextUtils.isEmpty(email)) {
            if (StringUtils.checkEmail(email)) {
                val query = BmobQuery<User>()
                query.addWhereEqualTo("email", email)
                query.findObjects(this, object : FindListener<User>() {
                    override fun onSuccess(list: List<User>) {
                        if (list.size > 0) {
                            BmobUser.resetPasswordByEmail(this@ForgetPassActivity, email, object : ResetPasswordByEmailListener() {
                                override fun onSuccess() {
                                    val intent = Intent(this@ForgetPassActivity, ForgetNextActivity::class.java)
                                    intent.putExtra(Constant.FORGET_PASS, email)
                                    startActivity(intent)
                                }

                                override fun onFailure(i: Int, s: String) {
                                    T.showShort(applicationContext, getString(R.string.reset_pass_fail))
                                }
                            })
                        } else {
                            T.showShort(applicationContext, getString(R.string.unregister_email))
                        }
                    }

                    override fun onError(i: Int, s: String) {
                        T.showShort(applicationContext, getString(R.string.check_net_connect))
                    }
                })
            } else {
                T.showShort(this, getString(R.string.input_right_email))
            }
        } else {
            T.showShort(this, getString(R.string.input_email))
        }
    }

}
