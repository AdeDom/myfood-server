package com.adedom.myfood.route.models.response

@kotlinx.serialization.Serializable
data class UserProfileResponse(
    val userId: String,
    val username: String,
    val name: String,
    val email: String?,
    val mobileNo: String?,
    val address: String?,
    val status: String,
    val created: String,
    val updated: String?,
)