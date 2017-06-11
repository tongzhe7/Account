package com.silence.account.activity

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

import com.silence.account.R
import com.silence.account.utils.AppUtils

//import butterknife.Bind
import kotlinx.android.synthetic.main.activity_about.*
class AboutActivity : BaseActivity() {

   // @Bind(R.id.tv_app_version)
   // internal var textVersion: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        title = getString(R.string.about_us)
        showBackwardView(true)
        tv_app_version!!.text = AppUtils.getAppVersionName(this)
    }

//    override fun getSubActivity(): Activity {
//        return this
//    }

    override val subActivity: Activity
        get() = this

}
