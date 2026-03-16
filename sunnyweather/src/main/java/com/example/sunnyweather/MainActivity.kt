package com.example.sunnyweather

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.sunnyweather.ui.CityChangeActivity
import com.example.sunnyweather.viewModel.WeatherViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var temp: TextView
    private lateinit var weather: TextView
    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var viewModel: WeatherViewModel
    /*
    * registerForActivityResult：向系统注册一个 Activity 结果回调。
    *   这意味着当子页面返回结果时，系统会自动执行后面花括号 { ... } 里的代码。
    *
    * 第一个参数：ActivityResultContracts.StartActivityForResult()
    *   这是一个预定义的协定，用于启动一个 Activity 并等待其返回结果。
    *
    * 第二个参数：{ result -> ... }
    *   这是一个 Lambda 表达式，用于处理子页面返回的结果。
    *   当子页面调用 setResult() 并关闭时，系统会自动调用这个 Lambda 表达式。
    * */
    private val cityChangeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val city = result.data?.getStringExtra("city")
            if (!city.isNullOrEmpty()) {
                currentCity = city
                viewModel.getWeather(currentCity)
            }
        }
    }

    //    private var currentCity = "116.403874,39.914885" // 北京经纬度
    private var currentCity = "121.473701,31.230416" // 上海经纬度

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initVM()
        playBgVideo()
        observeData()
    }
    private fun initView() {
        videoView = findViewById(R.id.videoView)
        temp = findViewById(R.id.temp)
        weather = findViewById(R.id.weather)
        refresh = findViewById(R.id.refresh)
        findViewById<Button>(R.id.btn_change).setOnClickListener {
            /*
            * launch：启动一个 Activity。
            * */
            cityChangeLauncher.launch(Intent(this, CityChangeActivity::class.java))
        }
    }

    private fun initVM() {
        /*
        * ViewModelProvider：用于创建和管理 ViewModel 实例。
        * - this：当前 Activity 的上下文。
        * - ViewModel如果存在，就复用，其里面的数据不会丢失。
        *
        * */
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        viewModel.getWeather(currentCity)

        // 下拉刷新
        refresh.setOnRefreshListener {
            viewModel.getWeather(currentCity)
        }
    }

    // 观察ViewModel数据
    private fun observeData() {
        viewModel.weatherData.observe(this) {
            temp.text = it.result.realtime.temperature.toInt().toString() + "°"
            weather.text = it.result.realtime.skycon.replace("_", " ")
        }

        viewModel.isRefreshing.observe(this) {
            refresh.isRefreshing = it
        }
    }

    // 视频背景循环播放
    private fun playBgVideo() {
        val uri = Uri.parse("android.resource://$packageName/${R.raw.bg}")
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener {
            it.isLooping = true
            it.start()
        }
    }

    override fun onResume() {
        super.onResume()
        // 移除原有的intent获取逻辑，现在通过Activity Result API获取
    }
}