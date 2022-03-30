package com.adedom.myfood.data.models.request

@kotlinx.serialization.Serializable
data class TokenRequest(
    val accessToken: String?,
    val refreshToken: String?,
)