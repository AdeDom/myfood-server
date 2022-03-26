package com.adedom.myfood.route.models.base

@kotlinx.serialization.Serializable
data class BaseError(
    val code: String? = null,
    val message: String,
)