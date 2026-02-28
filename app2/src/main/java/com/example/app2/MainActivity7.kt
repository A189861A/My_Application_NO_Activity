package com.example.app2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity7 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main7)

        println("主线程：${Thread.currentThread().name}")

        // 1. 绑定 Activity 生命周期的协程（lifecycleScope 是 Android 封装的 Scope）
        /*
        * lifecycleScope：Activity/Fragment 内置的协程作用域，页面销毁时自动取消协程，避免内存泄漏；
        * 协程作用域会自动跟随 Activity/Fragment 的生命周期，当 Activity/Fragment 销毁时，协程也会自动取消。
        * launch 是「非阻塞式」启动协程
        * */
        lifecycleScope.launch {
            // 协程默认运行在主线程（Dispatchers.Main）
            println("协程初始线程：${Thread.currentThread().name}")

            // 2. 切换到 IO 线程执行耗时操作（网络/文件读写）,执行完自动切回原线程（主线程）
            val result = withContext(Dispatchers.IO) {
                println("耗时操作线程：${Thread.currentThread().name}")
                delay(2000) // 模拟网络请求/文件读取
                "异步请求结果" // 最后一行是返回值
            }

            // 3. 自动切回主线程，更新 UI
            println("更新 UI 线程：${Thread.currentThread().name}")
            // textView.text = result
        }

        // 协程是非阻塞的，这里会立即执行
        println("onCreate 执行完毕")
    }
}