package com.adedom.myfood.data.resouce.local.food

import com.adedom.myfood.data.models.entities.FoodEntity

interface FoodLocalDataSource {

    suspend fun getFoodDetail(foodId: Int): FoodEntity?

    suspend fun getFoodByCategoryId(categoryId: Int): List<FoodEntity>

    suspend fun getFoodAll(): List<FoodEntity>

    suspend fun insertFoodAll(foodList: List<FoodEntity>): Int

    suspend fun deleteFoodAll(): Int
}