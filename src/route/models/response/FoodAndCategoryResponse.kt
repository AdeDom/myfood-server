package com.adedom.myfood.route.models.response

@kotlinx.serialization.Serializable
data class FoodAndCategoryResponse(
    val foodAndCategoryId: Int,
    val foodId: Int,
    val foodName: String,
    val alias: String?,
    val foodImage: String,
    val price: Double,
    val description: String?,
    val status: String,
    val foodCreated: String,
    val foodUpdated: String?,
    val categoryId: Int,
    val categoryName: String,
    val categoryImage: String,
    val categoryCreated: String,
    val categoryUpdated: String?,
)