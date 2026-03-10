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
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.getUserBtn("1002") // 触发加载李四的信息
            }, 1000)
        }
    }

}