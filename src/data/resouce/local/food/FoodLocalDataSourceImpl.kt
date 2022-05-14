package com.adedom.myfood.data.resouce.local.food

import com.adedom.myfood.data.models.entities.FoodEntity

class FoodLocalDataSourceImpl : FoodLocalDataSource {

    private val foodList = mutableListOf<FoodEntity>()

    override suspend fun getFoodDetail(foodId: Int): FoodEntity? {
        return this.foodList.find { foodEntity ->
            foodEntity.foodId == foodId
        }
    }

    override suspend fun getFoodByCategoryId(categoryId: Int): List<FoodEntity> {
        return this.foodList.filter { foodEntity ->
            foodEntity.categoryId == categoryId
        }
    }

    override suspend fun getFoodAll(): List<FoodEntity> {
        return this.foodList
    }

    override suspend fun insertFoodAll(foodList: List<FoodEntity>): Int {
        this.foodList.addAll(foodList)
        return this.foodList.size
    }

    override suspend fun deleteFoodAll() {
        this.foodList.clear()
    }
}