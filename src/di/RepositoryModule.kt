package com.adedom.myfood.di

import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.data.repositories.auth.AuthRepositoryImpl
import com.adedom.myfood.data.repositories.category.CategoryRepository
import com.adedom.myfood.data.repositories.category.CategoryRepositoryImpl
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.data.repositories.food.FoodRepositoryImpl
import com.adedom.myfood.data.repositories.profile.ProfileRepository
import com.adedom.myfood.data.repositories.profile.ProfileRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val repositoryModule = DI.Module(name = "repository") {

    bindSingleton<FoodRepository> { FoodRepositoryImpl(instance()) }
    bindSingleton<AuthRepository> { AuthRepositoryImpl(instance()) }
    bindSingleton<ProfileRepository> { ProfileRepositoryImpl(instance()) }
    bindSingleton<CategoryRepository> { CategoryRepositoryImpl(instance()) }
}