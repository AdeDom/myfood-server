package com.adedom.myfood.route.models.request

@kotlinx.serialization.Serializable
data class RegisterRequest(
    val username: String?,
    val password: String?,
    val name: String?,
)