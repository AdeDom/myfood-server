package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.request.RegisterRequest

interface AuthRepository {

    fun findUserByUsernameAndPassword(loginRequest: LoginRequest): UserEntity?

    fun findUserByUsername(username: String): Long

    fun insertUser(registerRequest: RegisterRequest): Int?

    fun updateUserStatusInActive(userId: String): Int
}