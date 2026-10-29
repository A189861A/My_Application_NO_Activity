package com.example.sunnyweather.repository

import android.util.Log
import com.example.sunnyweather.model.WeatherResponse
import com.example.sunnyweather.network.ApiService
import com.google.gson.Gson

/*
* WeatherRepo：是一个仓库类，
*   - 主要负责天气数据的获取和处理，充当了数据层与业务逻辑层之间的桥梁。
* */
class WeatherRepo {
    fun fetchWeather(city: String, callback: (WeatherResponse?) -> Unit) {
        /*
        * it: 特殊的标识符名称,它的出现通常只有一个场景：当 Lambda 表达式（匿名函数）只有 一个参数 时。
        * 表示传入的参数，即城市名称。
        * */
        ApiService.getWeather(city) {
            if (it.isEmpty()) {
                callback(null)
                return@getWeather // 只退出当前的 lambda，继续执行后面的代码，不结束外层函数
            }
            try {
                /*
                * Gson：一个 Java 语言下的 Json 解析库，用于将 Json 字符串转换成 Java 对象，
                * 或者将 Java 对象转换成 Json 字符串。
                * fromJson： 将 Json 字符串转换成 Java 对象。
                * */
                val data = Gson().fromJson(it, WeatherResponse::class.java)
                /*
                * Gson 解析成功不代表接口调用成功：
                * - 高德失败时返回 {"status":"0","info":"INVALID_PARAMS",...}，是合法 JSON，
                *   解析出来 status 为 "0"、lives 为 null。
                * - 所以这里校验 status 和 lives，不合法就交给上层按失败处理。
                * */
                if (data != null && data.status == "1" && !data.lives.isNullOrEmpty()) {
                    callback(data)
                } else {
                    Log.e("WeatherRepo", "invalid response: $it")
                    callback(null)
                }
            } catch (e: Exception) {
                callback(null)
            }
        }
    }
}