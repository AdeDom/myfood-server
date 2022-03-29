package com.adedom.myfood.data.resouce.local.rating_score

import com.adedom.myfood.route.models.entities.RatingScoreEntity

interface RatingScoreLocalDataSource {

    suspend fun getRatingScoreAll(): List<RatingScoreEntity>

    suspend fun replaceRatingScore(
        ratingScoreId: String,
        userId: String,
        foodId: Int,
        ratingScore: Int,
        isBackup: Int,
        created: String,
        updated: String?,
    ): Int?

    suspend fun deleteRatingScoreAll(): Int

    suspend fun findRatingScoreEntityByUserIdAndFoodId(userId: String, foodId: Int): RatingScoreEntity?
}