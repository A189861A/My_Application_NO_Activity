package com.example.app5

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Received intent with action " + intent.action, Toast.LENGTH_SHORT)
            .show()
        // 截断/取消当前的有序广播
        abortBroadcast()
    }
}