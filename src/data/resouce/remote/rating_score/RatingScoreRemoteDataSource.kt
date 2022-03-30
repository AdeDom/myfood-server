package com.adedom.myfood.data.resouce.remote.rating_score

import com.adedom.myfood.data.models.entities.RatingScoreEntity

interface RatingScoreRemoteDataSource {

    suspend fun replaceRatingScoreAll(ratingScoreList: List<RatingScoreEntity>): Int

    suspend fun getRatingScoreAll(): List<RatingScoreEntity>
}