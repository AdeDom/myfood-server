package com.adedom.myfood.data.models.request

@kotlinx.serialization.Serializable
data class ChangePasswordRequest(
    val oldPassword: String?,
    val newPassword: String?,
)