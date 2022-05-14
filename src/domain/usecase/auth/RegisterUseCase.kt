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

        val (email, password, name) = registerRequest
        return when {
            email.isNullOrBlank() -> {
                response.error = BaseError(message = "Email is null or blank.")
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
            isValidateEmail(email) -> {
                response.error = BaseError(message = "This email already exists.")
                Resource.Error(response)
            }
            else -> {
                authRepository.register(registerRequest)
            }
        }
    }

    private suspend fun isValidateEmail(email: String): Boolean {
        return authRepository.findUserByEmail(email) > 0
    }
}