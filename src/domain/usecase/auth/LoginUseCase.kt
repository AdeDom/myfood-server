package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.route.models.request.LoginRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.TokenResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import com.adedom.myfood.utility.jwt.JwtHelper

class LoginUseCase(
    private val jwtHelper: JwtHelper,
    private val authRepository: AuthRepository,
) {

    operator fun invoke(loginRequest: LoginRequest): Resource<BaseResponse<TokenResponse>> {
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
                val userEntity = authRepository.findUserByUsernameAndPassword(loginRequest)
                if (userEntity != null) {
                    response.status = ResponseKeyConstant.SUCCESS
                    response.result = TokenResponse(
                        accessToken = jwtHelper.encodeAccessToken(userEntity.userId),
                        refreshToken = jwtHelper.encodeRefreshToken(userEntity.userId)
                    )
                    Resource.Success(response)
                } else {
                    response.error = BaseError(message = "Username or password incorrect.")
                    Resource.Error(response)
                }
            }
        }
    }
}