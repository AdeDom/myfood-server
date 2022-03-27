package com.adedom.myfood.domain.usecase.favorite

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.favorite.FavoriteRepository
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.response.FavoriteResponse

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