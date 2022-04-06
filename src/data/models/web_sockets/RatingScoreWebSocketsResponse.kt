package com.adedom.myfood.data.models.web_sockets

@kotlinx.serialization.Serializable
data class RatingScoreWebSocketsResponse(
    val foodId: Int,
    val ratingScore: Float?,
    val ratingScoreCount: String?,
)