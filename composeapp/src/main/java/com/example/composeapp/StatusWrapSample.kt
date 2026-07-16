package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StatusWrapSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                val (box) = createRefs()
                Box(
                    modifier = Modifier
                        .background(Color.Gray)
                        .height(160.dp)
                        .constrainAs(box) {
                            top.linkTo(parent.top, margin = 100.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints // 在约束条件的限制下，让组件尽可能占据所有可用的空间
                        },
                    contentAlignment = Alignment.Center

                ) {
                    CounterScreen()
                }
            }
        }
    }

    //对于复杂的状态，可以创建专门的状态容器类：
    class CounterState {
        var count by mutableStateOf(0)
            /*
            * private set: 允许外部读取属性值，但只允许类内部修改属性值
            * */
            private set // 单独将 Setter 的可见性降级为 private，而保持 Getter 的可见性不变

        fun increment() {
            count++
        }

        fun decrement() {
            count--
        }
    }

    // 使用 remember 创建状态容器
    @Composable
    fun rememberCounterState(): CounterState = remember { CounterState() }

    @Composable
    fun Counter(
        count: Int,                    // 状态通过参数传入
        onIncrement: () -> Unit,        // 事件通过回调传出
        onDecrement: () -> Unit        // 事件通过回调传出
    ) {
        Column(
            modifier = Modifier
//                .fillMaxSize()
                .background(Color.White),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Count: $count")
            Button(onClick = onIncrement) {
                Text("+", fontSize = 20.sp, color = Color.Red)
            }
            Button(onClick = onDecrement) {
                Text("-")
            }
        }
    }

    @Composable
    fun CounterScreen() {
        val state = rememberCounterState()
        Counter(
            count = state.count,
            onIncrement = state::increment,
            onDecrement = state::decrement
        )
    }
}

















