import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

// 模拟用户数据模型
data class User(val id: String, val name: String, val age: Int)
/*
* object 关键字用于实现 单例模式.
*   # 核心特点
*       - 线程安全：Kotlin 的 object 初始化过程是线程安全的，由系统保证。
        - 全局唯一：在整个程序运行期间，这个对象只存在一份。
        - 直接调用：不需要 new，直接通过类名调用属性和方法。
        - 懒加载：只有在第一次被访问时，它才会被初始化。
* */
// 模拟数据仓库（网络/数据库请求）
object UserRepository {
    // 存储所有用户的列表
    private val allUsers = mutableListOf(
        User("1001", "张三", 25),
        User("1002", "李四", 30),
        User("1003", "王五", 28)
    )
    // 根据ID获取用户信息（模拟异步请求，返回LiveData）
    fun getUserById(userId: String): LiveData<User> {
        val result = MutableLiveData<User>()
        // 模拟网络请求延迟
        Thread {
            Thread.sleep(500)
            // 模拟根据ID返回不同用户
            val user = when (userId) {
                in allUsers.map { it.id } -> allUsers.find { it.id == userId } ?: User("0000", "未知用户", 0)
                else -> User("0000", "未知用户", 0)
            }
            result.postValue(user)
        }.start()
        return result
    }

    // 获取所有用户信息（模拟异步请求，返回LiveData）
    fun getAllUsers(): LiveData<List<User>> {
        val result = MutableLiveData<List<User>>()
        // 模拟网络请求延迟
        Thread {
            Thread.sleep(500)
            // 返回所有用户
            result.postValue(allUsers)
        }.start()
        return result
    }
}

// ViewModel（核心：使用switchMap）
class UserViewModel : ViewModel() {
    // 1. 触发源：用户ID的LiveData（私有，外部只能通过方法修改）
    private val _userId = MutableLiveData<String>()

    /*
    * switchMap:
    *   将一个 LiveData 的值变化映射为另一个 LiveData，并自动取消前一个映射结果的订阅.
    *   确保在新值到来时，取消前一个请求，避免内存泄漏.
    * */
    // 2. 用switchMap转换：ID变化时自动请求用户数据
    val userLiveData: LiveData<User> = _userId.switchMap{ userId ->
        // 每当_userId的值变化，就执行这里，返回新的LiveData
        UserRepository.getUserById(userId)
    }

    // 外部调用此方法更新用户ID
    fun getUserBtn(userId: String) {
        Log.d("--getUserBtn--", "getUserBtn:$userId")
        _userId.value = userId
    }
    // 获取所有用户id信息（模拟异步请求，返回LiveData）
    fun getAllUsersId(): LiveData<List<String>> {
        val result = MutableLiveData<List<String>>()
        UserRepository.getAllUsers().observeForever { users ->
            // 当用户数据加载完成后，提取 ID
            val userIds = users.map { it.id }
            result.postValue(userIds)
        }

        return result
    }
}
