package com.adedom.myfood.domain.usecase.favorite

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.favorite.FavoriteRepository
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.MyFavoriteRequest

class MyFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(
        userId: String?,
        myFavoriteRequest: MyFavoriteRequest,
    ): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val (favoriteId, foodId) = myFavoriteRequest
        return when {
            userId.isNullOrBlank() -> {
                response.error = BaseError(message = "User id is null or blank.")
                Resource.Error(response)
            }
            foodId == null -> {
                response.error = BaseError(message = "Food id is null or blank.")
                Resource.Error(response)
            }
            else -> {
                favoriteRepository.myFavorite(userId, myFavoriteRequest)
            }
        }
    }
}