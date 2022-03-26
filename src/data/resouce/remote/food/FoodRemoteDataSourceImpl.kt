package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.data.database.FoodTable
import com.adedom.myfood.route.models.entities.FoodEntity
import com.adedom.myfood.route.models.request.InsertFoodRequest
import com.adedom.myfood.utility.constant.AppConstant
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class FoodRemoteDataSourceImpl : FoodRemoteDataSource {

    override fun insertFood(insertFoodRequest: InsertFoodRequest, status: String): Int? {
        val (foodName, alias, image, price, description, categoryId) = insertFoodRequest

        val statement = transaction {
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

    override fun getFoodDetail(foodId: Int): FoodEntity? {
        return transaction {
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

    override fun getFoodByCategoryId(categoryId: Int): List<FoodEntity> {
        return transaction {
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
}