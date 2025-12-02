package utils

import android.util.Log

//Kotlin编译器会将所有的顶层方法全部编译成静态方法;
//所有的顶层方法都可以在任何位置被直接调用,不用管包名路径,也不用创建实例;
fun doSomething() {
    Log.d("--doSomething--", "doSomething: ");
}