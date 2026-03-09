package com.example.app3.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.ViewModel

//data class（数据类）来定义这种只保存数据的实体类，它会自动帮你生成 toString()、equals() 等常用方法。
// 定义 User 数据类
data class User(var firstName: String, var lastName: String, var age: Int) {
    // 如果需要额外的逻辑，可以在这里写函数
    fun isAdult(): Boolean {
        return age >= 18
    }
}

class LiveData_map(val user: User) : ViewModel() {
    var userLiveData = MutableLiveData<User>()
    /*
    * map()方法接收两个参数：
    *   第一个参数是原始的LiveData对象；
    *   第二个参数是一个转换函数，我们在转换函数里编写具体的转换逻辑即可。
    * */
    val userName: LiveData<String> = userLiveData.map {
        "${it.firstName} ${it.lastName}"
    }
}


