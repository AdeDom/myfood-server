package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.domain.usecase.Resource
import com.adedom.myfood.route.models.request.InsertFoodRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class InsertFoodUseCase(
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
            else -> {
                val isInsertFood = foodRepository.insertFood(insertFoodRequest) == 1
                if (isInsertFood) {
                    response.status = ResponseKeyConstant.SUCCESS
                    response.result = "Insert food success."
                    Resource.Success(response)
                } else {
                    response.error = BaseError(message = "Insert food failed.")
                    Resource.Error(response)
                }
            }
        }
    }
}