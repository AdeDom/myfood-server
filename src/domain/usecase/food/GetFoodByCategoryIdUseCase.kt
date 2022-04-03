package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.category.CategoryRepository
import com.adedom.myfood.data.repositories.food.FoodRepository

class GetFoodByCategoryIdUseCase(
    private val categoryRepository: CategoryRepository,
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(categoryId: String?): Resource<BaseResponse<List<FoodDetailResponse>>> {
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
                val isCategoryTypeRecommend = isCategoryTypeRecommend(categoryId.toInt())
                if (isCategoryTypeRecommend) {
                    foodRepository.getFoodByCategoryId(categoryId.toInt())
                } else {
                    foodRepository.getFoodByCategoryId(categoryId.toInt())
                }
            }
        }
    }

    private suspend fun isCategoryTypeRecommend(categoryId: Int): Boolean {
        return categoryRepository.findCategoryTypeCountByCategoryIdAndCategoryTypeRecommend(categoryId) == 1L
    }
}