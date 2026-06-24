package com.example.sunnyweather.model

//数据模型，定义天气数据结构
/*
* data class ：是 Kotlin 中专门用于存储数据的类，
*   它会自动为你生成一系列实用的方法，让你能够更简洁、更高效地处理数据。
*   自动生成：equals()、hashCode()、toString()、copy(),解构赋值 等方法。
*   -- 主构造函数必须至少有一个参数
*   -- 仅主构造参数参与自动生成方法，类内额外属性不参与
*
*   data class User(
        val id: Int,
        val name: String,
        var age: Int
    )
    # 结构赋值：
        val u = User(1,"张三",20)
        val (uid, uname, uage) = u
    # 仅主构造参数参与自动生成方法，类内额外属性不参与
    data class A(val a:Int){
        var b:Int = 0 // b 不参与 equals/toString
    }
* */
data class WeatherResponse(
    val result: Result // 主构造函数
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