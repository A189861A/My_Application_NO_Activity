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
/*
* 高德天气接口的返回结构：
*   成功：{"status":"1","count":"1","info":"OK","infocode":"10000","lives":[{...}]}
*   失败：{"status":"0","count":"0","info":"INVALID_PARAMS","infocode":"20003"}
* 失败时没有 lives 字段。Gson 不校验 Kotlin 的非空约束，会把缺失字段直接赋成 null，
* 所以可能缺失的字段都声明为可空类型，让编译器强制使用方处理 null。
* */
data class WeatherResponse(
    val status: String? = null,  // "1" 成功，"0" 失败
    val count: String? = null,
    val info: String? = null,    // 错误描述
    val infocode: String? = null,
    val lives: List<Live>? = null // 实况天气列表，失败时为 null
)

// 实况天气。高德接口返回的数值都是字符串，所以这里统一用 String 接收
data class Live(
    val province: String? = null,    // 省份
    val city: String? = null,        // 城市
    val adcode: String? = null,      // 城市编码
    val weather: String? = null,     // 天气：晴/阴/雨
    val temperature: String? = null, // 温度
    val winddirection: String? = null,
    val windpower: String? = null,
    val humidity: String? = null,    // 湿度
    val reporttime: String? = null   // 发布时间
)