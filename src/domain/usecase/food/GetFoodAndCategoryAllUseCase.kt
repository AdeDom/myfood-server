package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.response.FoodAndCategoryResponse

class GetFoodAndCategoryAllUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<List<FoodAndCategoryResponse>>> {
        return when {
            else -> {
                foodRepository.getFoodAndCategoryAll()
            }
        }
    }
}