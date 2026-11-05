package com.example.my_application_no_activity

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class FruitAdapter(activity: Activity, val resourceId: Int, val data: List<Fruit>) :
    ArrayAdapter<Fruit>(activity, resourceId, data) {

    /*
    * 没有大括号的类（无类体）：
    *   这个类只包含主构造函数中声明的属性，没有任何额外的属性、方法或初始化逻辑。它只是一个纯粹的“数据容器”。
    * 等价代码：
        inner class ViewHolder(val fruitImage: ImageView, val fruitName: TextView) {
            // 这里是空的，什么都没有
        }
        // 或者更详细的等价写法：
        inner class ViewHolder(val fruitImage: ImageView, val fruit用户名: TextView) {
            // 显式声明属性并初始化
            val fruitImage: ImageView = fruitImage
            val fruitName: TextView = fruitName
        }
        等价于
        ```kotlin```
        * 如果构造函数 参数 被 val 或 var 修饰，Kotlin 会自动在类内部生成同名的成员属性，并自动赋值。
        * 如果构造函数 参数 没有 val 或 var 修饰，则它们只是普通的构造函数参数，不会成为类成员属性。
        inner class ViewHolder(fruitImage: ImageView, fruitName: TextView) {
            val fruitImage: ImageView = fruitImage
            val fruitName: TextView = fruitFragment
        }

    * */
    inner class ViewHolder(val fruitImage: ImageView, val fruitName: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        /*
            LayoutInflater.from(context) - 获取当前上下文的 布局解析器 实例
            inflate(resourceId, parent, false) - 将指定的布局资源 ID 解析为 View 对象
            resourceId: 要加载的布局资源（即 fruit_item.xml）
            parent: 父视图组，用于正确的布局参数
            false: 不立即附加到父视图*/

        val view: View;
        val viewHolder: ViewHolder

//        convertView参数，这个参数用于将之前加载好的布局进行缓存，以便之后进行重用
        if (convertView == null) { // 如果 convertView 为空，则创建新的视图
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            val fruitImage = view.findViewById<ImageView>(R.id.fruit_image);
            val fruitName = view.findViewById<TextView>(R.id.fruit_name);

            viewHolder = ViewHolder(fruitImage, fruitName)
            view.tag = viewHolder

        } else { // 如果 convertView 不为空，则重用已有的视图
            // 如果有缓存视图，直接从 tag 中取出 ViewHolder，避免了 findViewById
            view = convertView
            viewHolder = view.tag as ViewHolder
        }


        //  数据获取: 从数据源（即构造函数中的 data 列表）中获取指定位置的 Fruit 对象
        //  position: 当前列表项的索引位置（从0开始）
        //  返回值: 对应位置的 Fruit 对象
        /*
        * getItem：它是 Adapter 的“数据提取器”，专门用来根据位置索引，从你的数据源列表中取出对应的那条数据。
        *
        * 当 ListView 滑动，需要渲染第 5 行（position = 4）的 UI 时，你的 getView() 方法会被调用。
        * 此时你需要知道第 5 行该填什么数据，于是你调用 getItem(4)，
        * Adapter 就会乖乖地把数据列表里第 5 个 Fruit 对象交给你。
        * */
        val fruit = getItem(position); //  获取当前项的Fruit实例
        Log.d("--fruit-position--", "fruit: $position")
        // 把取出来的数据塞给 UI 控件
        if (fruit != null) {
            viewHolder.fruitImage.setImageResource(fruit.imageId);
            viewHolder.fruitName.text = fruit.name;
        }
        return view;
    }

}