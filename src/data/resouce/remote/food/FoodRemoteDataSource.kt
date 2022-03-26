package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.route.models.entities.FoodAllEntity
import com.adedom.myfood.route.models.entities.FoodEntity
import com.adedom.myfood.route.models.request.InsertFoodRequest

interface FoodRemoteDataSource {

    fun insertFood(insertFoodRequest: InsertFoodRequest, status: String): Int?

    fun getFoodDetail(foodId: Int): FoodEntity?

    fun getFoodByCategoryId(categoryId: Int): List<FoodEntity>

    fun getFoodAll(): List<FoodAllEntity>
}