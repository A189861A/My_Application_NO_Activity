package com.example.app4

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app4.ui.theme.My_Application_NO_ActivityTheme

class MainActivity12 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_Application_NO_ActivityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .windowInsetsPadding(WindowInsets.safeDrawing)
                            .background(Color.LightGray),
                        verticalArrangement = Arrangement.Bottom
                    ) {
//                        InsetsDemo()
                        KeyboardAwareScreen()
                    }
                }
            }
        }
    }

    /*
    * Insets 表示系统 UI 占据的区域：
    * */
    @Composable
    fun InsetsDemo() {
        // 获取各种 Insets
        val statusBars = WindowInsets.statusBars // 状态栏
        val navigationBars = WindowInsets.navigationBars // 导航栏
        val ime = WindowInsets.ime //  软键盘高度
        val safeDrawing = WindowInsets.safeDrawing // 安全绘制区域

        Log.d("--InsetsDemo--", "safeDrawing: $safeDrawing")

        // 应用到布局
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(statusBars)
                .windowInsetsPadding(navigationBars)
                .background(Color.LightGray)
        ) {
            // 内容不会被系统栏遮挡
            Text("Hello, World!")
        }
    }

    @Composable
    fun KeyboardAwareScreen() {
        val imeInsets = WindowInsets.ime
        val isKeyboardVisible = imeInsets.getBottom(LocalDensity.current) > 0
        val text = remember { mutableStateOf("Hello, World!") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // 内容区域
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                // 列表项
            }

            // 输入框
            OutlinedTextField(
                value = text.value,
                onValueChange = { text.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.ime)
            )
        }
    }


}