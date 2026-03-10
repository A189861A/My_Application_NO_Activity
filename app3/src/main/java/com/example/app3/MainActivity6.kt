package com.example.app3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.app3.model.User

class MainActivity6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        var getUserBtn = findViewById<android.widget.Button>(R.id.getUserBtn)
        var getAllUsersBtn = findViewById<android.widget.Button>(R.id.getAllUsersBtn)
        var tv = findViewById<android.widget.TextView>(R.id.tv);

        val viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // 观察用户数据变化
        viewModel.userLiveData.observe(this) { user ->
            // 更新UI：显示用户信息
            Log.d("UserInfo", "姓名：${user.name}，年龄：${user.age}")
            tv.text = "姓名：${user.name}，年龄：${user.age}"
        }


        getAllUsersBtn.setOnClickListener {
            // 获取所有用户信息
            viewModel.getAllUsersId().observe( this) { users ->
                // 更新UI：显示所有用户信息
                Log.d("AllUsers", "所有用户ID：$users")
                tv.text = "所有用户ID：$users"
            }
        }

        getUserBtn.setOnClickListener {
            // 模拟用户ID变化（比如点击按钮切换）
            viewModel.getUserBtn("1001") // 触发加载张三的信息
            // 延迟后切换ID
            /*
            * Handler：主要用于线程间通信（尤其是子线程与 UI 线程的交互），
            *   本质是通过 “消息队列（MessageQueue）+ 循环器（Looper）” 实现任务的异步调度
            * Handler 构造函数：
            *   - 传入 Looper 后，Handler 就知道应该把消息发送到哪个线程的消息队列
            *   - postDelayed方法：延迟指定时间后 执行指定的任务.
            *   - post方法：立即执行任务.
            *   - sendEmptyMessageDelayed：延迟发送空消息.
            *   - removeCallbacks：移除未执行的任务.
            * Looper：
            *   - 消息循环器，用于管理消息队列和处理消息
            * Looper.getMainLooper()：
            *   - 获取主线程（UI 线程）的 Looper 对象.
            * */
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.getUserBtn("1002") // 触发加载李四的信息
            }, 1000)
        }
    }

}


/*
* Runnable 核心定义：
*   - 函数式接口（仅含一个抽象方法run）
*   - 是 Android 中处理异步任务、线程切换的核心接口.
*   - 本身不启动线程，需配合 Thread、Handler 等使用，常用于 UI 线程与子线程的交互.

    val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            // 切换到下一张图片
            viewPager.currentItem += 1
            handler.postDelayed(this, 3000) // 每 3 秒切换一次
        }
    }
    // Android 规定只能在 UI 线程更新控件，
    // 子线程需通过 Handler + Runnable 切换到 UI 线程更新 UI.
    handler.postDelayed(runnable, 3000)

*
* */

























