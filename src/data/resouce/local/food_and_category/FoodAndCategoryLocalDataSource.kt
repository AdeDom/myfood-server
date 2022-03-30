package com.adedom.myfood.data.resouce.local.food_and_category

import com.adedom.myfood.data.models.entities.FoodAndCategoryEntity

interface FoodAndCategoryLocalDataSource {

    suspend fun getFoodAndCategoryAll(): List<FoodAndCategoryEntity>

    suspend fun insertFoodAndCategoryAll(foodAndCategoryAll: List<FoodAndCategoryEntity>): Int

    suspend fun deleteFoodAndCategoryAll(): Int
}