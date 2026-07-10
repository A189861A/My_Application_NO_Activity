package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class DerivedStateSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginDemo()
        }
    }
}

/*
derivedStateOf {}
    普通可观察派生状态，一般用在 ViewModel / 非 Composable 作用域，返回 State<T>
rememberDerivedStateOf {}
    Compose 组件内专用，包裹 remember，重组不会重建计算逻辑，返回 State<T>
* */
@Preview
@Composable
fun LoginDemo() {
    /*
        Composable 函数在 Compose 中是“无状态”且“随时可能被重新执行”的。
        当 UI 的状态发生改变（如文本更新、动画进行）或者配置发生变化时，
        Compose 会重新执行（即重组）相关的 Composable 函数。

    * remember 的作用就是在重组期间保持状态不被销毁，它可以将计算结果或对象“记住”，跨越重组而存在。
    * */
    var name by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }

    // 派生状态：只有name或pwd变化才重新计算
    val canLogin = remember {
        derivedStateOf {
            name.isNotBlank() && pwd.length >= 6
        }
    }

    Column(Modifier.padding(20.dp)) {
        TextField(value = name, onValueChange = { name = it }, label = { Text("账号") })
        TextField(value = pwd, onValueChange = { pwd = it }, label = { Text("密码") })

        Button(
            onClick = {},
            enabled = canLogin.value // 读取派生状态值
        ) {
            Text("登录")
        }
    }
}