package com.adedom.myfood.data.database.h2

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object FoodAndCategoryTableH2 : Table(name = "food_and_category") {

    val foodAndCategoryId = integer("food_and_category_id").autoIncrement()

    val foodId = integer(name = "food_id").nullable()
    val foodName = varchar(name = "food_name", length = 100).nullable()
    val alias = varchar(name = "alias", length = 200).nullable()
    val foodImage = varchar(name = "food_image", length = 500).nullable()
    val price = double(name = "price").nullable()
    val description = varchar(name = "description", length = 1000).nullable()
    val status = varchar(name = "status", length = 10).nullable()
    val foodCreated = datetime(name = "food_created").nullable()
    val foodUpdated = datetime(name = "food_updated").nullable()

    val categoryId = integer(name = "category_id")
    val categoryName = varchar(name = "category_name", length = 100)
    val categoryImage = varchar(name = "category_image", length = 500)
    val categoryTypeName = varchar(name = "category_type_name", length = 500).default("normal")
    val categoryCreated = datetime(name = "category_created")
    val categoryUpdated = datetime(name = "category_updated").nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(foodAndCategoryId, name = "PK_FoodAndCategoryId_ID")
}