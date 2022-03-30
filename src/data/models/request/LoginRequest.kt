package com.adedom.myfood.data.models.request

@kotlinx.serialization.Serializable
data class LoginRequest(
    val username: String?,
    val password: String?,
)