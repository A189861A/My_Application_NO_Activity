package com.example.mylibrary

import android.content.pm.PackageManager
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
/*
* Fragment: （片段）是一种可以嵌入在 Activity 中的 UI 片段。
*   - 允许您创建可重用的 UI 组件，这些组件可以独立于 Activity 运行。
*   - 可以在运行时添加、移除、替换，从而实现 Activity 的动态界面效果。
*   - 可以与 Activity 共享数据和状态。
*   - Fragment 拥有独立的生命周期（依附于 Activity）
* */
class InvisibleFragment : Fragment() {
    // 可空的函数类型变量
    private var callback: ((Boolean, List<String>) -> Unit)? = null
    /*
    * registerForActivityResult: 处理从其他组件返回的结果.
    * 注册一个 ActivityResultLauncher启动器，用于处理 运行时 权限请求 的结果。
    * - ActivityResultContracts.RequestMultiplePermissions()：这是一个系统提供的合约，用于请求多个权限。
    * - 当权限请求结果返回时，会调用 lambda 表达式中的代码。
    * - permissions: Map<String, Boolean>：包含请求的权限名称和对应的授权结果（true 表示授权，false 表示拒绝）。
    * */
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        Log.d("InvisibleFragment", "$permissions")
        // 过滤出被拒绝的权限, 字符串列表，包含所有被拒绝的权限名称
        val deniedList = permissions.filter { !it.value }.keys.toList()
        Log.d("InvisibleFragment", "$deniedList")
        val allGranted = deniedList.isEmpty() // 布尔值，表示是否所有权限都被授予
        /*
        * - ?. ：Kotlin 的安全调用操作符，只有当 callback 不为 null 时才会执行后面的操作.
        * - let ：Kotlin 的作用域函数，将调用者（这里是 callback ）作为参数传递给 lambda 表达式.
        * - it ：lambda 表达式中的默认参数名，代表 callback 本身.
        **/
        callback?.let { it(allGranted, deniedList) }
    }
    /*
    * 启动运行时权限请求。
    * - cb: (Boolean, List<String>) -> Unit：权限请求结果的回调函数，参数为
            是否所有权限都被授权（true 表示授权，false 表示拒绝），以及拒绝的权限列表。
    * - vararg<可变参数> permissions: String：要请求的权限名称列表。
    * */
    fun requestNow(cb: (Boolean, List<String>) -> Unit, vararg permissions: String) {
        callback = cb
        /*
        * - requestPermissionLauncher 是通过 registerForActivityResult 注册的一个 ActivityResultLauncher 实例，
        *   用于处理权限请求的结果
        * - launch 方法会启动权限请求，并等待结果返回.
        * - 执行流程：调用 launch 后，系统会显示权限请求弹窗，用户选择“允许”或“拒绝”后，
        *   结果会回调到 registerForActivityResult 中定义的 lambda 表达式。
        **/
        requestPermissionLauncher.launch(
            /*
            * Kotlin 的 Array 构造函数签名为：
            * inline fun <reified T> Array(size: Int, init: (Int) -> T): Array<T>
            *
            * 将可变参数 permissions 转换为 String 数组，以便传递给 requestPermissionLauncher.launch 方法
            * it 关键字: 在 Kotlin 的 lambda 表达式中，如果没有显式声明参数名，默认参数名是 it。这里的 it 代表当前元素的索引值（从 0 开始）
            * - Array(permissions.size) { permissions[it] }：将权限数组转换为字符串数组，
            *    数组大小：permissions.size - 指定要创建的数组长度（与可变参数 permissions 的长度相同）。
            *    初始化 lambda：{ permissions[it] } - 用于为数组的每个元素赋值的函数。
            * 举例：
            *   kotlin写法：
            *     val arr = Array(5) { it * 2 }
            *   java写法：
                    int[] arr = new int[5];
                    for(int i=0; i<5; i++){
                        arr[i] = i * 2;
                    }
            * 结果：[0, 2, 4, 6, 8]
            *
            * */
            Array(permissions.size) { permissions[it] }
        )
    }
}