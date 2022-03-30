package com.adedom.myfood.domain.usecase.favorite

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FavoriteResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.favorite.FavoriteRepository

class GetFavoriteAllUseCase(
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<List<FavoriteResponse>>> {
        return when {
            else -> {
                favoriteRepository.getFavoriteAll()
            }
        }
    }
}