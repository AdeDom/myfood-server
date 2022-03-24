package com.adedom.myfood.route.models.request

@kotlinx.serialization.Serializable
data class InsertCategoryRequest(
    val categoryName: String?,
    val image: String?,
)