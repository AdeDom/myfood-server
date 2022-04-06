package com.adedom.myfood.domain.usecase.favorite

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.request.MyFavoriteRequest
import com.adedom.myfood.data.models.web_sockets.FavoriteWebSocketsResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.favorite.FavoriteRepository
import com.adedom.myfood.domain.usecase.auth.TokenUseCase

class MyFavoriteUseCase(
    private val tokenUseCase: TokenUseCase,
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(
        authKey: String?,
        myFavoriteRequest: MyFavoriteRequest
    ): Resource<BaseResponse<FavoriteWebSocketsResponse>> {
        val response = BaseResponse<FavoriteWebSocketsResponse>()

        val (foodId) = myFavoriteRequest
        return when {
            tokenUseCase.isValidateToken(authKey) -> {
                response.error = tokenUseCase.getBaseError(authKey)
                Resource.Error(response)
            }
            foodId == null -> {
                response.error = BaseError(message = "Food id is null.")
                Resource.Error(response)
            }
            else -> {
                val userId = tokenUseCase.getUserId(authKey)
                favoriteRepository.myFavorite(userId, foodId)
            }
        }
    }
}