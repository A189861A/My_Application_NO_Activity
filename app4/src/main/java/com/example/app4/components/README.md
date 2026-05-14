# Components 包

这个包存放可复用的自定义 Compose 组件。

## 可用组件

### 1. DeleteConfirmDialog
删除确认对话框组件，用于确认删除操作的对话框。

```kotlin
DeleteConfirmDialog(
    onConfirm = { /* 确认删除 */ },
    onDismiss = { /* 取消删除 */ }
)
```

### 2. CustomDialog
自定义对话框组件，支持任意内容的对话框。

```kotlin
CustomDialog(
    onDismiss = { /* 关闭对话框 */ }
) {
    Text("自定义内容")
}
```

### 3. EmailTextField
邮箱输入框组件，集成了邮箱验证的可重用输入框。

```kotlin
EmailTextField(
    value = email,
    onValueChange = { email = it },
    isError = emailError != null,
    errorMessage = emailError,
    label = "邮箱"
)
```

### 4. PasswordTextField
密码输入框组件，自动隐藏输入内容的密码输入框。

```kotlin
PasswordTextField(
    value = password,
    onValueChange = { password = it },
    isError = passwordError != null,
    errorMessage = passwordError,
    label = "密码"
)
```

### 5. LoadingButton
加载按钮组件，支持显示加载状态的按钮。

```kotlin
LoadingButton(
    text = "登录",
    onClick = { /* 点击事件 */ },
    isLoading = isLoading,
    enabled = true
)
```

## 使用示例

在任意文件中导入这些组件：

```kotlin
import com.example.app4.components.DeleteConfirmDialog
import com.example.app4.components.EmailTextField
import com.example.app4.components.PasswordTextField
import com.example.app4.components.LoadingButton

@Composable
fun MyScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column {
        EmailTextField(
            value = email,
            onValueChange = { email = it }
        )

        PasswordTextField(
            value = password,
            onValueChange = { password = it }
        )

        LoadingButton(
            text = "登录",
            onClick = { isLoading = true },
            isLoading = isLoading
        )
    }
}
```

## 设计原则

1. **可重用性**：组件设计为通用用途，可在多个界面中使用
2. **组合性**：遵循 Compose 的组合原则，小组件组合成大组件
3. **参数化**：通过参数实现定制化，保持灵活性
4. **一致性**：遵循统一的命名和设计模式
