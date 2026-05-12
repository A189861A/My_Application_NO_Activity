package com.example.app4

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app4.ui.theme.My_Application_NO_ActivityTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_Application_NO_ActivityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        TextDemo()
                        ButtonDemo()
                        CardDemo()
                        TextFieldDemo()
                    }
                }
            }
        }

    }

    @Composable
    fun TextDemo() {
        Column(modifier = Modifier.padding(16.dp)) {
            // 基础文本
            Text("Hello World")

            // 样式设置
            Text(
                text = "Styled Text",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )

            // 多行文本
            Text(
                text = "这是一段很长的文本，可能会自动换行显示。",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Composable
    fun ButtonDemo() {
        val context = LocalContext.current
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 基础按钮
            Button(onClick = {
                Toast.makeText(context, "点击了基础按钮", Toast.LENGTH_SHORT).show()
            }) {
                Text("点击我")
            }

            // 带图标的按钮
            Button(onClick = {
                Toast.makeText(context, "点击了添加按钮", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Add, contentDescription = "添加")
                Spacer(modifier = Modifier.width(8.dp))
                Text("添加")
            }

            // 描边按钮
            OutlinedButton(onClick = {
                Toast.makeText(context, "点击了描边按钮", Toast.LENGTH_SHORT).show()
            }) {
                Text("描边按钮")
            }

            // 文本按钮
            TextButton(onClick = {
                Toast.makeText(context, "点击了文本按钮", Toast.LENGTH_SHORT).show()
            }) {
                Text("文本按钮")
            }
        }
    }
    @Composable
    fun CardDemo() {
        // 卡片
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "卡片标题",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "这是卡片的内容描述文本。",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
    @Composable
    fun TextFieldDemo() {
        var text by remember { mutableStateOf("") }
        // 输入框
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("用户名") },
            placeholder = { Text("请输入用户名") },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}