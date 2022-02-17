package com.adedom.myfood.di

import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.data.repositories.auth.AuthRepositoryImpl
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.data.repositories.food.FoodRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val repositoryModule = DI.Module(name = "repository") {

    bindSingleton<FoodRepository> { FoodRepositoryImpl(instance()) }
    bindSingleton<AuthRepository> { AuthRepositoryImpl(instance()) }
}