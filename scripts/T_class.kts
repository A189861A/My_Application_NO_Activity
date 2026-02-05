package com.example.app2

class T_class {
}

/*
* 泛型类/接口
* 定义时在类名后加上<T>，T是类型参数，可以自定义
* */
class Box<T>(val content:T) {
    // 方法可使用类型参数 T
    fun _getContent():T {
        return content
    }
}
//val stringBox = Box<String>("Hello, World!");
//val intBox = Box<Int>(100);


/*
* 在函数名前加 <T>，声明该函数是泛型函数：
* */
fun <T> printList(list: List<T>) {
    list.forEach { println(it) }
}
// listOf("Apple", "Banana") // 传入 String 列表
// listOf(1, 2, 3)           // 传入 Int 列表


/*
*  泛型约束
*   通过 : 可以限定 T 的父类型（类似 Java 的 extends）
* */
fun <T : Number> sum(list: List<T>): Double {
    var total = 0.0
    list.forEach { total += it.toDouble() }
    return total
}
//sum(listOf(1, 2, 3))



/*
* <T>：泛型参数，代表任意类型；
* T.build, T.：扩展函数，给所有 T 类型加 build 方法
* block: T.() -> Unit：函数类型，表示接收一个 T 类型的对象，并返回 Unit（无返回值）
*       让 lambda 运行在 T 对象的上下文里，可直接操作对象的属性 / 方法
*       让传入的 lambda 变成「调用 build 的那个 T 对象的 “成员函数”」
*
    语法  	        含义	                            lambda 内指代对象的方式
    (T) -> Unit	    普通 lambda，接收一个 T 类型参数	用 it 指代
    T.() -> Unit	带接收者的 lambda，接收者是 T 类型	用 this 指代（可省略）
*
* */
fun <T> T.build(block: T.() -> Unit): T {
    block()
    return this // 实现链式调用，效果等价于 Kotlin 标准库的 apply 函数；
}


/*
* Lambda 基础语法
*   { 参数列表 -> 函数体 }
*
* */
// 1. 普通函数：计算两数之和
fun add(a: Int, b: Int): Int {
    return a + b
}

// 2. Lambda 表达式：和上面的 add 函数功能完全一致
val addLambda: (Int, Int) -> Int = { a, b -> a + b }

// 3. 调用
fun main() {
    println(add(1, 2)) // 输出：3
    println(addLambda(1, 2)) // 输出：3
}





































































