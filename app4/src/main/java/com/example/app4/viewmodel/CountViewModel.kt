package com.example.app4.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * 计数器 ViewModel
 * 使用 ViewModel 管理计数器状态，确保状态在屏幕旋转等配置更改时保持不变
 */
class CountViewModel : ViewModel() {

    /**
     * 计数器状态
     * 使用 mutableStateOf 使状态可被 Compose 观察和重新渲染
     */
    var count by mutableStateOf(0)
        private set // 限制外部直接修改状态

    /**
     * 增加计数值
     * 使用 private set 限制外部直接修改，只能通过此方法修改
     */
    fun increment() {
        count++
    }

    /**
     * 减少计数值
     */
    fun decrement() {
        count--
    }

    /**
     * 重置计数值
     */
    fun reset() {
        count = 0
    }
}
