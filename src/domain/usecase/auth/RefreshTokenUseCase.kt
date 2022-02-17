package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.route.models.request.TokenRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.TokenResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import com.adedom.myfood.utility.jwt.JwtHelper

class RefreshTokenUseCase(
    private val jwtHelper: JwtHelper,
) {

    operator fun invoke(tokenRequest: TokenRequest): Resource<BaseResponse<TokenResponse>> {
        val response = BaseResponse<TokenResponse>()

        val (accessToken, refreshToken) = tokenRequest
        return when {
            accessToken.isNullOrBlank() -> {
                response.error = BaseError(code = "001", message = "Access token is null or blank.")
                Resource.Error(response)
            }
            refreshToken.isNullOrBlank() -> {
                response.error = BaseError(code = "002", message = "Refresh token is null or blank.")
                Resource.Error(response)
            }
            validateRefreshToken(refreshToken) -> {
                response.error = BaseError(code = "401-002", message = "Refresh token expire.")
                Resource.Error(response)
            }
            else -> {
                response.status = ResponseKeyConstant.SUCCESS
                val userId = jwtHelper.decodeJwtGetUserId(refreshToken)
                val tokenResponse = TokenResponse(
                    accessToken = jwtHelper.encodeAccessToken(userId),
                    refreshToken = jwtHelper.encodeRefreshToken(userId),
                )
                response.result = tokenResponse
                Resource.Success(response)
            }
        }
    }

    private fun validateRefreshToken(refreshToken: String): Boolean {
        val expiresAtClaim = jwtHelper.decodeJwtGetExpiresAt(refreshToken)
        val currentTime = System.currentTimeMillis() / 1_000L
        val isTokenExpire = expiresAtClaim.minus(currentTime) > 0
        return !isTokenExpire
    }
}