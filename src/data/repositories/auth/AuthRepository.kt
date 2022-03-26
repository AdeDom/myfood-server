package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.entities.UserEntity
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.TokenResponse

interface AuthRepository {

    fun findUserByUsernameAndPassword(username: String, password: String): UserEntity?

    fun findUserByUsername(username: String): Long

    fun register(registerRequest: RegisterRequest): Resource<BaseResponse<TokenResponse>>

    fun findUserByUserIdAndPassword(userId: String, password: String): Long

    fun changePassword(userId: String, newPassword: String): Resource<BaseResponse<String>>
}