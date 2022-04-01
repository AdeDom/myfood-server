package com.adedom.myfood.data.repositories.auth

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.RegisterRequest
import com.adedom.myfood.data.models.response.TokenResponse
import com.adedom.myfood.data.repositories.Resource

interface AuthRepository {

    suspend fun login(username: String, password: String): Resource<BaseResponse<TokenResponse>>

    suspend fun findUserByUsername(username: String): Long

    suspend fun register(registerRequest: RegisterRequest): Resource<BaseResponse<TokenResponse>>

    suspend fun findUserByUserIdAndPassword(userId: String, password: String): Long

    suspend fun changePassword(userId: String, newPassword: String): Resource<BaseResponse<String>>

    suspend fun logout(): Resource<BaseResponse<String>>

    suspend fun refreshToken(refreshTokenMaster: String): Resource<BaseResponse<TokenResponse>>
}