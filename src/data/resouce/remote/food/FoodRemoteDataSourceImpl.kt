package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.data.database.CategoryTable
import com.adedom.myfood.data.database.FoodTable
import com.adedom.myfood.route.models.entities.FoodAllEntity
import com.adedom.myfood.route.models.entities.FoodEntity
import com.adedom.myfood.route.models.request.InsertFoodRequest
import com.adedom.myfood.utility.constant.AppConstant
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.joda.time.DateTime

class FoodRemoteDataSourceImpl(
    private val db: Database,
) : FoodRemoteDataSource {

    override suspend fun insertFood(insertFoodRequest: InsertFoodRequest, status: String): Int? {
        val (foodName, alias, image, price, description, categoryId) = insertFoodRequest

        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            FoodTable.insert {
                it[FoodTable.foodName] = foodName!!
                it[FoodTable.alias] = alias
                it[FoodTable.image] = image!!
                it[FoodTable.price] = price!!
                it[FoodTable.description] = description
                it[FoodTable.categoryId] = categoryId!!
                it[FoodTable.status] = status
                it[created] = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
            }
        }

        return statement.resultedValues?.size
    }

    override suspend fun getFoodDetail(foodId: Int): FoodEntity? {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FoodTable
                .slice(
                    FoodTable.foodId,
                    FoodTable.foodName,
                    FoodTable.alias,
                    FoodTable.image,
                    FoodTable.price,
                    FoodTable.description,
                    FoodTable.categoryId,
                    FoodTable.status,
                    FoodTable.created,
                    FoodTable.updated,
                )
                .select {
                    FoodTable.foodId eq foodId
                }
                .map { row ->
                    FoodEntity(
                        foodId = row[FoodTable.foodId],
                        foodName = row[FoodTable.foodName],
                        alias = row[FoodTable.alias],
                        image = row[FoodTable.image],
                        price = row[FoodTable.price],
                        description = row[FoodTable.description],
                        categoryId = row[FoodTable.categoryId],
                        status = row[FoodTable.status],
                        created = row[FoodTable.created],
                        updated = row[FoodTable.updated],
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun getFoodByCategoryId(categoryId: Int): List<FoodEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FoodTable
                .slice(
                    FoodTable.foodId,
                    FoodTable.foodName,
                    FoodTable.alias,
                    FoodTable.image,
                    FoodTable.price,
                    FoodTable.description,
                    FoodTable.categoryId,
                    FoodTable.status,
                    FoodTable.created,
                    FoodTable.updated,
                )
                .select {
                    FoodTable.categoryId eq categoryId
                }
                .map { row ->
                    FoodEntity(
                        foodId = row[FoodTable.foodId],
                        foodName = row[FoodTable.foodName],
                        alias = row[FoodTable.alias],
                        image = row[FoodTable.image],
                        price = row[FoodTable.price],
                        description = row[FoodTable.description],
                        categoryId = row[FoodTable.categoryId],
                        status = row[FoodTable.status],
                        created = row[FoodTable.created],
                        updated = row[FoodTable.updated],
                    )
                }
        }
    }

    override suspend fun getFoodAndCategoryAll(): List<FoodAllEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            (FoodTable innerJoin CategoryTable)
                .slice(
                    FoodTable.foodId,
                    FoodTable.foodName,
                    FoodTable.alias,
                    FoodTable.image,
                    FoodTable.price,
                    FoodTable.description,
                    FoodTable.status,
                    FoodTable.created,
                    FoodTable.updated,
                    CategoryTable.categoryId,
                    CategoryTable.categoryName,
                )
                .selectAll()
                .map { row ->
                    FoodAllEntity(
                        foodId = row[FoodTable.foodId],
                        foodName = row[FoodTable.foodName],
                        alias = row[FoodTable.alias],
                        image = row[FoodTable.image],
                        price = row[FoodTable.price],
                        description = row[FoodTable.description],
                        status = row[FoodTable.status],
                        created = row[FoodTable.created],
                        updated = row[FoodTable.updated],
                        categoryId = row[CategoryTable.categoryId],
                        categoryName = row[CategoryTable.categoryName],
                    )
                }
        }
    }

    override suspend fun getFoodAll(): List<FoodEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FoodTable
                .slice(
                    FoodTable.foodId,
                    FoodTable.foodName,
                    FoodTable.alias,
                    FoodTable.image,
                    FoodTable.price,
                    FoodTable.description,
                    FoodTable.categoryId,
                    FoodTable.status,
                    FoodTable.created,
                    FoodTable.updated,
                )
                .selectAll()
                .map { row ->
                    FoodEntity(
                        foodId = row[FoodTable.foodId],
                        foodName = row[FoodTable.foodName],
                        alias = row[FoodTable.alias],
                        image = row[FoodTable.image],
                        price = row[FoodTable.price],
                        description = row[FoodTable.description],
                        categoryId = row[FoodTable.categoryId],
                        status = row[FoodTable.status],
                        created = row[FoodTable.created],
                        updated = row[FoodTable.updated],
                    )
                }
        }
    }
}