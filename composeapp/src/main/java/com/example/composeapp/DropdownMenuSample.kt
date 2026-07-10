package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


/*
* 副作用是指 Composable 函数对外部世界的影响，如：
    网络请求
    数据库操作
    订阅事件
    修改共享状态
*
* */

class DropdownMenuSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DropdownMenuDemo()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownMenuDemo() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("选项一", "选项二", "选项三")
    val scope = rememberCoroutineScope() // 在回调中启动协程
    /*
    * 在组合时启动协程
    * key 变化时，取消旧协程，启动新协程
    * 重组时不会重新启动
    * **/
    LaunchedEffect(expanded) {
        println("expanded: $expanded")
    }

    // 每次重组都执行
    SideEffect {
        println("SideEffect")
    }

    // 需要清理的副作用
    //    key 变化时，先执行 onDispose，再执行新的副作用
    //    退出组合时，执行 onDispose
    DisposableEffect(expanded) {
        println("DisposableEffect-1")
        onDispose {
            println("DisposableEffect-2")
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(Color.Gray)
            .border(1.dp, Color.Red)
            .padding(top = 44.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        // 用一个小 Box 包裹按钮和菜单，使菜单锚定在按钮位置
        Box(
            modifier = Modifier
                .border(1.dp, Color.Blue)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    // 在回调中启动协程
                    scope.launch {
                        println("Load ...")
                    }
                }) {
                    Text("Load ...")
                }
                // 触发按钮
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "更多"
                    )
                }
            }
            // DropdownMenu：下拉弹窗容器
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }, // 点击空白/选完关闭
                offset = DpOffset(x = 0.dp, y = 8.dp) // 菜单已紧贴按钮，无需额外偏移
            ) {
                items.forEach { item ->
                    // 下拉选项
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            println("选中 $item")
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
