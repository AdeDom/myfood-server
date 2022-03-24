package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.route.models.entities.MyFoodEntity
import com.adedom.myfood.route.models.request.InsertFoodRequest
import com.adedom.myfood.route.models.response.base.BaseResponse

interface FoodRepository {

    fun getMyFood(): BaseResponse<List<MyFoodEntity>>

    fun insertFood(insertFoodRequest: InsertFoodRequest): Int?
}