package com.example.sunnyweather.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

/*
* 在 Kotlin 中，object 关键字
* - 实现单例模式或者创建匿名对象（类似于 Java 中的匿名内部类）.
* - 直接定义一个对象并继承某个类或实现某个接口.
* - 创建一个对象，并定义对象内的属性和方法.
* */
object ApiService {
    private const val TOKEN = "teHHeQ4HjH4SuMQ2"

    fun getWeather(city: String, callback: (String) -> Unit) {
        Log.d("--city--", "city: $city")
        val url = "https://api.caiyunapp.com/v2.6/$TOKEN/$city/realtime"
//        val url = "https://api.caiyunapp.com/v2.6/teHHeQ4HjH4SuMQ2/101.6656,39.2072/realtime"
        /*
        *
        *
        * */
        val client = OkHttpClient()
        // 构建一个包含了目标 URL 的 HTTP 请求对象
        val request = Request.Builder().url(url).build()
        /*
        * 开启子线程
        * Thread：子线程 是代表操作系统线程的类。它是程序执行的最小单元，拥有独立的执行路径和调用栈。
        * */
        Thread {
            try {
                // OkHttp 同步请求必须放 Thread 子线程
                val response = client.newCall(request).execute()
                val result = response.body?.string() ?: ""
                callback(result)
            } catch (e: Exception) {
                Log.e("ApiService", "Network error: ${e.message}", e)
                callback("")
            }
            // 切主线程刷新页面
            // runOnUiThread { }
        }.start()
    }
}