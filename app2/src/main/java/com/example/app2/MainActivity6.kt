package com.example.app2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

import okhttp3.OkHttpClient
import okhttp3.Request


class MainActivity6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        val startServiceBtn = findViewById<Button>(R.id.startServiceBtn)
        startServiceBtn.setOnClickListener {
            sendRequestWithOkHttp();
        }

    }

    private fun sendRequestWithOkHttp() {
        thread {
            try {
                // HTTP 客户端库
                val client = OkHttpClient()
                // 创建 Request 对象，指定请求的 URL、HTTP 方法（如 GET、POST）、请求头、请求体等。
                val request = Request.Builder()
                    .url("https://www.baidu.com/")
                    .build()
                /*
                * client.newCall 是 OkHttp 框架中发起网络请求的核心方法
                * newCall 用于基于配置好的 Request 对象创建一个可执行的 Call 对象，进而发起同步 / 异步网络请求。
                * */
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    // 在这里进行UI更新操作
                    showResponse(responseData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun showResponse(response: String) {
        /*
        * 在主线程（UI 线程）中执行代码
        *   当你在子线程中需要更新 UI 时，必须切换到主线程
        * */
        runOnUiThread {
            val responseText = findViewById<TextView>(R.id.tv)
            // 在这里进行UI操作，将结果显示到界面上
            responseText.text = response
        }
    }
}