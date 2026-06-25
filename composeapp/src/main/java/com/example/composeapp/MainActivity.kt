package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
            ) {
                MessageCard("Hello World")
                ButtonDemo()
                SwitchDemo()
                //  水平分割线
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                RadioDemo()
                BoxDemo()
                TextFieldDemo()
            }
        }
    }

    @Composable
    fun MessageCard(param: String) {
        Row(
            Modifier
                .background(Color.Gray)
                .border(width = 1.dp, color = Color.Black)
                .padding(all = 20.dp)
                .clickable(onClick = {
                    println("Clicked")
                })
        ) {
            Text(
                text = param,
                fontSize = 20.sp,
                style = TextStyle(
                    color = Color.Green
                )
            )
            Text(
                text = param,
                style = TextStyle(
                    color = Color.Blue
                )
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewMessageCard() {
        MessageCard(param = "Hello World")
    }

    @Preview(showBackground = true)
    @Composable
    fun ButtonDemo() {
        Column {
            Button(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Blue), // 如果直接用 background，必须搭配 clip 裁剪圆角，否则圆角会溢出
                shape = RoundedCornerShape(30.dp), //圆角
                onClick = {
                    println("button clicked")
                },
            ) {
                Text(
                    text = "我是按钮 button",
                    style = TextStyle(
                        color = Color.White,
                        background = Color.Cyan,
                        fontSize = 20.sp,

                        )
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                // 核心配置背景色
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2563EB), // 正常背景
                    contentColor = Color.White,         // 文字颜色
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("自定义蓝色按钮")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SwitchDemo() {
        var checked by rememberSaveable { mutableStateOf(true) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { checked = !checked },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "通知开关",
                style = TextStyle(fontSize = 16.sp)
            )
            Spacer(
                modifier =
                    Modifier
                        .width(8.dp)
                        .height(16.dp)
                        .background(Color.Blue)
            )
            Text(
                text = if (checked) "开启" else "关闭",
                modifier = Modifier.weight(1f),
                style = TextStyle(
                    color = if (checked) Color.Green else Color.Gray,
                    fontSize = 16.sp
                )
            )
            Switch(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color.Green,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }
    }

    @Composable
    fun RadioDemo() {
        val labels = listOf("A", "B", "C")
        var selectedLabel by rememberSaveable { mutableStateOf(labels.first()) }
        Row {
            labels.forEach { label ->
                val checked = label == selectedLabel
                println(selectedLabel)
                Text(
                    text = label,
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(
                        color = if (checked) Color.Green else Color.Gray,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = checked,
                    onClick = { selectedLabel = label }
                )
            }
        }
    }

    @Preview
    @Composable
    fun BoxDemo() {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .background(Color.Magenta)
                    .align(Alignment.TopStart)
            )
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .background(Color.Blue)
                    .align(Alignment.Center)
            )
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .background(Color.Yellow)
                    .align(Alignment.BottomEnd)
            ) { }
        }
    }

    @Preview
    @Composable
    fun TextFieldDemo() {
        var text by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = {
                Text("input password")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(
                    imageVector = image,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                )
            }
        )
    }

}















