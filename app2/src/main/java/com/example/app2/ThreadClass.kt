package com.example.app2

import kotlin.concurrent.thread

class ThreadClass {
}
//线程的基本用法
/*
* 方式一：
* 新建一个类继承自Thread，然后重写父类的run()方法
* */
class MyThread : Thread() {
    override fun run() {
// 编写具体的逻辑
    }
}



/*
* 方式二：
* 使用实现Runnable接口的方式来定义一个线程
* */
class MyRunnable : Runnable {
    override fun run() {
        // 线程逻辑
    }
}

/*
* 方式三：
* 使用Lambda的方式
* */
fun createLambdaThread(): Thread {
    return Thread {
        // 编写具体的逻辑
        println("Lambda thread is running")
    }
}

// 或者直接创建并启动Lambda线程
//Thread(Runnable {
//    println("Direct lambda thread is running")
//}).start()

fun x() {
    /*
    * thread是一个Kotlin内置的顶层函数，我们只需要在Lambda表达式中编写具体的逻辑
    * 就可以了，连start()方法都不用调用，thread函数在内部帮我们全部都处理好了。
    * */
    thread {
        // 编写具体的逻辑
    }
}

fun main() {
    // 启动线程
    // 方式一
    MyThread().start()
    // 方式二
    val myRunnable = MyRunnable()
    Thread(myRunnable).start()
    // 方式三
    Thread {
        // 编写具体的逻辑
    }.start()


}
