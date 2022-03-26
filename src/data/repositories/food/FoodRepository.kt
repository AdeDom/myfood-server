package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.entities.MyFoodEntity
import com.adedom.myfood.route.models.request.InsertFoodRequest
import com.adedom.myfood.route.models.response.FoodAllResponse
import com.adedom.myfood.route.models.response.FoodDetailResponse

interface FoodRepository {

    fun getMyFood(): BaseResponse<List<MyFoodEntity>>

    fun insertFood(insertFoodRequest: InsertFoodRequest): Resource<BaseResponse<String>>

    fun getFoodDetail(foodId: Int): Resource<BaseResponse<FoodDetailResponse>>

    fun getFoodByCategoryId(categoryId: Int): Resource<BaseResponse<List<FoodDetailResponse>>>

    fun getFoodAll(): Resource<BaseResponse<List<FoodAllResponse>>>
}