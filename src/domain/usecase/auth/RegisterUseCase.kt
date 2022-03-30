package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.RegisterRequest
import com.adedom.myfood.data.models.response.TokenResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.auth.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(registerRequest: RegisterRequest): Resource<BaseResponse<TokenResponse>> {
        val response = BaseResponse<TokenResponse>()

        val (username, password, name) = registerRequest
        return when {
            username.isNullOrBlank() -> {
                response.error = BaseError(message = "Username is null or blank.")
                Resource.Error(response)
            }
            password.isNullOrBlank() -> {
                response.error = BaseError(message = "Password is null or blank.")
                Resource.Error(response)
            }
            name.isNullOrBlank() -> {
                response.error = BaseError(message = "Name is null or blank.")
                Resource.Error(response)
            }
            isValidateUsername(username) -> {
                response.error = BaseError(message = "This username already exists.")
                Resource.Error(response)
            }
            else -> {
                authRepository.register(registerRequest)
            }
        }
    }

    private suspend fun isValidateUsername(username: String): Boolean {
        return authRepository.findUserByUsername(username) > 0
    }
}