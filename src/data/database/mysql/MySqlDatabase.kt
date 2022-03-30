package com.adedom.myfood.data.database.mysql

import org.jetbrains.exposed.sql.Database

interface MySqlDatabase {

    fun getDatabase(): Database
}