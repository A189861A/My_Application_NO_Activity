package com.example.app3.model

import androidx.lifecycle.ViewModel

/*
* countReserved参数，用于记录之前保存的计数值，并在初始化的时候赋值给counter变量。
* */
class Main4ViewModel(conuntReserved: Int) : ViewModel() {
    var counter = conuntReserved
}