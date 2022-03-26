package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.remote.food.FoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSource
import com.adedom.myfood.route.models.entities.MyFoodEntity
import com.adedom.myfood.route.models.request.InsertFoodRequest
import com.adedom.myfood.route.models.response.base.BaseError
import com.adedom.myfood.route.models.response.base.BaseResponse
import com.adedom.myfood.route.models.response.base.FoodDetailResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import org.joda.time.DateTime

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

    override fun insertFood(insertFoodRequest: InsertFoodRequest): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val isInsertFood = foodRemoteDataSource.insertFood(insertFoodRequest, AppConstant.ACTIVE) == 1
        return if (isInsertFood) {
            response.status = ResponseKeyConstant.SUCCESS
            response.result = "Insert food success."
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Insert food failed.")
            Resource.Error(response)
        }
    }

    override fun getFoodDetail(foodId: Int): Resource<BaseResponse<FoodDetailResponse>> {
        val response = BaseResponse<FoodDetailResponse>()

        val foodEntity = foodRemoteDataSource.getFoodDetail(foodId)
        return if (foodEntity != null) {
            response.status = ResponseKeyConstant.SUCCESS
            val foodDetailResponse = FoodDetailResponse(
                foodId = foodEntity.foodId,
                foodName = foodEntity.foodName,
                alias = foodEntity.alias,
                image = foodEntity.image,
                price = foodEntity.price,
                description = foodEntity.description,
                categoryId = foodEntity.categoryId,
                status = foodEntity.status,
                created = toDateTimeString(foodEntity.created).orEmpty(),
                updated = toDateTimeString(foodEntity.updated),
            )
            response.result = foodDetailResponse
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Food is null or blank.")
            Resource.Error(response)
        }
    }

    private fun toDateTimeString(dateTime: DateTime?): String? {
        return dateTime?.toString("d/M/yyyy H:m")
    }
}