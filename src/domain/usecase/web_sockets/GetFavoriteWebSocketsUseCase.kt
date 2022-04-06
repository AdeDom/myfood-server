package com.adedom.myfood.domain.usecase.web_sockets

import com.adedom.myfood.data.models.web_sockets.GetFavoriteWebSocketsResponse
import com.adedom.myfood.data.repositories.food.FoodRepository

class GetFavoriteWebSocketsUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(): List<GetFavoriteWebSocketsResponse> {
        return when {
            else -> {
                val getFoodAndCategoryAll = foodRepository.getFoodAndCategoryAll()
                getFoodAndCategoryAll
                    .asSequence()
                    .filter {
                        (it.favorite ?: 0L) > 0
                    }
                    .filter {
                        it.foodId != null
                    }
                    .map {
                        GetFavoriteWebSocketsResponse(
                            foodId = it.foodId!!,
                            favorite = it.favorite,
                        )
                    }
                    .toList()
            }
        }
    }
}