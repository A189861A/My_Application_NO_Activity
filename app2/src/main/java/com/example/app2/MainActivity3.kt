package com.example.app2

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    /*
    * 任何一个Service在整个应用程序范围内都是通用的，即MyService不仅可以和
        MainActivity绑定，还可以和任何一个其他的Activity进行绑定，而且在绑定完成后，它们都可
        以获取相同的DownloadBinder实例
    * */
    lateinit var downloadBinder : MyService.DownloadBinder
    /*
    * ServiceConnection 是 Android 中用于管理服务绑定和解绑的接口
    * 通过 ServiceConnection，客户端可以获取服务暴露的 Binder 对象，从而调用服务中的方法或获取数据
    * ServiceConnection 是实现服务绑定和交互的核心接口，负责管理客户端与服务之间的连接生命周期。
    * */
    private val connection = object : ServiceConnection {
//        当客户端成功绑定到服务时，系统会调用 onServiceConnected() 方法
//        通过该方法，客户端可以获取服务提供的 IBinder 对象，进而与服务进行交互
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }
        /*
        * 当服务意外断开连接时（例如服务崩溃或被系统终止），
        * 系统会调用 onServiceDisconnected(ComponentName name) 方法
        * */
        override fun onServiceDisconnected(name: ComponentName) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val startServiceBtn = findViewById<Button>(R.id.startServiceBtn)
        val stopServiceBtn = findViewById<Button>(R.id.stopServiceBtn)
        val bindServiceBtn = findViewById<Button>(R.id.bindServiceBtn)
        val unbindServiceBtn = findViewById<Button>(R.id.unbindServiceBtn)

        startServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            /*
            * startService 是 Context 类中的公开方法
            * 启动一个 Service
            * */
            startService(intent) // 启动Service
        }
        stopServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent) // 停止Service
        }

        bindServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            /*
            * bindService 是 Android 中 Context 类提供的一个方法，用于将客户端
            * （如 Activity）与服务（Service）进行绑定
            * */
            bindService(intent, connection, Context.BIND_AUTO_CREATE) // 绑定Service
        }

        unbindServiceBtn.setOnClickListener {
            unbindService(connection) // 解绑Service
        }

    }
}