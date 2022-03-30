package com.adedom.myfood.data.repositories.favorite

import com.adedom.myfood.data.models.base.BaseError
import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FavoriteResponse
import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.data.resouce.local.favorite.FavoriteLocalDataSource
import com.adedom.myfood.data.resouce.remote.favorite.FavoriteRemoteDataSource
import com.adedom.myfood.utility.constant.AppConstant
import com.adedom.myfood.utility.constant.ResponseKeyConstant
import org.joda.time.DateTime
import java.util.*

class FavoriteRepositoryImpl(
    private val favoriteLocalDataSource: FavoriteLocalDataSource,
    private val favoriteRemoteDataSource: FavoriteRemoteDataSource,
) : FavoriteRepository {

    override suspend fun getFavoriteAll(): Resource<BaseResponse<List<FavoriteResponse>>> {
        val response = BaseResponse<List<FavoriteResponse>>()

        val favoriteEntityList = favoriteLocalDataSource.getFavoriteAll()
        val favoriteResponseList = favoriteEntityList.map { favoriteEntity ->
            FavoriteResponse(
                favoriteId = favoriteEntity.favoriteId,
                userId = favoriteEntity.userId,
                foodId = favoriteEntity.foodId,
                isFavorite = favoriteEntity.isFavorite == AppConstant.FAVORITE,
                isBackup = favoriteEntity.isBackup == AppConstant.REMOTE_BACKUP,
                created = favoriteEntity.created,
                updated = favoriteEntity.updated,
            )
        }
        response.result = favoriteResponseList
        response.status = ResponseKeyConstant.SUCCESS
        return Resource.Success(response)
    }

    override suspend fun myFavorite(userId: String, foodId: Int): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val favoriteEntity = favoriteLocalDataSource.findFavoriteEntityByUserIdAndFoodId(userId, foodId)
        val favoriteIdForCreated = UUID.randomUUID().toString().replace("-", "")
        val currentDateTime = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
        val currentDateTimeString = currentDateTime.toString(AppConstant.DATE_TIME_FORMAT_REQUEST)
        val isFavorite = if ((favoriteEntity?.isFavorite ?: AppConstant.UN_FAVORITE) == AppConstant.FAVORITE) {
            AppConstant.UN_FAVORITE
        } else {
            AppConstant.FAVORITE
        }
        val isBackup = AppConstant.LOCAL_BACKUP
        val created = favoriteEntity?.created ?: currentDateTimeString
        val updated = if (favoriteEntity != null) currentDateTimeString else null
        val myFavoriteCount = favoriteLocalDataSource.replaceFavorite(
            favoriteEntity?.favoriteId ?: favoriteIdForCreated,
            userId,
            foodId,
            isFavorite,
            isBackup,
            created,
            updated,
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

    override suspend fun syncDataFavorite(): Resource<BaseResponse<String>> {
        val response = BaseResponse<String>()

        val favoriteLocalList = favoriteLocalDataSource.getFavoriteListByBackupIsLocal()
        val replaceFavoriteRemoteCount = favoriteRemoteDataSource.replaceFavoriteAll(favoriteLocalList)
        return if (favoriteLocalList.size == replaceFavoriteRemoteCount) {
            val updateFavoriteBackupCount = favoriteLocalDataSource.updateFavoriteByBackupIsRemote()
            if (favoriteLocalList.size == updateFavoriteBackupCount) {
                val favoriteRemoteList = favoriteRemoteDataSource.getFavoriteAll()
                val replaceFavoriteLocalCount = favoriteLocalDataSource.replaceFavoriteAll(favoriteRemoteList)
                if (favoriteRemoteList.size == replaceFavoriteLocalCount) {
                    response.result = "Sync data success."
                    response.status = ResponseKeyConstant.SUCCESS
                    Resource.Success(response)
                } else {
                    response.error = BaseError(message = "Sync data failed (3).")
                    Resource.Error(response)
                }
            } else {
                response.error = BaseError(message = "Sync data failed (2).")
                Resource.Error(response)
            }
        } else {
            response.error = BaseError(message = "Sync data failed (1).")
            Resource.Error(response)
        }
    }
}