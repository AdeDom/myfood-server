package com.adedom.myfood.data.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object FavoriteTable : Table(name = "favorite") {

    val favoriteId = integer(name = "favorite_id").autoIncrement()
    val userId = varchar(name = "user_id", length = 50)
    val foodId = integer(name = "food_id")
    val created = datetime(name = "created")
    val updated = datetime(name = "updated").nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(favoriteId, name = "PK_Favorite_ID")
}