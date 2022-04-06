package com.adedom.myfood.data.models.web_sockets

@kotlinx.serialization.Serializable
data class FavoriteAndRatingScoreResponse(
    val foodId: Int,
    val favorite: Long?,
    val ratingScore: Float?,
    val ratingScoreCount: String?,
)