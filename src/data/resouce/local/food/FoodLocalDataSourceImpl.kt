package com.adedom.myfood.data.resouce.local.food

import com.adedom.myfood.data.database.FoodTableH2
import com.adedom.myfood.route.models.entities.FoodEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class FoodLocalDataSourceImpl(
    private val db: Database,
) : FoodLocalDataSource {

    override suspend fun getFoodDetail(foodId: Int): FoodEntity? {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FoodTableH2
                .slice(
                    FoodTableH2.foodId,
                    FoodTableH2.foodName,
                    FoodTableH2.alias,
                    FoodTableH2.image,
                    FoodTableH2.price,
                    FoodTableH2.description,
                    FoodTableH2.categoryId,
                    FoodTableH2.status,
                    FoodTableH2.created,
                    FoodTableH2.updated,
                )
                .select {
                    FoodTableH2.foodId eq foodId
                }
                .map { row ->
                    FoodEntity(
                        foodId = row[FoodTableH2.foodId],
                        foodName = row[FoodTableH2.foodName],
                        alias = row[FoodTableH2.alias],
                        image = row[FoodTableH2.image],
                        price = row[FoodTableH2.price],
                        description = row[FoodTableH2.description],
                        categoryId = row[FoodTableH2.categoryId],
                        status = row[FoodTableH2.status],
                        created = row[FoodTableH2.created],
                        updated = row[FoodTableH2.updated],
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun getFoodByCategoryId(categoryId: Int): List<FoodEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FoodTableH2
                .slice(
                    FoodTableH2.foodId,
                    FoodTableH2.foodName,
                    FoodTableH2.alias,
                    FoodTableH2.image,
                    FoodTableH2.price,
                    FoodTableH2.description,
                    FoodTableH2.categoryId,
                    FoodTableH2.status,
                    FoodTableH2.created,
                    FoodTableH2.updated,
                )
                .select {
                    FoodTableH2.categoryId eq categoryId
                }
                .map { row ->
                    FoodEntity(
                        foodId = row[FoodTableH2.foodId],
                        foodName = row[FoodTableH2.foodName],
                        alias = row[FoodTableH2.alias],
                        image = row[FoodTableH2.image],
                        price = row[FoodTableH2.price],
                        description = row[FoodTableH2.description],
                        categoryId = row[FoodTableH2.categoryId],
                        status = row[FoodTableH2.status],
                        created = row[FoodTableH2.created],
                        updated = row[FoodTableH2.updated],
                    )
                }
        }
    }

    override suspend fun getFoodAll(): List<FoodEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FoodTableH2
                .slice(
                    FoodTableH2.foodId,
                    FoodTableH2.foodName,
                    FoodTableH2.alias,
                    FoodTableH2.image,
                    FoodTableH2.price,
                    FoodTableH2.description,
                    FoodTableH2.categoryId,
                    FoodTableH2.status,
                    FoodTableH2.created,
                    FoodTableH2.updated,
                )
                .selectAll()
                .map { row ->
                    FoodEntity(
                        foodId = row[FoodTableH2.foodId],
                        foodName = row[FoodTableH2.foodName],
                        alias = row[FoodTableH2.alias],
                        image = row[FoodTableH2.image],
                        price = row[FoodTableH2.price],
                        description = row[FoodTableH2.description],
                        categoryId = row[FoodTableH2.categoryId],
                        status = row[FoodTableH2.status],
                        created = row[FoodTableH2.created],
                        updated = row[FoodTableH2.updated],
                    )
                }
        }
    }

    override suspend fun insertFoodAll(foodList: List<FoodEntity>): Int {
        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            FoodTableH2.batchInsert(foodList) { foodEntity ->
                this[FoodTableH2.foodId] = foodEntity.foodId
                this[FoodTableH2.foodName] = foodEntity.foodName
                this[FoodTableH2.alias] = foodEntity.alias
                this[FoodTableH2.image] = foodEntity.image
                this[FoodTableH2.price] = foodEntity.price
                this[FoodTableH2.description] = foodEntity.description
                this[FoodTableH2.categoryId] = foodEntity.categoryId
                this[FoodTableH2.status] = foodEntity.status
                this[FoodTableH2.created] = foodEntity.created
                this[FoodTableH2.updated] = foodEntity.updated
            }
        }

        return statement.size
    }

    override suspend fun deleteFoodAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FoodTableH2.deleteAll()
        }
    }
}