package com.adedom.myfood.route.models.request

@kotlinx.serialization.Serializable
data class LoginRequest(
    val username: String?,
    val password: String?,
)