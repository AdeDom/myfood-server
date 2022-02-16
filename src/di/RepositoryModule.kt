package com.adedom.myfood.di

import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.data.repositories.food.FoodRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val repositoryModule = DI.Module(name = "repository") {

    bindSingleton<FoodRepository> { FoodRepositoryImpl() }
}