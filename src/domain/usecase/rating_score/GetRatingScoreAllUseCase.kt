package com.adedom.myfood.domain.usecase.rating_score

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.rating_score.RatingScoreRepository
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.response.RatingScoreResponse

class GetRatingScoreAllUseCase(
    private val ratingScoreRepository: RatingScoreRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<List<RatingScoreResponse>>> {
        return when {
            else -> {
                ratingScoreRepository.getRatingScoreAll()
            }
        }
    }
}