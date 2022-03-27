package com.adedom.myfood.route.models.entities

data class FavoriteEntity(
    val favoriteId: String,
    val userId: String,
    val foodId: Int,
    val backupState: Int,
    val created: String,
    val updated: String?,
)