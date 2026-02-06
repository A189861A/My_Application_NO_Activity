package com.example.app2

import android.os.Bundle
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import java.util.logging.Handler
import android.os.Handler
import kotlin.concurrent.thread


class MainActivity2 : AppCompatActivity() {
    lateinit var textView: TextView
    val updateText = 1
    /*
    * object: 是 Kotlin 中创建匿名类的方式，必须重写 handleMessage 方法
    * object : Handler()  表示创建一个继承自 Handler 的匿名子类实例
    * Looper.getMainLooper() 获取主线程的 Looper 对象
    * Looper：Looper 是一个消息循环器，它负责管理一个消息队列，并提供了一系列的方法来处理消息。
    * Handler：
    *       是 Android 中用于线程间通信的核心类.
    *       现了 MessageQueue 和 Looper 的消息处理机制
    *       可以用来在子线程中发送消息，然后在主线程中处理这些消息。
    *       Handler 的作用是将消息发送到消息队列中，然后由消息队列中的 Looper 负责取出消息并执行相应的处理函数。
    * */
    val handler = object : Handler(Looper.getMainLooper()) { // 与主线程绑定
        override fun handleMessage(msg: Message) {
            // 在主线程中更新 UI
            when (msg.what) {
                updateText -> textView.text = "Nice to meet you"
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        textView = findViewById<TextView>(R.id.textView)

        val changeTextBtn = findViewById<Button>(R.id.changeTextBtn)
        changeTextBtn.setOnClickListener {
            /*
            * 1. 开启了一个子线程
            * 2. Android确实是不允许在子线程中进行UI操作的
            * 3. 通过 异步消息处理机制 更新UI
            * */
            thread {
                // 发送消息到主线程
                val message = Message.obtain().apply {
                    what = updateText
                }
                // 子线程通过 handler.sendMessage(message) 向主线程发送消息
                handler.sendMessage(message) // 发送消息
            }
        }

    }
}