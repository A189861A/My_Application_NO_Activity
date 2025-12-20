package com.example.my_application_no_activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AnotherRightFragment : Fragment() {
    /*
    * 创建视图
    * @param inflater 布局加载器
    * @param container 容器视图
    * @param savedInstanceState 保存的实例状态
    * @return 视图
    * */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // 使用 inflater 加载布局文件
        return inflater.inflate(R.layout.another_right_fragment, container, false)
    }
}