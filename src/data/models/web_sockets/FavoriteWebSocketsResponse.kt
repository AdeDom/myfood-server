package com.adedom.myfood.data.models.web_sockets

@kotlinx.serialization.Serializable
data class FavoriteWebSocketsResponse(
    val foodId: Int,
    val favorite: Long?,
)