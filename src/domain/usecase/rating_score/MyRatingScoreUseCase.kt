package com.adedom.myfood.domain.usecase.rating_score

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.rating_score.RatingScoreRepository

class MyRatingScoreUseCase(
    private val ratingScoreRepository: RatingScoreRepository,
) {

    suspend operator fun invoke(
        userId: String?,
        foodId: String?,
        ratingScore: String?,
    ): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        return when {
            userId.isNullOrBlank() -> {
                response.error = BaseError(message = "User id is null or blank.")
                Resource.Error(response)
            }
            foodId.isNullOrBlank() -> {
                response.error = BaseError(message = "Food id is null or blank.")
                Resource.Error(response)
            }
            foodId.toIntOrNull() == null -> {
                response.error = BaseError(message = "Food id is text.")
                Resource.Error(response)
            }
            ratingScore.isNullOrBlank() -> {
                response.error = BaseError(message = "Rating score is null or blank.")
                Resource.Error(response)
            }
            ratingScore.toFloatOrNull() == null -> {
                response.error = BaseError(message = "Rating score is text.")
                Resource.Error(response)
            }
            ratingScore.toFloat() !in 0F..5F -> {
                response.error = BaseError(message = "Rating score invalid.")
                Resource.Error(response)
            }
            else -> {
                ratingScoreRepository.myRatingScore(userId, foodId.toInt(), ratingScore.toFloat())
            }
        }
    }
}