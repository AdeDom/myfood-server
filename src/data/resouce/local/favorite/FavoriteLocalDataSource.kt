package com.adedom.myfood.data.resouce.local.favorite

import com.adedom.myfood.route.models.entities.FavoriteEntity

interface FavoriteLocalDataSource {

    suspend fun getFavoriteAll(): List<FavoriteEntity>

    suspend fun myFavorite(userId: String, foodId: Int, created: String): Int?

    suspend fun deleteFavoriteAll(): Int
}