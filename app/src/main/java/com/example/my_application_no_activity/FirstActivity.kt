package com.example.my_application_no_activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_layout)
        val btn1: Button = findViewById(R.id.btn1);
        btn1.setOnClickListener {
//            Toast.makeText(this, "Button_1 clicked", Toast.LENGTH_SHORT).show()
//            显示Intent
//            val intent = Intent(this, SecondActivity::class.java);
//            隐式Intent
//            val intent = Intent("com.example.my_application_no_activity.ACTION_START");

//            使用隐式Intent打开第三方页面
//            val intent = Intent(Intent.ACTION_VIEW) // Intent.ACTION_VIEW：安卓内置动作
//            intent.data = Uri.parse("https://www.baidu.com");// 语法糖：intent.data == intent.setData
//          拨打电话
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:10086"));
            startActivity(intent);
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.add_item -> Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show()
            R.id.remove_item -> Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show()
        }
        return true;
    }
}





















