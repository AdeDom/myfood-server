package com.adedom.myfood.data.repositories.favorite

import com.adedom.myfood.data.repositories.Resource
import com.adedom.myfood.route.models.base.BaseResponse
import com.adedom.myfood.route.models.request.MyFavoriteRequest
import com.adedom.myfood.route.models.response.FavoriteResponse

interface FavoriteRepository {

    suspend fun getFavoriteAll(): Resource<BaseResponse<List<FavoriteResponse>>>

    suspend fun myFavorite(userId: String, myFavoriteRequest: MyFavoriteRequest): Resource<BaseResponse<String>>

    suspend fun deleteFavoriteAll(): Resource<BaseResponse<String>>
}