package com.example.my_application_no_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity14 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main14)

        /*
        * getPreferences() 是 Activity 类提供的一个简化版 SharedPreferences 获取方法，
        * 本质上还是获取 SharedPreferences 实例，但它是「当前 Activity 专用」的轻量级写法。
        *核心特点：
        *   无需指定 SharedPreferences 文件名，默认以当前 Activity 的类名作为文件名（比如 MainActivity 对应的文件是 MainActivity.xml）。
        *适用场景：
        *   仅需在单个 Activity 内部存储少量配置数据（如该页面的开关状态、输入框默认值），无需和其他 Activity 共享数据。
        * */
        val prefs = getPreferences(MODE_PRIVATE);
        val isRemember = prefs.getBoolean("remember_password", false)

        val accountEdit = findViewById<TextView>(R.id.accountEdit)
        val passwordEdit = findViewById<TextView>(R.id.passwordEdit)
        val rememberPass = findViewById<CheckBox>(R.id.rememberPass)
        val login = findViewById<TextView>(R.id.login)

        if (isRemember) {
            // 从 SharedPreferences 中读取密码
            val account = prefs.getString("account", "")
            val password = prefs.getString("password", "")

            // 设置 account 和 password 到对应的控件中
            accountEdit.setText(account)
            passwordEdit.setText(password)
            rememberPass.isChecked = true
        }
        /*
        * 登录按钮的点击事件
        * */
        login.setOnClickListener { view ->
            val account = accountEdit.text.toString()
            val password = passwordEdit.text.toString()
            val rememberPass = rememberPass.isChecked
            // 如果账号是admin且密码是123456，就认为登录成功
            if (account == "admin" && password == "123456") {
                /*
                * prefs.edit() 是 Android SharedPreferences API 中的一个方法，
                * 用于获取一个 Editor 对象来修改 SharedPreferences 中的数据。
                * */
                val editor = prefs.edit()
                if (rememberPass) { // 检查复选框是否被选中
                    editor.putBoolean("remember_password", true)
                    editor.putString("account", account)
                    editor.putString("password", password)
                } else {
                    /*
                    * editor.clear() 是 Android SharedPreferences.Editor 接口中的一个方法，用于清除当前 SharedPreferences 文件中的所有数据。
                    * 必须调用 editor.apply() 或 editor.commit() 才会真正执行清除操作
                    * */
                    editor.clear()
                }
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                /*
                * finish() 是 Android Activity 类中的一个方法，用于结束当前 Activity 并将其从任务栈中移除。
                * 1. 基本作用
                    结束当前 Activity 的生命周期
                    将当前 Activity 从活动栈中弹出
                    返回到前一个 Activity
                * */
                finish()
            } else {
                Toast.makeText(this, "account or password is invalid",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}