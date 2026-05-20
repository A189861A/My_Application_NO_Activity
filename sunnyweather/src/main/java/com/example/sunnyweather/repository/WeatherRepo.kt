package com.example.sunnyweather.repository

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
                * Gson：一个 Java 语言下的 Json 解析库，用于将 Json 字符串转换成 Java 对象，或者将 Java 对象转换成 Json 字符串。
                * */
                val data = Gson().fromJson(it, WeatherResponse::class.java)
                callback(data)
            } catch (e: Exception) {
                callback(null)
            }
        }
    }
}