package com.example.app4

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app4.ui.theme.My_Application_NO_ActivityTheme

class MainActivity4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_Application_NO_ActivityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BoxDemo(innerPadding)
                }
            }
        }
    }

    @Composable
    fun BoxDemo(innerPadding: PaddingValues) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            // 底层
            CircularProgressIndicator(modifier = Modifier.size(58.dp))

            // 上层
            Text("加载中...")

            Text(
                text = "左上角",
                color = Color.Red,
                modifier = Modifier.align(Alignment.TopStart).background(Color.Yellow)
            )

            Text(
                text = "右上角角标",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color.Red, CircleShape)
//                    .padding(12.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ,
                color = Color.White,
                fontSize = 12.sp
            )

            Text(
                text = "左下角",
                color = Color.Red,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }

}