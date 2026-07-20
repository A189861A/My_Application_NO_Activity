package com.example.composeapp.ui.userlist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeapp.userlist.data.User

class UserIforList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                UserListScreen()
            }
        }
    }
}

/**
 * UI 层：用户列表演示页面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UserListViewModel = viewModel()) {
    /*
    * collectAsState 将 ViewModel 中不断发射的“数据流”转化为 Compose 可以监听的“状态”，从而实现数据变化自动驱动 UI 刷新.
    * */
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val hideAdmin by viewModel.hideAdmin.collectAsState()
    /*
    * Scaffold 是实现 Material Design 布局的基石组件
    * */
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("用户列表") },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "隐藏管理员",
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = hideAdmin,
                            onCheckedChange = viewModel::setHideAdminUsers
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = viewModel::refreshUsers
            ) {
                when (val state = uiState) {
                    is UserListUiState.Loading -> LoadingContent()
                    is UserListUiState.Success -> UserListContent(users = state.users)
                    is UserListUiState.Error -> ErrorContent(
                        message = state.message,
                        onRetry = viewModel::refreshUsers
                    )
                }
            }
        }
    }
}

@Composable
fun UserListContent(users: List<User>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = users,
            key = { user -> user.id }
        ) { user ->
            UserListItem(user = user)
        }
    }
}

@Composable
fun UserListItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = user.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "ID: ${user.id}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Email: ${user.email}",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Role: ${user.role}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "加载失败: $message",
            fontSize = 16.sp,
            color = Color.Red
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        Button(onClick = onRetry) {
            Text("重试")
        }
    }
}


/*
* 分层架构
┌─────────────────────────────────────────────────────────────────┐
│                      UI Layer (Composable)                      │
│  - 显示数据                                                      │
│  - 处理用户交互                                                  │
│  - 调用 ViewModel 方法                                           │
├─────────────────────────────────────────────────────────────────┤
│                   ViewModel Layer                               │
│  - 管理 UI 状态                                                  │
│  - 处理业务逻辑                                                  │
│  - 调用 Repository 方法                                          │
├─────────────────────────────────────────────────────────────────┤
│                  Repository Layer                               │
│  - 数据聚合                                                      │
│  - 业务规则                                                      │
│  - 选择数据源                                                    │
├─────────────────────────────────────────────────────────────────┤
│                   Data Source Layer                             │
│  - 网络 API (Retrofit)                                          │
│  - 本地数据库 (Room)                                            │
│  - 本地缓存 (DataStore)                                         │
└─────────────────────────────────────────────────────────────────┘
* */
