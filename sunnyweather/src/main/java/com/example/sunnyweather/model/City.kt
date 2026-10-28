package com.example.sunnyweather.model

/*
* 城市数据，来源：高德官方行政区划文档 AMap_adcode_citycode.xlsx
* 已提取为 assets/cities.json，只保留地级市（含直辖市），共 338 条。
*
* 字段必须带默认值：Gson 用 Unsafe 创建对象、直接反射赋值，不走主构造函数。
* */
data class City(
    val name: String = "",    // 城市名，如：石家庄市
    val adcode: String = "",  // 城市编码，天气接口的 city 参数
    val province: String = "" // 所属省份，如：河北省
) {
    // 下拉框显示的文字：直辖市/省份名和城市名重复时只显示一次
    val displayName: String
        get() = if (province.contains(name) || name.contains(province)) {
            name
        } else {
            "$province $name"
        }
}
