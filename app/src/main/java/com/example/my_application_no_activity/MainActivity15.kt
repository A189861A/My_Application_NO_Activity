package com.example.my_application_no_activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity15 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main15)

        /*
        * 关键：
        *   获取数据库帮助类实例，并调用 getWritableDatabase()/getReadableDatabase()
        *   这一步会触发数据库创建 → 进而执行 onCreate() 中的建表语句
        * */
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 1)

        val createDatabase = findViewById<android.widget.Button>(R.id.createDatabase)
        createDatabase.setOnClickListener {
            // 触发数据库文件 BookStore.db 的创建过程
            dbHelper.getWritableDatabase(); // 首次调用时创建数据库+执行建表
        }

        val addData = findViewById<android.widget.Button>(R.id.addData)
        addData.setOnClickListener {
            /*
            * getWritableDatabase()：获取可读写的数据库实例（推荐）；
            * getReadableDatabase()：优先获取只读实例，若数据库只读则返回，否则返回可读写实例。
            * */
            val db = dbHelper.getWritableDatabase()
            // ContentValues 是 Android 提供的一个用于存储键值对数据的类，主要用于数据库操作。
            val values1 = ContentValues().apply {
                // 开始组装第一条数据
                put("name", "The Da Vinci Code")
                put("author", "Dan Brown")
                put("pages", 454)
                put("price", 16.96)
            }
            db.insert("Book", null, values1) // 插入第一条数据

            val values2 = ContentValues().apply {
                // 开始组装第二条数据
                put("name", "The Lost Symbol")
                put("author", "Dan Brown")
                put("pages", 510)
                put("price", 19.95)
            }
            db.insert("Book", null, values2) // 插入第二条数据
            Toast.makeText(this, "数据添加成功", Toast.LENGTH_SHORT).show()
        }

        val updateData = findViewById<android.widget.Button>(R.id.updateData)
        updateData.setOnClickListener {
            Toast.makeText(this, "数据更新成功", Toast.LENGTH_SHORT).show()
            val db = dbHelper.getWritableDatabase()
            val values = ContentValues()
            values.put("price", 10.99)
            db.update("Book", values, "name = ?", arrayOf("The Da Vinci Code"))
        }
    }
}