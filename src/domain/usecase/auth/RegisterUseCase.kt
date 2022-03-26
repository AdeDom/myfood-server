package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.TokenResponse

class RegisterUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(registerRequest: RegisterRequest): Resource<BaseResponse<TokenResponse>> {
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

    private fun isValidateUsername(username: String): Boolean {
        return authRepository.findUserByUsername(username) > 0
    }
}