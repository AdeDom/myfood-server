package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.response.FoodAllResponse

class GetFoodAllUseCase(
    private val foodRepository: FoodRepository,
) {

    operator fun invoke(): Resource<BaseResponse<List<FoodAllResponse>>> {
        return when {
            else -> {
                foodRepository.getFoodAll()
            }
        }
    }
}