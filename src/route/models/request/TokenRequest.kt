package com.adedom.myfood.route.models.request

@kotlinx.serialization.Serializable
data class TokenRequest(
    val accessToken: String?,
    val refreshToken: String?,
)