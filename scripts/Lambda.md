
# Lambda 核心定义
    - 没有函数名，但有参数、函数体、返回值，本质是把 “一段逻辑” 当作参数传递

# Lambda 基础语法
## 语法结构 { 参数列表 -> 函数体 }
 * {}：必须用大括号包裹整个 Lambda；
 * 参数列表：比如 a: Int, b: Int（参数类型可省略，Kotlin 会自动推导）；
 * ->：分隔参数和函数体，不可省略；
 * 函数体：执行的逻辑，最后一行代码的结果就是 Lambda 的返回值（无需写 return）

## 基础示例（对比普通函数）
```kotlin
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
````
# Lambda 核心用法
- 1. 作为函数参数（最常用场景）,Lambda 最大的价值是「把逻辑当作参数传递」，比如集合的遍历、过滤、映射：
```kotlin
fun main() {
    val list = listOf(1, 2, 3, 4, 5)

    // 示例1：遍历（替代 for 循环）
    list.forEach { num -> println(num) } // 输出：1 2 3 4 5
    // 简化：只有一个参数时，可省略参数名，用 it 指代
    list.forEach { println(it) }

    // 示例2：过滤（保留偶数）
    val evenList = list.filter { it % 2 == 0 }
    println(evenList) // 输出：[2, 4]

    // 示例3：映射（每个数乘2）
    val doubleList = list.map { it * 2 }
    println(doubleList) // 输出：[2, 4, 6, 8, 10]
} 
````

- 2. 省略参数类型（类型推导）
```kotlin
// 完整写法：指定参数类型
val sum1: (Int, Int) -> Int = { a: Int, b: Int -> a + b }
// 简化写法：省略参数类型（Kotlin 从变量类型 (Int, Int) -> Int 推导）
val sum2: (Int, Int) -> Int = { a, b -> a + b }
// 更简化：连变量类型都省略（Kotlin 从 Lambda 逻辑推导）
val sum3 = { a: Int, b: Int -> a + b }
```

- 3. 省略参数列表（只有一个参数时）
```kotlin
fun main() {
    // 1. 无参数 Lambda（() -> 返回值类型）
    val sayHello: () -> String = { "Hello Kotlin" }
    println(sayHello()) // 输出：Hello Kotlin

    // 2. 单参数 Lambda（可省略参数名，用 it 指代）
    val printNum: (Int) -> Unit = { println(it) }
    printNum(100) // 输出：100

    // 3. 多参数 Lambda（必须显式声明参数名）
    val calculate: (Int, String, Int) -> Int = { a, op, b ->
        when (op) {
            "+" -> a + b
            "-" -> a - b
            else -> 0
        }
    }
    println(calculate(10, "+", 5)) // 输出：15
}
```
- 4. 「带接收者的 Lambda」（T.() -> Unit）
    （这是 Kotlin 特有的增强版 Lambda，核心是给 Lambda 绑定一个 “接收者对象”，内部可直接用 this 指代该对象）
```kotlin
data class User(var name: String = "", var age: Int = 0)

fun main() {
    // 普通 Lambda：(User) -> Unit（参数是 User，用 it 指代）
    val updateUser1: (User) -> Unit = { it.name = "张三"; it.age = 20 }
    val user1 = User()
    updateUser1(user1)
    println(user1) // 输出：User(name=张三, age=20)

    // 带接收者的 Lambda：User.() -> Unit（接收者是 User，用 this 指代，可省略）
    val updateUser2: User.() -> Unit = { this.name = "李四"; this.age = 25 }
    val user2 = User()
    user2.updateUser2() // 像调用 User 的成员方法一样
    println(user2) // 输出：User(name=李四, age=25)
}
```
# Lambda 关键特性
- 1. 返回值无需写 return，Kotlin 会默认让这类无显式返回的 Lambda 返回 Unit
```kotlin
val sum = { a: Int, b: Int ->
    val result = a + b
    result // 最后一行是返回值，等价于 return result（但不能写 return）
}
```
- 2. it 关键字：只有一个参数的 Lambda，可省略参数名，用 it 指代该参数（最常用简化写法）；

- 3. Lambda 作为最后一个参数：如果函数的最后一个参数是 Lambda，可把 Lambda 移到函数括号外（语法糖）；
```kotlin
// 原始写法：forEach 的参数是 Lambda，写在括号内
list.forEach({ println(it) })
// 简化写法：Lambda 移到括号外（推荐）
list.forEach() { println(it) }
// 极致简化：如果函数只有 Lambda 一个参数，可省略括号
list.forEach { println(it) }
```

- 4. Lambda 是 “闭包”：可访问外部变量，甚至修改（和 Java 不同，Kotlin 无需声明 final）
```kotlin
fun main() {
    var total = 0
    val list = listOf(1, 2, 3)
    list.forEach { total += it } // Lambda 内部修改外部变量 total
    println(total) // 输出：6
}
````































