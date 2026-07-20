package com.example.composeapp.userlist.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 真实 Retrofit 构建示例
 * 当前 demo 使用 FakeUserRemoteDataSource，不需要真正创建 Retrofit 实例
 */
object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val userApi: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}
