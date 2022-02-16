package com.adedom.myfood.route.models.response.base

@kotlinx.serialization.Serializable
data class BaseError(
    val code: String,
    val message: String,
)