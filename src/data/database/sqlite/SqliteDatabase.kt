package com.adedom.myfood.data.database.sqlite

import org.jetbrains.exposed.sql.Database

interface SqliteDatabase {

    fun getDatabase(): Database
}