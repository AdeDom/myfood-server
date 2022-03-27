package com.adedom.myfood.data.resouce.local.favorite

import com.adedom.myfood.route.models.entities.FavoriteEntity

interface FavoriteLocalDataSource {

    suspend fun getFavoriteAll(): List<FavoriteEntity>

    suspend fun myFavorite(favoriteId: String, userId: String, foodId: Int, backupState: Int, created: String): Int?

    suspend fun deleteFavoriteAll(): Int
}