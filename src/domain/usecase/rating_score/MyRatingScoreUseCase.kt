package com.adedom.myfood.domain.usecase.rating_score

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.MyRatingScoreRequest
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.rating_score.RatingScoreRepository

class MyRatingScoreUseCase(
    private val ratingScoreRepository: RatingScoreRepository,
) {

    suspend operator fun invoke(
        userId: String?,
        myRatingScoreRequest: MyRatingScoreRequest,
    ): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val (foodId, ratingScore) = myRatingScoreRequest
        return when {
            userId.isNullOrBlank() -> {
                response.error = BaseError(message = "User id is null or blank.")
                Resource.Error(response)
            }
            foodId == null -> {
                response.error = BaseError(message = "Food id is null.")
                Resource.Error(response)
            }
            ratingScore == null -> {
                response.error = BaseError(message = "Rating score is null.")
                Resource.Error(response)
            }
            ratingScore !in 0F..5F -> {
                response.error = BaseError(message = "Rating score invalid.")
                Resource.Error(response)
            }
            else -> {
                ratingScoreRepository.myRatingScore(userId, foodId, ratingScore)
            }
        }
    }
}