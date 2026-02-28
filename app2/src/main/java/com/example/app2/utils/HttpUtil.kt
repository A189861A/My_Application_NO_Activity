package com.example.app2.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

import okhttp3.OkHttpClient
import okhttp3.Request

object HttpUtil {

    private val client = OkHttpClient()

    fun sendHttpRequest(address: String, listener: HttpCallbackListener) {
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL(address)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream // 获取服务器响应的输入流
                // InputStreamReader: 用于将字节流转换为字符流
                // BufferedReader: 读取字符流
                val reader = BufferedReader(InputStreamReader(input))
                reader.use { // 自动关闭资源
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                listener.onFinish(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                listener.onError(e)
            } finally {
                connection?.disconnect()
            }
        }
    }

    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback) {
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(callback)
    }
}

interface HttpCallbackListener {
    fun onFinish(response: String)
    fun onError(e: Exception)
}
