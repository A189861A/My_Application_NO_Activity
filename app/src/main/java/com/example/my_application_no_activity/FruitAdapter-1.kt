package com.example.my_application_no_activity

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class `FruitAdapter-1`(activity: Activity, val resourceId: Int, val data: List<Fruit>) : ArrayAdapter<Fruit>(activity, resourceId, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    /*    LayoutInflater.from(context) - 获取当前上下文的 布局解析器 实例
        inflate(resourceId, parent, false) - 将指定的布局资源 ID 解析为 View 对象
        resourceId: 要加载的布局资源（即 fruit_item.xml）
        parent: 父视图组，用于正确的布局参数
        false: 不立即附加到父视图*/
        val view = LayoutInflater.from(context).inflate(resourceId, parent, false);
        val fruitImage = view.findViewById<ImageView>(R.id.fruit_image);
        val fruitName = view.findViewById<TextView>(R.id.fruit_name);

//        数据获取: 从数据源（即构造函数中的 data 列表）中获取指定位置的 Fruit 对象
//        position: 当前列表项的索引位置（从0开始）
//        返回值: 对应位置的 Fruit 对象
        val fruit = getItem(position); //  获取当前项的Fruit实例
        if(fruit != null) {
            fruitImage.setImageResource(fruit.imageId);
            fruitName.text = fruit.name;
        }
        return view;
    }

}