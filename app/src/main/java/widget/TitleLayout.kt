//在添加自定义控件的时候，我们需要指明控件的完整类名，包名在这里是不可以省略的。
package com.example.my_application_no_activity.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.my_application_no_activity.R
import android.view.View

class TitleLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    init {
        //加载布局文件
//        通过LayoutInflater的from()方法可以构建出一个LayoutInflater对象
//        ，然后调用inflate()方法就可以动态加载一个布局文件
        LayoutInflater.from(context).inflate(R.layout.title, this);

        val backButton = findViewById<Button>(R.id.titleBack);
        val editButton = findViewById<Button>(R.id.titleEdit);

        backButton.setOnClickListener {
            Log.d("--TitleLayout--", "backButton: ");
        }
        editButton.setOnClickListener {
            Toast.makeText(context, "editButton", Toast.LENGTH_SHORT).show()
        }

    }
}