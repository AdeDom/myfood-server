package com.adedom.myfood.route.models.response

@kotlinx.serialization.Serializable
data class FavoriteResponse(
    val favoriteId: Int,
    val userId: String,
    val foodId: Int,
    val created: String,
    val updated: String?,
)