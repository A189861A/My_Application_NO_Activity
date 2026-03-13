package com.example.sunnyweather.repository

import com.example.sunnyweather.model.WeatherResponse
import com.example.sunnyweather.network.ApiService
import com.google.gson.Gson

/*
* WeatherRepo：是一个仓库类，
*   - 主要负责天气数据的获取和处理，充当了数据层与业务逻辑层之间的桥梁。
* */
class WeatherRepo {
    fun fetchWeather(city: String, callback: (WeatherResponse) -> Unit) {
        ApiService.getWeather(city) {
            val data = Gson().fromJson(it, WeatherResponse::class.java)
            callback(data)
        }
    }
}