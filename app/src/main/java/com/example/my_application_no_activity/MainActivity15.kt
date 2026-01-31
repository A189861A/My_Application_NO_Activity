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
        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)

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

        val replaceData = findViewById<android.widget.Button>(R.id.replaceData);
        replaceData.setOnClickListener {
            val db = dbHelper.getWritableDatabase();
            db.beginTransaction()// 开始事务
            var success = false
            try {
                db.delete("Book", null, null)
                // 模拟异常情况
                /*  if (true) {
                      // 手动抛出一个异常，让事务失败
                      throw NullPointerException()
                  }*/
                val values = ContentValues().apply {
                    put("name", "Game of Thrones")
                    put("author", "George Martin")
                    put("pages", 720)
                    put("price", 20.85)
                }
                db.insert("Book", null, values)
                success = true // 标记事务成功
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (success) {
                    db.setTransactionSuccessful() // 只有在事务成功时才标记为成功
                }
                db.endTransaction() // 最后统一结束事务
            }
        }

        val deleateData = findViewById<android.widget.Button>(R.id.deleateData)
        deleateData.setOnClickListener {
            val db = dbHelper.getWritableDatabase()
            try {
                // 删除 Book 表中的所有数据
                val deletedRows = db.delete("Book", null, null)
                if (deletedRows > 0) {
                    Toast.makeText(
                        this,
                        "数据删除成功，共删除 $deletedRows 条记录",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, "没有数据可删除", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "删除数据失败", Toast.LENGTH_SHORT).show()
            }
        }

        val queryData = findViewById<android.widget.Button>(R.id.queryData)
        queryData.setOnClickListener {
            val db = dbHelper.getWritableDatabase()
            val cursor = db.query("Book", null, null, null, null, null, null)
//            将游标移动到查询结果的第一行
            if (cursor.moveToFirst()) {
                do {
                    // 遍历Cursor对象，取出数据并打印
                    // 安全地获取列索引并读取数据
//                    getColumnIndex 是 Android 中 Cursor 类的一个方法，用于获取指定列名在查询结果中的索引位置。以下是其主要功能和使用要点：
                    val nameIndex = cursor.getColumnIndex("name")
                    val authorIndex = cursor.getColumnIndex("author")
                    val pagesIndex = cursor.getColumnIndex("pages")
                    val priceIndex = cursor.getColumnIndex("price")
                    if (nameIndex != -1 && authorIndex != -1 && pagesIndex != -1 && priceIndex != -1) {
                        val name = cursor.getString(nameIndex)
                        val author = cursor.getString(authorIndex)
                        val pages = cursor.getInt(pagesIndex)
                        val price = cursor.getDouble(priceIndex)
                        Log.d("MainActivity", "book name : $name"+" &author : $author" +" &pages : $pages")
                    }

                } while (cursor.moveToNext())
            } else {
                Toast.makeText(this, "查询结果为空", Toast.LENGTH_SHORT).show()
            }

//            必须调用 cursor.close() 释放资源，防止内存泄漏
            cursor.close()
        }
    }
}