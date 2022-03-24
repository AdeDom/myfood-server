package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.data.database.FoodTable
import com.adedom.myfood.route.models.request.InsertFoodRequest
import com.adedom.myfood.utility.constant.AppConstant
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

class FoodRemoteDataSourceImpl : FoodRemoteDataSource {

    override fun insertFood(insertFoodRequest: InsertFoodRequest): Int? {
        val (foodName, alias, image, price, description, categoryId) = insertFoodRequest

        val statement = transaction {
            FoodTable.insert {
                it[FoodTable.foodName] = foodName!!
                it[FoodTable.alias] = alias
                it[FoodTable.image] = image!!
                it[FoodTable.price] = price!!
                it[FoodTable.description] = description
                it[FoodTable.categoryId] = categoryId!!
                it[created] = DateTime(System.currentTimeMillis() + AppConstant.DATE_TIME_THAI)
            }
        }

        return statement.resultedValues?.size
    }
}