package entity

class Msg(val content: String, val type: Int) {
    /*
    * 伴生对象的作用：
    * 1. 相当于Java中的静态成员
    * 2. 可以直接通过类名访问，无需创建实例
    * 3. 定义消息类型的常量
    * */
    companion object {
        const val TYPE_RECEIVED = 0
        const val TYPE_SENT = 1
    }
}