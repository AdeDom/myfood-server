package com.adedom.myfood.data.resouce.local.favorite

import com.adedom.myfood.route.models.entities.FavoriteEntity

interface FavoriteLocalDataSource {

    suspend fun getFavoriteAll(): List<FavoriteEntity>

    suspend fun replaceFavorite(
        favoriteId: String,
        userId: String,
        foodId: Int,
        isFavorite: Int,
        isBackup: Int,
        created: String,
        updated: String?,
    ): Int?

    suspend fun deleteFavoriteAll(): Int

    suspend fun findFavoriteEntityByUserIdAndFoodId(userId: String, foodId: Int): FavoriteEntity?
}