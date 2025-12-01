package com.example.my_application_no_activity

import android.app.Activity

object ActivityCollector {
    private val activities = ArrayList<Activity>()
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
        }
    fun finishAll() {
        for (i in activities.size - 1 downTo 0) {
            val activity = activities[i]
//            判断当前 Activity 是否处于 “正在结束（销毁）” 状态的核心方法，主要用于避免在 Activity 销毁后执行无效操作
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear();
//        android.os.Process.killProcess(android.os.Process.myPid())
    }

}