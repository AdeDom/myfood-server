package com.adedom.myfood.di

import com.adedom.myfood.data.repositories.auth.AuthRepository
import com.adedom.myfood.data.repositories.auth.AuthRepositoryImpl
import com.adedom.myfood.data.repositories.category.CategoryRepository
import com.adedom.myfood.data.repositories.category.CategoryRepositoryImpl
import com.adedom.myfood.data.repositories.favorite.FavoriteRepository
import com.adedom.myfood.data.repositories.favorite.FavoriteRepositoryImpl
import com.adedom.myfood.data.repositories.food.FoodRepository
import com.adedom.myfood.data.repositories.food.FoodRepositoryImpl
import com.adedom.myfood.data.repositories.profile.ProfileRepository
import com.adedom.myfood.data.repositories.profile.ProfileRepositoryImpl
import com.adedom.myfood.data.repositories.rating_score.RatingScoreRepository
import com.adedom.myfood.data.repositories.rating_score.RatingScoreRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val repositoryModule = DI.Module(name = "repository") {

    bindSingleton<FoodRepository> { FoodRepositoryImpl(instance(), instance(), instance(), instance()) }
    bindSingleton<AuthRepository> { AuthRepositoryImpl(instance(), instance(), instance()) }
    bindSingleton<ProfileRepository> {
        ProfileRepositoryImpl(
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
        )
    }
    bindSingleton<CategoryRepository> { CategoryRepositoryImpl(instance(), instance()) }
    bindSingleton<FavoriteRepository> { FavoriteRepositoryImpl(instance(), instance()) }
    bindSingleton<RatingScoreRepository> { RatingScoreRepositoryImpl(instance(), instance()) }
}