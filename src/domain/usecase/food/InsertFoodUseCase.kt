package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.category.CategoryRepository
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.InsertFoodRequest

class InsertFoodUseCase(
    private val categoryRepository: CategoryRepository,
    private val foodRepository: FoodRepository,
) {

    operator fun invoke(insertFoodRequest: InsertFoodRequest): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val (foodName, _, image, price, _, categoryId) = insertFoodRequest
        return when {
            foodName.isNullOrBlank() -> {
                response.error = BaseError(message = "Food name is null or blank.")
                Resource.Error(response)
            }
            image.isNullOrBlank() -> {
                response.error = BaseError(message = "Image is null or blank.")
                Resource.Error(response)
            }
            price == null -> {
                response.error = BaseError(message = "Price is null or blank.")
                Resource.Error(response)
            }
            categoryId == null -> {
                response.error = BaseError(message = "Category id is null or blank.")
                Resource.Error(response)
            }
            hasCategory(categoryId) -> {
                response.error = BaseError(message = "Category id not found.")
                Resource.Error(response)
            }
            else -> {
                foodRepository.insertFood(insertFoodRequest)
            }
        }
    }

    private fun hasCategory(categoryId: Int): Boolean {
        val isFindCategoryId = categoryRepository.findCategoryId(categoryId)
        return isFindCategoryId == 0L
    }
}