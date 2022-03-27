package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.route.models.entities.FoodAllEntity
import com.adedom.myfood.route.models.entities.FoodEntity
import com.adedom.myfood.route.models.request.InsertFoodRequest

interface FoodRemoteDataSource {

  suspend fun insertFood(insertFoodRequest: InsertFoodRequest, status: String): Int?

    suspend fun getFoodDetail(foodId: Int): FoodEntity?

    suspend fun getFoodByCategoryId(categoryId: Int): List<FoodEntity>

    suspend fun getFoodAndCategoryAll(): List<FoodAllEntity>

    suspend fun getFoodAll(): List<FoodEntity>
}