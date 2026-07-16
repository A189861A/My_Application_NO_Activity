package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout

class StatusUpdateSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterScreen()
        }
    }

    // ✅ 正确：状态提升到父组件
    @Composable
    fun CounterScreen() {
        var counts by remember { mutableStateOf(List(3) { 0 }) }

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (column) = createRefs()

            Column(
                modifier = Modifier.constrainAs(column) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                counts.forEachIndexed { index, count ->
                    Counter(
                        count = count, onIncrement = {
                            /*
                            * toMutableList(): 创建一个原始集合的浅拷贝，并将其转换为一个可以增删改的动态列表。
                            * */
                            counts = counts.toMutableList().apply {
                                this[index]++
                            }
                        })
                }
            }
        }
    }

    @Composable
    fun Counter(
        count: Int,                    // 状态通过参数传入
        onIncrement: () -> Unit        // 事件通过回调传出
    ) {
        Button(onClick = onIncrement) {
            Text("Count: $count")
        }
    }
}
