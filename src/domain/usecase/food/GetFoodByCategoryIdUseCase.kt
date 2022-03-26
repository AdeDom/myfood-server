package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.response.FoodDetailResponse

class GetFoodByCategoryIdUseCase(
    private val foodRepository: FoodRepository,
) {

    operator fun invoke(categoryId: String?): Resource<BaseResponse<List<FoodDetailResponse>>> {
        val response = BaseResponse<List<FoodDetailResponse>>()

        return when {
            categoryId.isNullOrBlank() -> {
                response.error = BaseError(message = "Category id is null or blank.")
                Resource.Error(response)
            }
            categoryId.toIntOrNull() == null -> {
                response.error = BaseError(message = "Category id is text.")
                Resource.Error(response)
            }
            else -> {
                foodRepository.getFoodByCategoryId(categoryId.toInt())
            }
        }
    }
}