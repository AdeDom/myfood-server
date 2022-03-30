package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FoodAndCategoryResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.food.FoodRepository

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