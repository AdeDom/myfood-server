package com.adedom.myfood.route.models.response.base

@kotlinx.serialization.Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)