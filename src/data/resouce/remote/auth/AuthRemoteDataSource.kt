package com.adedom.myfood.data.resouce.remote.auth

import com.adedom.myfood.route.models.request.RegisterRequest

interface AuthRemoteDataSource {

    fun findUserIdByUsernameAndPassword(username: String, password: String, status: String): String?

    fun findUserByUsername(username: String): Long

    fun insertUser(userId: String, registerRequest: RegisterRequest, status: String): Int?

    fun findUserByUserIdAndPassword(userId: String, password: String): Long

    fun updateUserPassword(userId: String, password: String): Int
}