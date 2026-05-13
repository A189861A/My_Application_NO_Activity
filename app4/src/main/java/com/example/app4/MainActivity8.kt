package com.example.app4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.app4.ui.theme.My_Application_NO_ActivityTheme

class MainActivity8 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_Application_NO_ActivityTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CounterScreen(innerPadding)
                }
            }
        }
    }

    // 状态容器
    class CounterState {
        var count by mutableStateOf(0)
            /*
            * private set  是 Kotlin 中对属性的 setter 方法进行访问控制的修饰符。
            * 只能通过 increment/decrement 修改
            * */
            private set

        fun increment() {
            count++ // 类内部可以修改
        }

        fun decrement() {
            count--
        }
    }

    // 使用 remember 创建状态容器
    @Composable
    fun rememberCounterState() = remember { CounterState() }

    // 使用
    @Composable
    fun CounterScreen(innerPadding: androidx.compose.foundation.layout.PaddingValues) {
        val state = rememberCounterState()

        Counter(
            count = state.count,
            onIncrement = { state.increment() },
            modifier = Modifier.padding(innerPadding)
        )
    }

    @Composable
    fun Counter(
        count: Int,                    // 状态通过参数传入
        onIncrement: () -> Unit,       // 事件通过回调传出
        modifier: Modifier = Modifier  // 修饰符
    ) {
        Button(onClick = onIncrement, modifier = modifier) {
            Text("Count: $count")
        }
    }

}