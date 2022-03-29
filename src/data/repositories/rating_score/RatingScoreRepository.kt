package com.adedom.myfood.data.repositories.rating_score

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.response.RatingScoreResponse

interface RatingScoreRepository {

    suspend fun getRatingScoreAll(): Resource<BaseResponse<List<RatingScoreResponse>>>

    suspend fun myRatingScore(userId: String, foodId: Int, ratingScore: Int): Resource<BaseResponse<String>>

    suspend fun deleteRatingScoreAll(): Resource<BaseResponse<String>>
}