package com.adedom.myfood.data.database.local

import org.jetbrains.exposed.sql.Table

object FavoriteTableSqlite : Table(name = "favorite") {

    val favoriteId = text(name = "favorite_id")
    val userId = text(name = "user_id")
    val foodId = integer(name = "food_id")
    val backupState = integer(name = "backup_state")
    val created = text(name = "created")
    val updated = text(name = "updated").nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(favoriteId, name = "PK_Favorite_ID")
}