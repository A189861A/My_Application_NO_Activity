package com.example.my_application_no_activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity3 : AppCompatActivity() {
    private val data = listOf(
        "Apple",
        "Banana",
        "Orange",
        "Watermelon",
        "Pear",
        "Grape",
        "Pineapple",
        "Strawberry",
        "Cherry",
        "Mango",
        "Apple",
        "Banana",
        "Orange",
        "Watermelon",
        "Pear",
        "Grape",
        "Pineapple",
        "Strawberry",
        "Cherry",
        "Mango"
    );
    private val fruitList = ArrayList<Fruit>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main3);

        val listView = findViewById<android.widget.ListView>(R.id.listView);
//        集合中的数据是无法直接传递给ListView的
//        android.R.layout.simple_list_item_1作为ListView子项布局的id，这是一个
//        Android内置的布局文件，里面只有一个TextView
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
//        listView.adapter = adapter;

        initFruits(); // 初始化水果数据
//        item 布局中包含了可获得焦点的控件（如 Button、CheckBox 等），这些控件默认会拦截父容器的点击事件。
//        通过设置这些控件的 focusable 和 clickable 属性为 false 可以解决问题
        val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList);
        listView.adapter = adapter;

        listView.setOnItemClickListener { parent, view, position, id ->
            val fruit = fruitList[position];
            Log.d("--listItemClick--", "position: $position");
            Toast.makeText(this, "You clicked ${fruit.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initFruits() {
        repeat(20) {
            fruitList.add(Fruit("Apple", R.drawable.apple));
            fruitList.add(Fruit("Banana", R.drawable.banner));
            fruitList.add(Fruit("Orange", R.drawable.orange));
        }
    }
}