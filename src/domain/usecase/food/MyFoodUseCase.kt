package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.route.models.response.MyFoodResponse

class MyFoodUseCase(
    private val foodRepository: FoodRepository,
) {

    operator fun invoke(): MyFoodResponse {
        return foodRepository.myFood()
    }
}