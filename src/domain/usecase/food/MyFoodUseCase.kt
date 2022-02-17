package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.route.models.entities.MyFoodEntity
import com.adedom.myfood.route.models.response.base.BaseResponse

class MyFoodUseCase(
    private val foodRepository: FoodRepository,
) {

    operator fun invoke(): BaseResponse<List<MyFoodEntity>> {
        return foodRepository.getMyFood()
    }
}