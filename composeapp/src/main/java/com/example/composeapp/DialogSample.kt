package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

class DialogSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()// 让 App 全屏绘制到系统栏后面
        setContent {
            Box(
                modifier =
                    // 给内容加上状态栏、导航栏等安全边距
                    Modifier.safeDrawingPadding()
            ) {
                Column {
                    SimpleDialogDemo()
                    FullScreenDialogDemo()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
//基础 AlertDialog 弹窗（提示框 / 确认弹窗）
fun SimpleDialogDemo() {
    var showDialog by remember { mutableStateOf(false) }

    Button(onClick = { showDialog = true }) {
        Text("显示弹窗")
    }
    /*
    * Dialog/AlertDialog 必须包裹在 if (show) 内部，控制生命周期；
    * 弹窗内部独立重组，状态建议在弹窗内部 remember；
    * */
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("标题") }, // 标题区域（可选）
            text = { Text("这是弹窗内容") }, // 正文内容
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { showDialog = false }) {
                        Text("取消")
                    }
                    Button(onClick = { showDialog = false }) {
                        Text("确定")
                    }
                }
            },
            dismissButton = {},
            shape = RoundedCornerShape(16.dp), // 圆角
        )
    }
}

@Preview(showBackground = true)
@Composable
// 全屏弹窗
fun FullScreenDialogDemo() {
    var showDialog by remember { mutableStateOf(false) }
    Button(onClick = { showDialog = true }) {
        Text("显示全屏弹窗")
    }
    if (showDialog) {
        FullScreenDialog(onDismissRequest = { showDialog = false }) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Column {
                    Text("这是全屏弹窗内容")
                    Button(onClick = { showDialog = false }) {
                        Text("关闭")
                    }
                }
            }
        }
    }
}

@Composable
fun FullScreenDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false, // 不使用平台默认宽度
            decorFitsSystemWindows = false // 允许内容延伸到系统栏下方
        )
    ) {
        // 自定义弹窗内容
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(56.dp))
        ) {
            content()
        }
    }
}



