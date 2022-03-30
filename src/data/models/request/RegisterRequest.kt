package com.adedom.myfood.data.models.request

@kotlinx.serialization.Serializable
data class RegisterRequest(
    val username: String?,
    val password: String?,
    val name: String?,
    val email: String?,
    val mobileNo: String?,
    val address: String?,
)