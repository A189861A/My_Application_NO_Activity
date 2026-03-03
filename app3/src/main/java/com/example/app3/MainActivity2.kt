package com.example.app3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.app3.adapter.FruitAdapter
import com.example.app3.model.App3Fruit
import kotlin.concurrent.thread

class MainActivity2 : AppCompatActivity() {
    lateinit var swipeRefresh: SwipeRefreshLayout

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

        swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

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

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary) // 设置下拉刷新的进度条颜色
        //设置一个下拉刷新的监听器，当用户进行了下拉刷新操作时，就会回调到Lambda表达式当中
        swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
    }
    private fun initFruits() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    private fun refreshFruits(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                // 刷新数据后，调用notifyDataSetChanged()方法通知适配器数据发生了改变
                //告诉 RecyclerView：“数据源（List）里的内容变了（增、删、改），请重新绘制整个列表界面
                adapter.notifyDataSetChanged()
                swipeRefresh.isRefreshing = false
            }
        }
    }
}