package com.example.my_application_no_activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.content.Intent
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val data = intent.getStringExtra("extra_data")
        Log.d("--SecondActivity--", "data: $data")

        val btn2: Button = findViewById(R.id.button2);
        btn2.setOnClickListener {
//            Toast.makeText(this, "Button_2 clicked", Toast.LENGTH_SHORT).show()
             val intent = Intent(); // 构建Intent对象，传递数据
            intent.putExtra("data_return", "Hello FirstActivity");
            setResult(RESULT_OK, intent); // 设置返回结果
            finish(); // 关闭当前Activity
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(); // 构建Intent对象，传递数据
                intent.putExtra("data_return", "Hello FirstActivity");
                setResult(RESULT_OK, intent); // 设置返回结果
                finish(); // 关闭当前Activity
            }
        })

    }

//    override fun onBackPressed() {
//        super.onBackPressed();
//        val intent = Intent();
//        intent.putExtra("data_return", "Hello FirstActivity");
//        setResult(RESULT_OK, intent);
//        finish();
//    }


}