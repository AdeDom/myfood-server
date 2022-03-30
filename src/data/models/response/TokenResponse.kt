package com.adedom.myfood.data.models.response

@kotlinx.serialization.Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)