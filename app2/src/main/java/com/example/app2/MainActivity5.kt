package com.example.app2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val sendRequestBtn = findViewById<Button>(R.id.sendRequestBtn)
        sendRequestBtn.setOnClickListener {
            sendRequestWithHttpURLConnection();
        }
    }

    private fun sendRequestWithHttpURLConnection() {
        // 开启线程发起网络请求
        thread {
            var connection: HttpURLConnection? = null
            try {
                /*
                * 用于高效构建和操作字符串的类
                *   append()：追加内容到末尾。
                    toString()：将 StringBuilder 转换为 String。
                    insert()：在指定位置插入内容。
                    delete()：删除指定范围的字符。
                * */
                val response = StringBuilder()
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                // 获取服务器响应的数据流
                val input = connection.inputStream
                // 下面对获取到的输入流进行读取
                /*
                * InputStreamReader：将 字节流 转换为 字符流 的桥梁类
                * BufferedReader：用于高效读取字符流的类
                * */
                val reader = BufferedReader(InputStreamReader(input))
                /*
                * reader.use 是 Kotlin 中用于自动管理资源的语法糖
                *  use 函数会确保在代码块执行完毕后自动调用 reader.close()，避免资源泄漏。
                * */
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                showResponse(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun showResponse(response: String) {
        runOnUiThread {
            val responseText = findViewById<TextView>(R.id.responseText)
            // 在这里进行UI操作，将结果显示到界面上
            responseText.text = response
        }
    }
}
