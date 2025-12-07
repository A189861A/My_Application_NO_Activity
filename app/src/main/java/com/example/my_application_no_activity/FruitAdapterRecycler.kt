package com.example.my_application_no_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FruitAdapterRecycler(val fruitList: List<Fruit>) :
    RecyclerView.Adapter<FruitAdapterRecycler.ViewHolder>() {

        /*
        * ViewHolder 内部类，用于缓存视图控件*/
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fruitImage: ImageView = view.findViewById(R.id.fruit_image)
        val fruitName: TextView = view.findViewById(R.id.fruit_name)
    }
/*
* 创建ViewHolder实例，并将布局文件加载到ViewHolder中
* parent: 父视图组，用于正确的布局参数
* viewType: 当前列表项的类型（用于区分不同类型的列表项）
* 返回值: 对应位置的 ViewHolder 对象
* */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /*
        * LayoutInflater.from(parent.context) - 获取当前上下文的 布局解析器 实例
        * inflate(resourceId, parent, false) - 将指定的布局资源 ID 解析为 View 对象
        * resourceId: 要加载的布局资源（即 fruit_item.xml）
        * parent: 父视图组，用于正确的布局参数
        * false: 不立即附加到父视图
        * */
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_item_vertical, parent, false)
        return ViewHolder(view)
    }
    /*
    * 绑定数据，将数据填充到ViewHolder中
    * position: 当前列表项的索引位置（从0开始）
    * holder: 当前列表项的ViewHolder实例
    * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.fruitImage.setImageResource(fruit.imageId)
        holder.fruitName.text = fruit.name
    }
    /*
    * 返回数据项的数量*/
    override fun getItemCount() = fruitList.size
}