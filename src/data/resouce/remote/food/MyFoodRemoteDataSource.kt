package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.route.models.entities.MyFoodEntity

interface MyFoodRemoteDataSource {

    fun getMyFood(): List<MyFoodEntity>
}