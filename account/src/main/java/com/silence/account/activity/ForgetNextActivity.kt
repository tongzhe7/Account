package com.silence.account.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.TextView

import com.silence.account.R
import com.silence.account.utils.Constant

//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;

import kotlinx.android.synthetic.main.activity_forget_next.*

class ForgetNextActivity : BaseActivity() {

    //    @Bind(R.id.label_send_email)
    //    TextView label_send_email;
    //    @Bind(R.id.label_hint_email)
    //    TextView label_hint_email;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_next)
       // ButterKnife.bind(this)
        showBackwardView(true)
        val email = intent.getStringExtra(Constant.FORGET_PASS)
        val validateLabel = "<p>验证邮件已发送至<font color=\"#3f8ddb\">$email</font>，请于1小时内登陆您的邮箱并处理。</p>"
        val hintLabel = "<p>没有收到验证邮件？<br/>&bull; 有可能被误判为垃圾邮件<br/>&bull; 若超过20分钟仍无法接收邮件，请重新提交申请。</p>"
        //label_send_email.setText(Html.fromHtml(validateLabel))
       // label_hint_email.setText(Html.fromHtml(hintLabel))
        label_send_email.text = (Html.fromHtml(validateLabel))
        label_hint_email.text = (Html.fromHtml(hintLabel))

        btn_validate.setOnClickListener { nextClick() }
    }

    override val subActivity: Activity
        get() = this

    //@OnClick(R.id.btn_validate)
    fun nextClick() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
