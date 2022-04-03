package com.adedom.myfood.data.models.response

@kotlinx.serialization.Serializable
data class FoodAndCategoryGroupResponse(
    val categoryId: Int,
    val categoryName: String,
    val image: String,
    val categoryTypeName: String,
    val created: String,
    val updated: String?,
    val foodDetailList: List<CategoryFoodDetailResponse>,
)