package com.adedom.myfood.data.database.h2

import org.jetbrains.exposed.sql.Database

interface H2Database {

    fun getDatabase(): Database
}