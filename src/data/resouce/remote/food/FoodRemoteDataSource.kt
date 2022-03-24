package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.route.models.request.InsertFoodRequest

interface FoodRemoteDataSource {

    fun insertFood(insertFoodRequest: InsertFoodRequest): Int?
}