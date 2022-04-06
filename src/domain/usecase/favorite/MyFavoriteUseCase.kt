package com.adedom.myfood.domain.usecase.favorite

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.MyFavoriteRequest
import com.adedom.myfood.data.models.web_sockets.GetFavoriteWebSocketsResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.favorite.FavoriteRepository

class MyFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(
        userId: String?,
        myFavoriteRequest: MyFavoriteRequest
    ): Resource<BaseResponse<GetFavoriteWebSocketsResponse>> {
        val response = BaseResponse<GetFavoriteWebSocketsResponse>()

        val (foodId) = myFavoriteRequest
        return when {
            userId.isNullOrBlank() -> {
                response.error = BaseError(message = "User id is null or blank.")
                Resource.Error(response)
            }
            foodId == null -> {
                response.error = BaseError(message = "Food id is null.")
                Resource.Error(response)
            }
            else -> {
                favoriteRepository.myFavorite(userId, foodId)
            }
        }
    }
}