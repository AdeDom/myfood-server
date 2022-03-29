package com.adedom.myfood.di

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
import com.adedom.myfood.utility.constant.AppConstant
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val localDataSourceModule = DI.Module(name = "local_data_source") {

    bindSingleton<FavoriteLocalDataSource> { FavoriteLocalDataSourceImpl(instance(tag = AppConstant.SQLITE_DB)) }
    bindSingleton<RatingScoreLocalDataSource> { RatingScoreLocalDataSourceImpl(instance(tag = AppConstant.SQLITE_DB)) }

    bindSingleton<UserLocalDataSource> { UserLocalDataSourceImpl(instance(tag = AppConstant.H2_DB)) }
    bindSingleton<CategoryLocalDataSource> { CategoryLocalDataSourceImpl(instance(tag = AppConstant.H2_DB)) }
    bindSingleton<FoodLocalDataSource> { FoodLocalDataSourceImpl(instance(tag = AppConstant.H2_DB)) }
    bindSingleton<FoodAndCategoryLocalDataSource> { FoodAndCategoryLocalDataSourceImpl(instance(tag = AppConstant.H2_DB)) }
}