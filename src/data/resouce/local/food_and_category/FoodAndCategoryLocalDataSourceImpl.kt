package com.adedom.myfood.data.resouce.local.food_and_category

import com.adedom.myfood.data.database.h2.FoodAndCategoryTableH2
import com.adedom.myfood.data.models.entities.FoodAndCategoryEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class FoodAndCategoryLocalDataSourceImpl(
    private val db: Database,
) : FoodAndCategoryLocalDataSource {

    override suspend fun getFoodAndCategoryAll(): List<FoodAndCategoryEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FoodAndCategoryTableH2
                .slice(
                    FoodAndCategoryTableH2.foodAndCategoryId,
                    FoodAndCategoryTableH2.foodId,
                    FoodAndCategoryTableH2.foodName,
                    FoodAndCategoryTableH2.alias,
                    FoodAndCategoryTableH2.foodImage,
                    FoodAndCategoryTableH2.price,
                    FoodAndCategoryTableH2.description,
                    FoodAndCategoryTableH2.status,
                    FoodAndCategoryTableH2.foodCreated,
                    FoodAndCategoryTableH2.foodUpdated,
                    FoodAndCategoryTableH2.categoryId,
                    FoodAndCategoryTableH2.categoryName,
                    FoodAndCategoryTableH2.categoryImage,
                    FoodAndCategoryTableH2.categoryCreated,
                    FoodAndCategoryTableH2.categoryUpdated,
                )
                .selectAll()
                .map { row ->
                    FoodAndCategoryEntity(
                        foodAndCategoryId = row[FoodAndCategoryTableH2.foodAndCategoryId],
                        foodId = row[FoodAndCategoryTableH2.foodId],
                        foodName = row[FoodAndCategoryTableH2.foodName],
                        alias = row[FoodAndCategoryTableH2.alias],
                        foodImage = row[FoodAndCategoryTableH2.foodImage],
                        price = row[FoodAndCategoryTableH2.price],
                        description = row[FoodAndCategoryTableH2.description],
                        status = row[FoodAndCategoryTableH2.status],
                        foodCreated = row[FoodAndCategoryTableH2.foodCreated],
                        foodUpdated = row[FoodAndCategoryTableH2.foodUpdated],
                        categoryId = row[FoodAndCategoryTableH2.categoryId],
                        categoryName = row[FoodAndCategoryTableH2.categoryName],
                        categoryImage = row[FoodAndCategoryTableH2.categoryImage],
                        categoryCreated = row[FoodAndCategoryTableH2.categoryCreated],
                        categoryUpdated = row[FoodAndCategoryTableH2.categoryUpdated],
                    )
                }
        }
    }

    override suspend fun insertFoodAndCategoryAll(foodAndCategoryAll: List<FoodAndCategoryEntity>): Int {
        val statement = newSuspendedTransaction(Dispatchers.IO, db) {
            FoodAndCategoryTableH2.batchInsert(foodAndCategoryAll) { foodAndCategoryAllEntity ->
                this[FoodAndCategoryTableH2.foodId] = foodAndCategoryAllEntity.foodId
                this[FoodAndCategoryTableH2.foodName] = foodAndCategoryAllEntity.foodName
                this[FoodAndCategoryTableH2.alias] = foodAndCategoryAllEntity.alias
                this[FoodAndCategoryTableH2.foodImage] = foodAndCategoryAllEntity.foodImage
                this[FoodAndCategoryTableH2.price] = foodAndCategoryAllEntity.price
                this[FoodAndCategoryTableH2.description] = foodAndCategoryAllEntity.description
                this[FoodAndCategoryTableH2.status] = foodAndCategoryAllEntity.status
                this[FoodAndCategoryTableH2.foodCreated] = foodAndCategoryAllEntity.foodCreated
                this[FoodAndCategoryTableH2.foodUpdated] = foodAndCategoryAllEntity.foodUpdated
                this[FoodAndCategoryTableH2.categoryId] = foodAndCategoryAllEntity.categoryId
                this[FoodAndCategoryTableH2.categoryName] = foodAndCategoryAllEntity.categoryName
                this[FoodAndCategoryTableH2.categoryImage] = foodAndCategoryAllEntity.categoryImage
                this[FoodAndCategoryTableH2.categoryCreated] = foodAndCategoryAllEntity.categoryCreated
                this[FoodAndCategoryTableH2.categoryUpdated] = foodAndCategoryAllEntity.categoryUpdated
            }
        }

        return statement.size
    }

    override suspend fun deleteFoodAndCategoryAll(): Int {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            FoodAndCategoryTableH2.deleteAll()
        }
    }
}