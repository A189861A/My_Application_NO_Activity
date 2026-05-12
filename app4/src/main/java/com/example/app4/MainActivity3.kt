package com.example.app4

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app4.ui.theme.My_Application_NO_ActivityTheme

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_Application_NO_ActivityTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    ColumnDemo(modifier = Modifier.weight(0.5f))
                    RowDemo(modifier = Modifier.weight(0.5f))
                }
            }
        }
    }

    @Composable
    fun ColumnDemo(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.LightGray),
            verticalArrangement = Arrangement.Center,  // 垂直居中
            horizontalAlignment = Alignment.CenterHorizontally  // 水平居中
        ) {
            Text("第一行")
            Text("第二行")
            Text("第三行")
        }
    }
    @Composable
    fun RowDemo(modifier: Modifier = Modifier) {
        Row(
            modifier = modifier.fillMaxWidth().background(Color.Blue),
            horizontalArrangement = Arrangement.SpaceEvenly,  // 均匀分布
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("左侧")
            Text("中间")
            Text("右侧")
        }
    }
}