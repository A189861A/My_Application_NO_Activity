package com.example.app3.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app3.FruitActivity
import com.example.app3.R
import com.example.app3.model.App3Fruit

/*
* <FruitAdapter.ViewHolder>：ViewHolder类是FruitAdapter的内部类，继承自RecyclerView.ViewHolder
* 作用：ViewHolder类用于缓存RecyclerView中每个项的视图，避免重复创建视图，提高性能.
*  . (点操作符):
*   - 访问当前项的属性，如 fruitList[position].name 和 fruitList[position].imageId.
*   - 这里表示“包含”的关系.
* 尖括号 <> 代表 泛型（Generics）
*
* RecyclerView 的核心机制是操作 ViewHolder。
* */
class FruitAdapter (val context: Context, private val fruitList: List<App3Fruit>) : RecyclerView.Adapter<FruitAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.fruitImage)
        val fruitName: TextView = view.findViewById(R.id.fruitName)
    }
    /*
    *
    *
    * 作用：创建ViewHolder实例，用于缓存项的视图.
    * 参数：parent：表示父容器，即RecyclerView。
    * 参数：viewType：表示项的类型，这里没有使用，因此可以忽略.
    * 返回值：返回一个ViewHolder实例，用于缓存项的视图.
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*
        * inflate：将布局文件转换为视图对象（把 XML 变成 View 对象）.
        * attachToRoot (是否挂载到根视图)：false
，      *    表示不将视图添加到父容器中，而是返回一个未添加的视图.这样可以避免重复添加视图，提高性能.
        * */
        val view = LayoutInflater.from(context).inflate(R.layout.fruit_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val fruit = fruitList[position]
            val intent = Intent(context, FruitActivity::class.java).apply {
                putExtra(FruitActivity.FRUIT_NAME, fruit.name)
                putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.imageId)
            }
            context.startActivity(intent)
        }
        return holder
    }
    /*
    * 从数据源中取出对应位置的数据，并把这个数据填充（设置）到 ViewHolder 所持有的控件（如 TextView, ImageView）中
    * 作用：将数据绑定到视图上.
    * 参数：holder：表示 ViewHolder 实例，用于缓存项的视图.
    * 参数：position：表示项的位置.
    * 返回值：无.
    * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitName.text = fruit.name
        /*
        * Glide ：图片加载库
        *  - 生命周期智能管理
        *      Glide 会根据传入的 Context 自动智能地管理图片的生命周期。比如，如果页面销毁了（Activity finish），Glide 会自动取消加载，防止内存泄漏。
        *  - 极高的性能（缓存机制）
        *  - 自动根据 ImageView 的大小（比如 100x100 像素）去解码图片
        * */
        Glide.with(context).load(fruit.imageId).into(holder.fruitImage)
    }
    override fun getItemCount() = fruitList.size
}