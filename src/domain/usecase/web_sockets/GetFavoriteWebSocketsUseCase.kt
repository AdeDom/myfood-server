package com.adedom.myfood.domain.usecase.web_sockets

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.web_sockets.GetFavoriteWebSocketsResponse
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class GetFavoriteWebSocketsUseCase(
    private val foodRepository: FoodRepository,
) {

    suspend operator fun invoke(): BaseResponse<List<GetFavoriteWebSocketsResponse>> {
        val response = BaseResponse<List<GetFavoriteWebSocketsResponse>>()

        return when {
            else -> {
                val getFoodAndCategoryAll = foodRepository.getFoodAndCategoryAll()
                val getFavoriteWebSocketsResponse = getFoodAndCategoryAll
                    .asSequence()
                    .filter {
                        (it.favorite ?: 0L) > 0
                    }
                    .filter {
                        it.foodId != null
                    }
                    .map {
                        GetFavoriteWebSocketsResponse(
                            foodId = it.foodId!!,
                            favorite = it.favorite,
                        )
                    }
                    .toList()

                response.status = ResponseKeyConstant.SUCCESS
                response.result = getFavoriteWebSocketsResponse
                response
            }
        }
    }
}