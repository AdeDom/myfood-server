package com.adedom.myfood.route.models.response.base

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