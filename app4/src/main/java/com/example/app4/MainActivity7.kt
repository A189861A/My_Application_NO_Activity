package com.example.app4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app4.ui.theme.My_Application_NO_ActivityTheme

class MainActivity7 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_Application_NO_ActivityTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) { innerPadding ->
                    // 主内容区域
                    CounterScreen(innerPadding)
                }
            }
        }
    }

    @Composable
    fun CounterScreen(innerPadding: PaddingValues) {
        /*
        * mutableStateOf: 创建一个可变的 State 对象，用于存储计数器的值。
        * 当状态发生变化时，Compose会自动重新渲染使用该状态的 UI 组件。
        * */
        // 状态提升到父组件
        var counts by remember { mutableStateOf(List(3) { 0 }) }

        Column(modifier = Modifier.padding(innerPadding)) {
            counts.forEachIndexed { index, count ->
                Counter(
                    count = count,
                    onIncrement = {
                        /*
                        * toMutableList() 创建一个可变的 MutableList 副本
                        * */
                        counts = counts.toMutableList().apply {
                            this[index]++
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun Counter(
        count: Int,                    // 状态通过参数传入
        onIncrement: () -> Unit        // 事件通过回调传出
    ) {
        Button(onClick = onIncrement) {
            Text("Count: $count")
        }
    }
}