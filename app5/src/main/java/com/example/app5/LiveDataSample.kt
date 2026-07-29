package com.example.app5

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class LiveDataSample : AppCompatActivity() {
    private lateinit var vm: DemoViewModel
    private lateinit var tvText: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnNet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_sample)

        tvText = findViewById(R.id.tv_text)
        btnAdd = findViewById(R.id.btn_add)
        btnNet = findViewById(R.id.btn_net)
        /*
         * Activity 中写 val vm = MyViewModel()，每次 Activity 因为屏幕旋转等配置变更被销毁并重建时，
            MyViewModel() 都会被重新执行，你之前保存的数据就全丢了
        * ViewModelProvider：用来提供 ViewModel 实例的。
        *   -- 生命周期隔离：它创建的 ViewModel 的生命周期独立于 Activity/Fragment 的生命周期。
        *   -- 作用域内单例：在同一个 Activity/Fragment 的作用域内，无论你调用多少次 ViewModelProvider
        *       去获取同一个 ViewModel 类，你拿到的永远是同一个实例。
        *
        * */
        vm = ViewModelProvider(this).get(DemoViewModel::class.java)

        // 观察 LiveData
        vm.count.observe(this) { number ->
            // 页面活跃时自动回调，更新UI
            tvText.text = "当前数值：$number"
        }

        btnAdd.setOnClickListener {
            vm.addCount()
        }

        btnNet.setOnClickListener {
            vm.simulateNetRequest()
        }

    }
}