package com.example.app4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app4.ui.theme.My_Application_NO_ActivityTheme
import com.example.app4.viewmodel.CountViewModel

/**
 * 使用 CountViewModel 的 Activity
 * 演示如何将 ViewModel 与 Jetpack Compose 结合使用
 */
class MainActivity9 : ComponentActivity() {

    /**
     * 使用 activity.viewModels() 创建 ViewModel 实例
     * 这确保了 Activity 生命周期内 ViewModel 保持同一个实例
     *
     * val: 只读属性，初始化后不能重新赋值
     * by viewModels() ： 使用属性委托创建 ViewModel
     * viewModels() 是 Kotlin 扩展函数，用于在 Activity 或 Fragment 中创建和获取 ViewModel 实例。
     *
     */
    private val viewModel: CountViewModel by viewModels() // 使用委托
    /*
    * 传统方式（不使用委托）
    * viewModel = ViewModelProvider(this).get(CountViewModel::class.java)
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_Application_NO_ActivityTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CounterScreen(
                        viewModel = viewModel,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

/**
 * 计数器界面组件
 * 接收 ViewModel 作为参数，直接调用 ViewModel 的方法来更新状态
 */
@Composable
fun CounterScreen(
    viewModel: CountViewModel,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 显示当前计数值
        Text(
            text = "Count: ${viewModel.count}",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )

        // 按钮行
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // 减少按钮
            Button(onClick = { viewModel.decrement() }) {
                Text("-")
            }

            // 重置按钮
            Button(onClick = { viewModel.reset() }) {
                Text("Reset")
            }

            // 增加按钮
            Button(onClick = { viewModel.increment() }) {
                Text("+")
            }
        }
    }
}
