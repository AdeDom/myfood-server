package com.adedom.myfood.data.resouce.local.food

import org.jetbrains.exposed.sql.Database

class FoodLocalDataSourceImpl(
    private val db: Database,
) : FoodLocalDataSource {
}