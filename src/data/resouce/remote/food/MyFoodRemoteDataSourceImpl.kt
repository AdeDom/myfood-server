package com.adedom.myfood.data.resouce.remote.food

import com.adedom.myfood.data.database.MyFoodTable
import com.adedom.myfood.route.models.entities.MyFoodEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class MyFoodRemoteDataSourceImpl(
    private val db: Database,
) : MyFoodRemoteDataSource {

    override fun getMyFood(): List<MyFoodEntity> {
        return transaction(db) {
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