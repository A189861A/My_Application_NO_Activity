package com.example.app2

import MyDatabaseHelper
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 1. 初始化Helper（优先用applicationContext避免内存泄漏）
        // applicationContext : 代表当前应用程序的全局上下文
        val dbHelper = MyDatabaseHelper(applicationContext)

        // 2. 获取可写数据库（读数据用getReadableDatabase()）
        val db = dbHelper.writableDatabase

        // 3. 示例：插入数据（Kotlin的ContentValues使用更简洁）
        val values = ContentValues().apply {
            put(MyDatabaseHelper.COLUMN_NAME, "张三")
            put(MyDatabaseHelper.COLUMN_AGE, 25)
        }
        // 插入数据，返回新行ID（失败返回-1）
        val newRowId = db.insert(MyDatabaseHelper.TABLE_USER, null, values)
        Log.d("MainActivity", "插入数据的行ID：$newRowId")

        // 4. 关闭数据库（建议在onDestroy中调用）
        db.close()
        dbHelper.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 确保数据库资源释放
        // 如果全局复用Helper，可不在此处关闭，统一在Application销毁时处理
    }
}