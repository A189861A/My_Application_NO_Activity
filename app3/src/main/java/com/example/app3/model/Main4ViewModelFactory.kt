package com.example.app3.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
* ViewModelProvider.Factory：
*   - 创建ViewModel实例的工厂
*   - 接口只有一个方法：create
* */
class Main4ViewModelFactory(private val countReserved: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Main4ViewModel(countReserved) as T
    }
}
