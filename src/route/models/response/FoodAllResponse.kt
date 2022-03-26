package com.adedom.myfood.route.models.response

@kotlinx.serialization.Serializable
data class FoodAllResponse(
    val foodId: Int,
    val foodName: String,
    val alias: String?,
    val image: String,
    val price: Double,
    val description: String?,
    val status: String,
    val created: String,
    val updated: String?,
    val categoryId: Int,
    val categoryName: String,
)