package com.example.my_application_no_activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

//可以被继承，在类名的前面加上了open关键字
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("--BaseActivity--", javaClass.simpleName )
        ActivityCollector.addActivity(this);
    }
    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this);
    }
}