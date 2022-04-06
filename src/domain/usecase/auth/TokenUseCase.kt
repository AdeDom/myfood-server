package com.adedom.myfood.domain.usecase.auth

import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.auth0.jwt.JWT
import com.auth0.jwt.impl.PublicClaims

class TokenUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(authKey: String?): Boolean {
        return if (!authKey.isNullOrBlank()) {
            val accessToken = authKey.replace("Bearer", "").trim()
            val expiresAtClaim = JWT().decodeJwt(accessToken).getClaim(PublicClaims.EXPIRES_AT).asLong()
            val currentTime = System.currentTimeMillis() / 1_000L
            val isTokenExpire = expiresAtClaim.minus(currentTime) > 0
            if (isTokenExpire) {
                val statusLoginOrRefreshCount = authRepository.findStatusLoginOrRefreshByAccessToken(accessToken)
                if (statusLoginOrRefreshCount == 1L) {
                    false
                } else {
                    true
                }
            } else {
                true
            }
        } else {
            true
        }
    }
}