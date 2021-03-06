package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.data.models.entities.FoodAndCategoryEntity
import com.adedom.myfood.data.models.entities.FoodEntity
import com.adedom.myfood.data.models.request.InsertFoodRequest

interface FoodRemoteDataSource {

    suspend fun insertFood(insertFoodRequest: InsertFoodRequest, status: String): Int?

    suspend fun getFoodDetail(foodId: Int): FoodEntity?

    suspend fun getFoodByCategoryId(categoryId: Int): List<FoodEntity>

    suspend fun getFoodAndCategoryAll(): List<FoodAndCategoryEntity>

    suspend fun getFoodAll(): List<FoodEntity>
}