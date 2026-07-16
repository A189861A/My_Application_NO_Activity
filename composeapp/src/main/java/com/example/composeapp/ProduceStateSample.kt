package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class ProduceStateSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .padding(46.dp)
                    .background(Color.Gray)
            ) {
                UserInfoPage(1)
            }
        }
    }


    /*
    * produceState:
    *   - 是 remember 的异步版本
    *   - 启动协程异步，加载数据，把协程结果转换成 Compose State<T>
    *   - 参数
    *       - initialValue: 初始值
    *       - vararg: 依赖key，key变化会重新执行生产逻辑（自动重启协程，非常适合依赖参数变化的请求）
    *       - producer: 协程生产者，返回一个 suspend 函数
    *   - 返回值
    *       - State<T> (内部自带协程作用域，组件销毁自动取消协程，无内存泄漏)
    * */

    @Composable
    fun UserInfoPage(userId: Int) {
        // userId 作为 key，id一变自动重新请求
        val userState = produceState<Result>(initialValue = Result.Loading, userId) {
            // 挂起函数，异步逻辑
            val user = getUserByIdWithDelay(userId)
            // 给state赋值 = 更新UI
            /*
            * value 是 produceState 的协程作用域暴露出来的状态写入接口；
            * 在里面给 value 赋值，就是更新 Compose 的 State，从而触发对应的 UI 刷新。
            * value 只能赋值一次，多次赋值会抛出异常。
            * */
            value = Result.Success(user) // 在 Lambda 内部通过 value = ... 赋值触发重组
        }
        /*
        *
        * */
        when (val res = userState.value) {
            is Result.Loading -> Text("加载中...")
            is Result.Success -> Text("用户名：${res.data.name}")
            is Result.Error -> Text("失败：${res.msg}")
        }
    }

    /**
     * 延迟函数：模拟网络请求延迟后获取用户数据
     */
    private suspend fun getUserByIdWithDelay(userId: Int): User {
        delay(10000) // 模拟 2 秒网络延迟
        return User("张三") // 模拟获取用户数据
    }

    data class User(val name: String = "")

    /*
    * sealed：（密封）
    *
    * sealed class 的核心作用是：限制类的继承结构，确保子类类型是有限且确定的。
    *   - 密封类本身是抽象的，你不能直接实例化它。
    *   - 密封类不允许有非-private 构造函数（其构造函数默认为 private）。
    *   - 所有直接子类必须定义在同一个文件中
    *
    *
    * ┌────────────────┬───────────────────┬───────────────────────────────────┐
      │      写法       │       名称         │               含义                │
      ├────────────────┼───────────────────┼───────────────────────────────────┤
      │ class A {      │ 嵌套类（nested      │ 相当于 Java 的 static class       │
      │ class B {} }   │ class）           │ B，不持有外部类引用               │
      ├────────────────┼───────────────────┼───────────────────────────────────┤
      │ class A {      │ 内部类（inner      │ 必须依附外部类实例，持有 A.this   │
      │ inner class    │ class）           │ 引用                              │
      │ B() }          │                   │                                   │
      ├────────────────┼───────────────────┼───────────────────────────────────┤
      │ class A {      │ 嵌套单例            │ 静态单例，全局唯一                │
      │ object B }     │                   │                                   │
      └────────────────┴───────────────────┴───────────────────────────────────┘

    * */
//密封类
    sealed class Result {
        // object： Kotlin 单例类(整个程序里只有一个 Loading 实例)，会自动生成伴生对象
        //  Loading 是 Result 内部的 object 单例
        object Loading : Result() //  嵌套 object
        data class Success(val data: User) : Result()

        /*
        *   data class： Kotlin 数据类，会自动生成copy()、equals()、hashCode()、toString() 等方法
        *   Error:  子类名，表示“错误/失败”状态
        * */
        data class Error(val msg: String) : Result() // 嵌套 data class
    }


}













