package com.example.my_application_no_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

class MainActivity11 : BaseActivity2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main11)

        val button: Button = findViewById<Button>(R.id.forceOffline)
        button.setOnClickListener {
            Toast.makeText(this, "You clicked Force Offline", Toast.LENGTH_SHORT).show()
//            发送了一条广播
            val intent = Intent("com.example.broadcastbestpractice.FORCE_OFFLINE")
            // 添加这一行，将隐式广播变为显式广播
            intent.setPackage(packageName)
            sendBroadcast(intent)
        }
    }
}