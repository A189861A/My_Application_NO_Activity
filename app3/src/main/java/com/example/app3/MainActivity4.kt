package com.example.app3

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.app3.databinding.ActivityMain4Binding
import com.example.app3.model.Main4ViewModel
import com.example.app3.model.Main4ViewModelFactory

class MainActivity4 : AppCompatActivity() {
    // 延迟初始化
    lateinit var viewModel: Main4ViewModel
    lateinit var binding: ActivityMain4Binding
    /*
     * # SharedPreferences:
     * - 轻量级的存储类，主要用于保存应用程序的配置信息
     * - 用于存储和检索简单的键值对数据。
     * 使用 SharedPreferences 主要分为三步：
           1. 获取对象：通过 Context 获取实例。
           2. 获取编辑器：获取 Editor 对象用于写入数据。
           3. 提交/应用：保存数据
    * */
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        * getPreferences:获取 SharedPreferences 实例的一种便捷方式。
        *  - 简单来说，它是 getSharedPreferences() 的“简化版”。
        *  - 它会自动使用 当前 Activity 的类名 作为文件名（例如 MainActivity.xml）。
        *  - 它创建的文件仅属于当前 Activity，通常不建议在其他地方访问它。
        * */
        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved", 0)

        /*
        * # ViewModelProvider: 它说ViewModel 的"供应商"或"管家"。
        *   - 创建、存储并提供 ViewModel 实例，并确保这些实例在配置更改（如屏幕旋转）时不会丢失，
        *       从而避免数据重复加载或状态丢失。
        *   - 确保获取到的 ViewModel 实例与特定的作用域（通常是 Activity 或 Fragment）绑定。
        * */
//        viewModel = ViewModelProvider(this).get(Main4ViewModel::class.java)

        /*
        * ViewModelProvider：仓库管理员。
        * owner：仓库的房间号（决定了 ViewModel 存在哪里）。
        * factory：制造图纸（决定了 ViewModel 怎么造出来）。
        * */
        viewModel = ViewModelProvider(this,Main4ViewModelFactory(countReserved))
            .get(Main4ViewModel::class.java)
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
            viewModel.counter++
            refreshCounter();
        }
        binding.clearBtn.setOnClickListener {
            viewModel.counter = 0
            refreshCounter()
        }
    }

    private fun refreshCounter() {
        binding.infoText.text = viewModel.counter.toString()
    }
}