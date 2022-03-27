package com.adedom.myfood.route.models.response

@kotlinx.serialization.Serializable
data class FavoriteResponse(
    val favoriteId: String,
    val userId: String,
    val foodId: Int,
    val backupState: Int,
    val created: String,
    val updated: String?,
)