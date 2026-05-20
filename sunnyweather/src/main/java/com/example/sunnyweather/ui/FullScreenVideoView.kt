package com.example.sunnyweather.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

/*
* @JvmOverloads: 指示 Kotlin 编译器为带有默认参数值的函数或构造函数生成多个重载版本。
* Kotlin 原生支持默认参数，但 Java 并没有这个特性
* 当你给构造函数加上 @JvmOverloads 时，Kotlin 编译器会按照从右向左依次省略默认参数的规则，自动生成多个构造函数重载。
* */
class FullScreenVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : VideoView(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }
}
