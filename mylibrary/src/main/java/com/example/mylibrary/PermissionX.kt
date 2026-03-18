package com.example.mylibrary

import androidx.fragment.app.FragmentActivity

/*
* object: 声明创建一个 单例对象 ，即全局唯一的实例.
特点：
1. 单例对象在第一次被访问时创建，后续访问直接返回该实例.
2. 无构造函数 ：不能定义构造函数（因为是单例），不能通过构造函数创建实例.
3. 单例对象的成员变量和方法可以直接通过类名访问，无需创建实例.
4. 自动实例化 ：无需手动创建实例，Kotlin 会在首次访问时自动初始化.

**/
object PermissionX {
    // 用于标识 InvisibleFragment 的标签, 避免重复创建，用于在 Fragment 管理器中查找该 Fragment.
    private const val TAG = "InvisibleFragment"
    // 静态方法，用于请求权限
    fun request(activity: FragmentActivity, vararg permissions: String, callback: (Boolean, List<String>) -> Unit) {
        /*
        * supportFragmentManager：获取 FragmentActivity 的 Fragment 管理器，
        *   - 用于管理  Activity 中的 Fragment 的添加、删除等操作。
        *   - 管理 Fragment 的生命周期和回退栈.
        *
        **/
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            /*
            * -beginTransaction() - 开启事务,事务保证了操作的原子性：要么全部成功，要么全部失败
            * -add() - 添加 Fragment 到事务中, 第一个参数是 Fragment 实例，第二个参数是 Fragment 的标签.
            * -commitNow() - 立即执行事务, 没有延迟.不会等待下一次 UI 刷新
            * -commit() 异步，加入消息队列，会在下次 UI 刷新时执行.
            * */
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment // ← 下一行就要使用这个 fragment，所以这里要返回它
        }
        /*
        * 在 commitNow() 后立即调用了 fragment.requestNow()，
        *   - 确保 Fragment 已经完全添加到 Activity 中.
        * *permissions 中的 * 是 Kotlin 的解包操作符（Spread Operator），用于将数组展开成可变参数
        * */
        fragment.requestNow(callback, *permissions)// ← 必须确保 fragment 已添加
    }
}