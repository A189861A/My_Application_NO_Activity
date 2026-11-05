package com.example.my_application_no_activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

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
        * StaggeredGridLayoutManager：瀑布流布局管理器，是 RecyclerView 的三大内置布局管理器之一
        *   （另外两个是 LinearLayoutManager 和 GridLayoutManager）.
        * 参数：
           - 将子项（Item）排列成一个网格。
           - 每一行的 Item 数量固定（由 spanCount 决定）。
           - 每一列的高度可以不一样，形成错落有致的视觉效果
        * */
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        /*
        * 优化：防止瀑布流自动补空白导致右侧偏移
        * */
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        // 强制 StaggeredGridLayoutManager 重新计算所有 Item 的位置，确保它们正确排列。
        layoutManager.invalidateSpanAssignments()

        val recyclerView =
            findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        /*
        * 设置布局管理器的方向为水平方向
        * */
        recyclerView.layoutManager = layoutManager
        /*
        * 创建适配器，并设置给 RecyclerView
        * FruitAdapterRecycler 是自定义的适配器，用于将数据绑定到 RecyclerView中
        * */
        val adapter = FruitAdapterRecycler(fruitList)
        recyclerView.adapter = adapter


    }

    private fun initFruits() {
        repeat(20) {
            fruitList.add(Fruit(getRandomLengthString("Apple"), R.drawable.apple))
            fruitList.add(Fruit(getRandomLengthString("Banana"), R.drawable.banner))
            fruitList.add(Fruit(getRandomLengthString("Orange"), R.drawable.orange))
        }
    }

    private fun getRandomLengthString(str: String): String {
//        val n = (1..20).random()
//        val builder = StringBuilder()
//        repeat(n) {
//            builder.append(str)
//        }
//        return builder.toString()
        return str;
    }
}