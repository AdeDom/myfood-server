package com.adedom.myfood.data.models.request

@kotlinx.serialization.Serializable
data class MyRatingScoreRequest(
    val foodId: Int?,
    val ratingScore: Float?,
)