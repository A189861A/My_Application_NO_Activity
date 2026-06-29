package com.example.composeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LazyColumnDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnSample()
        }

    }

    @Composable
    fun LazyColumnSample() {
        /// 在可组合函数（Composable）中获取当前的 Android Context 上下文对象。
        val context = LocalContext.current
        val list = remember {
            mutableStateListOf(
                "Android",
                "iOS",
                "HTML5",
                "Linux",
                "Kotlin",
                "Android-2",
                "iOS-2",
                "HTML5-2",
                "Linux-2",
                "Kotlin-2",
                "Android-3", "iOS-3", "HTML5-3", "Linux-3", "Kotlin-3"
            )
        }
        val onClick: (String) -> Unit = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        LazyColumn {
            /*
            *   写法	                    作用	            参数
                item{}	                渲染1 个单独视图	无集合，单块 UI
                items(list){}	        遍历集合渲染多条	数据源集合
                items(count){}	        根据数字循环	    仅数量
                itemsIndexed(list){}	带下标遍历	    index + 数据
            * */
            // 头部布局
            item {
                Image(
                    painter = painterResource(id = R.drawable.zoom), // 指定画师去拿 zoom 这个资源
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            }
            // 中间列表项布局
            items(list.size, key = { index -> list[index] }) { index ->
                val item = list[index]
                SwipeToDismissItem(
                    item = item,
                    onClick = onClick,
                    onDismiss = { list.remove(item) }
                )
            }
            // 尾部布局
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("加载更多")
                }
            }
        }
    }

    @Composable
    fun SwipeToDismissItem(
        item: String,
        onClick: (String) -> Unit,
        onDismiss: () -> Unit
    ) {
        val state = rememberSwipeToDismissBoxState()
        LaunchedEffect(state.currentValue) {
            if (state.currentValue != SwipeToDismissBoxValue.Settled) {
                onDismiss()
                state.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                val color = when (state.dismissDirection) {
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.StartToEnd -> Color.Red
                    else -> Color.Transparent
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            },
            content = {
                ContactsItem(item, onClick)

            }
        )
    }

    @Composable
    fun ContactsItem(x0: String, x1: (String) -> Unit) {
        Text(
            text = x0,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { x1(x0) }
                .padding(16.dp)
        )
    }
}
