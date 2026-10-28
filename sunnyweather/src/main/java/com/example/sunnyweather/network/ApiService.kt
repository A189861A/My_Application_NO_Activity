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
    private const val KEY = "34731b60e3fddfe99f78c8aa4c6b5fbb"

    /*
    * 高德天气查询接口：
    *   - city：城市 adcode（行政区划编码），例如 110101 = 北京市东城区
    *   - key：高德开放平台申请的 Web 服务 Key
    *   - extensions：base 返回实况天气（lives），all 返回预报天气（forecasts）
    * */
    fun getWeather(city: String, callback: (String) -> Unit) {
        Log.d("--city--", "city: $city")
        val url =
            "https://restapi.amap.com/v3/weather/weatherInfo?city=$city&key=$KEY&extensions=base"
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