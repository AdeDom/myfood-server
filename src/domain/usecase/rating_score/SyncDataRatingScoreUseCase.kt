package com.adedom.myfood.domain.usecase.rating_score

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.rating_score.RatingScoreRepository
import com.adedom.myfood.route.models.base.BaseResponse

class SyncDataRatingScoreUseCase(
    private val ratingScoreRepository: RatingScoreRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<String>> {
        return when {
            else -> {
                ratingScoreRepository.syncDataRatingScore()
            }
        }
    }
}