package com.adedom.myfood.route.models.request

@kotlinx.serialization.Serializable
data class ChangePasswordRequest(
    val oldPassword: String?,
    val newPassword: String?,
)