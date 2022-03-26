package com.adedom.myfood.route.models.response.base

@kotlinx.serialization.Serializable
data class CategoryResponse(
    val categoryId: Int,
    val categoryName: String,
    val image: String,
    val created: String,
    val updated: String?,
)