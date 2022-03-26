package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.FoodDetailResponse

class GetFoodDetailUseCase(
    private val foodRepository: FoodRepository,
) {

    operator fun invoke(foodId: String?): Resource<BaseResponse<FoodDetailResponse>> {
        val response = BaseResponse<FoodDetailResponse>()

        return when {
            foodId.isNullOrBlank() -> {
                response.error = BaseError(message = "Food id is null or blank.")
                Resource.Error(response)
            }
            foodId.toIntOrNull() == null -> {
                response.error = BaseError(message = "Food id is text.")
                Resource.Error(response)
            }
            else -> {
                foodRepository.getFoodDetail(foodId.toInt())
            }
        }
    }
}