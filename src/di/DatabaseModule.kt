package com.adedom.myfood.di

import com.adedom.myfood.data.db.dao.MyFoodDao
import com.adedom.myfood.data.db.dao.MyFoodDaoImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val databaseModule = DI.Module(name = "database") {

    bindSingleton<MyFoodDao> { MyFoodDaoImpl() }
}