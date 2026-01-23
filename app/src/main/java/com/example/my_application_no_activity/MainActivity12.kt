package com.example.my_application_no_activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.OutputStreamWriter

class MainActivity12 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main12)

    }

    override fun onDestroy() {
        super.onDestroy()
        val inputText = findViewById<android.widget.EditText>(R.id.editText).text.toString()
        save(inputText)
    }


    private fun save(inputText: String) {
        try {
            /*
            * openFileOutput：用于在应用的 内部存储（私有目录） 中创建 / 打开一个文件并返回 FileOutputStream 输出流
            * MODE_PRIVATE：表示创建文件时如果文件已存在则覆盖
            *
            * OutputStreamWriter：将字节流转换为字符流，以便写入字符数据
            * */
            val fileOutputStream = openFileOutput("data", MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(fileOutputStream))
            writer.use {
                it.write(inputText)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}