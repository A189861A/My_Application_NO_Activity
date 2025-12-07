package com.example.my_application_no_activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat

class MainActivity4 : AppCompatActivity() {
    private val fruitList = ArrayList<Fruit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main4)

        initFruits() //初始化水果数据
        /*
        * 创建布局管理器，并设置给 RecyclerView
        * LinearLayoutManager 是 RecyclerView 的默认布局管理器，它可以实现垂直和水平方向的线性布局
        * */
        val layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapterRecycler(fruitList)
        recyclerView.adapter = adapter


    }
    private fun initFruits() {
        repeat(20) {
            fruitList.add(Fruit("Apple", R.drawable.apple))
            fruitList.add(Fruit("Banana", R.drawable.banner))
            fruitList.add(Fruit("Orange", R.drawable.orange))
        }
    }
}