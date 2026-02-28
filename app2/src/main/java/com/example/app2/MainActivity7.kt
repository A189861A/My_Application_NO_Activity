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
        val job = lifecycleScope.launch {
            // 协程默认运行在主线程（Dispatchers.Main）
            println("协程初始线程：${Thread.currentThread().name}")

            // 2. 切换到 IO 线程执行耗时操作（网络/文件读写）,执行完自动切回原线程（主线程）
            /*
            * withContext 是 Kotlin 协程提供的一个挂起函数，用于切换协程的执行上下文（dispatcher）
            * 1.阻塞当前协程：会挂起当前协程直到代码块执行完成
            * 2.切换线程：可以在不同的线程调度器之间切换
            * 3.返回结果：代码块执行结束后，会返回代码块的返回值

            调度器	                作用	                    适用场景
            Dispatchers.Main	    主线程（Android）	        更新 UI、轻量计算
            Dispatchers.IO	        IO 线程池（处理阻塞操作）	网络请求、文件读写、数据库操作
            Dispatchers.Default	    默认线程池（CPU 密集型）	复杂计算、循环、数据处理
            * */

            val result = withContext(Dispatchers.IO) {
                println("耗时操作线程：${Thread.currentThread().name}")
                delay(2000) // 模拟网络请求/文件读取
                "异步请求结果" // 最后一行是返回值
            }

            // 3. 自动切回主线程，更新 UI
            println("更新 UI 线程：${Thread.currentThread().name}" + "--result: $result")
            // textView.text = result

            val data = fetchData() // 同步写法，异步执行
            // 更新 UI
            println("更新 UI 线程：${Thread.currentThread().name}" + "--data: $data")
        }

        // 手动取消协程（比如点击按钮/页面销毁）
        // job.cancel()
        // job.join() // 等待协程取消完成（可选）

        // 协程是非阻塞的，这里会立即执行
        println("onCreate 执行完毕")
    }

    // 定义挂起函数（模拟网络请求）
    /*
    *
    * 1. 挂起函数：只能在协程中调用，不能在普通函数中调用,实现 “可暂停的耗时逻辑”
    * */
    suspend fun fetchData(): String {
        // 必须在协程中调用，否则编译报错
        withContext(Dispatchers.IO) {
            delay(1500)
        }
        return "从服务器获取的数据"
    }
}