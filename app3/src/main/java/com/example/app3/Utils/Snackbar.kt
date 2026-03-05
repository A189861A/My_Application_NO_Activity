package com.example.app3.Utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/*
* Snackbar的常规用法:
    Snackbar.make(view, "This is Snackbar", Snackbar.LENGTH_SHORT)
    .setAction("Action") {
        // 处理具体的逻辑
    }
    .show()
* */

/*由于make()方法接收一个View参数，Snackbar会使用这个View自动查找最外层的布局，用
于展示Snackbar。因此，我们就可以给View类添加一个扩展函数，并在里面封装显示
Snackbar的具体逻辑*/

fun View.showSnackbar(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, text, duration).show()
}
fun View.showSnackbar(resId: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, resId, duration).show()
}

fun View.showSnackbar(text: String, actionText: String? = null,
                      duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, text, duration)
    if (actionText != null && block != null) {
        snackbar.setAction(actionText) {
            block()
        }
    }
    snackbar.show()
}

fun View.showSnackbar(resId: Int, actionResId: Int? = null,
                      duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, resId, duration)
    if (actionResId != null && block != null) {
        snackbar.setAction(actionResId) {
            block()
        }
    }
    snackbar.show()
}


//view.showSnackbar("This is Snackbar", "Action") {
//// 处理具体的逻辑
//}



















