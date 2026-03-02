package com.example.app3

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

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
//      将一个自定义的 Toolbar（工具栏）设置为当前 Activity 的 ActionBar（应用栏）。
        setSupportActionBar(findViewById(R.id.toolbar))
        /*
        * 1. ?. 安全调用操作符
        * 2.let 函数
        *   类型：Kotlin 标准库中的作用域函数
            作用：将调用对象（这里是 supportActionBar）作为参数传递给 lambda 表达式
            参数：在 lambda 中，默认使用 it 作为参数名来引用调用对象
            返回值：返回 lambda 表达式的最后一个值
        * */
        supportActionBar?.let {
            it.title = "应用标题"
            it.setDisplayHomeAsUpEnabled(true)// 显示返回按钮
//            it.setHomeAsUpIndicator(R.drawable.ic_menu)// 自定义返回按钮图标

            val originalDrawable = ContextCompat.getDrawable(this, R.drawable.ic_menu)
            originalDrawable?.let { drawable ->
                /* 创建指定大小的 drawable
                *
                * */
                if (drawable is BitmapDrawable) {
                    val resizedBitmap = Bitmap.createScaledBitmap(
                        drawable.bitmap,
                        200, 200,
                        true
                    )
                    val resizedDrawable = BitmapDrawable(resources, resizedBitmap)
                    it.setHomeAsUpIndicator(resizedDrawable)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.toolbar, menu)
            return true
    }
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        Log.d("--onOptionsItemSelected--", "onOptionsItemSelected:${item.itemId}")
        when (item.itemId) {
            R.id.action_search -> {
                // Handle search action
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
            }
            R.id.action_settings -> {
                // Handle settings action
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            }
            R.id.action_share -> {
                // Handle share action
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            }
            android.R.id.home -> {
                val drawerLayout = findViewById<DrawerLayout>(R.id.main)
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }
}