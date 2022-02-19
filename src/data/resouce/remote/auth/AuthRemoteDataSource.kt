package com.adedom.myfood.data.resouce.remote.auth

import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.request.RegisterRequest

interface AuthRemoteDataSource {

    fun findUserByUsernameAndPassword(loginRequest: LoginRequest): UserEntity?

    fun findUserByUsername(username: String): Long

    fun insertUser(userId: String, registerRequest: RegisterRequest): Int?

    fun updateUserStatus(userId: String, status: String): Int
}