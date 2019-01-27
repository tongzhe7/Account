package com.silence.account.activity

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import com.silence.account.R

import butterknife.ButterKnife

import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : FragmentActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)
        btn_top_forward!!.setOnClickListener(this)
        btn_top_backward!!.setOnClickListener(this)
    }

    protected fun showBackwardView(show: Boolean, resId: Int) {
        if (show) {
            btn_top_backward!!.setImageResource(resId)
            btn_top_backward!!.visibility = View.VISIBLE
            top_left_divider!!.visibility = View.VISIBLE
        } else {
            if (btn_top_backward!!.visibility == View.VISIBLE) {
                btn_top_backward!!.visibility = View.GONE
                top_left_divider!!.visibility = View.INVISIBLE
            }
        }
    }

    protected fun showBackwardView(show: Boolean) {
        if (show) {
            if (btn_top_backward!!.visibility == View.GONE) {
                btn_top_backward!!.visibility = View.VISIBLE
                top_left_divider!!.visibility = View.VISIBLE
            }
        } else {
            if (btn_top_backward!!.visibility == View.VISIBLE) {
                btn_top_backward!!.visibility = View.GONE
                top_left_divider!!.visibility = View.INVISIBLE
            }
        }
    }

    protected fun showForwardView(show: Boolean, resId: Int) {
        if (show) {
            btn_top_forward!!.setImageResource(resId)
            btn_top_forward!!.visibility = View.VISIBLE
        } else {
            if (btn_top_forward!!.visibility == View.VISIBLE) {
                btn_top_forward!!.visibility = View.INVISIBLE
            }
        }
    }

    protected fun showForwardView(show: Boolean) {
        if (show) {
            if (btn_top_forward!!.visibility == View.INVISIBLE) {
                btn_top_forward!!.visibility = View.VISIBLE
            }
        } else {
            if (btn_top_forward!!.visibility == View.VISIBLE) {
                btn_top_forward!!.visibility = View.INVISIBLE
            }
        }
    }

    protected open fun onBackward() {        finish()    }

    protected open fun onForward() {    }

    override fun setTitle(titleResId: Int) {
        top_title!!.setText(titleResId)
    }

    override fun setTitle(title: CharSequence) {
        top_title!!.text = title
    }

    override fun setContentView(layoutResId: Int) {
        layout_content!!.removeAllViews()
        View.inflate(this, layoutResId, layout_content)
        onContentChanged()
        ButterKnife.bind(subActivity)
    }

    override fun setContentView(view: View) {
        layout_content!!.removeAllViews()
        layout_content!!.addView(view)
        onContentChanged()
        ButterKnife.bind(subActivity)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        layout_content!!.removeAllViews()
        layout_content!!.addView(view, params)
        onContentChanged()
        ButterKnife.bind(subActivity)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_top_backward -> onBackward()
            R.id.btn_top_forward -> onForward()
        }
    }

    abstract val subActivity: Activity

    //abstract fun getSubActivity(): Activity

}
