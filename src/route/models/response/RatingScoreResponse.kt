package com.adedom.myfood.route.models.response

@kotlinx.serialization.Serializable
data class RatingScoreResponse(
    val ratingScoreId: String,
    val userId: String,
    val foodId: Int,
    val ratingScore: Int,
    val isBackup: Boolean,
    val created: String,
    val updated: String?,
)