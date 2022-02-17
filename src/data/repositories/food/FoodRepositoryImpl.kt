package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSource
import com.adedom.myfood.route.models.entities.MyFoodEntity
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class FoodRepositoryImpl(
    private val dataSource: MyFoodRemoteDataSource,
) : FoodRepository {

    override fun getMyFood(): BaseResponse<List<MyFoodEntity>> {
        val response = BaseResponse<List<MyFoodEntity>>()

        val myFoods = dataSource.getMyFood()
        response.status = ResponseKeyConstant.SUCCESS
        response.result = myFoods

        return response
    }
}