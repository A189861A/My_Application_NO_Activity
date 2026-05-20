package com.example.sunnyweather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunnyweather.model.WeatherResponse
import com.example.sunnyweather.repository.WeatherRepo
import kotlinx.coroutines.launch

/*
* WeatherViewModel：是一个 ViewModel 类，
* - 管理UI数据。
* - 处理业务逻辑。
* */
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
        * - suspend：修饰符，用于标记一个函数或方法为挂起函数，可以在协程中调用。
        * - postValue：将数据更新到 LiveData 中，并通知所有观察者。
        * * */
        viewModelScope.launch {
            repo.fetchWeather(city) { data ->
                /*
                * ?.    安全调用操作符
                * let   作用域函数，用于在对象不为空时执行代码块。
                *   -- 把调用它的对象，作为参数（默认名字是 it）传递给后面的 Lambda 表达式。
                *   -- 提供一个局部作用域，在这个作用域内可以处理这个对象，并返回一个结果（Lambda 的最后一行）。
                * */
                data?.let { weatherData.postValue(it) }
                isRefreshing.postValue(false)
            }
        }
    }
}