package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.request.RegisterRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.TokenResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import com.adedom.myfood.utility.jwt.JwtHelper

class RegisterUseCase(
    private val jwtHelper: JwtHelper,
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
                val isSuccess = authRepository.insertUser(registerRequest) ?: 0
                val loginRequest = LoginRequest(username, password)
                val userEntity = authRepository.findUserByUsernameAndPassword(loginRequest)
                if (isSuccess > 0 && userEntity != null) {
                    response.status = ResponseKeyConstant.SUCCESS
                    val tokenResponse = TokenResponse(
                        accessToken = jwtHelper.encodeAccessToken(userEntity.userId),
                        refreshToken = jwtHelper.encodeRefreshToken(userEntity.userId)
                    )
                    response.result = tokenResponse
                    Resource.Success(response)
                } else {
                    response.error = BaseError(message = "Registration failed")
                    Resource.Error(response)
                }
            }
        }
    }

    private fun isValidateUsername(username: String): Boolean {
        return authRepository.findUserByUsername(username) > 0
    }
}