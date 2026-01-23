package com.example.my_application_no_activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity12 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main12)

        val inputText = load()
        val editText = findViewById<android.widget.EditText>(R.id.editText)
        if(inputText.isNotEmpty()) {
            editText.setText(inputText)
            editText.setSelection(inputText.length)
        }

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

    private fun load(): String {
        val content = StringBuilder()
        try {
            /*
            * openFileInput：用于在应用的 私有存储（私有目录） 中打开一个文件并返回 FileInputStream 输入流
            * InputStreamReader：将字节流转换为字符流，以便读取字符数据
            * BufferedReader：用于读取文本文件，它提供了读取文本的便捷方法
            * */
            val input = openFileInput("data")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.lines().forEach {
                    content.append(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return content.toString()
    }
}

























