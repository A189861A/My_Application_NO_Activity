package com.example.my_application_no_activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AnotherRightFragment : Fragment() {
    private val TAG = "AnotherRightFragment"
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }
    /*
    * 创建视图
    * @param inflater 布局加载器
    * @param container 容器视图
    * @param savedInstanceState 保存的实例状态
    * @return 视图
    * */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d(TAG, "onCreateView")
        // 使用 inflater 加载布局文件
        return inflater.inflate(R.layout.another_right_fragment, container, false)
    }
//    Fragment 的视图已经创建完成，并且与宿主 Activity 关联。
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }
    /*
    * Fragment 已经可见并且可以与用户交互时触发
    * 通常在这里启动动画、注册监听器或恢复需要实时运行的操作
    * */
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }
}