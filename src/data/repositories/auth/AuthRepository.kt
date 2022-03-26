package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.route.models.response.base.BaseResponse

interface AuthRepository {

    fun findUserByUsernameAndPassword(loginRequest: LoginRequest): UserEntity?

    fun findUserByUsername(username: String): Long

    fun insertUser(registerRequest: RegisterRequest): Int?

    fun findUserByUserIdAndPassword(userId: String, password: String): Long

    fun updateUserPassword(userId: String, newPassword: String): Resource<BaseResponse<String>>
}