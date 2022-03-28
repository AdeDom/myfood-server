package com.adedom.myfood.data.resouce.local.favorite

import com.adedom.myfood.data.database.local.FavoriteTableSqlite
import com.adedom.myfood.route.models.entities.FavoriteEntity
import com.adedom.myfood.utility.constant.AppConstant
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class FavoriteLocalDataSourceImpl(
    private val db: Database
) : FavoriteLocalDataSource {

    override suspend fun getFavoriteAll(): List<FavoriteEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FavoriteTableSqlite
                .slice(
                    FavoriteTableSqlite.favoriteId,
                    FavoriteTableSqlite.userId,
                    FavoriteTableSqlite.foodId,
                    FavoriteTableSqlite.isFavorite,
                    FavoriteTableSqlite.isBackup,
                    FavoriteTableSqlite.created,
                    FavoriteTableSqlite.updated,
                )
                .selectAll()
                .map { row ->
                    FavoriteEntity(
                        favoriteId = row[FavoriteTableSqlite.favoriteId],
                        userId = row[FavoriteTableSqlite.userId],
                        foodId = row[FavoriteTableSqlite.foodId],
                        isFavorite = row[FavoriteTableSqlite.isFavorite],
                        isBackup = row[FavoriteTableSqlite.isBackup],
                        created = row[FavoriteTableSqlite.created],
                        updated = row[FavoriteTableSqlite.updated],
                    )
                }
        }
    }

    override suspend fun replaceFavorite(
        favoriteId: String,
        userId: String,
        foodId: Int,
        isFavorite: Int,
        isBackup: Int,
        created: String,
        updated: String?,
    ): Int? {
        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            FavoriteTableSqlite.replace {
                it[FavoriteTableSqlite.favoriteId] = favoriteId
                it[FavoriteTableSqlite.userId] = userId
                it[FavoriteTableSqlite.foodId] = foodId
                it[FavoriteTableSqlite.isFavorite] = isFavorite
                it[FavoriteTableSqlite.isBackup] = isBackup
                it[FavoriteTableSqlite.created] = created
                it[FavoriteTableSqlite.updated] = updated
            }
        }

        return statement.resultedValues?.size
    }

    override suspend fun deleteFavoriteAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FavoriteTableSqlite.deleteAll()
        }
    }

    override suspend fun findFavoriteEntityByUserIdAndFoodId(userId: String, foodId: Int): FavoriteEntity? {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FavoriteTableSqlite
                .slice(
                    FavoriteTableSqlite.favoriteId,
                    FavoriteTableSqlite.userId,
                    FavoriteTableSqlite.foodId,
                    FavoriteTableSqlite.isFavorite,
                    FavoriteTableSqlite.isBackup,
                    FavoriteTableSqlite.created,
                    FavoriteTableSqlite.updated,
                )
                .select {
                    (FavoriteTableSqlite.userId eq userId) and (FavoriteTableSqlite.foodId eq foodId)
                }
                .map { row ->
                    FavoriteEntity(
                        favoriteId = row[FavoriteTableSqlite.favoriteId],
                        userId = row[FavoriteTableSqlite.userId],
                        foodId = row[FavoriteTableSqlite.foodId],
                        isFavorite = row[FavoriteTableSqlite.isFavorite],
                        isBackup = row[FavoriteTableSqlite.isBackup],
                        created = row[FavoriteTableSqlite.created],
                        updated = row[FavoriteTableSqlite.updated],
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun getFavoriteListByBackupIsLocal(): List<FavoriteEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FavoriteTableSqlite
                .slice(
                    FavoriteTableSqlite.favoriteId,
                    FavoriteTableSqlite.userId,
                    FavoriteTableSqlite.foodId,
                    FavoriteTableSqlite.isFavorite,
                    FavoriteTableSqlite.isBackup,
                    FavoriteTableSqlite.created,
                    FavoriteTableSqlite.updated,
                )
                .select {
                    FavoriteTableSqlite.isBackup eq AppConstant.LOCAL_BACKUP
                }
                .map { row ->
                    FavoriteEntity(
                        favoriteId = row[FavoriteTableSqlite.favoriteId],
                        userId = row[FavoriteTableSqlite.userId],
                        foodId = row[FavoriteTableSqlite.foodId],
                        isFavorite = row[FavoriteTableSqlite.isFavorite],
                        isBackup = row[FavoriteTableSqlite.isBackup],
                        created = row[FavoriteTableSqlite.created],
                        updated = row[FavoriteTableSqlite.updated],
                    )
                }
        }
    }

    override suspend fun updateFavoriteByBackupIsRemote(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FavoriteTableSqlite.update({ FavoriteTableSqlite.isBackup eq AppConstant.LOCAL_BACKUP }) {
                it[isBackup] = AppConstant.REMOTE_BACKUP
            }
        }
    }
}