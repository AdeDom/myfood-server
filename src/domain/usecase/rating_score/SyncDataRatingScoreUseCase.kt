package com.adedom.myfood.domain.usecase.rating_score

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.rating_score.RatingScoreRepository

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