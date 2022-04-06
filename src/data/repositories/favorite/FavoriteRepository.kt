package com.adedom.myfood.data.repositories.favorite

import com.adedom.myfood.data.models.base.BaseResponse
import com.adedom.myfood.data.models.response.FavoriteResponse
import com.adedom.myfood.data.models.web_sockets.FavoriteWebSocketsResponse
import com.adedom.myfood.data.repositories.Resource

interface FavoriteRepository {

    suspend fun getFavoriteAll(): Resource<BaseResponse<List<FavoriteResponse>>>

    suspend fun myFavorite(userId: String, foodId: Int): Resource<BaseResponse<FavoriteWebSocketsResponse>>

    suspend fun deleteFavoriteAll(): Resource<BaseResponse<String>>

    suspend fun syncDataFavorite(): Resource<BaseResponse<String>>
}