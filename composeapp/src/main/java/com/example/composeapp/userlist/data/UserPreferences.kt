package com.example.composeapp.userlist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 使用 DataStore 保存用户偏好
 * 选择 "hide admin users"：可直接影响列表过滤，演示 DataStore 与 Repository 联动
 *
- 这是一个 Context 的扩展属性。
- 通过 preferencesDataStore(name = "user_preferences") 委托创建。
- 每个 Context 只会创建一个 DataStore 实例（委托保证单例）。
- user_preferences 是存储文件名。
- 返回类型是 DataStore<Preferences>，Preferences 是一个接口，定义了存储数据的结构。
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    private val hideAdminUsersKey = booleanPreferencesKey("hide_admin_users")

    val hideAdminUsers: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[hideAdminUsersKey] ?: false
        }

    suspend fun setHideAdminUsers(hide: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[hideAdminUsersKey] = hide
        }
    }
}
