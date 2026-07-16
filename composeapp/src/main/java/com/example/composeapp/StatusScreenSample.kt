package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StatusScreenSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CounterScreen()
        }
    }
}

class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun increment() {
        _count.value++
    }

    fun decrement() {
        _count.value--
    }
}

@Composable
        /*
        *   参数名: 类型 = 默认值
            viewModel: CounterViewModel = viewModel()
        * */
fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
    /*
    * collectAsState() 是 Flow → Compose State 的转换桥梁：
    *   - 只能在 @Composable 函数内调用
    *   - 自动绑定生命周期，代码极简
    *   - 产出 State，直接驱动 UI 刷新
    *   - 适合：列表、文字、加载状态等界面展示数据
    *
    * by 委托省略 .value，简化写法
    * */
    // **推荐写法**，自动绑定生命周期，代码极简
//    val count by viewModel.count.collectAsState()

    // **冗余写法**，不如直接 collectAsState
    // 手动创建本地可变State，替代collectAsState自动生成的State
    var count by remember { mutableStateOf(0) }
    /*
    * LaunchedEffect 的功能用一句话概括就是：在 Composable 函数的安全环境中，
    * 启动一个协程来执行挂起函数或耗时操作，并自动管理其生命周期。*/
    LaunchedEffect(Unit) {
        // 收集ViewModel的StateFlow
        viewModel.count.collect { newValue ->
            // 收到新值，更新本地状态，UI刷新
            count = newValue
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Count: $count")
        Button(onClick = viewModel::increment) {
            Text("+")
        }
        Button(onClick = viewModel::decrement) {
            Text("-")
        }
    }
}

/*
┌─────────────────────────────────────────────────────────────────┐
│                    ViewModel 生命周期                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   Activity/Fragment 创建                                        │
│   ┌─────────────────────────────────────────────────────────┐  │
│   │ 1. ViewModel 创建                                       │  │
│   │ 2. 执行 init 块                                         │  │
│   │ 3. 开始收集数据                                          │  │
│   └─────────────────────────────────────────────────────────┘  │
│                              ↓                                  │
│   配置变更（旋转屏幕）                                           │
│   ┌─────────────────────────────────────────────────────────┐  │
│   │ 1. Activity 销毁                                        │  │
│   │ 2. ViewModel 保留（不销毁）                              │  │
│   │ 3. 新的 Activity 创建                                    │  │
│   │ 4. 获取相同的 ViewModel 实例                             │  │
│   │ 5. 状态保持不变                                          │  │
│   └─────────────────────────────────────────────────────────┘  │
│                              ↓                                  │
│   Activity/Fragment 真正销毁                                     │
│   ┌─────────────────────────────────────────────────────────┐  │
│   │ 1. onCleared() 被调用                                    │  │
│   │ 2. ViewModel 销毁                                        │  │
│   │ 3. 协程作用域取消                                        │  │
│   └─────────────────────────────────────────────────────────┘  │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘

* */