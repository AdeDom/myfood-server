package com.adedom.myfood.data.database.h2

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object FoodAndCategoryTableH2 : Table(name = "food_and_category") {

    val foodAndCategoryId = integer("food_and_category_id").autoIncrement()

    val foodId = integer(name = "food_id")
    val foodName = varchar(name = "food_name", length = 100)
    val alias = varchar(name = "alias", length = 200).nullable()
    val foodImage = varchar(name = "food_image", length = 500)
    val price = double(name = "price")
    val description = varchar(name = "description", length = 1000).nullable()
    val status = varchar(name = "status", length = 10)
    val foodCreated = datetime(name = "food_created")
    val foodUpdated = datetime(name = "food_updated").nullable()

    val categoryId = integer(name = "category_id")
    val categoryName = varchar(name = "category_name", length = 100)
    val categoryImage = varchar(name = "category_image", length = 500)
    val categoryCreated = datetime(name = "category_created")
    val categoryUpdated = datetime(name = "category_updated").nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(foodAndCategoryId, name = "PK_FoodAndCategoryId_ID")
}