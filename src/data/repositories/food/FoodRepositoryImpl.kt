package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.data.resouce.remote.food.FoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSource
import com.adedom.myfood.route.models.entities.MyFoodEntity
import com.adedom.myfood.route.models.request.InsertFoodRequest
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class FoodRepositoryImpl(
    private val dataSource: MyFoodRemoteDataSource,
    private val foodRemoteDataSource: FoodRemoteDataSource,
) : FoodRepository {

    override fun getMyFood(): BaseResponse<List<MyFoodEntity>> {
        val response = BaseResponse<List<MyFoodEntity>>()

        val myFoods = dataSource.getMyFood()
        response.status = ResponseKeyConstant.SUCCESS
        response.result = myFoods

        return response
    }

    override fun insertFood(insertFoodRequest: InsertFoodRequest): Int? {
        return foodRemoteDataSource.insertFood(insertFoodRequest, AppConstant.ACTIVE)
    }
}