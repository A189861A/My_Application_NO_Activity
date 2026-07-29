package com.example.app5

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        * CoroutineScope。即协程作用域，GlobalScope 是 CoroutineScope 的一个实现类，
        * 用于指定协程的作用范围，可用于管理多个协程的生命周期，所有协程都需要通过 CoroutineScope 来启动。
        *
        * CoroutineContext：即协程上下文，用于指定协程的运行载体，即用于指定协程要运行在哪类线程上。
        * Dispatchers.IO 就是 CoroutineContext 这个抽象概念的一种实现。
        *
        * CoroutineBuilder。即协程构建器，协程在 CoroutineScope 的上下文中通过 launch、async 等
        * 协程构建器来进行声明并启动。launch、async 均被声明为 CoroutineScope 的扩展方法
        * */
        GlobalScope.launch(Dispatchers.IO) {
            /*
            *延迟2s后执行
            * suspend function。即挂起函数，delay() 就是协程库提供的一个用于实现非阻塞式延时的挂起函数。
            * 挂起函数不会阻塞其所在线程，而是会将协程挂起，在特定的时候才再恢复执行。
            * */
            delay(2000)
            Log.d("--MainActivity--", "3")
        }
        Log.d("--MainActivity--", "1")
        // 主动休眠2s，防止JVM过快退出
        // 使用Thread.sleep()的话，线程就只能干等着而不能去执行其它任务
        Thread.sleep(2000)
        Log.d("--MainActivity--", "2")
    }
}