package com.adedom.myfood.data.resouce.local.favorite

import com.adedom.myfood.data.database.local.FavoriteTableSqlite
import com.adedom.myfood.route.models.entities.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.selectAll
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
                    FavoriteTableSqlite.created,
                    FavoriteTableSqlite.updated,
                )
                .selectAll()
                .map { row ->
                    FavoriteEntity(
                        favoriteId = row[FavoriteTableSqlite.favoriteId],
                        userId = row[FavoriteTableSqlite.userId],
                        foodId = row[FavoriteTableSqlite.foodId],
                        created = row[FavoriteTableSqlite.created],
                        updated = row[FavoriteTableSqlite.updated],
                    )
                }
        }
    }

    override suspend fun myFavorite(userId: String, foodId: Int, created: String): Int? {
        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            FavoriteTableSqlite.replace {
                it[FavoriteTableSqlite.userId] = userId
                it[FavoriteTableSqlite.foodId] = foodId
                it[FavoriteTableSqlite.created] = created
            }
        }

        return statement.resultedValues?.size
    }

    override suspend fun deleteFavoriteAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FavoriteTableSqlite.deleteAll()
        }
    }
}