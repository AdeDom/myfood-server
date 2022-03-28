package com.adedom.myfood.data.resouce.remote.favorite

import com.adedom.myfood.route.models.entities.FavoriteEntity

interface FavoriteRemoteDataSource {

    suspend fun replaceFavoriteAll(favoriteList: List<FavoriteEntity>): Int

    suspend fun getFavoriteAll(): List<FavoriteEntity>
}