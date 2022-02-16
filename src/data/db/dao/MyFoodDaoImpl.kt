package com.adedom.myfood.data.db.dao

import com.adedom.myfood.data.db.models.MyFoodEntity
import com.adedom.myfood.data.db.table.MyFoodTable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class MyFoodDaoImpl : MyFoodDao {

    override fun getMyFood(): List<MyFoodEntity> {
        return transaction {
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