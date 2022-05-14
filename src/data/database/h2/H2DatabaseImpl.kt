package com.adedom.myfood.data.database.h2

import com.adedom.myfood.data.database.mysql.CategoryTable
import com.adedom.myfood.data.database.mysql.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class H2DatabaseImpl : H2Database {

    private val database: Database = Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", "org.h2.Driver")

    init {
        transaction(database) {
            SchemaUtils.create(
                UserTable,
                CategoryTable,
            )
        }
    }

    override fun getDatabase(): Database {
        return database
    }
}