package com.silence.account.wheelpicker

import android.app.Activity
import android.app.Application
import android.app.Service
import android.os.Bundle

import java.util.LinkedList

import cn.qqtheme.framework.util.LogUtils
import kotlin.properties.Delegates

/**
 * Activity及Service管理，以便实现退出功能

 * @author 李玉江[QQ:1032694760]
 * *
 * @since 2015/12/17
 */
class AppManager/* :Application()*/ {
    //保存所有Activity
    /**
     * 所有的Activity

     * @return the activities
     */
    val activities = LinkedList<Activity>()
    //保存所有Service
    /**
     * 所有的Service

     * @return the services
     */
    val services = LinkedList<Service>()

    /**
     * 注册Activity以便集中“finish()”

     * @param activity the activity
     * *
     * @see Activity.onCreate
     * @see Activity.onStart
     */
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    /**
     * 移除Activity.

     * @param activity the activity
     * *
     * @see Activity.onDestroy
     * @see Activity.onStop
     */
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    /**
     * 最后加入的Activity

     * @return the activity
     */
    val lastActivity: Activity
        get() {
            val activity = activities.last
            LogUtils.debug(this, "last activity is " + activity.javaClass.name)
            return activity
        }

    /**
     * 注册Service以便集中“stopSelf()”

     * @param service the service
     */
    fun addService(service: Service) {
        services.add(service)
    }

    /**
     * Remove service.

     * @param service the service
     */
    fun removeService(service: Service) {
        services.remove(service)
    }

    /**
     * 退出软件
     */
    fun exitApp() {
        clearActivitiesAndServices()
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)//normal exit application
    }

    /**
     * 当内存不足时，需要清除已打开的Activity及Service

     * @see android.app.Application.onLowMemory
     */
    fun clearActivitiesAndServices() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        for (service in services) {
            service.stopSelf()
        }
    }

    companion object {
        var instance: AppManager ? = null
    }
    fun getInstance(): AppManager {
        if (instance == null) {
            instance = AppManager()
        }
        return instance as AppManager
    }
//    override fun onCreate(){
//        super.onCreate()
//        instance = this
//
//    }

}
