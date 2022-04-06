package com.adedom.myfood.domain.usecase.web_sockets

import com.adedom.myfood.data.models.web_sockets.FavoriteAndRatingScoreResponse
import com.adedom.myfood.data.repositories.food.FoodRepository

class FavoriteAndRatingScoreUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(): List<FavoriteAndRatingScoreResponse> {
        return when {
            else -> {
                val getFoodAndCategoryAll = foodRepository.getFoodAndCategoryAll()
                getFoodAndCategoryAll
                    .asSequence()
                    .filter {
                        (it.favorite ?: 0L) > 0 || (it.ratingScore ?: 0F) > 0
                    }
                    .filter {
                        it.foodId != null
                    }
                    .map {
                        FavoriteAndRatingScoreResponse(
                            foodId = it.foodId!!,
                            favorite = it.favorite,
                            ratingScore = it.ratingScore,
                            ratingScoreCount = it.ratingScoreCount,
                        )
                    }
                    .toList()
            }
        }
    }
}