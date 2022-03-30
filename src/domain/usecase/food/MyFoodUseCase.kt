package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.entities.MyFoodEntity
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.food.FoodRepository

class MyFoodUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<List<MyFoodEntity>>> {
        return when {
            else -> {
                foodRepository.getMyFood()
            }
        }
    }
}