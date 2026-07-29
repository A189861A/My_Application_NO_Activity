package com.example.app5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {
    // 私有可变，内部修改
    private val _count = MutableLiveData<Int>(0)

    /*
    * LiveData 提供了两种方式来更新数据
    *   -- setValue(value)：必须在主线程调用
    *   -- postValue(value)：可以在任何线程调用（短时间多次连续 postValue，中间值丢失，仅最后一个生效。）
    * */
    // 对外只读，UI只能观察，不能赋值
    val count: LiveData<Int> = _count

    // 普通更新（主线程调用）
    fun addCount() {
        val current = _count.value ?: 0
        _count.value = current + 1
    }

    // 子线程模拟网络请求，演示 postValue
    fun simulateNetRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val netResult = (_count.value ?: 0) + 1
            // 子线程不能使用 .value，使用 postValue
            _count.postValue(netResult)
        }
    }

    fun reset() {
        _count.value = 0
    }
}