package com.example.my_application_no_activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main6)
        val button = findViewById<View>(R.id.leftFrag).findViewById< Button>(R.id.button)

        button.setOnClickListener {
//  AnotherRightFragment： Kotlin 的顶层类型导入机制，支持通过包级别函数和属性的方式访问同一包下的类
            replaceFragment(AnotherRightFragment())
            Toast.makeText(this, "点击了按钮", Toast.LENGTH_SHORT).show()
        }
        replaceFragment(RightFragment())
    }
    private fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager 是 AppCompatActivity 提供的 Fragment 管理器，
        //        用于管理应用中的 Fragment 生命周期和事务操作
        val fragmentManager = supportFragmentManager
        /*
        * 开启一个事务
        * */
        val fragmentTransaction = fragmentManager.beginTransaction()
        /*
        * 向容器内添加或替换Fragment
        * */
        fragmentTransaction.replace(R.id.rightLayout, fragment)
//        将一个事务添加到返回栈中
        fragmentTransaction.addToBackStack(null)
//        提交事务，调用commit()方法来完成
        fragmentTransaction.commit()
    }
}