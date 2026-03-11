package com.example.app3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.app3.entity.User
import com.example.app3.model.UserViewModel2
import kotlinx.coroutines.launch

class MainActivity7 : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        viewModel = ViewModelProvider(this)[UserViewModel2::class.java];

        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            // 1.插入测试数据
            val testUser = User(null, "张三", 18, "zhangsan@example.com")
            viewModel.insertUser(testUser)
        }
        button.setOnClickListener {
            // 观察用户数据变化（自动更新UI）
            /*
            * lifecycleScope:
            *   用于管理协程的生命周期，确保协程在 Activity/Fragment 销毁时自动取消。
            *   提供的一个协程作用域构建器，它绑定到 LifecycleOwner（如 Activity、Fragment）的生命周期。
            *  - 默认在 Dispatchers.Main（主线程）
            *  - 使用 withContext(Dispatchers.IO) 切换到 IO 线程执行耗时操作
            *  - 执行完后自动返回主线程
            *  非阻塞：launch 是非阻塞的，onCreate 会继续执行后续代码.
            *  lifecycleScope.launch: 启动lifecycleScope协程（默认主线程）
            * */
            lifecycleScope.launch {
                /*
                * Flow: 用于异步流数据，可以处理大量数据而不阻塞线程。
                *   collect:
                *       - 会持续监听，数据库数据变化时自动回调.
                *       - 订阅并处理 Flow 发射的每一个数据，必须在协程中调用.
                * */
                viewModel.allUsers.collect { users ->
                    // 数据变化时更新UI，比如显示到 RecyclerView
                    Log.d("--users--", "用户列表：${users.size}")
                    if (users.isEmpty()) {
                        println("用户列表为空")
                    } else {
                        users.forEach { user ->
                            println("用户：${user.name}，年龄：${user.age}")
                        }
                    }
                }
            }

        }
    }
}