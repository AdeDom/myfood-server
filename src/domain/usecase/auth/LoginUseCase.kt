package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.LoginRequest
import com.adedom.myfood.data.models.response.TokenResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.auth.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(loginRequest: LoginRequest): Resource<BaseResponse<TokenResponse>> {
        val response = BaseResponse<TokenResponse>()

        val (username, password) = loginRequest
        return when {
            username.isNullOrBlank() -> {
                response.error = BaseError(message = "Username is null or blank.")
                Resource.Error(response)
            }
            password.isNullOrBlank() -> {
                response.error = BaseError(message = "Password is null or blank.")
                Resource.Error(response)
            }
            else -> {
                authRepository.login(username, password)
            }
        }
    }
}