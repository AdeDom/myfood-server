package com.adedom.myfood.data.resouce.remote.favorite

import com.adedom.myfood.data.database.remote.FavoriteTable
import com.adedom.myfood.route.models.entities.FavoriteEntity
import com.adedom.myfood.utility.constant.AppConstant
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchReplace
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class FavoriteRemoteDataSourceImpl(
    private val db: Database
) : FavoriteRemoteDataSource {

    override suspend fun replaceFavorite(favoriteList: List<FavoriteEntity>): Int {
        val dateTimeFormat = DateTimeFormat.forPattern(AppConstant.DATE_TIME_FORMAT_REQUEST)
        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            FavoriteTable.batchReplace(favoriteList) { favoriteEntity ->
                this[FavoriteTable.favoriteId] = favoriteEntity.favoriteId
                this[FavoriteTable.userId] = favoriteEntity.userId
                this[FavoriteTable.foodId] = favoriteEntity.foodId
                this[FavoriteTable.isFavorite] = favoriteEntity.isFavorite == AppConstant.FAVORITE
                this[FavoriteTable.created] = DateTime.parse(favoriteEntity.created, dateTimeFormat)
                favoriteEntity.updated?.let {
                    this[FavoriteTable.updated] = DateTime.parse(favoriteEntity.updated, dateTimeFormat)
                }
            }
        }

        return statement.size
    }
}