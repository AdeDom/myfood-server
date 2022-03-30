package com.adedom.myfood.data.repositories.food

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.entities.MyFoodEntity
import com.adedom.myfood.data.models.request.InsertFoodRequest
import com.adedom.myfood.data.models.response.FoodAndCategoryResponse
import com.adedom.myfood.data.models.response.FoodDetailResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.local.food.FoodLocalDataSource
import com.adedom.myfood.data.resouce.local.food_and_category.FoodAndCategoryLocalDataSource
import com.adedom.myfood.data.resouce.remote.food.FoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSource
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant

class FoodRepositoryImpl(
    private val foodLocalDataSource: FoodLocalDataSource,
    private val foodAndCategoryLocalDataSource: FoodAndCategoryLocalDataSource,
    private val myFoodRemoteDataSource: MyFoodRemoteDataSource,
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

        val foodList = foodLocalDataSource.getFoodAll()
        val foodEntity = if (foodList.isEmpty()) {
            val foodAll = foodRemoteDataSource.getFoodAll()

            foodLocalDataSource.deleteFoodAll()
            val listLocalCount = foodLocalDataSource.insertFoodAll(foodAll)
            if (listLocalCount != foodAll.size) {
                foodLocalDataSource.deleteFoodAll()
            }
            foodLocalDataSource.getFoodDetail(foodId)
        } else {
            foodLocalDataSource.getFoodDetail(foodId)
        }

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
                created = foodEntity.created.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
                updated = foodEntity.updated?.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
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
                created = foodEntity.created.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
                updated = foodEntity.updated?.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
            )
        }
        response.status = ResponseKeyConstant.SUCCESS
        response.result = foodListResponse
        return Resource.Success(response)
    }

    override suspend fun getFoodAndCategoryAll(): Resource<BaseResponse<List<FoodAndCategoryResponse>>> {
        val response = BaseResponse<List<FoodAndCategoryResponse>>()

        var foodAllList = foodAndCategoryLocalDataSource.getFoodAndCategoryAll()
        if (foodAllList.isEmpty()) {
            foodAllList = foodRemoteDataSource.getFoodAndCategoryAll()

            foodAndCategoryLocalDataSource.deleteFoodAndCategoryAll()
            val listLocalCount = foodAndCategoryLocalDataSource.insertFoodAndCategoryAll(foodAllList)
            if (listLocalCount != foodAllList.size) {
                foodAndCategoryLocalDataSource.deleteFoodAndCategoryAll()
            }
        }

        val foodAllListResponse = foodAllList.map { foodAllEntity ->
            FoodAndCategoryResponse(
                foodAndCategoryId = foodAllEntity.foodAndCategoryId,
                foodId = foodAllEntity.foodId,
                foodName = foodAllEntity.foodName,
                alias = foodAllEntity.alias,
                foodImage = foodAllEntity.foodImage,
                price = foodAllEntity.price,
                description = foodAllEntity.description,
                status = foodAllEntity.status,
                foodCreated = foodAllEntity.foodCreated.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
                foodUpdated = foodAllEntity.foodUpdated?.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
                categoryId = foodAllEntity.categoryId,
                categoryName = foodAllEntity.categoryName,
                categoryImage = foodAllEntity.categoryImage,
                categoryCreated = foodAllEntity.categoryCreated.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
                categoryUpdated = foodAllEntity.categoryUpdated?.toString(AppConstant.DATE_TIME_FORMAT_RESPONSE),
            )
        }
        response.status = ResponseKeyConstant.SUCCESS
        response.result = foodAllListResponse
        return Resource.Success(response)
    }
}