package com.adedom.myfood.domain.usecase.favorite

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.favorite.FavoriteRepository
import com.adedom.myfood.route.models.base.BaseResponse

class SyncDataFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<String>> {
        return when {
            else -> {
                favoriteRepository.syncDataFavorite()
            }
        }
    }
}