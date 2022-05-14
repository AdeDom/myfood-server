package com.adedom.myfood.di

import com.adedom.myfood.data.database.mysql.MySqlDatabase
import com.adedom.myfood.data.resouce.data_source_provider.DataSourceProvider
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
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val remoteDataSourceModule = DI.Module(name = "remote_data_source") {

    bindSingleton { DataSourceProvider() }

    bindSingleton<UserRemoteDataSource> { UserRemoteDataSourceImpl(instance<MySqlDatabase>().getDatabase()) }
    bindSingleton<MyFoodRemoteDataSource> { MyFoodRemoteDataSourceImpl(instance<MySqlDatabase>().getDatabase()) }
    bindSingleton<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(instance<MySqlDatabase>().getDatabase()) }
    bindSingleton<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl(instance<MySqlDatabase>().getDatabase()) }
    bindSingleton<CategoryRemoteDataSource> { CategoryRemoteDataSourceImpl(instance<MySqlDatabase>().getDatabase()) }
    bindSingleton<FoodRemoteDataSource> { FoodRemoteDataSourceImpl(instance<MySqlDatabase>().getDatabase()) }
    bindSingleton<FavoriteRemoteDataSource> { FavoriteRemoteDataSourceImpl(instance<MySqlDatabase>().getDatabase()) }
    bindSingleton<RatingScoreRemoteDataSource> { RatingScoreRemoteDataSourceImpl(instance<MySqlDatabase>().getDatabase()) }
}