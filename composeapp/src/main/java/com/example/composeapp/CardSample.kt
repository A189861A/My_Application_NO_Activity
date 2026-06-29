package com.example.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CardSample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                CardDemo()
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .background(Color.Blue)
                )
                CardDemo2()
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .background(Color.Blue)
                )
                VisibleDemo()
            }
        }
    }

    @Composable
    fun VisibleDemo() {
        var visible by remember { mutableStateOf(true) }
        Column {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { visible = !visible }
            ) {
                Text(if (visible) "隐藏" else "显示")
            }
            /*
            * 为组件的显示和隐藏添加过渡动画的核心 API
            * */
            AnimatedVisibility(
                visible = visible,
                /*
                * + 号 - 动画组合
                * 同一时间触发，并行执行，从而产生复合的视觉效果
                * */
                enter = fadeIn() + expandVertically(), // 入场动画
                exit = shrinkVertically() + fadeOut() // 出场动画
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        "带动画的卡片",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun CardDemo() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Red, // 正常背景（只能纯色，渐变需要外层 Box）
                contentColor = Color.Black, // 文字颜色
                disabledContainerColor = Color.Gray, // 禁用背景
                disabledContentColor = Color.White // 禁用文字颜色
            ),
            border = BorderStroke(1.dp, Color.Green)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                painter = painterResource(id = R.drawable.zoom),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Text(
                "New BMW 3",
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 22.sp,
                modifier = Modifier.padding(15.dp)
            )
        }
    }

    @Preview
    @Composable
    fun CardDemo2() {
        Box(
            Modifier
                .fillMaxWidth()
                .border(BorderStroke(1.dp, Color.Blue), RoundedCornerShape(12.dp))
                .height(150.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    /*
                    * Brush: （画刷）是用于描述如何填充区域颜色的核心类
                    * */
                    Brush.linearGradient(
                        listOf(Color.Blue, Color.Red),
                        start = Offset(0f, 0f),      // 可选：指定渐变起点
                        end = Offset(500f, 500f)     // 可选：指定渐变终点
                    )
                )
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = null,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "渐变卡片",
                    Modifier
                        .background(Color.Gray)
                        .padding(26.dp),
                    color = Color.White
                )
            }
        }
    }
}