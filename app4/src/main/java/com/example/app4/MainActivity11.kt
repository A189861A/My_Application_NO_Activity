package com.example.app4

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.unit.dp
import com.example.app4.ui.theme.My_Application_NO_ActivityTheme

class MainActivity11 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_Application_NO_ActivityTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "Hello, World!",
                            modifier = Modifier
                                .background(Color.Gray)
                        )
                        CustomCanvas();
                        TriangleShape();
                        CurvedLine();
                        GradientBackground();
//                        Spacer(
//                            modifier = Modifier
//                                .height(16.dp)
//                                .background(Color.DarkGray)
//                        )
                        RadialGradientCircle();
                    }

                }
            }
        }
    }

    @Composable
    fun CustomCanvas() {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
        ) {
            // 绘制矩形
            drawRect(
                color = Color.Blue,
                topLeft = Offset(0f, 0f),
                size = Size(100f, 100f)
            )

            // 绘制圆形
            drawCircle(
                color = Color.Red,
                radius = 50f,
                center = Offset(200f, 100f)
            )

            // 绘制线条
            /*
            *   1. 屏幕宽度 - 因为 Canvas 使用了 fillMaxWidth()，会占满父容器（Column，而
                    Column 又占满整个屏幕）的宽度
                2. 屏幕像素密度 - 值是以像素（px）为单位，不是 dp
            总结：size.width 表示当前 Canvas 画布的像素宽度，它是 DrawScope
            提供的属性，在运行时根据实际屏幕尺寸动态确定。
            * */
            drawLine(
                color = Color.Green,
                start = Offset(0f, 200f),
                end = Offset(size.width, 200f),
                strokeWidth = 5f
            )
        }
    }

    @Composable
    fun TriangleShape() {
        Canvas(
            modifier =
                Modifier
                    .size(100.dp)
                    .background(Color.Gray)
        ) {
            /*
            * size 是 DrawScope 提供的属性，表示当前 Canvas  画布的实际尺寸（以像素为单位）。
            * 计算方式：
              - 如果屏幕像素密度是 3 (xxhdpi)：100.dp → 300px，size.width = 300f
              - 如果屏幕像素密度是 2 (xhdpi)：100.dp → 200px，size.width = 200f
              - 如果屏幕像素密度是 1 (mdpi)：100.dp → 100px，size.width = 100f
            *
            * */
            val path = Path().apply {
                moveTo(size.width / 2, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            Log.d("TriangleShape", "Canvas size: width=${size.width}, height=${size.height}")

            drawPath(
                path = path,
                color = Color.Blue
            )
        }
    }

    @Composable
    fun CurvedLine() {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
        ) {
            val path = Path().apply {
                moveTo(0f, size.height / 2)

                // 二次贝塞尔曲线
                quadraticBezierTo(
                    size.width / 2, 0f,
                    size.width, size.height / 2
                )
            }

            drawPath(
                path = path,
                color = Color.Red,
                style = Stroke(width = 5f)
            )
        }
    }

    @Composable
    fun GradientBackground() {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val gradient = Brush.linearGradient(
                colors = listOf(Color.Blue, Color.Green, Color.Red),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            )

            drawRect(brush = gradient)
        }
    }

    /*
    *
      ┌───────────────────┬─────────────────────────────────┐
      │       属性        │              含义               │
      ├───────────────────┼─────────────────────────────────┤
      │ size.width        │ 画布像素宽度                    │
      ├───────────────────┼─────────────────────────────────┤
      │ size.height       │ 画布像素高度                    │
      ├───────────────────┼─────────────────────────────────┤
      │ size.minDimension │ 取 width 和 height 中较小的那个 │
      ├───────────────────┼─────────────────────────────────┤
      │ size.maxDimension │ 取 width 和 height 中较大的那个 │
      └───────────────────┴─────────────────────────────────
    * */
    @Composable
    fun RadialGradientCircle() {
        Canvas(modifier = Modifier.size(200.dp)) {
            val gradient = Brush.radialGradient(
                colors = listOf(Color.Yellow, Color.Green, Color.Red),
                center = center,
                radius = size.minDimension / 2
            )

            drawCircle(brush = gradient)
        }
    }

}

