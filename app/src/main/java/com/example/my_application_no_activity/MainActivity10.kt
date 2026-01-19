package com.example.my_application_no_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity10 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main10)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent("com.example.broadcasttest.MY_BROADCAST")
//            packageName是getPackageName()的语法糖写法，用于获取当前应用程序的包名。
            intent.setPackage(packageName)

            /*调用sendBroadcast()方法将广播发送出去，
              这样所有 监听com.example.broadcasttest.MY_BROADCAST这条广播的BroadcastReceiver就会收到消息了
              默认情况下我们发出的自定义广播恰恰都是隐式广播。因此这里一定要调用setPackage()方法，指定这条广播是
              发送给哪个应用程序的，从而让它变成一条显式广播
            */
//            sendBroadcast(intent)

            sendOrderedBroadcast(intent, null)

        }

    }
}