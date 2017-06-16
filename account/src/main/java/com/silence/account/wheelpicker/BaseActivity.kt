package com.silence.account.wheelpicker

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.CallSuper
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView

import java.util.ArrayList

import cn.qqtheme.framework.util.LogUtils

/**
 * Activity的基类

 * @author 李玉江[QQ:1023694760]
 * *
 * @since 2014-4-20
 */
abstract open class BaseActivity : FragmentActivity() {
    protected var context: Context? = null
    protected var activity: BaseActivity? = null
    protected var className = javaClass.simpleName
    private val lifeCycleListeners = ArrayList<LifeCycleListener>()


    protected abstract fun setContentViewAfter(contentView: View)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.verbose(className + " onCreate")
        for (listener in lifeCycleListeners) {
            listener.onActivityCreated(this)
        }
        context = applicationContext
        activity = this
        //不显示标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT >= 21) {
            setTheme(android.R.style.Theme_Material_Light_NoActionBar)
        } else if (Build.VERSION.SDK_INT >= 13) {
            setTheme(android.R.style.Theme_Holo_Light_NoActionBar)
        } else {
            setTheme(android.R.style.Theme_Light_NoTitleBar)
        }
        //被系统回收后重启恢复
        if (savedInstanceState != null) {
            LogUtils.verbose("savedInstanceState is not null")
            onStateRestore(savedInstanceState)
        }
        //显示界面布局
        var contentView: View? = getContentView()
        if (contentView == null) {
            val textView = TextView(this)
            textView.setBackgroundColor(Color.RED)
            textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            textView.gravity = Gravity.CENTER
            textView.text = "请先初始化内容视图"
            textView.setTextColor(Color.WHITE)
            contentView = textView
        }
        LogUtils.verbose(className + " setContentView before")
        setContentViewBefore()
        setContentView(contentView)
        if (isTranslucentStatusBar) {
            StatusBarUtil.setTransparent(activity as BaseActivity)
        }
        setContentViewAfter(contentView)
        LogUtils.verbose(className + " setContentView after")
    }

    protected fun onStateRestore(savedInstanceState: Bundle) {  }

    protected fun setContentViewBefore() {
        LogUtils.verbose(className + " setContentView before")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        LogUtils.verbose(className + " onBackPressed")
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        LogUtils.verbose(className + " onSaveInstanceState")
    }

    @CallSuper
    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtils.verbose(className + " onRestoreInstanceState")
    }

    @CallSuper
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtils.verbose(className + " onConfigurationChanged")
    }

    @CallSuper
    public override fun onRestart() {
        super.onRestart()
        LogUtils.verbose(className + " onRestart")
        for (listener in lifeCycleListeners) {
            listener.onActivityRestarted(this)
        }
    }

    @CallSuper
    public override fun onStart() {
        super.onStart()
        LogUtils.verbose(className + " onStart")
        for (listener in lifeCycleListeners) {
            listener.onActivityStarted(this)
        }
        //和removeActivity对应
        AppManager.instance?.addActivity(this)
    }

    @CallSuper
    public override fun onResume() {
        super.onResume()
        LogUtils.verbose(className + " onResume")
        for (listener in lifeCycleListeners) {
            listener.onActivityResumed(this)
        }
    }

    @CallSuper
    public override fun onPause() {
        super.onPause()
        LogUtils.verbose(className + " onPause")
        for (listener in lifeCycleListeners) {
            listener.onActivityPaused(this)
        }
    }

    @CallSuper
    public override fun onStop() {
        super.onStop()
        LogUtils.verbose(className + " onStop")
        for (listener in lifeCycleListeners) {
            listener.onActivityStopped(this)
        }
        // 极端情况下，系统会杀死APP进程，并不执行onDestroy()，
        // 因此需要使用onStop()来释放资源，从而避免内存泄漏。
        AppManager.instance?.removeActivity(this)
    }

    @CallSuper
    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtils.verbose(className + " onSaveInstanceState")
    }

    @CallSuper
    public override fun onDestroy() {
        super.onDestroy()
        LogUtils.verbose(className + " onDestroy")
        for (listener in lifeCycleListeners) {
            listener.onActivityDestroyed(this)
        }
    }

    @CallSuper
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtils.verbose(className + " onActivityResult")
    }

    @CallSuper
    override fun onLowMemory() {
        super.onLowMemory()
        LogUtils.verbose(className + " onLowMemory")
        AppManager.instance?.removeActivity(this)
    }

    protected val isTranslucentStatusBar: Boolean
        get() = true

    protected fun <T> inflateView(@LayoutRes layoutResource: Int): T {
        LogUtils.verbose(className + " inflate view by layout resource")

        return LayoutInflater.from(activity).inflate(layoutResource, null) as T
    }

    protected fun <T> findView(@IdRes id: Int): T {

        return findViewById(id) as T
    }

    fun addLifeCycleListener(listener: LifeCycleListener) {
        if (lifeCycleListeners.contains(listener)) {
            return
        }
        lifeCycleListeners.add(listener)
    }

    fun removeLifeCycleListener(listener: LifeCycleListener) {
        lifeCycleListeners.remove(listener)
    }

    /**
     * Activity生命周期监听
     */
    interface LifeCycleListener {

        fun onActivityCreated(activity: BaseActivity)

        fun onActivityResumed(activity: BaseActivity)

        fun onActivityStarted(activity: BaseActivity)

        fun onActivityRestarted(activity: BaseActivity)

        fun onActivityPaused(activity: BaseActivity)

        fun onActivityStopped(activity: BaseActivity)

        fun onActivityDestroyed(activity: BaseActivity)

    }

    abstract protected fun getContentView(): View
}
