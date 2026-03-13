package com.example.sunnyweather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunnyweather.model.WeatherResponse
import com.example.sunnyweather.repository.WeatherRepo
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repo = WeatherRepo()
    /*
    * MutableLiveData<WeatherResponse>：是一个可观察的数据容器，
    *   - 当数据发生变化时，会自动通知所有观察者。
    *   - 这里用于存储天气数据，当天气数据更新时，会自动通知界面更新。
    * */
    val weatherData = MutableLiveData<WeatherResponse>()
    val isRefreshing = MutableLiveData<Boolean>()

    // 获取天气
    fun getWeather(city: String) {
        isRefreshing.postValue(true)
        /*
        * viewModelScope: 提供的协程作用域，专门绑定到 ViewModel 的生命周期。
        * 它会自动管理协程生命周期，ViewModel 销毁时，协程自动取消，避免内存泄漏和无用任务。
        * - launch: 创建一个新的协程，并启动它。
        *     参数：block: suspend () -> Unit：协程体，即需要执行的代码块。
        *     返回值：Job：协程任务对象，可以通过它来取消协程。
        * */
        viewModelScope.launch {
            repo.fetchWeather(city) {
                weatherData.postValue(it)
                isRefreshing.postValue(false)
            }
        }
    }
}