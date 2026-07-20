package com.example.composeapp.userlist.data

import retrofit2.http.GET

/**
 * Retrofit 网络接口
 */
interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}
