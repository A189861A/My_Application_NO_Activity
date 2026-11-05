package com.example.my_application_no_activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        // 将一个 XML 布局文件转换为实际的 View 对象
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_item_vertical, parent, false)
        /*
        * ViewHolder(view) 是 RecyclerView.ViewHolder 的构造函数调用，用于创建一个 ViewHolder 实例，
        * 并缓存列表项中的子视图引用
        * */
        val viewHolder = ViewHolder(view)
        /*
        * viewHolder.itemView 是 RecyclerView.ViewHolder 类的一个属性，它代表当前 ViewHolder 所持有的根布局视图.
        * itemView 是你在 onCreateViewHolder 方法中通过 inflate 加载的列表项（Item）的根布局视图。
        * 它是整个列表项视图的容器，所有的子视图（如 TextView、ImageView 等）都是它的子视图。
        * */
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            Toast.makeText(
                parent.context, "you clicked view ${fruit.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
        viewHolder.fruitImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]
            Toast.makeText(
                parent.context, "you clicked image ${fruit.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
        return viewHolder

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