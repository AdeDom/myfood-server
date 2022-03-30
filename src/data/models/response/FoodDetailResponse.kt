package com.adedom.myfood.data.models.response

@kotlinx.serialization.Serializable
data class FoodDetailResponse(
    val foodId: Int,
    val foodName: String,
    val alias: String?,
    val image: String,
    val price: Double,
    val description: String?,
    val categoryId: Int,
    val status: String,
    val created: String,
    val updated: String?,
)