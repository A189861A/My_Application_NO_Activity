package com.example.app2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyServiceFront : Service() {
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
        /*
        *getSystemService 是 Android 中 Context 类的一个方法，
        *用于获取系统级别的服务。这些服务提供了访问设备功能（如通知、位置、网络等）的接口
        *
        * 在 Kotlin 中，as 是一个类型转换操作符，用于将对象强制转换为目标类型
        * 将其转换为 NotificationManager 类型，以便后续创建通知或管理通知渠道。
        * */
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        // 判断设备是否运行 Android 8.0 或更高版本。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "my_service", "前台Service通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        /*
        * 这段代码创建了一个 Intent 对象，用于启动 MainActivity。
        * Intent 是 Android 中用于组件间通信的类，可以用来启动 Activity、Service 或发送广播。
        *   this：当前上下文（这里是 Service）。
            MainActivity::class.java：目标 Activity 的类对象，表示要启动的页面。
        * */
        val intent = Intent(this, MainActivity::class.java)
        /*
        * 1. 延迟执行: 在未来的某个时间点触发，例如点击通知时启动 Activity。
        * 2. 权限委托: 其他应用可以通过 PendingIntent 执行你应用中的操作，而无需直接访问你的应用组件。
        * */
        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        // 创建通知
        val notification = NotificationCompat.Builder(this, "my_service")
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setSmallIcon(R.drawable.small_icon)
            // BitmapFactory 是 Android 中用于创建 Bitmap 对象的工具类.
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
            .setContentIntent(pi) // 设置点击通知打开的 Activity. 设置用户点击通知时要执行的操作
            .setAutoCancel(true) // 点击后自动关闭通知
            .build() // 用于生成最终的 Notification 对象
        /*
        * startForeground 是 Android 中 Service 的一个方法，用于将服务置于前台运行。
        * 在 Android 8.0（API level 26）及更高版本中，必须使用 startForeground 方法来启动前台服务，
        * 否则，系统将终止你的服务并显示一个错误消息。
        * */
        startForeground(1, notification)
    }
    /*
    * 当通过 startService() 或 startForegroundService() 启动服务时，系统会调用此方法。
    * 在 onStartCommand 方法中，你可以处理服务启动时的参数，并返回一个标志，指示服务是否应该继续运行。
    * 主要作用是处理启动服务时传递的 Intent 数据，并定义服务的运行方式。
    * */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService", "onStartCommand executed-"+ flags + "/" + startId)
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService", "onDestroy executed")
    }
}