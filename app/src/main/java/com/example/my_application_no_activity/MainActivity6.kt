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
        /*
        * 在 Android 的 Kotlin 开发中，能够“直接”调用 findViewById 而不需要写类似 activity.findViewById()
        * 或 view.findViewById().
        *
        * Kotlin 的“隐式 this”机制：
        *   当你在 MainActivity 里写代码时，当前的上下文（作用域）就是 this（即当前的 Activity 实例）。
        * */
        val button = findViewById<View>(R.id.leftFrag).findViewById<Button>(R.id.button)

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
        * replace() 方法用于替换容器内的 Fragment，它接受3个参数：
        *   参数1:容器的 ID，用于指定要替换的容器；
        *   参数2:要添加的 Fragment 实例。
        *       例如，以下代码将一个名为 fragment 的 Fragment 实例添加到 ID 为 R.id.fragment_container 的容器中：
        *   参数3：tag (String) —— 可选
        *       通过 fragmentManager.findFragmentByTag(tag) 在任何地方把这个 Fragment 实例找出来，
        *       方便 Fragment 之间进行通信。
        * */
        fragmentTransaction.replace(R.id.rightLayout, fragment)
//        将一个事务添加到返回栈中
        fragmentTransaction.addToBackStack(null)
//        提交事务，调用commit()方法来完成
        fragmentTransaction.commit()
    }
}