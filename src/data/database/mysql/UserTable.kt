package com.adedom.myfood.data.database.mysql

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

object UserTable : Table("user") {

    val userId = varchar(name = "user_id", length = 50)
    val username = varchar(name = "username", length = 50)
    val password = varchar(name = "password", length = 150)
    val name = varchar(name = "name", length = 150)
    val email = varchar(name = "email", length = 150).nullable()
    val mobileNo = varchar(name = "mobile_no", length = 20).nullable()
    val address = varchar(name = "address", length = 300).nullable()
    val image = varchar(name = "image", length = 500).nullable()
    val status = varchar(name = "status", length = 10)
    val created = datetime(name = "created")
    val updated = datetime(name = "updated").nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(userId, name = "PK_User_ID")
}