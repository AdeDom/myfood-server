package com.adedom.myfood.route.models.request

@kotlinx.serialization.Serializable
data class MyFavoriteRequest(
    val favoriteId: String?,
    val foodId: Int?,
)