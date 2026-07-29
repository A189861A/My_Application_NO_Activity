package com.example.app5

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CustomCoroutineScope : AppCompatActivity() {
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        * CoroutineScope 的实例可以通过 CoroutineScope() 或 MainScope() 的工厂函数来构建。
        * 前者创建通用作用域，后者创建 UI 应用程序的作用域并使用 Dispatchers.Main 作为默认的调度器
        * */
        mainScope.launch {
            repeat(5) {
                println("CoroutineScope $it")
                delay(1000L * it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}