package com.example.app4

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import com.example.app4.components.DeleteConfirmDialog
import com.example.app4.components.CustomDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.app4.ui.theme.My_Application_NO_ActivityTheme
import java.util.regex.Pattern
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.app4.components.PasswordTextField
import kotlinx.coroutines.delay

class MainActivity10 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_Application_NO_ActivityTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LoginForm(innerPadding)
                }
            }
        }
    }

    @Composable
    fun LoginForm(innerPadding: PaddingValues) {
        /*
        * LaunchedEffect 用于在 Composable 函数中执行 一次性 操作（如初始化、网络请求）
        * */
        LaunchedEffect(Unit) {
            // 执行初始化操作
            delay(1000)
            Log.d("Compose", "Initialization complete")
        }

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var showDeleteDialog by remember { mutableStateOf(false) }
        var showCustomDialog by remember { mutableStateOf(false) }

        val emailError = if (email.isNotEmpty() && !email.isValidEmail()) {
            "请输入有效的邮箱地址"
        } else null

        val passwordError = if (password.isNotEmpty() && password.length < 6) {
            "密码不能少于 6 位"
        } else null

        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            /*
            * 在 Kotlin 中，it 是 lambda 表达式中单参数的默认名称
            * */
            OutlinedTextField(
                value = email,
                onValueChange = { email = it }, // it 是用户输入的新值
                label = { Text("邮箱") },
                isError = emailError != null,
                /*
                 ?.let 是 Kotlin 中处理可空类型的优雅方式：
                  - 安全：避免空指针异常
                  - 简洁：比 if-else 更简洁
                  - 链式：支持函数链式调用
                  - 作用域：在代码块中使用 it 引用对象
                * */
                supportingText = emailError?.let { { Text(it) } }
            )

            /*   OutlinedTextField(
                   value = password,
                   onValueChange = { password = it },
                   label = { Text("密码") },
                   visualTransformation = PasswordVisualTransformation(),
                   isError = passwordError != null,
                   supportingText = passwordError?.let { { Text(it) } }
               )*/
            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("--密码--") },
                isError = passwordError != null,
                errorMessage = passwordError
            )

            Row {
                Button(
                    onClick = {
                        // 处理登录逻辑
                        Toast.makeText(this@MainActivity10, "登录成功", Toast.LENGTH_SHORT).show()
                    },
                    enabled = email.isNotEmpty() && password.isNotEmpty() && emailError == null && passwordError == null
                ) {
                    Text("登录")
                }

                Button(
                    onClick = { showDeleteDialog = true },
                ) {
                    Text("删除")
                }

                Button(
                    onClick = { showCustomDialog = true },
                ) {
                    Text("自定义 Dialog")
                }

                // 显示删除确认对话框
                if (showDeleteDialog) {
                    DeleteConfirmDialog(
                        onConfirm = {
                            // 处理删除逻辑
                            Toast.makeText(this@MainActivity10, "删除成功", Toast.LENGTH_SHORT)
                                .show()
                            showDeleteDialog = false
                        },
                        onDismiss = {
                            // 处理取消逻辑
                            Toast.makeText(this@MainActivity10, "取消删除", Toast.LENGTH_SHORT)
                                .show()
                            showDeleteDialog = false
                        }
                    )
                }
                // 显示自定义 Dialog
                if (showCustomDialog) {
                    CustomDialog(
                        onDismiss = { showCustomDialog = false },
                    ) {
                        Text(
                            "自定义 Dialog",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

        }
    }

//    @Composable
//    fun DeleteConfirmDialog(
//        onConfirm: () -> Unit,
//        onDismiss: () -> Unit
//    ) {
//        AlertDialog(
//            onDismissRequest = onDismiss,
//            title = { Text("确认删除") },
//            text = { Text("确定要删除这个项目吗？此操作无法撤销。") },
//            confirmButton = {
//                TextButton(
//                    onClick = onConfirm,
//                    colors = ButtonDefaults.textButtonColors(
//                        contentColor = MaterialTheme.colorScheme.error
//                    )
//                ) {
//                    Text("删除")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = onDismiss) {
//                    Text("取消")
//                }
//            }
//        )
//    }

    // 自定义 Dialog
//    @Composable
//    fun CustomDialog(
//        onDismiss: () -> Unit,
//        content: @Composable () -> Unit
//    ) {
//        Dialog(onDismissRequest = onDismiss) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                shape = MaterialTheme.shapes.extraLarge
//            ) {
//                content()
//            }
//        }
//    }
}

/**
 * 邮箱验证扩展函数
 * 使用正则表达式验证邮箱格式
 */
fun String.isValidEmail(): Boolean {
    val emailRegex = Pattern.compile(
        "[a-zA-Z0-9._%\\-+]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return emailRegex.matcher(this).matches()
}







