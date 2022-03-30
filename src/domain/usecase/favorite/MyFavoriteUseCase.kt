package com.adedom.myfood.domain.usecase.favorite

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.favorite.FavoriteRepository

class MyFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(userId: String?, foodId: String?): Resource<BaseResponse<String>> {
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
            else -> {
                favoriteRepository.myFavorite(userId, foodId.toInt())
            }
        }
    }
}