package com.example.my_application_no_activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FourActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_four)

        val button4: Button = findViewById(R.id.button4);
        button4.setOnClickListener {
//            Toast.makeText(this, "Button_4 clicked", Toast.LENGTH_SHORT).show()
            ActivityCollector.finishAll();
//            杀掉当前进程的代码
//            myPid()方法来获得当前程序的进程id
//            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }
}