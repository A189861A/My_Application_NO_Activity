package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DerivedStateSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column() {
                LoginDemo()
                Spacer(Modifier.padding(10.dp))
                Parent()
            }
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

    API	                    作用	            使用场景
mutableStateOf	        单一可观察值	    布尔、字符串、数字、普通对象
mutableStateListOf	    可观察集合	    列表数据，增删自动刷新
mutableStateMapOf	    可观察键值对	    字典数据
rememberDerivedStateOf	计算派生状态	    多状态联动判断
rememberSaveable	    持久化 State	    横竖屏切换保留输入(普通 remember 屏幕旋转丢失数据)

    * remember 的作用就是在重组期间保持状态不被销毁，它可以将计算结果或对象“记住”，跨越重组而存在。
    * mutableStateOf：创建一个可变的、可观察的状态，当状态值发生变化时，会触发重组。
    *      -- 仅监听变量重新赋值，不会深度监听对象内部字段变更
    * */
    var name by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }

    /*
    * Compose 的 UI 是声明式的。当状态发生变化时，读取该状态的 Composable 函数会重新执行，这个过程就叫重组。
    * remember：在 Composable 函数的多次重组中，保持数据不被重新初始化。
    *       -- 组件首次执行创建 State；后续重组直接复用，不会重建
    *       -- 组件销毁（页面消失）remember 缓存自动清除
    *

    * */
    // 派生状态：只有name或pwd变化才重新计算
    val canLogin = remember {
        // derivedStateOf 会缓存计算结果，只有当 lambda 表达式的返回值与上一次不同时，才会触发重组。
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

@Preview(showBackground = true)
@Composable
fun Parent() {
//    状态放在父组件，子组件只接收只读 State + 回调，解耦复用
    var text by remember { mutableStateOf("Hello") }
    // 传递给子组件，只读 + 修改回调
    Child(text = text, onTextChange = { text = it })
}

@Composable
fun Child(text: String, onTextChange: (String) -> Unit) {
    Text(
        fontSize = 20.sp,
        color = Color.Red,
        text = text
    )
    // 修改回调，更新状态
    TextField(value = text, onValueChange = onTextChange)
}


























