import android.content.Context
import android.widget.Toast


fun String.showToast(context: Context) {
    /*
    * context: Toast显示的上下文环境，必不可少，不能省略
    * text:  Toast显示的文本内容，不能为空，否则会抛出异常
    * duration: Toast显示的时间，默认是Toast.LENGTH_SHORT
    * */
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun Int.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}