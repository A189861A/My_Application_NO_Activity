package com.example.my_application_no_activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity9 : AppCompatActivity() {
    lateinit var timeChangeReceiver: TimeChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main9)

        val intentFilter = IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");

        timeChangeReceiver = TimeChangeReceiver();
        /*
        * 注册广播
        * */
        registerReceiver(timeChangeReceiver, intentFilter);
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver);
    }
    inner class TimeChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val toast = Toast.makeText(context, "时间已改变", Toast.LENGTH_SHORT)
            toast.setGravity(android.view.Gravity.CENTER, 0, 0)
            toast.show()
        }
    }
}