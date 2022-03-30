package com.adedom.myfood.data.resouce.remote.auth

import com.adedom.myfood.data.models.request.RegisterRequest

interface AuthRemoteDataSource {

    suspend fun findUserIdByUsernameAndPassword(username: String, password: String, status: String): String?

    suspend fun findUserByUsername(username: String): Long

    suspend fun insertUser(userId: String, registerRequest: RegisterRequest, status: String): Int?

    suspend fun findUserByUserIdAndPassword(userId: String, password: String): Long

    suspend fun updateUserPassword(userId: String, password: String): Int
}