package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class ScaffoldSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScaffoldDemo()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldDemo() {
    // 创建并记住抽屉的开关状态，让你能用代码控制抽屉滑出/收回
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    /*
    * rememberCoroutineScope： 在 Composable 函数内部创建一个协程作用域，
    * 让你能够在 Compose 的声明式UI中，安全地执行异步操作（如网络请求、动画、延迟等）。
    * */
    val scope = rememberCoroutineScope()
    /*
    * ModalNavigationDrawer： 从屏幕边缘滑出的抽屉式导航面板
    * */
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "抽屉菜单",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp
                )
                NavigationDrawerItem(
                    label = { Text("菜单项 1") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("菜单项 2") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(
            // 顶部栏
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("页面标题") },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "打开抽屉"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFADD8E6) // 浅蓝色背景
                    )
                )
            },
            // 底部栏
            bottomBar = { BottomAppBar { Text("底部导航") } },
            // 右下角浮动按钮
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            },
            // 浮动按钮 FAB（控制悬浮按钮在屏幕底部的水平对齐位置）
            floatingActionButtonPosition = FabPosition.End,
            // 内容区域
            content = { innerPadding ->
                // innerPadding 是系统自动算出的安全边距，必须给内容使用
                Box(
                    modifier = Modifier
                        .background(Color.Gray)
                        .padding(innerPadding)
                        .border(BorderStroke(1.dp, Color.Black))
                ) {
                    Text("页面主体内容")
                }
            }
        )
    }
}
