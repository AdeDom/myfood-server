package com.adedom.myfood.route.models.entities

data class FavoriteEntity(
    val favoriteId: Int,
    val userId: String,
    val foodId: Int,
    val created: String,
    val updated: String?,
)