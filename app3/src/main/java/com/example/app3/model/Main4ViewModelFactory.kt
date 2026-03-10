package com.example.app3.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
* ViewModelProvider.Factory：
*   - 创建ViewModel实例的工厂
*   - 接口只有一个方法：create
* */
class Main4ViewModelFactory(private val countReserved: Int) : ViewModelProvider.Factory {
    // @Suppress("UNCHECKED_CAST") 是 Kotlin 中的一个注解，告诉编译器忽略"不安全类型转换"警告 。
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 类型转换为请求的ViewModel类型
        // as T： 这种强制转换在 编译时无法检查安全性 ，所以会产生 "unchecked cast" 警告
        return Main4ViewModel(countReserved) as T 
    }
}
