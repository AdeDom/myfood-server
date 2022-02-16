package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.data.db.dao.MyFoodDao
import com.adedom.myfood.data.db.models.MyFoodEntity

class MyFoodRemoteDataSourceImpl(
    private val dao: MyFoodDao,
) : MyFoodRemoteDataSource {

    override fun getMyFood(): List<MyFoodEntity> {
        return dao.getMyFood()
    }
}