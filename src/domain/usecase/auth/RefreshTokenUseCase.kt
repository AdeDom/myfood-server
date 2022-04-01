package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.base.ErrorResponse
import com.adedom.myfood.data.models.request.TokenRequest
import com.adedom.myfood.data.models.response.TokenResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.utility.jwt.JwtHelper

class RefreshTokenUseCase(
    private val jwtHelper: JwtHelper,
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(tokenRequest: TokenRequest): Resource<BaseResponse<TokenResponse>> {
        val response = BaseResponse<TokenResponse>()

        val (accessToken, refreshToken) = tokenRequest
        return when {
            accessToken.isNullOrBlank() -> {
                response.error = BaseError(message = "Access token is null or blank.")
                Resource.Error(response)
            }
            refreshToken.isNullOrBlank() -> {
                response.error = BaseError(message = "Refresh token is null or blank.")
                Resource.Error(response)
            }
            validateRefreshToken(refreshToken) -> {
                response.error = BaseError(
                    code = ErrorResponse.RefreshTokenError.code,
                    message = ErrorResponse.RefreshTokenError.message,
                )
                Resource.Error(response)
            }
            isValidateAccessTokenAndRefreshToken(accessToken, refreshToken) -> {
                response.error = BaseError(message = "Access token or refresh token incorrect.")
                Resource.Error(response)
            }
            else -> {
                authRepository.refreshToken(refreshToken)
            }
        }
    }

    private fun validateRefreshToken(refreshToken: String): Boolean {
        val expiresAtClaim = jwtHelper.decodeJwtGetExpiresAt(refreshToken)
        val currentTime = System.currentTimeMillis() / 1_000L
        val isTokenExpire = expiresAtClaim.minus(currentTime) > 0
        return !isTokenExpire
    }

    private suspend fun isValidateAccessTokenAndRefreshToken(accessToken: String, refreshToken: String): Boolean {
        val tokenCount = authRepository.findTokenByAccessTokenAndRefreshToken(accessToken, refreshToken)
        return tokenCount == 0L
    }
}