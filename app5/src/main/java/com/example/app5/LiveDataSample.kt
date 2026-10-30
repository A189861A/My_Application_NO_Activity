package com.example.app5

import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class LiveDataSample : AppCompatActivity() {
    lateinit var timeChangeReceiver: TimeChangeReceiver
    private lateinit var vm: DemoViewModel
    private lateinit var tvText: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnReset: Button
    private lateinit var btnNet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_sample)

        tvText = findViewById(R.id.tv_text)
        btnNet = findViewById(R.id.btn_net)
        btnAdd = findViewById(R.id.btn_add)
        btnReset = findViewById(R.id.btn_reset)
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
        /*
        * observe 方法：观察 LiveData 的数据变化，当数据变化时，会回调传入的 lambda 表达式。
        * */
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

        btnReset.setOnClickListener {
            vm.reset()
        }

        /*
        * 当系统时间发生变化时，系统发出的正是一条值为android.intent.action.TIME_TICK的广播
        * */
        val intentFilter = IntentFilter()
        /*
        * android.intent.action.TIME_TICK：
            - 每分钟触发一次.
            - 只能动态注册，AndroidManifest 静态注册收不到这个广播.
        * */
        intentFilter.addAction("android.intent.action.TIME_TICK")
        timeChangeReceiver = TimeChangeReceiver()
        // 动态注册监听时间变化
        registerReceiver(timeChangeReceiver, intentFilter)

    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消动态注册监听时间变化
        unregisterReceiver(timeChangeReceiver)
    }
}