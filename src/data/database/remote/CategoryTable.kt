package com.adedom.myfood.data.database.remote

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object CategoryTable : Table(name = "category") {

    val categoryId = integer(name = "category_id").autoIncrement()
    val categoryName = varchar(name = "category_name", length = 100)
    val image = varchar(name = "image", length = 500)
    val created = datetime(name = "created")
    val updated = datetime(name = "updated").nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(categoryId, name = "PK_Category_ID")
}