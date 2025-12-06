package com.example.my_application_no_activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    private val data = listOf("Apple", "Banana", "Orange", "Watermelon",
        "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
        "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
        "Pineapple", "Strawberry", "Cherry", "Mango");

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3);

        val listView = findViewById<android.widget.ListView>(R.id.listView);
//        集合中的数据是无法直接传递给ListView的
//        android.R.layout.simple_list_item_1作为ListView子项布局的id，这是一个
//        Android内置的布局文件，里面只有一个TextView
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        listView.adapter = adapter;


    }
}