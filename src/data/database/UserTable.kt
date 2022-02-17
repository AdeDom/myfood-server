package com.adedom.myfood.data.database

import org.jetbrains.exposed.sql.Table

object UserTable : Table("user") {

    val userId = varchar(name = "user_id", length = 50)
    val username = varchar(name = "username", length = 50)
    val password = varchar(name = "password", length = 150)
    val name = varchar(name = "name", length = 150)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(userId, name = "PK_User_ID")
}