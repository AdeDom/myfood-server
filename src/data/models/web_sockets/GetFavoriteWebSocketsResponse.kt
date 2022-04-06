package com.adedom.myfood.data.models.web_sockets

@kotlinx.serialization.Serializable
data class GetFavoriteWebSocketsResponse(
    val foodId: Int,
    val favorite: Long?,
)