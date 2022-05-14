package com.adedom.myfood.di

import com.adedom.myfood.data.database.h2.H2Database
import com.adedom.myfood.data.database.sqlite.SqliteDatabase
import com.adedom.myfood.data.resouce.local.auth.AuthLocalDataSource
import com.adedom.myfood.data.resouce.local.auth.AuthLocalDataSourceImpl
import com.adedom.myfood.data.resouce.local.category.CategoryLocalDataSource
import com.adedom.myfood.data.resouce.local.category.CategoryLocalDataSourceImpl
import com.adedom.myfood.data.resouce.local.favorite.FavoriteLocalDataSource
import com.adedom.myfood.data.resouce.local.favorite.FavoriteLocalDataSourceImpl
import com.adedom.myfood.data.resouce.local.food.FoodLocalDataSource
import com.adedom.myfood.data.resouce.local.food.FoodLocalDataSourceImpl
import com.adedom.myfood.data.resouce.local.food_and_category.FoodAndCategoryLocalDataSource
import com.adedom.myfood.data.resouce.local.food_and_category.FoodAndCategoryLocalDataSourceImpl
import com.adedom.myfood.data.resouce.local.rating_score.RatingScoreLocalDataSource
import com.adedom.myfood.data.resouce.local.rating_score.RatingScoreLocalDataSourceImpl
import com.adedom.myfood.data.resouce.local.user.UserLocalDataSource
import com.adedom.myfood.data.resouce.local.user.UserLocalDataSourceImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val localDataSourceModule = DI.Module(name = "local_data_source") {

    bindSingleton<FavoriteLocalDataSource> { FavoriteLocalDataSourceImpl(instance<SqliteDatabase>().getDatabase()) }
    bindSingleton<RatingScoreLocalDataSource> { RatingScoreLocalDataSourceImpl(instance<SqliteDatabase>().getDatabase()) }
    bindSingleton<AuthLocalDataSource> { AuthLocalDataSourceImpl(instance<SqliteDatabase>().getDatabase()) }

    bindSingleton<UserLocalDataSource> { UserLocalDataSourceImpl(instance<H2Database>().getDatabase()) }
    bindSingleton<CategoryLocalDataSource> { CategoryLocalDataSourceImpl(instance<H2Database>().getDatabase()) }
    bindSingleton<FoodLocalDataSource> { FoodLocalDataSourceImpl() }
    bindSingleton<FoodAndCategoryLocalDataSource> { FoodAndCategoryLocalDataSourceImpl() }
}