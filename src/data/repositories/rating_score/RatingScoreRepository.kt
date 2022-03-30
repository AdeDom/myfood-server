package com.adedom.myfood.data.repositories.rating_score

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.RatingScoreResponse
import com.adedom.myfood.data.repositories.Resource

interface RatingScoreRepository {

    suspend fun getRatingScoreAll(): Resource<BaseResponse<List<RatingScoreResponse>>>

    suspend fun myRatingScore(userId: String, foodId: Int, ratingScore: Float): Resource<BaseResponse<String>>

    suspend fun deleteRatingScoreAll(): Resource<BaseResponse<String>>

    suspend fun syncDataRatingScore(): Resource<BaseResponse<String>>
}