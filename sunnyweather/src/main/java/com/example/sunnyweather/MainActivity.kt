package com.example.sunnyweather

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
    * 返回一个 ActivityResultLauncher 对象 (启动器)，你通过这个对象的 launch() 方法来触发跳转。
    *
    * 第一个参数：ActivityResultContracts.StartActivityForResult()
    *   这是一个预定义的协定，用于启动一个 Activity 并等待其返回结果。
    *
    * 第二个参数：{ result -> ... }
    *   这是一个 Lambda 表达式，用于处理子页面返回的结果。
    *   当子页面调用 setResult() 并关闭时，系统会自动调用这个 Lambda 表达式。
    * */
    private val cityChangeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val city = result.data?.getStringExtra("city")
                if (!city.isNullOrEmpty()) {
                    currentCity = city
                    viewModel.getWeather(currentCity)
                }
            }
        }

    private var currentCity = "110100" // 北京市 adcode（下拉列表只到地级市）
//      private var currentCity = "310100" // 上海市 adcode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initVM()
        playBgVideo()
        observeData()
    }

    /*
    * 初始化视图.
    * 核心功能：集中管理与 UI 控件相关的所有初始化操作，将视图绑定、属性设置、事件监听等逻辑与业务逻辑分离，
    *   从而提高代码的可读性和可维护性。
    * */
    private fun initView() {
        videoView = findViewById(R.id.videoView)
        temp = findViewById(R.id.temp)
        weather = findViewById(R.id.weather)
        refresh = findViewById(R.id.refresh)
        findViewById<Button>(R.id.btn_change).setOnClickListener {
            /*
            * launch：启动一个 Activity。
            * */
            cityChangeLauncher.launch(
                Intent(this, CityChangeActivity::class.java)
                    .putExtra("currentCity", currentCity) // 传给下拉框，默认选中当前城市
            )
        }
    }

    /*
    * 初始化 ViewModel:
    *   ViewModel 是一个独立于视图和生命周期管理的类，用于存储和管理与 UI 相关的数据。
    *   它的生命周期比视图更长，即使视图被销毁，ViewModel 中的数据也不会丢失。
    * 核心功能：
    *   集中管理 ViewModel 的实例化、数据观察以及 UI 与 ViewModel 之间的交互绑定，将“数据逻辑”与“视图逻辑”彻底分离。
    * */
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
            Toast.makeText(this, "正在刷新...", Toast.LENGTH_SHORT).show()
            viewModel.getWeather(currentCity)
        }
    }

    // 观察ViewModel数据
    /*
    * 封装 LiveData 的观察逻辑，简化代码，统一处理加载状态、错误和数据更新
    *
    * 可以把它理解为一个**“智能观察器”**，它不仅会帮你观察数据，还会帮你处理各种 UI 状态（如显示加载中、错误提示、空数据视图等），
    * 让你的代码更简洁、更健壮。
    * */
    private fun observeData() {
        viewModel.weatherData.observe(this) {
            // lives 为 null 或空（接口返回失败结构时），取不到就直接返回
            val live = it.lives?.firstOrNull() ?: return@observe
            // 带占位符的字符串放 resources 里，避免拼接文本导致的 SetTextI18n 警告
            /*
            * getString 是 Context 的方法，从资源文件中获取字符串，支持参数替换。
            *   所以在 Activity、Fragment 或 Service 中可以直接调用。
            *   在其他类中，需要传入 Context 对象：
            *
            * %1$s 是 Android 资源字符串（strings.xml）中使用的格式化占位符，用于在运行时替换字符串中的特定部分。
            * 占位符结构：% + 序号 + $ + 类型
                %：标记开始
                1：参数序号（表示使用第几个参数）
                $：分隔符
                s：参数类型（表示字符串类型） // d：整数（Integer） f：浮点数（Float）  c：字符（Char） b：布尔值（Boolean）
            *
            * ?: ：为可空类型提供默认值,或者在值为 null 时执行某种兜底逻辑.
            *       val result = 可空变量 ?: 兜底值
            *       等价于 Java 中的三元运算符 a != null ? a : b
            * */
            temp.text = getString(
                R.string.temperature_format,
                live.temperature ?: getString(R.string.unknown)
            )
            weather.text = live.weather ?: getString(R.string.unknown)
        }
        /*
        * observe：
        *   * - this：当前 Activity 的上下文。
        * - 自动绑定生命周期：observe 会自动感知 Activity/Fragment 的生命周期。
        * - 数据驱动 UI：当 ViewModel 中的数据通过 LiveData 更新时，observe 会接收到回调，你可以在回调里更新 UI，
        *   而不是在代码里手动调用 UI 控件的方法。
        * - 数据一致性：当屏幕旋转等配置更改导致 Activity 重建时，LiveData 会保留最新数据，observe 会自动重新绑定，
        *   UI 不会丢失状态。
        * */
        viewModel.isRefreshing.observe(this) {
            refresh.isRefreshing = it // it 是 isRefreshing 的值
        }

        viewModel.errorMessage.observe(this) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                viewModel.errorMessage.value = null // 消费掉，避免旋转屏幕后重复提示
            }
        }
    }

    // 视频背景循环播放
    private fun playBgVideo() {
        /*
        * $packageName：获取当前应用的包名。 com.example.sunnyweather
        * */
        // 1. 构建视频资源的 URI
        val uri = Uri.parse("android.resource://$packageName/${R.raw.bg}")
        // 2. 设置视频源
        videoView.setVideoURI(uri)

        // 3. 设置准备完成监听器
        /*
        * 当 VideoView 完成资源加载和初始化后，会触发这个监听器。
        * 在监听器中，你可以设置播放器的各种属性（如循环播放、音量等）
        * */
        videoView.setOnPreparedListener {
            it.isLooping = false // 设置是否循环播放
            it.start() // 开始播放
        }
    }

    override fun onResume() {
        super.onResume()
        // 移除原有的intent获取逻辑，现在通过Activity Result API获取
    }
}