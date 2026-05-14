package com.example.app4.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * 邮箱输入框组件
 * 集成了邮箱验证的可重用输入框
 */
@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    label: String = "邮箱"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        modifier = modifier,
        supportingText = errorMessage?.let {
            { Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            ) }
        }
    )
}
