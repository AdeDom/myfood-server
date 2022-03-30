package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.data.models.entities.MyFoodEntity

interface MyFoodRemoteDataSource {

    suspend fun getMyFood(): List<MyFoodEntity>
}