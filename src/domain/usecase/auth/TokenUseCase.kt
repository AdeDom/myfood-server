package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.ErrorResponse
import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.utility.jwt.JwtHelper
import com.auth0.jwt.JWT
import com.auth0.jwt.impl.PublicClaims

class TokenUseCase(
    private val authRepository: AuthRepository,
) {

    suspend fun isValidateToken(authKey: String?): Boolean {
        return if (!authKey.isNullOrBlank()) {
            val accessToken = authKey.replace("Bearer", "").trim()
            val expiresAtClaim = JWT().decodeJwt(accessToken).getClaim(PublicClaims.EXPIRES_AT).asLong()
            val currentTime = System.currentTimeMillis() / 1_000L
            val isTokenExpire = expiresAtClaim.minus(currentTime) > 0
            if (isTokenExpire) {
                val statusLoginOrRefreshCount = authRepository.findStatusLoginOrRefreshByAccessToken(accessToken)
                statusLoginOrRefreshCount != 1L
            } else {
                true
            }
        } else {
            true
        }
    }

    suspend fun getBaseError(authKey: String?): BaseError? {
        return if (authKey != null) {
            val accessToken = authKey.replace("Bearer", "").trim()
            val expiresAtClaim = JWT().decodeJwt(accessToken).getClaim(PublicClaims.EXPIRES_AT).asLong()
            val currentTime = System.currentTimeMillis() / 1_000L
            val isTokenExpire = expiresAtClaim.minus(currentTime) > 0
            if (isTokenExpire) {
                val statusLoginOrRefreshCount = authRepository.findStatusLoginOrRefreshByAccessToken(accessToken)
                if (statusLoginOrRefreshCount == 1L) {
                    null
                } else {
                    BaseError(
                        code = ErrorResponse.AccessTokenNotAvailableError.code,
                        message = ErrorResponse.AccessTokenNotAvailableError.message
                    )
                }
            } else {
                BaseError(
                    code = ErrorResponse.AccessTokenError.code,
                    message = ErrorResponse.AccessTokenError.message
                )
            }
        } else {
            BaseError(
                code = ErrorResponse.UnauthorizedError.code,
                message = ErrorResponse.UnauthorizedError.message
            )
        }
    }

    fun getUserId(authKey: String?): String {
        val accessToken = authKey?.replace("Bearer", "")?.trim()
        return JWT().decodeJwt(accessToken).getClaim(JwtHelper.USER_ID).asString()
    }
}