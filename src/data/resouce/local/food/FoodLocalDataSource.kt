package com.adedom.myfood.data.resouce.local.food

import com.adedom.myfood.route.models.entities.FoodEntity

interface FoodLocalDataSource {

    suspend fun getFoodByCategoryId(categoryId: Int): List<FoodEntity>

    suspend fun getFoodAll(): List<FoodEntity>

    suspend fun insertFoodAll(foodList: List<FoodEntity>): Int

    suspend fun deleteFoodAll(): Int
}