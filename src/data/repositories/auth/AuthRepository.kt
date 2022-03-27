package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.route.models.response.TokenResponse

interface AuthRepository {

    suspend fun login(username: String, password: String): Resource<BaseResponse<TokenResponse>>

    suspend fun findUserByUsername(username: String): Long

    suspend fun register(registerRequest: RegisterRequest): Resource<BaseResponse<TokenResponse>>

    suspend fun findUserByUserIdAndPassword(userId: String, password: String): Long

    suspend fun changePassword(userId: String, newPassword: String): Resource<BaseResponse<String>>
}