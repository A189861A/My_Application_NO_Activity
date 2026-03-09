package com.example.app3

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.app3.databinding.ActivityMain4Binding
import com.example.app3.model.Main4ViewModel

class MainActivity4 : AppCompatActivity() {
    // 延迟初始化
    lateinit var viewModel: Main4ViewModel
    lateinit var binding: ActivityMain4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        * # ViewModelProvider: 它说ViewModel 的"供应商"或"管家"。
        *   - 创建、存储并提供 ViewModel 实例，并确保这些实例在配置更改（如屏幕旋转）时不会丢失，
        *       从而避免数据重复加载或状态丢失。
        *   - 确保获取到的 ViewModel 实例与特定的作用域（通常是 Activity 或 Fragment）绑定。
        * */
        viewModel = ViewModelProvider(this).get(Main4ViewModel::class.java)
        /*
        * #  Android ViewBinding（视图绑定）
        * - ActivityMain4Binding 是一个自动生成的类，它包含了对布局文件中所有视图的引用。
        * - 它的名字是根据你的布局文件名自动生成的
        * - 这个类包含了布局中所有带有 android:id 属性的控件的引用。
        * # inflate静态方法
        * - inflate() 方法用于将布局文件转换为 View 对象.
        * */
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.plusOneBtn.setOnClickListener {
            Log.d("--onClick--", "onClick:")
            viewModel.count++
            refreshCounter();
        }

    }

    private fun refreshCounter() {
        binding.infoText.text = viewModel.count.toString()
    }
}