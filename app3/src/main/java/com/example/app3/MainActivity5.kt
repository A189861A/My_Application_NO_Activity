package com.example.app3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.app3.databinding.ActivityMain5Binding
import com.example.app3.model.Main4ViewModel
import com.example.app3.model.Main4ViewModelFactory
import com.example.app3.model.Main5ViewModelFactory
import com.example.app3.model.MainViewModel
import androidx.core.content.edit

class MainActivity5 : AppCompatActivity() {
    lateinit var bingding: ActivityMain5Binding
    lateinit var viewModel: MainViewModel
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved", 0)

        bingding = ActivityMain5Binding.inflate(layoutInflater)
        setContentView(bingding.root)

        viewModel = ViewModelProvider(this, Main5ViewModelFactory(countReserved)).get(MainViewModel::class.java)

        bingding.plusOneBtn.setOnClickListener {
            viewModel.plusOne()
        }
        bingding.clearBtn.setOnClickListener {
            viewModel.clear()
        }

        /*
        * 任何LiveData对象都可以调用它的observe()方法来观察数据的变化
        * observe：观察数据变化
        * observe()方法接收两个参数：
        *  - 第一个参数是 LifecycleOwner 对象（“拥有生命周期的对象“），通常是 Activity 或 Fragment。
        *  - 第二个参数是一个 Lambda 表达式，当 LiveData 中的数据发生变化时，会调用这个 Lambda 表达式。
        * */
        viewModel.counter.observe(this) { newCount ->
            bingding.counterText.text = newCount.toString()
        }

    }
    override fun onPause() {
        super.onPause()
        // 保存数据
        // 在 Kotlin 中，?: 空值合并操作符。(如果左边的表达式不为 null，就返回左边；如果为 null，就返回右边)
        sp.edit { putInt("count_reserved", viewModel.counter.value ?: 0) }
    }
}