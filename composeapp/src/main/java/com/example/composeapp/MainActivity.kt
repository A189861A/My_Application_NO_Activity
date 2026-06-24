package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard("Hello World")
        }
    }

    @Composable
    fun MessageCard(param: String) {
        Column(
            Modifier
                .padding(all = 8.dp)
                .clickable(onClick = {
                    println("Clicked")
                })
                .background(Color.Gray)
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

}