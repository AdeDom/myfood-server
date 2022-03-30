package com.adedom.myfood.data.database.mysql

import org.jetbrains.exposed.sql.Table

object MyFoodTable : Table(name = "my_food") {

    val id = integer(name = "id").autoIncrement()
    val foodDefault = varchar(name = "food_default", length = 200)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id, name = "PK_MyFood_ID")
}