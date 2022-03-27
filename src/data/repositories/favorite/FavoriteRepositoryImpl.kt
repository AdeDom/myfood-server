package com.adedom.myfood.data.repositories.favorite

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.local.favorite.FavoriteLocalDataSource
import com.adedom.myfood.route.models.base.BaseError
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.MyFavoriteRequest
import com.adedom.myfood.route.models.response.FavoriteResponse
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import org.joda.time.DateTime
import java.util.*

class FavoriteRepositoryImpl(
    private val favoriteLocalDataSource: FavoriteLocalDataSource,
) : FavoriteRepository {

    override suspend fun getFavoriteAll(): Resource<BaseResponse<List<FavoriteResponse>>> {
        val response = BaseResponse<List<FavoriteResponse>>()

        val favoriteEntityList = favoriteLocalDataSource.getFavoriteAll()
        val favoriteResponseList = favoriteEntityList.map { favoriteEntity ->
            FavoriteResponse(
                favoriteId = favoriteEntity.favoriteId,
                userId = favoriteEntity.userId,
                foodId = favoriteEntity.foodId,
                backupState = favoriteEntity.backupState,
                created = favoriteEntity.created,
                updated = favoriteEntity.updated,
            )
        }
        response.result = favoriteResponseList
        response.status = ResponseKeyConstant.SUCCESS
        return Resource.Success(response)
    }

    override suspend fun myFavorite(
        userId: String,
        myFavoriteRequest: MyFavoriteRequest,
    ): Resource<BaseResponse<String>> {
        val (favoriteId, foodId) = myFavoriteRequest
        val response = BaseResponse<String>()

        val favoriteIdForCreated = UUID.randomUUID().toString().replace("-", "")
        val currentDateTime = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
        val myFavoriteCount = favoriteLocalDataSource.myFavorite(
            favoriteId ?: favoriteIdForCreated,
            userId,
            foodId!!,
            AppConstant.LOCAL_BACKUP,
            currentDateTime.toString("yyyy/M/d H:m"),
        ) ?: 0
        return if (myFavoriteCount == 1) {
            response.result = "Favorite is success."
            response.status = ResponseKeyConstant.SUCCESS
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Favorite is failed.")
            Resource.Error(response)
        }
    }

    override suspend fun deleteFavoriteAll(): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val favoriteEntityList = favoriteLocalDataSource.getFavoriteAll()
        val deleteFavoriteCount = favoriteLocalDataSource.deleteFavoriteAll()
        return if (deleteFavoriteCount == favoriteEntityList.size) {
            response.result = "Delete favorite all is success."
            response.status = ResponseKeyConstant.SUCCESS
            Resource.Success(response)
        } else {
            response.error = BaseError(message = "Delete favorite all is failed.")
            Resource.Error(response)
        }
    }
}