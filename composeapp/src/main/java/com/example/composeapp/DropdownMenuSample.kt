package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

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
            // 触发按钮
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "更多"
                )
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
