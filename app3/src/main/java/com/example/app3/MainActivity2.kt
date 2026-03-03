package com.example.app3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.app3.adapter.FruitAdapter
import com.example.app3.model.App3Fruit

class MainActivity2 : AppCompatActivity() {
    val fruits = mutableListOf(
        App3Fruit("Apple", R.drawable.apple),
        App3Fruit("Banana", R.drawable.banana),
        App3Fruit("Orange", R.drawable.orange),
        App3Fruit("Watermelon", R.drawable.watermelon),
        App3Fruit("Pear", R.drawable.pear),
        App3Fruit("Grape", R.drawable.grape),
        App3Fruit("Pineapple", R.drawable.pineapple),
        App3Fruit("Strawberry", R.drawable.strawberry),
        App3Fruit("Cherry", R.drawable.cherry),
        App3Fruit("Mango", R.drawable.mango)
    )
    val fruitList = ArrayList<App3Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        initFruits()
        /*
        * GridLayoutManager：创建一个网格布局管理器
        * 第一个参数：上下文
        * 第二个参数：列数
        * */
        val layoutManager = GridLayoutManager(this, 2)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruitList)
        recyclerView.adapter = adapter
    }
    private fun initFruits() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }
}