package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.entities.MyFoodEntity
import com.adedom.myfood.data.models.request.InsertFoodRequest
import com.adedom.myfood.data.models.response.FoodAndCategoryResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse
import com.adedom.myfood.data.repositories.Resource

interface FoodRepository {

    suspend fun getMyFood(): Resource<BaseResponse<List<MyFoodEntity>>>

    suspend fun insertFood(insertFoodRequest: InsertFoodRequest): Resource<BaseResponse<String>>

    suspend fun getFoodDetail(foodId: Int): Resource<BaseResponse<FoodDetailResponse>>

    suspend fun getFoodByCategoryId(categoryId: Int): List<FoodDetailResponse>

    suspend fun getFoodAndCategoryAll(): List<FoodAndCategoryResponse>
}