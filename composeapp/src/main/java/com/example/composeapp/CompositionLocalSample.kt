package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CompositionLocalSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    // 1. 创建 CompositionLocal
    val LocalUser = compositionLocalOf<User> { error("No user provided") }

    // 2. 提供值
    @Composable
    fun App() {
        val user = User("张三", "admin")
        CompositionLocalProvider(LocalUser provides user) {
            UserProfile()  // 不需要传递 user
        }
    }

    // 3. 使用值
    @Composable
    fun UserProfile() {
        val user = LocalUser.current // 从 CompositionLocal 中获取值
        Text(
            text = "Hello, ${user.name}",
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp)
        )
    }

    /*
    *
    * */
    data class User(val name: String, val role: String)

}

/*
CompositionLocal 是一种隐式传参机制，允许在 Composable 树中向下传递数据，而不需要通过每一层的参数。
    就近覆盖规则(内层 Provider 会覆盖外层同类型 Local)

┌─────────────────────────────────────────────────────────────────┐
│                  CompositionLocal 原理                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   Composable 树                                                 │
│   ┌─────────────────────────────────────────────────────────┐  │
│   │ CompositionLocalProvider(LocalColor provides Color.Red) │  │
│   │ ┌─────────────────────────────────────────────────────┐ │  │
│   │ │  Child1()                                          │ │  │
│   │ │   ┌─────────────────────────────────────────────┐  │ │  │
│   │ │   │  Child2()                                  │  │ │  │
│   │ │   │   ┌─────────────────────────────────────┐  │  │ │  │
│   │ │   │   │  LocalColor.current  // 返回 Red   │  │  │ │  │
│   │ │   │   └─────────────────────────────────────┘  │  │ │  │
│   │ │   └─────────────────────────────────────────────┘  │ │  │
│   │ └─────────────────────────────────────────────────────┘ │  │
│   └─────────────────────────────────────────────────────────┘  │
│                                                                 │
│   查找规则：                                                      │
│   1. 从当前节点向上查找                                           │
│   2. 找到最近的 CompositionLocalProvider                          │
│   3. 返回对应的值                                                 │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘


函数	                            特点	                            适用场景
compositionLocalOf	            值变化时，只重组读取该值的组件	    频繁变化的值
staticCompositionLocalOf	    值变化时，重组整个子树	            很少变化的值
* */