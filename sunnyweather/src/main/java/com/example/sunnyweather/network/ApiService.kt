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

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        Thread {
            val response = client.newCall(request).execute()
            val result = response.body?.string() ?: ""
            callback(result)
        }.start()
    }
}