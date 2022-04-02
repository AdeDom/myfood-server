package com.adedom.myfood.domain.usecase.food

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FoodAndCategoryResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class GetFoodAndCategoryAllUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(): Resource<BaseResponse<List<FoodAndCategoryResponse>>> {
        val response = BaseResponse<List<FoodAndCategoryResponse>>()

        return when {
            else -> {
                val foodAllListResponse = foodRepository.getFoodAndCategoryAll()
                response.status = ResponseKeyConstant.SUCCESS
                response.result = foodAllListResponse
                return Resource.Success(response)
            }
        }
    }
}