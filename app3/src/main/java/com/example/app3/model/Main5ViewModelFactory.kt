package com.example.app3.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
* ViewModelProvider.Factory：Android 中的一个接口，这里表示 Main5ViewModelFactory 实现了该接口
*   - 创建ViewModel实例的工厂
*   - 接口只有一个方法：create
* */
class Main5ViewModelFactory(private val countReserved: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        /*
        * 检查请求的ViewModel类是否是MainViewModel的子类
        * */
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(countReserved) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
