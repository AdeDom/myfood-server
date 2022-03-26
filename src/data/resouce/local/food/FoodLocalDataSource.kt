package com.adedom.myfood.data.resouce.local.food

import com.adedom.myfood.route.models.entities.FoodEntity

interface FoodLocalDataSource {

    fun getFoodByCategoryId(categoryId: Int): List<FoodEntity>

    fun getFoodAll(): List<FoodEntity>

    fun insertFoodAll(foodList: List<FoodEntity>): Int

    fun deleteFoodAll(): Int
}