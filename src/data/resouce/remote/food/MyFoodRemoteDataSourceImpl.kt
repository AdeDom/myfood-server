package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.data.database.mysql.MyFoodTable
import com.adedom.myfood.route.models.entities.MyFoodEntity
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class MyFoodRemoteDataSourceImpl(
    private val db: Database,
) : MyFoodRemoteDataSource {

    override suspend fun getMyFood(): List<MyFoodEntity> {
        return newSuspendedTransaction(Dispatchers.IO, db) {
            MyFoodTable
                .slice(
                    MyFoodTable.id,
                    MyFoodTable.foodDefault,
                )
                .selectAll()
                .map { row ->
                    MyFoodEntity(
                        id = row[MyFoodTable.id],
                        foodDefault = row[MyFoodTable.foodDefault],
                    )
                }
        }
    }
}