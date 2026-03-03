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
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val fab = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            Toast.makeText(this, "FAB 点击事件", Toast.LENGTH_SHORT).show()
//            val drawerLayout = findViewById<DrawerLayout>(R.id.main)
//            drawerLayout.openDrawer(GravityCompat.START)
        }

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
            /*
            * 1.ContextCompat：是 AndroidX 库中的一个类，提供向下兼容（Backward Compatibility）的支持。
            * 2.getDrawable()：是 ContextCompat 类中的一个方法，用于从资源文件中获取 Drawable 对象。
            * 3.Drawable ：是 Android 中用于表示图像的抽象类，可以表示各种类型的图像，如位图、形状、颜色等。
            *   任何继承自 Drawable 的对象都可以被绘制到 Canvas（画布）上.
            *   Drawable 的常见子类 (类型):BitmapDrawable 、ShapeDrawable、ColorDrawable 等。
            *
            * */
            val originalDrawable = ContextCompat.getDrawable(this, R.drawable.ic_menu)
            originalDrawable?.let { drawable ->
                /* 创建指定大小的 drawable
                * */
                if (drawable is BitmapDrawable) {
                    /*
                    * Bitmap：是 Android 中用于表示位图的类。
                    *      屏幕上看到的图片的数字化表示（例如：.png 或 .jpg 文件被解码到内存后，就变成了 Bitmap 对象
                    * Bitmap.createScaledBitmap()：是 Bitmap 类中的一个静态方法，用于创建一个指定大小的位图。
                    * */
                    val resizedBitmap = Bitmap.createScaledBitmap(
                        drawable.bitmap, // 源 Bitmap 对象
                        200, // 目标宽度
                        200, // 目标高度
                        true // 是否开启抗锯齿（滤波）
                    )
                    /*
                    * 在 Android 开发中，存在两种处理图片的体系：
                    * Bitmap 体系：侧重于数据处理。它负责存储像素点，进行解码、缩放、像素修改等底层操作。它是“死”的数据块。
                    * Drawable 体系：侧重于UI 渲染。它是 View 能够理解的对象。View（如 ImageView、Button）只能接受 Drawable 作为背景或图片源。
                    * */
                    val resizedDrawable = BitmapDrawable(resources, resizedBitmap)
                    it.setHomeAsUpIndicator(resizedDrawable)
                }
            }
        }

        val navView = findViewById<NavigationView>(R.id.navView)
        /*
        * 用于处理 NavigationView（侧滑导航菜单） 中菜单项点击事件的核心方法。
        * */
        navView.setNavigationItemSelectedListener {
            menuItem ->
//             处理菜单项点击事件
            when (menuItem.itemId) {
                R.id.navCall -> {
                    // 处理 Home 菜单项点击事件
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                }
                R.id.navFriends -> {
                    // 处理 Favorites 菜单项点击事件
                    Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show()
                }
            }
            val drawerLayout = findViewById<DrawerLayout>(R.id.main)
            drawerLayout.closeDrawer(GravityCompat.START)
            true;
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