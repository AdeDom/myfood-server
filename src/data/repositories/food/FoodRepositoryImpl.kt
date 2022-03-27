package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.local.food.FoodLocalDataSource
import com.adedom.myfood.data.resouce.remote.food.FoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSource
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.entities.MyFoodEntity
import com.adedom.myfood.route.models.request.InsertFoodRequest
import com.adedom.myfood.route.models.response.FoodAllResponse
import com.adedom.myfood.route.models.response.FoodDetailResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class FoodRepositoryImpl(
    private val myFoodRemoteDataSource: MyFoodRemoteDataSource,
    private val foodLocalDataSource: FoodLocalDataSource,
    private val foodRemoteDataSource: FoodRemoteDataSource,
) : FoodRepository {

    override suspend fun getMyFood(): Resource<BaseResponse<List<MyFoodEntity>>> {
        val response = BaseResponse<List<MyFoodEntity>>()

        val myFoods = myFoodRemoteDataSource.getMyFood()
        response.status = ResponseKeyConstant.SUCCESS
        response.result = myFoods
        return Resource.Success(response)
    }

    override suspend fun insertFood(insertFoodRequest: InsertFoodRequest): Resource<BaseResponse<String>> {
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

    override suspend fun getFoodDetail(foodId: Int): Resource<BaseResponse<FoodDetailResponse>> {
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
                created = foodEntity.created.toString(AppConstant.DATE_TIME_FORMAT),
                updated = foodEntity.updated?.toString(AppConstant.DATE_TIME_FORMAT),
            )
            response.result = foodDetailResponse
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Food is null or blank.")
            Resource.Error(response)
        }
    }

    override suspend fun getFoodByCategoryId(categoryId: Int): Resource<BaseResponse<List<FoodDetailResponse>>> {
        val response = BaseResponse<List<FoodDetailResponse>>()

        var foodList = foodLocalDataSource.getFoodAll()
        foodList = if (foodList.isEmpty()) {
            val foodAll = foodRemoteDataSource.getFoodAll()

            foodLocalDataSource.deleteFoodAll()
            val listLocalCount = foodLocalDataSource.insertFoodAll(foodAll)
            if (listLocalCount != foodAll.size) {
                foodLocalDataSource.deleteFoodAll()
            }
            foodLocalDataSource.getFoodByCategoryId(categoryId)
        } else {
            foodLocalDataSource.getFoodByCategoryId(categoryId)
        }

        val foodListResponse = foodList.map { foodEntity ->
            FoodDetailResponse(
                foodId = foodEntity.foodId,
                foodName = foodEntity.foodName,
                alias = foodEntity.alias,
                image = foodEntity.image,
                price = foodEntity.price,
                description = foodEntity.description,
                categoryId = foodEntity.categoryId,
                status = foodEntity.status,
                created = foodEntity.created.toString(AppConstant.DATE_TIME_FORMAT),
                updated = foodEntity.updated?.toString(AppConstant.DATE_TIME_FORMAT),
            )
        }
        response.status = ResponseKeyConstant.SUCCESS
        response.result = foodListResponse
        return Resource.Success(response)
    }

    override suspend fun getFoodAndCategoryAll(): Resource<BaseResponse<List<FoodAllResponse>>> {
        val response = BaseResponse<List<FoodAllResponse>>()

        val foodAllList = foodRemoteDataSource.getFoodAndCategoryAll()
        val foodAllListResponse = foodAllList.map { foodAllEntity ->
            FoodAllResponse(
                foodId = foodAllEntity.foodId,
                foodName = foodAllEntity.foodName,
                alias = foodAllEntity.alias,
                image = foodAllEntity.image,
                price = foodAllEntity.price,
                description = foodAllEntity.description,
                status = foodAllEntity.status,
                created = foodAllEntity.created.toString(AppConstant.DATE_TIME_FORMAT),
                updated = foodAllEntity.updated?.toString(AppConstant.DATE_TIME_FORMAT),
                categoryId = foodAllEntity.categoryId,
                categoryName = foodAllEntity.categoryName,
            )
        }
        response.status = ResponseKeyConstant.SUCCESS
        response.result = foodAllListResponse
        return Resource.Success(response)
    }
}