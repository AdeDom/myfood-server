package com.adedom.myfood.data.models.base

@kotlinx.serialization.Serializable
data class BaseError(
    val code: String? = null,
    val message: String,
)