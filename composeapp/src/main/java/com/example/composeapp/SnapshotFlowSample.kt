package com.example.composeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class SnapshotFlowSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(16.dp, 36.dp)
            ) {
                SearchDemo()
                Spacer(modifier = Modifier.height(16.dp))
                SearchDemo2()
                Spacer(modifier = Modifier.height(16.dp))
                SearchDemo3()
                Spacer(modifier = Modifier.height(16.dp))
                LoginPage()
            }
        }
    }

    @Preview
    @Composable
    fun SearchDemo() {
        var keyword by remember { mutableStateOf("") }
        /*
    * LaunchedEffect 是 Compose 专用 API，在重组树内安全启动协程，
    * 替代手动 rememberCoroutineScope().launch。
    * */
        LaunchedEffect(Unit) {
            /*
            * block 里读取哪些 State，就监听哪些
            *
            * snapshotFlow 可以把 Compose State（MutableState/State） 转换成 Kotlin Flow。
              监听 State 的 .value 变化，每次更新自动发射新值，桥接 Compose 状态与协程 Flow 体系。
            * */
            snapshotFlow { keyword }
                .debounce(300) // 输入停300ms才触发
                .distinctUntilChanged() // 相同值不重复发送
                .collect { text -> // 收到新值后执行（text 只是一个参数名，指的是当前流发射出来的字符串值）
                    // 执行网络搜索
                    println("请求搜索：$text")
                }
        }

        TextField(value = keyword, onValueChange = { keyword = it })
    }

    //  多 State 合并监听
    @Preview
    @Composable
    fun SearchDemo2() {
        var name by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            snapshotFlow {
                // 同时依赖两个State
                Pair(name, age)
            }
                .debounce(300) // 输入停300ms才触发
                .filter { it.first.isNotBlank() && it.second.isNotBlank() }
                .collect { (n, a) ->
                    println("姓名:$n 年龄:$a")
                }
        }

        TextField(value = name, onValueChange = { name = it })
        TextField(value = age, onValueChange = { age = it })
    }

    @Preview
    @Composable
    fun SearchDemo3() {
        var key by remember { mutableStateOf("") }

        val resultState = produceState(emptyList<String>(), Unit) {
            snapshotFlow { key }
                .debounce(300)
                .distinctUntilChanged()
                .filter { it.isNotEmpty() }   // 过滤空值
                .map { listOf("${it}-x-") }
                .collect { result ->
                    value = value + result
                }
        }
        Log.d("--resultState--", resultState.value.toString())
        TextField(value = key, onValueChange = { key = it })
        Text(text = resultState.value.joinToString())
    }

    @Preview
    @Composable
    fun LoginPage() {
        var account by remember { mutableStateOf("") }
        var pwd by remember { mutableStateOf("") }

        /*
        * - derivedStateOf { ... }：创建一个派生State，内部会自动缓存结果，只有依赖变化时才重新计算。
          - remember { ... }：在重组时保持这个 State 实例，避免每次重组都新建。
        * */
        // 派生状态：仅account/pwd改变时才执行判断
        val canSubmit = remember {
            derivedStateOf {
                account.isNotBlank() && pwd.length >= 6
            }
        }

        Column(Modifier.padding(20.dp)) {
            TextField(value = account, onValueChange = { account = it }, label = { Text("账号") })
            TextField(value = pwd, onValueChange = { pwd = it }, label = { Text("密码") })

            Button(
                onClick = {},
                enabled = canSubmit.value
            ) {
                Text("登录")
            }
        }
    }
}