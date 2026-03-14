package com.example.sunnyweather.model

//数据模型，定义天气数据结构
/*
* data class ：是 Kotlin 中专门用于存储数据的类，
*   它会自动为你生成一系列实用的方法，让你能够更简洁、更高效地处理数据。
* 自动生成：equals()、hashCode()、toString()、copy() 等方法。
* */
data class WeatherResponse(
    val result: Result
)

data class Result(
    val realtime: Realtime
)

data class Realtime(
    val temperature: Float, // 温度
    val skycon: String,     // 天气：晴/雨/云
    val humidity: Float,    // 湿度
    val wind: Wind
)

data class Wind(
    val speed: Float
)