package com.example.my_application_no_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        点击事件
        val startNormalActivity = findViewById<Button>(R.id.startNormalActivity)
        val startDialogActivity = findViewById<Button>(R.id.startDialogActivity)

        startNormalActivity.setOnClickListener {
            val intent = Intent(this, NormalActivity::class.java)
            startActivity(intent);
        }

        startDialogActivity.setOnClickListener {
            val intent = Intent(this, DialogActivity::class.java)
            startActivity(intent);
        }
        if(savedInstanceState != null){
            val tempData = savedInstanceState.getString("tempData")
            Log.d("--MainActivity-oncreate--", "tempData: $tempData")
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("--MainActivity--", "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.d("--MainActivity--", "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d("--MainActivity--", "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d("--MainActivity--", "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("--MainActivity--", "onDestroy")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d("--MainActivity--", "onRestart")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("--MainActivity--", "onSaveInstanceState")
        val tempData = "Hello, this is a temp data"
        outState.putString("tempData", tempData)
    }
}