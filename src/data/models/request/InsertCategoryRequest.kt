package com.adedom.myfood.data.models.request

@kotlinx.serialization.Serializable
data class InsertCategoryRequest(
    val categoryName: String?,
    val image: String?,
)