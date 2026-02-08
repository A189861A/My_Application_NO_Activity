package com.example.app2

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    private val mBinder = DownloadBinder();
    /*
    * Binder 是 Android IPC 的核心机制，用于实现跨进程通信。
    * 在 onBind 方法中返回一个 IBinder 实例，可以让客户端与服务建立连接。
    * 通过自定义 Binder 类，你可以将服务的功能暴露给客户端，从而实现更灵活的服务交互。
    * */
     class DownloadBinder : Binder() {
        fun startDownload() {
            Log.d("MyService", "startDownload executed")
        }
        fun getProgress(): Int {
            Log.d("MyService", "getProgress executed")
            return 0
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder;
    }
    override fun onCreate() {
        super.onCreate()
        Log.d("MyService", "onCreate executed")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand executed")
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "onDestroy executed")
    }
}