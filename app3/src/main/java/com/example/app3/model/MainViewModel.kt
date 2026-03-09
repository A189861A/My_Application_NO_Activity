package com.example.app3.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(countReserved: Int) : ViewModel() {
    /*
    * MutableLiveData 是 Android Jetpack 架构组件中 LiveData 的一个可变子类。
    * 它允许观察者订阅数据变化，并在数据发生变化时收到通知。
    * LiveData 是一个可观察的数据持有者类，它能够感知生命周期，并在生命周期处于活跃状态时更新数据。
    *  - getValue()方法用于获取LiveData中包含的数据
    *  - setValue()方法用于设置LiveData中包含的数据(只能在主线程中调用)
    *  - postValue()方法用于在非主线程中给LiveData设置数据
    * */
    val counter = MutableLiveData<Int>()
    /*
    * init:
    *  可以把它看作是类的**“构造函数的辅助部分”**
    *  它在创建对象实例时执行，主要用于编写初始化逻辑
    * */
    init {
        counter.value = countReserved
    }
    fun plusOne() {
        val count = counter.value ?: 0
        counter.value = count + 1
    }
    fun clear() {
        counter.value = 0
    }
}