package com.adedom.myfood.data.models.entities

import org.joda.time.DateTime

data class UserEntity(
    val userId: String,
    val username: String,
    val password: String,
    val name: String,
    val email: String?,
    val mobileNo: String?,
    val address: String?,
    val image: String?,
    val status: String,
    val created: DateTime,
    val updated: DateTime?,
)