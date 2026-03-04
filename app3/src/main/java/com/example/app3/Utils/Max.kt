
package com.example.app3.Utils

/**
 * 求多个整数的最大值
 * @param numbers 可变参数的整数
 * @return 最大值
 * @throws IllegalArgumentException 当没有参数时抛出
 */
fun max(vararg numbers: Int): Int {
    if (numbers.isEmpty()) {
        throw IllegalArgumentException("至少需要一个参数")
    }
    var maxValue = numbers[0]
    for (i in 1 until numbers.size) {
        if (numbers[i] > maxValue) {
            maxValue = numbers[i]
        }
    }
    return maxValue
}

/**
 * 求多个整数的最小值
 * @param numbers 可变参数的整数
 * @return 最小值
 * @throws IllegalArgumentException 当没有参数时抛出
 */
fun min(vararg numbers: Int): Int {
    if (numbers.isEmpty()) {
        throw IllegalArgumentException("至少需要一个参数")
    }
    var minValue = numbers[0]
    for (i in 1 until numbers.size) {
        if (numbers[i] < minValue) {
            minValue = numbers[i]
        }
    }
    return minValue
}

/**
 * 求多个浮点数的最大值
 * @param numbers 可变参数的浮点数
 * @return 最大值
 * @throws IllegalArgumentException 当没有参数时抛出
 */
fun max(vararg numbers: Double): Double {
    if (numbers.isEmpty()) {
        throw IllegalArgumentException("至少需要一个参数")
    }
    var maxValue = numbers[0]
    for (i in 1 until numbers.size) {
        if (numbers[i] > maxValue) {
            maxValue = numbers[i]
        }
    }
    return maxValue
}

/**
 * 求多个浮点数的最小值
 * @param numbers 可变参数的浮点数
 * @return 最小值
 * @throws IllegalArgumentException 当没有参数时抛出
 */
fun min(vararg numbers: Double): Double {
    if (numbers.isEmpty()) {
        throw IllegalArgumentException("至少需要一个参数")
    }
    var minValue = numbers[0]
    for (i in 1 until numbers.size) {
        if (numbers[i] < minValue) {
            minValue = numbers[i]
        }
    }
    return minValue
}


/*
* 泛型参数 <T : Comparable<T>>：
*  - T 是类型参数，表示这个函数可以接受任何类型
*  - : Comparable<T> 是类型约束，要求 T 必须实现 Comparable 接口。
*  - Comparable 接口确保类型 T 可以进行比较操作（如 >、< 等）
* vararg 表示可变参数，可以传入任意数量的 T 类型值
* */
fun <T : Comparable<T>> max(vararg nums: T): T {
    if (nums.isEmpty()) throw RuntimeException("Params can not be empty.")
    var maxNum = nums[0]
    for (num in nums) {
        if (num > maxNum) {
            maxNum = num
        }
    }
    return maxNum
}