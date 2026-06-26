package com.example.composeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
        val list = listOf("Android", "iOS", "HTML5", "Linux", "Kotlin")
        val onClick: (String) -> Unit = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        LazyColumn() {
            // 头部布局
            item {
                Image(
                    painter = painterResource(id = R.drawable.zoom),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
            }
            // 中间列表项布局
            items(list.size) { index ->
                ContactsItem(list[index], onClick)
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
