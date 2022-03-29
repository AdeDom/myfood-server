package com.adedom.myfood.di

import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSource
import com.adedom.myfood.data.resouce.remote.auth.AuthRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.category.CategoryRemoteDataSource
import com.adedom.myfood.data.resouce.remote.category.CategoryRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.favorite.FavoriteRemoteDataSource
import com.adedom.myfood.data.resouce.remote.favorite.FavoriteRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.food.FoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.FoodRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSource
import com.adedom.myfood.data.resouce.remote.food.MyFoodRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.profile.ProfileRemoteDataSource
import com.adedom.myfood.data.resouce.remote.profile.ProfileRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.rating_score.RatingScoreRemoteDataSource
import com.adedom.myfood.data.resouce.remote.rating_score.RatingScoreRemoteDataSourceImpl
import com.adedom.myfood.data.resouce.remote.user.UserRemoteDataSource
import com.adedom.myfood.data.resouce.remote.user.UserRemoteDataSourceImpl
import com.adedom.myfood.utility.constant.AppConstant
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val remoteDataSourceModule = DI.Module(name = "remote_data_source") {

    bindSingleton<UserRemoteDataSource> { UserRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<MyFoodRemoteDataSource> { MyFoodRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<FavoriteRemoteDataSource> { FavoriteRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
    bindSingleton<RatingScoreRemoteDataSource> { RatingScoreRemoteDataSourceImpl(instance(tag = AppConstant.MY_SQL_DB)) }
}