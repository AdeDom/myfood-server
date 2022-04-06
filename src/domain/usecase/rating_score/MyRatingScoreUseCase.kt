package com.adedom.myfood.domain.usecase.rating_score

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.MyRatingScoreRequest
import com.adedom.myfood.data.models.web_sockets.RatingScoreWebSocketsResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.rating_score.RatingScoreRepository
import com.adedom.myfood.domain.usecase.auth.TokenUseCase
import com.adedom.myfood.utility.jwt.JwtHelper
import com.auth0.jwt.JWT

class MyRatingScoreUseCase(
    private val tokenUseCase: TokenUseCase,
    private val ratingScoreRepository: RatingScoreRepository,
) {

    suspend operator fun invoke(
        authKey: String?,
        myRatingScoreRequest: MyRatingScoreRequest,
    ): Resource<BaseResponse<RatingScoreWebSocketsResponse>> {
        val response = BaseResponse<RatingScoreWebSocketsResponse>()

        val (foodId, ratingScore) = myRatingScoreRequest
        return when {
            tokenUseCase(authKey) -> {
                response.error = BaseError(message = "Authentication is invalid.")
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
                val accessToken = authKey?.replace("Bearer", "")?.trim()
                val userId = JWT().decodeJwt(accessToken).getClaim(JwtHelper.USER_ID).asString()
                ratingScoreRepository.myRatingScore(userId, foodId, ratingScore)
            }
        }
    }
}