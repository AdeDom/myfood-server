package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.data.db.models.MyFoodEntity

interface MyFoodRemoteDataSource {

    fun getMyFood(): List<MyFoodEntity>
}