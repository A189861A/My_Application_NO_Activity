package com.example.sunnyweather

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
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

    private var currentCity = "116.403874,39.914885" // 北京经纬度

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
            startActivity(Intent(this, CityChangeActivity::class.java))
        }
    }

    private fun initVM() {
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
        val city = intent.getStringExtra("city")
        if (!city.isNullOrEmpty()) {
            currentCity = city
            viewModel.getWeather(currentCity)
        }
    }
}